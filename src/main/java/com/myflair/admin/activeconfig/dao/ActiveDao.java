package com.myflair.admin.activeconfig.dao;

import com.myflair.common.annotation.MyBatisRepository;
import com.myflair.common.pojo.ActiveConfigInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/5/20.
 */
@Component
@MyBatisRepository
public interface ActiveDao {
    int delete(Long id);

    ActiveConfigInfo selectById(Long id);

    ActiveConfigInfo query(Map<String, Object> params);

    List<ActiveConfigInfo> queryList(Map<String, Object> params);

    int queryCount(Map<String, Object> params);

    int insert(ActiveConfigInfo activeConfigInfo);

    int update(ActiveConfigInfo activeConfigInfo);
}
