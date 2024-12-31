<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>
<link rel="stylesheet" href="../../resources/css/member/myPagelogin.css"/>
    <title>비밀번호 확인</title>
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('cancelButton').addEventListener('click', function() {
            window.location.href = '/jsp/main/mainPage.jsp'; // 메인 페이지 URL로 수정
        });
    });
</script>
</head>
<body>
<jsp:include page="../../jsp/common/_header.jsp"></jsp:include>
<main class="layout-content">
	<div class="main-content">
	<section>
		<div class="container">
		<div class="findid-img"></div>
		<h2>마이페이지</h2>
    <form id="verifyPasswordForm" method="post" action="/member/verifyPassword.do">
        <p>비밀번호</p>
        <div class="input-container">
        <input type="password" id="userPwd" name="userPwd" placeholder="비밀번호를 입력해주세요." required>
        <button type="submit" class="btn-01">확인</button>
        <button type="button" id="cancelButton" class="btn-01">취소</button>
        </div>
    </form>
	    </div>
	   </section>
    </div>
   </main>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <%
        if (errorMessage != null) {
    %>
        <div style="color: red;"><%= errorMessage %></div>
    <%
        }
    %>
<jsp:include page="../../jsp/common/_footer.jsp"></jsp:include>
</body>
</html>
