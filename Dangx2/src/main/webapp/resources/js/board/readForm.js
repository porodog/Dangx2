/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	// 댓글 처리 이벤트
	// 등록
	const replyInsertButton = document.querySelector('#replyInsertButton');
	if(replyInsertButton!=null) {
		replyInsertButton.addEventListener('click', function(e) {
			const replyInsertText = document.querySelector('#replyInsertText');
			const content = replyInsertText.value;
			if(content.length<1) {
				alert('등록할 댓글내용을 입력해주세요');
				return false;
			}
			
			if(content.length>0 && confirm('댓글을 등록하겠습니까?')) {
				makeFormAndSubmitMethod('insert', null, replyInsertText.value);
			}
		});
	}
	
	// 수정
	const replySubmitButton = document.querySelectorAll('.reply-submit-button');
	if(replySubmitButton.length>0) {
		replySubmitButton.forEach((target) => {
			target.addEventListener('click', function(e) {
				const replyIdx = e.target.dataset.replyIdx;
				const replyModifyText = document.querySelector(`#replyModifyText-${replyIdx}`);
				const content = replyModifyText.value;
				if(content.length<1) {
					alert('수정할 댓글내용을 입력해주세요');
					return false;
				}
				
				if(content.length>0 && confirm('댓글을 수정하시겠습니까?')) {
					makeFormAndSubmitMethod('modify', replyIdx, content);
				}
			});
		});
	}
	
	// 삭제
	const replyDeleteButton = document.querySelectorAll('.reply-delete-button');
	if(replyDeleteButton.length>0) {
		replyDeleteButton.forEach((target) => {
			target.addEventListener('click', function(e) {
				const replyIdx = e.target.dataset.replyIdx;
				if(confirm('댓글을 삭제하시겠습니까?')) {
					makeFormAndSubmitMethod('delete', replyIdx, null);
				}
			});
		});
	}
});

function makeFormAndSubmitMethod(type, targetIdx, targetValue) {
	const form = document.createElement("form");
	form.setAttribute("charset", "UTF-8");
	form.setAttribute("method", "post");
	form.setAttribute("action", "/board/reply/"+type+".do");
	
	let intput = document.createElement("input");
	intput.setAttribute("type", "hidden");
	intput.setAttribute("name", "boardType");
	intput.setAttribute("value", getBoardType); //메뉴
	form.appendChild(intput);
	
	intput = document.createElement("input");
	intput.setAttribute("type", "hidden");
	intput.setAttribute("name", "boardIdx");
	intput.setAttribute("value", getBoardIdx);
	form.appendChild(intput);
	
	if(type=="modify" || type=="delete") {
		intput = document.createElement("input");
		intput.setAttribute("type", "hidden");
		intput.setAttribute("name", "replyIdx");
		intput.setAttribute("value", targetIdx); //댓글 idx
		form.appendChild(intput);
	}
	
	if(type=="modify" || type=="insert") {
		intput = document.createElement("input");
		intput.setAttribute("type", "hidden");
		intput.setAttribute("name", "content");
		intput.setAttribute("value", targetValue); //댓글내용
		form.appendChild(intput);
	}
	
	document.body.appendChild(form);
 	form.submit();
}