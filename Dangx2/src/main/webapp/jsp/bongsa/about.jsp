<%@page import="org.apache.tomcat.jakartaee.commons.lang3.ObjectUtils"%>
<%@page import="login.LoginDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	LoginDTO sessionDTO = (LoginDTO) session.getAttribute("userInfo");
	String sessionId = "";
	if(!ObjectUtils.isEmpty(sessionDTO)) {
		sessionId = sessionDTO.getUserId();
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>봉사 안내</title>

<!-- 공통사용 css -->
<link rel="icon" href="../../resources/image/fav_favicon.ico" type="image/x-icon"
      style="background-color: transparent;">
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>

<!-- 봉사안내 -->
<link rel="stylesheet" href="../../resources/css/bongsa/about.css"/>
<script type="text/javascript">
	const sessionId = "<%=sessionId %>";
</script>
<script defer type="text/javascript" src="../../resources/js/bongsa/about.js"></script>
</head>
<body>
	<jsp:include page="../common/_header.jsp"></jsp:include>
	
	<main class="layout-content">
		<!-- 컨텐츠 제작 영역 -->
		<div class="content-inner">
			<h1>댕댕히어로즈 <span>봉사</span></h1>
			
			<section class="inner-section">
				<div class="section-board-title">
					<div>
						<h1>봉사 안내</h1>
					</div>
				</div>
				
				<div class="section-board-list">
					<div class="boost-image">
						<h1></h1>
					</div>
					<div class="boost-content">
						<p>댕댕히어로즈는 봉사자분들의 힘으로 운영됩니다.</p>
						<p>기본적으로 하루에 두 번 배변할 수 있도록 견사 밖으로 산책시키고 견사를 청소하며 배식해 주는 일을 합니다.</p>
						<p>평일은 물론 휴일에도 거를 수 없는 일입니다.</p>
						<p>많은 봉사자분들이 계시지만 여러 가지 여건의 제약으로 인해 쉼터 봉사를 해주시는 분이 많지 않습니다.</p>
						<p>하루만 짬을 내어 봉사에 참여해 주세요.</p>
					</div>
					
					<div class="boost-about">
						<div class="section-about-content">
							<div>
								<div>
									<p class="section-about-content-number">1</p>
									<p>신청방법</p>
								</div>
								<div>
									<p>회원가입 후 로그인하시면 신청이 가능합니다.</p>
									<p>원하는 날짜를 선택하신 후, 정보확인 및 추가 구성원의 정보를 입력해 주세요.</p>
								</div>
							</div>
							<div>
								<div>
									<p class="section-about-content-number">2</p>
									<p>신청제한</p>
								</div>
								<div>
									<p>원활한 스케줄 관리를 위해 금일기준 +1일부터 +1월까지 신청할 수 있습니다.</p>
									<p>1회 신청 시, 최대인원은 신청자를 포함한 4명입니다.</p>
								</div>
							</div>
							<div>
								<div>
									<p class="section-about-content-number">3</p>
									<p>비고</p>
								</div>
								<div>
									<p>첫 봉사 때는 다른 봉사자님들이 함께해서 안내해 주십니다.</p>
									<p>안전상의 이유나 기타 사유로 인해 신청한 봉사가 취소될 수 있습니다.</p>
									<p>봉사에 관한 문의는 “엔젤봉사단” 오픈채팅방에서 이루집니다.</p>
								</div>
							</div>
						</div>
						<div class="section-about-button">
							<button type="button" id="applyBtn">신청하기</button>
						</div>
					</div>
				</div>
			</section>
		</div>
	</main>
	
	<jsp:include page="../common/_footer.jsp"></jsp:include>
</body>
</html>