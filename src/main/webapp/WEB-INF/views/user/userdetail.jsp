<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<!DOCTYPE html> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户信息</title>
<link href="${baseContextPath}static/css/global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${baseContextPath}static/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${baseContextPath}static/jquery/intranetCss_js.js"></script>
</head>

<body style="overflow-x:hidden;">
<div class="brumbtext gray_666">
	<h1> <span class="bold">用户信息</span></h1>
</div>
<!--列表信息-->
<form action="updateMemberdetail.do" id="memberForm" method="post">
<div style="width:700px;">
	<div class="bg_gray_ED p10 m10">
		<div class="bg_white disType1">
			<table class="tab_information" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<th><span class="red">*</span>用户名：</th>
					<td><input type="text" name="username" id="username" class="ipt_150" value="${user.username}"  <c:if test="${fun:length(user.username) > 0}">readonly</c:if>  /></td>
					<th><span class="red">*</span>姓名：</th>
					<td><input type="text" name="nick" id="nick" class="ipt_150" value="${user.nick}" /></td>
				</tr>
				<tr>
					<th><span class="red">*</span> 手机号：</th>
					<td><input type="text" name="mobile" id="mobile" class="ipt_150" value="${user.mobile}"/></td>
					<th><span class="red">*</span> 邮箱：</th>
					<td><input type="text" name="email" id="email" class="ipt_150" value="${user.email}"/></td>
				</tr>
				<tr>
					<th><span class="red">*</span> 状态：</th>
					<td>
					 <select name="status" id="status" class="ipt_150" >
						  <option value ="1" <c:if test="${user.status == 1}" >selected</c:if> >正常</option>
						  <option value ="2" <c:if test="${user.status == 2}" >selected</c:if> >不可用</option>
					   </select>
					</td>
					<th><span class="red">*</span> 角色：</th>
					<td>
					 <select name="roleId" id="roleId" class="ipt_150" >
						  <option value ="2" <c:if test="${user.roleId == 2}" >selected</c:if> >普通管理员</option>
						  <option value ="0" <c:if test="${user.roleId == 0}" >selected</c:if> >超级管理员</option>
					   </select>
					</td>
				</tr> 
				<tr>
					<th><span class="red">*</span> 密码：</th>
					<td><input type="password" name="password" id="password" class="ipt_150" value="${user.password}"/></td>
				</tr>
			</table>
		</div>
	</div>
	<input type="hidden" name="action" id="action" value="${action }" />
    <input type="hidden" id="id" name="id" value="${user.id}">
	<div class="ac p10 m10_0" >
	    <input name="input" type="button" id="saveBtn" class="orange_btn_100"  value="保存并提交" />
		<input name="input" type="button"  id="cancelBtn" class="orange_btn_100" onClick="javascript: window.close();" value="取 消" />
	</div>
</div>

</form>
<script src="${baseContextPath}static/js/business/mobile_validate.js"></script>
<script type="text/javascript">
 $(function(){ 
    $("#saveBtn").click(function(){
       saveUser();
    });
 });
function getURLParameter(name) {
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}
 function saveUser(){
	if(!validate()){
		return;
	}
	 var user = {}; 
	 user.username = $("#username").val();
     user.nick = $("#nick").val();
	 user.mobile = $("#mobile").val();
	 user.email = $("#email").val();
	 user.status = $("#status").val();
	 user.roleId = $("#roleId").val();
	 user.password = $("#password").val();
	 
	 if($("#id").val()){
	    user.id=$("#id").val();
	 }
	var action = getURLParameter("action");//$("#action").val();
	var data = {req:JSON.stringify(user),action:action};
	$.ajax({
	    type: "POST",
	    url: "${baseContextPath}user/saveuser.do", 
	    data: data,
	    dataType: "json",
	    success:function(resp){
	       if(resp.status==0){
	    	   alert(resp.msg);
	    	   window.opener.location.href = "userlist.do";//window.opener.location.href;
               window.close();
	       }else{
	    	   alert(resp.msg);
	       }
	    }
	});
}
 
function validate(){
	var username = $("#username").val();
	var mobile = $("#mobile").val();
	var email = $("#email").val();
	var password = $("#password").val();
	
	if(!username || username.length>20){
		alert("用户名有误！");
		return false;
	}
	
	if( null === mobile || "" === mobile ||!mobileValidate(mobile)){
		alert("手机号输入有误！");
		return false;
	}
	
	if(!IsEmail(email)){
		alert("邮箱输入有误！");
		return false;
	}
	if(!password){
		alert("密码不能为空！");
		return false;
	}
	return true;
}
</script>
</body>
</html>
