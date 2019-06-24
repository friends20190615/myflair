package com.myflair.third.dao;

import com.myflair.common.annotation.MyBatisRepository;
import com.myflair.common.pojo.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/3/24.
 */

@Component
@MyBatisRepository
public interface YouZanDataDao {

    Integer getCountByOrderId(Map<String, Object> params);

    Integer getCountByOrderNo(Map<String, Object> params);

    OrderInfo getOrderDetail(Map<String, Object> params);

    void updateOrderHistoryStatus(OrderHistory orderHistory);

    Integer insertOrderInfos(OrderInfo orderInfo);

    void insertOrderHistor(OrderHistory orderHistory);

    void updateOrderInfos(OrderInfo orderInfo);

    MobileFanns getMfInfoByOrderInfo(OrderInfo orderInfo);

    PullNewMember getPnmInfoByOrderInfo(OrderInfo orderInfo);

    PullNewMember getBeInviteMember(OrderInfo orderInfo);

    MobileFanns getInviteFans(PullNewMember pullNewMember);

    Long insertMfInfos(MobileFanns mobileFanns);

    void updatePullNewMember(PullNewMember pullNewMember);

    void insertCouponInfo(CouponInfo couponInfo);

    PullNewMember getBeInviteMemberAndNoReturnTicket(Map<String, Object> params);

    void updateBeInviteMember(PullNewMember pullNewMember);

    void updateOrderInfoStatus(OrderInfo orderInfo);

    List<PullNewMember> getNotReturnCouponInviteMobiles();

    void updatePullNewMemberInviteMobileStatus(PullNewMember pullNewMember);

    Integer getRegistCouponByMobile(Map<String, Object> params);

    void insertIntegralInfo(IntegralInfo integralInfo);

    void updateOrderWlStats(Map<String, Object> params);
}
