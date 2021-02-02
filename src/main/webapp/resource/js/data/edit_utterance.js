$(function () {
    $('#editUtteranceBtn').click(function () {
        $('#loadingArea').show();
        let form = $('#edit_form').val();
        let original = $("input[type=hidden][name=utteranceForm]").val();

        if (form == '') {
            alert("바꿀 문장을 입력해주세요.");
            return false;
        }
        else if(form == original){
            alert("기존 문장과 같습니다.");
            return false;
        }

        var formData = new FormData($('#editUtteranceForm')[0]);

        /* ajax로 제출 */
        $.ajax({
            url: contextPath + "/editUtterance",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,

            success: function (result) {
                if (result == 'true') {
                    alert("문장이 수정되었습니다.\n페이지를 다시 읽어옵니다.");
                    location.reload(true);
                    $('#loadingArea').hide();
                }
            },

            error: function (request, status, error) {
                //alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
            }
        });
        $('#editUtteranceModal').modal("hide"); //닫기
    })
});