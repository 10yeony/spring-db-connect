var contextPath = '';

$(function(){

	/* Context Path */
	contextPath = $('#contextPath').val();
	
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
						location.href = contextPath + "/getMemberListByAdmin?data=ALL&current_page_no=1&count_per_page=10&count_per_list=10&search_word=&approval="
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

function approval(member_id){
	$.ajax({
		url: contextPath + "/approval",
		data : "member_id=" + member_id,
		type: "POST",

		success : function (){
			alert("가입 승인 완료");
			location.reload();
		}
	})
}

function accountActivation(member_id){
	$.ajax({
		url: contextPath + "/accountActivation",
		data : "member_id=" + member_id,
		type: "POST",

		success : function (){
			alert("계정 활성화 완료");
			location.reload();
		}
	})
}
