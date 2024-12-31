<%@page import="bongsa.BongsaInfoDTO"%>
<%@page import="java.util.List"%>
<%@page import="bongsa.BongsaDTO"%>
<%@page import="login.LoginDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	LoginDTO sessionDTO = (LoginDTO) session.getAttribute("userInfo");
	String idx = request.getParameter("idx");
	String date = request.getParameter("date");
	String error = request.getParameter("error");
	
	String setAction = "/bongsa/insert.do";
	String setUserId = sessionDTO.getUserId();
	String setName = sessionDTO.getUserName();
	//String setPhone = sessionDTO.getUserPhone();
	String setDate = date;
	String setBtnValue = "등록";
	
	int index = 0;
	String[] setBongsaNameArr = new String[3];
	String[] setBongsaPhoneArr = new String[3];
	
	if(idx!=null) {
		BongsaDTO bongsaDTO = (BongsaDTO) request.getAttribute("bongsaDTO");
		setAction = "/bongsa/modify.do";
		setName = sessionDTO.getUserName();
		//setPhone = sessionDTO.getUserPhone();
		setDate = bongsaDTO.getBongsaDate();
		setBtnValue = "수정";
		
		List<BongsaInfoDTO> bongsaInfoDTOList = (List<BongsaInfoDTO>) request.getAttribute("bongsaInfoDTOList");
		
		if(bongsaInfoDTOList!=null && bongsaInfoDTOList.size()>0) {
			for(BongsaInfoDTO dto : bongsaInfoDTOList) {
				setBongsaNameArr[index] = dto.getBongsaName();
				setBongsaPhoneArr[index] = dto.getBongsaPhone();
				index++;
			}
		}
	}
	if(index<3) {
		for(int i=index; i<3; i++) {
			setBongsaNameArr[i] = "";
			setBongsaPhoneArr[i] = "";
		}
	}
	
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

<!-- 봉사 등록/수정 페이지 -->
<link rel="stylesheet" href="../../resources/css/bongsa/form.css"/>

<%
	if(error!=null) {
%>
	<script type="text/javascript">
		alert("에러가 발생했습니다");
	</script>
<%
	}
%>
<script defer type="text/javascript" src="../../resources/js/bongsa/form.js"></script>
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
					<%
						if(idx!=null) {
					 %>
					 	<h1>봉사신청 수정</h1>
					 <%	
						}
						else {
					%>
						<h1>봉사신청 등록</h1>
					<%
						}
					%>
					</div>
				</div>
				
				<div class="section-board-list">
					<div class="section-bongsa-form-base">
						<h1>신청자 정보</h1>
						<div>
							<p>* 성명 : <%=setName %> (<%=setUserId %>)</p>
							<p>* 신청일 : <%=setDate %></p>
						</div>
					</div>
					
					<div class="section-bongsa-form-detail">
						<h1>추가인원 정보</h1>
						<div>
							<form id="bongsaForm" action="<%=setAction %>" method="post">
							<input type="hidden" name="idx" value="<%=idx %>">
							<input type="hidden" name="bongsaDate" value="<%=setDate %>">
							<table class="form-detail-table">
								<thead>
									<tr>
										<th width="10%"></th>
										<th width="40%">성명</th>
										<th width="50%">연락처</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><span>1</span></td>
										<td><input type="text" name="bongsaName" value="<%=setBongsaNameArr[0] %>"></td>
										<td><input type="text" name="bongsaPhone" value="<%=setBongsaPhoneArr[0] %>"></td>
									</tr>
									<tr>
										<td><span>2</span></td>
										<td><input type="text" name="bongsaName" value="<%=setBongsaNameArr[1] %>"></td>
										<td><input type="text" name="bongsaPhone" value="<%=setBongsaPhoneArr[1] %>"></td>
									</tr>
									<tr>
										<td><span>3</span></td>
										<td><input type="text" name="bongsaName" value="<%=setBongsaNameArr[2] %>"></td>
										<td><input type="text" name="bongsaPhone" value="<%=setBongsaPhoneArr[2] %>"></td>
									</tr>
								</tbody>
							</table>
							</form>
						</div>
					</div>
					
					<div class="section-bongsa-form-button">
						<button type="button" class="submit-button" id="submitBtn"><%=setBtnValue %></button>
					<%
						if(idx!=null) {
					%>
						<button type="button" class="delete-button" id="deleteBtn">삭제</button>
					<%	
						}
					%>
						<button type="button" class="back-button" id="backBtn">목록</button>
					</div>
				</div>
			</section>
		</div>
	</main>
	
	<jsp:include page="../common/_footer.jsp"></jsp:include>
</body>
</html>