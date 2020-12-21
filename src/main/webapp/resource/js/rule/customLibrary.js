var contextPath;

$(function(){
	/* Context Path */
	contextPath = getContextPath();
	
	getAllCustomByCreator();
});

function customUpload(fileType) {
	var res = '';
	var formData;

	if(fileType == 'jar'){
		formData = new FormData($('#customJarUpload')[0]);
		
		// jar 파일 형식 확인
	   for(var item of formData.entries()) {
	   		var name = item[1]["name"];
	   		if(name != undefined){
	   			if(name.substring(name.lastIndexOf('.'), name.length) != '.jar'){
					console.log("error");
					alert("jar 파일을 업로드해주세요.");
					return;
				}
	   		}
		}
	}
	else if(fileType == 'class'){
		formData = new FormData($('#customClassUpload')[0]);
		
		// class 파일 형식 확인
		var name;
		for(var item of formData.entries()) {
	   		if(item[1]["name"] != undefined){
	   			name = item[1]["name"];
	   			if(name.substring(name.lastIndexOf('.'), name.length) != '.class'){
					console.log("error");
					alert("class 파일을 업로드해주세요.");
					return;
				}
	   		}
		}
		
		let packageName = $('#pack').val();
		let pattern_check = /^[a-zA-Z0-9.$_]{1,}$/; // 패키지명으로 사용가능한 문자 모음

		if(packageName == ''){
			alert("패키지명을 입력하세요");
			return;
		}
		else if(!isNaN(packageName.substring(0, 1))){
			alert("숫자로 시작하는 패키지명은 사용할 수 없습니다");
			return;
		}
		else if(!pattern_check.test(packageName)){
			alert("_ 또는 $를 제외한 특수문자는 패키지명으로 사용할 수 없습니다");
			return;
		}
		else if(packageName.substring(0, 4) == 'java'){
			alert("java로 시작하는 패키지명은 사용할 수 없습니다");
			return;
		}
		else if(packageName.lastIndexOf('.') == -1){
			alert("도트(.)가 누락되었습니다");
			return;
		}
		else if(name.substring(0, name.lastIndexOf('.')) != packageName.substring(packageName.lastIndexOf('.')+1, packageName.length)){
			alert("패키지명에서 클래스 이름을 바르게 입력하세요");
			return;
		}
	}
	
	$.ajax({
		type:"POST",
		enctype: 'multipart/form-data', 
		url: contextPath + "/rule/uploadCustom",
		data: formData,
		processData: false,
		contentType: false,
		cache: false,
		
		success:function (result){
			res = result;
			if(res == 'true'){
				alert("파일을 성공적으로 업로드했습니다.")
				document.getElementById('loadingArea').style.display='none';
				getAllCustomByCreator();
			}
			
			else if(res == 'false'){
				alert("이미 업로드한 파일입니다.") 
				document.getElementById('loadingArea').style.display='none';
			}
		},
		
		error: function (request,status,error){
			//alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			alert("업로드한 파일 용량이 너무 큽니다.");
			document.getElementById('loadingArea').style.display='none';
		}
	});
	document.getElementById('loadingArea').style.display='block';
}

function getAllCustomByCreator(){
	$.ajax({
		type:"GET",
		url: contextPath + "/rule/getAllCustomByCreator",
		
		success:function(result){
			$('#library-content').empty();
			for(let i=0; i<result.length; i++){
				var temp = [];
				temp[0] = result[i].file_name;
				$('#library-content').append(
					'<tr>' + 
						'<td>' + 
							(i+1) + 
						'</td>' +
						'<td>' + 
							result[i].file_name +
							'<a href="javascript:deleteCustom(' + result[i].id + ')" style="margin-left:5px">삭제</a>' +
						'</td>' +
						'<td>' +
							result[i].class_package +
						'</td>' +
					'</tr>'
				);
			}
		},
		
		error: function (request,status,error){
			//alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

function deleteCustom(id){
	var result = confirm("정말로 삭제하시겠습니까?");
	if(result){
		$.ajax({
			//요청
			type: "POST",
			url: contextPath + "/rule/deleteCustomLibrary",
			data: {
				id : id,
			},
	
			//응답
			success : function(response){
				if(response == 1){
					alert("선택한 라이브러리를 성공적으로 삭제했습니다.");
				}
				getAllCustomByCreator();
			},
			error : function(request, status, error) {
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		}); //ajax
	}
}

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}