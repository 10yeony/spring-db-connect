	$(function() {
		/* 로그인 폼을 제출하기 전 아이디와 비밀번호가 일치하는지 검사함 */
		$('#loginBtn').click(function(){
			$.ajax({
				//요청
				type: "POST",
				url: "/login", 
				dataType: "json",
				data: {
					member_id: $('#login_member_id').val(),
					pwd: $('#login_pwd').val()
				},
				
				/* 데이터를 전송하기 전에 헤더에 csrf 값을 설정(이거 안하면 403 에러) */
				beforeSend : function(xhr){
					var $token = $("#token");
					xhr.setRequestHeader($token.data("token-name"), $token.val());
				},
				
				//응답
				success:function(response){  
					if(response.code == "200"){ // 정상 처리 된 경우
						window.location = response.item.url; //이전페이지로 돌아가기
					} else {
						alert(response.message);
						$('#login_member_id').val('');
						$('#login_pwd').val('');
						$('login_member_id').focus();
					}
				},
				error : function(request, status, error) {
					alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
				}
			}); //ajax
		}); //click
	}); //ready