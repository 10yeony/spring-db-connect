$(function(){
	var ruleChangeSize = $('input[name=ruleChangeSize]').val();
	var ruleChangeTitle = $('.ruleChangeTitle');
	if(ruleChangeSize == 2){
		ruleChangeTitle[0].innerHTML = '수정 전 버전';
		ruleChangeTitle[1].innerHTML = '수정 후 버전';
	}else{
		ruleChangeTitle[0].innerHTML = '최초 버전';
	}
});