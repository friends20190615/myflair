package com.myflair.admin.lachine.service;

import com.myflair.admin.lachine.dao.LachineDao;
import com.myflair.common.pojo.PullNewMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/7/1.
 */
@Service
public class LachineService {

    @Autowired
    LachineDao lachineDao;


    public List<PullNewMember> queryList(Map<String, Object> params) {
        return lachineDao.queryList(params);
    }

    public int queryCount(Map<String, Object> params) {
        return lachineDao.queryCount(params);
    }
}
