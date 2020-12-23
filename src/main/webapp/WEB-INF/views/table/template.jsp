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
	<!-- requestMapping -->
	<input type="hidden" id="requestUrl" value="${requestUrl}">

	<!-- data type, count_per_page, count_per_list -->
	<input type="hidden" id="show_data_type" value="${data}">
	<input type="hidden" id="show_count_per_page" value="${count_per_page}">
	<input type="hidden" id="show_count_per_list" value="${count_per_list}">

	<!-- search_word -->
	<input type="hidden" id="show_search_word" value="${search_word}">

	<div class="d-sm-inline-block form-inline ml-md-3 my-2 my-md-0 mw-100">
		<!-- Select Area -->
  		<%@ include file="/WEB-INF/views/table/select.jsp"%>
  		
  		<!-- Search Area -->
		<input type="text" class="form-control bg-light border-0 small" style="width:300px;"
			placeholder="Search for..." id="inputSearchText">
		<button class="btn btn-primary" type="button" id="inputSearchButton">
			<i class="fas fa-search fa-sm"></i>
		</button>
	</div><br/>
	
	<!-- Select View -->
	<div style="display:inline-block; float:right;">
		<input type="radio" name="views" value="10views"> 
		<span id="10viewsSpan" style="cursor: pointer">10개씩 보기</span> 
		<input type="radio" name="views" value="20views" style="margin-left:10px;"> 
		<span id="20viewsSpan" style="cursor: pointer">20개씩 보기</span>
		<input type="radio" name="views" value="30views" style="margin-left:10px;"> 
		<span id="30viewsSpan" style="cursor: pointer">30개씩 보기</span> 
	</div>
	
	<!-- Table -->
	<div class="table-responsive">
		<%@ include file="/WEB-INF/views/table/table.jsp"%>
		${pagination}
		<br/><br/>
	</div>
</body>

</html>
