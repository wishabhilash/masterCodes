<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"><html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<title>untitled</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta name="generator" content="Geany 1.22" />
	<link type="text/css" href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
</head>

<body id="overall-body">
	<div id = "wrapper">
		<div id="head" align="center">
			Library Portal
		</div>
		
		<%
			String authError = "";
			if(null != request.getAttribute("auth_error")){
				authError = (String)request.getAttribute("auth_error");
			}
			
		%>
		<!--	LOGIN FORM	-->
		<div id="login-box">
			<div id="id-form-space" align="center"><%=authError %></div>
			<form action="Login" method="post">
				<table class="auto-margin">
					<tr>
						<td>Username:</td><td><input type="text" name="username" id="" value="" class="text-box"></td>
					</tr>
					<tr>
						<td>Membership:</td>
						<td>
							<select name = "logger">
								<option>student</option>
								<option>admin</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>Password:</td><td><input type="password" name="password" id="" value="" class="text-box"></td>
					</tr>
					<tr colspan=2>
						<td><input type="submit" name="submit" id="id-submit" value="Login"></td>
					</tr>
				</table>
			</form>
		</div>
		
	</div>
</body>

</html>
