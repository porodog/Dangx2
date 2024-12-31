/**
 * 
 */
function sendEmailAuthCodeForRecovery(purpose) {
    const email = document.getElementById('userEmail').value;

    // 이메일 유효성 검사
    if (!email.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)) {
        alert('유효한 이메일을 입력해주세요.');
        return;
    }

    // 이미 인증 완료되었는지 확인
    const authVerified = document.getElementById('authVerified').value;
    if (authVerified === 'true') {
        alert('이미 인증이 완료되었습니다.');
        return;
    }

    // 서버에 이메일 인증 요청
    fetch('/member/sendEmailAuthCodeForRecovery.do', {  // 아이디/비밀번호 찾기용 엔드포인트
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'userEmail=' + encodeURIComponent(email) + '&purpose=' + encodeURIComponent(purpose)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            alert(data.message);
            document.getElementById('inputAuthCode').disabled = false;
            document.getElementById('authCodeCheckBtn').disabled = false;
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('서버와 통신 중 오류가 발생했습니다.');
    });
}