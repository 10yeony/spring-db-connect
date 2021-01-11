var contextPath = '';
var requestUrl = '';

$(function(){
	/* Context Path */
	contextPath = $('#contextPath').val();
	
	/* 화면 세팅을 위한 변수 선언 */
	$('#current_page_no').val(1);
	$('#show_count_per_page').val(10);
	$('#show_count_per_list').val(10);
	
	/* 한 페이지당 몇개씩 보는지 세팅 */
	if($('#show_count_per_list').val()==10){
		$('input[value=10views]').prop("checked", true);
	}else if($('#show_count_per_list').val()==20){
		$('input[value=20views]').prop("checked", true);
	}else if($('#show_count_per_list').val()==30){
		$('input[value=30views]').prop("checked", true);
	}
	
	/* 검색 기능 (클릭, 엔터) */
	$('#inputSearchButton').click(function(){
		startPagingHandling();
	});
	$("#inputSearchText").keydown(function(event) {
		if(event.keyCode == 13){
			startPagingHandling();
		}
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

function getTable(currentPageNo){
	if(currentPageNo === undefined){
		$('#current_page_no').val(1);
	}else{
		$('#current_page_no').val(currentPageNo);
	}
	startPagingHandling();
}

function setListSize(size){
	$('#show_count_per_list').val(size);
	startPagingHandling();
}

function markKeyword(keyword){
	var context = document.querySelector(".paging-table"); // requires an element with class "context" to exist
	var instance = new Mark(context);
	instance.mark(keyword);
}

function setEmptyPagingValue(){
	if($('#current_page_no').val() == ''){
		$('#current_page_no').val(1);
	}
	if($('#show_count_per_page').val() == ''){
		$('#show_count_per_page').val(10);
	}
	if($('#show_count_per_list').val() == ''){
		$('#show_count_per_list').val(10);		
	}
}

function getPagingResult(requestUrl, data){
	setEmptyPagingValue();
	data.current_page_no = $('#current_page_no').val();
	data.count_per_page = $('#show_count_per_page').val();
	data.count_per_list = $('#show_count_per_list').val();
	data.search_word = $("#inputSearchText").val();
	$.ajax({
		//요청
		type: "GET",
		url: contextPath + requestUrl,
		data: data, 
			
		//응답
		success : function(response){  
			var json = JSON.parse(response);
			$('#show_count_per_page').val(json.count_per_page);
			$('#show_count_per_list').val(json.count_per_list);
			$('#pagination').empty();
			$('#pagination').append(json.pagination);
			appendTable(json.result);
			searchResultArea(json.search_word, json.totalCount);
			markKeyword($("#inputSearchText").val());
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}

function clickBackBtn(){
	window.history.back();
}

function fnExcelReport(id) {
	let today = new Date();
	var title = $('#title').val();

	let year = today.getFullYear(); // 년도
	let month = today.getMonth() + 1;  // 월
	let date = today.getDate();  // 날짜
	let hours = today.getHours(); // 시
	let minutes = today.getMinutes();  // 분

	var time = year + '-' +month+'-'+date+' '+hours+";"+minutes;

	var tab_text = '<html xmlns:x="urn:schemas-microsoft-com:office:excel">';
	tab_text = tab_text + '<head><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8">';
	tab_text = tab_text + '<xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>'
	tab_text = tab_text + '<x:Name>'+title+ '</x:Name>';
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

	$.ajax({
		url: contextPath + "/downloadExcel",
		data : "title=" + title,
		type: "POST"
	})
}