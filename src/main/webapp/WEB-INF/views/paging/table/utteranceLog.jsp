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
<input type="hidden" id="title" value="문장수정이력관리">
<table id="downTable" class="table table-bordered paging-table" width="100%" cellspacing="0">
    <thead>
    <tr>
        <th>No.</th>
        <th>아이디</th>
        <th>IP 주소</th>
        <th>시간</th>
        <th>기존 문장</th>
        <th>바뀐 문장</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${result}" var="item" varStatus="status">
        <tr>
            <td>${item.row_num}</td>
            <td>${item.member_id}</td>
            <td>${item.ip_addr}</td>
            <td>${item.time}</td>
            <td>${item.before}</td>
            <td>${item.after}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>

</html>
