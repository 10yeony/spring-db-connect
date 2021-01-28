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
	<script src="${pageContext.request.contextPath}/resource/vendor/jquery/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/mark.js/8.11.1/mark.min.js" charset="UTF-8"></script>

	<!-- Custom fonts for this template-->
	<link href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

	<!-- Custom styles for this template-->
	<link href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css" rel="stylesheet">
	
	<script
			src="${pageContext.request.contextPath}/resource/codemirror/codemirror.js"></script>
	<link
			rel="stylesheet" href="${pageContext.request.contextPath}/resource/codemirror/codemirror.css">
	<c:forEach items="${list}" var="rule" varStatus="status">
		<c:if test="${rule.rule_type == 'method'}">
			<link
					rel="stylesheet" href="https://codemirror.net/theme/hopscotch.css">
			<script
					src="${pageContext.request.contextPath}/resource/codemirror/clike.js"></script>
		</c:if>
		<c:if test="${rule.rule_type == 'sql'}">
			<link
	            rel="stylesheet" href="https://codemirror.net/theme/duotone-light.css">
		    <script
		    	src="${pageContext.request.contextPath}/resource/codemirror/sql.js"></script>
		</c:if>
	</c:forEach>
</head>

<body id="page-top">
<input name="ruleChangeSize" type="hidden" value="${size}">
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
				<div>
					<h1 class="h3 mb-0 text-gray-800"><b>룰 버전 비교</b></h1><br>
				</div>
				
				<div class="row">	
					<c:forEach items="${list}" var="rule" varStatus="status">
						<div class="col-xl-6 col-lg-6">
							<div class="card shadow mb-4">
								<!-- Card Header - Dropdown -->
								<div
										class="card-header">
									<h5 class="m-0 font-weight-bold text-primary ruleChangeTitle"></h5>
								</div>
								<!-- Card Body -->
								<div class="card-body">
									<div align="center">
										<table class="table table-bordered" width="100%">
											<tr>
												<th>최근 작성일</th>
												<td class="rule_date">${rule.date}</td>
											</tr>
											<tr>
												<th>대분류</th>
												<td class="top_level_name">${rule.top_level_name}</td>
											</tr>
											<tr>
												<th>중분류</th>
												<td class="middle_level_name">${rule.middle_level_name}</td>
											</tr>
											<tr>
												<th>이름</th>
												<td class="bottom_level_name">${rule.bottom_level_name}</td>
											</tr>
											<tr>
												<th>버전</th>
												<td class="rule_version">${rule.version}</td>
											</tr>
											<tr>
												<th>설명</th>
												<td class="rule_description">${rule.description}</td>
											</tr>
										</table>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				
				<button class="btn btn-secondary" type="button" style="float:left;" onclick="window.history.back();">뒤로 가기</button>
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
<script src="${pageContext.request.contextPath}/resource/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="${pageContext.request.contextPath}/resource/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="${pageContext.request.contextPath}/resource/js/sb-admin-2.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/rule/getRuleChange.js"></script>
</body>

</html>
