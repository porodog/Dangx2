<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>

<!-- 공통사용 css -->
<link rel="icon" href="../../resources/image/fav_favicon.ico" type="image/x-icon"
      style="background-color: transparent;">
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>

<link rel="stylesheet" href="../../resources/css/login/login.css">

<script defer type="text/javascript" src="../../resources/js/login/loginPage.js"></script>
<script defer src="../../resources/js/member/konotinput.js"></script>
<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function() {
        const togglePassword = document.querySelector('.toggle-password');
        const passwordInput = document.querySelector('#userPwd');

        togglePassword.addEventListener('click', function() {
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);

            // 아이콘 클래스 토글 (fa-eye, fa-eye-slash에 해당하는 동작)
            this.classList.toggle('fa-eye');
            this.classList.toggle('fa-eye-slash');
        });
    });
    </script>
<script src="https://kit.fontawesome.com/0efe0dbbd0.js" crossorigin="anonymous"></script>
</head>
  <body>
  <jsp:include page="../../jsp/common/_header.jsp"></jsp:include>
    <main class="layout-content">
      <!-- 로그인 폼 -->
      <div class="main-content">
        <section>
          <div class="container">
            <div class="login-img"></div>
            <form id="loginForm" action="/login/loginProcess.do" method="post">
              <!-- 서버 측 에러 메시지 출력 -->
              <%
                  String errorMessage = (String) session.getAttribute("errorMessage");
                  if (errorMessage != null) {
              %>
                  <p class="error-message"><%= errorMessage %></p>
                  <%
                      // 오류 메시지를 출력한 후, 세션에서 삭제
                      session.removeAttribute("errorMessage");
                  %>
              <%
                  }
              %>
              
              <p>아이디</p>
              <div class="input-container">
                <img src="../../resources/image/usericon.png" alt="아이디 아이콘" id="userIcon"> <!-- 아이디 아이콘 이미지 -->
                <input type="text" name="userId" id="userId"
                  placeholder="아이디를 입력해주세요" oninput="restrictHangul(this)"/>
              </div>
              <span id="userIdError" class="error-message2"></span>

              <p>비밀번호</p>
              <div class="input-container">
                <img src="../../resources/image/passicon.png" alt="비번 아이콘" id="passIcon"> <!-- 아이디 아이콘 이미지 -->
                <span class="toggle-password fa fa-eye-slash" style="cursor: pointer;"></span>
                <input type="password" name="userPwd" id="userPwd"
                  placeholder="비밀번호를 입력해주세요" />
              </div>
               <span id="userPwdError" class="error-message2"></span>
           
               
			    
              <button type="submit" id="loginButton">로그인하기</button>
            </form>
            <div class="findbtn">
              <a href="/jsp/member/FindID.jsp">아이디 찾기</a>
              <p>|</p>
              <a href="/jsp/member/FIndPassWord.jsp">비밀번호 찾기</a>
              <p>|</p>
              <a href="/jsp/member/agRee.jsp">회원가입</a>
            </div>
          </div>
        </section>
      </div>
    </main>
    <script defer type="text/javascript" src="../../resources/js/login/loginicon.js"></script>
  <jsp:include page="../../jsp/common/_footer.jsp"></jsp:include>
  </body>
</html>
