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

<script
	src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resource/codemirror/codemirror.js"></script>
<link 
	rel="stylesheet" href="${pageContext.request.contextPath}/resource/codemirror/codemirror.css">
<link 
	rel="stylesheet" href="https://codemirror.net/theme/eclipse.css">
<script
	src="${pageContext.request.contextPath}/resource/codemirror/clike.js"></script>

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

<script>
	window.onload = function() {
		var contents_textarea = document.getElementById("contents");
		
		window.editor = CodeMirror.fromTextArea(contents_textarea, {
		    mode: "text/x-java",
			 lineNumbers: true,
		    lineWrapping: true,
		    styleActiveLine: true,
		    matchBrackets: true,
		    theme: "eclipse"
		  });
	}
</script>

</head>

<body id="page-top">
	<input id="msg" type="hidden" value="${msg}">
	<!-- 소분류 아이디 -->
	<input type="hidden" id="bottom_level_id"
		value="${rule.bottom_level_id}" name="bottom_level_id">

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
					<div
						class="d-sm-flex align-items-center justify-content-between mb-4">
						<h1 class="h3 mb-2 text-gray-800" style="display: inline-block">
							<b>Rule 작성</b>
						</h1>
						<br /> <span><b>이름 :</b> ${rule.bottom_level_name}</span>
					</div>

					<!-- Page Body -->
					<div class="card shadow mb-4">
						<div class="card-body">
							<div style="font-size: 1.2em;">
								제시된 양식을 참고하여 코드를 작성해주세요 <a class="small" id="goToRuleApi"
									href="#" data-dismiss="modal" data-toggle="modal"
									data-target="#ruleApiModal"> API 문서 보기 </a>
							</div>
							<br />
							<form
								action="${pageContext.request.contextPath}/rule/saveRuleContents"
								id="editRuleFrm">
								<input name="${_csrf.parameterName}" type="hidden"
									value="${_csrf.token}" />
								<div id="code_area" style="font-size: 1.2em; font-weight: bold;">
									<div>
										<span style="color: orange;">public class</span> <span
											style="color: #26B3E0;"> ${rule.file_name}</span> {
									</div>
									<div style="margin-left: 3%;">
										<span style="color: orange;">public </span> <span
											style="color: #26B3E0;">Object </span> <span
											style="color: #00967B;">run()</span>{
									</div>

									<textarea id="contents">${rule.contents}</textarea>
									
									<div style="margin-left: 3%;">}</div>
									<div style="margin-bottom: 5px;">}</div>
								</div>
								<input name="bottom_level_id" type="hidden" id="bottom_level_id"
									value="${rule.bottom_level_id}">

								<div id="show_result_after_update">
									<b>실행결과</b>
									<textarea class="form-control" rows="5" style="resize: none;"
										readonly></textarea>
								</div>
								<div style="display: block; margin-top: 5px;">
									<button class="btn btn-danger" type="button" id="deleteRuleBtn"
										style="float: left;">삭제</button>
									<button id="ruleUpdateBtn" type="button"
										class="btn btn-primary" style="float: right;">작성</button>
									<button class="btn btn-secondary" type="button"
										id="backRuleBtn" style="float: right; margin-right: 5px;">돌아가기</button>
								</div>
							</form>
						</div>
						<!-- card-body -->
					</div>
					<!-- card shadow mb-4 -->
				</div>
				<!-- container-fluid -->
			</div>
			<!-- content -->
		</div>
		<!-- content-wrapper -->
	</div>
	<!-- wrapper -->
</body>
<!-- /.container-fluid -->
<!-- End of Main Content -->

<!-- Rule API 문서 Modal -->
<%@ include file="ruleApi.jsp"%>

<!-- footer include-->
<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- End of Content Wrapper -->

<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top"> <i
	class="fas fa-angle-up"></i>
</a>

<!-- Bootstrap core JavaScript-->
<script
	src="${pageContext.request.contextPath}/resource/vendor/jquery/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script
	src="${pageContext.request.contextPath}/resource/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script
	src="${pageContext.request.contextPath}/resource/js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script
	src="${pageContext.request.contextPath}/resource/vendor/chart.js/Chart.min.js"></script>

<!-- Page level custom scripts -->
<script
	src="${pageContext.request.contextPath}/resource/js/demo/chart-area-demo.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/demo/chart-pie-demo.js"></script>

<script
	src="${pageContext.request.contextPath}/resource/js/rule/editRule.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/rule/ruleApi.js"></script>
</body>

</html>
