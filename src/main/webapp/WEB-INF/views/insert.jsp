<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<html>
<head>
	<title>insert</title>
</head>
<body>
	<h1>회원가입</h1>

	<form action="insertUser" method="post">
		ID:<input type="text" name="userid"><br>
		<font color="red">${message}</font><br>
		PW:<input type="password" name="pwd"><br>
		<input type="submit" value="SIGNUP">
	</form>


</body>
</html>