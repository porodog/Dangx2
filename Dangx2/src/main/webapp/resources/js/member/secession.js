document.addEventListener('DOMContentLoaded', function() {
    // 회원 탈퇴 기능
    document.querySelector('input[value="탈퇴하기"]').addEventListener('click', function() {
        // 모달을 열고 비밀번호 입력 받기
        const modal = document.getElementById('passwordModal');
        modal.style.display = 'block';
    });

    // 비밀번호 입력 확인 및 서버 요청 처리
    document.getElementById('confirmDelete').addEventListener('click', function() {
        const password = document.getElementById('passwordInput').value;
        if (password) {
            if (confirm("정말로 탈퇴하시겠습니까?")) {
                // fetch API로 서버에 비밀번호 전송
                fetch('/member/deleteMember.do', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: `userPwd=${encodeURIComponent(password)}`
                })
                .then(response => response.json())
                .then(data => {
                    if (data.error) {
                        // 오류 메시지를 alert로 표시
                        alert(data.error);
                    } else if (data.message) {
                        // 성공 메시지 alert로 표시
                        alert(data.message);
                        // 메인 페이지로 리다이렉트
                        if (data.redirectUrl) {
                            window.location.href = data.redirectUrl;
                        }
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('회원 탈퇴 중 오류가 발생했습니다.');
                });

                // 모달 닫기
                modal.style.display = 'none';
            }
        } else {
            alert("비밀번호를 입력해주세요.");
        }
    });

    // 취소 버튼 클릭 시 모달 닫기
    document.getElementById('cancelDelete').addEventListener('click', function() {
        document.getElementById('passwordModal').style.display = 'none';
    });
});