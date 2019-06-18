package com.myflair.front.service;

import com.alibaba.fastjson.JSONObject;
import com.myflair.common.pojo.CouponInfo;
import com.myflair.common.pojo.MobileFanns;
import com.myflair.common.pojo.OrderInfo;
import com.myflair.common.pojo.PullNewMember;
import com.myflair.front.controller.PullNewMemberController;
import com.myflair.front.dao.PullNewMemberDao;
import com.myflair.third.dao.YouZanDataDao;
import com.myflair.third.util.YouZanUtil;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanUmpCouponSearchResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanUmpCouponTakeResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanUmpCouponsUnfinishedSearchResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.Map;

/**
 * Created by user on 2018/3/25.
 */
@Service
public class PullNewMemberService {

    private final Logger logger = Logger.getLogger(PullNewMemberService.class);

    @Autowired
    private PullNewMemberDao pullNewMemberDao;
    @Autowired
    private YouZanDataDao youZanDataDao;

    @Transactional(rollbackFor=Exception.class)
    public JSONObject pullNewMemHandler(Map<String,Object> params) throws Exception {
       JSONObject jsonObject = new JSONObject();
       jsonObject.put("code",2);
       jsonObject.put("result","success");
       jsonObject.put("msg","老用户");
        String mobile = (String) params.get("beInviteMobile");
        registCoupon(mobile);
        PullNewMember pullNewMember =  pullNewMemberDao.getByParams(params);
        if(pullNewMember == null){
           OrderInfo orderInfo = pullNewMemberDao.getOrderInfoByParams(params);
           if(orderInfo == null ){
               Boolean isMemOrCustom = YouZanUtil.isMemOrCustomByMobile(mobile);
               pullNewMember = new PullNewMember();
               pullNewMember.setBeInviteReturnTicketStatus(3);//老用户 不参与
               try{

                   YouZanUtil.cretateCoustom(mobile);
               }catch (Exception e){
                   logger.warn("创建用户失败："+e.getMessage());
               }
               // 调用接口给被邀请人发券
               if(!isMemOrCustom){
                   registCoupon(mobile);
                   if (insertNewMemberCoupon(jsonObject, mobile, pullNewMember)) return jsonObject;
               }else{
                   Long fansiId= YouZanUtil.getFansIdByMobile(mobile);
                   if(fansiId != null && YouZanUtil.userIsExistsOrder(fansiId)){
                       pullNewMember.setBeInviteReturnTicketStatus(3);//老用户 不参与
                   }else{
                       if (insertNewMemberCoupon(jsonObject, mobile, pullNewMember)) return jsonObject;
                       jsonObject.put("code",1);
                       jsonObject.put("result","success");
                       jsonObject.put("msg","新用户领取成功");
                   }
               }
               pullNewMember.setBeInviteReturnTicketTime(new Date());
               pullNewMember.setBeInviteMobile((String) params.get("beInviteMobile"));
               pullNewMember.setInviteMobile((String) params.get("inviteMobile"));
               pullNewMember.setReturnTicketStatus(0);
               pullNewMemberDao.insertPullNewMem(pullNewMember);
           }
       }
        return jsonObject;
    }

    private void registCoupon(String mobile) {
        //给一个注册券 位置
        try{
            YouzanUmpCouponSearchResult.CouponGroup coupon = YouZanUtil.getCoupon("新人体验券");
            if(coupon != null){
                YouzanUmpCouponTakeResult.UmpPromocardUserTakedetail result = YouZanUtil.couponPull(mobile,coupon.getId());
                // 插入返券记录
                if(result != null){
                    CouponInfo couponInfo = new CouponInfo();
                    couponInfo.setCouponCondition(result.getCondition());
                    couponInfo.setCouponId(result.getPromocardId());
                    couponInfo.setUseStartTime(result.getStartAt());
                    couponInfo.setUseEndTime(result.getEndAt());
                    couponInfo.setReduceValue(result.getValue());
                    couponInfo.setTitle(result.getTitle());
                    couponInfo.setMobile(mobile);
                    couponInfo.setStatus(3);
                    youZanDataDao.insertCouponInfo(couponInfo);
                }
            }
        }catch (Exception e){
            logger.error("新用户领取注册券报错"+e.getMessage());
        }
    }

    private boolean insertNewMemberCoupon(JSONObject jsonObject, String mobile, PullNewMember pullNewMember) throws Exception {
        YouzanUmpCouponSearchResult.CouponGroup coupon = YouZanUtil.getCoupon("分悦代金券");
        if(coupon != null){
            YouzanUmpCouponTakeResult.UmpPromocardUserTakedetail result = YouZanUtil.couponPull(mobile,coupon.getId());
            // 插入返券记录
            if(result != null){
                CouponInfo couponInfo = new CouponInfo();
                couponInfo.setCouponCondition(result.getCondition());
                couponInfo.setCouponId(result.getPromocardId());
                couponInfo.setUseStartTime(result.getStartAt());
                couponInfo.setUseEndTime(result.getEndAt());
                couponInfo.setReduceValue(result.getValue());
                couponInfo.setTitle(result.getTitle());
                couponInfo.setMobile(mobile);
                couponInfo.setStatus(1);
                youZanDataDao.insertCouponInfo(couponInfo);
            }else{
                throw new Exception("领券失败！！！没有返回值！！");
            }
            pullNewMember.setBeInviteReturnTicketStatus(1);//领取
            jsonObject.put("code",1);
            jsonObject.put("result","success");
            jsonObject.put("msg","新用户领取成功");
        }else{
            jsonObject.put("code",5);
            jsonObject.put("result","success");
            jsonObject.put("msg","没有优惠券");
            return true;
        }
        return false;
    }


    @Transactional(rollbackFor=Exception.class)
    public JSONObject pullRegistHandler(JSONObject res,String mobile) throws Exception {
        try{

            YouZanUtil.cretateCoustom(mobile);
        }catch (Exception e){
            logger.warn("创建用户失败："+e.getMessage());
        }
        YouzanUmpCouponSearchResult.CouponGroup coupon = YouZanUtil.getCoupon("新人体验券");
        if(coupon != null){
            YouzanUmpCouponTakeResult.UmpPromocardUserTakedetail result = YouZanUtil.couponPull(mobile,coupon.getId());
            // 插入返券记录
            if(result != null){
                CouponInfo couponInfo = new CouponInfo();
                couponInfo.setCouponCondition(result.getCondition());
                couponInfo.setUseStartTime(result.getStartAt());
                couponInfo.setCouponId(result.getPromocardId());
                couponInfo.setUseEndTime(result.getEndAt());
                couponInfo.setReduceValue(result.getValue());
                couponInfo.setTitle(result.getTitle());
                couponInfo.setMobile(mobile);
                couponInfo.setStatus(3);
                youZanDataDao.insertCouponInfo(couponInfo);
            }else{
                throw new Exception("领券失败！！！没有返回值！！");
            }
        }else{
            res.put("code",5);
            res.put("success",false);
            res.put("msg","活动结束");
        }
        return res;
    }

}
