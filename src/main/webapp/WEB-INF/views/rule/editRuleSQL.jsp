<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SDTM</title>

    <!-- Custom fonts for this template-->
    <link
            href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css"
            rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resource/css/paging.css" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link
            href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css"
            rel="stylesheet">
    <script
            src="${pageContext.request.contextPath}/resource/vendor/codemirror/codemirror.js"></script>
    <link
            rel="stylesheet" href="${pageContext.request.contextPath}/resource/vendor/codemirror/codemirror.css">
    <link
            rel="stylesheet" href="https://codemirror.net/theme/duotone-light.css">
    <script
            src="${pageContext.request.contextPath}/resource/vendor/codemirror/sql.js"></script>

    <!-- w3 css -->
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

    <!-- 로딩 이미지 -->
    <style>
        .CodeMirror{
            font-size: 1.5em;
            font-family: Nunito,-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
        }

        .loading {
            position: absolute;
            top: 50%;
            left: 50%;
            width: 80px;
            height: 80px;
            margin: -50px 0 0 -50px;
        }
    </style>
</head>

<body id="page-top">
<form id="deleteRuleFrm">
    <input type="hidden" name="level" value="bottom">
    <input type="hidden" name="top_level_id" value="${rule.top_level_id}">
    <input type="hidden" name="top_level_name" value="${rule.top_level_name}">
    <input type="hidden" name="middle_level_id" value="${rule.middle_level_id}">
    <input type="hidden" name="middle_level_name" value="${rule.middle_level_name}">
    <input type="hidden" name="bottom_level_id" value="${rule.bottom_level_id}">
    <input type="hidden" name="bottom_level_name" value="${rule.bottom_level_name}">
</form>

<!-- Page Wrapper -->
<div id="wrapper">
    <input name="bottom_level_id" type="hidden" id="bottom_level_id"
           value="${rule.bottom_level_id}">
    <!-- POST 방식 403 에러를 막기 위해 csrf 토큰 처리 -->
    <%@ include file="/WEB-INF/views/include/csrf-token.jsp"%>

    <!-- 로딩 화면 -->
    <div id="loadingArea" class="w3-modal w3-animate-opacity">
        <img class="loading" width="100px"
             src="${pageContext.request.contextPath}/resource/img/loading.gif">
    </div>

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
                <div
                        class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-2 text-gray-800">
                    	<b>룰 작성 - SQL</b>
                    	<a href="${pageContext.request.contextPath}/rule/getRuleVersionList?data=${rule.bottom_level_id}&current_page_no=1&count_per_page=10&count_per_list=10&search_word=" style="font-size:0.6em;">버전 관리</a>
                    </h1>
                </div>
					
					<!-- 룰 정보 -->
					 <%@ include file="/WEB-INF/views/rule/include/ruleInfo.jsp"%>

                <!-- Page Body -->
                <div class="card shadow mb-4">
                    <div class="card-body">
                        <b>실행할 쿼리를 입력해주세요.</b>
                        <br><br>
                        <textarea id="query" name="query" cols="170" rows="20"
                                  style="height: 300px;">${rule.contents}</textarea><br>
                        <button class="btn btn-danger float-left" type="button" id="deleteRuleBtn">삭제</button>
                        <button type="button" onclick="runRuleSQL()" class="btn btn-primary btn-icon-split float-right">
                            <span class="text">작성</span>
                        <button class="btn btn-secondary" type="button"
                                id="backRuleBtn" style="float: right; margin-right: 5px;">돌아가기</button>
                        </button>
                        <br>
                        <br><hr><br>

                        <div id="show_result_after_update">
                            <b>실행결과</b>
                            <textarea class="form-control" rows="5" style="resize: none;"
                                      readonly></textarea>
                            <div id="select_table" class="card-body"></div>
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
<a class="scroll-to-top rounded" href="#page-top"> <i
        class="fas fa-angle-up"></i>
</a>


<!-- Bootstrap core JavaScript-->
<script
        src="${pageContext.request.contextPath}/resource/vendor/jquery/jquery.min.js"></script>
<script
        src="${pageContext.request.contextPath}/resource/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script
        src="${pageContext.request.contextPath}/resource/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script
        src="${pageContext.request.contextPath}/resource/js/sb-admin-2.min.js"></script>
<script 
		src="${pageContext.request.contextPath}/resource/js/rule/ruleCategory.js"></script>
<script
        src="${pageContext.request.contextPath}/resource/js/rule/editRuleSQL.js"></script>
</body>

</html>