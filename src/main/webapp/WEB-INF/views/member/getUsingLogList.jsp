<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>JsonLog</title>

    <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css" rel="stylesheet">
	
	<link href="${pageContext.request.contextPath}/resource/css/paging.css" rel="stylesheet">
</head>

<body id="page-top">
<!-- count_per_page, count_per_list -->
<input type="hidden" id="show_count_per_page" value="${count_per_page}">
<input type="hidden" id="show_count_per_list" value="${count_per_list}">

<!-- program_title, subtitle, creator, file_num -->
<input type="hidden" id="show_search_word" value="${search_word}">

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
                    	<b>사용 기록 조회</b>
                    	<span style="font-size:18px;">(${searchResult} ${totalCount}건)</span>
                    	<c:if test="${searchResult != null}">
                    		<a href="${pageContext.request.contextPath}/getUsingLog?function_name=getUsingLog&current_page_no=1&count_per_page=10&count_per_list=10&search_word=" 
                    			style="font-size:0.6em;">전체보기</a>
                    	</c:if>
                    </h1>
                </div>

                <!-- Page Body -->
                <div class="card shadow mb-4">
                    <div class="card-body"><br/>
                    	<div class="d-sm-inline-block form-inline ml-md-3 my-2 my-md-0 mw-100">
								<input type="text" class="form-control bg-light border-0 small" style="width:300px;"
									placeholder="Search for..." id="inputSearchText">
								<button class="btn btn-primary" type="button" id="inputSearchButton">
									<i class="fas fa-search fa-sm"></i>
								</button>
							</div><br/>
							<div style="display:inline-block; float:right;">
									<input type="radio" name="views" value="10views"> 
									<span id="10viewsSpan" style="cursor: pointer">10개씩 보기</span> 
									<input type="radio" name="views" value="20views" style="margin-left:10px;"> 
									<span id="20viewsSpan" style="cursor: pointer">20개씩 보기</span>
									<input type="radio" name="views" value="30views" style="margin-left:10px;"> 
									<span id="30viewsSpan" style="cursor: pointer">30개씩 보기</span> 
							</div>
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                    <tr>
                                    	 <th>no.</th>
                                        <th>아이디</th>
                                        <th>사용 내용</th>
                                        <th>IP 주소</th>
                                        <th>접속 시간</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${result}" var="item" varStatus="status">
                                        <tr>
                                            <td>${item.row_num}</td>
                                            <td><a href="getMemberByAdmin?member_id=${item.member_id}">${item.member_id}</a></td>
                                            <td>${item.content}</td>
                                            <td>${item.ip_addr}</td>
                                            <td>${item.time}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            ${pagination}
                            <br/><br/>
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

<script 
	src="${pageContext.request.contextPath}/resource/js/member/getUsingLogList.js"></script>

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
