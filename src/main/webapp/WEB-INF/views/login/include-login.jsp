<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Register</title>

<!-- Custom fonts for this template-->
<link
	href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link
	href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>
<script 
	src="${pageContext.request.contextPath}/resource/js/member/login.js"></script>
</head>

<body>
	<!-- POST 방식 403 에러를 막기 위해 csrf 토큰 처리 -->
	<%@ include file="/WEB-INF/views/include/csrf-token.jsp"%>
	
	<!-- 로그인 에러 메세지 및 로그인한 회원 정보 -->
	<input type="hidden" id="member_login" value="${member}">
	
	<div class="text-center">
		<h1 class="h4 text-gray-900 mb-4">Welcome To SDTM</h1>
	</div>
	<form id="loginFrm" method="post">
		<!-- 폼 제출시 POST 방식 403 에러를 막기 위해 csrf 토큰 처리 -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<div class="form-group">
			<input type="text" class="form-control form-control-user"
				id="login_member_id" name="member_id" placeholder="아이디">
		</div>
		
		<div class="form-group">
			<input type="password" class="form-control form-control-user"
				id="login_pwd" name="pwd" placeholder="비밀번호">
			<div style="margin-top:2px;" id="isExistId"></div>
			<a id="" class="small" href="javascript:void(0);" style="display:none">
				비밀번호를 잊어버리셨습니까?
			</a>
		</div>

		<div class="form-group">
			<div class="custom-control custom-checkbox small">
				<input type="checkbox" class="custom-control-input" name="remember-me" id="customCheck"> 
				<label class="custom-control-label" for="customCheck">자동 로그인</label>
			</div>
		</div>
		
		<div class="form-group">
			<input id="loginBtn" type="button" value="로그인" class="form-control form-control-user">
		</div>
	</form>
</body>
</html>
