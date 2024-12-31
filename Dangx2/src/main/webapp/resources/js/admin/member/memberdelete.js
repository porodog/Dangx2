/**
 * 
 */

// 회원 삭제 버튼 클릭 시 호출되는 함수
function deleteMembers(event) {
    event.preventDefault();  // 링크의 기본 동작(페이지 이동)을 방지

    // 선택된 회원들의 ID를 가져옵니다.
    const selectedMembers = document.querySelectorAll('input[name="memberIds"]:checked');
    if (selectedMembers.length === 0) {
        alert("삭제할 회원을 선택해주세요.");
        return;
    }

    const idsToDelete = [];
    selectedMembers.forEach(function(member) {
        idsToDelete.push(member.value);
    });

    // AJAX 요청으로 서버에 데이터를 보냅니다.
    fetch('/admin/member/list.do', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({ 'memberIds': idsToDelete })
    })
    .then(response => response.text())
    .then(result => {
        // 서버 응답 처리
        if (result.includes('empty')) {
            alert('선택된 회원이 없습니다.');
        } else {
            window.location.href = '/jsp/admin/member/memberdelete.jsp'; // 삭제 성공 후 리디렉션
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('회원 삭제 중 오류가 발생했습니다.');
    });
}
