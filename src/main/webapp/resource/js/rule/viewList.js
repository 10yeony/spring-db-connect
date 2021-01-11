var contextPath;
var top_level_id, middle_level_id, bottom_level_id;

$(function(){
	/* Context Path */
	contextPath = $('#contextPath').val();
	
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
	
	top_level_id = $('#ruleList_top_level_id').val();
	middle_level_id = $('#ruleList_middle_level_id').val();
	bottom_level_id = $('#ruleList_bottom_level_id').val();
	if(top_level_id == 0 && middle_level_id == 0 && bottom_level_id == 0){
		top_level_id = '';
		middle_level_id = '';
		bottom_level_id = '';
	}else if(top_level_id != 0 && middle_level_id == 0 && bottom_level_id == 0){
		middle_level_id = '';
		bottom_level_id = '';
	}else if(top_level_id != 0 && middle_level_id != 0 && bottom_level_id == 0){
		bottom_level_id = '';
	}
	startPagingHandling();
	
	/* 대분류 선택시 */
	$('#top_level').change(function(){
		$('#ruleListTbody').empty(); //Rule 테이블 리셋
		top_level_id = $(this).val();
		middle_level_id = '';
		bottom_level_id = '';
		if(top_level_id == 'top_level_value'){
			top_level_id = '';
		}
		startPagingHandling();
	});
	
	/* 중분류 선택시 */
	$('#middle_level').change(function(){
		$('#ruleListTbody').empty(); //Rule 테이블 리셋
		top_level_id = $('#top_level').val();
		middle_level_id = $(this).val();
		bottom_level_id = '';
		if(middle_level_id == 'middle_level_value'){
			middle_level_id = '';
		}
		startPagingHandling();
	});
	
	/* 소분류 선택시 */
	$('#bottom_level').change(function(){
		$('#ruleListTbody').empty(); //Rule 테이블 리셋
		top_level_id = $('#top_level').val();
		middle_level_id = $('#middle_level').val();
		bottom_level_id = $(this).val();
		if(bottom_level_id == 'bottom_level_value'){
			bottom_level_id = '';
		}
		startPagingHandling();
	});
});

/* 전사규칙 리스트를 가져옴 */
function startPagingHandling(){
	/*
	var data = {
		top_level_id: top_level_id,
		middle_level_id: middle_level_id,
		bottom_level_id: bottom_level_id,
		current_page_no: $('#current_page_no').val(),
		count_per_page: $('#show_count_per_page').val(),
		count_per_list: $('#show_count_per_list').val(),
		search_word: $("#inputSearchText").val()
	}
	*/
	var data = 
	"?top_level_id=" + top_level_id +
	"&middle_level_id=" + middle_level_id + 
	"&bottom_level_id=" + bottom_level_id + 
	"&current_page_no=" + $('#current_page_no').val() + 
	"&count_per_page=" + $('#show_count_per_page').val() + 
	"&count_per_list=" + $('#show_count_per_list').val() +
	"&search_word=" + $("#inputSearchText").val();
	var json = JSON.parse(getPagingResult(data));
	//var list = json.item.list;
	//appendTable(list);
}

/* 전사규칙 표를 덧붙임 */
function appendTable(list){
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

		var rule_type = JSON.stringify(list[i].rule_type);
		rule_type = rule_type.replace(/"/g, ""); //큰 따옴표 제거
		console.log('rule_type' + rule_type)
				
		$('#ruleListTbody').append(
			"<tr>" +
				"<td><input type='checkbox' class='selectItem'></td>" +
				"<td>" + row_num + "</td>" +
				"<td>" + top_level_name + "</td>" +
				"<td>" + middle_level_name + "</td>" +
				"<td><a href=" + contextPath + "/rule/editRule?bottom_level_id=" + bottom_level_id + ">"+ bottom_level_name + "</a></td>" +
				"<td>" + description + "</td>" +
				"<td>" + rule_type + "</td>" +
				"<td>" + creator + "</td>" +
			"</tr>"
		);
	}
}

function checkAllRuleInThisPage(event){
	if(event.target.checked){
		$('.selectItem').prop('checked', true);
	}else{
		$('.selectItem').prop('checked', false);
	}
}

function deleteRule(){
	alert("삭제");
}