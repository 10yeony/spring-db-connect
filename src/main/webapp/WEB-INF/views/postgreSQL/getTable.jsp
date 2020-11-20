<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
	<script src="http://code.jquery.com/jquery-1.4.4.js"></script>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>Metadata Table</title>

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
	<%@ include file="../include/sidebar.jsp"%>

	<!-- Content Wrapper -->
	<div id="content-wrapper" class="d-flex flex-column">

		<!-- Main Content -->
		<!-- 이 부분만 바꿔주면 됩니다 -->
		<div id="content">
			<!-- 툴바 include -->
			<%@ include file="../include/toolbar.jsp"%>
			<!-- Begin Page Content -->
			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="d-sm-flex align-items-center justify-content-between mb-4">
					<h1 class="h3 mb-2 text-gray-800"><b>한국어 강의 데이터 목록</b></h1>
					<div>
						<!-- 파일 다운로드 버튼 -->
						<a href="${pageContext.request.contextPath}/metadata/docx" class="d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
								class="fas fa-download fa-sm text-white-50"></i> Word</a>
						<a href="${pageContext.request.contextPath}/metadata/xlsx" class="d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
								class="fas fa-download fa-sm text-white-50"></i> Excel</a>
						<div class="my-2"></div>
<%--						<a onclick="mail('metadataMail/docx');" class="d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i--%>
<%--								class="fas fa-download fa-sm text-white-50"></i> docxMail</a>--%>
<%--						<a onclick="mail('metadataMail/xlsx');" class="d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i--%>
<%--								class="fas fa-download fa-sm text-white-50"></i> xlsxMail</a>--%>

						<!-- 파일 전송 버튼 -->
						<div>
							<ul class="navbar-nav ml-auto">
								<li class="nav-item dropdown no-arrow">
									<a class="nav-link dropdown-toggle btn btn-primary shadow-sm btn-sm " href="#" id="fileSend" style="width: 105pt"
									   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i
											class="fas fa-paper-plane fa-sm text-white-50"></i>&nbsp;&nbsp;파일 전송</a>
									<!-- Dropdown - User Information -->
									<div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
										 aria-labelledby="fileSend">
										<h5 class="dropdown-header" style="color: black;font-size: 1.5em">
											<i class="fas fa-at"></i>&nbsp;&nbsp;E-Mail
										</h5>
										<div class="my-2"></div>
										<a class="dropdown-item btn" onclick="send('mail','metadataMail/docx');">
											<h6>- Word</h6>
										</a>
										<a class="dropdown-item btn" onclick="send('mail','metadataMail/xlsx');">
											<h6>- Excel</h6>
										</a>
										<a class="dropdown-item text-center small text-gray-500">${member.email}(으)로 발송됩니다.</a>
<%--										<div class="dropdown-divider"></div>--%>
<%--										<h5 class="dropdown-header" style="color: black;font-size: 1.5em">--%>
<%--											<i class="fas fa-envelope"></i>&nbsp;&nbsp;SMS--%>
<%--										</h5>--%>
<%--										<div class="my-2"></div>--%>
<%--										<a class="dropdown-item btn" onclick="send('sms','metadataSMS/docx')">--%>
<%--											<h6>- Word</h6>--%>
<%--										</a>--%>
<%--										<a class="dropdown-item btn" onclick="send('sms','metadataSMS/xlsx')">--%>
<%--											<h6>- Excel</h6>--%>
<%--										</a>--%>
<%--										<a class="dropdown-item text-center small text-gray-500">${member.phone}(으)로 발송됩니다.</a>--%>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>

				<!-- Page Body -->
				<div class="card shadow mb-4">

					<div class="card-body">
						<div class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100">
							<input type="text" class="form-control bg-light border-0 small" placeholder="Search for..."
								   id="inputSearchText">
							<button class="btn btn-primary" type="button">
								<i class="fas fa-search fa-sm"></i>
							</button>
						</div><br><br>
						<div class="table-responsive">
							<table class="table table-bordered" id="metadata" width="100%" cellspacing="0">
								<thead>
									<tr>
										<th>Id</th>
										<th>제목</th>
										<th>부제</th>
										<th>Creator</th>
										<th>Year</th>
										<th>파일명</th>
										<th>문장 수</th>
										<th>어절 수</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${result}" var="item">
										<tr>
											<td>${item.id}</td>
											<td>${item.program.title}</td>
											<td><a href="getUtteranceTable/${item.id}">${item.program.subtitle}</a></td>
											<td>${item.creator}</td>
											<td>${item.year}</td>
											<td>${item.title}</td>
											<td><a href="getUtteranceTable/${item.id}">${item.sentence_count}개</a></td>
											<td><a href="getUtteranceTable/${item.id}">${item.eojeol_total}개</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>


			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- End of Main Content -->

		<!-- footer include-->
		<%@ include file="../include/footer.jsp"%>

	</div>
	<!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
	<i class="fas fa-angle-up"></i>
</a>

<script>

$(document).ready(function() {
	$("#inputSearchText").keyup(function() {
		var k = $(this).val();
		$("#metadata > tbody > tr").hide();
		var temp = $("#metadata > tbody > tr > td:contains('" + k + "')");

		$(temp).parent().show();
	})
})

function send(type, fileurl){
	var url = '${pageContext.request.contextPath}/' + fileurl;

	$.ajax({
		type:"GET",
		url: url,

		success:function (){
			if(type == 'mail')
				alert("메일이 성공적으로 전송되었습니다.");
			else if(type == 'sms')
				alert("문자가 성공적으로 전송되었습니다.");
		},
		error: function (){
			alert("에러");
		}
	})

	if(type == 'mail')
		alert("메일이 전송됩니다.");
	else if(type == 'sms')
		alert("문자가 전송됩니다.");
}
</script>

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
</body>

</html>
