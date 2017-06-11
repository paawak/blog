<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Welcome to bare-bones Spring Rest</title>
</head>
<body>
<h1>Hi, welcome ${user}! This is a test page for a bare bones Spring MVC/Rest POC</h1>
<div>
	<table border="1">
		<thead>
			<tr>
				<th>Key</th>
				<th>Value</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="mapEntry" items="${miscMap}">
			<tr>
				<td>${mapEntry.key}</td>
				<td>${mapEntry.value}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</body>
</html>