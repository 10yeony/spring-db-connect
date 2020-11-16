	var loginResult = '';
	$(function() {
		/* 로그인 폼을 제출하기 전 아이디와 비밀번호가 일치하는지 검사함 */
		$('#loginForm').submit(function(){
			var username = $('#login_username').val();
			var password = $('#login_password').val();
			
			$.ajax({
				//요청
				type: "POST",
				url: "isUser", 
				data: $('#loginForm').serialize(),
				async: false,
				
				//응답
				success:function(result){  
					loginResult = result;
				},
				error : function(request, status, error) {
					alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
				}
			}); //ajax
			
			if(loginResult == 'none'){ //로그인 실패시
				alert("아이디 또는 비밀번호가 틀렸습니다.");
				$('#login_username').val('');
				$('#login_password').val('');
				$('login_username').focus();
				return false;
			}
		}); //submit
	}); //ready