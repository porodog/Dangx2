/**
 * 
 */
function ValidationPW() {
    // 모든 검사를 통과해야 true 반환 (제출 허용)
    return validateUsername() && validateName() && validateEmail();
}

// 아이디 유효성 검사 함수
function validateUsername() {
    const elInputUsername = document.querySelector('#userId');
    const elEmptyIdMessage = document.querySelector('.empty-id-message');

    if (elInputUsername.value.trim().length === 0) {
        elEmptyIdMessage.classList.remove('hide');
        return false;
    } else {
        elEmptyIdMessage.classList.add('hide');
    return true
	}

}

// 이름 유효성 검사 함수
function validateName() {
    const elInputName = document.querySelector('#userName');
    const elNameMessage = document.querySelector('.name-message');

    if (elInputName.value.trim().length === 0) {
        elNameMessage.classList.remove('hide');
        return false;
    }
    elNameMessage.classList.add('hide');
    return true;
}

// 이메일 유효성 검사 함수
function validateEmail() {
    const elInputEmail = document.querySelector('#userEmail');
    const elEmailMessage = document.querySelector('.email-message');
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(elInputEmail.value)) {
        elEmailMessage.classList.remove('hide');
        return false;
    }
    elEmailMessage.classList.add('hide');
    return true;
}

// onkeyup 이벤트로 실시간 유효성 검사
document.querySelector('#userId').onkeyup = validateUsername;
document.querySelector('#userName').onkeyup = validateName;
document.querySelector('#userEmail').onkeyup = validateEmail;

// 기타 유효성 검사 함수
function idLength(value) {
    return value.length >= 4 && value.length <= 12;
}

function onlyNumberAndEnglish(str) {
    return /^[A-Za-z0-9]+$/.test(str);
}