var contextPath;

$(function(){
	/* Context Path */
	contextPath = getContextPath();
	
	$('#goToRuleApi').click(function(){
		getApiDesc(1);
	});

	$('#api-class-select').change(function(){
		let class_id = $(this).val();
		getApiDesc(class_id);
		
	});
});

function getApiDesc(class_id){
	$.ajax({
		//요청
		type: "GET",
		url: contextPath + "/rule/getApiDesc", 
		data: {
			class_id: class_id
		},
		async: false,
			
		//응답
		success : function(response){  
			var json = JSON.parse(response);
			
			var api_class = json.item.class;
			$('#class-name').html('Class '+api_class[0].class_name);
			$('#class-description').html(api_class[0].class_description);
			
			$('#class-field').html('');
			var api_field = json.item.field;
			for(let i=0; i<api_field.length; i++){
				$('#class-field').append(
					'<tr>' +
						'<td>' + api_field[i].field_type + '</td>' +
						'<td>' +
							'<div class="class-component-bold">' + api_field[i].field_name + '</div>' +
							'<div>' + api_field[i].field_description + '</div>' +
						'</td>' +
					'</tr>'
				);
			}
			
			$('#class-constructor').html('');
			var api_constructor = json.item.constructor;
			for(let i=0; i<api_constructor.length; i++){
				$('#class-constructor').append(
					'<tr>' + 
						'<td>' +
							'<span class="class-component-bold">' + 
								api_class[0].class_name + 
							'</span>' + 
							'(' + api_constructor[i].constructor_parameter + ')' +
							'<div>' + api_constructor[i].constructor_description + '</div>' +
						'</td>' + 
					'</tr>'
				);
			}
			
			$('#class-method').html('');
			var api_method = json.item.method;
			for(let i=0; i<api_method.length; i++){
				$('#class-method').append(
					'<tr>' +
						'<td>' + api_method[i].method_return_type + '</td>' +
						'<td>' +
							'<span class="class-component-bold">' + 
								api_method[i].method_name + 
							'</span>' + 
							'(' + api_method[i].method_parameter + ')' +
							'<div>' +
								api_method[i].method_description +
							'</div>' + 
						'</td>' + 
					'</tr>'
				);
			}
		},
		error : function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
		}
	}); //ajax
}

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}