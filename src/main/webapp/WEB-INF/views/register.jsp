<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
</head>

<body>
	<!-- POST 방식 403 에러를 막기 위해 csrf 토큰 처리 -->
	<%@ include file="/WEB-INF/views/login/csrf-token.jsp"%>
	
	<div class="text-center">
		<h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
	</div>
	<form method="post" id="registerFrm" name="register">
		<div class="form-group">
			<span>
				<b style="font-size:14px">아이디 </b> 
				<span style="font-size:12px; color:red;">(필수)</span>
			</span><br/>
			<input style="display:inline-block; width:63%;" type="text" class="form-control" name="member_id"
				id="register_member_id" placeholder="4자 이상 10자 이하" required> 
			<input style="display:inline-block; width:35%;" type="button" class="form-control" id="idCheck" value="중복체크">
			<div style="margin-top:2px;" id="isExistId"></div>
		</div>
		
		<div class="form-group">
			<span>
				<b style="font-size:14px">비밀번호 </b> 
				<span style="font-size:12px; color:red;">(필수)</span>
			</span><br/>
			<input type="password" class="form-control" name="pwd" 
				id="register_pwd" placeholder="영어/숫자/특수문자 포함 8자 이상 12자 이하" required>
		</div>
		
		<div class="form-group">
			<span>
				<b style="font-size:14px">비밀번호 확인 </b> 
				<span style="font-size:12px; color:red;">(필수)</span>
			</span><br/>
			<input type="password" class="form-control" name="pwdCheck" id="register_pwd_check">
			<div style="margin-top:2px;" id="isSamePwd" required></div>
		</div>
		
		<div class="form-group">
			<span>
				<b style="font-size:14px">이메일 </b> 
				<span style="font-size:12px; color:red;">(필수)</span>
			</span><br/>
			<input type="email" class="form-control" name="email" 
				id="register_email" placeholder="예) sdtm@namutech.co.kr" required>
		</div>
		
		<div class="form-group">
			<span>
				<b style="font-size:14px">연락처 </b> 
				<span style="font-size:12px">(문자 발송 서비스에 활용됩니다.)</span>
			</span><br/>
			<input type="text" class="form-control" name="phone" 
				id="register_phone" placeholder="예) 010-1234-5678">
		</div>
		
		<div class="form-group">
			<div class="custom-control custom-checkbox small">
				<input type="checkbox" class="custom-control-input" id="customAgree"> 
				<label class="custom-control-label" for="customAgree">개인 정보 활용에 동의합니다.</label>
			</div>
		</div>
		
		<div class="form-group">
			<input type="button" id="register_submit" value="회원가입"
				class="form-control form-control-user">
		</div>
	</form>
</body>
</html>
