var ruleChangeSize;
var myCodeMirror;
var imp_myCodeMirror;

$(function(){
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
	//runCodemirror();
});

function markKeyword(className, keyword1, keyword2){
	let before = $(className)[0]
	let instance = new Mark(before);
	instance.mark(keyword1);
	
	let after = $(className)[1]; 
	instance = new Mark(after);
	instance.mark(keyword2);
}