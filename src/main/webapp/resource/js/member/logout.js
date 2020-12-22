var contextPath = '';

$(function(){
	/* Context Path */
	contextPath = getContextPath();
	
	$('#logoutFrm').submit(function(){
		recordLogout();
	});
}); //ready

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}

/* 사용 로그에 로그아웃을 기록함 */
function recordLogout(){
	$.ajax({
		//요청
		type: "GET",
		url: contextPath + "/recordLogout", 
			
		//응답
		success : function(){
			
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}