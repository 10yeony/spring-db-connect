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
				<th>no.</th>
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
			<c:forEach items="${result}" var="item" varStatus="status">
				<tr>
					<td>${item.row_num}</td>
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
</body>

</html>
