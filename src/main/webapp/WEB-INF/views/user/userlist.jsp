<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<!DOCTYPE html> 
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<link href="${baseContextPath}static/css/global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${baseContextPath}static/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${baseContextPath}static/jquery/intranetCss_js.js"></script>
</head>
<body>
<div>
	<div class="brumbtext gray_666">
		<h1>  用户管理> <span class="bold">用户列表</span></h1>
	</div>
	<div class="p10">
	            <input name="input" type="button" class="blue_btn_bg" value="新增" onclick="openAddUserWindow()"/>
	</div>
	<div class="p0_10" >
	<form action="userlist.do" id="userform" name="userform"  onsubmit="return query();" method="post" style="display:none">
	<table class="tab_inquiry m10_0" cellpadding="0" cellspacing="0">
		<tr>
			<th>联系人姓名：</th>
			<td><input type="text" name="name" id="name" class="ipt_150" value="${user.nick}"/></td>
			<th>联系人手机号：</th>
			<td><input type="text" name="mobile" id="mobile" class="ipt_150" value="${user.mobile}"/></td>
			<th>邮箱：</th>
			<td><input type="text" name="email" id="email" class="ipt_150" value="${user.email}"/></td>
		</tr>
		<tr>
			<th>状&nbsp;&nbsp;&nbsp;&nbsp;态：</th>
			<td>
				<select name="state" class="u181" id="state"  >
					<option selected="selected" value="">请选择</option>
					<option value="1" <c:if test="${user.status == 1}">selected="selected"</c:if>>正常</option>
					<option value="2" <c:if test="${user.status == 2}">selected="selected"</c:if>>不可用</option>
				</select>
			</td>
		</tr>
		<tfoot>
			<tr>
				<td colspan="8" class="ac p10_0"><input name="input2" type="submit" value="查　询" class="orange_btn_100"/></td>
				</tr>
		</tfoot>
	</table>
	<input type="hidden" name="page" id="page" value="${dqPage}">
	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage}">
	</form>
		<!--列表信息-->
		<table class="tab_list tr_even ml" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<th width="60">选择</th>
				<th>用户名</th>
				<th>昵称</th>
				<th>手机号</th>
				<th>邮箱</th>
				<th>状态</th>
				<th>角色</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
			<tbody>
			<c:if test="${fun:length(list) > 0}">
				<c:forEach items="${list}" var="user">
					<tr>
					<td ><input type="radio" name="bmvid" id="bmvid${user.id}" value="${user.id}" class="checked_radio"/></td>
					<td>${user.username }</td>
					<td>${user.nick }</td>
					<td>${user.mobile }</td>
					<td>${user.email }</td>
					<td><c:if test="${user.status == 1}">正常</c:if><c:if test="${user.status == 2}">不可用</c:if></td>
					<td><c:if test="${user.roleId == 2}">普通管理员</c:if><c:if test="${user.roleId == 0}">超级管理员</c:if></td>
					<td>${user.createTime }</td>
					<td>${user.updateTime }</td>
					<td><a href="#" itemid="${user.id}" class="edit">修改</a> <a href="#" itemid="${user.id}" id="delete" class="delete">删除</a></td>
				</tr>
				</c:forEach>
			</c:if>
			</tbody>
		</table>
	</div>
	<!-- 分页内容 -->
	<div class="height20"></div>
	<div class="page-nav">
		<ol>
			<li style="width:auto">每页 ${pageSize}条，共 ${count } 条</li>
			<li class="first"><a href="javascript:goPage(${dqPage },'','')">上一页</a></li>
			<c:if test="${totalPage > 0 }">
					<c:forEach  var="i" begin="1" end="${totalPage }">
					<c:if test="${totalPage <= 5 }">
						<li><a  <c:if test="${dqPage==i}"> class="cur"</c:if> href="javascript:goPage('',${i},'')">${i}</a></li>
					</c:if>
					<c:if test="${totalPage > 5 }">
						<c:if test="${dqPage < 5 }" >
							<li  <c:if test="${i > 5  }"> style="display:none;"</c:if>>
							<a  <c:if test="${dqPage==i }"> class="cur"</c:if> href="javascript:goPage('',${i},'')">${i}</a></li>
						</c:if>
						<c:if test="${dqPage >= 5 }" >
							<c:if test="${dqPage+2>totalPage }">
								<li <c:if test="${i < totalPage-4 }"> style="display:none;"</c:if>>
							</c:if>
							<c:if test="${dqPage+2<=totalPage }">
								<li <c:if test="${dqPage<=i+2 and dqPage>=i-2 }"> style="display:block;"</c:if> 
								<c:if test="${(dqPage>i+2 or dqPage<i-2)  }"> style="display:none;"</c:if>>
							</c:if>
							<a  <c:if test="${dqPage==i }"> class="cur"</c:if> href="javascript:goPage('',${i},'')">${i}</a></li>
						</c:if>
					</c:if>
				</c:forEach>
				<%-- <c:if test="${totalPage > 5 }">
					<li>...</li>
				</c:if> --%>
			</c:if>
			<li class="last"><a href="javascript:goPage('','',${dqPage })">下一页</a></li>
			<li style="width:auto">共${totalPage}页</li>
			<li style="width:auto">到第
				<input type="text" style="width:20px;" id="inputpage" name="inputpage" />
				页
				<input class="serbacktlsub" style="margin-top:0" type="button" value="确定" onclick="inputpage();"/>
			</li>
		</ol>
	</div>
	<div class="height20"></div>
	<!-- 分页内容 结束 -->
