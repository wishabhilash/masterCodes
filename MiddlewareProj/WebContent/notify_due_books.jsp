<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>untitled</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta name="generator" content="Geany 1.22" />
	<link type="text/css" href="${pageContext.request.contextPath}/css/notify_due_books.css" rel="stylesheet">
</head>

<body id="overall-body">
	<div id = "wrapper">
		<div id="head">
			Library Portal
		</div>
		<div>
			<a href="Logout">Logout!!!</a>
		</div>
		<div align="right">
			<span id="id-stud-portal">Admin Dash!!!</span>
		</div>
		<div id="id-link-band">
			<a href="add_book.jsp">Add books</a>
			<a href="returned_books.jsp">Return books</a>
			<a href="BookOrdersNotify">Notify ordered books</a>
		</div>
		
		<div id="body">
			<div class="space30"></div>
			<%=request.getAttribute("message") %>
			<form action="NotifyDueBooks" method="get" class = "auto-margin">
				<input type = "submit" value = "Click to notify" name = "buttonNotify" id="id-submit">
			</form>
			
		</div>
	</div>
</body>

</html>