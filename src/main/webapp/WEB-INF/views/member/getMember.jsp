<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

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
	src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>
	<script 
		src="${pageContext.request.contextPath}/resource/js/member/getMember.js"></script>
	<script 
		src="${pageContext.request.contextPath}/resource/js/member/editAuthorities.js"></script>

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
                    	<b>사용자 정보</b>
                    	<a href="${pageContext.request.contextPath}/getUsingLogList?data=${thisMember.member_id}&current_page_no=1&count_per_page=10&count_per_list=10&search_word=" style="font-size:0.6em;">사용 기록 조회</a>
                    </h1>
                </div>

                <!-- Page Body -->
                <div class="card shadow mb-4">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="50%" cellspacing="0">
                                <thead></thead>
                                <tbody>
	                                <tr>
	                                    <th>아이디</th>
	                                    <td>
	                                    	<input type="text" id="thisMember_id" class="form-control" 
	                                    		value="${thisMember.member_id}" disabled>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th>이름</th>
	                                    <td>
	                                    	<input type="text" id="thisName" class="form-control" 
	                                    		value="${thisMember.name}" disabled>
	                                    </td>
	                                </tr>
	                                <tr>
	                                	<th>이메일</th>
	                                	<td>
	                                		<input type="text" class="form-control" 
	                                			value="${thisMember.email}" disabled>
	                                	</td>
	                                </tr>
	                                <tr>
	                                    <th>연락처</th>
	                                    <td>
	                                    	<input type="text" class="form-control" 
	                                    		value="${thisMember.phone}" disabled>
	                                    </td>
	                                </tr>
	                            	<tr>
	                            		<th>가입일</th>
	                            		<td>${thisMember.join_date}</td>
	                            	</tr>
									<tr>
										<th>가입 승인</th>
										<td>
											<c:if test="${thisMember.approval eq 'true'}">
												승인 완료
											</c:if>
											<c:if test="${thisMember.approval eq 'false'}">
												<button type="button" onclick="approval()" class="btn btn-primary btn-icon-split">
													<span class="text">가입 승인하기</span>
												</button>
											</c:if>
										</td>
									</tr>
									<tr>
	                                	<th>현재 권한</th>
	                                	<td>
		                                	<c:forEach items="${thisMember.authorities}" var="item" varStatus="status">
						                        <c:if test="${item=='ROLE_VIEW'}">
							                        <span style="margin-right:3px">
							                           	데이터 조회 권한 |
							                        </span>
						                        </c:if>
						                        <c:if test="${item=='ROLE_INPUT'}">
						                        	<span style="margin-right:3px">
						                           		데이터 입력 권한 |
						                           	</span>
						                        </c:if>
						                        <c:if test="${item=='ROLE_ADMIN'}">
													<span style="margin-right:3px">
														관리자 권한 |	
													</span>
						                        </c:if>
						                    </c:forEach>
						                    <a href="javascript:void(0);" data-toggle="modal" data-target="#editAuthoritiesModal">권한 편집</a>
			                            </td>
	                            	</tr>
                                </tbody>
                            </table>
                           
                            <div>
						        		<button class="btn btn-danger" type="button" id="deleteBtnByAdmin" style="float:right; margin-right:10px;">회원탈퇴</button>
										<button class="btn btn-secondary" type="button" id="backToMemberList"
											style="float:left;">뒤로 가기</button>
						        </div>
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

<!-- 회원탈퇴 Modal include -->
<%@ include file="editAuthorities.jsp"%>
</body>
</html>
