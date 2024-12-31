<%@page import="admin.common.AdminDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	AdminDTO sessionDTO = (AdminDTO) request.getSession().getAttribute("adminInfo");
%>
<!-- Topbar -->
	<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
		<h4 class="mb-0 ml-4">Admin Manage System</h4>
	    <!-- Topbar Navbar -->
	    <ul class="navbar-nav ml-auto">
	        <div class="topbar-divider d-none d-sm-block"></div>
	
	        <!-- Nav Item - User Information -->
	        <li class="nav-item dropdown no-arrow">
	            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
	                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%=sessionDTO.getAdminName() %>님</span>
	                <img class="img-profile rounded-circle"
	                    src="../../../resources/image/link_cafe.png">
	            </a>
	            <!-- Dropdown - User Information -->
	            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
	                aria-labelledby="userDropdown">
	                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal" onclick="doLogOut();">
	                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
	                    Logout
	                </a>
	            </div>
	        </li>
	    </ul>
	</nav>
	
	<script type="text/javascript">
		function doLogOut() {
			location.href = '/admin/logout.do';
		}
	</script>
<!-- End of Topbar -->