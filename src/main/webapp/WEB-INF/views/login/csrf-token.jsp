<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>CSRF-Token</title>
<head>
	<!-- POST 방식 ajax를 사용할 때, 403 에러를 막기 위해 csrf 토큰 처리 -->
	<meta id="_csrf" name="_csrf" content="${_csrf.token}" />
	<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}" />
	<script
	src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>
	<script>
		$(document).ready(function(){
		    var token = $("meta[name='_csrf']").attr("content");
		    var header = $("meta[name='_csrf_header']").attr("content");
		    $(document).ajaxSend(function(e, xhr, options) {
		        xhr.setRequestHeader(header, token);
		    });
		});
	</script>
</head>
<body>
	
</body>
</html>
