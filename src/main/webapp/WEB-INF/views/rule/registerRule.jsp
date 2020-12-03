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

	<title>Home Page</title>

	<!-- Custom fonts for this template-->
	<link href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

	<!-- Custom styles for this template-->
	<link href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css" rel="stylesheet">
</head>

<body id="page-top">
	
<!-- Page Wrapper -->
<div id="wrapper">

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
					<h1 class="h3 mb-2 text-gray-800"><b>Rule 등록</b></h1>
				</div>
				<!-- Page Body -->
				<div class="card shadow mb-4">
					<div class="card-body">
					 	<div class="table-responsive">
					 		<table class="table table-bordered" id="dataTable" width="50%" cellspacing="0">
					 			<thead></thead>
					 			<tbody>
					 				<tr>
					 					<th>대분류</th>
					 					<td>
					 						<%@ include file="/WEB-INF/views/rule/include/topLevel.jsp"%>
					 						<div id="add_top_level_area" style="margin-top:5px;">
					 							<form method="post" action="${pageContext.request.contextPath}/rule/addRuleLevel" accept-charset="utf-8">
							 						<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
							 						<input style="display:inline-block; width:75%;" type="text" class="form-control" 
							 						name="new_top_level_name" id="new_top_level_name" placeholder="10자 이하로 입력하세요" maxlength="10" required> 
													<input style="display:inline-block; width:22%;" type="submit" 
													class="form-control" id="add_top_level_btn" value="등록">
												</form>
											</div>
					 					</td>
					 				</tr>
					 				<tr>
					 					<th>중분류</th>
					 					<td>
					 						<%@ include file="/WEB-INF/views/rule/include/middleLevel.jsp"%>
					 					</td>
					 				</tr>
					 			</tbody>
					 		</table>
						</div><!-- table-responsive -->
					</div><!-- card-body -->
				</div><!-- card shadow mb-4 -->
				
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

<script src="${pageContext.request.contextPath}/resource/js/rule/ruleCategory.js"></script>\
<script src="${pageContext.request.contextPath}/resource/js/rule/registerRule.js"></script>
</body>
</html>
