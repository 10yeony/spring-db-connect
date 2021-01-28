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
	<input type="hidden" id="show_approval" value="${approval}">
	<select class="form-control" id="approvalSelect" style="margin-right:3px;">
		<option value="">승인 여부</option>
		<option value="true">가입 승인</option>
		<option value="false">가입 미승인</option>
	</select>
</body>
</html>
