function handleImgFile(fileId, imgId, btnId, e){
	var thisFileId = document.getElementById(fileId);
	var thisImgId = document.getElementById(imgId);
	var files = e.target.files;
	var filesArr = Array.prototype.slice.call(files);

	if (thisFileId.value != "") {
		filesArr.forEach(function(f) {
			/* 확장자 제한 */
			if(!f.type.match("image.*")) {
				alert("확장자는 이미지 확장자만 가능합니다.");
				thisFileId.value = "";
				return false;
			}
			
			/* 용량 제한 */
			var fileSize = thisFileId.files[0].size;
			var maxSize = 5 * 1024 * 1000;
			if (fileSize > maxSize) {
				alert("파일용량 5MB을 초과했습니다.");
				thisFileId.value = "";
				return false;
			}
			
			/* 업로드 이미지 미리보기 */
			var reader = new FileReader();
			reader.onload = function(e) {
				thisImgId.setAttribute("src", e.target.result);
			}
			reader.readAsDataURL(f);
			
			/* 파일 업로드 리셋 버튼 보이기 */
			var uploadBtn = document.getElementById(btnId);
			uploadBtn.setAttribute("style", "display: inline-block");
		}) //forEach
	} //if
}

/* 업로드 이미지 삭제 */
function resetUploadImg(fileId, imgId, btnId) {
	let answer = confirm("업로드한 이미지를 삭제합니다.");
	
	if(answer){
		/* 파일 업로드된 파일을 삭제해서 리셋시킴 */
		document.getElementById(fileId).value = '';
		
		/* 파일 업로드 디폴트 이미지로 바꿈 */
		var thisImgId = document.getElementById(imgId);
		var imgSrc = $('#contextPath').val() + "/resource/img/user.png";
		thisImgId.setAttribute("src", imgSrc);

		/* 파일 업로드 리셋 버튼 숨김 */
		var thisBtnId = document.getElementById(btnId);
		thisBtnId.setAttribute("style", "display: none");
	}
}