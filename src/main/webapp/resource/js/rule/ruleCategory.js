var contextPath;

$(function(){
	/* Context Path */
	contextPath = $('#contextPath').val();

	getAllRuleTopLevel();
	
	/* 드롭다운 핸들링 */
	$('#top_level').change(function(){ //대분류 선택시
		handleDropDown('top', $(this).val(), null);
	});
	$('#middle_level').change(function(){ //중분류 선택시
		handleDropDown('middle', $('#top_level').val(), $(this).val());
	});
});

/* 드롭다운 핸들링 */
function handleDropDown(cmd, selectedTopValue, selectedMiddleValue){
	if(cmd == 'top'){
		/* bottom_level 내용을 리셋 */
		$('#bottom_level').empty();
		$('#bottom_level').append('<option value="bottom_level_value">소분류</option>');
		$('#bottom_level').val('bottom_level_value');
		
		/* middle_level 내용을 리셋 */
		$('#middle_level').empty();
		$('#middle_level').append('<option value="middle_level_value">중분류</option>');
		$('#middle_level').val('middle_level_value');
		
		/* middle_level 내용을 새롭게 세팅 */
		if(selectedTopValue == 'top_level_value' || selectedTopValue == 'top_level_input'){
			return false;
		}
		getAllRuleMiddleLevel(selectedTopValue); //중분류 드롭다운 목록 호출
		let className = 'top_level_' + selectedTopValue;
		$('.' + className).attr('style', 'display:block');
	}
	else if(cmd == 'middle'){
		/* bottom_level 내용을 리셋 */
		$('#bottom_level').empty();
		$('#bottom_level').append('<option value="bottom_level_value">소분류</option>');
		$('#bottom_level').val('bottom_level_value');
		
		/* bottom_level 내용을 새롭게 세팅 */
		if(selectedMiddleValue == 'middle_level_value' || selectedMiddleValue == 'middle_level_input'){
			return false;
		}
		getAllRuleBottomLevel(selectedTopValue, selectedMiddleValue); //소분류 드롭다운 목록 호출
		let className = 'middle_level_' + selectedMiddleValue;
		$('.' + className).attr('style', 'display:block');
	}
}

/* 대분류 드롭다운 목록을 가져옴 */
function getAllRuleTopLevel(){
	$.ajax({
		//요청
		type: "GET",
		url: contextPath + "/rule/getRuleCategory", 
		data: {
			top_level_id: '',
			middle_level_id: ''
		},
		async: false,
			
		//응답
		success : function(response){  
			var json = JSON.parse(response);
			var list = json.item.list;
			for(let i=0; i<list.length; i++){
				var top_level_id = JSON.stringify(list[i].top_level_id);
				var top_level_name = JSON.stringify(list[i].top_level_name);
				top_level_name = top_level_name.replace(/"/g, ""); //큰 따옴표 제거
				$('#top_level').append(
					"<option value=" + top_level_id + ">" + top_level_name + "</option>"
				);
			}
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}

/* 중분류 드롭다운 목록을 가져옴 */
function getAllRuleMiddleLevel(top_level_id){
	$.ajax({
		//요청
		type: "GET",
		url: contextPath + "/rule/getRuleCategory", 
		data: {
			top_level_id: top_level_id,
			middle_level_id: ''
		},
		async: false,
			
		//응답
		success : function(response){  
			var json = JSON.parse(response);
			var list = json.item.list;
			for(let i=0; i<list.length; i++){
				var middle_level_id = JSON.stringify(list[i].middle_level_id);
				var middle_level_name = JSON.stringify(list[i].middle_level_name);
				middle_level_name = middle_level_name.replace(/"/g, ""); //큰 따옴표 제거
				$('#middle_level').append(
					"<option class='middle_level_collection top_level_" + top_level_id + "' value=" + middle_level_id + ">" + middle_level_name + "</option>"
				);
			}
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}

/* 소분류 드롭다운 목록을 가져옴 */
function getAllRuleBottomLevel(top_level_id, middle_level_id){
	$.ajax({
		//요청
		type: "GET",
		url: contextPath + "/rule/getRuleCategory", 
		data: {
			top_level_id: top_level_id,
			middle_level_id: middle_level_id
		},
		async: false,
		
		//응답
		success : function(response){  
			var json = JSON.parse(response);
			var list = json.item.list;
			for(let i=0; i<list.length; i++){
				var bottom_level_id = JSON.stringify(list[i].bottom_level_id);
				var bottom_level_name = JSON.stringify(list[i].bottom_level_name);
				bottom_level_name = bottom_level_name.replace(/"/g, ""); //큰 따옴표 제거
				$('#bottom_level').append(
					"<option class='bottom_level_collection middle_level_" + middle_level_id + "' value=" + bottom_level_id + ">" + bottom_level_name + "</option>"
				);
			}
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}