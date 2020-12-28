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
						<div><b>${item.content}</b></div>
						<c:if test="${item.top_level_name != null}">
							<div>
								대분류 : 
								<c:choose>
									<c:when test="${fn:contains(item.content, '대분류 삭제')}">
										${item.top_level_name}
									</c:when>
									<c:otherwise>
										<a href="${pageContext.request.contextPath}/rule/ruleList/${item.top_level_id}/0/0">
											${item.top_level_name}
										</a>
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>
						<c:if test="${item.middle_level_name != null}">
							<div>
								중분류 : 
								<c:choose>
									<c:when test="${fn:contains(item.content, '중분류 삭제')}">
										${item.middle_level_name}
									</c:when>
									<c:otherwise>
										<a href="${pageContext.request.contextPath}/rule/ruleList/${item.top_level_id}/${item.middle_level_id}/0">
											${item.middle_level_name}
										</a>	
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>
						<c:if test="${item.bottom_level_name != null}">
							<div>
								소분류 : 
								<c:choose>
									<c:when test="${fn:contains(item.content, '소분류 삭제')}">
										${item.bottom_level_name}
									</c:when>
									<c:otherwise>
										<a href="${pageContext.request.contextPath}/rule/ruleList/${item.top_level_id}/${item.middle_level_id}/${item.bottom_level_id}">
											${item.bottom_level_name}
										</a> 
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>
						<c:if test="${item.library_file_name != null}">
							<div>
								라이브러리 : ${item.library_file_name} 
							</div>
							<div>
								<c:set var="fileName" value="${fn:split(item.library_file_name, '.')}" />
								<c:choose>
									<c:when test="${fileName[fn:length(fileName)-1] == 'class'}">
										패키지명 : ${item.class_package}
									</c:when>
								</c:choose>
							</div>
						</c:if>
					</td>
					<td>${item.ip_addr}</td>
					<td>${item.time}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<script
	src="${pageContext.request.contextPath}/resource/js/table/table.js"></script>
</body>
</html>
