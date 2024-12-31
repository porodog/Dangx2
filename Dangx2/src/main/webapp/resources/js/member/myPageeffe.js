function ValidationCorrection() {
    const errorMessages = [];

    // 비밀번호가 입력된 경우에만 비밀번호 유효성 검사 수행
    const elUserPwd = document.querySelector('#userPwd');
    if (elUserPwd && elUserPwd.value.trim().length > 0) {
        if (!validatePassword()) errorMessages.push("비밀번호가 유효하지 않습니다.");
        if (!validatePasswordRetype()) errorMessages.push("비밀번호가 일치하지 않습니다.");
    }

    // 핸드폰 유효성 검사
    if (!validatePhone()) errorMessages.push("핸드폰 번호가 유효하지 않습니다.");

    if (errorMessages.length > 0) {
        alert(errorMessages.join("\n")); // 모든 오류 메시지를 alert로 표시
        return false; // 검사를 통과하지 못했으므로 false 반환
    }

    return true; // 모든 검사를 통과해야 true 반환 (제출 허용)
}


// 비밀번호 유효성 검사 함수
function validatePassword() {
    const elInputPassword = document.querySelector('#userPwd');
    const elEmptyPasswordMessage = document.querySelector('.empty-password-message');

    if (!elInputPassword || !elEmptyPasswordMessage) return true; // 방어 코드

    if (elInputPassword.value.trim().length === 0) {
        elEmptyPasswordMessage.classList.remove('hide');
        return false;
    }

    if (!strongPassword(elInputPassword.value)) {
        elEmptyPasswordMessage.classList.remove('hide');
        return false;
    }

    elEmptyPasswordMessage.classList.add('hide');
    return true;
}

// 비밀번호 확인 유효성 검사 함수
function validatePasswordRetype() {
    const elInputPassword = document.querySelector('#userPwd');
    const elInputPasswordRetype = document.querySelector('#userPwd2');
    const elMismatchMessage = document.querySelector('.mismatch-message');

    if (!elInputPassword || !elInputPasswordRetype || !elMismatchMessage) return true; // 방어 코드

    if (elInputPassword.value.trim().length === 0 && elInputPasswordRetype.value.trim().length === 0) {
        elMismatchMessage.classList.add('hide');
        return true;  // 둘 다 비어있으면 통과
    }

    if (!isMatch(elInputPassword.value, elInputPasswordRetype.value)) {
        elMismatchMessage.classList.remove('hide');
        return false;
    }

    elMismatchMessage.classList.add('hide');
    return true;
}

// 핸드폰 번호 유효성 검사 함수
function validatePhone() {
    const elInputPhone = document.querySelector('#userPhone');
    const elPhoneMessage = document.querySelector('.phone-message');

    if (!elInputPhone || !elPhoneMessage) return true; // 방어 코드

    const phoneRegex = /^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$/;

    if (elInputPhone.value.trim().length === 0) {
        elPhoneMessage.classList.remove('hide');
        return false;
    }

    if (!phoneRegex.test(elInputPhone.value)) {
        elPhoneMessage.classList.remove('hide');
        return false;
    }

    elPhoneMessage.classList.add('hide');
    return true;
}

// 핸드폰 입력 시 자동으로 하이픈 추가
const autoHyphen2 = (target) => {
    const value = target.value.replace(/[^0-9]/g, ''); // 숫자 외의 문자는 모두 제거
    if (value.length <= 3) {
        target.value = value;
    } else if (value.length <= 6) {
        target.value = value.slice(0, 3) + '-' + value.slice(3);
    } else if (value.length <= 10) {
        target.value = value.slice(0, 3) + '-' + value.slice(3, 6) + '-' + value.slice(6);
    } else {
        target.value = value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7, 11);
    }
}

// onkeyup 이벤트로 실시간 유효성 검사
document.querySelector('#userPwd').onkeyup = validatePassword;
document.querySelector('#userPwd2').onkeyup = validatePasswordRetype;
document.querySelector('#userPhone').onkeyup = validatePhone;


function strongPassword(str) {
    return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/.test(str);
}

function isMatch(password1, password2) {
    return password1 === password2;
}
