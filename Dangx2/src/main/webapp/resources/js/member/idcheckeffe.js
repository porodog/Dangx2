let isIdChecked = false; // 중복 확인 여부
let idIsDuplicate = false; // 중복된 아이디 여부

// 아이디 중복 확인 함수
function checkDuplicateId() {
    const userId = document.getElementById('userId').value;
    if (userId.trim() === "") {
        document.getElementById('id-check-message').innerText = "아이디를 입력해주세요.";
        return;
    }

    console.log("Sending request to check ID:", userId);

    // Ajax를 통해 서버로 아이디 중복 확인 요청
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/member/checkDuplicateId.do", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                isIdChecked = true; // 중복 확인 완료
                idIsDuplicate = response.isDuplicate; // 중복 여부 저장

                document.getElementById('id-check-message').innerText = response.isDuplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.";
            } else {
                document.getElementById('id-check-message').innerText = "서버 오류가 발생했습니다.";
            }
        }
    };

    xhr.send("userId=" + encodeURIComponent(userId));
}

// 폼 제출 시 실행될 함수
function submitForm() {   

    // 중복 확인을 안 했거나 중복된 아이디인 경우 폼 제출 방지
    if (!isIdChecked) {
        alert("아이디 중복 확인을 먼저 해주세요.");
        return false;
    }

    if (idIsDuplicate) {
        alert("이미 사용 중인 아이디입니다. 다른 아이디를 사용해주세요.");
        return false;
    }

    return true; // 유효성 검사를 통과하고 중복 확인이 완료된 경우 폼을 제출함
}

function Validation() {
    const userId = document.getElementById('userId').value.trim();
    const userPwd = document.getElementById('userPwd').value.trim();
    const userPwd2 = document.getElementById('userPwd2').value.trim();
    const userName = document.getElementById('userName').value.trim();
    const userPhone = document.getElementById('userPhone').value.trim();
    const userEmail = document.getElementById('userEmail').value.trim();
		
		
		if (!isIdChecked) {
		        alert("아이디 중복 확인을 먼저 해주세요.");
		        return false;
		    }
			
		if (idIsDuplicate) {
			    alert("이미 사용 중인 아이디입니다. 다른 아이디를 사용해주세요.");
			    return false;
			 }
		
			 // 필수 정보 입력 여부 확인
			     if (!userId || !userPwd || !userPwd2 || !userName || !userPhone || !userEmail) {
			         alert("모든 정보를 입력해 주세요.");
			         return false;
			     }

			     // 개별 유효성 검사
			     if (!validateUsername()) {
			         alert("아이디를 올바르게 입력해 주세요.");
			         return false;
			     }
			     if (!validatePassword()) {
			         alert("비밀번호를 올바르게 입력해 주세요.");
			         return false;
			     }
			     if (!validatePasswordRetype()) {
			         alert("비밀번호가 일치하지 않습니다.");
			         return false;
			     }
			     if (!validateName()) {
			         alert("이름을 올바르게 입력해 주세요.");
			         return false;
			     }
			     if (!validatePhone()) {
			         alert("핸드폰 번호가 유효하지 않습니다.");
			         return false;
			     }
			     if (!validateEmail()) {
			         alert("이메일 주소가 유효하지 않습니다.");
			         return false;
			     }

			     return true; // 모든 검사를 통과하면 true 반환
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
			     }
			     return true;
			 }

			 // 비밀번호 유효성 검사 함수
			 function validatePassword() {
			     const elInputPassword = document.querySelector('#userPwd');
			     const elEmptyPasswordMessage = document.querySelector('.empty-password-message');

			     if (elInputPassword.value.trim().length === 0) {
			         elEmptyPasswordMessage.classList.remove('hide');
			         return false;
			     } else {
			         elEmptyPasswordMessage.classList.add('hide');
			     }
			     return true;
			 }

			 // 비밀번호 확인 유효성 검사 함수
			 function validatePasswordRetype() {
			     const elInputPasswordRetype = document.querySelector('#userPwd2');
			     const elInputPassword = document.querySelector('#userPwd');
			     const elMismatchMessage = document.querySelector('.mismatch-message');

			     if (elInputPasswordRetype.value.trim().length === 0) {
			         elMismatchMessage.classList.remove('hide');
			         return false;
			     } else {
			         elMismatchMessage.classList.add('hide');
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
    const phoneRegex = /^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$/;

    if (elInputPhone.value.trim().length === 0 || !phoneRegex.test(elInputPhone.value)) {
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

// 이메일 유효성 검사 함수
function validateEmail() {
    const elInputEmail = document.querySelector('#userEmail');
    const elEmailMessage = document.querySelector('.email-message');
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (elInputEmail.value.trim().length === 0 || !emailRegex.test(elInputEmail.value)) {
        elEmailMessage.classList.remove('hide');
        return false;
    }
    elEmailMessage.classList.add('hide');
    return true;
}

// onkeyup 이벤트로 실시간 유효성 검사
document.querySelector('#userId').onkeyup = validateUsername;
document.querySelector('#userPwd').onkeyup = validatePassword;
document.querySelector('#userPwd2').onkeyup = validatePasswordRetype;
document.querySelector('#userName').onkeyup = validateName;
document.querySelector('#userPhone').onkeyup = validatePhone;
document.querySelector('#userEmail').onkeyup = validateEmail;

// 기타 유효성 검사 함수들
function idLength(value) {
    return value.length >= 4 && value.length <= 12;
}

function onlyNumberAndEnglish(str) {
    return /^[A-Za-z0-9]+$/.test(str);
}

function strongPassword(str) {
    return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/.test(str);
}

function isMatch(password1, password2) {
    return password1 === password2;
}
