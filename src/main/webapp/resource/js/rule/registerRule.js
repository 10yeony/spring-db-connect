var contextPath;

$(function(){

	/* Context Path */
	contextPath = getContextPath();
	
	/* 메세지 체크 후 띄우기 */
	var ruleRegSuccessMsg = $('#ruleRegSuccessMsg').val();
	if(ruleRegSuccessMsg != ''){
		alert(ruleRegSuccessMsg);
	}
	var ruleRegErrorMsg = $('#ruleRegErrorMsg').val();
	if(ruleRegErrorMsg != ''){
		alert(ruleRegErrorMsg);
	}
	var ruleDelSuccessMsg = $('#ruleDelSuccessMsg').val();
	if(ruleDelSuccessMsg != ''){
		alert(ruleDelSuccessMsg);
	}
	var ruleDelErrorMsg = $('#ruleDelErrorMsg').val();
	if(ruleDelErrorMsg != ''){
		alert(ruleDelErrorMsg);
	}

	$('#top_level').append(
		'<option id="top_level_input" value="top_level_input">직접 입력</option>'
	);
	
	/* 대분류 드롭다운 핸들링 */
	$('#top_level').change(function(){
		var top_level_id = $(this).val();
		$('input[name=top_level_id]')[0].value = top_level_id;
		$('input[name=top_level_id]')[1].value = top_level_id;
		
		var top_level_name = $('#top_level option:checked').text();
		$('input[name=top_level_name]')[0].value = top_level_name;
		$('input[name=top_level_name]')[1].value = top_level_name;
		
		if(top_level_id == 'top_level_input'){
			$('#add_top_level_area').attr('style', 'display:block');
			$('#add_top_level_area').attr('style', 'margin-top:7px');
			$('#add_middle_level_area').attr('style', 'display:none');
		}else{
			$('#add_top_level_area').attr('style', 'display:none');
			
			if(top_level_id != 'top_level_value'){
				if($('#middle_level_input').val() == undefined){
					$('#middle_level').append(
						'<option id="top_level_input" value="middle_level_input">직접 입력</option>'
					);
				}
			} else{
				$('#add_middle_level_area').attr('style', 'display:none');
			}
		}
	});
	
	/* 중분류 드롭다운 핸들링 */
	$('#middle_level').change(function(){
		var middle_level_id = $(this).val();
		$('input[name=middle_level_id]')[0].value = middle_level_id;
		
		var middle_level_name = $('#middle_level option:checked').text();
		$('input[name=middle_level_name]')[0].value = middle_level_name;
		
		if(middle_level_id == 'middle_level_input'){
			$('#add_middle_level_area').attr('style', 'display:block');
			$('#add_middle_level_area').attr('style', 'margin-top:7px');
		}else{
			$('#add_middle_level_area').attr('style', 'display:none');
		}
	});
	
	/* Rule 등록 전 검사 */
	$('#add_bottom_level_frm').submit(function(){
		let top_level_id = $('input[name=top_level_id]')[1].value;
		let middle_level_id = $('input[name=middle_level_id]')[0].value;
		if(top_level_id == '' || 
			top_level_id == 'top_level_value' || 
			top_level_id == 'top_level_input'){
			alert("대분류를 선택하세요");
			return false;
		}else if(middle_level_id == '' ||
				middle_level_id == 'middle_level_value' || 
				middle_level_id == 'middle_level_input'){
			alert("중분류를 선택하세요");
			return false;
		}
	});
});

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}

/* 대분류 삭제(프론트에서 확인) */
function deleteTopLevel(){
	let top_level = $('#top_level').val();
	if(top_level == 'top_level_value' || top_level == 'top_level_input'){
		alert("삭제하실 대분류를 선택하세요.")
	}else{
		let answer = confirm("대분류를 삭제하시면 대분류에 해당되는 전사규칙들이 모두 삭제됩니다. 그래도 삭제하시겠습니까?");
		if(answer){
			location.href = contextPath + "/rule/deleteRuleLevel?level=top&id=" + top_level + "&name=" + $('#top_level option:checked').text();
		}else{
			return false;
		}
	}
}

/* 중분류 삭제(프론트에서 확인) */
function deleteMiddleLevel(){
	let middle_level = $('#middle_level').val();
	if(middle_level == 'middle_level_value' || middle_level == 'middle_level_input'){
		alert("삭제하실 중분류를 선택하세요.")
	}else{
		let answer = confirm("중분류를 삭제하시면 중분류에 해당되는 전사규칙들이 모두 삭제됩니다. 그래도 삭제하시겠습니까?");
		if(answer){
			location.href = contextPath + "/rule/deleteRuleLevel?level=middle&id=" + middle_level + "&name=" + $('#middle_level option:checked').text();
		}else{
			return false;
		}
	}
}