package com.myflair.common.pojo;


public class Result<T> {
    private int status = statusContant_innerError;
    private String msg = "服务器内部错误";
    private T re;




    public Result() {

    }

    public Result(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

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

    public T getRe() {
        return re;
    }

    public void setRe(T re) {
        this.re = re;
    }

  
    public static final int statusContant_innerError = -500;
    public static final int statusContant_UnloginlRequest = -910;
    public static final int statusContant_RateLimit = -920;
    public static final int statusContant_IllegalRequest = -930;
    public static final int statusContant_Starterr = -912;
    public static final int statusContant_RuleValidate = -801;

}