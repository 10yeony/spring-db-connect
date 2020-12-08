var contextPath;
const editor = document.getElementById('editor');
const selectionOutput = document.getElementById('selection');

$(function(){

	/* Context Path */
	contextPath = getContextPath();
	
	/* 메세지 체크 후 띄우기 */
	var msg = $('#msg').val();
	if(msg != ''){
		alert(msg);
	}
	
	$('#deleteRuleBtn').click(function(){
		let bottom_level_id = $('#bottom_level_id').val();
		location.href = contextPath + '/rule/deleteRuleLevel?level=bottom&id='+bottom_level_id;
	});
	
	$('#backRuleBtn').click(function(){
		location.href = contextPath + '/rule/ruleList';
	});
	
});

/* ContextPath를 가져옴 */
function getContextPath() {
    let contextPath = $('#contextPath').val();
    return contextPath;
}

/* 라이브 코드 하이라이트 변환 */
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

editor.addEventListener('input', updateEditor);

function updateEditor() {
    const sel = window.getSelection();
    const textSegments = getTextSegments(editor);
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
    
    editor.innerHTML = renderText(textContent);
    
    restoreSelection(anchorIndex, focusIndex);
}

function restoreSelection(absoluteAnchorIndex, absoluteFocusIndex) {
    const sel = window.getSelection();
    const textSegments = getTextSegments(editor);
    let anchorNode = editor;
    let anchorIndex = 0;
    let focusNode = editor;
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
        if (word === 'return') {
            return `<strong>${word}</strong>`;
        }
        else if (word === 'String') {
            return `<span style='color:red'>${word}</span>`;
        }
        else {
            return word;
        }
    })
    return output.join('');
}

updateEditor();