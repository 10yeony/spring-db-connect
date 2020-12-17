var contextPath;

$(function(){
	/* Context Path */
	contextPath = getContextPath();
	
	getAllCustomByCreator();
});

function customUpload() {
	var res = '';

	var formData = new FormData($('#customUpload')[0]);

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
				$('#library-content').append(
					'<tr>' + 
						'<td>' + 
							(i+1) + 
						'</td>' +
						'<td>' + 
							result[i].file_name +
							'<a href="javascript:deleteCustom()" style="margin-left:5px">삭제</a>' +
							'<input type="hidden" value="+ result[i].id +">' + 
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

function deleteCustom(){
	var result = confirm("정말로 삭제하시겠습니까?");
	if(result){
		alert($(this).next().val());
	}
}

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}