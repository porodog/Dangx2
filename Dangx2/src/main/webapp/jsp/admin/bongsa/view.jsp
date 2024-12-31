<%@page import="bongsa.BongsaInfoDTO"%>
<%@page import="java.util.List"%>
<%@page import="bongsa.BongsaDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
BongsaDTO bongsaDTO = (BongsaDTO) request.getAttribute("bongsaDTO");
List<BongsaInfoDTO> bongsaInfoDTOList = (List<BongsaInfoDTO>) request.getAttribute("bongsaInfoDTOList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>봉사 상세</title>

	<!-- css -->
	<c:import url="../common/_css.jsp" charEncoding="UTF-8" />
    
    <!-- js -->
    <c:import url="../common/_javascript.jsp" charEncoding="UTF-8" />
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
                    <h1 class="h3 mb-2 text-gray-800">봉사관리</h1>
                    
					<div class="mb-2" style="display: flex; justify-content: flex-end;">
						<a href="/admin/bongsa/list.do" class="btn btn-success btn-icon-split mr-2">
                           <span class="icon text-white-50">
                               <i class="fas fa-arrow-left"></i>
                           </span>
                           <span class="text">목록</span>
                        </a>
						<!-- <a href="#" class="btn btn-danger btn-icon-split mr-2">
                            <span class="icon text-white-50">
                                <i class="fas fa-arrow-left"></i>
                            </span>
                            <span class="text">취소</span>
                        </a> -->
					</div>
					
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-body">
                        	<div class="card mb-4">
	                            <div class="card-header">
	                            	<strong>신청자 기본 정보</strong>
	                        	</div>
		                        <div class="card-body card-block">
	                                <div class="form-group">
	                                	<label class="form-control-label">아이디</label>
	                                	<p class="ml-4">
	                                		<c:out value="${bongsaDTO.userId}"></c:out>
	                                	</p>
	                                </div>
	                                <div class="form-group">
	                                	<label class="form-control-label">연락처</label>
	                                	<p class="ml-4">
	                                		<c:out value="${bongsaDTO.userPhone}"></c:out>
	                                	</p>
	                                </div>
	                                <div class="form-group">
	                                	<label class="form-control-label">봉사일</label>
	                                	<p class="ml-4">
	                                		<c:out value="${bongsaDTO.bongsaDate}"></c:out>
	                                	</p>
	                                </div>
	                                <div class="form-group">
	                                	<label class="form-control-label">사용여부</label>
	                                	<p class="ml-4">
	                                		<c:out value="${bongsaDTO.useYn eq 'Y'?'사용':'미사용'}"></c:out>
	                                	</p>
	                                </div>
	                                <div class="form-group">
	                                	<label class="form-control-label">등록일</label>
	                                	<p class="ml-4">
	                                		<c:out value="${bongsaDTO.regDate}"></c:out>
	                                	</p>
	                                </div>
		                        </div>
	                    	</div>
                        
                        
	                        <div class="card">
	                            <div class="card-header">
	                                <strong class="card-title">추가 구성원 정보</strong>
	                            </div>
	                            <div class="card-body">
	                                <table class="table">
	                                    <thead>
	                                        <tr>
	                                          <th scope="col">#</th>
	                                          <th scope="col">이름</th>
	                                          <th scope="col">연락처</th>
	                                      </tr>
	                                  </thead>
	                                  <tbody>
	                                    <c:choose>
	                                    	<c:when test="${not empty bongsaInfoDTOList and fn:length(bongsaInfoDTOList) gt 0}">
	                                    		<c:forEach items="${bongsaInfoDTOList}" var="i" varStatus="vs">
	                                    		<tr>
			                                        <th scope="row"><c:out value="${vs.count}"></c:out></th>
			                                        <td><c:out value="${i.bongsaName}"></c:out></td>
			                                        <td><c:out value="${i.bongsaPhone}"></c:out></td>
			                                    </tr>
	                                    		</c:forEach>
	                                    	</c:when>
	                                    	<c:otherwise>
	                                    	<tr>
		                                    	<td colspan="3" style="text-align: center;">No Data</td>
		                                    </tr>
	                                    	</c:otherwise>
	                                    </c:choose>
	                                </tbody>
	                              </table>
	                        	</div>
	                    	</div>
                        </div>
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