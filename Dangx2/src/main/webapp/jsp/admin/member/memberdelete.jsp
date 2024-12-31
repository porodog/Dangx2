<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 삭제 결과</title>
<%
    String status = request.getParameter("status");
%>
</head>
<body>
<h1>회원 삭제 결과</h1>
    <%
        if ("empty".equals(status)) {
    %>
        <p>삭제할 회원을 선택해주세요.</p>
    <%
        } else {
    %>
        <p>선택된 회원이 삭제되었습니다.</p>
    <%
        }
    %>
    <a href="/admin/member/list.do">회원 관리 페이지로 돌아가기</a>
</body>
</html>