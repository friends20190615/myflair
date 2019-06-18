package com.myflair.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myflair.common.utils.CookieUtil;
import com.myflair.common.utils.EncryptUtils;
import org.apache.commons.lang3.StringUtils;

import com.myflair.common.constant.NewsConstant;
import com.myflair.common.constant.UserRoleEnum;


public class AuthFilter implements Filter{
	//private final static Logger logger = Logger.getLogger(AuthFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		 String uri = req.getRequestURI();  
		 String LOGIN_PATH = req.getContextPath() + "/user/login.do";
		 if(uri.equals("/") || uri.equals("/myflair/")){
			// String new_url = "http://w-21208-89053-33573.139196136184.sites.cn87.qifeiye.com";
			 String new_url="http://www.myflair.wang";
			 resp.sendRedirect(new_url);
			// chain.doFilter(request, response);
			 return;
		 }else if(uri.equals("/myflair")
				 || uri.contains("/user/login.do")  ||  uri.contains("/html/")
				 || uri.contains("getERWMImage.do")
				 || uri.contains("getERWMImage1.do")
				 || uri.contains("/youzan/")
				 || uri.contains("/pull/")
				 || uri.contains("/weixinShare")){
			 chain.doFilter(request, response);
			 return;
		 }else{
			 try{
				 String token = CookieUtil.getCookieValue("token", req);
				 if(StringUtils.isNotEmpty(token)){
					 String deToken = EncryptUtils.decrypt(NewsConstant.SEC_KEY, token);
					 String[] arr = deToken.split("#");
					 if(arr.length==2){
						 Integer roleId = Integer.parseInt(arr[1]);
						 boolean isAdmin =  UserRoleEnum.ADMIN.getType().intValue()==roleId.intValue();
						 boolean isNewsPublisher  = UserRoleEnum.NEWS_PUBLISHER.getType().intValue()==roleId.intValue(); 
						 if(isAdmin){
							 chain.doFilter(request, response);
							 return;
						 }
						 if(isNewsPublisher){
							 if(uri.contains("/user/userlist.do")  ||uri.contains("/user/userdetail.do") || uri.contains("/user/delete.do") || uri.contains("/user/saveuser.do")){
								 resp.sendRedirect(LOGIN_PATH);
								 return;
							 }else{
								 chain.doFilter(request, response);
								 return;
							 }
						 }
					 }
					
					 
					 resp.sendRedirect(LOGIN_PATH);
					 return;
				 }else{
					 resp.sendRedirect(LOGIN_PATH);
					 return;
				 }
			 }catch(Exception e ){
				 e.printStackTrace();
			 }
			
		 } 
		
	}

	@Override
	public void destroy() {

	}

}
