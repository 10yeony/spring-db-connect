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
	var search_word = $('#show_search_word').val();
	var approval = $('#show_approval').val();
	
	markKeyword(search_word);
	
	/* 선택한 데이터 타입 세팅 */
	$('#dataSelect').val(data_type);
	$('#approvalSelect').val(approval);
	
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
		let link = contextPath + "/" + requestUrl
					+ "?data=" + selectOption
					+ "&current_page_no=1"
					+ "&count_per_page=" + $('#show_count_per_page').val()
					+ "&count_per_list=" + $('#show_count_per_list').val()
					+ "&search_word=";
		if(requestUrl == 'getMemberListByAdmin'){
			link += "&approval=" + $('#show_approval').val();
		}
		location.href = link;
	});
	
	/* 사용자 목록 - 승인여부 선택 */
	$('#approvalSelect').change(function(){
		let selectOption = $(this).val();
		let link = contextPath + "/" + requestUrl
					+ "?data=" + $('#show_data_type').val()
					+ "&current_page_no=1"
					+ "&count_per_page=" + $('#show_count_per_page').val()
					+ "&count_per_list=" + $('#show_count_per_list').val()
					+ "&search_word="
					+ "&approval=" + selectOption;
		location.href = link;
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
	let link = contextPath + "/" + requestUrl
				+ "?data=" + $('#show_data_type').val()
				+ "&current_page_no=1"
				+ "&count_per_page=" + $('#show_count_per_page').val()
				+ "&count_per_list=" + $('#show_count_per_list').val()
				+ "&search_word=" + searchWord;
	if(requestUrl == 'getMemberListByAdmin'){
		link += "&approval=" + $('#show_approval').val();
	}
	location.href = link;
}

function getTable(currentPageNo){
	if(currentPageNo === undefined){
		currentPageNo = "1";
	}
	let link = contextPath + "/" + requestUrl
				+ "?data=" + $('#show_data_type').val()
				+ "&current_page_no=" + currentPageNo
				+ "&count_per_page=" + $('#show_count_per_page').val()
				+ "&count_per_list=" + $('#show_count_per_list').val()
				+ "&search_word=" + $('#show_search_word').val();
	if(requestUrl == 'getMemberListByAdmin'){
		link += "&approval=" + $('#show_approval').val();
	}
	location.href = link;
}

function setListSize(size){
	let link = contextPath + "/" + requestUrl
				+ "?data=" + $('#show_data_type').val()
				+ "&current_page_no=" + 1
				+ "&count_per_page=" + $('#show_count_per_page').val()
				+ "&count_per_list=" + size
				+ "&search_word=" + $('#show_search_word').val();
	if(requestUrl == 'getMemberListByAdmin'){
		link += "&approval=" + $('#show_approval').val();
	}
	location.href = link;
}

function markKeyword(keyword){
	var context = document.querySelector(".paging-table"); // requires an element with class "context" to exist
	var instance = new Mark(context);
	instance.mark(keyword);
}

function clickBackBtn(){
	window.history.back();
}

function fnExcelReport(id, title) {
	let today = new Date();

	let year = today.getFullYear(); // 년도
	let month = today.getMonth() + 1;  // 월
	let date = today.getDate();  // 날짜
	let hours = today.getHours(); // 시
	let minutes = today.getMinutes();  // 분

	var time = year + '-' +month+'-'+date+' '+hours+";"+minutes;

	var tab_text = '<html xmlns:x="urn:schemas-microsoft-com:office:excel">';
	tab_text = tab_text + '<head><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8">';
	tab_text = tab_text + '<xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>'
	tab_text = tab_text + '<x:Name>Test Sheet</x:Name>';
	tab_text = tab_text + '<x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions></x:ExcelWorksheet>';
	tab_text = tab_text + '</x:ExcelWorksheets></x:ExcelWorkbook></xml></head><body>';
	tab_text = tab_text + "<table border='1px'>";
	var exportTable = $('#' + id).clone();
	exportTable.find('input').each(function (index, elem) { $(elem).remove(); });
	tab_text = tab_text + exportTable.html();
	tab_text = tab_text + '</table></body></html>';
	var data_type = 'data:application/vnd.ms-excel';
	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE ");
	var fileName = title + "_" + time + '.xls';

//Explorer 환경에서 다운로드
	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) {
		if (window.navigator.msSaveBlob) {
			var blob = new Blob([tab_text], {
				type: "application/csv;charset=utf-8;"
			});
			navigator.msSaveBlob(blob, fileName);
		}
	} else {
		var blob2 = new Blob([tab_text], {
			type: "application/csv;charset=utf-8;"
		});
		var filename = fileName;
		var elem = window.document.createElement('a');
		elem.href = window.URL.createObjectURL(blob2);
		elem.download = filename;
		document.body.appendChild(elem);
		elem.click();
		document.body.removeChild(elem);
	}
}