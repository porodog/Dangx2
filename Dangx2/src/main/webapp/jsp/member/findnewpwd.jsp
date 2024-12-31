<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>
<link rel="stylesheet" href="../../resources/css/member/findnewpwd.css"/>
<script defer src="../../resources/js/member/findnewpwd.js"></script>
<title>비밀번호 재설정</title>
</head>
<body>
<jsp:include page="../../jsp/common/_header.jsp"></jsp:include>
<main class="layout-content">
<div class="content-inner">
<div class="main-content">
<div class="container">
<div class="resetpw-img"></div>
	<h2>비밀번호 재설정</h2>
    <form id="resetPasswordForm" method="post" action="/member/resetPassword.do" onsubmit="return ValidationCorrection()">
    <input type="hidden" id="userId" name="userId" value="${userId}">
    
    <p>새 비밀번호</p> 
    <div class="input-container">
    <input type="password" id="newPassword" name="newPassword" placeholder="영문,숫자,특수문자(@$!%*#?&)포함 8~16자">
    </div>
    <div class="empty-password-message hide effefont">새 비밀번호를 입력해 주세요.</div>
    
    <p>비밀번호 확인</p>
    <div class="input-container">
    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="비밀번호 확인을 입력해주세요.">
    </div>
    <div class="mismatch-message hide effefont">비밀번호가 일치하지 않습니다</div>
    
    <button type="submit" class="btn-02">비밀번호 재설정</button>
    </form>
    </div>
  </div>
 </div>
</main>
<jsp:include page="../../jsp/common/_footer.jsp"></jsp:include>
</body>
</html>