/**
 * 
 */

function doInsert() {
	if(validate() && confirm('등록하시겠습니까?')) {
		const insertForm = document.querySelector('#insertForm');
		insertForm.submit();
	}
}

function doListPage(boardTypeCd) {
	location.href = `/admin/board/${boardTypeCd}/list.do`;
}

function validate() {
	let result = false;
	
	return result;
}