package com.myflair.common.vo;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


public class JSONResult<T> {

	public final static int ERROR_CODE = -1;

	public final static int NOLOGIN_CODE = -403;// 没有登录状态码

	public final static int SUCCUESS_CODE = 0;

	private int status;

	private String msg;

	private T result;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

    public static String addField(String jsonStr,String key,Object value){
        if(StringUtils.isNotEmpty(jsonStr) && StringUtils.isNotEmpty(key) && value != null){
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            JSONObject resultJsonObj = jsonObject.getJSONObject("result");
            if(resultJsonObj != null){
                resultJsonObj.put(key,value);
                jsonObject.put("result",resultJsonObj);
                return jsonObject.toJSONString();
            }
        }
        return jsonStr;
    }

	public static <M> JSONResult<M> getErrorResult(Integer code, String msg) {
		JSONResult<M> result = new JSONResult<M>();
		result.setStatus(code);
		result.setMsg(msg);
		return result;
	}
	
	public static <Object> JSONResult<Object> getErrorResult(Integer code, String msg,Object obj) {
		JSONResult<Object> result = new JSONResult<Object>();
		result.setStatus(code);
		result.setMsg(msg);
		result.setResult(obj);
		return result;
	}

	public static <M> JSONResult<M> getSuccessResult(M obj) {
		JSONResult<M> result = new JSONResult<M>();
		result.setStatus(SUCCUESS_CODE);
		result.setMsg("成功");
		result.setResult(obj);
		return result;
	}

	public static <M> JSONResult<M> getSuccessResult(int status,String msg,M obj) {
		JSONResult<M> result = new JSONResult<M>();
		result.setStatus(status);
		result.setMsg(msg);
		result.setResult(obj);
		return result;
	}
	
	public static <M> JSONResult<M> getCommonResult(M obj) {
		if (obj != null) {
			return getSuccessResult(obj);
		}
		return getErrorResult(ERROR_CODE, "系统繁忙！请稍后再试！");
	}
	
	/**
	 * 获取没有登录返回的JSON字符串
	 * @return
	 */
	public static String getNoLoginResult() {
		return JSON.toJSONString(JSONResult.getErrorResult(
				JSONResult.NOLOGIN_CODE, "用户信息错误！"),
				SerializerFeature.BrowserCompatible);
	}
	
	
}
