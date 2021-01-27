var contextPath;
var runRule;
var list;
var ruleList;

$(document).ajaxStart(function (){
	$('#loadingArea').show();
});
$(document).ajaxStop(function (){
	$('#loadingArea').hide();
});

$(function(){
	/* Context Path */
	contextPath = $('#contextPath').val();
	
	/* 룰 실행 여부 */
	runRule = false;
	
	ruleList = [];
	
	$('#run_rule_btn').click(function(event){
		event.preventDefault();

		let top_level = $('#top_level').val();
		let middle_level = $('#middle_level').val();
		let bottom_level = $('#bottom_level').val();

		if(top_level == '' || top_level == 'top_level_value'){
			$('#run_rule_result_area').empty();
			$('#run_rule_result_area').append(
				'<br/>' +
				'<textarea class="form-control" rows="6" style="resize: none;" readonly>' +
					'대분류를 입력하세요.' +
				'</textarea>'
			);
			return;
		} else if(middle_level == 'middle_level_value'){
			middle_level = '';
			bottom_level = '';
		} else if(bottom_level == 'bottom_level_value'){
			bottom_level = '';
		}
		runRuleCompiler(top_level, middle_level, bottom_level)
	});

});

function runRuleCompiler(top_level, middle_level, bottom_level){
	$.ajax({
		//요청
		type: "POST",
		url: contextPath + "/rule/runRuleCompiler",
		data: {
			top_level_id: top_level,
			middle_level_id: middle_level,
			bottom_level_id: bottom_level
		},

		//응답
		success : function(response){
			var json = JSON.parse(response);
			list = json.item.list;
			ruleList =[];
			$('#run_rule_result_area').empty();
			appendRunRuleResultArea(list);
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}

function appendRunRuleResultArea(list){
	runRule = true;

	if(list == ''){
		$('#run_rule_result_area').append(
			'<br/>' +
			'<textarea class="form-control" rows="6" style="resize: none;" readonly>' +
				'작성된 전사규칙이 없습니다.' + 
			'</textarea>'
		);
	}else{
		$('#run_rule_result_area').append(
			'<iframe name="ifrm" width="0" height="0" frameborder="0"></iframe>' +
			'<hr>' +
			'<b style="float:right">선택한 순서대로 보고서가 만들어집니다.</b><br>' +
				'<span style="float: right;">' +
					'<label>' +
						'<input type="checkbox" data-toggle="checkbox" onchange="checkAllRuleResult(event)"> 전체선택' +
					'</label>' +
				'</span>'
		);
	}

	for(let i=0; i<list.length; i++){
		var top_level_name = list[i].top_level_name;
				
		var middle_level_name = list[i].middle_level_name;
				
		var bottom_level_id = list[i].bottom_level_id;
				
		var bottom_level_name = list[i].bottom_level_name;
		
		var result = list[i].result;

		var rule_type = list[i].rule_type;

		var imp_contents = list[i].imp_contents;

		var append = 
			'<br xmlns="http://www.w3.org/1999/html"/>' +
			'<hr>' + 
			'<div style="margin-bottom:5px;">' +
				'<b>대분류 : </b>' + top_level_name + 
				'<span style="float: right;">' +
					'<label>' +
						'<input type="checkbox" data-toggle="checkbox" name="ruleReport" onclick="clickChkBox('+ bottom_level_id +')" value="'+ bottom_level_id +'"> 선택' +
					'</label>' +
				'</span><br/>' +
				'<b>중분류 : </b>' + middle_level_name + '<br/>' +
				'<b>소분류 : </b>' + bottom_level_name + '<br/>'+
				'<b>타입 : </b>' + rule_type + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
			'</div><br/><br/>';

		if(rule_type == 'method'){
			$('#run_rule_result_area').append(
				append +
				'<textarea class="form-control" rows="6" style="resize: none;" readonly>' +
					result +
				'</textarea>'
			);
		}
		else if((rule_type == 'sql' && result == '')||result==null){
			$('#run_rule_result_area').append(
				append +
				'<textarea class="form-control" rows="6" style="resize: none;" readonly>' +
					imp_contents +
				'</textarea>'
			);
		}
		else if(rule_type == 'sql' && result != ''){
			var resultList = result.substring(2, result.length-2).split('], [');
			for(let j=0; j<resultList.length; j++){
				resultList[j] = resultList[j].split(', ');
			}
			
			append += 
				'<textarea class="form-control" rows="6" style="resize: none;" readonly>' +
					imp_contents +
				'</textarea>' +
				'<br>' +
				'<div class="table-responsive">' +
					'<b>전체 테이블은 워드를 다운받아 확인할 수 있습니다.</b><br>' +
					'<table class="table table-bordered" width="100%" cellspacing="0">' + 
						'<thead>'+
							'<tr>';
			for(let j=0; j<resultList[0].length; j++){
				append += '<td>'+ resultList[0][j] +'</td>';
			}
			append += '</tr></thead><tbody>';

			var end = 5;
			if(resultList.length<5)
				end = resultList.length;

			for(let a=1; a<end; a++){
				append += '<tr>';
				for(let j=0; j<resultList[a].length; j++)
					append += '<td>'+ resultList[a][j] +'</td>';
				append += '</tr>';
			}

			append += '</tbody></table></div>';

			var tableList = result;
			$('#run_rule_result_area').append(append);
		}
	}
}

function checkAllRuleResult(event){
	ruleList = [];
	let ruleReport = $('input[name=ruleReport]');
	if(event.target.checked){
		$('input[name=ruleReport]').prop('checked', true);
		for(let i=0; i<ruleReport.length; i++){
			if(ruleReport[i].checked){
				ruleList.push(ruleReport[i].value);
			}
		}
	}else{
		$('input[name=ruleReport]').prop('checked', false);
	}
}

// 선택한 순서대로 룰 보고서가 만들어지도록 컨트롤러에 넘길때 선택한 순으로 정렬된 배열을 넘김
function clickChkBox(idx){
	if(ruleList.indexOf(idx)>=0){
		ruleList.splice(ruleList.indexOf(idx),1);
	}
	else{
		ruleList.push(idx);
	}
}

function checkRunRule(page){
	if(!click) return ;
	overClick();
	if(runRule || page == 'nonRunRulePage'){
		if(ruleList.length == 0){
			alert("다운받을 룰 실행 결과를 선택해주세요.");
			return;
		}
		$("input[type=hidden][name=hiddenRule]").val(ruleList);
		alert("곧 보고서가 다운로드됩니다.");
		document.getElementById('ruleForm').submit()
	}else{
		alert("룰을 실행해주세요");
	}
}

// 다운버튼 더블클릭시 폼이 여러번 제출되어 발생하는 에러 해결
var click = true;
function overClick() {
	if (click) {
		click = !click;
		// 2초뒤 다시 click = true
		setTimeout(function () {
			click = true;
		}, 2000)
	}
}

