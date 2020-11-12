<!doctype html>
<html lang="ko">
<head>
  <style>
    #svg-area {
	  position: relative;
	}
	
	#tooltip {
	  position: absolute;
	  opacity: 0;
	  width: 40px;
	  height: 20px;
	  line-height: 20px;
	  transition: all 0.3s;
	  border-radius: 5px;
	  text-align: center;
	  color: #fff;
	  font-size: 14px;
	  font-weight: bold;
	}
  </style>
  <script src="https://d3js.org/d3.v5.min.js"></script>
</head>
<body>
  <div id="svg-area">
    <div id="tooltip"></div>
  </div>
  <script src="${pageContext.request.contextPath}/resource/js/dashboard/sample/bar-chart.js"></script>
</body>
</html>