<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1"> 
    <meta name="renderer" content="webkit">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <style type="text/css">
        *{padding:0px;margin:0px; font-family: 'microsoft yahei', "黑体";}
        body{background:#4291c9 url("${baseContextPath}static/images/ucarloginbg.jpg") repeat-x left top;}
        .loginform-bd{width: 740px;height:434px;position: absolute;left: 50%;top:186px; margin-left:-370px; background: url("${baseContextPath}static/images/ucarloginfrombg.jpg") no-repeat center bottom;}
        .loginform-bd h1{ text-align: center;color: #1776b8; font-size:36px;line-height:80px;}
        .loginform-bd h2{color: #677983; font-size:28px;line-height:60px;font-weight: normal;padding-left:40px;padding-top: 13px;}
        .loginform-bd ul{overflow: hidden;padding-left:40px; width:460px;}
        .loginform-bd li{list-style: none;float: left; font-size: 18px; color: #657984; width: 50%;}
        .loginform-bd li input{width:195px;height: 34px; line-height:34px; text-indent:1rem; font-size: 14px;color: #333;margin-top: 5px; border-radius: 5px; }
        .btnbox{margin:10px 0 0 370px; }
        .sub{width:99px;height: 36px;line-height:36px;border: none;font-size: 14px; color: #007db5; background: url("${baseContextPath}static/images/ucarloginformbutton.jpg") no-repeat; text-align: left;padding-left: 24px; display: block;}
    </style>
</head>
<body>
<div class="loginform-bd">
	<form action="login.do" id="loginForm" method="post">
		<input type="hidden" id="result" value="${result}" type="text">
	    <h1>欢迎使用MyFlair后台管理系统</h1>
	    <h2>登入</h2>
	    <ul>
	        <li>
	            <div>用户名:</div>
	            <input name="username" id="username" type="text">
	        </li>
	        <li>
	            <div>密码:</div>
	            <input name="password" id="password" type="password">
	        </li>
	    </ul>
	    <div class="btnbox">
	        <input type="button" onClick="validate();" class="sub" value="登录">
	    </div>
    </form>
</div>
<script type="text/javascript" src="${baseContextPath}static/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
function validate(){
	var username = $("#username").val();
	var password = $("#password").val();
	
	if(!username){
		alert("请输入用户名！");
		return false;
	}
	
	if(!password){
		alert("请输入密码！");
		return false;
	}
	
	
	document.getElementById("loginForm").submit();
}
$(function(){
	var result = $("#result").val();
	if(result && result=="failed"){
		alert("用户名或密码输入错误");
	}
	if(self!=top){
		window.top.location.href = location.href;
	}
	
});

</script>
</body>
</html>