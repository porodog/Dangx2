/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	const submitBtn = document.querySelector('#submitBtn');
	const backBtn = document.querySelector('#backBtn');
	const deleteBtn = document.querySelector('#deleteBtn');
	submitBtn.addEventListener('click', function() {
		const idxValue = document.getElementsByName('idx')[0].value;
		let message = '등록하시겠습니까?';
		
		if(idxValue!="null") {
			message = '수정하시겠습니까?';
		}
		if(validate() && confirm(message)) {
			bongsaForm.submit();
		}
	});
	
	backBtn.addEventListener('click', function() {
		location.href = '/bongsa/calendar.do';
	});
	
	if(deleteBtn!=null) {
		deleteBtn.addEventListener('click', function() {
			if(confirm('삭제하시겠습니까?')) {
				bongsaForm.action = '/bongsa/delete.do';
				bongsaForm.submit();
			}
		});
	}
	
	
	function validate() {
		let result = true;
		for(let i=0; i<bongsaForm.bongsaName.length; i++) {
			const bongsaName = bongsaForm.bongsaName[i].value;
			const bongsaPhone = bongsaForm.bongsaPhone[i].value;
			
			//값 매칭 (둘 중 하나만 입력 시)
			if((bongsaName!="" && bongsaPhone=="") || (bongsaName=="" && bongsaPhone!="")) {
				result = false;
				alert("구성원 입력을 확인해주세요.");
				break;			
			}
			
			//이름 체크
			if(bongsaName.length>0) {
				if(bongsaName.length>10) {
					result = false;
					alert("성명은 최대 10글자까지 입력할 수 있습니다.");
					break;
				}
			}
			
			//연락처 체크
			if(bongsaPhone.length>0) {
				if(bongsaPhone.length>20) {
					result = false;
					alert("연락처는 최대 20글자까지 입력할 수 있습니다.");
					break;
				}
				if(isNaN(bongsaPhone)) {
					result = false;
					alert("연락처는 숫자만 입력할 수 있습니다.");
					break;
				}
			}
		}
		return result;
	}
});