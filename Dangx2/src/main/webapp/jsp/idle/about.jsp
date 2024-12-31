<%@page import="idle.IdleDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String menuType = (String) request.getAttribute("menuType");
	String menuName = "쉼터";
	if(menuType.equals("protect")) {
		menuName = "임시보호";
	}
	else if(menuType.equals("adoption")) {
		menuName = "입양";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=menuName %> 안내</title>

<!-- 공통사용 css -->
<link rel="icon" href="../../resources/image/fav_favicon.ico" type="image/x-icon"
      style="background-color: transparent;">
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>

<link rel="stylesheet" href="../../resources/css/idle/about.css"/>
</head>
<body>
	<jsp:include page="../common/_header.jsp"></jsp:include>
	
	<main class="layout-content">
		<!-- 컨텐츠 제작 영역 -->
		<div class="content-inner">
			<h1>댕댕히어로즈 <span><%=menuName %> 안내</span></h1>
			
			<section class="inner-section">
				<div class="section-board-title">
					<!-- <div>
						<span class="title-about-icon"></span>
						<h1>쉼터 <span>안내</span></h1>
					</div> -->
					<%-- <div>
						<h1><%=menuName %> 안내</h1>
					</div> --%>
				</div>
				
				<div class="section-board-list">
					<div class="boost-image">
						<h1></h1>
					</div>
					
					<div class="boost-content">
						<div class="inner-rectangle">
                			<h1></h1>
                		</div>
                		<div class="inner-content">
		                	<p>우리 아이들은 대부분 중대형견들입니다. 품종견이 아니라고 해서 지능이 떨어지거나 반려견으로서 부족함이</p>
							<p>있는 것은 아닙니다. 너무나 사랑스럽고 사랑을 갈구하는 상처받은 아이들입니다.</p>
							<p>사지말고 입양해 주세요. 입양에 관심 있으시다면, 아이들 정보를 보시고 맘에 들어오는 아이들 후보를</p>
							<p>생각하신 후 <span>카카오 채널(QR코드 스캔)</span>로 연락 주시면 상담해 드립니다.</p>
						</div>
					</div>
				</div>
			</section>
		</div>
	</main>
	
	<jsp:include page="../common/_footer.jsp"></jsp:include>
</body>
</html>