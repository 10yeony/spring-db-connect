var ruleChangeSize;
var myCodeMirror;
var imp_myCodeMirror;

$(function(){
	let rule_type = $('.rule_type')[0].value;
	if(rule_type == 'sql'){
		runCodemirror('sql');
	}else if(rule_type == 'method'){
		runCodemirror('method');
	}
	
	ruleChangeSize = $('input[name=ruleChangeSize]').val();
	let ruleChangeTitle = $('.ruleChangeTitle');
	if(ruleChangeSize == 2){
		ruleChangeTitle[0].innerHTML = '수정 전 버전';
		ruleChangeTitle[1].innerHTML = '수정 후 버전';
		
		let rule_date = $('.rule_date');
		if(rule_date[0].innerHTML != rule_date[1].innerHTML){
			markKeyword('.rule_date', rule_date[0].innerHTML, rule_date[1].innerHTML);
		}
		let top_level_name = $('.top_level_name');
		if(top_level_name[0].innerHTML != top_level_name[1].innerHTML){
			markKeyword('.top_level_name', top_level_name[0].innerHTML, top_level_name[1].innerHTML);
		}
		let middle_level_name = $('.middle_level_name');
		if(middle_level_name[0].innerHTML != middle_level_name[1].innerHTML){
			markKeyword('.middle_level_name', middle_level_name[0].innerHTML, middle_level_name[1].innerHTML);
		}
		let bottom_level_name = $('.bottom_level_name');
		if(bottom_level_name[0].innerHTML != bottom_level_name[1].innerHTML){
			markKeyword('.bottom_level_name', bottom_level_name[0].innerHTML, bottom_level_name[1].innerHTML);
		}
		let rule_version = $('.rule_version');
		if(rule_version[0].innerHTML != rule_version[1].innerHTML){
			markKeyword('.rule_version', rule_version[0].innerHTML, rule_version[1].innerHTML);
		}
		let rule_description = $('.rule_description');
		if(rule_description[0].innerHTML != rule_description[1].innerHTML){
			markKeyword('.rule_description', rule_description[0].innerHTML, rule_description[1].innerHTML);
		}
	}else{
		ruleChangeTitle[0].innerHTML = '최초 버전';
	}
});

function markKeyword(className, keyword1, keyword2){
	let before = $(className)[0]
	let instance = new Mark(before);
	instance.mark(keyword1);
	
	let after = $(className)[1]; 
	instance = new Mark(after);
	instance.mark(keyword2);
}

function runCodemirror(rule_type){
	ruleChangeSize = $('input[name=ruleChangeSize]').val()
	if(rule_type == 'sql'){
		var contents_textarea = $('.query');
		let leftCode; 
		let rightCode;
		for(let i=0; i<contents_textarea.length; i++){
			myCodeMirror = CodeMirror.fromTextArea(contents_textarea[i], {
		        mode: "text/x-pgsql",
		        lineNumbers: true,
		        lineWrapping: true,
		        styleActiveLine: true,
		        matchBrackets: true,
		        theme: 'duotone-light',
		        readOnly: true
		    });
		    if(i == 0){
		    	leftCode = myCodeMirror;
		    }
		    else if(i == 1){
		    	rightCode = myCodeMirror;
		    }
		}
		
		/* 코드 부분 비교 */
		let minLength;
		if(leftCode.lineCount() - rightCode.lineCount() > 0){
			minLength = rightCode.lineCount();
			compareLeftAndRightCode(minLength, leftCode, rightCode, 'sql_style');
		}else if(leftCode.lineCount() - rightCode.lineCount() < 0){
			minLength = leftCode.lineCount();
			compareLeftAndRightCode(minLength, rightCode, leftCode, 'sql_style');
		}else{
			compareLeftAndRightCode(leftCode.lineCount(), leftCode, rightCode, 'sql_style');
		}
	}else if(rule_type == 'method'){
		var contents_textarea = $('.contents');
		var imp_contents_textarea = $('.imp_contents');
		let leftCode; 
		let rightCode;
		let leftImpCode; 
		let rightImpCode;
		
		for(let i=0; i<contents_textarea.length; i++){
			myCodeMirror = CodeMirror.fromTextArea(contents_textarea[i], {
				mode: "text/x-java",
				lineNumbers: true,
				lineWrapping: true,
				styleActiveLine: true,
				matchBrackets: true,
				theme: "hopscotch",
				readOnly: true
			});
			if(i == 0){
				leftCode = myCodeMirror;
			}else if(i == 1){
				rightCode = myCodeMirror;
			}
		
			imp_myCodeMirror = CodeMirror.fromTextArea(imp_contents_textarea[i], {
				mode: "text/x-java",
				lineNumbers: true,
				lineWrapping: true,
				styleActiveLine: true,
				matchBrackets: true,
				theme: "hopscotch",
				readOnly: true
			});
			imp_myCodeMirror.setSize(null,100);
			if(i == 0){
				leftImpCode = imp_myCodeMirror;
			}else if(i == 1){ 
				rightImpCode = imp_myCodeMirror;
			}
		}
		
		let minLength;
		/* import 부분 비교 */
		if(leftImpCode.lineCount() - rightImpCode.lineCount() > 0){
			minLength = rightImpCode.lineCount();
			compareLeftAndRightCode(minLength, leftImpCode, rightImpCode, 'method_style');
		}else if(leftImpCode.lineCount() - rightImpCode.lineCount() < 0){
			minLength = leftImpCode.lineCount();
			compareLeftAndRightCode(minLength, rightImpCode, leftImpCode, 'method_style');
		}else{
			compareLeftAndRightCode(leftImpCode.lineCount(), leftImpCode, rightImpCode, 'method_style');
		}
		
		/* 코드 부분 비교 */
		if(leftCode.lineCount() - rightCode.lineCount() > 0){
			minLength = rightCode.lineCount();
			compareLeftAndRightCode(minLength, leftCode, rightCode, 'method_style');
		}else if(leftCode.lineCount() - rightCode.lineCount() < 0){
			minLength = leftCode.lineCount();
			compareLeftAndRightCode(minLength, rightCode, leftCode, 'method_style');
		}else{
			compareLeftAndRightCode(leftCode.lineCount(), leftCode, rightCode, 'method_style');
		}
	}
}

function compareLeftAndRightCode(minLength, BigObj, SmallObj, className){
	for(let i=0; i<minLength; i++){
		if(BigObj.getLine(i) != SmallObj.getLine(i)){
			BigObj.markText({line:i, ch:0}, {line:i, ch:BigObj.getLine(i).length}, {className:className});
			SmallObj.markText({line:i, ch:0}, {line:i, ch:SmallObj.getLine(i).length}, {className:className});
		}
	}
	for(let i=minLength; i<BigObj.lineCount(); i++){
		BigObj.markText({line:i, ch:0}, {line:i, ch:BigObj.getLine(i).length}, {className:className});
	}
}