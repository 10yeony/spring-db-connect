<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<script
		src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>
	<script 
		src="${pageContext.request.contextPath}/resource/js/member/member_info.js"></script>
</head>
<body>
<input type="hidden" class="session_email" value="${member.email}">
<input type="hidden" class="session_phone" value="${member.phone}">
<!-- Logout Modal-->
<div class="modal fade" id="memberInfoModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">사용자 정보 수정</h5>
				<button class="close" type="button" data-dismiss="modal"
					 id="editX" aria-label="Close">
					<span aria-hidden="true">x</span>
				</button>
			</div>
			<div class="modal-body">
				<form method="post" id="editFrm" name="edit">
					<div class="form-group">
						<span>
							<b style="font-size:14px">아이디 </b> 
						</span><br/>
						<input type="text" class="form-control" name="member_id"
							id="edit_member_id" value="${member.member_id}" disabled> 
					</div>
					
					<div class="form-group">
						<span>
							<b style="font-size:14px">이름 </b> 
						</span><br/>
						<input type="text" class="form-control" name="name"
							id="edit_name" value="${member.name}"> 
					</div>
					
					<div class="form-group">
						<span>
							<b style="font-size:14px">비밀번호 </b> 
							<span style="font-size:12px; color:red;">(필수)</span>
							<span style="float:right">
								<a class="small" id="goToMemberEdit" href="#" data-dismiss="modal"
								data-toggle="modal" data-target="#eidtPwdModal">
									비밀번호 수정
								</a>
							</span>
						</span><br/>
						<input type="password" class="form-control" name="pwd" 
							id="edit_pwd" placeholder="현재 비밀번호를 입력하세요" 
							minlength="8" maxlength="12" required>
						<div id="edit_pwdNotExist" class="small">
							<b style="color:#4e73df;">비밀번호를 입력하세요</b>
						</div>
						<div id="edit_pwdNotCorrect" style="display:none;">
							<span class="small">
								<b style="color:#e74a3b;">비밀번호가 틀렸습니다.</b>
							</span>
							<a id="edit_pwdForgot" class="small" href="javascript:void(0);"  
							data-dismiss="modal" data-toggle="modal" data-target="#sendPwdModal">
								비밀번호를 잊어버리셨습니까?
							</a>
						</div>
						<div id="edit_pwdCorrect" class="small" style="display:none;">
							<b style="color:#28a745;">OK !</b>
						</div>
					</div>

					<div class="form-group">
						<span>
							<b style="font-size:14px">이메일 </b> 
							<span style="font-size:12px; color:red;">(필수)</span>
						</span><br/>
						<input type="email" class="form-control" name="email" 
							id="edit_email" placeholder="예) sdtm@namutech.co.kr"
							value="${member.email}" required>
						<div style='display:inline-block; float:left;' id="edit_email_input_alert">
							<span class="small">
								<b style="color: #4e73df;">이메일을 입력하세요.</b>
							</span>
						</div>
						<div style='display:none; float:left;' id="edit_email_able_alert">
							<span class="small">
								<b style="color: #28a745;">사용가능</b>
							</span>
						</div>
						<div style='display:none; float:left;' id="edit_email_disable_alert">
							<span class="small">
								<b style="color: #dc3545;">사용불가능한 이메일입니다.</b>
							</span>
						</div>
						<div style='display:none; float:left;' id="edit_email_check_alert">
							<span class="small">
								<b style="color: #007bff;">이메일 중복체크가 필요합니다.</b>
							</span>
						</div>
						<input style="display:inline-block; width:35%; float:right; margin-top:4px;" 
							type="button" class="form-control" id="edit_emailCheck" value="중복체크"><br/>
					</div>
					
					<div class="form-group">
						<span>
							<b style="font-size:14px">연락처 </b> 
							<span style="font-size:12px">(문자 발송 서비스에 활용됩니다.)</span>
						</span><br/>
						<input type="text" class="form-control" name="phone" 
							id="edit_phone" placeholder="예) 010-1234-5678"
							value="${member.phone}">
						<div style='display:none' id="edit_phone_able_alert">
							<span class="small">
								<b style="color: #28a745;">사용가능</b>
							</span>
						</div>
						<div style='display:none; float:left;' id="edit_phone_disable_alert">
							<span class="small">
								<b style="color: #dc3545;">사용불가능한 연락처입니다.</b>
							</span>
						</div>
						<div style='display:none; float:left;' id="edit_phone_check_alert">
							<span class="small">
								<b style="color: #007bff;">연락처 중복체크가 필요합니다.</b>
							</span>
						</div>
						<input style="display:inline-block; width:35%; float:right; margin-top:4px;" 
							type="button" class="form-control" id="edit_phoneCheck" value="중복체크"><br/><br/>
					</div>
				</form>
			</div>
			<div class="modal-footer" style="display:block;">
				<button class="btn btn-danger" type="button" data-dismiss="modal"
					data-toggle="modal" data-target="#deleteModal" style="float:left;">탈퇴</button>
				<button class="btn btn-primary" id="editBtn" style="float:right;">정보 수정</button>
				<button class="btn btn-secondary" type="button" data-dismiss="modal"
					style="float:right;">취소</button>
			</div>
		</div>
	</div>
</div>
<!-- 임시 비밀번호 발송 Modal include -->
<%@ include file="/WEB-INF/views/login/send_pwd.jsp"%>

<!-- 비밀번호 수정 Modal include -->
<%@ include file="edit_pwd.jsp"%>

<!-- 회원탈퇴 Modal include -->
<%@ include file="delete_info.jsp"%>
</body>
</html>