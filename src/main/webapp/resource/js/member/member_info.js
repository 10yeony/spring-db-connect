var contextPath = '';

$(function() {
	
	/* 비밀번호 인증 */
	var pwdOK = false;
	
	/* 이메일 중복검사 */
	var emailCheck = false; //id 중복검사 여부
	var emailOk = false; //id 사용가능 여부
	
	/* 연락처 중복검사 */
	var phoneCheck = false; //id 중복검사 여부
	var phoneOk = false; //id 사용가능 여부
	
	/* 정규표현식을 이용한 유효성 검사 */
   	var emailRegex =  /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    // 이메일이 적합한지 검사할 정규식
    var phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/; //핸드폰 번호 정규식

	/* Context Path */
	contextPath = getContextPath();
	
	/* 비밀번호 변경 클릭시 현재 모달을 사라지게 함 */
	$('#goToMemberEdit').click(function(){
		$('#memberInfoModal').attr('style', 'display:none');
	});

	/* 비밀번호가 일치하는지 확인 */
	$('#edit_pwd').keyup(function(){
		if($('#edit_pwd').val() === ''){
			$('#edit_pwdCorrect').attr('style', 'display:none');
			$('#edit_pwdNotExist').attr('style', 'display:block');
			$('#edit_pwdNotCorrect').attr('style', 'display:none');
			return false;
		}
		
		let pwd = encodeURIComponent($('#edit_pwd').val());
	
		$.ajax({
			url : contextPath + "/ableToEdit",
			type : "POST",
			data : "pwd=" + pwd,
			async: false,
			
			success : function(result) {
				if(result == 'true'){
					pwdOK = true;
					$('#edit_pwdCorrect').attr('style', 'display:block');
					$('#edit_pwdNotExist').attr('style', 'display:none');
					$('#edit_pwdNotCorrect').attr('style', 'display:none');
				}else{
					$('#edit_pwdNotCorrect').attr('style', 'display:block');
					$('#edit_pwdNotExist').attr('style', 'display:none');
					$('#edit_pwdCorrect').attr('style', 'display:none');
				}
			},
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		});//ajax
	}); //keyup
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	/* 이메일 중복검사 */
	$('#edit_emailCheck').click(function() {
		
		/* 세션에 있는 연락처와 현재 입력한 연락처를 비교함 */
		let session_email = $('.session_email').val();
		if($('#edit_email').val() == session_email){
			emailCheck = true;
			emailOk = true;
			$('#edit_email_able_alert').attr('style', 'display:inline-block');
			$('#edit_email_input_alert').attr('style', 'display:none');
			$('#edit_email_disable_alert').attr('style', 'display:none');
			$('#edit_email_check_alert').attr('style', 'display:none');
			return false;
		}
		
		if(!emailRegex.test($('#edit_email').val())){
			alert("이메일 형식이 잘못되었습니다.");
			$('#edit_email').val('');
			$('#edit_email').focus();
			return false;
		}
		
		$.ajax({
			url : contextPath + "/register/check/email",
			type : "POST",
			data : { 
				"email" : $('#edit_email').val() 
			},
			
			success : function(data) {
				if (data == 0 && $.trim($('#edit_email').val()) != '') {
					emailCheck = true;
					emailOk = true;
					/* 사용가능한 이메일 */
					$('#edit_email_input_alert').attr('style', 'display:none');
					$('#edit_email_able_alert').attr('style', 'display:inline-block');
					$('#edit_email_disable_alert').attr('style', 'display:none');
					$('#edit_email_check_alert').attr('style', 'display:none');
				} else {
					emailCheck = true;
					emailOk = false;
					/* 사용불가능한 이메일 */
					$('#edit_email_input_alert').attr('style', 'display:none');
					$('#edit_email_able_alert').attr('style', 'display:none');
					$('#edit_email_disable_alert').attr('style', 'display:inline-block');
					$('#edit_email_check_alert').attr('style', 'display:none');
				}
			},
			
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		}); //ajax
	}); //click
		
	/* email을 새로 입력하면 중복검사를 안 한 것으로 표기 */
	$('#edit_email').keyup(function(){
	
		/* 세션에 있는 연락처와 현재 입력한 연락처를 비교함 */
		let session_email = $('.session_email').val();
		if($('#edit_email').val() == session_email){
			emailCheck = true;
			emailOk = true;
			$('#edit_email_able_alert').attr('style', 'display:inline-block');
			$('#edit_email_input_alert').attr('style', 'display:none');
			$('#edit_email_disable_alert').attr('style', 'display:none');
			$('#edit_email_check_alert').attr('style', 'display:none');
			return false;
		}
		
		emailCheck = false;
		/* 이메일 중복 체크 필요 */
		$('#edit_email_input_alert').attr('style', 'display:none');
		$('#edit_email_able_alert').attr('style', 'display:none');
		$('#edit_email_disable_alert').attr('style', 'display:none');
		$('#edit_email_check_alert').attr('style', 'display:inline-block');
		if($(this).val() == ''){
			/* 이메일 입력 필요 */
			$('#edit_email_input_alert').attr('style', 'display:inline-block');
			$('#edit_email_able_alert').attr('style', 'display:none');
			$('#edit_email_disable_alert').attr('style', 'display:none');
			$('#edit_email_check_alert').attr('style', 'display:none');
		}
	});
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	/* 연락처 중복검사 */
	$('#edit_phoneCheck').click(function() {
		
		/* 세션에 있는 연락처와 현재 입력한 연락처를 비교함 */
		let session_phone = $('.session_phone').val();
		if($('#edit_phone').val() == session_phone){
			phoneCheck = true;
			phoneOk = true;
			$('#edit_phone_able_alert').attr('style', 'display:inline-block');
			$('#edit_phone_disable_alert').attr('style', 'display:none');
			$('#edit_phone_check_alert').attr('style', 'display:none');
			return false;
		}
	
		if(!phoneRegex.test($('#edit_phone').val())){
			alert("핸드폰 번호 형식이 잘못되었습니다.");
			$('#edit_phone').val('');
			$('#edit_phone').focus();
			return false;
		}
	
		$.ajax({
			url : contextPath + "/register/check/phone",
			type : "POST",
			data : { 
				"phone" : $('#edit_phone').val() 
			},
			
			success : function(data) {
				if (data == 0 && $.trim($('#edit_phone').val()) != '') {
					phoneCheck = true;
					phoneOk = true;
					/* 사용가능한 연락처 */
					$('#edit_phone_able_alert').attr('style', 'display:inline-block');
					$('#edit_phone_disable_alert').attr('style', 'display:none');
					$('#edit_phone_check_alert').attr('style', 'display:none');
				} else {
					phoneCheck = true;
					phoneOk = false;
					/* 사용불가능한 연락처 */
					$('#edit_phone_able_alert').attr('style', 'display:none');
					$('#edit_phone_disable_alert').attr('style', 'display:inline-block');
					$('#edit_phone_check_alert').attr('style', 'display:none');
				}
			},
			
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		}); //ajax
	}); //click
	
	/* phone을 새로 입력하면 중복검사를 안 한 것으로 표기 */
	$('#edit_phone').keyup(function(){
		
		/* 세션에 있는 연락처와 현재 입력한 연락처를 비교함 */
		let session_phone = $('.session_phone').val();
		if($('#edit_phone').val() == session_phone){
			phoneCheck = true;
			phoneOk = true;
			$('#edit_phone_able_alert').attr('style', 'display:inline-block');
			$('#edit_phone_disable_alert').attr('style', 'display:none');
			$('#edit_phone_check_alert').attr('style', 'display:none');
			return false;
		}
	
		phoneCheck = false;
		/* 연락처 중복 체크 필요 */
		$('#edit_phone_able_alert').attr('style', 'display:none');
		$('#edit_phone_disable_alert').attr('style', 'display:none');
		$('#edit_phone_check_alert').attr('style', 'display:inline-block');
		if($(this).val() == ''){
			/* 연락처 입력 안 했을 경우 */
			$('#edit_phone_able_alert').attr('style', 'display:none');
			$('#edit_phone_disable_alert').attr('style', 'display:none');
			$('#edit_phone_check_alert').attr('style', 'display:none');
		}
	});
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	/* 회원가입 제출 */
	$('#editBtn').click(function(){
		
		/* 제출 전 입력폼 검사 */
		let pwd = $('#edit_pwd').val();
		let email = $('#edit_email').val();
		let phone = $('#edit_phone').val();
		
		/* 공백 검사 */
		if(pwd == '' || email == ''){
			alert("빈칸을 입력하세요.");
			return false;
		}
		
		/* 비밀번호 관련 검사 */
		else if(pwdOK == false){
			alert("비밀번호가 틀렸습니다.");
			return false;
		}
		
		/* 이메일 관련 검사 */
		else if(!emailRegex.test(email)){
			alert("이메일 형식이 잘못되었습니다.");
			return false;
		}else if($('.session_email').val() != email && emailCheck === false){
			alert("이메일 중복체크가 필요합니다.");
			return false;
		}else if($('.session_email').val() != email && emailOk === false){
			alert("사용불가능한 이메일입니다.");
			return false;
		}
		
		/* 연락처 관련 검사(선택입력) */
		if(phone == ''){ //연락처를 입력하지 않은 경우
			let result = confirm("연락처를 입력하지 않으시면 문자 발송 서비스를 사용하실 수 없습니다. 계속 진행하시겠습니까?");
			if(!result){
				return false;
			}
		}else{ //연락처를 입력한 경우
			if(!phoneRegex.test(phone)){
				alert("핸드폰 번호 형식이 잘못되었습니다.");
				return false;
			}else if(phoneCheck === false){
				alert("연락처 중복체크가 필요합니다.");
				return false;
			}else if(phoneOk === false){
				alert("사용불가능한 연락처입니다.");
				return false;
			}
		}
		
		/* ajax로 제출 */
		$.ajax({
			url : contextPath + "/updateMember",
			type : "POST",
			data : $('#editFrm').serialize(),
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			async: false,
			
			success : function(result) {
				if(result == 'true'){
					alert("회원 정보가 수정되었습니다.");
					location.reload(true);
				}
			},
			
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		});
	}); //click
}); //ready

function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}
