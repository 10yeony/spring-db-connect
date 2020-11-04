<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Utterance</title>
</head>
<body>
<h2>Title : ${metadata.title},  creator : ${metadata.creator}</h2><br/>
<table border="1" width="1500">
    <tr><th>Id</th><th>Form</th></tr>
    <c:forEach items="${utterances}" var="item" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td>${item.form}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
