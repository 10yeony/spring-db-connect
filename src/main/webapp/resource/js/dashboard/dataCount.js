$(function (){
    $.ajax({
        //요청
        type: "GET",
        url: contextPath + "/chart/getCountData",
        async: false,

        //응답
        success : function(response){
            var json = JSON.parse(response);
            console.log(response);
            $('#metadata').append(json.metadata);
            $('#utterance').append(json.utterance);
            $('#eojeol').append(json.eojeol);
            $('#member').append(json.member);
        },
        error : function(request, status, error) {
            //alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
        }
    });
});