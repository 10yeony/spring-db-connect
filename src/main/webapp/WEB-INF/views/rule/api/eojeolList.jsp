<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<body>
	<h3>
		<b id="class-name">Class Data</b>
	</h3>
	<span id="class-description">사용자가 가져다 쓸 DB 접속 객체</span><br/><br/>
	<h5>
		<b>Field Summary</b>
	</h5>
	<div class="table-responsive">
		<table class="table table-bordered" width="100%" cellspacing="0">
			<thead>
				<tr>
					<th>Modifier and Type</th>
					<th>Field and Description</th>
				</tr>
			</thead>
			<tbody id="class-field">
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold">driver</div>
						<div>DB(PostgreSQL) Driver 클래스명</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div><br/>
	<h5>
		<b>Constructor Summary</b>
	</h5>
	<div class="table-responsive">
		<table class="table table-bordered" width="100%" cellspacing="0">
			<thead>
				<tr>
					<th>Constructor and Description</th>
				</tr>
			</thead>
			<tbody id="class-constructor">
				<tr>
					<td>
						<span class="class-component-bold">Data</span>()
						<div>Data 객체의 기본 생성자(JDBC 정보 세팅)</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div><br/>
	<h5>
		<b>Method Summary</b>
	</h5>
	<div class="table-responsive">
		<table class="table table-bordered" width="100%" cellspacing="0">
			<thead>
				<tr>
					<th>Modifier and Type</th>
					<th>Method and Description</th>
				</tr>
			</thead>
			<tbody id="class-method">
				<tr>
					<td>Connection</td>
					<td>
						<span class="class-component-bold">getConnect</span>()
						<div>
							DB 설정 정보를 넣고 DB 연결 객체를 반환함.
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>