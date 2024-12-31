package login;

import java.io.IOException;

import common.SHA256;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet({
	/* get */
	"/login/loginPage.do"
	
	/*=================*/
	
	/* post */
	,"/login/loginProcess.do"
	})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/loginPage.do")) {
			// 로그인 페이지 이동
			// 세션체크해서 존재하면? -> 메인페이지 이동처리 필요 (나중에..)
			
			RequestDispatcher rd = request.getRequestDispatcher("../jsp/login/loginPage.jsp");
			rd.forward(request, response);
		}
		
		// 세션에서 에러 메시지 제거
	    request.getSession().removeAttribute("errorMessage");
	    RequestDispatcher rd = request.getRequestDispatcher("/jsp/login/loginPage.jsp");
	    rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("text/html; charset=UTF-8");
		    request.setCharacterEncoding("UTF-8");
		    String RequestURI = request.getRequestURI();

		    if (RequestURI.endsWith("/loginProcess.do")) {
		        // 로그인 처리
		        String userId = request.getParameter("userId");
		        String userPwd = request.getParameter("userPwd");

		        // 실제 로그인 처리 로직
		        LoginDTO dto = doLoginProcess(userId, userPwd);
		        if (dto != null) {
		            // 로그인 성공 -> 사용자정보 세션 추가
		            request.getSession().setAttribute("userInfo", dto);
		            request.getSession().setMaxInactiveInterval(1800);
		            //response.sendRedirect("/main/mainPage.do");
		            
		            String prevPage = (String) request.getSession().getAttribute("prevPage");
					request.getSession().removeAttribute("prevPage");
					response.sendRedirect(prevPage);
		        } else {
		            // 로그인 실패 -> 로그인페이지 재이동
		            request.getSession().setAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
		            response.sendRedirect("/jsp/login/loginPage.jsp");
		        }
		    }
		}
	
	
	private LoginDTO doLoginProcess(String userId, String userPwd) {
		LoginDTO dto = null;
		
		try {
			SHA256 sha256 = new SHA256();
			
			System.out.println(">>>>> sha256.encrypt("+userPwd+") >>>>>>");
			userPwd = sha256.encrypt(userPwd);
			System.out.println(userPwd);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			dto = LoginDAO.getInstance().doLoginProcess(userId, userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

}