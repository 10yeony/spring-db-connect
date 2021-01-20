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
<input type="hidden" id="title" value="룰 기록 조회">
	<table id="downTable" class="table table-bordered paging-table" width="100%" cellspacing="0">
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
						<div>
							<b>
								<c:choose>
									<c:when test="${fn:contains(item.content, '총')}">
										<a href="${pageContext.request.contextPath}/getRuleLogList?data=${item.using_log_no}&current_page_no=1&count_per_page=10&count_per_list=10&search_word=">${item.content}</a>
									</c:when>
									<c:otherwise>
										${item.content}
									</c:otherwise>
								</c:choose>
							</b>
						</div>
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
	<c:forEach items="${result}" var="item" varStatus="status">
		<c:choose>
			<c:when test="${fn:contains(item.content, '총') && data != 0}">
				<br/>
				<h5><b>세부사항</b></h5>
				<table class="table table-bordered paging-table" width="100%" cellspacing="0">
					<thead>
						<tr>
							<c:choose>
								<c:when test="${fn:contains(item.content, '실행')}">
									<th>
										<input type="checkbox" onchange="checkAllRuleLogDetailInThisPage(event);">
									</th>
								</c:when>
							</c:choose>
							<th>no.</th>
							<th>사용 내역</th>
							<th>대분류</th>
							<th>중분류</th>
							<th>소분류</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ruleLogDetail}" var="subItem" varStatus="subStatus">
							<tr>
								<c:choose>
									<c:when test="${fn:contains(subItem.content, '실행')}">
										<td><input type="checkbox" class="selectItem" name="통일해서 쓰기..." value="${subItem.bottom_level_id}"></td>
									</c:when>
								</c:choose>
								<td>${subStatus.count}</td>
								<td>${subItem.content}</td>
								<td>
									<a href="${pageContext.request.contextPath}/rule/ruleList/${subItem.top_level_id}/0/0">
										${subItem.top_level_name}
									</a>
								</td>
								<td>
									<a href="${pageContext.request.contextPath}/rule/ruleList/${subItem.top_level_id}/${subItem.middle_level_id}/0">
										${subItem.middle_level_name}
									</a>
								</td>
								<td>
									<c:choose>
										<c:when test="${fn:contains(item.content, '삭제')}">
											${subItem.bottom_level_name}
										</c:when>
										<c:otherwise>
											<a href="${pageContext.request.contextPath}/rule/ruleList/${subItem.top_level_id}/${subItem.middle_level_id}/${subItem.bottom_level_id}">
												${subItem.bottom_level_name}
											</a> 
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
		</c:choose>
	</c:forEach>
<script
	src="${pageContext.request.contextPath}/resource/js/table/table.js"></script>
</body>
</html>
