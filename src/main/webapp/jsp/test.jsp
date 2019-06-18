<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<!DOCTYPE html> en">
	<head>
		<title>我的登陆测试</title>
	</head>
	<body>
		<form action="/login.do" method="post" onsubmit="return validate();">
		<table>
			<tr>
				<td>用户名</td>
				<td><input name="username" type="text" id="username" onblur="validateU();"></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input name="password" type="password" id="password" onblur="validateP();"></td>
			</tr>
			<tr><td colspan="2"><button type="submit" value="登陆">登陆</button></td></tr>
		</table>
		</form>
		<script type="text/javascript">
			function validateU(){
				var username = document.getElementById("username").value;
				if(!username){
					alert("用户名不能为空");
				}
			}
			function validateP(){
				var password = document.getElementById("password").value;
				if(!password){
					alert("密码不能为空");
				}
			}
			function validate(){
				var username = document.getElementById("username").value;
				if(!username){
					alert("用户名不能为空");
					return false;
				}
				var password = document.getElementById("password").value;
				if(!password){
					alert("密码不能为空");
					return false;
				}
				return true;
			}
		</script>
	</body>
</html>