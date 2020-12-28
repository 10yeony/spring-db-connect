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
		<option value="ALL">전체 권한</option>
		<option value="ROLE_VIEW">데이터 조회 권한</option>
		<option value="ROLE_INPUT">데이터 입력 권한</option>
		<option value="ROLE_ADMIN">관리자 권한</option>
	</select>
</body>
</html>
