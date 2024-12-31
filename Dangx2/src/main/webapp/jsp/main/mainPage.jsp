<%@page import="board.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.tomcat.jakartaee.commons.lang3.ObjectUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%
	List<BoardDTO> boardList1 = (List<BoardDTO>) request.getAttribute("boardList1");
	List<BoardDTO> boardList2 = (List<BoardDTO>) request.getAttribute("boardList2");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댕댕히어로즈</title>

<!-- 공통사용 css -->
<link rel="icon" href="../../resources/image/fav_favicon.ico" type="image/x-icon"
      style="background-color: transparent;">
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>

<!-- 메인페이지 -->
<link rel="stylesheet" href="../../resources/css/main/mainPage.css"/>
</head>
<body>
	<jsp:include page="../common/_header.jsp"></jsp:include>
	
	<main class="layout-content">
	<!-- 컨텐츠 제작 영역 -->
	
		<div class="content-banner">
			<div>
				<div class="banner-content">
					<h1>댕댕히어로즈에</h1>
					<h1>오신것을 환영합니다.</h1>
					<br/><br/>
					<p>자원봉사자들과 후원자들이 모여</p>
					<p>유실견들을 보호하고, 임시보호자와 연결하거나</p>
					<p>국내외로 입양보내는 비영리단체입니다.</p>
				</div>
				<div class="banner-image">
					<h1></h1>
				</div>
			</div>
		</div>
		<div class="paw-container">
			<img id="paw1" class="paw" src="../../resources/image/lpaw.png">
			<img id="paw2" class="paw" src="../../resources/image/rpaw.png">
		</div>
	
		<div class="content-inner">
			<section class="inner-section">
				<h1 class="section-title">댕댕히어로즈의 <span>운영방식</span></h1>
				<div class="section-about-content">
					<div>
						<p class="section-about-content-number">1</p>
						<p>댕댕히어로즈는 순수하게 자원봉사자들로 운영됩니다.</p>
					</div>
					<div>
						<p class="">2</p>
						<p>댕댕히어로즈는 비영리단체이기 때문에 100% 후원으로만 운영되고 있습니다.</p>
					</div>
					<div>
						<p class="">3</p>
						<p>후원받는 물품이나 후원금은 모두 쉼터 아이들을 위해 사용되고 있습니다.</p>
					</div>
				</div>
			</section>
			
			<section class="inner-section">
				<div class="section-link-content">
					<div class="link-content-boost">
						<a href="/contents/boost.do">
							<div>
								<h2 class="link-content-boost-image"></h2>
								<p>후원<br/>바로가기</p>
							</div>
						</a>
					</div>
					<div class="link-content-adoption">
						<a href="/idle/adoption/about.do">
							<div>
								<h2 class="link-content-adoption-image"></h2>
								<p>입양<br/>바로가기</p>
							</div>
						</a>
					</div>
					<div class="link-content-service">
						<a href="/bongsa/about.do">
							<div>
								<h2 class="link-content-service-image"></h2>
								<p>봉사<br/>바로가기</p>
							</div>
						</a>
					</div>
				</div>
			</section>
			
			<section class="inner-section">
				<div class="section-board-title">
					<div>
						<span class="board-title-shlter-icon"></span>
						<h1>쉼터게시판</h1>
					</div>
					<a href="/boardList1.do">더보기</a>
				</div>
				<div class="section-board-list">
					<ul>
<%
	if(!ObjectUtils.isEmpty(boardList1)) {
%>
					<c:forEach var="dto" items="${boardList1}">
						<li>
							<a href="/readForm.do?idx=${dto.idx}&boardType=${dto.board_type_cd}">
								<div><c:out value="${dto.title}" /></div>
								<div>
									<fmt:formatDate value="${dto.reg_date}" pattern="yy.MM.dd" />
								</div>
							</a>
						</li>
					</c:forEach>
<%			
	}
%>
					</ul>
				</div>
			</section>
			
			<section class="inner-section">
				<div class="section-board-title">
					<div>
						<span class="board-title-free-icon"></span>
						<h1>입양게시판</h1>
					</div>
					<a href="/boardList2.do">더보기</a>
				</div>
				<div class="section-board-list">
					<ul>
<%
	if(!ObjectUtils.isEmpty(boardList2)) {
%>
					<c:forEach var="dto" items="${boardList2}">
						<li>
							<a href="/readForm.do?idx=${dto.idx}&boardType=${dto.board_type_cd}"">
								<div><c:out value="${dto.title}" /></div>
								<div>
									<fmt:formatDate value="${dto.reg_date}" pattern="yy.MM.dd" />
								</div>
							</a>
						</li>
					</c:forEach>
<%			
	}
%>
					</ul>
				</div>
			</section>
			
			
			<section class="inner-section">
				<div class="section-board-title">
					<div>
						<h1>도움을 주신 분들</h1>
					</div>
				</div>
				<div class="section-help-list">
					<a href="https://www.instagram.com/k_animalstory" target="_blank">
						<div class="link-help-instagram-image"></div>
					</a>
					<a href="https://blog.naver.com/kanimalstory" target="_blank">
						<div class="link-help-blog-image"></div>
					</a>
					<a href="https://cafe.naver.com/dike201" target="_blank">
						<div class="link-help-cafe-image"></div>
					</a>
					<a href="https://www.tjoeun.co.kr" target="_blank">
						<div class="link-help-tj-image"></div>
					</a>
					<a href="#">
						<div class="link-help-noimage-image"></div>
					</a>
					<a href="#">
						<div class="link-help-noimage-image"></div>
					</a>
				</div>
			</section>
		</div>
	</main>
	
	<jsp:include page="../common/_footer.jsp"></jsp:include>
</body>
</html>