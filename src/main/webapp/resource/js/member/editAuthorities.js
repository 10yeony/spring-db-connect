var contextPath = '';

$(function(){

	/* Context Path */
	contextPath = getContextPath();
	
	/* 권한 포함 관계 처리 */
	$('#role_view_check').change(function(){ //데이터 조회 권한
		if($('#role_admin_check').is(":checked")){
			$('#role_input_check').prop("checked", true);
			$('#role_view_check').prop("checked", true);
		}
		if($('#role_input_check').is(":checked")){
			$('#role_view_check').prop("checked", true);
		}
	});
	
	$('#role_input_check').change(function(){ //데이터 입력 권한
		if($('#role_admin_check').is(":checked")){
			$('#role_input_check').prop("checked", true);
			$('#role_view_check').prop("checked", true);
		}
		if($('#role_input_check').is(":checked")){
			$('#role_view_check').prop("checked", true);
		}
	});
	
	$('#role_admin_check').change(function(){ //관리자 권한
		if($('#role_admin_check').is(":checked")){
			$('#role_input_check').prop("checked", true);
			$('#role_view_check').prop("checked", true);
		}
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
				url : contextPath + "/member/updateAuthorities",
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

function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}
	