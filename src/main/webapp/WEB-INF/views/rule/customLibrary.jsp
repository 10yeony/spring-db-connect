<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<title>SDTM</title>
	<style>
		th{
			background: #96BEE0;
		}
		tr:nth-child(even){
			background: #D8DEE1;
		}
		.class-component-bold {
			font-size:1.2em; 
			font-weight:bold;
		}
	</style>
</head>
<body>

<!-- Logout Modal-->
<div class="modal fade" id="customLibraryModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">라이브러리 추가하기</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">x</span>
				</button>
			</div>
			<div class="modal-body"><br/>
				
				<!-- .jar 라이브러리 파일 업로드 -->
				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">라이브러리 파일 업로드(.jar)</h6>
					</div>
					<div class="card-body">
						<form id="customJarUpload">
							<input type="file" id="customJarFile" name="customFile" accept="*" multiple>
							<input type="hidden" name="pack">
							<hr>
							<button type="button" onclick="customUpload('jar');" class="btn btn-primary btn-icon-split" style="float: left;">
								<span class="icon text-white-50"> 
									<i class="fas fa-check"></i>
								</span> 
								<span class="text">업로드</span>
							</button>
						</form>
					</div>
				</div><br/>
				
				<!-- .class 라이브러리 파일 업로드 -->
				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">라이브러리 파일 업로드(.class)</h6>
					</div>
					<div class="card-body">
						<form id="customClassUpload">
							<input type="file" id="customClassFile" name="customFile" accept="*"><br/><br/>
							<span><b>Package 이름 : </b></span><br/>
							<span style="font-size:1em; color:#e74a3b;">예) Test.class ➔ aaa.bbb.Test</span>
							<input class="form-control" type="text" id="pack" name="pack">
							<hr>
							<button type="button" onclick="customUpload('class');"
								class="btn btn-primary btn-icon-split" style="float: left;">
								<span class="icon text-white-50"> 
									<i class="fas fa-check"></i>
								</span> 
								<span class="text">업로드</span>
							</button>
						</form>
					</div>
				</div><br/>
				
				<!-- 내가 추가한 라이브러리 목록 -->
				<h5>
					<b>내가 추가한 라이브러리 목록</b>
				</h5>
				<div class="table-responsive">
					<table class="table table-bordered" width="100%" cellspacing="0">
						<thead>
							<tr>
								<th>no.</th>
								<th>라이브러리 이름</th>
								<th>패키지명</th>
							</tr>
						</thead>
						<tbody id="library-content"></tbody>
					</table>
				</div>
			</div><!-- modal-body -->
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>
<script
	src="${pageContext.request.contextPath}/resource/js/rule/customLibrary.js"></script>
</body>
</html>