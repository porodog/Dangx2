<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="membership.MemberDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script defer src="../../resources/js/admin/member/memberdelete.js"></script>
<title>회원 목록</title>

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
                    <h1 class="h3 mb-2 text-gray-800">회원관리</h1>
                    
                    <div class="mb-2" style="display: flex; justify-content: flex-end;">
						<!-- <a href="#" class="btn btn-success btn-icon-split mr-2">
                           <span class="icon text-white-50">
                               <i class="fas fa-check"></i>
                           </span>
                           <span class="text">등록</span>
                        </a> -->
						<a href="#" class="btn btn-danger btn-icon-split mr-2" onclick="deleteMembers(event)">
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
										<col width="5%">
										<col width="8%">
										<col width="8%">
										<col width="8%">
										<col width="12%">
										<col width="15%">
										<col width="5%">
										<col width="15%;">
									</colgroup>
                                    <thead>
                                        <tr>
                                            <th>선택</th>
							                <th>이름</th>
							                <th>아이디</th>
							                <th>전화번호</th>
							                <th>이메일</th>
							                <th>주소</th>
							                <th>성별</th>
							                <th>가입일</th>
                                        </tr>
                                    </thead>
                                
                                    <tbody>
                                     <%
						                List<MemberDTO> members = (List<MemberDTO>) request.getAttribute("members");
						                if (members != null) {
						                    for (MemberDTO member : members) {
						             %>
                                         <tr>
							                <td><input type="checkbox" name="memberIds" value="<%= member.getUserId() %>"></td>
							                <td><%= member.getUserName() %></td>
							                <td><%= member.getUserId() %></td>
							                <td><%= member.getUserPhone() %></td>
							                <td><%= member.getUserEmail() %></td>
							                <td><%= member.getUserPost() %> <%= member.getUserAddr() %> <%= member.getUserAddrDtl() %></td>
							                <td><%= member.getUserGenderCd() %></td>
							                <td><%= member.getRegDate() %></td>
							            </tr>
							            <%
							                    }
							                }
							            %>
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