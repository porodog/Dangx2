<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String bongsaList = (String) request.getAttribute("bongsaList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>봉사 신청</title>

<!-- 공통사용 css -->
<link rel="icon" href="../../resources/image/fav_favicon.ico" type="image/x-icon"
      style="background-color: transparent;">
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>

<!-- 봉사달력 페이지 -->
<link rel="stylesheet" href="../../resources/css/bongsa/calendar.css"/>

<!-- fullcalendar api -->
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js"></script>

<!-- script -->
<script type="text/javascript">
	const bongsaList = <%=bongsaList %>;
</script>
<script defer type="text/javascript" src="../../resources/js/bongsa/calendar.js"></script>
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
						<h1>봉사 신청</h1>
					</div>
				</div>
				
				<div class="section-board-list">
					<div class="section-bongsa-calendar">
						<div id="calendar"></div>
					</div>
				</div>
			</section>
		</div>
	</main>
	
	<jsp:include page="../common/_footer.jsp"></jsp:include>
</body>
</html>