var contextPath;

$(function(){
	/* Context Path */
	contextPath = getContextPath();
	
	getAllCustomByCreator();
});

function customUpload() {
	var res = '';

	var formData = new FormData($('#customUpload')[0]);
	var pack = $('form input[name = pack]').val();
	// 파일 형식 확인
   for(var item of formData.entries()) {
   		var name = item[1]["name"];
	   if(name.substring(name.length-4, name.length) != '.jar' && name.substring(name.length-6, name.length) != '.class'){
			console.log("error");
			alert("jar 파일 또는 class을 업로드해주세요.");
			return;
		}
	}
	
	$.ajax({
		type:"POST",
		enctype: 'multipart/form-data', 
		url: contextPath + "/rule/uploadCustom",
		data: formData, pack,
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