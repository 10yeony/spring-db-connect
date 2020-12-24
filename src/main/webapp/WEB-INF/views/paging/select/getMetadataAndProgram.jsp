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
	<select class="form-control" id="dataSelect" style="margin-right:3px;">
		<option value="all">전체 데이터</option>
		<option value="korean_lecture">한국어 강의</option>
		<option value="meeting_audio">회의 음성</option>
		<option value="customer_reception">고객 응대</option>
		<option value="counsel_audio">상담 음성</option>
	</select>
</body>
</html>
