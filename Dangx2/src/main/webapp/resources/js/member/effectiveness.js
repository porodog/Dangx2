/**
 * 
 */
function Validation() {
	//alert("cxzcc");
	
	
    // 아이디 검증
	/*console.log("id");
    const userIdValid = validateUsername();
	console.log(userIdValid);
    if (!userIdValid) return false;

    // 비밀번호 검증
	console.log("pwd");
    const passwordValid = validatePassword();
	console.log(passwordValid);
    if (!passwordValid) return false;

    // 비밀번호 일치 검증
	console.log("pwd2");
    const passwordMatch = validatePasswordRetype();
	console.log(passwordMatch);
    if (!passwordMatch) return false;*/

    // 이름 검증
	console.log("name");
    const nameValid = validateName();
	console.log(nameValid);
    if (!nameValid) return false;

    // 핸드폰 검증
	console.log("phone");
    const phoneValid = validatePhone();
    if (!phoneValid) return false;

    // 이메일 검증
    const emailValid = validateEmail();
    if (!emailValid) return false;

    return true; // 모든 검사를 통과하면 true 반환 (제출 허용)
}

	const elInputUsername = document.querySelector('#userId');
    const elFailureMessage = document.querySelector('.failure-message');
    const elEmptyIdMessage = document.querySelector('.empty-id-message');

// 아이디 유효성 검사 함수
elInputUsername.onkeyup = function () {

    if (elInputUsername.value.trim().length === 0) {
        elEmptyIdMessage.classList.remove('hide');
        return false;
    } else {
        elEmptyIdMessage.classList.add('hide');
    }

    if (!onlyNumberAndEnglish(elInputUsername.value) || !idLength(elInputUsername.value)) {
        elFailureMessage.classList.remove('hide');
        return false;
    }
    elFailureMessage.classList.add('hide');
    return true;
}

	const elInputPassword = document.querySelector('#userPwd');
	const elInputPasswordRetype = document.querySelector('#userPwd2');
    const elStrongPasswordMessage = document.querySelector('.strongPassword-message');
	const elMismatchMessage = document.querySelector('.mismatch-message');
    const elEmptyPasswordMessage = document.querySelector('.empty-password-message');
	const elEmptyPassword2Message = document.querySelector('.empty-password2-message');

// 비밀번호 유효성 검사 함수
elInputPassword.onkeyup = function() {

    if (elInputPassword.value.trim().length === 0) {
        elEmptyPasswordMessage.classList.remove('hide');
        return false;
    } else {
        elEmptyPasswordMessage.classList.add('hide');
    }

    if (!strongPassword(elInputPassword.value)) {
        elStrongPasswordMessage.classList.remove('hide');
        return false;
    }
    elStrongPasswordMessage.classList.add('hide');
    return true;
}
   

// 비밀번호 확인 유효성 검사 함수
elInputPasswordRetype.onkeyup = function () {
    
    if (elInputPasswordRetype.value.trim().length === 0) {
        elEmptyPassword2Message.classList.remove('hide');
        return false;
    } else {
        elEmptyPassword2Message.classList.add('hide');
    }

    if (!isMatch(elInputPassword.value, elInputPasswordRetype.value)) {
        elMismatchMessage.classList.remove('hide');
        return false;
    }
    elMismatchMessage.classList.add('hide');
    return true;
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

// 핸드폰 번호 유효성 검사 함수
function validatePhone() {
    const elInputPhone = document.querySelector('#userPhone');
    const elPhoneMessage = document.querySelector('.phone-message');
    const elEmptyPhoneMessage = document.querySelector('.empty-phone-message');
    const phoneRegex = /^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
	
    if (elInputPhone.value.trim().length === 0) {
        elEmptyPhoneMessage.classList.remove('hide');
        return false;
    } else {
        elEmptyPhoneMessage.classList.add('hide');
    }

    if (!phoneRegex.test(elInputPhone.value)) {
        elPhoneMessage.classList.remove('hide');
        return false;
    }
    	elPhoneMessage.classList.add('hide');
    	return true;
}

const autoHyphen2 = (target) => {
 target.value = target.value
   .replace(/[^0-9]/g, '')
  .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
}


// 이메일 유효성 검사 함수
function validateEmail() {
    const elInputEmail = document.querySelector('#userEmail');
    const elEmailMessage = document.querySelector('.email-message');
    const elEmptyEmailMessage = document.querySelector('.empty-email-message');
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (elInputEmail.value.trim().length === 0) {
        elEmptyEmailMessage.classList.remove('hide');
        return false;
    } else {
        elEmptyEmailMessage.classList.add('hide');
    }

    if (!emailRegex.test(elInputEmail.value)) {
        elEmailMessage.classList.remove('hide');
        return false;
    }
    elEmailMessage.classList.add('hide');
    return true;
}

// 기타 유효성 검사 함수들
function idLength(value) {
  return value.length >= 4 && value.length <= 12;
}

function onlyNumberAndEnglish(str) {
  return /^[A-Za-z0-9][A-Za-z0-9]*$/.test(str);
}

function strongPassword(str) {
  return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/.test(str);
}

function isMatch(password1, password2) {
  return password1 === password2;
}