<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<!DOCTYPE html> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>头部</title>
<link href="${baseContextPath}static/css/global.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	body{ background:url(${baseContextPath}static/images/head_bg.gif) repeat-x; height:55px;}
</style>
<script type="text/javascript" src="${baseContextPath}static/jquery/jquery-1.4.js"></script>
<script type="text/javascript" src="${baseContextPath}static/jquery/Clock.js"></script>

</head>

<body> 

<form id="form1">
<div class="clearfix">
<div class="fl"><img src="${baseContextPath}static/images/logo1.png" align="top" /></div>
<div class="fr pr30" style="padding-top:25px;"><span id=clock class="f12 pr10 gray_666"></span>|
	<span class="gray_666 pl10 pr10">当前用户：<font class="f14 orange bold" id="nick"></font></span>|
	<span><img src="${baseContextPath}static/images/nav_back.gif" align=absMiddle border=0> <a href="javascript:logout();" class="gray_666">注销</a></span>
</div>
</div>
<script type="text/javascript">
    var clock = new Clock();
    clock.display(document.getElementById("clock"));
    function logout(){
    	var  a = 0;
		$.ajax({
			type : "post",
			url : "${baseContextPath}user/outlogin.do",
			dataType : "text",
			data : "",
			async : false,
			error : function(xhr, status, err) {
				alert(err);
			},
			success : function(data) {
				if(data=="success"){
					window.parent.location.href ='${baseContextPath}user/login.do';
				}else{
					alert("系统错误！");
				}
			}
		});
    }
	function getCookie(name) {
        var cookieArray = document.cookie.split("; ");
        for (var i = 0; i < cookieArray.length; i++) {
            var arr = cookieArray[i].split("=");
            if (arr[0] == name) {
                return decodeURI(arr[1]);//unescape(arr[1]);
            }
        }
    }
	document.getElementById("nick").innerText= getCookie("nick");
</script>
</form>
</body>
</html>
