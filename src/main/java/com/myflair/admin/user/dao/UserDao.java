package com.myflair.admin.user.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.myflair.common.annotation.MyBatisRepository;
import com.myflair.common.pojo.User;

@Component
@MyBatisRepository
public interface UserDao {
 
    int delete(Long id);

  
    int insert(User record);

  
    User selectById(Long id);
    
    User query(Map<String, Object> params);
    
    int update(User record);
    
    List<User> queryList(Map<String, Object> params);
    
    int queryCount(Map<String, Object> params);

    
}