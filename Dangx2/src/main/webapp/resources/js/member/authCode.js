function sendEmailAuthCode() {
    const email = document.getElementById('userEmail').value;

    // 이메일 유효성 검사
    if (!email.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)) {
        alert('유효한 이메일을 입력해주세요.');
        return;
    }

    // 서버에 이메일 인증 요청
    fetch('/member/sendEmailAuthCode.do', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'userEmail=' + encodeURIComponent(email)
    }).then(response => response.json()).then(data => {
        if (data.success) {
            alert(data.message);
            document.getElementById('inputAuthCode').disabled = false;
            document.getElementById('authCodeCheckBtn').disabled = false;
        } else {
            alert(data.message); // 중복된 이메일 또는 이미 인증된 경우 처리
        }
    }).catch(error => {
        console.error('Error:', error);
    });
}