var contextPath;

$(function(){
	/* Context Path */
	contextPath = getContextPath();

	$('#top_level').append(
		'<option value="top_level_input">직접 입력</option>'
	);
	
	$('#top_level').change(function(){
		var top_level_id = $(this).val();
		if(top_level_id == 'top_level_input'){
			alert("직접 입력");
		}
	});
});

/* ContextPath를 가져옴 */
function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}