<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<body>
<!-- contextPath -->
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

<!-- POST 방식 403 에러를 막기 위해 csrf 토큰 처리 -->
<%@ include file="/WEB-INF/views/include/csrf-token.jsp"%>

<!-- Topbar -->
<nav
	class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

<!-- Sidebar Toggle (Topbar) -->
<button id="sidebarToggleTop"
	class="btn btn-link d-md-none rounded-circle mr-3">
	<i class="fa fa-bars"></i>
</button>

<!-- Topbar Navbar -->
<ul class="navbar-nav ml-auto">
	<!-- Nav Item - User Information -->
	<li class="nav-item dropdown no-arrow">
		<a
			class="nav-link dropdown-toggle" href="#" id="userDropdown"
			role="button" data-toggle="dropdown" aria-haspopup="true"
			aria-expanded="false">
		<span
			class="mr-2 d-none d-lg-inline text-gray-600 small">
			<span style="font-size:19px;"><b>${member.name}</b>(${member.member_id}) </span>
			<span style="font-size:15px;">님, 환영합니다</span> 
		</span>
		<c:choose>
			<c:when test="${member.profile_img != null}">
				<img src="${pageContext.request.contextPath}/user/${member.member_id}/profileImg/${member.profile_img}" width="40px">
			</c:when>
			<c:otherwise>
				<img src="${pageContext.request.contextPath}/resource/img/user.png" width="40px">
			</c:otherwise>
		</c:choose>
	</a> <!-- Dropdown - User Information -->
		<div
			class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
			aria-labelledby="userDropdown">
			<a class="dropdown-item" href="#" data-toggle="modal"
				data-target="#memberInfoModal"> 
				<i class="fas fa-edit fa-sm fa-fw mr-2 text-gray-400"></i>
				정보 수정
			</a>
			<a class="dropdown-item" href="#" data-toggle="modal"
				data-target="#logoutModal">  
				<i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
				로그아웃
			</a>
		</div>
	</li>
</ul>
</nav>
<!-- End of Topbar -->

<!-- 로그아웃 Modal include-->
<%@ include file="logout.jsp"%>

<!-- 회원정보 수정 Modal include -->
<%@ include file="member_info.jsp"%>
</body>
</html>