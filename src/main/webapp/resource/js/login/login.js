$(function(){
	/* Context Path */
	var contextPath = getContextPath();
	
	/* 로그인한 뒤 접근했을 경우 */
	var member_login = $('#member_login').val();
	if(member_login != ''){
		location.href = contextPath + "/main";
	}
	
	/* 스프링 시큐리티 로그인 후 성공/실패시 응답을 받음 */
	$('#loginBtn').click(function(){
		
		/* 아이디 비밀번호 공백 여부 체크 */
		if($('#login_member_id').val() === '' || $('#login_pwd').val() === ''){
			alert("빈칸을 채워주세요");
			return false;
		}
		
		$.ajax({
			//요청
			type: "POST",
			url: contextPath + "/loginProcess", 
			data: $('#loginFrm').serialize(),
			async: false,
			
			//응답
			success : function(response){  
				var json = JSON.parse(response);
				if(json.code == "200"){ //로그인 성공
					successPath = json.item.url; //해당되는 페이지(메인)로 가기
					location.href = contextPath + successPath;
				} else { //로그인 실패(아이디, 비밀번호 불일치)
					alert(json.message); 
					$('#login_member_id').val('');
					$('#login_pwd').val('');
					$('#login_member_id').focus();
				}
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		}); //ajax
	}); //click
}); //ready

function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}
