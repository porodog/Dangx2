package board;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import board.BoardDAO;
import common.file.FileDAO;
import common.file.FileDTO;
import common.file.FileUpload;
import login.LoginDTO;

@WebServlet("/BoardWriteAction.do")
public class BoardWriteAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 세션에서 사용자 정보를 가져옴
        LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
        int userIdx;
        
        // 세션이 없는 경우 기본 값 또는 테스트용 값 설정
        if (sessionDTO == null) {
            userIdx = 0;  // 세션이 없을 때 테스트용 사용자 ID 설정
            System.out.println("세션 없음: 테스트용 userIdx = 0");
        } else {
            userIdx = sessionDTO.getIdx();
        }

        // 요청 처리 로직
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String board_type_cd = request.getParameter("board_type_cd"); // 쉼터1, 입양2, 후원3

        //System.out.println(title);
        //System.out.println(content);
        
        // 데이터베이스에 저장
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setUser_id(id);  
        boardDTO.setTitle(title);
        boardDTO.setContent(content);
        boardDTO.setBoard_type_cd(board_type_cd);
        boardDTO.setReg_idx(userIdx);
        boardDTO.setMod_idx(userIdx);

        BoardDAO boardDAO = new BoardDAO();
        int boardIdx = boardDAO.insertBoard(boardDTO);
        
        FileUpload fileUpload = new FileUpload(request, Integer.parseInt(board_type_cd), boardIdx);
        fileUpload.fileUploadAndInsert();

        // DB저장 후 해당 게시판 목록 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/boardList" + board_type_cd + ".do");
    }
}
