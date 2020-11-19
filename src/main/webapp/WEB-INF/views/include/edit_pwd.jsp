<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<script
		src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>	
	<script 
		src="${pageContext.request.contextPath}/resource/js/member/edit_pwd.js"></script>
</head>
<!-- Logout Modal-->
<div class="modal fade" id="eidtPwdModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">비밀번호를 수정하시겠습니까?</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span class="goToMemberInfo" aria-hidden="true">x</span>
				</button>
			</div>
			<div class="modal-body">
				비밀번호를 수정하시려면 빈칸을 모두 입력하세요.<br/><br/>
				<div class="form-group">
					<span>
						<b style="font-size:14px">현재 비밀번호 </b> 
					</span><br/>
					<input type="password" class="form-control" name="present_pwd" 
						id="edit_present_pwd" placeholder="현재 비밀번호를 입력하세요" required>
					<div id="edit_pwd_pwdNotExist" class="small" style="color:#4e73df;">
						<b>현재 비밀번호를 입력하세요</b>
					</div>
					<div id="edit_pwd_pwdNotCorrect" style="display:none;">
						<span class="small" style="color:#e74a3b;">
							<b>현재 비밀번호가 틀렸습니다.</b>
						</span>
					</div>
					<div id="edit_pwd_pwdCorrect" class="small" style="display:none;">
						<b style="color:#28a745;">OK !</b>
					</div>
				</div>
				
				<div class="form-group">
					<span>
						<b style="font-size:14px">새 비밀번호 </b> 
					</span><br/>
					<input type="password" class="form-control" name="pwd" 
						id="edit_new_pwd" placeholder="영어/숫자/특수문자 포함 8자 이상 12자 이하" required>
				</div>
				
				<div class="form-group">
					<span>
						<b style="font-size:14px">새 비밀번호 확인 </b> 
					</span><br/>
					<input type="password" class="form-control" name="pwdCheck" 
						id="edit_new_pwd_check" placeholder="새 비밀번호를 한번 더 입력하세요." required>
					<div style='display:block;' id="edit_new_pwd_input_alert">
						<span class="small">
							<b style="color: #4e73df;">새 비밀번호를 입력하세요.</b>
						</span>
					</div>
					<div style='display:none;' id="edit_new_pwd_same_alert">
						<span class="small"> 
							<b style="color: #28a745;">새 비밀번호 일치</b>
						</span>
					</div>
					<div style='display:none;' id="edit_new_pwd_different_alert">
						<span class="small">
							<b style="color: #dc3545;">새 비밀번호가 일치하지 않습니다.</b>
						</span>
					</div>
					<div style='display:none;' id="edit_new_pwd_disable_alert">
						<span class="small">
							<b style="color: #dc3545;">새 비밀번호의 형식이 잘못되었습니다.</b>
						</span>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-secondary goToMemberInfo" type="button" data-dismiss="modal">취소</button>
				<button class="btn btn-primary" type="button" id="editPwdBtn">비밀번호 수정</button>
			</div>
		</div>
	</div>
</div>