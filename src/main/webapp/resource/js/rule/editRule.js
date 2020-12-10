var contextPath;

$(function(){

	/* Context Path */
	contextPath = getContextPath();
	
	$('#ruleUpdateBtn').click(function(){
		saveRuleContents();
	});
	
	$('#deleteRuleBtn').click(function(){
		let bottom_level_id = $('#bottom_level_id').val();
		location.href = contextPath + '/rule/deleteRuleLevel?level=bottom&id='+bottom_level_id;
	});
	
	$('#backRuleBtn').click(function(){
		location.href = contextPath + '/rule/ruleList';
	});
});

function saveRuleContents(){
	$.ajax({
		//요청
		type: "POST",
		url: contextPath + "/rule/saveRuleContents", 
		data: {
			bottom_level_id : $('#bottom_level_id').val(),
			contents : $('#contents').text()
		},
		datatype: 'html',
		async: false,
			
		//응답
		success : function(response){  
			var json = JSON.parse(response);
			alert(json.message);
			var obj = json.item.obj;
			$('#show_result_after_update textarea').empty();
			$('#show_result_after_update textarea').append(obj);
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

/* code highlight */
const contents = document.getElementById('contents');
const selectionOutput = document.getElementById('selection');

function getTextSegments(element) {
    const textSegments = [];
    Array.from(element.childNodes).forEach((node) => {
        switch(node.nodeType) {
            case Node.TEXT_NODE:
                textSegments.push({text: node.nodeValue, node});
                break;
                
            case Node.ELEMENT_NODE:
                textSegments.splice(textSegments.length, 0, ...(getTextSegments(node)));
                break;
                
            default:
                throw new Error(`Unexpected node type: ${node.nodeType}`);
        }
    });
    return textSegments;
}

contents.addEventListener('input', updateContents);

function updateContents() {
    const sel = window.getSelection();
    const textSegments = getTextSegments(contents);
    const textContent = textSegments.map(({text}) => text).join('');
    let anchorIndex = null;
    let focusIndex = null;
    let currentIndex = 0;
    textSegments.forEach(({text, node}) => {
        if (node === sel.anchorNode) {
            anchorIndex = currentIndex + sel.anchorOffset;
        }
        if (node === sel.focusNode) {
            focusIndex = currentIndex + sel.focusOffset;
        }
        currentIndex += text.length;
    });
    
    contents.innerHTML = renderText(textContent);
    
    restoreSelection(anchorIndex, focusIndex);
}



function restoreSelection(absoluteAnchorIndex, absoluteFocusIndex) {
    const sel = window.getSelection();
    const textSegments = getTextSegments(contents);
    let anchorNode = contents;
    let anchorIndex = 0;
    let focusNode = contents;
    let focusIndex = 0;
    let currentIndex = 0;
    textSegments.forEach(({text, node}) => {
        const startIndexOfNode = currentIndex;
        const endIndexOfNode = startIndexOfNode + text.length;
        if (startIndexOfNode <= absoluteAnchorIndex && absoluteAnchorIndex <= endIndexOfNode) {
            anchorNode = node;
            anchorIndex = absoluteAnchorIndex - startIndexOfNode;
        }
        if (startIndexOfNode <= absoluteFocusIndex && absoluteFocusIndex <= endIndexOfNode) {
            focusNode = node;
            focusIndex = absoluteFocusIndex - startIndexOfNode;
        }
        currentIndex += text.length;
    });
    
    sel.setBaseAndExtent(anchorNode,anchorIndex,focusNode,focusIndex);
}

function renderText(text) {
    const words = text.split(/(\s+)/);
    const output = words.map((word) => {
        if (word === 'System') {
            return `<strong>${word}</strong>`;
        }
        else if (word === 'return') {
            return `<span style='color:brown'>${word}</span>`;
        }
        else if (word === 'String') {
            return `<span style='color:blue'>${word}</span>`;
        }
        else if (word === 'int') {
            return `<span style='color:brown'>${word}</span>`;
        }
        else if (word === 'boolean') {
            return `<span style='color:brown'>${word}</span>`;
        }
        else if (word === 'PostgreDao') {
            return `<span style='color:skyblue'>${word}</span>`;
        }
        else if (word === 'private') {
            return `<span style='color:brown'>${word}</span>`;
        }
        else {
            return word;
        }
    })
    return output.join('');
}

updateContents();