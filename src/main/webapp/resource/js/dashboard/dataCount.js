$(function (){
    $.ajax({
        //요청
        type: "GET",
        url: contextPath + "/chart/getCountData",
        async: false,

        //응답
        success : function(response){
            var json = JSON.parse(response);
            $('#metadata').append(json.metadata);
            $('#utterance').append(json.utterance);
            $('#eojeol').append(json.eojeol);
            $('#member').append(json.member);
        },
        error : function(request, status, error) {
        }
    });
});