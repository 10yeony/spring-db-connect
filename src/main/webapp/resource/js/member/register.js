var contextPath = '';

$(function() {
	/* 아이디 중복검사 */
	var idCheck = false; //id 중복검사 여부
	var idOk = false; //id 사용가능 여부
	
	/* 이메일 중복검사 */
	var emailCheck = false; //id 중복검사 여부
	var emailOk = false; //id 사용가능 여부
	
	/* 연락처 중복검사 */
	var phoneCheck = false; //id 중복검사 여부
	var phoneOk = false; //id 사용가능 여부
	
	/* 정규표현식을 이용한 유효성 검사 */
	var idRegex = /^[a-zA-Z0-9]{4,10}$/; //아이디가 적합한지 검사
	var pwdRegex =  /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*~^#?&])[A-Za-z\d$@$!%*~^#?&]{8,12}$/; //비밀번호가 적합한지 검사
   	var emailRegex =  /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    // 이메일이 적합한지 검사할 정규식
    var phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/; //핸드폰 번호 정규식
	
	/* Context Path */
	contextPath = $('#contextPath').val();
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	/* id 중복검사 */
	$('#idCheck').click(function() {
		if(!idRegex.test($('#register_member_id').val())){
			alert("영어/숫자로 된 4자 이상 10자 이하의 아이디를 입력하세요");
			$('#register_member_id').val('');
			$('#register_member_id').focus();
			return false;
		}
		
		$.ajax({
			url : contextPath + "/register/check/id",
			type : "POST",
			data : { 
				"member_id" : $('#register_member_id').val() 
			},
			
			success : function(data) {
				if (data == 0 && $.trim($('#register_member_id').val()) != '') {
					idCheck = true;
					idOk = true;
					/* 사용가능한 아이디 */
					$('#id_input_alert').attr('style', 'display:none');
					$('#id_able_alert').attr('style', 'display:block');
					$('#id_disable_alert').attr('style', 'display:none');
					$('#id_check_alert').attr('style', 'display:none');
				} else {
					idCheck = true;
					idOk = false;
					/* 사용불가능한 아이디 */
					$('#id_input_alert').attr('style', 'display:none');
					$('#id_able_alert').attr('style', 'display:none');
					$('#id_disable_alert').attr('style', 'display:block');
					$('#id_check_alert').attr('style', 'display:none');
				}
			},
			
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		}); //ajax
	}); //click
	
	/* id를 새로 입력하면 중복검사를 안 한 것으로 표기 */
	$('#register_member_id').keyup(function(){
		idCheck = false;
		/* 아이디 중복 체크 필요 */
		$('#id_input_alert').attr('style', 'display:none');
		$('#id_able_alert').attr('style', 'display:none');
		$('#id_disable_alert').attr('style', 'display:none');
		$('#id_check_alert').attr('style', 'display:block');
		if($(this).val() == ''){
			/* 아이디 입력 필요 */
			$('#id_input_alert').attr('style', 'display:block');
			$('#id_able_alert').attr('style', 'display:none');
			$('#id_disable_alert').attr('style', 'display:none');
			$('#id_check_alert').attr('style', 'display:none');
		}
	});
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	/* 이메일 중복검사 */
	$('#emailCheck').click(function() {
		if(!emailRegex.test($('#register_email').val())){
			alert("이메일 형식이 잘못되었습니다.");
			$('#register_email').val('');
			$('#register_email').focus();
			return false;
		}
		
		$.ajax({
			url : contextPath + "/register/check/email",
			type : "POST",
			data : { 
				"email" : $('#register_email').val() 
			},
			
			success : function(data) {
				if (data == 0 && $.trim($('#register_email').val()) != '') {
					emailCheck = true;
					emailOk = true;
					/* 사용가능한 이메일 */
					$('#email_input_alert').attr('style', 'display:none');
					$('#email_able_alert').attr('style', 'display:inline-block');
					$('#email_disable_alert').attr('style', 'display:none');
					$('#email_check_alert').attr('style', 'display:none');
				} else {
					emailCheck = true;
					emailOk = false;
					/* 사용불가능한 이메일 */
					$('#email_input_alert').attr('style', 'display:none');
					$('#email_able_alert').attr('style', 'display:none');
					$('#email_disable_alert').attr('style', 'display:inline-block');
					$('#email_check_alert').attr('style', 'display:none');
				}
			},
			
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		}); //ajax
	}); //click
	
	/* email을 새로 입력하면 중복검사를 안 한 것으로 표기 */
	$('#register_email').keyup(function(){
		emailCheck = false;
		/* 이메일 중복 체크 필요 */
		$('#email_input_alert').attr('style', 'display:none');
		$('#email_able_alert').attr('style', 'display:none');
		$('#email_disable_alert').attr('style', 'display:none');
		$('#email_check_alert').attr('style', 'display:inline-block');
		if($(this).val() == ''){
			/* 이메일 입력 필요 */
			$('#email_input_alert').attr('style', 'display:inline-block');
			$('#email_able_alert').attr('style', 'display:none');
			$('#email_disable_alert').attr('style', 'display:none');
			$('#email_check_alert').attr('style', 'display:none');
		}
	});
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	/* 연락처 중복검사 */
	$('#phoneCheck').click(function() {
		if(!phoneRegex.test($('#register_phone').val())){
			alert("핸드폰 번호 형식이 잘못되었습니다.");
			$('#register_phone').val('');
			$('#register_phone').focus();
			return false;
		}
	
		$.ajax({
			url : contextPath + "/register/check/phone",
			type : "POST",
			data : { 
				"phone" : $('#register_phone').val() 
			},
			
			success : function(data) {
				if (data == 0 && $.trim($('#register_phone').val()) != '') {
					phoneCheck = true;
					phoneOk = true;
					/* 사용가능한 연락처 */
					$('#phone_able_alert').attr('style', 'display:inline-block');
					$('#phone_disable_alert').attr('style', 'display:none');
					$('#phone_check_alert').attr('style', 'display:none');
				} else {
					phoneCheck = true;
					phoneOk = false;
					/* 사용불가능한 연락처 */
					$('#phone_able_alert').attr('style', 'display:none');
					$('#phone_disable_alert').attr('style', 'display:inline-block');
					$('#phone_check_alert').attr('style', 'display:none');
				}
			},
			
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		}); //ajax
	}); //click
	
	/* phone을 새로 입력하면 중복검사를 안 한 것으로 표기 */
	$('#register_phone').keyup(function(){
		phoneCheck = false;
		/* 연락처 중복 체크 필요 */
		$('#phone_able_alert').attr('style', 'display:none');
		$('#phone_disable_alert').attr('style', 'display:none');
		$('#phone_check_alert').attr('style', 'display:inline-block');
		if($(this).val() == ''){
			/* 연락처 입력 안 했을 경우 */
			$('#phone_able_alert').attr('style', 'display:none');
			$('#phone_disable_alert').attr('style', 'display:none');
			$('#phone_check_alert').attr('style', 'display:none');
		}
	});
	///////////////////////////////////////////////////////////////////////////////////////
	
	/* 비밀번호 입력 검사 */
	$('#register_pwd').keyup(function(){
		let pwd = $('#register_pwd').val();
		let pwdCheck = $('#register_pwd_check').val();
		
		if(pwd == ''){
			$('#pwd_input_alert').attr('style', 'display:block');
			$('#pwd_different_alert').attr('style', 'display:none');
			$('#pwd_disable_alert').attr('style', 'display:none');
			$('#pwd_same_alert').attr('style', 'display:none');
			$('#register_pwd').focus();
			return false;
		}else if(!pwdRegex.test(pwd)){
			$('#pwd_input_alert').attr('style', 'display:none');
			$('#pwd_different_alert').attr('style', 'display:none');
			$('#pwd_disable_alert').attr('style', 'display:block');
			$('#pwd_same_alert').attr('style', 'display:none');
			$('#register_pwd').focus();
			return false;
		}else{
			$('#pwd_input_alert').attr('style', 'display:none');
			$('#pwd_different_alert').attr('style', 'display:none');
			$('#pwd_disable_alert').attr('style', 'display:none');
			$('#pwd_same_alert').attr('style', 'display:none');
		}
	});
	
	
	/* 비밀번호 일치 검사(비밀번호  확인) */
	$('#register_pwd_check').keyup(function(){
		let pwd = $('#register_pwd').val();
		let pwdCheck = $('#register_pwd_check').val();
		
		if(pwd != pwdCheck){
			$('#pwd_input_alert').attr('style', 'display:none');
			$('#pwd_different_alert').attr('style', 'display:block');
			$('#pwd_disable_alert').attr('style', 'display:none');
			$('#pwd_same_alert').attr('style', 'display:none');
			return false;
		}else if(pwd === ''){
			$('#pwd_input_alert').attr('style', 'display:block');
			$('#pwd_different_alert').attr('style', 'display:none');
			$('#pwd_disable_alert').attr('style', 'display:none');
			$('#pwd_same_alert').attr('style', 'display:none');
			$('#register_pwd').focus();
			return false;
		}else {
			$('#pwd_input_alert').attr('style', 'display:none');
			$('#pwd_different_alert').attr('style', 'display:none');
			$('#pwd_disable_alert').attr('style', 'display:none');
			$('#pwd_same_alert').attr('style', 'display:block');
		}
	});
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	/* 회원가입 제출 */
	$('#register_submit').click(function(){
	
		/* 제출 전 입력폼 검사 */
		let name = $('#register_name').val();
		let member_id = $('#register_member_id').val();
		let pwd = $('#register_pwd').val();
		let pwdCheck = $('#register_pwd_check').val();
		let email = $('#register_email').val();
		let phone = $('#register_phone').val();
		
		/* 공백 검사 */
		if(name == ''){
			alert(name);
			alert("이름을 입력하세요.");
			return false;
		}
		else if(member_id == ''){
			alert("아이디를 입력하세요.");
			return false;
		}
		else if(pwd == ''){
			alert("비밀번호를 입력하세요.");
			return false;
		}
		else if(pwdCheck == ''){
			alert("비밀번호를 한번 더 입력하세요.");
			return false;
		}
		else if(email == ''){
			alert("이메일을 입력하세요.");
			return false;
		}
		
		/* 아이디 관련 검사 */
		else if(!idRegex.test(member_id)){
			alert("영어/숫자로 된 4자 이상 10자 이하의 아이디를 입력하세요");
			return false;
		}else if(idCheck === false){
			alert("아이디 중복체크가 필요합니다.");
			return false;
		}else if(idOk === false){
			alert("사용불가능한 아이디입니다.");
			return false;
		}
		
		/* 비밀번호 관련 검사 */
		else if(!pwdRegex.test(pwd)){
			alert("영어/숫자/특수문자를 포함한 8자 이상 12자 이하의 비밀번호를 입력하세요.");
			return false;
		}else if(pwd != pwdCheck){
			alert("비밀번호가 일치하지 않습니다.");
			return false;
		}
		
		/* 이메일 관련 검사 */
		else if(!emailRegex.test(email)){
			alert("이메일 형식이 잘못되었습니다.");
			return false;
		}else if(emailCheck === false){
			alert("이메일 중복체크가 필요합니다.");
			return false;
		}else if(emailOk === false){
			alert("사용불가능한 이메일입니다.");
			return false;
		}
		
		/* 개인 정보 활용 동의 체크 확인 */
		else if(!$('#customAgree').is(":checked")){
			alert('개인 정보 활용에 동의해주세요.');
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
			url : contextPath + "/register",
			type : "POST",
			data : $('#registerFrm').serialize(),
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			async: false,
			
			success : function(data) {
				alert(data);
				location.href = contextPath + "/index.jsp";
			},
			
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		});
	}); //click
}); //ready
