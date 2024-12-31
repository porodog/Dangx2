<%@page import="org.apache.tomcat.jakartaee.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>

	<!-- css -->
	<c:import url="../common/_css.jsp" charEncoding="UTF-8" />
    
    <!-- js -->
    <c:import url="../common/_javascript.jsp" charEncoding="UTF-8" />
    
    <script type="text/javascript" src="../../../resources/js/admin/login/loginPage.js"></script>
</head>
<body class="bg-gradient-primary">

    <div class="container">
        <!-- Outer Row -->
        <div class="row justify-content-center">

            <div class="col-xl-10 col-lg-12 col-md-9">

                <div class="card o-hidden border-0 shadow-lg my-5"
                	style="margin-top: 30% !important;"
                >
                    <div class="card-body p-0">
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block bg-login-image">
                            	<div class=""
									style="
									background: url(../../../resources/image/logo_dangx2.png) no-repeat center;
								    background-size: 100%;
								    display: block;
								    width: 100%;
								    height: 100%;
								    margin-left: 10px;
									"
								>
								</div>
                            </div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
<%
	String error = request.getParameter("error");
   	if(!StringUtils.isBlank(error) && error.equals("1")) {
%>
                                        <h5><code>Login Failed!!</code></h5>
<%   		
   	}
%>
                                    </div>
                                    
                                    <form class="user" id="loginForm" action="/admin/login.do" method="post">
                                        <div class="form-group">
                                            <input type="email" class="form-control form-control-user"
                                                id="adminId" aria-describedby="emailHelp" name="adminId"
                                                placeholder="ID">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control form-control-user"
                                                id="adminPwd" placeholder="Password" name="adminPwd">
                                        </div>
                                        <!-- <div class="form-group">
                                            <div class="custom-control custom-checkbox small">
                                                <input type="checkbox" class="custom-control-input" id="customCheck">
                                                <label class="custom-control-label" for="customCheck">Remember
                                                    Me</label>
                                            </div>
                                        </div> -->
                                        <a href="#" class="btn btn-primary btn-user btn-block" onclick="doLogin();">
                                            Login
                                        </a>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>