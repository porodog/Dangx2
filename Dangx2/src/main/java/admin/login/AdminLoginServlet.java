package admin.login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.LoginDAO;
import login.LoginDTO;

import java.io.IOException;

import admin.common.AdminDTO;
import common.SHA256;

@WebServlet({
	/* get */
	"/admin/login.do"
	,"/admin/logout.do"
	/*=================*/
	
	/* post */
	//,"/admin/login.do"
	})
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AdminLoginServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/login.do")) {
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin/login/loginPage.jsp");
			rd.forward(request, response);
		}
		else if(RequestURI.endsWith("/logout.do")) {
			request.getSession().invalidate();
			response.sendRedirect("/admin/login.do");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/login.do")) {
			 // 로그인 처리
	        String adminId = request.getParameter("adminId");
	        String adminPwd = request.getParameter("adminPwd");
	        
	        // 실제 로그인 처리 로직
	        AdminDTO dto = doLoginProcess(adminId, adminPwd);
	        if (dto != null) {
	            // 로그인 성공 -> 사용자정보 세션 추가
	            request.getSession().setAttribute("adminInfo", dto);
	            request.getSession().setMaxInactiveInterval(1800);
	            response.sendRedirect("/admin/member/list.do");
	        } else {
	            // 로그인 실패 -> 로그인페이지 재이동
	            response.sendRedirect("/admin/login.do?error=1");
	        }
		}
		
	}
	
	private AdminDTO doLoginProcess(String userId, String userPwd) {
		AdminDTO dto = null;
		
		try {
			SHA256 sha256 = new SHA256();
			
			System.out.println(">>>>> sha256.encrypt("+userPwd+") >>>>>>");
			userPwd = sha256.encrypt(userPwd);
			System.out.println(userPwd);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			dto = AdminLoginDAO.getInstance().doLoginProcess(userId, userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

}
