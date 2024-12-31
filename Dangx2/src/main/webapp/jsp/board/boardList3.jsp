<%@page import="login.LoginDTO"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="board.BoardDTO" %> 
<%@ page import="java.util.List" %> 
<%@ page import="java.util.ArrayList" %> 
<%@ page import="java.sql.Timestamp" %>

<%
    request.setCharacterEncoding("UTF-8");

    // 서블릿에서 전달받은 데이터를 JSP에서 사용
    List<BoardDTO> lists = (List<BoardDTO>) request.getAttribute("lists");
    lists = lists != null ? lists : new ArrayList<>();  // null 체크
	
    // 게시판 타입
    String boardTypeCd = (String) request.getAttribute("boardTypeCd");
    
    // 검색관련 데이터
    BoardDTO boardDTO = (BoardDTO) request.getAttribute("boardDTO");
    String searchKey = boardDTO.getSearchKey();
    searchKey = (searchKey==null)?"title":searchKey;
    
    String searchValue = boardDTO.getSearchValue();
    searchValue = (searchValue==null)?"":searchValue;
    
 	//세션 체크
    LoginDTO sessionDTO = (LoginDTO) session.getAttribute("userInfo");
    String sessionIdx = (sessionDTO != null)?String.valueOf(sessionDTO.getIdx()):null;
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
<link rel="stylesheet" href="../../resources/css/board/list.css"/>
<script type="text/javascript">
	const boardTypeCd = "<%=boardTypeCd%>";
	const sessionIdx = "<%=sessionIdx%>";
</script>
<script defer type="text/javascript" src="../../resources/js/board/searchForm.js"></script>
</head>
<body>
	<jsp:include page="../common/_header.jsp"></jsp:include>
	
	<main class="layout-content">
		<!-- 컨텐츠 제작 영역 -->
		<div class="content-inner">
			<h1>댕댕히어로즈 <span>게시판</span></h1>
			
			<section class="inner-section">
				<div class="board-search-content-items">
					<div class="search-button-items">
						<button type="button" class="board-button-shelter" 
							onclick="location.href='${pageContext.request.contextPath}/boardList1.do'">쉼터</button>
						<button type="button" class="board-button-adoption"
							onclick="location.href='${pageContext.request.contextPath}/boardList2.do'">입양</button>
						<button type="button" class="board-button-boost active"
							onclick="location.href='${pageContext.request.contextPath}/boardList3.do'">후원</button>
					</div>
					
					<form id="searchForm" name="searchForm" method="get">
					<div class="search-group-items">
						<input type="hidden" name="page" value="1"/>
						<div class="search-item-condition">
							<dl>
								<dt>검색어</dt>
								<dd>
									<select name="searchKey">				
										<option value="tb.title"
										<%
										if(searchKey.equals("tb.title")) {
										%>
											selected="selected"
										<%
										}
										%>
										>제목</option>
										<option value="tu.user_id" 
										<%
										if(searchKey.equals("tu.user_id")) {
										%>
											selected="selected"
										<%
										}
										%>
										>작성자</option>
										<option value="tb.content"
										<%
										if(searchKey.equals("tb.content")) {
										%>
											selected="selected"
										<%
										}
										%>
										>내용</option>
									</select>
									<input type="text" name="searchValue" value="<%=searchValue%>">
								</dd>
							</dl>
						</div>
						<div class="search-item-button">
							<button type="button" onclick="sendIt();">검색</button>
						</div>	
					</div>
					</form>
				</div>
				
				<div class="board-view-contents">
					<div class="view-item-list">
						<table>
							<colgroup>
								<col width="10%;">
								<col width="*;">
								<col width="15%;">
								<col width="15%;">
							</colgroup>
							<tbody>
								<c:if test="${totalBoards lt 1}">
								<tr class="list-nodata">
									<td colspan="4">조회 결과가 없습니다</td>
								</tr>
								</c:if>
								<c:if test="${totalBoards gt 0}">
									<c:forEach var="dto" items="${lists}">
									<tr>
										<td>${dto.idx}</td>
										<td>
											<a href="${pageContext.request.contextPath}/readForm.do?idx=${dto.idx}&boardType=<%=boardTypeCd %>">﻿﻿${dto.title}</a>
										</td>
										<td>${dto.user_id}</td>
										<td>
											<fmt:formatDate value="${dto.reg_date}" pattern="yy.MM.dd" />
										</td>
									</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
					
					<div class="view-item-page">
						<c:if test="${pagination.listCnt ge 1 }">
						<ul>
						<c:if test="${pagination.curPage ne 1}">
							<li><a href="#" onClick="fn_paging(1)">&laquo;</a></li>
							<li><a href="#" onClick="fn_paging('${pagination.prevPage }')">&lsaquo;</a></li>
						</c:if>
						<c:forEach var="pageNum" begin="${pagination.startPage }" end="${pagination.endPage }">
							<c:choose>
								<c:when test="${pageNum eq  pagination.curPage}">
				                    <li class="active"><a href="#" onClick="fn_paging('${pageNum }')">${pageNum }</a></li>
				                </c:when>
				                <c:otherwise>
				                	<li><a href="#" onClick="fn_paging('${pageNum }')">${pageNum }</a></li>
				                </c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${pagination.curPage ne pagination.pageCnt && pagination.pageCnt > 0}">
							<li><a href="#" onClick="fn_paging('${pagination.nextPage }')">&rsaquo;</a></li>
							<li><a href="#" onClick="fn_paging('${pagination.pageCnt }')">&raquo;</a></li>
						</c:if>
						</ul>
						</c:if>
					</div>
					
					<div class="view-item-button">
						<button type="button" onclick="writeForm();">글쓰기</button>
					</div>
				</div>
			</section>
		</div>
	</main>
	
	<jsp:include page="../common/_footer.jsp"></jsp:include>
</body>
</html>