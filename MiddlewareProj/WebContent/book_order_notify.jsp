<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="com.db.model.*" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Return Books</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta name="generator" content="Geany 1.22" />
	<link type="text/css" href="${pageContext.request.contextPath}/css/book_order_notify.css" rel="stylesheet">
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
				<a href="add_book.jsp">Add books</a>
				<a href="returned_books.jsp">Return books</a>
				<a href="notify_due_books.jsp">Notify due books</a>
		</div>
		<div id="body">
			<span><%=request.getAttribute("message") %></span>
			
			<table>
				<form action="BookOrdersNotify" method="get">
					<tr>
						<td>
							<select name="order-select">
								<%
									SQLObject sqlObj = (SQLObject)request.getAttribute("orders");
									while(sqlObj.hasNext()){
										HashMap<String, String> map = sqlObj.next();
								%>
									<option><%=map.get("book_name") + ":" + map.get("faculty_id") + ":" + map.get("order_id") %></option>
								<%
									}
								%>
							</select>
						</td>
						<td><input type="submit" value="Notify" name="submit" id="id-submit"></td>
					</tr>
				</form>
			</table>
		</div>
</body>
</html>