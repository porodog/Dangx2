package admin.member;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import membership.MemberDTO;

@WebServlet({
	/* get */
	"/admin/member/list.do"
	
	/*=================*/
	
	/* post */
	
	})
public class AdminMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AdminMemberServlet() {
        super();
    }
    
    private AdminDAO adminDAO;

    public void init() {
        adminDAO = AdminDAO.getInstance(); // 싱글톤 패턴으로 AdminDAO 인스턴스 생성
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/list.do")) {
			
			List<MemberDTO> members = adminDAO.getAllMembers(); // AdminDTO로 변경

	        if (members == null || members.isEmpty()) {
	            request.setAttribute("message", "회원 정보가 없습니다.");
	        } else {
	            request.setAttribute("members", members); // AdminDTO 리스트를 request에 저장
	        }
			
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin/member/list.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String[] memberIds = request.getParameterValues("memberIds"); // 삭제할 회원의 ID 배열
	        if (memberIds != null) {
	            List<String> idsToDelete = new ArrayList<>();
	            for (String idStr : memberIds) {
	                idsToDelete.add(idStr); // String 형식의 userId를 List에 추가
	            }
	            adminDAO.deleteMembers(idsToDelete); // 선택된 회원 삭제
	            response.sendRedirect("/jsp/admin/member/memberdelete.jsp"); // 삭제 완료 후 결과 페이지로 이동
	        } else {
	            response.sendRedirect("/jsp/admin/member/memberdelete.jsp?status=empty"); // 선택된 회원이 없을 때 처리
	        }
	    }
	}

