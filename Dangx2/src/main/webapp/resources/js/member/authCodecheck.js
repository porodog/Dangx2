/**
 * 
 */
function authCodeCheck() {
    const authCode = document.getElementById('inputAuthCode').value;

    // 서버에 인증번호 확인 요청
    fetch('/member/verifyAuthCode.do', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'authCode=' + encodeURIComponent(authCode)
    }).then(response => response.json()).then(data => {
        if (data.isAuthSuccess) { // 서버 응답의 isAuthSuccess 필드 확인
            alert('인증이 완료되었습니다.');
            document.getElementById('authVerified').value = 'true'; // 인증 완료 표시
            
            // 인증이 완료되면 입력 필드와 버튼 비활성화
            document.getElementById('inputAuthCode').disabled = true;  // 인증번호 입력 필드 비활성화
            document.getElementById('authCodeCheckBtn').disabled = true;  // 인증 버튼 비활성화
            document.getElementById('sendEmailButton').disabled = true;  // 인증 메일 보내기 버튼 비활성화

        } else {
            alert('인증번호가 일치하지 않습니다.');
        }
    }).catch(error => {
        console.error('Error:', error);
        alert('서버와 통신 중 오류가 발생했습니다.');
    });
}