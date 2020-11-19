<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<script
		src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>	
	<script 
		src="${pageContext.request.contextPath}/resource/js/member/delete_info.js"></script>
</head>
<!-- Logout Modal-->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">정말로 탈퇴하시겠습니까?</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">x</span>
				</button>
			</div>
			<div class="modal-body">
				탈퇴하시려면 비밀번호를 입력해주세요.<br/><br/>
				<div class="form-group">
					<span>
						<b style="font-size:14px">비밀번호 </b> 
					</span><br/>
					<input type="password" class="form-control" name="pwd" id="delete_pwd" required>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">취소</button>
				<button class="btn btn-danger" type="button" id="deleteBtn">회원탈퇴</button>
			</div>
		</div>
	</div>
</div>