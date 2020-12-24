<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
	<meta charset="utf-8">
	<title>Table</title>
</head>

<body>
	<table id="usingLogList" class="table table-bordered" width="100%" cellspacing="0">
		<thead>
			<tr>
				<th>no.</th>
				<th>아이디</th>
				<th>사용 내역</th>
				<th>IP 주소</th>
				<th>접속 시간</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result}" var="item" varStatus="status">
				<tr>
					<td>${item.row_num}</td>
					<td><a href="getMemberByAdmin?member_id=${item.member_id}">${item.member_id}</a></td>
					<td>
					<c:choose>
					<c:when test="${fn:contains(item.content, 'Rule')}">
						<a href="${pageContext.request.contextPath}/getRuleLogList?data=${item.no}&current_page_no=1&count_per_page=10&count_per_list=10&search_word=">${item.content}</a>
					</c:when>
					<c:otherwise>
						${item.content}
					</c:otherwise>
					</c:choose>
					</td>
					<td>${item.ip_addr}</td>
					<td>${item.time}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<button type="button" style="float: right;" class="btn btn-primary btn-icon-split" onclick="fnExcelReport('usingLogList','usingLog');">
		<span class="icon text-white-50"><i class="fas fa-download fa-sm text-white-50"></i></span>
		<span class="text">Excel</span>
	</button>

<script
		src="${pageContext.request.contextPath}/resource/js/table/table.js"></script>
</body>
</html>
