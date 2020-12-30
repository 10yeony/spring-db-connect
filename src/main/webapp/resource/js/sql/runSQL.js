var contextPath = '';
var myCodeMirror;

window.onload = function() {
    runCodemirror();
}

function runCodemirror(){
    var contents_textarea = document.getElementById("query");

    myCodeMirror = CodeMirror.fromTextArea(contents_textarea, {
        mode: "text/x-pgsql",
        lineNumbers: true,
        lineWrapping: true,
        styleActiveLine: true,
        matchBrackets: true,
        theme: 'duotone-light'
    });

    myCodeMirror.setSize(null,150);
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
                $('#show_result_after_update textarea').append(list.length-1 + '개의 데이터가 검색되었습니다.');
                $('#select_table').empty();
                append += '<br><div class="table-responsive">' +
                    '<b>결과 테이블</b><br><br>' +
                    '<table class="table table-bordered" width="100%" cellspacing="0">';
                for(var i=0; i<list.length; i++){
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
                }
                append += '</div></table>';
                $('#select_table').append(append);
            }
            else {
                $('#select_table').empty();
                $('#show_result_after_update textarea').empty();
                $('#show_result_after_update textarea').append(json.item);
            }
        }
    })
}