</div>
<script type="text/javascript">
$(document).ready(function(){
	$(".edit").click(function(){
		var id = $(this).attr("itemid");
		//console.log("id:"+id);
		window.open ('userdetail.do?action=modify&id='+id, 'newwindow', 'height=500, width=700, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	});
	
	$(".delete").on("click",function(){
		if(confirm("您确定要删除该条记录？")){
			var id = $(this).attr("itemid");
			$.ajax({
			    type: "POST",
			    url: "${baseContextPath}user/delete.do", 
			    data: {id:id},
			    dataType: "json",
			    success:function(resp){
			       if(resp.status==0){
			    	   alert(resp.msg);
			    	   location.href = "userlist.do";//window.opener.location.href;
		               window.close();
			       }else{
			    	   alert(resp.msg);
			       }
			       
			    }
			});
		}
		
	});
	
	$("#addBtn").click(function(){
		openAddUserWindow();
	});
});


function openAddUserWindow(){
	window.open ('userdetail.do?action=create', 'newwindow', 'height=500, width=700, top=100, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');

}
function inputpage(){
	var inputpage = $("#inputpage").val();
	var intPage = parseInt(inputpage);
	var totalPage = parseInt($("#totalPage").val());
	if(intPage){
		if(intPage<1 || intPage>totalPage){
			alert("错误的页码，请重新输入");
			$("#inputpage").val("");
		}else{
			$("#page").val(inputpage);
			$("#userform").submit();
		}
	}else{
		alert("错误的页码，请重新输入");
		$("#inputpage").val("");
	}
}
function goPage(qpage,page,hpage){
		var p="";
		if(qpage!=""){
			if(qpage>1){
				p = eval(qpage-1);
			}else{
				p=qpage;
			}
		}else if(hpage!=""){
			if(hpage=='${totalPage}'){
				p=hpage;
			}else{
				p = eval(hpage+1);
			}
		}else if(page){
			p = page;
		}
		
		$("#page").val(p);
		$("#userform").submit();
}
function query(){

	return true;
}


function viewuser(userid){
	window.open ('userdetail.do?type=view&id='+userid, 'newwindow', 'height=500, width=700, top=100, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
}
function inputpage(){
	var inputpage = $("#inputpage").val();
	var intPage = parseInt(inputpage);
	var totalPage = parseInt($("#totalPage").val());
	if(intPage){
		if(intPage<1 || intPage>totalPage){
			alert("错误的页码，请重新输入");
			$("#inputpage").val("");
		}else{
			$("#page").val(inputpage);
			$("#userform").submit();
		}
	}else{
		alert("错误的页码，请重新输入");
		$("#inputpage").val("");
	}
}
</script>
</body>
</html>
