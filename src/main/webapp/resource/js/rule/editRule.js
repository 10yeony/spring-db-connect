var contextPath;

$(function(){

	/* Context Path */
	contextPath = getContextPath();
	
	$('#ruleUpdateBtn').click(function(){
		saveRuleContents();
	});
	
	$('#deleteRuleBtn').click(function(){
		let bottom_level_id = $('#bottom_level_id').val();
		location.href = contextPath + '/rule/deleteRuleLevel?level=bottom&id='+bottom_level_id;
	});
	
	$('#backRuleBtn').click(function(){
		location.href = contextPath + '/rule/ruleList';
	});
});

function saveRuleContents(){
	$.ajax({
		//요청
		type: "POST",
		url: contextPath + "/rule/saveRuleContents", 
		data: {
			bottom_level_id : $('#bottom_level_id').val(),
			contents : $('#contents').text()
		},
		datatype: 'html',
		async: false,
			
		//응답
		success : function(response){  
			var json = JSON.parse(response);
			alert(json.message);
			var obj = json.item.obj;
			$('#show_result_after_update textarea').empty();
			$('#show_result_after_update textarea').append(obj);
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}

