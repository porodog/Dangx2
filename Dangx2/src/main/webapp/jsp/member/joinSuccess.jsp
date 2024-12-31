<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
        window.onload = function() {
            var successMessage = "<%= request.getAttribute("successMessage") %>";
            if (successMessage) {
                alert(successMessage);
                window.location.href = "/main/mainPage.do";  // 메인 페이지로 리다이렉트
            }
        }
    </script>
<title>회원가입 완료</title>
</head>
<body>

</body>
</html>