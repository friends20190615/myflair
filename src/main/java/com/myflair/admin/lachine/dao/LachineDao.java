package com.myflair.admin.lachine.dao;

import com.myflair.common.annotation.MyBatisRepository;
import com.myflair.common.pojo.PullNewMember;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/7/1.
 */

@Component
@MyBatisRepository
public interface LachineDao {


    List<PullNewMember> queryList(Map<String, Object> params);

    int queryCount(Map<String, Object> params);
}
