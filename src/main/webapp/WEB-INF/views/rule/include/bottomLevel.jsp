<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>SDTM</title>
	
	<!-- Custom fonts for this template-->
	<link href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

	<!-- Custom styles for this template-->
	<link href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css" rel="stylesheet">
	
	<style>
		.bottom_level_collection{
			display: none;
		}
	</style>
</head>

<body>

	<select class="form-control" id="bottom_level" style="margin-right:3px;">
		<option value="bottom_level_value">소분류</option>
		<!-- 티맥스 중분류 -->
		<option class="bottom_level_collection tmax_bottom_level" 
			value="tmax_bottom_level1">테스트</option>
	</select>

	<!-- Bootstrap core JavaScript-->
	<script src="${pageContext.request.contextPath}/resource/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
