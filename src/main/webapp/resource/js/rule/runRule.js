var contextPath;

$(function(){
	/* Context Path */
	contextPath = getContextPath();
	
	
});

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}