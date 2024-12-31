<%@page import="board.BoardReplyDTO"%>
<%@page import="org.apache.tomcat.jakartaee.commons.lang3.ObjectUtils"%>
<%@page import="common.file.FileDTO"%>
<%@page import="java.util.List"%>
<%@page import="login.LoginDTO"%>
<%@page import="board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    LoginDTO sessionDTO = (LoginDTO) session.getAttribute("userInfo");  //세션 정보
    
    BoardDTO boardDTO = (BoardDTO) request.getAttribute("boardDTO");
    List<FileDTO> fileDTOList = (List<FileDTO>) request.getAttribute("fileDTOList");
    List<BoardReplyDTO> replyDTOList = (List<BoardReplyDTO>) request.getAttribute("replyDTOList");
   
    Integer sessionIdx = null;
	if(!ObjectUtils.isEmpty(sessionDTO)) {
		sessionIdx = sessionDTO.getIdx();
	}
   
    String user_id = (sessionDTO != null) ? sessionDTO.getUserId() : "test_user";  // 나중에 수정
    
    String boardType = request.getParameter("boardType");
    if (boardType == null) {
        boardType = "1"; // 기본값으로 쉼터 게시판 선택
    }
    
    String boardTypeName = "쉼터";
    if(boardType.equals("2")) {
    	boardTypeName = "입양";
    }
    else if(boardType.equals("3")) {
    	boardTypeName = "후원";
    }
    
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
<link rel="stylesheet" href="../../resources/css/board/view.css"/>

<script type="text/javascript">
	const getBoardType = "<%=boardType %>";
	const getBoardIdx = "<%=boardDTO.getIdx() %>";
</script>
<script defer type="text/javascript" src="../../resources/js/board/common.js"></script>
<script defer type="text/javascript" src="../../resources/js/board/readForm.js"></script>
</head>
<body>
	<jsp:include page="../common/_header.jsp"></jsp:include>
	
	<main class="layout-content">
		<!-- 컨텐츠 제작 영역 -->
		<div class="content-inner">
			<h1>댕댕히어로즈 <span>게시판</span></h1>
			
			<section class="inner-section">
				<div class="section-board-title">
					<h1>[<%=boardTypeName %>] <%=boardDTO.getTitle() %></h1>
					<div>
						<ul>
							<li><%=boardDTO.getUser_id() %></li>
							<li>
								<fmt:formatDate value="${boardDTO.reg_date}" pattern="yy.MM.dd" />
							</li>
						</ul>
					</div>
				</div>
				
				<div class="section-board-items">
					<div class="board-content">
						<%=boardDTO.getContent() %>
					</div>
					<div class="board-file">
<%
	if(!ObjectUtils.isEmpty(fileDTOList)) {
%>
						<ul>
<%
		for(FileDTO fileDTO : fileDTOList) {
%>
						
							<li><a href="/common/file/download.do?idx=<%=fileDTO.getIdx()%>"><%=fileDTO.getOrgName() %></a></li>
<%			
		}
%>
						</ul>
<%		
	}
%>
					</div>
				</div>
				
				<div class="section-board-button">
<%
	if(sessionIdx!=null && sessionIdx>0 && sessionIdx==boardDTO.getReg_idx()) {
%>

					<button type="button" class="submit-button" onclick="location.href='/editForm.do?idx=<%=boardDTO.getIdx()%>&boardType=<%=boardType%>';">수정</button>
<%		
	}
%>
					<!-- <button type="button" class="delete-button">삭제</button> -->
					<button type="button" class="back-button" onclick="location.href='/boardList<%=boardType %>.do';">목록</button>
				</div>
			</section>
			
			<section class="inner-section">
				<div class="section-reply-title">
					<h1>댓글 <span><%=ObjectUtils.isEmpty(replyDTOList)?"0":replyDTOList.size() %></span>개</h1>
				</div>
				
				<div class="section-reply-input">
<%
	if(sessionIdx!=null && sessionIdx>0) {
%>
					<input type="text" id="replyInsertText">
					<button type="button" id="replyInsertButton">등록</button>
<%		
	}
	else {
%>
					<p>댓글등록은 로그인 후 가능합니다</p>
<%		
	}
%>
				</div>
				
				<div class="section-reply-list">
					<ul>
<%
	if(!ObjectUtils.isEmpty(replyDTOList)) {
		for(BoardReplyDTO dto : replyDTOList) {
%>
						<li>
							<div class="section-reply-info">
								<div class="reply-info-item">
									<ul>
										<li><%=dto.getUserId() %></li>
										<li><%=dto.getRegDt() %></li>
									</ul>
								</div>
<%
			if(sessionIdx!=null && sessionIdx>0 && sessionIdx==dto.getRegIdx()) {
%>
								<div class="reply-button-item">
									<div class="button-item-type1">
										<button type="button" class="reply-modify-button">수정</button>
										<button type="button" class="reply-delete-button" data-reply-idx="<%=dto.getIdx() %>">삭제</button>
									</div>
									<div class="button-item-type2">
										<button type="button" class="reply-submit-button" data-reply-idx="<%=dto.getIdx() %>">등록</button>
										<button type="button" class="reply-cancel-button">취소</button>
									</div>
								</div>
<%				
			}
%>
							</div>
							<div class="section-reply-content">
								<p><%=dto.getContent() %></p>
								<input type="text" id="replyModifyText-<%=dto.getIdx() %>">
							</div>
						</li>
<%
		}
	}
	else {
%>						
						<li class="list-nodata">
							등록 된 댓글이 없습니다
						</li>
<%
	}
%>
					</ul>
				</div>
			</section>
		</div>
	</main>
	
	<jsp:include page="../common/_footer.jsp"></jsp:include>
</body>
</html>