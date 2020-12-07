var contextPath;

$(function(){

	/* Context Path */
	contextPath = getContextPath();
	
	/* 메세지 체크 후 띄우기 */
	var msg = $('#msg').val();
	if(msg != ''){
		alert(msg);
	}
	
	$('#deleteRuleBtn').click(function(){
		let bottom_level_id = $('#bottom_level_id').val();
		location.href = contextPath + '/rule/deleteRuleLevel?level=bottom&id='+bottom_level_id;
	});
	
	$('#backRuleBtn').click(function(){
		location.href = contextPath + '/rule/ruleList';
	});
	
});

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}