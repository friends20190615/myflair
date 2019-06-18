package com.myflair.admin.activeconfig.service;

import com.myflair.admin.activeconfig.dao.ActiveDao;
import com.myflair.common.pojo.ActiveConfigInfo;
import com.myflair.common.pojo.Result;
import com.myflair.common.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/5/20.
 */
@Service
public class ActiceService {

    @Autowired
    private ActiveDao activeDao;

    public int delete(Long id) {
        return activeDao.delete(id);
    }
    public ActiveConfigInfo selectById(Long id) {
        return activeDao.selectById(id);
    }


    public ActiveConfigInfo queryByCode(String code) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("code", code);
        return activeDao.query(params);
    }
    public List<ActiveConfigInfo> queryList(Map<String, Object> params) {
        return activeDao.queryList(params);
    }
    public int queryCount(Map<String, Object> params) {
        return activeDao.queryCount(params);
    }

    public Result addActiveConfigInfo(ActiveConfigInfo activeConfigInfo) {
        Result result = new Result();
        int ret = activeDao.insert(activeConfigInfo);
        if(ret>0){
            result.setStatus(0);
            result.setMsg("新增信息成功");
            return result;
        }else{
            result.setStatus(-1);
            result.setMsg("新增信息失败");
            return result;
        }

    }
    public Result update(ActiveConfigInfo activeConfigInfo) {
        Result result = new Result();
        if(null == activeConfigInfo || activeConfigInfo.getId()==null || !isActiveExist(activeConfigInfo.getId())  ){
            result.setStatus(-1);
            result.setMsg("更新失败,信息不存在");
            return result;
        }
        int ret = activeDao.update(activeConfigInfo);
        if(ret>0){
            result.setStatus(0);
            result.setMsg("更新信息成功");
            return result;
        }else{
            result.setStatus(-1);
            result.setMsg("更新信息失败");
            return result;
        }
    }

    private boolean isActiveExist(Long id) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id", id);
        int count  =  queryCount(params);
        return count>0;
    }


}
