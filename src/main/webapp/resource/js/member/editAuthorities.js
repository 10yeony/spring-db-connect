var contextPath = '';

$(function(){

	/* Context Path */
	contextPath = $('#contextPath').val();
	
	/* ROLE_VIEW 체크변화시 */
	$('#role_view_check').change(function(){
		view_check_handler();
	});
	
	/* ROLE_INPUT 체크변화시 */
	$('#role_input_check').change(function(){ 
		input_check_handler();
	});
	
	/* ROLE_ADMIN 체크변화시 */
	$('#role_admin_check').change(function(){ 
		admin_check_handler();
	});
	
	/* ROLE_VIEW 영역 클릭시 */
	$('#role_view_check_area').click(function(){
		if($('#role_view_check').is(":checked")){
			$('#role_view_check').prop("checked", false);
		}else{
			$('#role_view_check').prop("checked", true);
		}
		view_check_handler();
	});
	
	/* ROLE_INPUT 영역 클릭시 */
	$('#role_input_check_area').click(function(){
		if($('#role_input_check').is(":checked")){
			$('#role_input_check').prop("checked", false);
		}else{
			$('#role_input_check').prop("checked", true);
		}
		input_check_handler();
	});
	
	/* ROLE_ADMIN 영역 클릭시 */
	$('#role_admin_check_area').click(function(){
		if($('#role_admin_check').is(":checked")){
			$('#role_admin_check').prop("checked", false);
		}else{
			$('#role_admin_check').prop("checked", true);
		}
		admin_check_handler();
	});
	
	/* 관리자 권한으로 회원정보 수정 */
	$('#editAuthoritiesBtn').click(function(){
		let authorities = []
		if($('#role_view_check').is(":checked")){
			authorities.push('ROLE_VIEW');
		}
		if($('#role_admin_check').is(":checked")){
			authorities.push('ROLE_ADMIN');
		}
		if($('#role_input_check').is(":checked")){
			authorities.push('ROLE_INPUT');
		}
		let result = confirm('해당 회원의 정보를 수정하시겠습니까?');
		if(result){
			$.ajax({
				url : contextPath + "/updateAuthoritiesByAdmin",
				type : "POST",
				data : { 
					"member_id" : $('#thisMember_id').val(),
					"authorities" : authorities.join(',')
				},
				
				success : function(result) {
					if (result == 'true') {
						alert("해당 회원의 권한을 성공적으로 수정하였습니다.");
						location.reload(true);
					} else {
						alert("해당 회원의 권한을 수정하는 데 실패하였습니다.");
					}
				},
				
				error : function(request, status, error) {
					//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
				}
			}); //ajax
		}
	});
});

/* 권한 포함 관계(ROLE_VIEW < ROLE_INPUT < ROLE_ADMIN) */
function view_check_handler(){
	if(!$('#role_view_check').is(":checked")){ //view 체크해제시
		$('#role_input_check').prop("checked", false);
		$('#role_admin_check').prop("checked", false);
	}
}
function input_check_handler(){
	if($('#role_input_check').is(":checked")){ //input 체크시
		$('#role_view_check').prop("checked", true);
	}
	else{ //input 체크해제시
		$('#role_admin_check').prop("checked", false);
	}
}
function admin_check_handler(){
	if($('#role_admin_check').is(":checked")){ //admin 체크시
		$('#role_input_check').prop("checked", true);
		$('#role_view_check').prop("checked", true);
	}
}
	