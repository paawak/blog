<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Add Author</title>
</head>
<body>
	<h1 align="center">Add Author</h1>
	<form action="rest/author-genre" method="post" enctype="application/x-www-form-urlencoded"> 
		<div>
			<div align="left">
				Run in transaction mode: <input type="checkbox" name="transactional" value="true">
			</div>		
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
				Genre Short Name: <input name="genreShortName" type="text" />
			</div>
			<div align="left">
				Genre Name: <input name="genreName" type="text" />
			</div>
			<div align="center">
				<input id="submit" type="submit" value="Save Author"/>
			</div>
		</div>
	</form>
</body>
</html>