package admin.bongsa;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import bongsa.BongsaDTO;
import bongsa.BongsaInfoDTO;

@WebServlet({
	/* get */
	"/admin/bongsa/list.do"
	,"/admin/bongsa/view.do"
	/*=================*/
	
	/* post */
	
	})
public class AdminBongsaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AdminBongsaServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/list.do")) {
			List<BongsaDTO> bongsaDTOList = AdminBongsaDAO.getInstance().selectBongsaList();
			request.setAttribute("bongsaDTOList", bongsaDTOList);
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin/bongsa/list.jsp");
			rd.forward(request, response);
		}
		else if(RequestURI.endsWith("/view.do")) {
			selectBongsaData(request);
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin/bongsa/view.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void selectBongsaData(HttpServletRequest request) {
		int bongsaIdx = Integer.parseInt(request.getParameter("idx"));
		BongsaDTO bongsaDTO = AdminBongsaDAO.getInstance().selectBongsaData(bongsaIdx);
		List<BongsaInfoDTO> bongsaInfoDTOList = AdminBongsaDAO.getInstance().selectBongsaInfoData(bongsaIdx);
		
		request.setAttribute("bongsaDTO", bongsaDTO);
		request.setAttribute("bongsaInfoDTOList", bongsaInfoDTOList);
	}
}
