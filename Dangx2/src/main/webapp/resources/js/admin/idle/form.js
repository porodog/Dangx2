/**
 * 
 */

function doInsert() {
	if(validate() && confirm('등록하시겠습니까?')) {
		const insertForm = document.querySelector('#insertForm');
		insertForm.submit();
	}
}

function doListPage(idleTypeCd) {
	location.href = `/admin/idle/${idleTypeCd}/list.do`;
}

function validate() {
	let result = false;
	const idleName = document.querySelector('#idleName');
	const idleBreed = document.querySelector('#idleBreed');
	const idleYear = document.querySelector('#idleYear');
	const idleKg = document.querySelector('#idleKg');
	const idleContent = document.querySelector('#idleContent');
	
	if(idleName.value=="") {
		alert('이름을 입력해주세요');
	}
	else if(idleBreed.value=="") {
		alert('품종을 입력해주세요');
	}
	else if(idleYear.value=="") {
		alert('생년을 입력해주세요');
	}
	else if(isNaN(idleYear.value)) {
		alert('생년은 숫자만 입력가능합니다');
	}
	else if(idleKg.value=="") {
		alert('몸무게를 입력해주세요');
	}
	else if(idleContent.value=="") {
		alert('내용을 입력해주세요');
	}
	else {
		result = true;
	}
	
	return result;
}