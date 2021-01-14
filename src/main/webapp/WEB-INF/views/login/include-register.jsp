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
	
<link
	href="${pageContext.request.contextPath}/resource/css/upload-img.css"
	rel="stylesheet" type="text/css">
<script
	src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>
<script 
	src="${pageContext.request.contextPath}/resource/js/member/register.js"></script>

<!-- w3 css -->
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

<style>
	.loading {
		position: absolute;
		top: 50%;
		left: 50%;
		width: 80px;
		height: 80px;
		margin: -50px 0 0 -50px;
	}
</style>
</head>

<body>
	<!-- POST 방식 403 에러를 막기 위해 csrf 토큰 처리 -->
	<%@ include file="/WEB-INF/views/include/csrf-token.jsp"%>

	<!-- 로딩 화면 -->
	<div id="loadingArea" class="w3-modal w3-animate-opacity">
		<img class="loading" width="100px"
			 src="${pageContext.request.contextPath}/resource/img/loading.gif">
	</div>
	
	<div class="text-center">
		<h1 class="h5 text-gray-900 mb-4">사용자 등록</h1>
	</div>
	<form method="post" id="registerFrm" name="register">
		<div class="form-group uploadTotal">
			<div class="uploadArea">
				<input type="file" id="uploadImgFile" class="uploadImgFile" name="uploadImgFile" 
					onchange="handleImgFile('uploadImgFile', 'uploadImgPreview', 'uploadResetBtn', event)"> 
				<img src="${pageContext.request.contextPath}/resource/img/user.png"
	          		id="uploadImgPreview" class="uploadImgPreview" 
	          		onclick='document.getElementById("uploadImgFile").click()'>
	          	<button type="button" id="uploadResetBtn" class="uploadResetBtn" style="display: none;"
	          		onclick="resetUploadImg('uploadImgFile', 'uploadImgPreview', 'uploadResetBtn')">삭제</button>
			</div>
			<div style="font-size:12px;">이미지를 클릭하면 프로필 사진을 올릴 수 있습니다</div>
		</div>
	
		<div class="form-group">
			<span>
				<b style="font-size:14px">이름 </b> 
				<span style="font-size:12px; color:red;">(필수)</span>
			</span><br/>
			<input type="text" class="form-control" name="name"
				id="register_name" placeholder="이름을 입력하세요." required> 
		</div>
	
		<div class="form-group">
			<span>
				<b style="font-size:14px">아이디 </b> 
				<span style="font-size:12px; color:red;">(필수)</span>
			</span><br/>
			<input style="display:inline-block; width:63%;" type="text" class="form-control" name="member_id"
				id="register_member_id" placeholder="4자 이상 10자 이하" minlength="4" maxlength="10" required> 
			<input style="display:inline-block; width:35%;" type="button" class="form-control" id="idCheck" value="중복체크">
			<div id="id_input_alert">
				<span class="small">
					<b style='color: #4e73df;'>아이디를 입력하세요.</b>
				</span>
			</div>
			<div style='display:none' id="id_able_alert">
				<span class="small">
					<b style="color: #28a745;">사용가능</b>
				</span>
			</div>
			<div style='display:none' id="id_disable_alert">
				<span class="small">
					<b style="color: #dc3545;">사용불가능한 아이디입니다.</b>
				</span>
			</div>
			<div style='display:none' id="id_check_alert">
				<span class="small">
					<b style="color: #4e73df;">아이디 중복체크가 필요합니다.</b>
				</span>
			</div>
		</div>
		
		<div class="form-group">
			<span>
				<b style="font-size:14px">비밀번호 </b> 
				<span style="font-size:12px; color:red;">(필수)</span>
			</span><br/>
			<input type="password" class="form-control" name="pwd" 
				id="register_pwd" placeholder="영어/숫자/특수문자 포함 8자 이상 12자 이하"
				minlength="8" maxlength="12" required>
		</div>
		
		<div class="form-group">
			<span>
				<b style="font-size:14px">비밀번호 확인 </b> 
				<span style="font-size:12px; color:red;">(필수)</span>
			</span><br/>
			<input type="password" class="form-control" name="pwdCheck" 
				id="register_pwd_check" placeholder="비밀번호를 한번 더 입력하세요." 
				minlength="8" maxlength="12" required>
			<div style='display:block;' id="pwd_input_alert">
				<span class="small">
					<b style="color: #4e73df;">비밀번호를 입력하세요.</b>
				</span>
			</div>
			<div style='display:none;' id="pwd_same_alert">
				<span class="small"> 
					<b style="color: #28a745;">비밀번호 일치</b>
				</span>
			</div>
			<div style='display:none;' id="pwd_different_alert">
				<span class="small">
					<b style="color: #dc3545;">비밀번호가 일치하지 않습니다.</b>
				</span>
			</div>
			<div style='display:none;' id="pwd_disable_alert">
				<span class="small">
					<b style="color: #dc3545;">비밀번호 형식이 잘못되었습니다.</b>
				</span>
			</div>
		</div>
		
		<div class="form-group">
			<span>
				<b style="font-size:14px">이메일 </b> 
				<span style="font-size:12px; color:red;">(필수)</span>
			</span><br/>
			<input type="email" class="form-control" name="email" 
				id="register_email" placeholder="예) sdtm@namutech.co.kr" required>
			<div style='display:inline-block; float:left;' id="email_input_alert">
				<span class="small">
					<b style="color: #4e73df;">이메일을 입력하세요.</b>
				</span>
			</div>
			<div style='display:none; float:left;' id="email_able_alert">
				<span class="small"> 
					<b style="color: #28a745;">사용가능</b>
				</span>
			</div>
			<div style='display:none; float:left;' id="email_disable_alert">
				<span class="small">
					<b style="color: #dc3545;">사용불가능한 이메일입니다.</b>
				</span>
			</div>
			<div style='display:none; float:left;' id="email_check_alert">
				<span class="small">
					<b style="color: #4e73df;">이메일 중복체크가 필요합니다.</b>
				</span>
			</div>
			<input style="display:inline-block; width:35%; float:right; margin-top:4px;" 
				type="button" class="form-control" id="emailCheck" value="중복체크"><br/>
		</div>
		
		<div class="form-group">
			<span>
				<b style="font-size:14px">연락처 </b> 
				<span style="font-size:12px">(SMS 서비스에 활용됩니다.)</span>
			</span><br/>
			<input type="text" class="form-control" name="phone" 
				id="register_phone" placeholder="예) 010-1234-5678">
			<div style='display:none' id="phone_able_alert">
				<span class="small">
					<b style="color: #28a745;">사용가능</b>
				</span>
			</div>
			<div style='display:none; float:left;' id="phone_disable_alert">
				<span class="small">
					<b style="color: #dc3545;">사용불가능한 연락처입니다.</b>
				</span>
			</div>
			<div style='display:none; float:left;' id="phone_check_alert">
				<span class="small">
					<b style="color: #4e73df;">연락처 중복체크가 필요합니다.</b>
				</span>
			</div>
			<input style="display:inline-block; width:35%; float:right; margin-top:4px;" 
				type="button" class="form-control" id="phoneCheck" value="중복체크"><br/>
		</div>

		<div class="form-group">
			<span>
				<b style="font-size:14px">조직 </b>
			</span><br/>
			<input type="text" class="form-control" name="organization"
				   id="register_organization" placeholder="조직명을 입력하세요.">
		</div>
		
		<div class="form-group">
			<div class="custom-control custom-checkbox small" style="text-align:center">
				<input type="checkbox" class="custom-control-input" id="customAgree"> 
				<label class="custom-control-label" for="customAgree">개인 정보 활용에 동의합니다.</label>
			</div>
		</div>
		
		<div class="form-group">
			<input type="button" id="register_submit" value="등록"
				class="form-control form-control-user">
		</div>
	</form>
	<script 
		src="${pageContext.request.contextPath}/resource/js/member/upload_img.js"></script>
</body>
</html>
