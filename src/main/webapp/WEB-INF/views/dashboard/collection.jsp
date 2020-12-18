<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<!-- chart.js -->
	<script 
	src="${pageContext.request.contextPath}/resource/vendor/chart.js/Chart.min.js"></script>

	<!-- D3.js -->
	<script 
		src="https://d3js.org/d3.v5.min.js"></script>
	
	<!-- 대시보드 css -->
	<link 
		href="${pageContext.request.contextPath}/resource/css/dashboard.css" rel="stylesheet">
</head>

<!-- Content Row -->
<div class="row">

	<!-- Earnings (Monthly) Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-primary shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
							총 전사 데이터 개수</div>
						<div class="h5 mb-0 font-weight-bold text-gray-800" id="metadata"></div>
					</div>
					<div class="col-auto">
						<i class="far fa-file-audio fa-3x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Earnings (Monthly) Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-success shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-success text-uppercase mb-1">
							총 문장 개수</div>
						<div class="h5 mb-0 font-weight-bold text-gray-800" id="utterance"></div>
					</div>
					<div class="col-auto">
						<i class="fas fa-comment-dots fa-2x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Earnings (Monthly) Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-info shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-info text-uppercase mb-1">총 어절 개수
						</div>
						<div class="row no-gutters align-items-center">
							<div class="col-auto">
								<div class="h5 mb-0 mr-3 font-weight-bold text-gray-800" id="eojeol"></div>
							</div>
						</div>
					</div>
					<div class="col-auto">
						<i class="fas fa-comment-dots fa-2x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Pending Requests Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-warning shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
							회원 수</div>
						<div class="h5 mb-0 font-weight-bold text-gray-800" id="member"></div>
					</div>
					<div class="col-auto">
						<i class="fas fa-user-friends fa-2x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

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
				<h5 class="m-0 font-weight-bold text-primary">음성 데이터 유형</h5>
			</div>
			<!-- Card Body -->
			<div class="card-body">
				<div align="center">
					<canvas class="inline-block-chart" id="myPieChart" width="500%"></canvas>
					<script src="${pageContext.request.contextPath}/resource/js/demo/chart-pie-demo.js"></script>
				</div>
				<div class="mt-4 text-center small" id="pieChartDesc" style="margin-top:4px; margin-bottom:12px;"></div>
			</div>
		</div>
	</div>
</div>
</html>

<script
		src="${pageContext.request.contextPath}/resource/js/dashboard/dataCount.js"></script>