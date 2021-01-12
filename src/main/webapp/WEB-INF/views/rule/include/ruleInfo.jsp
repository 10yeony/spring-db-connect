<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	th, td{
		padding-bottom: 15px;
	}
</style>
</head>
<body>
<div class="card shadow mb-4">
	<div class="card-body">
		<table width="100%">
			<tr>
				<th>대분류</th>
				<td>
					<%@ include file="/WEB-INF/views/rule/include/topLevel.jsp"%>
				</td>
			</tr>
			<tr>
				<th>중분류</th>
				<td>
					<%@ include file="/WEB-INF/views/rule/include/middleLevel.jsp"%>
				</td>
			</tr>
			<tr>
				<th style="padding-top:5px;">종류</th>
				<td style="padding-bottom:10px;">
					<input type="radio" name="rule_type" value="method" <c:if test="${rule.rule_type eq 'method'}">checked</c:if>> 
					<span style="cursor:pointer;" onclick="document.getElementsByName('rule_type')[0].checked = true">&nbsp;메서드</span>&nbsp;&nbsp;
					<input type="radio" name="rule_type" value="sql" <c:if test="${rule.rule_type eq 'sql'}">checked</c:if>> 
					<span style="cursor:pointer;" onclick="document.getElementsByName('rule_type')[1].checked = true">&nbsp;SQL</span>
				</td>
			</tr>
			<tr>
				<th>이름</th>
				<td>
					<input type="text" id="ruleInfoName" class="form-control" value="${rule.bottom_level_name}"
						placeholder="30자 이하로 입력하세요.">
				</td>
			</tr>
			<tr>
				<th>설명</th>
				<td>
					<textarea id="ruleInfoDesc" class="form-control" rows="3" style="resize:none;">${rule.description}</textarea>
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>