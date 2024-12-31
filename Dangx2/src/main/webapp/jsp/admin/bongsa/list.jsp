<%@page import="bongsa.BongsaDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	List<BongsaDTO> bongsaDTOList = (List<BongsaDTO>) request.getAttribute("bongsaDTOList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>봉사 목록</title>

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

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                	<colgroup>
                                		<col width="5%;">
										<col width="20%;">
										<col width="10%;">
										<col width="10%;">
										<col width="10%;">
										<col width="20%;">
									</colgroup>
                                    <thead>
                                        <tr>
                                            <th>idx</th>
                                            <th>봉사일</th>
                                            <th>아이디</th>
                                            <th>추가인원수</th>
                                            <th>사용여부</th>
                                            <th>등록일</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${not empty bongsaDTOList and fn:length(bongsaDTOList) gt 0}">
	                                    	<c:forEach items="${bongsaDTOList}" var="i">
	                                   		<tr>
	                                            <td>
	                                            	<a href="/admin/bongsa/view.do?idx=${i.idx}"
	                                            		style="font-size: 18px;"
	                                            	>
	                                            		<c:out value="${i.idx}"></c:out>
	                                            	</a>
                                            	</td>
	                                            <td><c:out value="${i.bongsaDate}"></c:out></td>
	                                            <td><c:out value="${i.userId}"></c:out></td>
	                                            <td><c:out value="${i.bongsaCount}"></c:out></td>
	                                            <td><c:out value="${i.useYn eq 'Y'?'사용':'미사용'}"></c:out></td>
	                                            <td><c:out value="${i.regDate}"></c:out></td>
	                                        </tr>
	                                    	</c:forEach>
	                                   	</c:if>
                                        
                                    </tbody>
                                </table>
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