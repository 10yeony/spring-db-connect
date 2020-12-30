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
    $.ajax({
        url: contextPath + "/runSQL",
        data: { "query" : myCodeMirror.getValue() },
        type: "POST",
        async: false,

        success: function (response){
            var json = JSON.parse(response);
            var type = json.code;

            if(type=='select'){
                var list = json.item;
                var append = "";

                $('#show_result_after_update textarea').empty();
                for(var i=0; i<list.length; i++){
                    append += '<table class="table table-bordered paging-table" width="100%" cellspacing="0">';
                    if(i==0){
                        append += '<thead><tr>'
                        for(var j=0; j<list[i].length; j++){
                            append += '<td>' + list[i][j] + '</td>';
                        }
                        append += '</tr></thead>'
                    }
                    else if(i==1){
                        append += '<tbody><tr>';
                        for(var j=0; j<list[i].length; j++){
                            append += '<td>' + list[i][j] + '</td>';
                        }
                        append += '</tr>';
                    }
                    else if(i == list.length-1){
                        append += '<tr>';
                        for(var j=0; j<list[i].length; j++){
                            append += '<td>' + list[i][j] + '</td>';
                        }
                        append += '</tr></tbody>';
                    }
                    else{
                        append += '<tr>';
                        for(var j=0; j<list[i].length; j++){
                            append += '<td>' + list[i][j] + '</td>';
                        }
                        append += '</tr>';
                    }
                    append += '</table>';
                }
                console.log(append);
                $('#show_result_after_update textarea').append(append);
            }
            else {
                $('#show_result_after_update textarea').empty();
                $('#show_result_after_update textarea').append(json.item);
            }
        }
    })
}