package com.myflair.admin.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myflair.common.utils.CookieUtil;
import com.myflair.common.utils.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myflair.common.constant.NewsConstant;
import com.myflair.common.pojo.Result;
import com.myflair.common.pojo.User;
import com.myflair.common.utils.DateUtils;
import com.myflair.admin.user.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	private final static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/jsp/index.jsp");
		return mv;
	}

	@RequestMapping(value = "/userlist")
	public ModelAndView newslist(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/WEB-INF/views/user/userlist.jsp");
		String pageStr = request.getParameter("page");
		String statusStr = request.getParameter("status");
		int pageNo = NumberUtils.toInt(pageStr, 1);
		int status = NumberUtils.toInt(statusStr, 0);
		Map<String, Object> params = new HashMap<String, Object>();
		int start = (pageNo - 1) * NewsConstant.PAGESIZE;
		params.put("start", start);
		params.put("pageSize", NewsConstant.PAGESIZE);
		if (status != 0) {
			params.put("status", status);
		}
		try {
			List<User> list = userService.queryList(params);
			int total = userService.queryCount(params);
			mv.addObject("list", list);

			int totalPage = (total - 1) / NewsConstant.PAGESIZE + 1;
			mv.addObject("count", total);// 总条数
			mv.addObject("totalPage", totalPage);// 总页数
			mv.addObject("dqPage", pageNo);// 当前页
			mv.addObject("pageSize", NewsConstant.PAGESIZE);// 当前页
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return mv;
	}

	@RequestMapping(value = "/userdetail")
	public ModelAndView useradd(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/WEB-INF/views/user/userdetail.jsp");
		String action = request.getParameter("action");
		mv.addObject("action", action);
		if ("modify".equals(action)) {
			String idStr = request.getParameter("id");
			if (null != idStr && !"".equals(idStr)) {
				Long id = Long.parseLong(idStr);
				User user = userService.selectById(id);
				mv.addObject("user", user);
			}
		}
		return mv;
	}

	@RequestMapping(value = "/saveuser")
	@ResponseBody
	public String saveuser(@RequestParam(value = "req") String req,
			@RequestParam(value = "action") String action,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		Result<String> result = new Result<String>();
		logger.error(req);
		User user = (User) JSONObject.parseObject(req, User.class);
		String now = DateUtils.date2String(new Date(), DateUtils.dateTimeSecondPattern);
		user.setCreateTime(now);
		user.setUpdateTime(now);

		if ("modify".equals(action)) {
			result = userService.updateUser(user);
		} else {
			result = userService.addUser(user);
		}
		logger.error(user.toString());
		return JSONObject.toJSONString(result);
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(@RequestParam(value = "id") Long id,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		Result<String> result = new Result<String>();

		int ret = userService.delete(id);
		if (ret > 0) {
			result.setStatus(0);
			result.setMsg("删除成功!");
		} else {
			result.setStatus(-1);
			result.setMsg("删除失败，请重试!");
		}
		return JSONObject.toJSONString(result);
	}
	    

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public ModelAndView toLogin(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/WEB-INF/views/user/login.jsp");
		return mv;
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public ModelAndView getLogin(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		ModelAndView mv = new ModelAndView();
		try {
			if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
				mv.addObject("result", "failed");
				mv.addObject("msg","用户名或密码不能为空");
				mv.setViewName("/WEB-INF/views/user/login.jsp");
				return mv;
			}
			
			User user = userService.queryByUsername(username,password);
			if (null == user ||  user.getStatus() != 1) {
				mv.addObject("result", "failed");
				mv.addObject("msg", "登录失败");
				mv.setViewName("/WEB-INF/views/user/login.jsp");
				return mv;
			}else{
				String unique = user.getUsername()+"#" + user.getRoleId();
				String token = EncryptUtils.encrypt(NewsConstant.SEC_KEY, unique);
				CookieUtil.addCookie("token", token, response);
				String nick = StringUtils.isNotEmpty(user.getNick())? user.getNick() :user.getUsername();
				CookieUtil.addCookie("nick", nick, response);
				CookieUtil.addCookie("uid", String.valueOf(user.getId()), response);
				CookieUtil.addCookie("rid", String.valueOf(user.getRoleId()), response);
				mv.setViewName("redirect:/user/index.do");
				return mv;
			}
		} catch (Exception e) {
			mv.addObject("result", "failed");
			mv.addObject("msg", "登录失败");
			mv.setViewName("/WEB-INF/views/user/login.jsp");
			logger.error(e.getMessage(),e);
			return mv;
		}
	}

	@RequestMapping(value = "/outlogin.do")
	public @ResponseBody
	String outlogin(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("islogin", 0);
		/*
		 * HttpSession session = request.getSession();
		 * session.removeAttribute("user");
		 */
		CookieUtil.expireCookie("token", request, response);
		return "success";
	}
}
