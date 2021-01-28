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
	<input type="hidden" id="show_search_logType" value="${search_logType}">
	<input type="hidden" id="show_search_memberId" value="${search_memberId}">
	<input type="hidden" id="show_search_usingList" value="${search_usingList}">
	<input type="hidden" id="show_search_ipAddr" value="${search_ipAddr}">
	<input type="hidden" id="show_search_accessTime" value="${search_accessTime}">
	<select class="form-control" id="logSelect" style="margin-right:3px;">
		<option value="all">전체</option>
		<option value="memberId">아이디</option>
		<option value="usingList">사용 내역</option>
		<option value="ipAddr">IP 주소</option>
		<option value="accessTime">접속 시간</option>
	</select>
</body>
</html>
