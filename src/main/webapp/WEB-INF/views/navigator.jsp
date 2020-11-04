<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>스프링 프레임워크를 통한 DB 연동</title>
</head>
<body>
	<h1>${sessionScope.loginId}님의페이지</h1>
	<form action="logout" method="get">
		<input type="submit" value="logout">
	</form>
	
	<h2>스프링 프레임워크를 통한 DB 연동</h2>
	<a href="elasticPage">Elasticsearch</a>
	<br />
	<a href="mongoPage">MongoDB</a>
	<br />
	<a href="postgrePage">PostgreSQL</a>
	<br />
	<a href="reportPage">보고서 파일 생성</a>
	<br />
</body>
</html>