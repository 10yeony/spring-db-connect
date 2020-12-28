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
				<th>아이디</th>
				<th>이름</th>
				<th>이메일</th>
				<th>연락처</th>
				<th>최종 로그인</th>
				<th>가입 승인</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result}" var="item" varStatus="status">
				<tr>
					<td>${item.row_num}</td>
					<td><a href="${pageContext.request.contextPath}/getMemberByAdmin?member_id=${item.member_id}">${item.member_id}</a></td>
					<td><a href="${pageContext.request.contextPath}/getMemberByAdmin?member_id=${item.member_id}">${item.name}</a></td>
					<td>${item.email}</td>
					<td>${item.phone}</td>
					<td>${item.login_time}</td>
					<td>
						<c:if test="${item.approval == 'true'}">
							승인
						</c:if>
						<c:if test="${item.approval == 'false'}">
							<select class="form-control" onchange="approval('${item.member_id}')">
								<option <c:if test="${item.approval == 'false'}">selected</c:if>>미승인</option>
								<option>승인</option>
							</select>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script 
		src="${pageContext.request.contextPath}/resource/js/member/getMember.js"></script>
</body>
</html>
