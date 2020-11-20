var contextPath = '';

$(function(){

	/* Context Path */
	contextPath = getContextPath();
	
	/* 관리자 권한으로 회원 탈퇴 */
	$('#deleteBtnByAdmin').click(function(){
		let result = confirm('해당 회원을 정말로 탈퇴시키겠습니까?');
		if(result){
			
		}
	});

	/* 뒤로 가기 클릭 */
	$('#backToMemberList').click(function(){
		location.href = contextPath + "/member/getMemberList";
	});
	
	/* 관리자 권한으로 회원정보 수정 */
	$('#editBtnByAdmin').click(function(){
		let result = confirm('해당 회원의 정보를 수정하시겠습니까?');
		if(result){
			
		}
	});
});

function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}
