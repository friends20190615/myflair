package com.myflair.admin.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myflair.common.pojo.Result;
import com.myflair.common.pojo.User;
import com.myflair.admin.user.dao.UserDao;

@Service
public class UserService {
	private final static Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	UserDao userDao;
	
	
	public int delete(Long id) {
		return userDao.delete(id);
	}
	public User selectById(Long id) {
		return userDao.selectById(id);
	}
	public User queryByUsername(String username,String password) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("username", username);
		params.put("password", password);
		return userDao.query(params);
	}
	public User queryByEmail(String email) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("email", email);
		return userDao.query(params);
	}
	public List<User> queryList(Map<String, Object> params) {
		return userDao.queryList(params);
	}
	public int queryCount(Map<String, Object> params) {
		return userDao.queryCount(params);
	}
	public boolean isUsernameExist(String username) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("username", username);
		int count  =  queryCount(params);
		return count>0;
	}
	public boolean isEmailExist(String email) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("email", email);
		int count  =  queryCount(params);
		return count>0;
	}
	public boolean isMobileExist(String mobile) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("mobile", mobile);
		int count  =  queryCount(params);
		return count>0;
	}
	public boolean isUserExist(Long id) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		int count  =  queryCount(params);
		return count>0;
	}
	public Result addUser(User user) {
		Result result = new Result();
		if(isUsernameExist(user.getUsername())){
			result.setStatus(-1);
			result.setMsg("用户名已存在");
			return result;
		}
		if(isEmailExist(user.getEmail())){
			result.setStatus(-1);
			result.setMsg("邮箱已存在");
			return result;
		}
		if(isMobileExist(user.getMobile())){
			result.setStatus(-1);
			result.setMsg("手机号已存在");
			return result;
		}
		int ret = userDao.insert(user);
		if(ret>0){
			result.setStatus(0);
			result.setMsg("新增用户成功");
			return result;
		}else{
			result.setStatus(-1);
			result.setMsg("新增用户失败");
			return result;
		}
		
	}
	public Result updateUser(User user) {
		Result result = new Result();
		if(null == user || user.getId()==null || !isUserExist(user.getId())  ){
			result.setStatus(-1);
			result.setMsg("更新失败,用户不存在");
			return result;
		}
		int ret = userDao.update(user);
		if(ret>0){
			result.setStatus(0);
			result.setMsg("更新用户成功");
			return result;
		}else{
			result.setStatus(-1);
			result.setMsg("更新用户失败");
			return result;
		}
	}
}