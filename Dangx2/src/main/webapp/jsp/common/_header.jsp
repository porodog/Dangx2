<%@page import="login.LoginDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header id="layout-header">
	<div class="header-inner">
		<h1 class="header-logo">
			<a href="/"></a>
		</h1>

		<div class="header-navigation">
			<nav>
				<ul>
					<li>
						<a href="/contents/about.do">소개</a>
						<!-- <ul>
							<li><a href="#">안내</a></li>
						</ul> -->
					</li>
					<li>
						<a href="/idle/shelter/about.do">쉼터</a>
						<ul>
							<li><a href="/idle/shelter/about.do">안내</a></li>
							<li><a href="/idle/shelter/list.do">아이들</a></li>
						</ul>
					</li>
					<li>
						<a href="/idle/adoption/about.do">입양</a>
						<ul>
							<li><a href="/idle/adoption/about.do">안내</a></li>
							<li><a href="/idle/adoption/list.do">아이들</a></li>
						</ul>
					</li>
					<li>
						<a href="/contents/boost.do">후원</a>
						<!-- <ul>
							<li><a href="#">안내</a></li>
						</ul> -->
					</li>
					<li>
						<a href="/bongsa/about.do">봉사</a>
						<!-- <ul>
							<li><a href="#">신청</a></li>
						</ul> -->
					</li>
					<li>
						<a href="/boardList1.do">커뮤니티</a>
						<ul>
							<li><a href="/boardList1.do">쉼터</a></li>
							<li><a href="/boardList2.do">입양</a></li>
							<li><a href="/boardList3.do">후원</a></li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>
		
		<div class="header-info">
			<nav>
				<ul>
				<%
					LoginDTO userInfo = (LoginDTO) session.getAttribute("userInfo"); // 세션에서 사용자 정보 가져오기
					if (userInfo != null) {
				%>
					<!-- 로그인된 경우 환영 메시지와 로그아웃 링크 -->
					<li><a href="/jsp/member/myPagelogin.jsp"><%= userInfo.getUserId() %>님</a></li>
					<li><a href="../../jsp/login/logout2.jsp">로그아웃</a></li>
				<%
				} else {
				%>
					<li><a href="/login/loginPage.do">로그인</a></li>
					<li><a href="../../jsp/member/agRee.jsp">회원가입</a></li>
				<%
				}
				%>
				</ul>
			</nav>
		</div>
      </div>
</header>