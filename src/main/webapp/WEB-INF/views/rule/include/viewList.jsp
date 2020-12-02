<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Home Page</title>

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
</head>

<body>
	<div class="table-responsive">
		<table class="table table-bordered" id="ruleList" width="100%" cellspacing="0">
			<thead>
				<tr>
					<th>no.</th>
					<th>대분류</th>
					<th>중분류</th>
					<th>소분류</th>
					<th>설명</th>
					<th>작성자</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td>대분류 내용</td>
					<td>중분류 내용</td>
					<td><a href="${pageContext.request.contextPath}/editRule">소분류 내용</a></td>
					<td>설명</td>
					<td>작성자</td>
				</tr>
			</tbody>
		</table>
	</div>					
<!-- Bootstrap core JavaScript-->
<script
	src="${pageContext.request.contextPath}/resource/vendor/jquery/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script
	src="${pageContext.request.contextPath}/resource/vendor/jquery-easing/jquery.easing.min.js"></script>

</html>
