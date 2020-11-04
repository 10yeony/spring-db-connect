<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PostgreSQL Metadata 가져오기</title>
</head>
<body>
<h2>PostgreSQL MetaData 가져오기</h2><br/>
<table border="1" width="1500">
	<tr>
		<th>Id</th>
		<th>Title</th>
		<th>Subtitle</th>
		<th>Creator</th>
		<th>Annotation Level</th>
		<th>Year</th>
		<th>Sampling</th>
		<th>File_num</th>
		<th>Category</th>
		<th>Distributor</th>
		<th>Relation</th>
	</tr>
	<c:forEach items="${result}" var="item">
		<tr>
			<td>${item.id}</td>
			<td>${item.program.title}</td>
			<td><a href="getUtteranceTable/${item.id}">${item.program.subtitle}</a></td>
			<td>${item.creator}</td>
			<td>${item.annotation_level}</td>
			<td>${item.year}</td>
			<td>${item.sampling}</td>
			<td>${item.title}</td>
			<td>${item.category}</td>
			<td>${item.distributor}</td>
			<td>${item.relation}</td>
		</tr>
	</c:forEach>
</table>
<br><br>
<a href="index.jsp">처음으로</a>
</body>
</html>