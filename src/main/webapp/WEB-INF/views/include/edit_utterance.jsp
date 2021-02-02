<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <script src="${pageContext.request.contextPath}/resource/js/data/edit_utterance.js"></script>
</head>
<!-- Logout Modal-->
<div class="modal fade" id="editUtteranceModal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">전사데이터 수정 ${utteranceId}</h5>
                <button class="close" type="button" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">x</span>
                </button>
            </div>
            <form id="editUtteranceForm" method="POST">
                <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
                <div class="modal-body" id="editArea">
                </div>
            </form>
            <div class="modal-footer">
                <button class="btn btn-secondary" id="exitModal" data-dismiss="modal">취소</button>
                <button class="btn btn-primary" id="editUtteranceBtn" style="float:right;">수정</button>
            </div>
        </div>
    </div>
</div>