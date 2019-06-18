package com.myflair.third.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.myflair.common.constant.OrderStatusEnum;
import com.myflair.common.pojo.CouponInfo;
import com.myflair.common.pojo.OrderInfo;
import com.myflair.common.pojo.PullNewMember;
import com.myflair.common.utils.DateUtils;
import com.myflair.third.dao.YouZanDataDao;
import com.myflair.third.util.YouZanUtil;
import com.mysql.jdbc.StringUtils;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeGetResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanUmpCouponSearchResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanUmpCouponTakeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2018/3/24.
 */
@Service
public class YouZanDataService {

    @Autowired
    private YouZanDataDao youZanDataDao;

    public Boolean dataHandler(String msg) throws Exception {
        if(!StringUtils.isNullOrEmpty(msg)){
            JSONObject jsonObject = JSON.parseObject(msg);
            String orderNO = jsonObject.getString("tid");
            if(org.apache.commons.lang3.StringUtils.isNotBlank(orderNO)){
                // 调用有赞接口 获取订单详情信息
                YouzanTradeGetResult result = YouZanUtil.getOrderDetail(orderNO);
                if(result != null && result.getTrade()!= null){
                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setOrderNo(orderNO);
                    YouzanTradeGetResult.FansInfo  fansInfo= result.getTrade().getFansInfo();
                    if(fansInfo != null && fansInfo.getFansId() != null){
                        orderInfo.setFansiId(fansInfo.getFansId());
                        orderInfo.setStats(OrderStatusEnum.getIndex(result.getTrade().getStatus()));
                        // 判断order id 是否存在
                        Map<String,Object> params = new HashMap<String, Object>();
                        params.put("orderNo",orderInfo.getOrderNo());
                        OrderInfo newOrderInfo = youZanDataDao.getOrderDetail(params);
                        if(newOrderInfo != null){// 存在
                            orderInfo.setOrderId(newOrderInfo.getOrderId());
                            orderInfo.setMobile(newOrderInfo.getMobile());
                            orderInfo.setId(newOrderInfo.getId());
                            orderInfo.setCreateTime(newOrderInfo.getCreateTime());
                            existOrderIdHandler(orderInfo);
                        }else{// 不存在
                            notExistOrderIdHandler(orderInfo);
                        }
                    }
                }
            }
        }
        return true;
    }

    /*
    *
    * 不存在的时候逻辑处理
    */
    @Transactional(rollbackFor=Exception.class)
    private void notExistOrderIdHandler(OrderInfo orderInfo) throws Exception {
        // 调用有赞的 会员详情接口 获取用户手机号
        String mobile = YouZanUtil.getMemberMobileByFansId(orderInfo.getFansiId());
        if(mobile != null){
            orderInfo.setMobile(mobile);
        }
        youZanDataDao.insertOrderInfos(orderInfo);
    }
    /*
    * 存在的时候逻辑处理
    */
    @Transactional(rollbackFor=Exception.class)
    private void existOrderIdHandler(OrderInfo orderInfo) throws Exception {
        youZanDataDao.updateOrderInfoStatus(orderInfo);
        PullNewMember pullNewMember = youZanDataDao.getBeInviteMember(orderInfo);
        if (pullNewMember != null){
            pullNewMember.setReturnTicketStatus(0);
            pullNewMember.setOrderId(orderInfo.getId().longValue());
            if(pullNewMember.getFirstOrderTime() == null){
                pullNewMember.setFirstOrderTime(DateUtils.dateString2Date(orderInfo.getCreateTime(),DateUtils.dateTimeSecondPattern));
            }
            pullNewMember.setInviteStatus(1);
            youZanDataDao.updatePullNewMember(pullNewMember);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public void handlerCustomer(String msg) throws Exception {
        JSONObject jsonObject = JSON.parseObject(msg);
        String mobile = jsonObject.getString("mobile");
        if(org.apache.commons.lang3.StringUtils.isNotBlank(mobile)){
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("beInviteMobile",mobile);
            PullNewMember pullNewMember = youZanDataDao.getBeInviteMemberAndNoReturnTicket(params);
            if (pullNewMember != null){
                YouzanUmpCouponSearchResult.CouponGroup  coupon = YouZanUtil.getCoupon("新人体验券");
                YouzanUmpCouponTakeResult.UmpPromocardUserTakedetail result =YouZanUtil.couponPull(mobile, coupon.getId());
                insertCouponMethod(result,mobile,1);
                pullNewMember.setBeInviteReturnTicketTime(new Date());
                pullNewMember.setBeInviteReturnTicketStatus(1);
                youZanDataDao.updateBeInviteMember(pullNewMember);
            }
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public void handlerExpress(String msg) throws Exception {// 物流信息
        JSONObject jsonObject = JSON.parseObject(msg);
        String tid = jsonObject.getString("tid");
        if(org.apache.commons.lang3.StringUtils.isNotBlank(tid)){
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("orderNo",tid);
            OrderInfo orderInfo = youZanDataDao.getOrderDetail(params);
            if(orderInfo != null){
                Date updateTime= jsonObject.getDate("update_time");
                Long stat = YouZanUtil.getWlStat(tid);
                params.put("wlStat",stat);
                params.put("wlUpdateTime",updateTime);
                youZanDataDao.updateOrderWlStats(params);
            }

        }
    }

    private void insertCouponMethod( YouzanUmpCouponTakeResult.UmpPromocardUserTakedetail result,String mobile,Integer status) throws Exception {
        if(result != null){
            // 插入返券记录
            CouponInfo couponInfo = new CouponInfo();
            couponInfo.setCouponCondition(result.getCondition());
            couponInfo.setCouponId(result.getPromocardId());
            couponInfo.setUseStartTime(result.getStartAt());
            couponInfo.setReduceValue(result.getValue());
            couponInfo.setUseEndTime(result.getEndAt());
            couponInfo.setTitle(result.getTitle());
            couponInfo.setMobile(mobile);
            couponInfo.setStatus(status);
            youZanDataDao.insertCouponInfo(couponInfo);
        }else{
            throw new Exception("领券失败！！！没有返回值！！");
        }
    }
}
