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
<input type="hidden" id="title" value="룰 버전 리스트">
	<table id="downTable" class="table table-bordered paging-table" width="100%" cellspacing="0">
		<thead>
			<tr>
				<th>Id</th>
				<th>작성일시</th>
				<th>버전</th>
				<th>작성자</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result}" var="item" varStatus="status">
				<tr>
					<td>${item.row_num}</td>
					<td>
						<a href="${pageContext.request.contextPath}/rule/getRuleChange?bottom_level_id=${item.bottom_level_id}&prev_bottom_level_id=${item.prev_bottom_level_id}">${item.date}</a>
					</td>
					<td>
						<a href="${pageContext.request.contextPath}/rule/getRuleChange?bottom_level_id=${item.bottom_level_id}&prev_bottom_level_id=${item.prev_bottom_level_id}">${item.version}</a>
					</td>
					<td>${item.creator}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>

</html>
