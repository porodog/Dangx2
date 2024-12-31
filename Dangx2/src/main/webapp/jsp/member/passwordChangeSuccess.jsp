<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경 성공</title>
    <script type="text/javascript">
        window.onload = function() {
            // 서버에서 전달한 successMessage를 가져옵니다
            var successMessage = "<%= request.getAttribute("successMessage") != null ? request.getAttribute("successMessage") : "" %>";
            
            // 성공 메시지를 alert로 출력
            if (successMessage !== "") {
                alert(successMessage);
                // 로그인 페이지로 리다이렉트
                window.location.href = "/jsp/login/loginPage.jsp";
            }
        }
    </script>
</head>
<body>

</body>
</html>