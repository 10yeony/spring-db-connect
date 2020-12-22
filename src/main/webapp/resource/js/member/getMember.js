var contextPath = '';

$(function(){

	/* Context Path */
	contextPath = getContextPath();
	
	/* 관리자 권한으로 회원 탈퇴 */
	$('#deleteBtnByAdmin').click(function(){
		let member_id = $('#thisMember_id').val();
		let result = confirm('해당 회원을 정말로 탈퇴시키겠습니까?');
		if(result){
			$.ajax({
				url : contextPath + "/deleteMemberByAdmin",
				type : "GET",
				data : "member_id=" + member_id,
				async: false,
				
				success : function(result) {
					if(result == 'true'){
						alert("해당 회원이 정상적으로 탈퇴되었습니다.");
						location.href = contextPath + "/getMemberListByAdmin?role=ALL"
					}else{
						alert("해당 회원을 탈퇴시키는 데 실패하였습니다.");
					}
				},
				error : function(request, status, error) {
					//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
				}
			}); //ajax
		}
	});

	/* 뒤로 가기 클릭 */
	$('#backToMemberList').click(function(){
		window.history.back();
	});
});

function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}

function approval(){
	let member_id = $('#thisMember_id').val();
	$.ajax({
		url: contextPath+"/approval",
		data : "member_id=" + member_id,
		type: "POST",

		success : function (){
			alert("가입 승인 완료");
			location.reload(true);
		}
	})
}
