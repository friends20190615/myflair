package com.myflair.front.dao;

import com.myflair.common.annotation.MyBatisRepository;
import com.myflair.common.pojo.MobileFanns;
import com.myflair.common.pojo.OrderInfo;
import com.myflair.common.pojo.PullNewMember;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/3/25.
 */

@Component
@MyBatisRepository
public interface PullNewMemberDao {

    PullNewMember getByParams(Map<String, Object> params);

    OrderInfo getOrderInfoByParams(Map<String, Object> params);

    MobileFanns getMobileFannsByParams(Map<String, Object> params);

    void insertPullNewMem(PullNewMember pullNewMember);

    List<PullNewMember> getBimListByInviteMobile(Map<String, Object> params);

}
