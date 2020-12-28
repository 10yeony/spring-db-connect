var contextPath = '';

$(function(){
	/* Context Path */
	contextPath = $('#contextPath').val();
	
	/* 비밀번호 검사 표시 */
	var pwdOK = false;
	
	/* 정규표현식을 이용한 유효성 검사 */
	var pwdRegex =  /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,12}$/; //비밀번호가 적합한지 검사
	
	/* 현재 비밀번호가 일치하는지 확인 */
	$('#edit_present_pwd').keyup(function(){
		if($('#edit_present_pwd').val() === ''){
			$('#edit_pwd_pwdCorrect').attr('style', 'display:none');
			$('#edit_pwd_pwdNotExist').attr('style', 'display:block');
			$('#edit_pwd_pwdNotCorrect').attr('style', 'display:none');
			return false;
		}
		
		let pwd = encodeURIComponent($('#edit_present_pwd').val());

		$.ajax({
			url : contextPath + "/ableToEdit",
			type : "POST",
			data : "pwd=" + pwd,
			async: false,
			
			success : function(result) {
				if(result == 'true'){
					$('#edit_pwd_pwdCorrect').attr('style', 'display:block');
					$('#edit_pwd_pwdNotExist').attr('style', 'display:none');
					$('#edit_pwd_pwdNotCorrect').attr('style', 'display:none');
					$('#edit_present_pwd').attr('disabled', 'true'); //비밀번호가 일치할 경우 수정하지 못하도록 막음
					pwdOK = true;
				}else{
					$('#edit_pwd_pwdNotCorrect').attr('style', 'display:block');
					$('#edit_pwd_pwdNotExist').attr('style', 'display:none');
					$('#edit_pwd_pwdCorrect').attr('style', 'display:none');
				}
			},
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		});//ajax
	}); //keyup
	
	/* 새 비밀번호 입력 검사 */
	$('#edit_new_pwd').keyup(function(){
		let pwd = $('#edit_new_pwd').val();
		let pwdCheck = $('#edit_new_pwd_check').val();
		
		if(pwd == ''){
			$('#edit_new_pwd_input_alert').attr('style', 'display:block');
			$('#edit_new_pwd_different_alert').attr('style', 'display:none');
			$('#edit_new_pwd_disable_alert').attr('style', 'display:none');
			$('#edit_new_pwd_same_alert').attr('style', 'display:none');
			$('#edit_new_pwd').focus();
			return false;
		}else if(!pwdRegex.test(pwd)){
			$('#edit_new_pwd_input_alert').attr('style', 'display:none');
			$('#edit_new_pwd_different_alert').attr('style', 'display:none');
			$('#edit_new_pwd_disable_alert').attr('style', 'display:block');
			$('#edit_new_pwd_same_alert').attr('style', 'display:none');
			$('#edit_new_pwd').focus();
			return false;
		}else{
			$('#edit_new_pwd_input_alert').attr('style', 'display:none');
			$('#edit_new_pwd_different_alert').attr('style', 'display:none');
			$('#edit_new_pwd_disable_alert').attr('style', 'display:none');
			$('#edit_new_pwd_same_alert').attr('style', 'display:none');
		}
	}); //keyup
	
	
	/* 새 비밀번호 일치 검사(비밀번호  확인) */
	$('#edit_new_pwd_check').keyup(function(){
		let pwd = $('#edit_new_pwd').val();
		let pwdCheck = $('#edit_new_pwd_check').val();
		
		if(pwd != pwdCheck){
			$('#edit_new_pwd_input_alert').attr('style', 'display:none');
			$('#edit_new_pwd_different_alert').attr('style', 'display:block');
			$('#edit_new_pwd_disable_alert').attr('style', 'display:none');
			$('#edit_new_pwd_same_alert').attr('style', 'display:none');
			return false;
		}else if(pwd === ''){
			$('#edit_new_pwd_input_alert').attr('style', 'display:block');
			$('#edit_new_pwd_different_alert').attr('style', 'display:none');
			$('#edit_new_pwd_disable_alert').attr('style', 'display:none');
			$('#edit_new_pwd_same_alert').attr('style', 'display:none');
			$('#edit_new_pwd').focus();
			return false;
		}else {
			$('#edit_new_pwd_input_alert').attr('style', 'display:none');
			$('#edit_new_pwd_different_alert').attr('style', 'display:none');
			$('#edit_new_pwd_disable_alert').attr('style', 'display:none');
			$('#edit_new_pwd_same_alert').attr('style', 'display:block');
		}
	}); //keyup
	
	
	/* 비밀번호 수정 제출 */
	$('#editPwdBtn').click(function(){
	
		/* 제출 전 입력폼 검사 */
		let present_pwd = $('#edit_present_pwd').val();
		let pwd = $('#edit_new_pwd').val();
		let pwdCheck = $('#edit_new_pwd_check').val();
		
		/* 공백 검사 */
		if(present_pwd == '' || pwd == '' || pwdCheck == ''){
			alert("빈칸을 입력하세요.");
			return false;
		}
		
		/* 비밀번호 관련 검사 */
		if(pwdOK == false){
			alert("현재 비밀번호가 틀렸습니다.");
			return false;
		}else if(!pwdRegex.test(pwd)){
			alert("영어/숫자/특수문자를 포함한 8자 이상 12자 이하의 새 비밀번호를 입력하세요.");
			return false;
		}else if(pwd != pwdCheck){
			alert("새 비밀번호가 일치하지 않습니다.");
			return false;
		}
		
		/* ajax로 제출 */
		$.ajax({
			url : contextPath + "/updatePwd",
			type : "POST",
			data : 'pwd=' + pwd,
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			async: false,
			
			success : function(data) {
				if(data == 'true'){
					alert('비밀번호를 수정하였습니다. 다시 로그인해주세요.');
					location.href = contextPath + "/login";
				}
			},
			
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		});
	});
});
