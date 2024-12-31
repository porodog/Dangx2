<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>

<!-- 공통사용 css -->
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>
<link rel="stylesheet" href="../../resources/css/member/displayFoundId.css"/>

</head>
<body>
	<jsp:include page="../../jsp/common/_header.jsp"></jsp:include>
	
	<main class="layout-content">
		<!-- 컨텐츠 제작 영역 -->
		<div class="content-inner">
		<div class=container>
		<img alt="Logo" src="./image/logo_dangx2.png">
			<div class="foundId">
			<h1>아이디 찾기 결과</h1>
			<div class="line1"></div>
    <p>고객님의 정보와 일치하는 ID는 <br><strong><%= request.getAttribute("foundId") %></strong> 입니다.</p>
    <a href="/login/loginPage.do">로그인 페이지로 이동</a>
			</div>
		</div>
		</div>
	</main>
	
<jsp:include page="../../jsp/common/_footer.jsp"></jsp:include>
</body>
</html>