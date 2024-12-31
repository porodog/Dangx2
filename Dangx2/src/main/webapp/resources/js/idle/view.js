/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	// 목록 버튼
	const backBtn = document.querySelector('#backBtn');
	backBtn.addEventListener('click', function() {
		location.href = '/idle/'+getMenuType+'/list.do';
	});
	
	// 댓글수정 커스텀 - 폼 변경 이벤트
	const replyModifyBtn = document.querySelectorAll('.reply-modify-button');
	if(replyModifyBtn.length>0) {
		replyModifyBtn.forEach((target) => {
			target.addEventListener('click', function(e) {
				replyFormChangeMethod(this, 'modify');
			});
		});
	}
	
	// 댓글수정 취소 커스텀 - 폼 변경 이벤트
	const replyCancelBtn = document.querySelectorAll('.reply-cancel-button');
	if(replyCancelBtn.length>0) {
		replyCancelBtn.forEach((target) => {
			target.addEventListener('click', function(e) {
				replyFormChangeMethod(this, 'cancel');
			});
		});
	}
	
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
	form.setAttribute("action", "/idle/reply/"+type+".do");
	
	let intput = document.createElement("input");
	intput.setAttribute("type", "hidden");
	intput.setAttribute("name", "menuType");
	intput.setAttribute("value", getMenuType); //메뉴
	form.appendChild(intput);
	
	intput = document.createElement("input");
	intput.setAttribute("type", "hidden");
	intput.setAttribute("name", "idleIdx");
	intput.setAttribute("value", getIdleIdx); //아이들 idx
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


function replyFormChangeMethod(obj, type) {
	// button
	const tag_button_div = obj.closest('.reply-button-item');
	const tag_type1_div = tag_button_div.querySelector('.button-item-type1');
	const tag_type2_div = tag_button_div.querySelector('.button-item-type2');
	if(type==='modify') {
		tag_type1_div.style.display = 'none';
		tag_type2_div.style.display = 'block';
	}
	else {
		tag_type1_div.style.display = 'block';
		tag_type2_div.style.display = 'none';
	}
	
	// input
	const tag_li = obj.closest('li');
	const tag_content_div = tag_li.querySelector('.section-reply-content');
	const tag_p = tag_content_div.querySelector('p');
	const tag_input = tag_content_div.querySelector('input');
	if(type==='modify') {
		tag_p.style.display = 'none';
		tag_input.style.display = 'block';
		tag_input.value = '';
	}
	else {
		tag_p.style.display = 'block';
		tag_input.style.display = 'none';
	}
}