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
			<style>
				#code_area{
					background: #322931;
					text-align: left;
					padding: 10px;
				}
				.CodeMirror{
					font-size: 1em;
					font-family: Nunito,-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
				}
				.codeWhite{ color: white; }
				.codeRed{ color: #dd464c; }
				.codeOrange{ color: #fd8b19; }
				.codeYellowGreen{ color: #8fc13e }
				.codeRemoveMargin{ margin-left:-3px; margin-right:-3px; }
			</style>
		</c:if>
		<c:if test="${rule.rule_type == 'sql'}">
			<link
	            rel="stylesheet" href="https://codemirror.net/theme/duotone-light.css">
		    <script
		    	src="${pageContext.request.contextPath}/resource/codemirror/sql.js"></script>
		    <style>
		    	#code_area {
		    		text-align: left;
		    	}
		    </style>
		</c:if>
	</c:forEach>
	<style>
		mark {
			background: #F5ACB9;
		}
		.sql_style{
			background: #E4F5EB;
		}
		.method_style{
			background: black;
		}
	</style>
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
						<input type="hidden" class="rule_type" value="${rule.rule_type}">
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
										<c:if test="${rule.rule_type == 'sql'}">
											<div id="code_area">
												<textarea class="query" name="query" cols="170" rows="20"
	                                  		style="height: 300px;">${rule.contents}</textarea>
                                  	</div>
										</c:if>
										<c:if test="${rule.rule_type == 'method'}">
											<div id="code_area" style="font-size: 1.2em; font-weight: bold;">
												<div style="margin-left:0.23%;">
													<span class="codeRed">package</span>
													<span class="codeYellowGreen">kr</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">com</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">inspect</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">rule</span>
													<span class="codeWhite codeRemoveMargin">;</span>
												</div><br/>
												<div style="margin-left: 0.23%;">
													<span class="codeRed">import</span>
													<span class="codeYellowGreen">kr</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">com</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">inspect</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">rule</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">Data</span>
													<span class="codeWhite codeRemoveMargin">;</span>
													<br/>
													<span class="codeRed">import</span>
													<span class="codeYellowGreen">kr</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">com</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">inspect</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">dto</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">Metadata</span>
													<span class="codeWhite codeRemoveMargin">;</span>
													<br/>
													<span class="codeRed">import</span>
													<span class="codeYellowGreen">kr</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">com</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">inspect</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">dto</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">Program</span>
													<span class="codeWhite codeRemoveMargin">;</span>
													<br/>
													<span class="codeRed">import</span>
													<span class="codeYellowGreen">kr</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">com</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">inspect</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">dto</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">Speaker</span>
													<span class="codeWhite codeRemoveMargin">;</span>
													<br>
													<span class="codeRed">import</span>
													<span class="codeYellowGreen">kr</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">com</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">inspect</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">dto</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">Utterance</span>
													<span class="codeWhite codeRemoveMargin">;</span>
													<br>
													<span class="codeRed">import</span>
													<span class="codeYellowGreen">kr</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">com</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">inspect</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">dto</span>
													<span class="codeWhite codeRemoveMargin">.</span>
													<span class="codeYellowGreen">EojeolList</span>
													<span class="codeWhite codeRemoveMargin">;</span>
												</div>
												<div style="height: 100px;"><br/>
													<textarea class="imp_contents" name="imp_contents" cols="170" rows="20"
														style="height: 50px;">${rule.imp_contents}</textarea>
												</div><br/><br/>
												<div style="margin-left: 0.23%;">
													<span class="codeRed">public class</span>
													<span class="codeOrange"> ${rule.file_name}</span>
													<span class="codeWhite">{</span>
												</div>
												<div style="margin-left: 3%;">
													<span class="codeRed">public </span>
													<span class="codeWhite">Object </span>
													<span class="codeOrange">run</span>
													<span class="codeWhite" style="margin-left:-5px;">()</span>
													<span class="codeWhite">throws </span>
													<span class="codeYellowGreen">Exception </span>
													<span class="codeWhite">{</span>
												</div>
												<div style="margin-left: 5%; height: 300px;">
													<textarea class="contents" name="contents" cols="170" rows="20"
														style="resize: none;">${rule.contents}</textarea>
												</div>
				
												<div style="margin-left: 3%;">
													<span class="codeWhite">}</span>
												</div>
												<div style="margin-bottom: 5px;">
													<span class="codeWhite">}</span>
												</div>
											</div>
										</c:if>
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
