var contextPath = '';

$(function(){

	/* Context Path */
	contextPath = $('#contextPath').val();
	
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
