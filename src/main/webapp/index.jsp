<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>SDTM Login</title>
</head>
<body>
	<!-- 로그인한 경우 -->
	<sec:authorize access="isAuthenticated()">
		<%@ include file="/WEB-INF/views/main.jsp"%>
	</sec:authorize>

	<!-- 로그인하지 않은 경우 -->
	<sec:authorize access="isAnonymous()">
		<%@ include file="/WEB-INF/views/login.jsp"%>
	</sec:authorize>
</body>
</html>