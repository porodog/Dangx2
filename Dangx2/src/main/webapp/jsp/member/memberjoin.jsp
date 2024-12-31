<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="icon" href="../../resources/image/fav_favicon.ico" type="image/x-icon"
      style="background-color: transparent;">
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>
<link rel="stylesheet" href="../../resources/css/member/memberjoin.css"/>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script defer src="../../resources/js/member/userPost.js"></script>
<script defer src="../../resources/js/member/konotinput.js"></script>
<script defer src="../../resources/js/member/authCode.js"></script>
<script defer src="../../resources/js/member/authCodecheck.js"></script>
<script defer src="../../resources/js/member/idcheckeffe.js"></script>
<% String errorMessage = (String) request.getAttribute("error"); %>
<script type="text/javascript">
const errorMessage = "<%= errorMessage != null ? errorMessage : "" %>";
if (errorMessage !== "") {
    alert(errorMessage);  // 에러 메시지가 있을 경우에만 alert로 출력
}
</script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('cancelButton').addEventListener('click', function() {
            window.location.href = '/jsp/main/mainPage.jsp'; // 로그인 페이지 URL로 수정
        });
    });
</script>

<script>
let isComposing = false; // 한글 조합 상태를 추적

document.addEventListener('DOMContentLoaded', function () {
    const nameInput = document.getElementById('userName');

    // 한글 입력 시작 시
    nameInput.addEventListener('compositionstart', function () {
        isComposing = true; // 한글 조합 중
    });

    // 한글 입력 끝날 때
    nameInput.addEventListener('compositionend', function () {
        isComposing = false; // 한글 조합 완료
        isKoreanOrEnglish(nameInput); // 한글 조합 후 검증
    });

    // oninput 이벤트에서 한글 조합 중이 아닐 때만 검증
    nameInput.addEventListener('input', function () {
        if (!isComposing) {
            isKoreanOrEnglish(nameInput);
        }
    });
});

// 한글과 영문만 입력 허용하는 함수
function isKoreanOrEnglish(input) {
    const pattern = /[^가-힣a-zA-Z]/g;
    input.value = input.value.replace(pattern, ''); // 한글과 영문 외의 문자는 제거
}

</script>
</head>
<body>

<jsp:include page="../../jsp/common/_header.jsp"></jsp:include>
<main class="layout-content">
<div class="content-inner">
<div class="main-content">
<div class="container">
<div class="join-img"></div>
<form id="joinform" name="joinForm" method="post" action="/member/memberProcess.do" onSubmit="return Validation() && submitForm();">
    <p>아이디</p>
    <div class="input-container">
    <input type="text" class="rectangle-4141" id="userId" name="userId" value="" placeholder="영문,숫자포함 4~12자" oninput="restrictHangul(this)" >
    <button type="button" class="btn-02" onclick="checkDuplicateId()">중복확인</button>
    </div>
    <div id="id-check-message" style="color: red;"></div>
    <div class="empty-id-message hide effefont">아이디를 입력해주세요.</div>
    
    <p>비밀번호</p>
    <div class="input-container">
    <input type="password" class="rectangle-4142" id="userPwd" name="userPwd" value="" placeholder="영문,숫자,특수문자(@$!%*#?&)포함 8~16자"><br> 
    </div>
    <div class="empty-password-message hide effefont">비밀번호를 입력해주세요.</div>
    
    <p>비밀번호 확인</p>
    <div class="input-container">
    <input type="password" class="rectangle-4142" id="userPwd2" name="userPwd2" value="" placeholder="비밀번호확인을 입력해주세요."><br>
    </div>
    <div class="mismatch-message hide effefont">비밀번호가 일치하지 않습니다</div>
    
    <p>이름</p>
    <div class="input-container">
    <input type="text" class="rectangle-4142" id="userName" name="userName" value="" placeholder="이름을 입력해주세요."><br> 
    </div>
    <div class="name-message hide effefont">이름을 입력해주세요.</div>
    
    <p>핸드폰</p>
    <div class="input-container">
    <input type="text" class="rectangle-4142" id="userPhone" name="userPhone" oninput="autoHyphen2(this)" maxlength="13" value="" placeholder="핸드폰번호를 입력해주세요."><br>
    </div>
    <div class="phone-message hide effefont">유효한 핸드폰 번호를 입력해주세요.</div>
    
    
    <p>이메일</p>
   	<div class="input-container">
		<input type="email" class="rectangle-4141" id="userEmail" name="userEmail" value="" placeholder="이메일을 입력해주세요.">
		<button type="button" class="btn-01" id="sendEmailButton" onclick="sendEmailAuthCode()">이메일 인증</button>
	</div>
		<div class="email-message hide effefont">유효한 이메일 주소를 입력해주세요.</div>
	
	
	<p>인증번호</p>	
    <div class="input-container">
		<input type="text" class="rectangle-4141" id="inputAuthCode" name="authCode" placeholder="인증번호를 입력해주세요." disabled>
		<button type="button" class="btn-02" id="authCodeCheckBtn" onclick="authCodeCheck()" disabled>인증</button>
		<input type="hidden" id="authVerified" name="authVerified" value="false">
	</div>	
		<div class="auth-failure-message hide">인증번호가 일치하지 않습니다.</div>
		<div class="auth-success-message hide effefont">인증이 완료되었습니다.</div>
    	
    <p>우편번호</p>	
    <div class="input-container">
        <input type="text" class="rectangle-4141" id="sample6_postcode" name="userPost" placeholder="우편번호">
		<button type="button" onclick="sample6_execDaumPostcode()" class="btn-01">우편번호 찾기</button><br>
		<input type="text" class="rectangle-4142" id="sample6_address" name="userAddr" placeholder="주소"><br>
		<input type="text" class="rectangle-4142" id="sample6_detailAddress" name="userAddrDtl" placeholder="상세주소">
    </div>
    
    <p>성별</p>
    <div class="gender-container">
    <div class="gender-option">
        <input type="radio" id="userGenderCd1" name="userGenderCd" value="M" checked>
        <label for="userGenderCd1"></label>
        <span>남자</span>
    </div>
    <div class="gender-option">
        <input type="radio" id="userGenderCd2" name="userGenderCd" value="W">
        <label for="userGenderCd2"></label>
        <span>여자</span>
    </div>
</div>
    <button type="submit" class="btn-03">회원가입</button>
    <button type="button" class="btn-03" id="cancelButton">취소</button>
</form>
</div>
</div>
</div>
</main>
<jsp:include page="../../jsp/common/_footer.jsp"></jsp:include>
</body>

</html>