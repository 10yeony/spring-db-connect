<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<title>SDTM</title>
	<style>
		.api-table th{
			background: #96BEE0;
		}
		.api-table tr:nth-child(even){
			background: #D8DEE1;
		}
		.class-component-bold {
			font-size:1.2em; 
			font-weight:bold;
		}
	</style>
</head>
<body>
<!-- Logout Modal-->
<div class="modal fade" id="ruleApiModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">SDTM 룰 API 문서</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">x</span>
				</button>
			</div>
			<div class="modal-body">
				<!-- 클래스 선택 -->
				<select id="api-class-select" class="form-control">
					<option value=1>Data</option>
					<option value=2>Metadata</option>
					<option value=3>Program</option>
					<option value=4>Speaker</option>
					<option value=5>Utterance</option>
					<option value=6>EojeolList</option>
				</select><br/>
				
				<%@ include file="./include/apiTable.jsp"%>
			</div><!-- modal-body -->
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>
</body>
</html>