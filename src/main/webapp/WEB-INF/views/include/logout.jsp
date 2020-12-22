<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<script src="${pageContext.request.contextPath}/resource/js/member/logout.js"></script>
</head>
<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">로그아웃 하시겠습니까?</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">x</span>
				</button>
			</div>
			<div class="modal-body">로그아웃 하시려면 로그아웃 버튼을 눌러주세요.</div>
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">취소</button>
				
				<form id="logoutFrm" action="${pageContext.request.contextPath}/logout" method="POST">
					<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
					<input type="submit" value="로그아웃" class="btn btn-primary">
				</form>

			</div>
		</div>
	</div>
</div>