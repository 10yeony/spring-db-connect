var contextPath = '';

$(function(){
	/* 가입승인/계정 활성화/회원탈퇴 alert */
	if(sessionStorage.getItem("memberAlert") != null){
		alert(sessionStorage.getItem("memberAlert"));
		sessionStorage.removeItem("memberAlert");
	}

	/* Context Path */
	contextPath = $('#contextPath').val();
	
	/* 관리자 권한으로 회원 탈퇴 */
	$('#deleteBtnByAdmin').click(function(){
		let result = confirm('해당 회원을 정말로 탈퇴시키겠습니까?');
		if(result){
			let member_id = $('#thisMember_id').val();
			console.log(member_id)
			deleteMemberByAdmin(member_id);
		}
	});

	/* 뒤로 가기 클릭 */
	$('#backToMemberList').click(function(){
		window.history.back();
	});
});

function checkAllMemberInThisPage(event){
	if(event.target.checked){
		$('.selectItem').prop('checked', true);
	}else{
		$('.selectItem').prop('checked', false);
	}
}

function handleMember(cmd){
	let selectItem = document.getElementsByClassName('selectItem');
	let idList = [];
	let nameList = [];
	let emailList = [];
	let approvalList = [];
	let expiredList = [];
	let checkCnt = 0;
	for(let i=0; i<selectItem.length; i++){
		if(selectItem[i].checked){
			let tr = selectItem[i].parentNode.parentNode;
			let idTd = tr.childNodes[5].firstChild.innerHTML; //id
			let nameTd = tr.childNodes[7].firstChild.innerHTML; //name
			let emailTd = tr.childNodes[9].innerHTML; //email
			let approvalTd = tr.childNodes[15].innerHTML; //가입 승인
			let expiredTd = tr.childNodes[17].innerHTML; //계정만료
			idList.push(idTd);
			nameList.push(nameTd);
			emailList.push(emailTd);
			approvalList.push(approvalTd);
			expiredList.push(expiredTd);
			checkCnt++;
		}
	}
	if(checkCnt == 0){
		alert("사용자를 선택하세요");
		return;
	}
	if(cmd == 'approval'){
		let result = confirm('선택한 회원의 가입을 승인하시겠습니까?');
		if(result){
			for(let i=0; i<idList.length; i++){
				if(approvalList[i] != '승인'){
					approval(idList[i], nameList[i], emailList[i]);
				}
			}
		}
	}
	else if(cmd == 'active'){
		let result = confirm('선택한 회원의 계정을 활성화하시겠습니까?');
		if(result){
			for(let i=0; i<idList.length; i++){
				if(expiredList[i] != '만료되지 않음'){
					accountActivation(idList[i]);
				}
			}
		}
	}
	else if(cmd == 'delete'){
		let result = confirm('선택한 회원을 정말로 탈퇴시키겠습니까?');
		if(result){
			for(let i=0; i<idList.length; i++){
				deleteMemberByAdmin(idList[i]);
			}
		}
	}
}

function approval(member_id, name, email){
	$.ajax({
		url: contextPath + "/approval",
		data : {
			member_id : member_id,
			name : name,
			email : email
		},
		type: "POST",
	
		success : function (){
			sessionStorage.setItem("memberAlert", "가입 승인 처리 완료");
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
			sessionStorage.setItem("memberAlert", "계정 활성화 처리 완료");
			location.reload();
		}
	})
}

function deleteMemberByAdmin(member_id){
	$.ajax({
		url : contextPath + "/deleteMemberByAdmin",
		type : "GET",
		data : "member_id=" + member_id,
		async: false,
				
		success : function(result) {
			if(result == 'true'){
				sessionStorage.setItem("memberAlert", "회원 탈퇴 처리 완료");
				location.href = contextPath + "/getMemberListByAdmin?data=ALL&current_page_no=1&count_per_page=10&count_per_list=10&search_word=&approval="
			}
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}