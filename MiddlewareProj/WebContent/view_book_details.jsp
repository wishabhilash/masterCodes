<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*, java.io.*" %>
<%@ page language="java" import="com.db.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<td>Name of the book</td>
			<td>Publisher</td>
			<td>Version</td>
			<td>Price</td>
		</tr>
		<%
			SQLObject sqlObj = (SQLObject) request.getAttribute("resultSet");
			while(sqlObj.hasNext()){
				HashMap<String, String> map = sqlObj.next();
				String name = map.get("book_name");
				String publisher = map.get("publisher");
				String version = map.get("version");
				String price = map.get("price");
		%>
			<tr>
				<td><%=name %></td>
				<td><%=publisher %></td>
				<td><%=version %></td>
				<td><%=price %></td>
			</tr>
		<% } %>
	</table>
</body>
</html>