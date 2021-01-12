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

	<!-- w3 css -->
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<!-- 로딩 이미지 -->
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

<body id="page-top">
	
<!-- Page Wrapper -->
<div id="wrapper">
	<!-- 업로드시 로딩 화면 -->
	<div id="loadingArea" class="w3-modal w3-animate-opacity">
		<img class="loading" width="100px"
			 src="${pageContext.request.contextPath}/resource/img/loading.gif">
	</div>

	<!-- 사이드바 include-->
	<%@ include file="/WEB-INF/views/include/sidebar.jsp"%>

	<!-- Content Wrapper -->
	<div id="content-wrapper" class="d-flex flex-column">

		<!-- Main Content -->
		<!-- 이 부분만 바꿔주면 됩니다 -->
		<div id="content">
			<!-- 툴바 include -->
			<%@ include file="/WEB-INF/views/include/toolbar.jsp"%>
			
			<!-- Begin Page Content -->
			<div class="container-fluid">
				<!-- Page Heading -->
				<div class="d-sm-flex align-items-center justify-content-between mb-4">
					<h1 class="h3 mb-2 text-gray-800"><b>룰 실행</b></h1>
				</div>
				
				<!-- Page Body -->
				<div class="card shadow mb-4">
					<div class="card-body">
						<div class="d-sm-inline-block form-inline ml-md-3 my-2 my-md-0 mw-100">
							<%@ include file="/WEB-INF/views/rule/include/topLevel.jsp"%>
							<%@ include file="/WEB-INF/views/rule/include/middleLevel.jsp"%>
							<%@ include file="/WEB-INF/views/rule/include/bottomLevel.jsp"%>
						</div>
						<button style="display:inline-block;" 
					 		class="btn btn-primary" id="run_rule_btn">실행</button>
						<button id="ruleReportBtn" type="button" class="btn btn-primary btn-icon-split" style="float: right;" onclick="test();">
							<span class="icon text-white-50"><i class="fas fa-download fa-sm text-white-50"></i></span>
							<span class="text"> Word</span>
						</button>
					 	<div id="run_rule_result_area">
					 		<br/>
					 		<textarea class="form-control" rows="5" style="resize: none;" readonly>
전사규칙을 선택하고 실행하세요.
					 		</textarea>
					 	</div>
					</div><br/><br/>
				</div>
			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- End of Main Content -->

<!-- footer include-->
<%@ include file="/WEB-INF/views/include/footer.jsp"%>

	</div>
	<!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
	<i class="fas fa-angle-up"></i>
</a>

<!-- Bootstrap core JavaScript-->
<script src="${pageContext.request.contextPath}/resource/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="${pageContext.request.contextPath}/resource/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="${pageContext.request.contextPath}/resource/js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script src="${pageContext.request.contextPath}/resource/vendor/chart.js/Chart.min.js"></script>

<!-- Page level custom scripts -->
<script src="${pageContext.request.contextPath}/resource/js/demo/chart-area-demo.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/demo/chart-pie-demo.js"></script>

<script src="${pageContext.request.contextPath}/resource/js/rule/ruleCategory.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/rule/runRule.js"></script>
</body>

</html>
