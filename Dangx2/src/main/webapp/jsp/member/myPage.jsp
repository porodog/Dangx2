<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="login.LoginDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>
<link rel="stylesheet" href="../../resources/css/member/myPage.css"/>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('cancelButton').addEventListener('click', function() {
            window.location.href = '/jsp/main/mainPage.jsp'; // 메인 페이지 URL로 수정
        });
    });
</script>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<%
    String successMessage = (String) session.getAttribute("successMessage");
    if (successMessage != null) {
        out.println("<script>alert('" + successMessage + "');</script>");
        session.removeAttribute("successMessage"); // 한 번 출력 후 삭제
    }
%>
<script type="text/javascript">
const errorMessage = "<%= errorMessage != null ? errorMessage : "" %>";
if (errorMessage !== "") {
    alert(errorMessage);  // 에러 메시지가 있을 경우 alert로 출력
} 
</script>
<script defer src="../../resources/js/member/myPageeffe.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script defer src="../../resources/js/member/userPost.js"></script>
<script defer src="../../resources/js/member/secession.js"></script>
<title>마이 페이지</title>
</head>
<body>
<jsp:include page="../../jsp/common/_header.jsp"></jsp:include>
<%
   LoginDTO userInfo = (LoginDTO) session.getAttribute("userInfo"); // 세션에서 사용자 정보 가져오기
   	if (userInfo != null) {
%>
<main class="layout-content">
<div class="content-inner">
<div class="main-content">
<div class="container">
<div class="mypage-img"></div>

	<h2>마이 페이지</h2>
	<form name="joinForm" method="post" action="/member/updateMemberInfo.do" onSubmit="return ValidationCorrection()">
	    
	    <p>아이디</p>
	    <div class="fixedvalue">
	   	<span class="fixedvalue2"><%=userInfo.getUserId() %></span> <!-- 아이디를 세션에서 불러와 표시, 수정 불가 -->
	   	</div>	
	    
	    <p>새 비밀번호</p>
	    <div class="input-container">
	    <input type="password" id="userPwd" name="userPwd" value="" placeholder="영문,숫자,특수문자(@$!%*#?&)포함 8~16자"> 
	    </div>
	    <div class="empty-password-message hide effefont">새 비밀번호를 입력해주세요.</div>
	    
	    <p>비밀번호확인</p> 
	    <div class="input-container">
	    <input type="password" id="userPwd2" name="userPwd2" value="" placeholder="비밀번호 확인을 입력해주세요.">
	    </div>
	    <div class="mismatch-message hide effefont">비밀번호가 일치하지 않습니다</div>
	    
	    <p>이름</p>
	    <div class="fixedvalue">
	    <span class="fixedvalue2"><%=userInfo.getUserName() %></span> <!-- 이름은 수정 불가 -->
	    </div>
	    
	    <p>핸드폰</p> 
	    <div class="input-container">
	    <input type="text" id="userPhone" name="userPhone" oninput="autoHyphen2(this)" maxlength="13" value="<%= userInfo.getUserPhone() %>">
	    </div>
	    <div class="phone-message hide effefont">유효한 핸드폰 번호를 입력해주세요.</div>

	    
	   	<p>이메일</p>
	   	<div class="fixedvalue">
		<span class="fixedvalue2"><%=userInfo.getUserEmail() %></span> <!-- 이메일은 수정 불가 -->
		</div>
			
	    <p>우편번호</p>	
   		<div class="input-container">	
	        <input type="text" id="sample6_postcode" name="userPost" class="rectangle-4141" placeholder="우편번호" value="<%=userInfo.getUserPost() != null ? userInfo.getUserPost() : "" %>">
			<button type="button" onclick="sample6_execDaumPostcode()" class="btn-01">우편번호 찾기</button>
			<input type="text" id="sample6_address" name="userAddr" placeholder="주소" value="<%=userInfo.getUserAddr() != null ? userInfo.getUserAddr() : "" %>"><br>
			<input type="text" id="sample6_detailAddress" name="userAddrDtl" placeholder="상세주소" value="<%=userInfo.getUserAddrDtl() != null ? userInfo.getUserAddrDtl() : "" %>">
	    </div>
	   
	   <div class="btn">
	   <span class="secession">회원 탈퇴</span>
<!-- 	   	<button type="button" class="btn-02" value="탈퇴하기" >탈퇴하기</button> -->
	   	<input type="button"class="btn-02" value="탈퇴하기">
	   </div>
		<!-- 비밀번호 입력을 위한 모달 -->
		<div id="passwordModal" class="input-container" style="display: none;">
		        <input type="password" id="passwordInput" class="rectangle-4141" placeholder="비밀번호를 입력해주세요." />
		        <button type="button" id="confirmDelete" class="btn-04">확인</button>
		        <button type="button" id="cancelDelete" class="btn-04">취소</button>
		</div>
			  	   
	   <div class="btn2">
	   	<button type="submit" class="btn-03">정보수정</button>
	   	<button type="button" class="btn-03" id="cancelButton">취소</button>
	   </div>
	</form>
	
	</div>
</div>
</div>
</main>

<%
	} else {
	    // 로그인되지 않았을 때 처리 (예: 로그인 페이지로 리다이렉트)
	    response.sendRedirect("/jsp/login/loginPage.jsp");
	}
%>

<jsp:include page="../../jsp/common/_footer.jsp"></jsp:include>
</body>
</html>