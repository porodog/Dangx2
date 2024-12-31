<%@page import="idle.IdleDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	IdleDTO idleDTO = (IdleDTO) request.getAttribute("idleDTO");
	List<IdleDTO> idleDTOList = (List<IdleDTO>) request.getAttribute("idleDTOList");
	String getMenuType = idleDTO.getIdleTypeName();
	String menuName = "쉼터";
	if(getMenuType.equals("protect")) {
		menuName = "임시보호";
	}
	else if(getMenuType.equals("adoption")) {
		menuName = "입양";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이들 목록</title>

<!-- 공통사용 css -->
<link rel="icon" href="../../resources/image/fav_favicon.ico" type="image/x-icon"
      style="background-color: transparent;">
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>

<!-- 아이들 목록 -->
<link rel="stylesheet" href="../../resources/css/idle/list.css"/>

<script type="text/javascript">
	const getMenuType = "<%=getMenuType %>";
</script>
<script defer type="text/javascript" src="../../resources/js/idle/list.js"></script>
</head>
<body>
	<jsp:include page="../common/_header.jsp"></jsp:include>
	
	<main class="layout-content">
		<!-- 컨텐츠 제작 영역 -->
		<div class="content-inner">
			<h1>댕댕히어로즈 <span><%=menuName %> 아이들</span></h1>
			
			<section class="inner-section">
				<div class="idle-view-contents">
					<div class="view-item-list">
<% 
	for(IdleDTO dto : idleDTOList) {
		String name = dto.getIdleName();
		String breed = dto.getIdleBreed();
		String year = dto.getIdleYear();
		String imagePath = dto.getImagePath();
		
		name = (name==null)?("-"):(name);
		breed = (breed==null)?("-"):(breed);
		year = (year==null)?("-"):(year);
		imagePath = (imagePath==null)?("../../resources/image/noimage.jpg"):("/dangx2/upload/"+imagePath);
%>
						<div class="idle-item">
							<a href="/idle/<%=getMenuType %>/view.do?idx=<%=dto.getIdx() %>">
								<div class="idle-image">
									<p>보러가기&nbsp;<span>&rsaquo;</span></p>
									<img alt="" src="<%=imagePath %>">
								</div>
								<div class="idle-info">
									<p><%=name %></p>
									<p><%=breed %></p>
									<p>생년 : <%=year %></p>
								</div>
							</a>
						</div>
<%
	}
%>
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
				</div>
			</section>
		</div>
	</main>
	
	<jsp:include page="../common/_footer.jsp"></jsp:include>
</body>
</html>