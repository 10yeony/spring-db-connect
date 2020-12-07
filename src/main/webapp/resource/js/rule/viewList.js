var contextPath;

$(function(){
	/* Context Path */
	contextPath = getContextPath();
	
	/* 메세지 체크 후 띄우기 */
	var ruleRegSuccessMsg = $('#ruleRegSuccessMsg').val();
	if(ruleRegSuccessMsg != ''){
		alert(ruleRegSuccessMsg);
	}
	var ruleDelSuccessMsg = $('#ruleDelSuccessMsg').val();
	if(ruleDelSuccessMsg != ''){
		alert(ruleDelSuccessMsg);
	}
	var ruleDelErrorMsg = $('#ruleDelErrorMsg').val();
	if(ruleDelErrorMsg != ''){
		alert(ruleDelErrorMsg);
	}
	
	getRuleList();
	
	/* 대분류 선택시 */
	$('#top_level').change(function(){
		$('#ruleListTbody').empty(); //Rule 테이블 리셋
		
		var top_level_id = $(this).val();
		if(top_level_id == 'top_level_value'){
			getRuleList();
			return;
		}
		
		getRuleListByTopId(top_level_id);
	});
	
	/* 중분류 선택시 */
	$('#middle_level').change(function(){
		$('#ruleListTbody').empty(); //Rule 테이블 리셋
		
		var top_level_id = $('#top_level').val();
		var middle_level_id = $(this).val();
		if(middle_level_id == 'middle_level_value'){
			getRuleListByTopId(top_level_id);
			return;
		}
		
		getRuleListByTopMiddleId(top_level_id, middle_level_id);
	});
	
	/* 소분류 선택시 */
	$('#bottom_level').change(function(){
		$('#ruleListTbody').empty(); //Rule 테이블 리셋
		
		var top_level_id = $('#top_level').val();
		var middle_level_id = $('#middle_level').val();
		var bottom_level_id = $(this).val();
		if(bottom_level_id == 'bottom_level_value'){
			getRuleListByTopMiddleId(top_level_id, middle_level_id);
			return;
		}
		
		getRuleListByTopMiddleBottomId(top_level_id, middle_level_id, bottom_level_id);
	});
});

/* 전사규칙 리스트를 가져옴 */
function getRuleList(){
	$.ajax({
		//요청
		type: "GET",
		url: contextPath + "/rule/getRuleList", 
		data: {
			top_level_id: '',
			middle_level_id: '',
			bottom_level_id: ''
		},
		async: false,
			
		//응답
		success : function(response){  
			var json = JSON.parse(response);
			var list = json.item.list;
			appendRuleTable(list);
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}

/* 대분류 아이디를 통해 전사규칙 리스트를 가져옴 */
function getRuleListByTopId(top_level_id){
	$.ajax({
		//요청
		type: "GET",
		url: contextPath + "/rule/getRuleList", 
		data: {
			top_level_id: top_level_id,
			middle_level_id: '',
			bottom_level_id: ''
		},
		async: false,
			
		//응답
		success : function(response){  
			var json = JSON.parse(response);
			var list = json.item.list;
			appendRuleTable(list);
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}

/* 대분류, 중분류 아이디를 통해 전사규칙 리스트를 가져옴 */
function getRuleListByTopMiddleId(top_level_id, middle_level_id){
	$.ajax({
		//요청
		type: "GET",
		url: contextPath + "/rule/getRuleList", 
		data: {
			top_level_id: top_level_id,
			middle_level_id: middle_level_id,
			bottom_level_id: ''
		},
		async: false,
			
		//응답
		success : function(response){  
			var json = JSON.parse(response);
			var list = json.item.list;
			appendRuleTable(list);
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}

/* 대분류, 중분류, 소분류 아이디를 통해 전사규칙 리스트를 가져옴 */
function getRuleListByTopMiddleBottomId(top_level_id, middle_level_id, bottom_level_id){
	$.ajax({
		//요청
		type: "GET",
		url: contextPath + "/rule/getRuleList", 
		data: {
			top_level_id: top_level_id,
			middle_level_id: middle_level_id,
			bottom_level_id: bottom_level_id
		},
		async: false,
			
		//응답
		success : function(response){  
			var json = JSON.parse(response);
			var list = json.item.list;
			appendRuleTable(list);
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}

/* 전사규칙 표를 덧붙임 */
function appendRuleTable(list){
	for(let i=0; i<list.length; i++){
		var row_num = JSON.stringify(list[i].row_num);
			
		var top_level_name = JSON.stringify(list[i].top_level_name);
		top_level_name = top_level_name.replace(/"/g, ""); //큰 따옴표 제거
				
		var middle_level_name = JSON.stringify(list[i].middle_level_name);
		middle_level_name = middle_level_name.replace(/"/g, ""); //큰 따옴표 제거
				
		var bottom_level_id = JSON.stringify(list[i].bottom_level_id);
				
		var bottom_level_name = JSON.stringify(list[i].bottom_level_name);
		bottom_level_name = bottom_level_name.replace(/"/g, ""); //큰 따옴표 제거
				
		var description = JSON.stringify(list[i].description);
		description = description.replace(/"/g, ""); //큰 따옴표 제거
				
		var creator = JSON.stringify(list[i].creator);
		creator = creator.replace(/"/g, ""); //큰 따옴표 제거
		
		var contents = JSON.stringify(list[i].contents);
		contents = contents.replace(/"/g, ""); //큰 따옴표 제거
				
		$('#ruleListTbody').append(
			"<tr>" +
				"<td>" + row_num + "</td>" +
				"<td>" + top_level_name + "</td>" +
				"<td>" + middle_level_name + "</td>" +
				"<td><a href=" + contextPath + "/rule/editRule?bottom_level_id=" + bottom_level_id + ">"+ bottom_level_name + "</a></td>" +
				"<td>" + description + "</td>" +
				"<td>" + creator + "</td>" +
			"</tr>"
		);
	}
}

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}