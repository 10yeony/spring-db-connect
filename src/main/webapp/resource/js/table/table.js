var contextPath = '';
var requestUrl = '';

$(function(){
	/* Context Path */
	contextPath = $('#contextPath').val();
	
	/* Request URL */
	requestUrl = $('#requestUrl').val();
	
	/* 화면 세팅을 위한 변수 선언 */
	var data_type = $('#show_data_type').val();
	var count_per_list = $('#show_count_per_list').val();
	
	/* 선택한 데이터 타입 세팅 */
	$('#dataSelect').val(data_type);
	
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
		search();
	});
	$("#inputSearchText").keydown(function(event) {
		if(event.keyCode == 13){
			search();
		}
	});
	
	/* 데이터 타입 선택 */
	$('#dataSelect').change(function(){
		let selectOption = $(this).val();
		location.href = contextPath + "/" + requestUrl
							+ "?data=" + selectOption
							+ "&current_page_no=1"
							+ "&count_per_page=" + $('#show_count_per_page').val()
							+ "&count_per_list=" + $('#show_count_per_list').val()
							+ "&search_word=";
	});
	
	/* 10개씩, 20개씩, 30개씩 보기 */
	$('input[name=views]').change(function(){
		let check = $(this).val();
		if(check == '10views'){
			setListSize(10);
		}else if(check == '20views'){
			setListSize(20);
		}else if(check == '30views'){
			setListSize(30);
		}
	});
	$('#10viewsSpan').click(function(){
		$('input[value=10views]').prop("checked", true);
		if($('input[value=10views]').is(":checked")){
			setListSize(10);
		}
	});
	$('#20viewsSpan').click(function(){
		$('input[value=20views]').prop("checked", true);
		if($('input[value=20views]').is(":checked")){
			setListSize(20);
		}
	});
	$('#30viewsSpan').click(function(){
		$('input[value=30views]').prop("checked", true);
		if($('input[value=30views]').is(":checked")){
			setListSize(30);
		}
	});
})

function search(){
	let searchWord = $("#inputSearchText").val();
	location.href = contextPath + "/" + requestUrl
						+ "?data=" + $('#show_data_type').val()
						+ "&current_page_no=1"
						+ "&count_per_page=" + $('#show_count_per_page').val()
						+ "&count_per_list=" + $('#show_count_per_list').val()
						+ "&search_word=" + searchWord;
}

function getTable(currentPageNo){
	if(currentPageNo === undefined){
		currentPageNo = "1";
	}
	location.href = contextPath + "/" + requestUrl
				+ "?data=" + $('#show_data_type').val()
				+ "&current_page_no=" + currentPageNo
				+ "&count_per_page=" + $('#show_count_per_page').val()
				+ "&count_per_list=" + $('#show_count_per_list').val()
				+ "&search_word=" + $('#show_search_word').val();
}

function setListSize(size){
	location.href = contextPath + "/" + requestUrl
				+ "?data=" + $('#show_data_type').val()
				+ "&current_page_no=" + 1
				+ "&count_per_page=" + $('#show_count_per_page').val()
				+ "&count_per_list=" + size
				+ "&search_word=" + $('#show_search_word').val();
}