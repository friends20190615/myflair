<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<!DOCTYPE html> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MyFlair后台管理系统</title>
</head>
<frameset rows="55,*" cols="*" frameborder="no" border="0" framespacing="0">
	<frame src="${baseContextPath}jsp/common/head.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
	<frameset cols="185,*" frameborder="no" border="0" framespacing="0">
		<frame src="${baseContextPath}jsp/common/left.jsp" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame" />
		<frame src="${baseContextPath}jsp/common/main.jsp" name="mainFrame" id="mainFrame" title="mainFrame" />
	</frameset>
</frameset>
<noframes><body>
</body></noframes>
</html>
