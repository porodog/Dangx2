<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>
<link rel="stylesheet" href="../../resources/css/member/findpw.css"/>
<script defer src="../../resources/js/member/idpwauthcode.js"></script>
<script defer src="../../resources/js/member/authCodecheck.js"></script>
<script defer src="../../resources/js/member/findpweffe.js"></script>
<script defer src="../../resources/js/member/konotinput.js"></script>
<% String errorMessage = (String) request.getAttribute("error"); %>
<script type="text/javascript">
window.onload = function() {
    const errorMessage = "<%= errorMessage != null ? errorMessage : "" %>";
    if (errorMessage !== "") {
        alert(errorMessage);  // 에러 메시지가 있을 경우에만 alert로 출력
    }
};
</script>
</head>
<body>
<jsp:include page="../../jsp/common/_header.jsp"></jsp:include>
<main class="layout-content">
	<div class="main-content">
	<section>
		<div class="container">
		<div class="findid-img"></div>
	<h2>비밀번호 찾기</h2>
	<form id="findpasswordform" method="post" onSubmit="return ValidationPW()" action="/member/findPassword.do">
		<p>이름</p> 
		<div class="input-container">
		<input type="text" id="userName" placeholder="이름을 입력해주세요." name="userName">
		</div>
		<div class="name-message hide effefont">이름을 입력해주세요.</div>
		
		<p>아이디</p> 
		<div class="input-container">
		<input type="text" id="userId" placeholder="영문,숫자포함 4~12자" name="userId" oninput="restrictHangul(this)">
		</div>
    	<div class="empty-id-message hide effefont">아이디를 입력해주세요.</div>
		
		<p>이메일</p> 
		<div class="input-container">
		<input type="email" id="userEmail" class="rectangle-4141" placeholder="이메일을 입력해주세요." name="userEmail">
		<button type="button" id="sendEmailButton" class="btn-01" onclick="sendEmailAuthCodeForRecovery()">이메일 인증</button>
		</div>
		<div class="email-message hide effefont">유효한 이메일 주소를 입력해주세요.</div>
		
		

		<p>인증번호</p>
		<div class="input-container">
		<input type="text" id="inputAuthCode" class="rectangle-4141" name="authCode" placeholder="인증번호를 입력해주세요." disabled>
		<button type="button" id="authCodeCheckBtn" class="btn-01" onclick="authCodeCheck()" disabled>인증</button>
		</div>
		<input type="hidden" id="authVerified" name="authVerified" value="false">
		<div class="auth-failure-message hide effefont">인증번호가 일치하지 않습니다.</div>
		<div class="auth-success-message hide" style="color:green;">인증이 완료되었습니다.</div>
		<button type="submit" class="btn-02">비밀번호 찾기</button>
	</form>
	   </div>
	   </section>
    </div>
   </main>
   <jsp:include page="../../jsp/common/_footer.jsp"></jsp:include>
</body>
</html>