var contextPath = '';
var myCodeMirror;

window.onload = function() {
    runCodemirror();
}

function runCodemirror(){
    var contents_textarea = document.getElementById("query");

    myCodeMirror = CodeMirror.fromTextArea(contents_textarea, {
        mode: "text/x-java",
        lineNumbers: true,
        lineWrapping: true,
        styleActiveLine: true,
        matchBrackets: true
    });
}

$(function(){
    /* Context Path */
    contextPath = $('#contextPath').val();
})

function runSQL(){
    console.log("runSQL start");
    $.ajax({
        url: contextPath + "/runSQL",
        data: { "query" : myCodeMirror.getValue() },
        type: "POST",
        async: false
    })
}