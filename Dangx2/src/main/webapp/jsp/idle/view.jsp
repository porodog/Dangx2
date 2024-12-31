<%@page import="org.apache.tomcat.jakartaee.commons.lang3.StringUtils"%>
<%@page import="idle.IdleReplyDTO"%>
<%@page import="common.file.FileDTO"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.tomcat.jakartaee.commons.lang3.ObjectUtils"%>
<%@page import="idle.IdleDTO"%>
<%@page import="login.LoginDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	LoginDTO sessionDTO = (LoginDTO) session.getAttribute("userInfo");
	Integer sessionIdx = null;
	if(!ObjectUtils.isEmpty(sessionDTO)) {
		sessionIdx = sessionDTO.getIdx();
	}
	IdleDTO idleDTO = (IdleDTO) request.getAttribute("idleDTO");
	String getMenuType = idleDTO.getIdleTypeName();
	
	IdleDTO dataDTO = (IdleDTO) request.getAttribute("dataDTO");
	List<FileDTO> fileDTOList = (List<FileDTO>) request.getAttribute("fileDTOList");
	List<IdleReplyDTO> idleReplyDTOList = (List<IdleReplyDTO>) request.getAttribute("idleReplyDTOList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이들 상세</title>

<!-- 공통사용 css -->
<link rel="icon" href="../../resources/image/fav_favicon.ico" type="image/x-icon"
      style="background-color: transparent;">
<link rel="stylesheet" href="../../resources/css/reset.css"/>
<link rel="stylesheet" href="../../resources/css/common.css"/>

<!-- 아이들 상세 -->
<link rel="stylesheet" href="../../resources/css/idle/view.css">

<script type="text/javascript">
	const getMenuType = "<%=getMenuType %>";
	const getIdleIdx = "<%=dataDTO.getIdx() %>";
</script>
<script defer type="text/javascript" src="../../resources/js/idle/view.js"></script>
</head>
<body>
	<jsp:include page="../common/_header.jsp"></jsp:include>
	
	<main class="layout-content">
            <!-- 컨텐츠 제작 영역 -->
            <div class="content-inner">
                <section class="inner-section1">
                   <div id="bigImages">
<% 
	String noImagePath = "../../resources/image/noimage.jpg";
	if(fileDTOList!=null && fileDTOList.size()>0) {
		String firstImagePath = "/dangx2/upload/" + fileDTOList.get(0).getPath() + fileDTOList.get(0).getSaveName();
%>
                       <img id="big" src="<%=firstImagePath %>">
                   </div>
                   <div class="smallImages">
<%
		int index = 0;
		for(FileDTO dto : fileDTOList) {
			String imagePath = "/dangx2/upload/" + dto.getPath() + dto.getSaveName();
%>
                       <img src="<%=imagePath %>" class="small">
<%
			index++;
		}
		
		if(index<3) {
			for(int i=index; i<3; i++) {
%>
						<img src="<%=noImagePath %>" class="small">
<%				
			}
		}
%>
                   </div>
<%
	}
	else {
%>
						<img id="big" src="<%=noImagePath %>">
					</div>
					<div class="smallImages">
<%
		for(int i=0; i<3; i++) {
%>
						<img src="<%=noImagePath %>" class="small">
<%
		}
%>								
					</div>
<%
	}
%>					
               	</section>
                
                    <!-- 
                    <section class="inner-section1">
                        <div id="bigImages">
                            <img id="big" src="./image/idle-img/가온1.jpg">
                        </div>
                        <div class="smallImages">
                            <img src="./image/idle-img/가온1.jpg" class="small">
                            <img src="./image/dangdog2.png" class="small">
                            <img src="./image/dangdog1.png" class="small">
                        </div>
                    </section> 
                    -->
                    <div class="about-table" >
<%
	String idleName = dataDTO.getIdleName();
	String neuterYn = dataDTO.getIdleNeuterYn();
	String genderName = dataDTO.getIdleGenderName();
	String idleYear = dataDTO.getIdleYear();
	String breed = dataDTO.getIdleBreed();
	String kg = dataDTO.getIdleKg();
	
	idleName = StringUtils.isBlank(idleName)?"-":idleName;
	neuterYn = StringUtils.isBlank(neuterYn)?"-":neuterYn;
	genderName = StringUtils.isBlank(genderName)?"-":genderName;
	idleYear = StringUtils.isBlank(idleYear)?"-":idleYear;
	breed = StringUtils.isBlank(breed)?"-":breed;
	kg = StringUtils.isBlank(kg)?"-":kg + "kg";
%>
                	<table>
                		<tr>
                			<th>이름</th>
                			<td><%=idleName %></td>
                		</tr>
                		<tr>
                			<th>중성화</th>
                			<td><%=neuterYn %></td>
                		</tr>
                	</table>
                	<table>
                		<tr>
                			<th>성별</th>
                			<td><%=genderName %></td>
                		</tr>
                		<tr>
                			<th>생년</th>
                			<td><%=idleYear %></td>
                		</tr>
                	</table>
                	<table>
                		<tr>
                			<th>품종</th>
                			<td><%=breed %></td>
                		</tr>
                		<tr>
                			<th>체중</th>
                			<td><%=kg %></td>
                		</tr>
                	</table>
                </div>
                <section class = "inner-section">
                	<div class="text-section">
                		<div>
                	<span class="title-about-icon"></span>
                	<h1 class= "section-title">강아지
                	<span>소개서</span>
                	</h1>
                	</div>
                	<div class="about-detail">
<%
	String resqueContent = dataDTO.getIdleRescueContent();
	String currentContent = dataDTO.getIdleCurrentContent();
	String resultContent = "";
	if(!StringUtils.isBlank(resqueContent)) {
		resultContent += resqueContent;
		resultContent += "<br/><br/>";
	}
	if(!StringUtils.isBlank(currentContent)) {
		resultContent += currentContent;
	}
	
	if(StringUtils.isBlank(currentContent)) {
		resultContent = "-";
	}
%>
					<p><%=resultContent %></p>
                	<!-- <p> 3마리 같이 입소, 3개월추정실외배변 하고, 산책 시 줄당김 없어요.<br>
						궁금한 것도 많고 소심하지만 산책 좋아해요.좀 만 지나면 보호자 껌딱지될 가능성 높아요. 큰 개들 사이에도<br>
						밀리지 않는 당찬 아이고, 혹시 불리하면 켄넬로 쏙 들어갔다 다시 나오고 아주 영리해요. -->
                	</div>
                	</div>
                </section>
                
                <!-- <section class = "inner2-section">
                	<div class="text-section2">
                		<div>
                			<span class="title-about-icon"></span>
                			<h1 class= "ado-section-title">입양
                				<span>안내</span>
                			</h1>
                		</div> -->
                	<!-- <div class="adoption-detail">
                		<div class="inner-rectangle">
                			<div class="adoption-image"></div>
                			</div>
                	<p>우리 아이들은 대부분 중대형견들입니다. 품종견이 아니라고 해서 지능이 떨어지거나 반려견으로서 부족함이<br>
					있는 것은 아닙니다. 너무나 사랑스럽고 사랑을 갈구하는 상처받은 아이들입니다.<br>
					사지말고 입양해 주세요. 입양에 관심 있으시다면, 아이들 정보를 보시고 맘에 들어오는 아이들 후보를<br>
					생각하신 후 <span>카카오 채널(QR코드 스캔)</span>로 연락 주시면 상담해 드립니다.</p>
                	</div>
                </div> -->
                <!-- </section> -->
                
                <!-- 목록버튼 -->
                <div class="view-item-button">
					<button type="button" id="backBtn">목록</button>
				</div>
				
				
				<!-- 댓글 -->
				<section class="inner-section">
					<div class="section-reply-title">
						<h1>댓글 <span><%=ObjectUtils.isEmpty(idleReplyDTOList)?"0":idleReplyDTOList.size() %></span>개</h1>
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
	if(!ObjectUtils.isEmpty(idleReplyDTOList)) {
		for(IdleReplyDTO dto : idleReplyDTOList) {
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
            <script>
            const bigPic = document.querySelector("#big");
            const smallPics = document.querySelectorAll(".small");

            smallPics.forEach(function(pic) {
                pic.addEventListener("mouseover", function() {
                    const smallPicSrc = this.getAttribute("src");
                    bigPic.style.opacity = "0"; // 큰 이미지의 투명도를 0으로 설정
                    setTimeout(() => {
                        bigPic.setAttribute("src", smallPicSrc);
                        bigPic.style.opacity = "1"; // 큰 이미지의 투명도를 1로 설정
                    }, 300); // 0.3초 후에 소스를 변경
                });
            });

            </script>
        </main>
	
	<jsp:include page="../common/_footer.jsp"></jsp:include>
</body>
</html>