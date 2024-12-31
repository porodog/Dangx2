package board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.LoginDTO;

@WebServlet({
	/* get */

	/*=================*/
	
	/* post */
	"/board/reply/insert.do"
	,"/board/reply/modify.do"
	,"/board/reply/delete.do"
	})
public class BoardReplyAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardReplyAction() {
        super();
    }


//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		String boardType = (String) request.getParameter("boardType");
		String boardIdx = (String) request.getParameter("boardIdx");
		
		if(RequestURI.endsWith("/insert.do")) {
			insertReplyData(request);
		}
		else if(RequestURI.endsWith("/modify.do")) {
			updateReplyData(request);
		}
		else if(RequestURI.endsWith("/delete.do")) {
			deleteReplyData(request);
		}
		response.sendRedirect("/readForm.do?idx="+boardIdx+"&boardType="+boardType);
		return;
	}
	
	private int insertReplyData(HttpServletRequest request) {
		int result = 0;
		
		int boardIdx = Integer.parseInt(request.getParameter("boardIdx"));
		String content = request.getParameter("content");
		
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		int userIdx = sessionDTO.getIdx();
		
		BoardReplyDTO replyDTO = new BoardReplyDTO();
		replyDTO.setBoardIdx(boardIdx);
		replyDTO.setContent(content);
		replyDTO.setRegIdx(userIdx);
		replyDTO.setModIdx(userIdx);
		
		result = BoardDAO.getInstance().insertBoardReply(replyDTO);
		
		return result;
	}
	
	private int updateReplyData(HttpServletRequest request) {
		int result = 0;
		
		int idx = Integer.parseInt(request.getParameter("replyIdx"));
		String content = request.getParameter("content");
		
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		int userIdx = sessionDTO.getIdx();
		
		BoardReplyDTO replyDTO = new BoardReplyDTO();
		replyDTO.setIdx(idx);
		replyDTO.setContent(content);
		replyDTO.setRegIdx(userIdx);
		replyDTO.setModIdx(userIdx);
		
		result = BoardDAO.getInstance().updateBoardReply(replyDTO);
		
		return result;
	}

	private int deleteReplyData(HttpServletRequest request) {
		int result = 0;
		
		int idx = Integer.parseInt(request.getParameter("replyIdx"));
		
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		int userIdx = sessionDTO.getIdx();
		
		BoardReplyDTO replyDTO = new BoardReplyDTO();
		replyDTO.setIdx(idx);
		replyDTO.setRegIdx(userIdx);
		replyDTO.setModIdx(userIdx);
		
		result = BoardDAO.getInstance().deleteBoardReply(replyDTO);
		
		return result;
	}
	
	
}
