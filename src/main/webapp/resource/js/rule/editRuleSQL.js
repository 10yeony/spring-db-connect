var contextPath = '';
var myCodeMirror;
var selectList;
var currentPage;
var startPage = 1;
var endPage;
var end;
var countPerPage = 10;

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

}

$(function(){
    /* Context Path */
    contextPath = $('#contextPath').val();

    $('#deleteRuleBtn').click(function(){
        location.href = contextPath + '/rule/deleteRuleLevel?' + $('#deleteRuleFrm').serialize();
    });

    $('#backRuleBtn').click(function(){
        location.href = contextPath + '/rule/ruleList/0/0/0';
    });
})

function runRuleSQL(){
    var query = myCodeMirror.getValue();
    $.ajax({
        url: contextPath + "/runRuleSQL",
        data: {
            bottom_level_id: $('#bottom_level_id').val(),
            contents : query
        } ,
        type: "POST",

        success: function (response){
            var json = JSON.parse(response);
            var type = json.code;

            if(type=='select'){
                selectList = json.item;
                var append = "";
                endPage = Math.ceil((selectList.length-1)/countPerPage);
                startPage = 1;

                $('#show_result_after_update textarea').empty();
                $('#show_result_after_update textarea').append((selectList.length-1) + '개의 데이터를 조회했습니다.');
                run(1);
            }
            else {
                $('#select_table').empty();
                $('#show_result_after_update textarea').empty();
                $('#show_result_after_update textarea').append(json.item);
            }
        },
        error:function(request,status,error){
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
    $(document).ajaxStart(function (){
        $('#loadingArea').show();
    });
    $(document).ajaxStop(function (){
        $('#loadingArea').hide();
    });
}

function run(current){
    if(current < 1)
        current = 1;
    else if(current > Math.ceil((selectList.length-1)/countPerPage))
        current = Math.ceil((selectList.length-1)/countPerPage);


    currentPage = current;
    $('#select_table').empty();
    var append = "";
    startPage = Math.floor((currentPage-1)/countPerPage)*countPerPage +1;
    endPage = startPage + 9;



    if(Math.ceil((selectList.length-1)/countPerPage)<=endPage){
        endPage = Math.ceil((selectList.length-1)/countPerPage);
    }

    end = currentPage*countPerPage + 1;
    if(end >= selectList.length)
        end = selectList.length;

    append += '<br><div class="table-responsive">' +
        '<b>결과 테이블</b><br><br>' +
        '<table class="table table-bordered" width="100%" cellspacing="0">' +
        '<thead><tr>';
    for(var j=0; j<selectList[0].length; j++){
        append += '<td>' + selectList[0][j] + '</td>';
    }
    append += '</tr></thead>';

    if(selectList.length != 1){
        for(var i = 1 +(currentPage-1)*countPerPage; i<end; i++){
            if(i==1 +(currentPage-1)*countPerPage){
                append += '<tbody><tr>';
                for(var j=0; j<selectList[i].length; j++){
                    append += '<td>' + selectList[i][j] + '</td>';
                }
                append += '</tr>';
            }
            else if(i == end-1){
                append += '<tr>';
                for(var j=0; j<selectList[i].length; j++){
                    append += '<td>' + selectList[i][j] + '</td>';
                }
                append += '</tr></tbody>';
            }
            else{
                append += '<tr>';
                for(var j=0; j<selectList[i].length; j++){
                    append += '<td>' + selectList[i][j] + '</td>';
                }
                append += '</tr>';
            }
        }
    }
    append += '</table></div><br><br>' +
        '<div style="text-align:center">';

    if(selectList.length != 1){
        append += '<a onclick="run(1)" style="cursor: pointer;" class="other_page">[<<]</a>'+'&nbsp;';
        append += '<a onclick="run(' + ((Math.floor((current-1)/countPerPage) - 1)*countPerPage + 1) + ')" style="cursor: pointer;" class="other_page">[<]</a>'+'&nbsp;';

        for(var i=startPage; i<=endPage; i++){
            if(i == currentPage){
                append += '<a onclick="run('+ i +')" class="on_page" style="cursor: pointer;">[' + i + ']</a>';
            }
            else{
                append += '<a onclick="run('+ i +')" class="other_page" style="cursor: pointer;">[' + i + ']</a>';
            }
        }
        append += '<a onclick="run(' + ((Math.floor((current-1) /countPerPage) + 1)*countPerPage + 1) + ')" style="cursor: pointer;" class="other_page">[>]</a>'+'&nbsp;';
        append += '<a onclick="run(' + Math.ceil((selectList.length-1)/countPerPage) + ')" style="cursor: pointer;" class="other_page">[>>]</a>';
    }

    append += '</div>';
    $('#select_table').append(append);
}