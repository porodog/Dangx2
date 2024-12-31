/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
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
	
	// 파일첨부 커스텀 - 파일명 붙이기
	const fileUpload = document.querySelector('#fileUpload');
	if(fileUpload!=null) {
		fileUpload.addEventListener('change', function (e) {
		    let files = e.target.files;
		    let fileArr = new Array();
		
		    for (let i=0; i<files.length; i++) {
		      fileArr.push(files[i].name);
		    }
			const spanFile =  document.querySelector('.span-file');
			spanFile.innerText = fileArr.join(' , ');
		});
	}
});

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
	}
	else {
		tag_p.style.display = 'block';
		tag_input.style.display = 'none';
	}
	
}