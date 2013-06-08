<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>untitled</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta name="generator" content="Geany 1.22" />
	<link type="text/css" href="${pageContext.request.contextPath}/css/add_book.css" rel="stylesheet">
</head>

<body id="overall-body">
	<div id = "wrapper">
		<div id="head">
			Library Portal
		</div>
		<div align="right">
			<span id="id-stud-portal">Admin Dash!!!</span>
		</div>
		<div>
			<a href="Logout">Logout!!!</a>
		</div>
		<div id="id-link-band">
			<a href="notify_due_books.jsp">Notify due books</a>
			<a href="returned_books.jsp">Return books</a>
			<a href="BookOrdersNotify">Notify ordered books</a>
		</div>
		
		<div id="body">
			<div class="space30"></div>
			<div><%=request.getAttribute("message") %></div>
			<form action="AddBooks" method="get" class = "auto-margin">
				<table>
					<tr>
						<td>Book Name:</td><td><input type="text" name="book_name" class="text-box"></td>
					</tr>
					<tr>
						<td>Version:</td><td><input type="text" name="version" class="text-box"></td>
					</tr>
					<tr>
						<td>Publisher:</td><td><input type="text" name="publisher" class="text-box"></td>
					</tr>
					<tr>
						<td>Price:</td><td><input type="text" name="price" class="text-box"></td>
					</tr>
					<tr>
						<td>Copies:</td><td><input type="text" name="copies" class="text-box"></td>
					</tr>
					<tr colspan="2">
						<td><input type = "submit" value = "Update Database" name = "submit" id="id-submit"></td>
					</tr>
				</table>
				
				
			</form>
			
		</div>
	</div>
</body>

</html>