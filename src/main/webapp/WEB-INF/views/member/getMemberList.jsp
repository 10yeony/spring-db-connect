<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
	<script src="http://code.jquery.com/jquery-1.4.4.js"></script>
	
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SDTM</title>

    <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css" rel="stylesheet">
	
	<script 
		src="${pageContext.request.contextPath}/resource/js/member/getMemberList.js"></script>
</head>

<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- 사이드바 include-->
    <%@ include file="../include/sidebar.jsp"%>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <!-- 이 부분만 바꿔주면 됩니다 -->
        <div id="content">
            <!-- 툴바 include -->
            <%@ include file="../include/toolbar.jsp"%>
            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-2 text-gray-800">
                    	<b>사용자 관리</b>
                    	<span style="font-size:18px;">(${selectedRole})</span>
                    </h1>
                </div>

                <!-- Page Body -->
                <div class="card shadow mb-4">
                    <div class="card-body">
                    	<div class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100">
							<select class="form-control" id="roleSelect" style="margin-right:3px;">
								<option>권한 선택</option>
    							<option value="ALL">전체</option>
    							<option value="ROLE_VIEW">데이터 조회</option>
    							<option value="ROLE_INPUT">데이터 입력</option>
    							<option value="ROLE_ADMIN">관리자</option>
  							</select>
							<input type="text" class="form-control bg-light border-0 small" placeholder="Search for..."
								   id="memberSearchText">
							<button class="btn btn-primary" type="button">
								<i class="fas fa-search fa-sm"></i>
							</button>
						</div><br><br>
                        <div class="table-responsive">
                            <table class="table table-bordered" id="memberTable" width="100%" cellspacing="0">
                                <thead>
	                                <tr>
	                                    <th>no.</th>
	                                    <th>아이디</th>
	                                    <th>이름</th>
	                                    <th>이메일</th>
	                                    <th>연락처</th>
	                                </tr>
                                </thead>
                                <tbody>
	                                <c:forEach items="${memberList}" var="item" varStatus="status">
	                                    <tr>
	                                        <td>${status.count}</td>
	                                        <td><a href="${pageContext.request.contextPath}/getMemberByAdmin?member_id=${item.member_id}">${item.member_id}</a></td>
	                                        <td><a href="${pageContext.request.contextPath}/getMemberByAdmin?member_id=${item.member_id}">${item.name}</a></td>
	                                        <td>${item.email}</td>
	                                        <td>${item.phone}</td>
	                                    </tr>
	                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- End of Main Content -->

        <!-- footer include-->
        <%@ include file="../include/footer.jsp"%>

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Bootstrap core JavaScript-->
<script src="${pageContext.request.contextPath}/resource/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="${pageContext.request.contextPath}/resource/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="${pageContext.request.contextPath}/resource/js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script src="${pageContext.request.contextPath}/resource/vendor/chart.js/Chart.min.js"></script>

<!-- Page level custom scripts -->
<script src="${pageContext.request.contextPath}/resource/js/demo/chart-area-demo.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/demo/chart-pie-demo.js"></script>
</body>

</html>
