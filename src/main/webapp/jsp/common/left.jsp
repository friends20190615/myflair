<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<!DOCTYPE html> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MyFlair后台管理系统</title>
<script type="text/javascript" src="${baseContextPath}static/jquery/jquery-1.4.js"></script>
<link href="${baseContextPath}static/css/global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"> 
$(function(){
	$('div.list_box dt').click(function(){
		$('div.list_box dt').removeClass("selected");
		$(this).addClass('selected');
		$(this).nextAll().slideDown().end().closest('dl').siblings().children("dd").slideUp();
	});
	$('div.list_box dd a').click(function(){
		$(this).nextAll().slideDown().end().closest('dd').siblings().children("p").slideUp();
		$('div.list_box dd a').removeClass("bold");
		$(this).addClass('bold');
	});
	$('div.list_box dd p').click(function(){
		$('div.list_box dd p').removeClass("bold");
		$(this).addClass('bold');
	});
})
</script>
<style type="text/css">
body{background:url(${baseContextPath}static/images/left_right_bg.gif) right repeat-y #417eb7;}
.list_box{padding-right:12px;}
.list_box a:hover{text-decoration:none;}
.list_box dl dt{line-height:36px; padding-left:20px; font-size:14px; color:#FFF; font-weight:bold; cursor:pointer;}
.list_box dl dt a{color:#FFF;}
.list_box dl dt.default{background:url(${baseContextPath}static/images/left_dt_moren.gif) repeat-x;}
.list_box dl dt.selected{background:url(${baseContextPath}static/images/left_dt_selected.gif) repeat-x; color:#000; border-left:1px solid #0e64b5;}
.list_box dl dt.selected a{color:#000;}
.list_box dl dd{line-height:25px;display:none; color:#b9ddff; padding-left:15px; cursor:pointer;}
.list_box dl dd a{background:url(${baseContextPath}static/images/left_dd_image.gif) no-repeat;padding-left:25px;color:#b9ddff; }
.list_box dl dd p{display:none; color:#FC0; line-height:20px; padding-left:15px; cursor:pointer;}
.list_box dl dd p a{color:#FC0;}
.list_box dl dd p ul li{color:#FC0; line-height:20px; padding-left:20px; cursor:pointer;}
.list_box dl dd p ul li a{color:#FC0;}
.blue_bg{background-color:#417eb7;}
.bold{font-weight:bold;}
</style>
</head>

<body>
<div class="list_box">
<c:if test="${cookie.rid.value eq 0 }">
<dl>
	<dt class="default">用户管理</dt>
	<dd>
		<a href="${baseContextPath}user/userlist.do" target="mainFrame">用户列表</a>
	</dd>
</dl> 
</c:if>
<dl>
	<dt class="default">拉新管理</dt>
	<dd>
		<a href="${baseContextPath}lachine/lachinelist.do" target="mainFrame">拉新列表</a>
	</dd>
</dl>
<dl>
	<dt class="default">分享配置管理</dt>
	<dd>
		<a href="${baseContextPath}activeConfig/activelist.do" target="mainFrame">分享配置列表</a>
	</dd>
</dl>
<%--<dl>
	<dt class="default">优惠券管理</dt>
	<dd>
		<a href="${baseContextPath}user/userlist.do" target="mainFrame">优惠券列表</a>
	</dd>
</dl>--%>
</div>
</body>
</html>
