<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
//Cookie user = new Cookie("id", "");
session.invalidate();//세션의 모든 속성 제거
response.sendRedirect("/main/mainPage.do");
%>
<!-- 로그아웃 처리 완료 -->
</body>
</html>