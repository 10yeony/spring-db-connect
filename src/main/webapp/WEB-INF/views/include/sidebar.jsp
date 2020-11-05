<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="navigator.jsp">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">SDTM<sup>2</sup></div>
    </a>

    <!-- Divider -->
    <hr class="sidebar-divider my-0">

    <!-- Nav Item - Dashboard -->
    <li class="nav-item active">
        <a class="nav-link" href="">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Dashboard</span></a>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider">

    <!-- Heading -->
    <div class="sidebar-heading">
        List
    </div>

    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#elasticSearch" aria-expanded="true" aria-controls="elasticSearch">
            <i class="fas fa-fw fa-folder"></i>
            <span>Elasticsearch</span>
        </a>
        <div id="elasticSearch" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="${pageContext.request.contextPath}/getElasticIndex">특정 인덱스 불러오기</a>
            </div>
        </div>
    </li>

    <!-- Nav Item - Utilities Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#mongoDB" aria-expanded="true" aria-controls="mongoDB">
            <i class="fas fa-fw fa-folder"></i>
            <span>MongoDB</span>
        </a>
        <div id="mongoDB" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="${pageContext.request.contextPath}/insertElasticIndexIntoMongo">Elasticsearch 인덱스 저장</a>
                <a class="collapse-item" href="${pageContext.request.contextPath}/insertJSONData">JSON 파일 저장</a>
                <a class="collapse-item" href="${pageContext.request.contextPath}/getMongoCollection">특정 컬렉션 가져오기</a>
            </div>
        </div>
    </li>

    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#postgreSQL" aria-expanded="true" aria-controls="postgreSQL">
            <i class="fas fa-fw fa-folder"></i>
            <span>PostgreSQL</span>
        </a>
        <div id="postgreSQL" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <h6 class="collapse-header">Login Screens:</h6>
                <a class="collapse-item" href="${pageContext.request.contextPath}/insertElasticIndexIntoPostgre">Elasticsearch 인덱스 저장</a>
                <a class="collapse-item" href="${pageContext.request.contextPath}/insertJSONIntoPostgre">JSON 저장</a>
                <a class="collapse-item" href="${pageContext.request.contextPath}/insertXlsxIntoPostgre">xlsx 저장</a>
                <a class="collapse-item" href="${pageContext.request.contextPath}/getMetadataAndProgram">Metadata 목록</a>
            </div>
        </div>
    </li>

    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#report" aria-expanded="true" aria-controls="report">
            <i class="fas fa-fw fa-folder"></i>
            <span>보고서 파일 생성</span>
        </a>
        <div id="report" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="${pageContext.request.contextPath}/report/docx">docx 파일</a>
                <a class="collapse-item" href="${pageContext.request.contextPath}/report/xlsx">xlsx 파일</a>
            </div>
        </div>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider d-none d-md-block">

    <!-- Sidebar Toggler (Sidebar) -->
    <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
    </div>
</ul>
<!-- End of Sidebar -->