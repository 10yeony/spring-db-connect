<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- D3.js -->
	<script src="https://d3js.org/d3.v5.min.js"></script>
	
	<!-- 대시보드 css -->
	<link href="${pageContext.request.contextPath}/resource/css/dashboard.css" rel="stylesheet">
</head>

<!-- bar Chart -->
<div class="row">
	<div class="col-xl-5 col-lg-5">
		<div class="card shadow mb-4">
			<!-- Card Header - Dropdown -->
			<div
					class="card-header">
				<h5 class="m-0 font-weight-bold text-primary">JSON 파일 입력 시간</h5>
			</div>
			<!-- Card Body -->
			<div class="card-body">
				<div align="center">
					<div class="inline-block-chart" id="bar-svg-area1">
						<div id="bar-tooltip1"></div>
					</div>
					<script src="${pageContext.request.contextPath}/resource/js/dashboard/sample/bar-chart.js"></script>
				</div>
			</div>
		</div>
	</div>

	<!-- line Chart -->
	<div class="col-xl-7 col-lg-6">
		<div class="card shadow mb-4">
			<!-- Card Header - Dropdown -->
			<div
					class="card-header">
				<h5 class="m-0 font-weight-bold text-primary">line chart</h5>
			</div>
			<!-- Card Body -->
			<div class="card-body">
				<div align="center">
					<div class="inline-block-chart" id="line-svg-area1"></div>
					<script src="${pageContext.request.contextPath}/resource/js/dashboard/sample/line-chart.js"></script>
				</div>
			</div>
		</div>
	</div>
</div>
</html>