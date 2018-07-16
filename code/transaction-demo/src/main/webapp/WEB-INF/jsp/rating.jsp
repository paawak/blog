<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Add Rating</title>
</head>
<body>
	<h1 align="center">Add Rating</h1>
	<form action="rest/author-rating" method="post" enctype="application/x-www-form-urlencoded"> 
		<div>
			<div align="left">
				Author Id: <input name="authorId" type="text" />
			</div>
			<div align="left">
				Author First Name: <input name="authorFirstName" type="text" />
			</div>
			<div align="left">
				Author Last Name: <input name="authorLastName" type="text" />
			</div>
			<div align="left">
				User Name: <input name="userName" type="text" />
			</div>
			<div align="left">
				Rating: <input name="rating" type="text" />
			</div>
			<div align="center">
				<input id="submit" type="submit" value="Submit Rating"/>
			</div>
		</div>
	</form>
</body>
</html>