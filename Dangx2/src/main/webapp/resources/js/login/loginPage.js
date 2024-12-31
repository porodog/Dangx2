document.addEventListener('DOMContentLoaded', function() {
    console.log("ready");

    const loginButton = document.querySelector('#loginButton');
    loginButton.addEventListener('click', function(event) {
        // 이벤트 기본 동작 막기 (폼 제출 방지)
        event.preventDefault();

        // 유효성 검사를 통과한 경우에만 폼 제출
        if (validate()) {
            document.querySelector('#loginForm').submit();
        }
    });

    function validate() {
        const userId = document.querySelector('#userId');
        const userPwd = document.querySelector('#userPwd');
        const userIdError = document.querySelector('#userIdError');
        const userPwdError = document.querySelector('#userPwdError');
        let result = true;

        // 오류 메시지 초기화
        userIdError.textContent = "";
        userPwdError.textContent = "";

        // 아이디 검증
        if (userId.value.trim() === "") {
            result = false;
            userIdError.textContent = "아이디를 입력해주세요";
        }

        // 비밀번호 검증
        else if (userPwd.value.trim() === "") {
            result = false;
            userPwdError.textContent = "비밀번호를 입력해주세요";
        }

        return result;
    }
});
