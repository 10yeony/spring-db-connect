var contextPath;

$(function(){
	/* Context Path */
	contextPath = getContextPath();
	
	$('#deleteRuleBtn').click(function(){
		alert("룰 삭제");
	});
	
	$('#backRuleBtn').click(function(){
		location.href = contextPath + '/rule/ruleList';
	});
});

/* ContextPath를 가져옴 */
function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}