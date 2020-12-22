var contextPath = '';

$(function(){
	/* Context Path */
	contextPath = getContextPath();
	
	/* 화면 세팅을 위한 변수 선언 */
	var count_per_list = $('#show_count_per_list').val();
		
	/* 한 페이지당 몇개씩 보는지 세팅 */
	if(count_per_list==10){
		$('input[value=10views]').prop("checked", true);
	}else if(count_per_list==20){
		$('input[value=20views]').prop("checked", true);
	}else if(count_per_list==30){
		$('input[value=30views]').prop("checked", true);
	}
		
	/* 검색 기능 (클릭, 엔터) */
	$('#inputSearchButton').click(function(){
		searchUsingLog();
	});
	$("#inputSearchText").keydown(function(event) {
		if(event.keyCode == 13){
			searchJsonLog();
		}
	});
		
	/* 10개씩, 20개씩, 30개씩 보기 */
	$('input[name=views]').change(function(){
		let check = $(this).val();
		if(check == '10views'){
			setJsonLogListSize(10);
		}else if(check == '20views'){
			setJsonLogListSize(20);
		}else if(check == '30views'){
			setJsonLogListSize(30);
		}
	});
	$('#10viewsSpan').click(function(){
		$('input[value=10views]').prop("checked", true);
		if($('input[value=10views]').is(":checked")){
			setJsonLogListSize(10);
		}
	});
	$('#20viewsSpan').click(function(){
		$('input[value=20views]').prop("checked", true);
		if($('input[value=20views]').is(":checked")){
			setJsonLogListSize(20);
		}
	});
	$('#30viewsSpan').click(function(){
		$('input[value=30views]').prop("checked", true);
		if($('input[value=30views]').is(":checked")){
			setJsonLogListSize(30);
		}
	});
}); //ready

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}

function searchUsingLog(){
	let searchWord = $("#inputSearchText").val();
	location.href = contextPath
							+ "/getUsingLog?function_name=getUsingLog"
							+ "&current_page_no=1"
							+ "&count_per_page=" + $('#show_count_per_page').val()
							+ "&count_per_list=" + $('#show_count_per_list').val()
							+ "&search_word=" + searchWord;
}
	
function getUsingLog(currentPageNo){
	if(currentPageNo === undefined){
		currentPageNo = "1";
	}
	location.href = contextPath 
					+ "/getUsingLog?function_name=getUsingLog"
					+ "&current_page_no=" + currentPageNo
					+ "&count_per_page=" + $('#show_count_per_page').val()
					+ "&count_per_list=" + $('#show_count_per_list').val()
					+ "&search_word=" + $('#show_search_word').val();
	}

function setJsonLogListSize(size){
	location.href = contextPath
					+ "/getUsingLog?function_name=getUsingLog"
					+ "&current_page_no=" + 1
					+ "&count_per_page=" + $('#show_count_per_page').val()
					+ "&count_per_list=" + size
					+ "&search_word=" + $('#show_search_word').val();
}