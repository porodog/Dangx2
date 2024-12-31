<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String menuURI = (String) request.getAttribute("menuURI");
%>

<!-- Sidebar -->
<ul
	class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
	id="accordionSidebar">

	<!-- Sidebar - Brand -->
	<a class="sidebar-brand d-flex align-items-center justify-content-center" href="/admin/member/list.do">
		<!-- <div class="sidebar-brand-icon rotate-n-15">
			<i class="fas fa-laugh-wink"></i>
		</div> -->
		<div class="sidebar-brand-text mx-3"
			style="
			background: url(../../../resources/image/logo_dangx2.png) no-repeat center; 
			background-size: 100%;
		    display: block;
		    width: 135px;
		    height: 60px;
			"
		>
			<!-- SB Admin <sup>2</sup> -->
			
		</div>
	</a>

	<!-- Divider -->
	<hr class="sidebar-divider">

	<!-- Heading -->
	<div class="sidebar-heading">회원</div>
	<li class="nav-item <c:if test="${fn:contains(menuURI, '/member/') }">active</c:if>">
		<a class="nav-link" href="/admin/member/list.do"><i class="fas fa-fw fa-user"></i>
			<span>회원관리</span>
		</a>
	</li>

	<!-- Divider -->
	<hr class="sidebar-divider">

	<!-- Heading -->
	<div class="sidebar-heading">봉사</div>
	<li class="nav-item <c:if test="${fn:contains(menuURI, '/bongsa/') }">active</c:if>">
		<a class="nav-link" href="/admin/bongsa/list.do"><i class="fas fa-fw fa-table"></i>
			<span>봉사관리</span>
		</a>
	</li>

	<!-- Divider -->
	<hr class="sidebar-divider">

	<!-- Heading -->
	<div class="sidebar-heading">컨텐츠</div>
	<li class="nav-item <c:if test="${fn:contains(menuURI, '/idle/') }">active</c:if>">
		<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages" 
			aria-expanded="true" aria-controls="collapsePages"><i class="fas fa-fw fa-dog"></i>
			<span>아이들</span>
		</a>
		<div id="collapsePages" class="collapse <c:if test="${fn:contains(menuURI, '/idle/') }">show</c:if>" 
			aria-labelledby="headingPages" data-parent="#accordionSidebar">
			<div class="bg-white py-2 collapse-inner rounded">
				<h6 class="collapse-header">쉼터:</h6>
				<a class="collapse-item <c:if test="${fn:contains(menuURI, '/idle/1/list') or fn:contains(menuURI, '/idle/1/form')}">active</c:if>" 
					href="/admin/idle/1/list.do">쉼터관리</a> 
				<a class="collapse-item <c:if test="${fn:contains(menuURI, '/idle/1/replyList') }">active</c:if>" 
					href="/admin/idle/1/replyList.do">댓글관리</a>
				<div class="collapse-divider"></div>
				<h6 class="collapse-header">입양:</h6>
				<a class="collapse-item <c:if test="${fn:contains(menuURI, '/idle/3/list') or fn:contains(menuURI, '/idle/3/form')}">active</c:if>" 
					href="/admin/idle/3/list.do">입양관리</a> 
				<a class="collapse-item <c:if test="${fn:contains(menuURI, '/idle/3/replyList') }">active</c:if>" 
					href="/admin/idle/3/replyList.do">댓글관리</a>
			</div>
		</div>
	</li>

	<li class="nav-item <c:if test="${fn:contains(menuURI, '/board/') }">active</c:if>">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages2"
            aria-expanded="true" aria-controls="collapsePages2"><i class="fas fa-fw fa-folder"></i>
            <span>커뮤니티</span>
        </a>
        <div id="collapsePages2" class="collapse <c:if test="${fn:contains(menuURI, '/board/') }">show</c:if>" 
        	aria-labelledby="headingPages" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <h6 class="collapse-header">쉼터:</h6>
                <a class="collapse-item <c:if test="${fn:contains(menuURI, '/board/1/list') }">active</c:if>" 
                	href="/admin/board/1/list.do">쉼터관리</a>
                <a class="collapse-item  <c:if test="${fn:contains(menuURI, '/board/1/replyList') }">active</c:if>" 
                	href="/admin/board/1/replyList.do">댓글관리</a>
                <div class="collapse-divider"></div>
                <h6 class="collapse-header">입양:</h6>
                <a class="collapse-item  <c:if test="${fn:contains(menuURI, '/board/2/list') }">active</c:if>" 
                	href="/admin/board/2/list.do">입양관리</a>
                <a class="collapse-item <c:if test="${fn:contains(menuURI, '/board/2/replyList') }">active</c:if>" 
                	href="/admin/board/2/replyList.do">댓글관리</a>
                <div class="collapse-divider"></div>
                <h6 class="collapse-header">후원:</h6>
                <a class="collapse-item  <c:if test="${fn:contains(menuURI, '/board/3/list') }">active</c:if>" 
                	href="/admin/board/3/list.do">후원관리</a>
                <a class="collapse-item <c:if test="${fn:contains(menuURI, '/board/3/replyList') }">active</c:if>" 
                	href="/admin/board/3/replyList.do">댓글관리</a>
            </div>
        </div>
    </li>

	<!-- Divider -->
	<hr class="sidebar-divider d-none d-md-block">
</ul>
<!-- End of Sidebar -->