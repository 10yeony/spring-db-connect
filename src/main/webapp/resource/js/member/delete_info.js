var contextPath = '';

$(function() {
	contextPath = getContextPath();

	$('#deleteBtn').click(function(){
		$.ajax({
			url : contextPath + "/ableToEdit",
			type : "POST",
			data : "pwd=" + $('#delete_pwd').val(),
			async: false,
			
			success : function(result) {
				if(result == 'true'){
					deleteMember();
					location.href = contextPath + "/login";
				}else{
					alert("비밀번호를 잘못 입력하셨습니다.");
				}
			},
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		});//ajax
	});//click
}); //ready

function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}

function deleteMember(){
	$.ajax({
		url : contextPath + "/deleteMember",
		type : "GET",
		async: false,
		
		success : function(result) {
			if(result == 'true'){
				alert("정상적으로 탈퇴되었습니다.");
			}else{
				alert("회원탈퇴에 실패하였습니다.");
			}
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	});
}