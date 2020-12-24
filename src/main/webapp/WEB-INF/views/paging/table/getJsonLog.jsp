<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
	<meta charset="utf-8">
	<title>Table</title>
</head>

<body>
	<table class="table table-bordered" width="100%" cellspacing="0">
		<thead>
			<tr>
				<th>Id</th>
				<th>파일명</th>
				<th>제목</th>
				<th>부제</th>
				<th width="90">강의 시간</th>
				<th>입력 시작 시간</th>
				<th>입력 종료 시간</th>
				<th width="150">Parsing 및 <br/>DB 입력 소요시간<br>(단위 분:초)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${jsonLog}" var="item" varStatus="status">
				<tr>
					<td>${item.row_num}</td>
					<td><a href="getUtteranceTable/${item.metadata_id}">${item.title}</a></td>
					<td>${item.program.title}</td>
					<td>${item.program.subtitle}</td>
					<td>${item.program.running_time}</td>
					<td>${item.start}</td>
					<td>${item.finish}</td>
					<td>${item.elapsed}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>

</html>
