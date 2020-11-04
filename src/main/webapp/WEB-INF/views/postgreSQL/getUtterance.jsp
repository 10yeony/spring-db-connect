<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Utterance</title>
</head>
<body>
<%--<h2>creator : ${metadata.creator}, Title : ${metadata.title}</h2><br/>--%>
<table border="1">
    <tr width="100"><th>Id</th><th>Form</th></tr>
    <c:forEach items="${result}" var="item" varStatus="status">
        <tr width="1000">
            <td>${status.count}</td>
            <td>${item.form}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
