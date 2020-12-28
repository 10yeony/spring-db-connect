<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>	
	<title>SDTM</title>
	<style>
		.role_check_area { cursor:pointer; }
	</style>
</head>
<!-- Logout Modal-->
<div class="modal fade" id="editAuthoritiesModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">권한 편집</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">x</span>
				</button>
			</div>
			<div class="modal-body">
				해당 회원에게 부여할 권한을 선택하세요.<br/>
				<span class="small">(데이터 조회 < 데이터입력 < 관리자)</span><br/><br/>
				<div>
					<input type="checkbox" id="role_view_check">
					<span style="margin-right:10px;" class="role_check_area" id="role_view_check_area">
	            	 	 데이터 조회 
		          	</span>
		          	<input type="checkbox" id="role_input_check">
		          	<span style="margin-right:10px;" class="role_check_area" id="role_input_check_area">
		            	 데이터 입력 
		          	</span>
		          	<input type="checkbox" id="role_admin_check">
		          	<span class="role_check_area" id="role_admin_check_area">
		            	 관리자 
		          	</span>
	            </div><br/><br/>
			</div>
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">취소</button>
				<button class="btn btn-primary" type="button" id="editAuthoritiesBtn">권한 수정</button>
			</div>
		</div>
	</div>
</div>