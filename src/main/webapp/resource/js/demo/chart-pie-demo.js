// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

var contextPath;
var pieDataList;

$(function(){
	/* Context Path */
	contextPath = getContextPath();
	
	getRatioOnMetadataByType();

	// Pie Chart Example
	var ctx = document.getElementById("myPieChart");
	var myPieChart = new Chart(ctx, {
	  type: 'doughnut',
	  data: {
	    labels: ["한국어 강의", "회의 음성", "고객 응대", "상담 음성"],
	    datasets: [{
	      data: [pieDataList.korean_lecture, pieDataList.meeting_audio, pieDataList.customer_reception, pieDataList.counsel_audio],
	      backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc', '#FF7E7E'],
	      hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf', '#DD464C'],
	      hoverBorderColor: "rgba(234, 236, 244, 1)",
	    }],
	  },
	  options: {
	    maintainAspectRatio: false,
	    tooltips: {
	      backgroundColor: "rgb(255,255,255)",
	      bodyFontColor: "#858796",
	      borderColor: '#dddfeb',
	      borderWidth: 1,
	      xPadding: 15,
	      yPadding: 15,
	      displayColors: false,
	      caretPadding: 10,
	    },
	    legend: {
	      display: false
	    },
	    cutoutPercentage: 80,
	  },
	});
});

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}

function getRatioOnMetadataByType(){
	$.ajax({
		//요청
		type: "GET",
		url: contextPath + "/chart/getRatioOnMetadataByType", 
		async: false,
			
		//응답
		success : function(response){  
			pieDataList = response;
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	});
}