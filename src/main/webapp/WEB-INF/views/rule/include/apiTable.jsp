<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<title>SDTM</title>
	<style>
		.api-table th{
			background: #96BEE0;
		}
		.api-table tr:nth-child(even){
			background: #D8DEE1;
		}
		.normal-weight{
			font-weight:normal;
		}
	</style>
</head>
<body>
	<h3>
		<b id="class-name"></b>
	</h3>
	<span id="class-description"></span><br/><br/>
	<h5>
		<b>필드 요약</b>(Field Summary)
	</h5>
	<div class="table-responsive">
		<table class="table table-bordered api-table" width="100%" cellspacing="0">
			<thead>
				<tr>
					<th>
						제한자 및 타입<br/>
						<span class="normal-weight">(Modifier and Type)</span>
					</th>
					<th>
						필드 및 설명<br/>
						<span class="normal-weight">Field and Description</span>
					</th>
				</tr>
			</thead>
			<tbody id="class-field">
			</tbody>
		</table>
	</div><br/>
	<h5>
		<b>생성자 요약</b>(Constructor Summary)
	</h5>
	<div class="table-responsive">
		<table class="table table-bordered api-table" width="100%" cellspacing="0">
			<thead>
				<tr>
					<th>
						생성자 및 설명<br/>
						<span class="normal-weight">(Constructor and Description)</span>
					</th>
				</tr>
			</thead>
			<tbody id="class-constructor">
			</tbody>
		</table>
	</div><br/>
	<h5>
		<b>메소드 요약</b>(Method Summary)
	</h5>
	<div class="table-responsive">
		<table class="table table-bordered api-table" width="100%" cellspacing="0">
			<thead>
				<tr>
					<th>
						제한자와 타입<br/>
						<span class="normal-weight">Modifier and Type</span>
					</th>
					<th>
						메소드 및 설명<br/>
						<span class="normal-weight">Method and Description</span>
					</th>
				</tr>
			</thead>
			<tbody id="class-method">
			</tbody>
		</table>
	</div>
</body>
</html>