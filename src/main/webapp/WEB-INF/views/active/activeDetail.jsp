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
	<h1> <span class="bold">数据信息</span></h1>
</div>
<!--列表信息-->
<form action="updateMemberdetail.do" id="memberForm" method="post">
<div style="width:750px;">
	<div class="bg_gray_ED p10 m10">
		<div class="bg_white disType1">
			<table class="tab_information" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<th><span class="red">*</span>编码：</th>
					<td><input type="text" name="code" id="code" class="ipt_150" value="${active.code}"  <c:if test="${fun:length(user.username) > 0}">readonly</c:if>  /></td>
					<th><span class="red">*</span>标题：</th>
					<td><input type="text" name="title" id="title" class="ipt_150" value="${active.title}" /></td>
				</tr>
				<tr>
					<th><span class="red">*</span> 好友分享图片地址：</th>
					<td><input type="text" name="hyImg" id="hyImg" class="ipt_150" value="${active.hyImg}"/></td>
					<th><span class="red">*</span> 好友分享文案：</th>
					<td><input type="text" name="hyContent" id="hyContent" class="ipt_150" value="${active.hyContent}"/></td>
				</tr>
				<tr>
					<th><span class="red">*</span> 朋友圈分享图片地址：</th>
					<td><input type="text" name="pyqImg" id="pyqImg" class="ipt_150" value="${active.pyqImg}"/></td>
					<th><span class="red">*</span> 朋友圈分享文案：</th>
					<td><input type="text" name="pyqContent" id="pyqContent" class="ipt_150" value="${active.pyqContent}"/></td>
				</tr>
				<tr>
					<th><span class="red">*</span> 分享地址：</th>
					<td><input type="text" name="shareUrl" id="shareUrl" class="ipt_150" value="${active.shareUrl}"/></td>
				</tr>
				<tr>
					<th><span class="red">*</span> 描述：</th>
					<td>
						<textarea name="remark" id="remark" cols="30" rows="10"></textarea>
				</tr>
			</table>
		</div>
	</div>
	<input type="hidden" name="action" id="action" value="${action }" />
    <input type="hidden" id="id" name="id" value="${active.id}">
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
       saveActive();
    });
 });
function getURLParameter(name) {
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}
 function saveActive(){
	if(!validate()){
		return;
	}
	 var active = {};
     active.title = $("#title").val();
     active.code = $("#code").val();
     active.hyImg = $("#hyImg").val();
     active.hyContent = $("#hyContent").val();
     active.pyqImg = $("#pyqImg").val();
     active.pyqContent = $("#pyqContent").val();
     active.shareUrl = $("#shareUrl").val();
     active.remark = $("#remark").val();
	 
	 if($("#id").val()){
         active.id=$("#id").val();
	 }
	var action = getURLParameter("action");//$("#action").val();
	var data = {req:JSON.stringify(active),action:action};
	$.ajax({
	    type: "POST",
	    url: "${baseContextPath}activeConfig/saveActive.do",
	    data: data,
	    dataType: "json",
	    success:function(resp){
	       if(resp.status==0){
	    	   alert(resp.msg);
	    	   window.opener.location.href = "activelist.do";//window.opener.location.href;
               window.close();
	       }else{
	    	   alert(resp.msg);
	       }
	    }
	});
}
 
function validate(){
	return true;
}
</script>
</body>
</html>
