package main;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import board.BoardDTO;
import bongsa.BongsaDAO;
import bongsa.BongsaDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.LoginDTO;


@WebServlet({
	/* get */
	"/main/mainPage.do"
	
	/*=================*/
	/* post */
	
	})
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MainServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/mainPage.do")) {
			//메인화면 페이지
			selectBoardList(request);
			RequestDispatcher rd = request.getRequestDispatcher("../jsp/main/mainPage.jsp");
			rd.forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	private void selectBoardList(HttpServletRequest request) {
		List<BoardDTO> boardList = MainDAO.getInstance().selectMainBoardList(1);
		request.setAttribute("boardList1", boardList);
		boardList = MainDAO.getInstance().selectMainBoardList(2);
		request.setAttribute("boardList2", boardList);
	}
	
}
