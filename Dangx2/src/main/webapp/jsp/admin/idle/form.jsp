<%@page import="org.apache.tomcat.jakartaee.commons.lang3.StringUtils"%>
<%@page import="idle.IdleDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	int idleTypeCd = (int) request.getAttribute("idleTypeCd");
	
	String menuTypeName = "쉼터";
	if(idleTypeCd==3) {
		menuTypeName = "입양";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이들 등록</title>

	<!-- css -->
	<c:import url="../common/_css.jsp" charEncoding="UTF-8" />
    
    <!-- js -->
    <c:import url="../common/_javascript.jsp" charEncoding="UTF-8" />
    
<%
	String error = (String) request.getParameter("error");
	if(!StringUtils.isBlank(error)) {
%>
	<script type="text/javascript">
		alert('등록을 실패하였습니다');
	</script>
<%		
	}
%>
    <script type="text/javascript" src="../../../resources/js/admin/idle/form.js"></script>
</head>
<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">
		<!-- sidebar -->
        <c:import url="../common/_sidebar.jsp" charEncoding="UTF-8" />

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">
				<!-- topbar -->
                <c:import url="../common/_topbar.jsp" charEncoding="UTF-8" />

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800"><%=menuTypeName %>관리</h1>
                    
					<div class="mb-2" style="display: flex; justify-content: flex-end;">
						<a href="#" class="btn btn-success btn-icon-split mr-2" onclick="doInsert();">
                           <span class="icon text-white-50">
                               <i class="fas fa-check"></i>
                           </span>
                           <span class="text">등록</span>
                        </a>
						<a href="#" class="btn btn-danger btn-icon-split mr-2" onclick="doListPage('<%=idleTypeCd%>');">
                            <span class="icon text-white-50">
                                <i class="fas fa-arrow-left"></i>
                            </span>
                            <span class="text">취소</span>
                        </a>
					</div>
					
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                    	<form id="insertForm" action="/admin/idle/<%=idleTypeCd %>/insert.do" method="post" enctype="multipart/form-data">
	                        <div class="card-body">
		                        <div class="mb-3">
								  <label for="idleName" class="form-label">이름</label>
								  <input type="text" class="form-control" name="idleName" id="idleName" placeholder="이름을 입력해주세요 (예: 다래)" maxlength="20">
								</div>
								<div class="mb-3">
								  <label for="idleBreed" class="form-label">품종</label>
								  <input type="text" class="form-control" name="idleBreed" id="idleBreed" placeholder="품종을 입력해주세요 (예: 믹스견)" maxlength="20">
								</div>
								<div class="mb-3">
								  <label for="idleYear" class="form-label">생년</label>
								  <input type="text" class="form-control" name="idleYear" id="idleYear" placeholder="생년을 숫자만 입력해주세요 (예: 2024)" maxlength="5">
								</div>
								<div class="mb-3">
								  <label for="idleKg" class="form-label">몸무게</label>
								  <input type="text" class="form-control" name="idleKg" id="idleKg" placeholder="몸무게를 숫자만 입력해주세요 (예: 10.5)" maxlength="10">
								</div>
								<div class="mb-3">
								  <label for="gender1" class="form-label">성별</label>
								  <br/>
								  <div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="idleGenderCd" id="gender1" value="1" checked="checked">
									  <label class="form-check-label" for="gender1">수컷</label>
								  </div>
								  <div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="idleGenderCd" id="gender2" value="2">
									  <label class="form-check-label" for="gender2">암컷</label>
								  </div>
								</div>
								<div class="mb-3">
								  <label for="neuter1" class="form-label">중성화여부</label>
								  <br/>
								  <div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="idleNeuterYn" id="neuter1" value="Y" checked="checked">
									  <label class="form-check-label" for="neuter1">O</label>
								  </div>
								  <div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="idleNeuterYn" id="neuter2" value="N">
									  <label class="form-check-label" for="neuter2">X(미확인 포함)</label>
								  </div>
								</div>
								<div class="mb-3">
								  <label for="idleContent" class="form-label">내용</label>
								  <textarea class="form-control" name="content" id="idleContent" rows="3" placeholder="내용을 입력해주세요" maxlength="300"></textarea>
								</div>
								<div class="mb-3">
								  <label for="fileUpload" class="form-label">이미지 업로드</label>
								  <input class="form-control" type="file" name="fileUpload" id="fileUpload" multiple
								  	style="border: none;">
								</div>
	                        </div>
                        </form>
                    </div>
                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

			<!-- footer -->
            <c:import url="../common/_footer.jsp" charEncoding="UTF-8" />

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    
</body>
</html>