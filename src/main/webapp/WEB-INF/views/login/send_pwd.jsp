<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<script
		src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>	
	<script 
		src="${pageContext.request.contextPath}/resource/js/member/send_pwd.js"></script>
</head>
<!-- Logout Modal-->
<div class="modal fade" id="sendPwdModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">임시 비밀번호 메일 발송</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span class="goToMemberInfo" aria-hidden="true">x</span>
				</button>
			</div><!-- modal-header -->
			<div class="modal-body">
				가입하신 아이디와 이메일을 입력하세요.<br/><br/>
				<div class="form-group">
					<span>
						<b style="font-size:14px">아이디 </b> 
					</span><br/>
					<input type="text" class="form-control" name="send_pwd_id" 
						id="send_pwd_id" placeholder="아이디를 입력하세요" required>
				</div><!-- form-group -->
				
				<div class="form-group">
					<span>
						<b style="font-size:14px">이메일 </b> 
					</span><br/>
					<input type="text" class="form-control" name="send_pwd_email" 
						id="send_pwd_email" placeholder="이메일을 입력하세요" required>
				</div><!-- form-group -->
			</div><!-- modal-body -->
				
			<div class="modal-footer">
				<button class="btn btn-secondary goToMemberInfo" type="button" data-dismiss="modal">취소</button>
				<button class="btn btn-primary" type="button" id="sendPwdBtn">메일 발송</button>
			</div><!-- modal-footer -->
		</div>
	</div>
</div>
</html>