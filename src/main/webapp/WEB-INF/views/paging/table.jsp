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
	<c:choose>
		<c:when test="${requestUrl == 'getMetadataAndProgram'}">
			<%@ include file="/WEB-INF/views/paging/table/getMetadataAndProgram.jsp"%>
		</c:when>
		
		<c:when test="${requestUrl == 'getJsonLog'}">
			<%@ include file="/WEB-INF/views/paging/table/getJsonLog.jsp"%>
		</c:when>
		
		<c:when test="${requestUrl == 'getUsingLogList'}">
			<%@ include file="/WEB-INF/views/paging/table/getUsingLogList.jsp"%>
		</c:when>
		
		<c:when test="${requestUrl == 'getRuleLogList'}">
			<%@ include file="/WEB-INF/views/paging/table/getRuleLogList.jsp"%>
		</c:when>
		
		<c:when test="${requestUrl == 'getMemberListByAdmin'}">
			<%@ include file="/WEB-INF/views/paging/table/getMemberList.jsp"%>
		</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${requestUrl != 'getMetadataAndProgram'}">
			<button class="btn btn-secondary" type="button" id="backBtn" style="float:left;" onclick="clickBackBtn();">뒤로 가기</button>
			<button type="button" style="float: right;" class="btn btn-primary btn-icon-split" onclick="fnExcelReport('getTableForExcel','usingLog');">
				<span class="icon text-white-50"><i class="fas fa-download fa-sm text-white-50"></i></span>
				<span class="text">Excel</span>
			</button>
		</c:when>
	</c:choose>
</body>

</html>
