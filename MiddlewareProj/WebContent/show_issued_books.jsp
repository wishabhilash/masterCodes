<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="com.db.model.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Return Books</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta name="generator" content="Geany 1.22" />
	<link type="text/css" href="${pageContext.request.contextPath}/css/show_issued_books.css" rel="stylesheet">
</head>

<body id="overall-body">
	<div id = "wrapper">
		<div id="head">
			Library Portal
		</div>
		<div align="right">
			<span id="id-stud-portal">Student Dash!!!</span>
		</div>
		<div>
			<a href="Logout">Logout!!!</a>
		</div>
		<div id="id-link-band">
			<a href="IssuesAvailable">Books Available</a>
		</div>
		
		<div id="body">
			<div class="space30"></div>
			<div>Issues Available</div>
			<table>
				<tr>
					<td>Book Name</td>
					<td>Version</td>
					<td>Publisher</td>
					<td>Copies</td>
					<td>Issue Date</td>
					<td>Due Date</td>
				</tr>
				
				<%
					ResultSet rs = (ResultSet)request.getAttribute("issuedBooks");
					while(rs.next()){
						
				%>
				<tr>
					<td><%=rs.getString("book_name") %></td>
					<td><%=rs.getString("version") %></td>
					<td><%=rs.getString("publisher") %></td>
					<td><%=rs.getString("copies") %></td>
					<td><%=rs.getString("issue_date") %></td>
					<td><%=rs.getString("due_date") %></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>
</body>
</html>