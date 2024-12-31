function checkForm() {
    const title = document.newWrite.title.value;
    const content = document.newWrite.content.value;

    if (!title) {
        alert("제목을 입력해 주세요.(최대 40자)");
        return false;
    }
    if (title.length > 40) {
        alert("제목은 40자 미만이어야 합니다.");
        return false;
    }
    if (!content) {
        alert("내용을 입력해 주세요.(최대 1000자)");
        return false;
    }
    if (content.length > 1000) {
        alert("내용은 1000자 미만이어야 합니다.");
        return false;
    }
	
	if(!confirm('글을 등록하시겠습니까?')) {
		return false;
	}
	
    return true; // 모든 검증 통과 시 true 반환, false 반환 시 빠꾸
}

	//게시글 삭제
function deletePost(idx, boardType) {
    if (confirm("정말로 게시글을 삭제하시겠습니까?")) {
        const form = document.createElement('form');
        form.method = 'post';
        form.action = 'deleteBoard.do';

        const idxInput = document.createElement('input');
        idxInput.type = 'hidden';
        idxInput.name = 'idx';
        idxInput.value = idx;
        form.appendChild(idxInput);

        const boardTypeInput = document.createElement('input');
        boardTypeInput.type = 'hidden';
        boardTypeInput.name = 'boardType';
        boardTypeInput.value = boardType;
        form.appendChild(boardTypeInput);

        document.body.appendChild(form);
        form.submit();
    }
}

function deleteBoardFile(fileIdx) {
    // UI에서 삭제 처리
    document.getElementById(`file-${fileIdx}`).remove();

    // hidden input에 삭제된 파일 ID 추가
    const deletedFilesInput = document.getElementById("deletedFiles");
    let deletedFiles = JSON.parse(deletedFilesInput.value);
    deletedFiles.push(fileIdx);
    deletedFilesInput.value = JSON.stringify(deletedFiles);
}
