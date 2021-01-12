var contextPath;
var myCodeMirror;
var imp_myCodeMirror;

window.onload = function() {
	runCodemirror();
}

function runCodemirror(){
	var contents_textarea = document.getElementById("contents");
	var imp_contents_textarea = document.getElementById("imp_contents");

	myCodeMirror = CodeMirror.fromTextArea(contents_textarea, {
		mode: "text/x-java",
		lineNumbers: true,
		lineWrapping: true,
		styleActiveLine: true,
		matchBrackets: true,
		theme: "hopscotch"
	});

	imp_myCodeMirror = CodeMirror.fromTextArea(imp_contents_textarea, {
		mode: "text/x-java",
		lineNumbers: true,
		lineWrapping: true,
		styleActiveLine: true,
		matchBrackets: true,
		theme: "hopscotch"
	});
	imp_myCodeMirror.setSize(null,100);
}

$(document).ajaxStart(function (){
	$('#loadingArea').show();
});
$(document).ajaxStop(function (){
	$('#loadingArea').hide();
});

$(function(){

	/* Context Path */
	contextPath = $('#contextPath').val();
	
	handleDropDown('top', $('input[name="top_level_id"]').val(), null);
	$('#top_level').val($('input[name="top_level_id"]').val());
	handleDropDown('middle', $('input[name="top_level_id"]').val(), $('input[name="middle_level_id"]').val());
	$('#middle_level').val($('input[name="middle_level_id"]').val());

	$('#ruleUpdateBtn').click(function(){
		saveRuleContents();
	});

	$('#deleteRuleBtn').click(function(){
		location.href = contextPath + '/rule/deleteRuleLevel?' + $('#deleteRuleFrm').serialize();
	});

	$('#backRuleBtn').click(function(){
		location.href = contextPath + '/rule/ruleList/0/0/0';
	});
});

function saveRuleContents(){
	if($('#top_level').val() == 'top_level_value'){
		alert("대분류를 선택하세요");
		return;
	}
	if($('#middle_level').val() == 'middle_level_value'){
		alert("중분류를 선택하세요");
		return;
	}
	if($('#ruleInfoName').val() == ''){
		alert("이름을 입력하세요");
		return;
	}

	$.ajax({
		//요청
		type: "POST",
		url: contextPath + "/rule/saveRuleContents",
		data: {
			top_level_id : $('#top_level').val(),
        	middle_level_id : $('#middle_level').val(),
			bottom_level_id: $('#bottom_level_id').val(),
			bottom_level_name : $('#ruleInfoName').val(),
			description : $('#ruleInfoDesc').val(),
          rule_type : $('input[name=rule_type]:checked').val(),
			contents: myCodeMirror.getValue(),
			imp_contents: imp_myCodeMirror.getValue()
		},

		//응답
		success: function (response) {
			var json = JSON.parse(response);
			alert(json.message);
			var obj = json.item.obj;
			$('#show_result_after_update textarea').empty();
			$('#show_result_after_update textarea').append(obj);
		},
		error: function (request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}