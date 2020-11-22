var contextPath = '';

$(function(){

	/* Context Path */
	contextPath = getContextPath();
	
	$("#memberSearchText").keyup(function() {
		let k = $(this).val();
		$("#memberTable > tbody > tr").hide();
		let temp = $("#memberTable > tbody > tr > td:contains('" + k + "')");

		$(temp).parent().show();
	})
	
	$('#roleSelect').change(function(){
		let role = $(this).val();
		location.href = contextPath + "/getMemberListByAdmin?role=" + role;
	});
});

function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}
