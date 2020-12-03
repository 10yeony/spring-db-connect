var contextPath;

$(function(){
	/* Context Path */
	contextPath = getContextPath();
	
	var ruleRegSuccessMsg = $('#ruleRegSuccessMsg').val();
	if(ruleRegSuccessMsg != ''){
		alert(ruleRegSuccessMsg);
	}
		
	var ruleRegErrorMsg = $('#ruleRegErrorMsg').val();
	if(ruleRegErrorMsg != ''){
		alert(ruleRegErrorMsg);
	}

	$('#top_level').append(
		'<option id="top_level_input" value="top_level_input">직접 입력</option>'
	);
	
	/* 대분류 드롭다운 핸들링 */
	$('#top_level').change(function(){
		var top_level_id = $(this).val();
		$('input[name=top_level_id]')[0].value = top_level_id;
		$('input[name=top_level_id]')[1].value = top_level_id;
		
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
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}

/* 대분류 삭제 */
function deleteTopLevel(){
	alert("대분류 삭제");
}

/* 중분류 삭제 */
function deleteMiddleLevel(){
	alert("중분류 삭제");
}