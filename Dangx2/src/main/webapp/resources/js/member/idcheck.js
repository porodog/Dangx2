/**
 * 
 */
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
                if (response.isDuplicate) {
                    document.getElementById('id-check-message').innerText = "이미 사용 중인 아이디입니다.";
                } else {
                    document.getElementById('id-check-message').innerText = "사용 가능한 아이디입니다.";
                }
            } else {
                document.getElementById('id-check-message').innerText = "서버 오류가 발생했습니다.";
            }
        }
    };
    
    xhr.send("userId=" + encodeURIComponent(userId));
}