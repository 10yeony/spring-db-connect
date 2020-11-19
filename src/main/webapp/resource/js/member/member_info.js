var contextPath = '';

$(function() {
	contextPath = getContextPath();

	$('#edit_present_pwd').keyup(function(){
		$.ajax({
			url : contextPath + "/ableToEdit",
			type : "POST",
			data : "pwd=" + $('#edit_present_pwd').val(),
			async: false,
			
			success : function(result) {
				if(result == 'true'){
					$('#pwdCorrect').attr('style', 'display:block');
					$('#pwdNotExist').attr('style', 'display:none');
					$('#pwdNotCorrect').attr('style', 'display:none');
				}else{
					$('#pwdNotCorrect').attr('style', 'display:block');
					$('#pwdNotExist').attr('style', 'display:none');
					$('#pwdCorrect').attr('style', 'display:none');
				}
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		});//ajax
	}); //keyup
}); //ready

function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}
