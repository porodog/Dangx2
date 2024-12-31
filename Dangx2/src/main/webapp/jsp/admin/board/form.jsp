<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	int boardTypeCd = (int) request.getAttribute("boardTypeCd"); //게시판타입코드
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
<title>커뮤니티 등록</title>

	<!-- css -->
	<c:import url="../common/_css.jsp" charEncoding="UTF-8" />
    
    <!-- js -->
    <c:import url="../common/_javascript.jsp" charEncoding="UTF-8" />
    <script type="text/javascript">
    	const boardTypeCd = "<%=boardTypeCd %>";
    </script>
    <script type="text/javascript" src="../../../resources/js/admin/board/form.js"></script>
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
						<a href="#" class="btn btn-success btn-icon-split mr-2" onclick="doInsert();">
                           <span class="icon text-white-50">
                               <i class="fas fa-check"></i>
                           </span>
                           <span class="text">등록</span>
                        </a>
						<a href="#" class="btn btn-danger btn-icon-split mr-2" onclick="doListPage('<%=boardTypeCd%>');">
                            <span class="icon text-white-50">
                                <i class="fas fa-arrow-left"></i>
                            </span>
                            <span class="text">취소</span>
                        </a>
					</div>
					
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                    	<form id="insertForm" action="/admin/board/<%=boardTypeCd %>/insert.do" method="post" enctype="multipart/form-data">
	                        <div class="card-body">
		                        <div class="mb-3">
								  <label for="idleName" class="form-label">제목</label>
								  <input type="text" class="form-control" name="boardTitle" id="boardTitle">
								<div class="mb-3">
								  <label for="idleContent" class="form-label">내용</label>
								  <textarea class="form-control" name="boardContent" id="boardContent" rows="10"></textarea>
								</div>
								<div class="mb-3">
								  <label for="fileUpload" class="form-label">파일 업로드</label>
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