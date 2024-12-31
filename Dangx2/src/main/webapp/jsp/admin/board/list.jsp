<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="board.BoardDTO" %> 
<%@ page import="java.util.List" %> 
<%@ page import="java.util.ArrayList" %> 
<%@ page import="java.sql.Timestamp" %>

<%
	int boardTypeCd = (int) request.getAttribute("boardTypeCd"); //게시판타입코드
	List<BoardDTO> boardDTOList = (List<BoardDTO>) request.getAttribute("boardDTOList");
	
	String boardTypeName = "쉼터";
	if(boardTypeCd==2) {
		boardTypeName = "입양";
	}
	else if(boardTypeCd==3) {
		boardTypeName = "후원";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커뮤니티 목록</title>

    <!-- css -->
    <c:import url="../common/_css.jsp" charEncoding="UTF-8" />
    
    <!-- js -->
    <c:import url="../common/_javascript.jsp" charEncoding="UTF-8" />
    <script type="text/javascript">
        const boardTypeCd = "<%=boardTypeCd %>";
    </script>
    <script type="text/javascript" src="../../../resources/js/admin/board/list.js"></script>
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
                    <h1 class="h3 mb-2 text-gray-800"><%=boardTypeName %>관리</h1>
                    
					<div class="mb-2" style="display: flex; justify-content: flex-end;">
						<%-- <a href="#" class="btn btn-success btn-icon-split mr-2" onclick="doInsertPage('<%=boardTypeCd%>');">
                           <span class="icon text-white-50">
                               <i class="fas fa-check"></i>
                           </span>
                           <span class="text">등록</span>
                        </a> --%>
						<a href="#" class="btn btn-danger btn-icon-split mr-2" onclick="doDelete();">
                            <span class="icon text-white-50">
                                <i class="fas fa-trash"></i>
                            </span>
                            <span class="text">삭제</span>
                        </a>
					</div>
					
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                	<colgroup>
                                		<col width="5%;">
										<col width="8%;">
										<col width="20%;">
										<col width="20%;">
										<col width="20%;">
										<col width="20%;">
									</colgroup>
                                    <thead>
                                        <tr>
                                        	<th>#</th>
                                            <th>idx</th>
                                            <th>제목</th>
                                            <th>내용</th>
                                            <!-- <th>작성자</th> -->
                                            <th>등록일</th>
                                            <th>사용여부</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <!-- 게시판 목록 호출영역 -->
                                   	<c:if test="${not empty boardDTOList and fn:length(boardDTOList) gt 0}">
                                    	<c:forEach items="${boardDTOList}" var="dto">
									<tr>
										<td><input type="checkbox" name="boardIdxs" value="${dto.idx}"></td>
										<td><c:out value="${dto.idx}"></c:out></td>
										<td><c:out value="${dto.title}"></c:out></td>
										<td><c:out value="${dto.content}"></c:out></td>
										<%-- <td><c:out value="${dto.user_id}"></c:out></td> --%>
										<td><c:out value="${dto.reg_date}"></c:out></td>
										<td><c:out value="${dto.use_yn eq 'Y'?'사용':'미사용'}"></c:out></td>
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