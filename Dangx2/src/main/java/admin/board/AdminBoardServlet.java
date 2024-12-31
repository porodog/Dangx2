// AdminBoardServlet.java

package admin.board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import board.BoardDTO;
import board.BoardReplyDTO;

@WebServlet({
    /* get */
    "/admin/board/1/list.do",
    "/admin/board/1/form.do",
    "/admin/board/1/replyList.do",
    "/admin/board/2/list.do",
    "/admin/board/2/form.do",
    "/admin/board/2/replyList.do",
    "/admin/board/3/list.do",
    "/admin/board/3/form.do",
    "/admin/board/3/replyList.do",

    /* post */
		"/admin/board/1/delete.do",
		"/admin/board/1/replyDelete.do", 
		"/admin/board/2/delete.do",
		"/admin/board/2/replyDelete.do", 
		"/admin/board/3/delete.do",
		"/admin/board/3/replyDelete.do"
		})
public class AdminBoardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminBoardServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String RequestURI = request.getRequestURI();

        int boardTypeCd = getBoardTypeCd(request, RequestURI);

        if (RequestURI.endsWith("/list.do")) {
            List<BoardDTO> boardDTOList = AdminBoardDAO.getInstance().selectBoardList(boardTypeCd);
            request.setAttribute("boardDTOList", boardDTOList);
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin/board/list.jsp");
            rd.forward(request, response);

        } else if (RequestURI.endsWith("/form.do")) {
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin/board/form.jsp");
            rd.forward(request, response);

        } else if (RequestURI.endsWith("/replyList.do")) {
            // 댓글 목록 가져오기
            List<BoardReplyDTO> replyList = AdminBoardDAO.getInstance().selectBoardReplyList(boardTypeCd);
            request.setAttribute("boardReplyDTOList", replyList);  // JSP에서 사용할 attribute 설정
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin/board/replyList.jsp");
            rd.forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String RequestURI = request.getRequestURI();
        System.out.println("Request URI: " + RequestURI);  // 로그 출력

        int boardTypeCd = getBoardTypeCd(request, RequestURI);

     // 댓글 삭제 요청 처리
        if (RequestURI.endsWith("/replyDelete.do")) {
            int result = deleteBoardReplyData(request);
            if (result > 0) {
                //response.getWriter().write("success");
                response.sendRedirect("/admin/board/"+boardTypeCd+"/replyList.do");
                return;
            } else {
                response.getWriter().write("failure");
            }
        }
        else if(RequestURI.endsWith("/delete.do")) {
        	int result = deleteBoardData(request);
            if (result > 0) {
                //response.getWriter().write("success");
            	response.sendRedirect("/admin/board/"+boardTypeCd+"/list.do");
            } else {
                response.getWriter().write("failure");
            }
        }
    }

    private int getBoardTypeCd(HttpServletRequest request, String RequestURI) {
        int boardTypeCd = 1;
        if (RequestURI.contains("/2/")) {
            boardTypeCd = 2;
        } else if (RequestURI.contains("/3/")) {
            boardTypeCd = 3;
        }
        request.setAttribute("boardTypeCd", boardTypeCd);
        return boardTypeCd;
    }
    
    public int deleteBoardData(HttpServletRequest request) {
        int result = 0;
        
        try {
            String jsonStr = request.getParameter("boardIdxs");
            
            JSONArray jsonArr = new JSONArray();
            JSONParser parser = new JSONParser();
            
            // 문자열 형식의 데이터를 JSONArray로 가공
            jsonArr = (JSONArray) parser.parse(jsonStr);
            
            // 데이터의 길이만큼 반복 및 JSONObject로 변환하며 확인
            for (int i = 0; i < jsonArr.size(); i++) {
                org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) jsonArr.get(i);  // 수정된 부분
                int idx = Integer.parseInt((String) jsonObj.get("idx"));
                BoardDTO boardDTO = new BoardDTO();
		        boardDTO.setIdx(idx);
		        boardDTO.setMod_idx(0);
		        result = AdminBoardDAO.getInstance().deleteBoardData(boardDTO);
            }
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public int deleteBoardReplyData(HttpServletRequest request) {
		int result = 0;
		
		try {
			String jsonStr = request.getParameter("replyIdxs");
		    
		    JSONArray jsonArr = new JSONArray();
		    JSONParser parser = new JSONParser();
		    
		    // 문자열 형식의 데이터를 JSONArray로 가공
		    jsonArr = (JSONArray)parser.parse(jsonStr);
		    
			// 데이터의 길이만큼 반복 및 JSONObject로 변환하며 확인
		    for(int i=0;i<jsonArr.size();i++){
		    	org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) jsonArr.get(i);  // 수정된 부분
		        int idx = Integer.parseInt((String) jsonObj.get("idx"));
		        BoardReplyDTO boardReplyDTO = new BoardReplyDTO();
		        boardReplyDTO.setIdx(idx);
		        boardReplyDTO.setModIdx(0);
		        result = AdminBoardDAO.getInstance().deleteBoardReplyData(boardReplyDTO);
		    }
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}


