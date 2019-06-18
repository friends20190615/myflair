package com.myflair.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtil {

	public static void addCookie(String key, String value,
			HttpServletResponse response) {
		
		try {
			Cookie cookie  = new Cookie(key, URLEncoder.encode(value,"utf-8"));
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Cookie getCookie(String key, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}

		return cookieMap.get(key);

	}
	
	
	public static String getCookieValue(String key, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Map<String, String> cookieMap = new HashMap<String, String>();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				try {
					cookieMap.put(cookie.getName(),
							URLDecoder.decode(cookie.getValue(),"utf-8"));
				} catch (Exception e) {
					continue;
				}
			}
		}

		return cookieMap.get(key);

	}
	
	
	public static void expireCookie(String key,HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().removeAttribute("userId");

		Cookie cookie = getCookie(key, request);

		if (cookie != null) {
			cookie.setMaxAge(0);
			cookie.setPath("/");

			response.addCookie(cookie);

		}

	}
}
