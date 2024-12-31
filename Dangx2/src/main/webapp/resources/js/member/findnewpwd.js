function ValidationCorrection() {
    const errorMessages = [];

    // 비밀번호 필드 가져오기
    const elNewPassword = document.querySelector('#newPassword');
    const elConfirmPassword = document.querySelector('#confirmPassword');

    // 두 필드 모두 비어있는지 확인
    if (elNewPassword.value.trim().length === 0 && elConfirmPassword.value.trim().length === 0) {
        alert("모든 정보를 입력해주세요.");
        return false; // 검사를 통과하지 못했으므로 false 반환
    }

    // 비밀번호가 입력되지 않았거나 비밀번호 확인이 일치하지 않는 경우
    if (elNewPassword && elConfirmPassword) {
        if (!validatePassword()) errorMessages.push("비밀번호가 유효하지 않습니다.");
        if (!validatePasswordRetype()) errorMessages.push("비밀번호가 일치하지 않습니다.");
    }

    if (errorMessages.length > 0) {
        alert(errorMessages.join("\n")); // 모든 오류 메시지를 alert로 표시
        return false; // 검사를 통과하지 못했으므로 false 반환
    }

    return true; // 모든 검사를 통과해야 true 반환 (제출 허용)
}

// 비밀번호 유효성 검사 함수
function validatePassword() {
    const elInputPassword = document.querySelector('#newPassword');
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
    const elInputPassword = document.querySelector('#newPassword');
    const elInputPasswordRetype = document.querySelector('#confirmPassword');
    const elMismatchMessage = document.querySelector('.mismatch-message');

    if (!elInputPassword || !elInputPasswordRetype || !elMismatchMessage) return true; // 방어 코드

    // 둘 다 비어있으면 통과
    if (elInputPassword.value.trim().length === 0 || elInputPasswordRetype.value.trim().length === 0) {
        elMismatchMessage.classList.add('hide');
        return false; // 비어있을 경우 false 반환
    }

    if (!isMatch(elInputPassword.value, elInputPasswordRetype.value)) {
        elMismatchMessage.classList.remove('hide');
        return false;
    }

    elMismatchMessage.classList.add('hide');
    return true;
}

// onkeyup 이벤트로 실시간 유효성 검사
document.querySelector('#newPassword').onkeyup = validatePassword;
document.querySelector('#confirmPassword').onkeyup = validatePasswordRetype;

// 비밀번호 유효성 체크 정규식
function strongPassword(str) {
    return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/.test(str);
}

// 비밀번호 확인 일치 여부 확인 함수
function isMatch(password1, password2) {
    return password1 === password2;
}
