package com.myflair.third.schedule;

import com.myflair.common.intercepter.StartLister;
import com.myflair.common.pojo.IntegralInfo;
import com.myflair.common.pojo.PullNewMember;
import com.myflair.third.dao.YouZanDataDao;
import com.myflair.third.util.YouZanUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 2018/4/14.
 */

public class MyTask extends QuartzJobBean {

    private final static Logger logger = Logger.getLogger(MyTask.class);

    private YouZanDataDao youZanDataDao = (YouZanDataDao) StartLister.getApplicationContext().getBean("youZanDataDao");

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

        List<PullNewMember> pullNewMemberList = youZanDataDao.getNotReturnCouponInviteMobiles();
        if(!org.springframework.util.CollectionUtils.isEmpty(pullNewMemberList)){
            String mobile;
            for(PullNewMember pullNewMember:pullNewMemberList){
                mobile = pullNewMember.getInviteMobile();
                    if(StringUtils.isNotBlank(mobile)){
                        try {
                           /* YouzanUmpCouponSearchResult.CouponGroup coupon = YouZanUtil.getCoupon("邀请有礼");
                            YouzanUmpCouponTakeResult.UmpPromocardUserTakedetail result =YouZanUtil.couponPull(mobile, coup(){}on.getId());*/
                           if(YouZanUtil.addJf(mobile)){
                               insertCouponMethod(pullNewMember.getOrderId(),mobile);
                               pullNewMember.setReturnTicketStatus(1);
                               pullNewMember.setInviteStatus(2);
                               pullNewMember.setReturnTicketTime(new Date());
                               youZanDataDao.updatePullNewMemberInviteMobileStatus(pullNewMember);
                           }
                        } catch (Exception e) {
                            logger.error("自动任务失败："+e.getMessage());
                        }
                    }
            }

        }


    }

    private void insertCouponMethod(Long id, String mobile){
            // 插入返券记录
        try{
            IntegralInfo integralInfo = new IntegralInfo();
            integralInfo.setCreateTime(new Date());
            integralInfo.setIntegralValue(100L);
            integralInfo.setMobile(mobile);
            integralInfo.setOrderId(id);
            integralInfo.setTitle("积分");
            youZanDataDao.insertIntegralInfo(integralInfo);
        }catch (Exception e){
            logger.error("插入积分数据失败："+e.getMessage());
        }

    }

}
