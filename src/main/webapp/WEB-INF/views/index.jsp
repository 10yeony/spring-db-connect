<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>

	<h1>�α���</h1>

	<form action="loginUser" method="post">
		<font color="red">${message}</font> ID:<input type="text"
			name="userid"><br> PW:<input type="password" name="pwd"><br>
		<input type="submit" value="login">
	</form>

	<h2>ȸ������</h2>
	<a href="insert">ȸ������</a>
	<br />

</body>
</html>