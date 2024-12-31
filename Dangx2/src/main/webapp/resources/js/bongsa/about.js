/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	//console.log("ready");
	const applyBtn = document.querySelector('#applyBtn');
	applyBtn.addEventListener('click', function() {
		if(sessionId!=null && sessionId!="") {
			location.href = '/bongsa/calendar.do';
		}
		else {
			if(confirm("로그인 후 신청이 가능합니다.\n로그인페이지로 이동하시겠습니까?")) {
				location.href = '/login/loginPage.do';
			}
		}
	});
});