<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="login.LoginDTO"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // 세션에서 사용자 정보 가져오기 (세션이 없으면 기본값 설정)
    LoginDTO sessionDTO = (LoginDTO) session.getAttribute("userInfo");

    String sessionId = (sessionDTO != null) ? String.valueOf(sessionDTO.getIdx()) : "test_user";
    String user_id = (sessionDTO != null) ? sessionDTO.getUserId() : "test_user";  // 나중에 수정

    // URL에서 게시판별 타입을 파라미터로 가져오기
    String boardType = request.getParameter("boardType");
    if (boardType == null) {
        boardType = "1"; // 기본값으로 쉼터 게시판 선택
    }
    
    // boardType을 request 속성으로 설정
    request.setAttribute("boardType", boardType);
    
    SimpleDateFormat sf = new SimpleDateFormat("yy.MM.dd");
    String today = sf.format(new Date());
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 공통사용 css -->
<link rel="icon" href="../../resources/image/fav_favicon.ico" type="image/x-icon"
      style="background-color: transparent;">
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>

<!-- 게시판 영역 -->
<link rel="stylesheet" href="../../resources/css/board/insert.css"/>
<script defer type="text/javascript" src="../../resources/js/board/common.js"></script>
<script defer type="text/javascript" src="../../resources/js/board/editForm.js"></script>
</head>
<body>
	<jsp:include page="../common/_header.jsp"></jsp:include>
	
	<main class="layout-content">
		<!-- 컨텐츠 제작 영역 -->
		<div class="content-inner">
			<h1>댕댕히어로즈 <span>게시판</span></h1>
			
			<section class="inner-section">
				<form name="newWrite" action="${pageContext.request.contextPath}/BoardWriteAction.do" method="post" enctype="multipart/form-data" onsubmit="return checkForm()">
				<input name="id" type="hidden" value="<%=sessionId %>">
				<div class="section-board-title">
					<div>
						<dl>
							<dd>
								<select name="board_type_cd">				
									<option value="1" <c:if test="${boardType == '1'}">selected</c:if>>쉼터</option>
									<option value="2" <c:if test="${boardType == '2'}">selected</c:if>>입양</option>
									<option value="3" <c:if test="${boardType == '3'}">selected</c:if>>후원</option>
								</select>
								<input type="text" name="title" placeholder="제목을 입력해 주세요.">
							</dd>
						</dl>
					</div>
					<div>
						<ul>
							<li><%=user_id %></li>
							<li><%=today %></li>
						</ul>
					</div>
				</div>
				
				<div class="section-board-items">
					<div class="board-content">
						<textarea name="content" placeholder="내용을 작성해 주세요."></textarea>
					</div>
					<div class="board-file">
						<input type="file" class="input-file" id="fileUpload" name="fileUpload" multiple>
						<label class="label-file" for="fileUpload">파일선택</label>
						<span class="span-file">선택된 파일이 없습니다.</span>
					</div>
				</div>
				
				<div class="section-board-button">
					<button type="submit" class="submit-button">등록</button>
					<button type="button" class="back-button" onclick="location.href='/boardList<%=boardType %>.do';">목록</button>
				</div>
				</form>
			</section>
		</div>
	</main>
	
	<jsp:include page="../common/_footer.jsp"></jsp:include>
</body>
</html>
