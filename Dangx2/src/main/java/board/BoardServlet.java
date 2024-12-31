package board;

import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import java.util.ArrayList;
import common.file.FileDAO;
import common.file.FileDTO;
import common.file.FileUpload;
import common.page.Pagination;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({ /* GET */
    "/boardList1.do", 
    "/boardList2.do", 
    "/boardList3.do", 
    "/readForm.do",
    
    /* POST */
    "/updateBoard.do",
    "/writeForm.do",
    "/editForm.do",
    "/deleteBoard.do",
    "/deleteBoardFile.do"
})
public class BoardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BoardDAO boardDAO = BoardDAO.getInstance();
        
        String boardTypeCd = "1";  // 기본값: 쉼터
        String requestURI = request.getRequestURI();
        if (requestURI.contains("boardList2")) {
            boardTypeCd = "2";  // 입양
        } else if (requestURI.contains("boardList3")) {
            boardTypeCd = "3";  // 후원
        }
        
        if (request.getRequestURI().contains("readForm.do")) {
            int idx = Integer.parseInt(request.getParameter("idx"));
            BoardDTO boardDTO = boardDAO.getBoardByIdx(idx);
            
            if (boardDTO != null) {
                int boardType = Integer.parseInt(request.getParameter("boardType"));
                List<FileDTO> fileDTOList = FileDAO.getInstance().selectFileList(boardType, idx);
                List<BoardReplyDTO> replyDTOList = boardDAO.selectBoardReplyList(idx);
                
                request.setAttribute("fileDTOList", fileDTOList);
                request.setAttribute("replyDTOList", replyDTOList);
            }
            
            request.setAttribute("boardDTO", boardDTO);
            
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/board/readForm.jsp");
            rd.forward(request, response);
            return;
        } else if (request.getRequestURI().contains("writeForm.do")) {
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/board/writeForm.jsp");
            rd.forward(request, response);
            return;
        } else if (request.getRequestURI().contains("editForm.do")) {
            // idx 파라미터를 받아 해당 게시글을 조회
            int idx = Integer.parseInt(request.getParameter("idx"));
            BoardDTO boardDTO = boardDAO.getBoardByIdx(idx);
            
            if (boardDTO != null) {
                int boardType = Integer.parseInt(request.getParameter("boardType"));
                List<FileDTO> fileDTOList = FileDAO.getInstance().selectFileList(boardType, idx);

                request.setAttribute("boardDTO", boardDTO);
                request.setAttribute("fileDTOList", fileDTOList);
            }

            RequestDispatcher rd = request.getRequestDispatcher("/jsp/board/editForm.jsp");
            rd.forward(request, response);
            return;
        } else {
            // 페이징 위한 파라미터
            String page = request.getParameter("page"); // 페이지 파라미터 조회
            page = (page == null) ? "1" : page;
            
            String searchKey = request.getParameter("searchKey");
            searchKey = (searchKey == null) ? "title" : searchKey;
            
            String searchValue = request.getParameter("searchValue");
            searchValue = (searchValue == null) ? "" : searchValue;
            
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setBoard_type_cd(boardTypeCd);
            boardDTO.setSearchKey(searchKey);
            boardDTO.setSearchValue(searchValue);
            
            int totalBoards = boardDAO.getTotalBoardsCount(boardDTO);
            
            int curPage = Integer.parseInt(page); // 현재페이지
            int pageSize = 10; // 사이즈
            Pagination pagination = new Pagination(totalBoards, curPage, pageSize);
            boardDTO.setStartIndex(pagination.getStartIndex());
            boardDTO.setCntPerPage(pagination.getPageSize());
            
            List<BoardDTO> boards = boardDAO.getBoards(boardDTO);

            request.setAttribute("lists", boards);  
            request.setAttribute("totalBoards", totalBoards);
            request.setAttribute("boardTypeCd", boardTypeCd);
            request.setAttribute("pagination", pagination);
            request.setAttribute("boardDTO", boardDTO);

            String jspPage = "/jsp/board/boardList1.jsp";  // 기본 쉼터
            if (boardTypeCd.equals("2")) {
                jspPage = "/jsp/board/boardList2.jsp";
            } else if (boardTypeCd.equals("3")) {
                jspPage = "/jsp/board/boardList3.jsp";
            }

            RequestDispatcher rd = request.getRequestDispatcher(jspPage);
            rd.forward(request, response);
        }
    }

 // 게시글 수정
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        BoardDAO boardDAO = BoardDAO.getInstance();
        Integer sessionUserIdx = (Integer) request.getSession().getAttribute("userIdx");

        if (requestURI.contains("updateBoard.do")) {
            int idx = Integer.parseInt(request.getParameter("idx"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String boardType = request.getParameter("boardType");

            // 수정할 게시글 DTO 생성 및 설정
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setIdx(idx);
            boardDTO.setTitle(title);
            boardDTO.setContent(content);
            boardDTO.setBoard_type_cd(boardType);
            System.out.println(boardType);

            // 삭제할 파일 ID 목록 추출
            String deletedFilesParam = request.getParameter("deletedFiles");
            List<Integer> deletedFileIds = new ArrayList<>();
            if (deletedFilesParam != null && !deletedFilesParam.isEmpty()) {
                JSONArray deletedFileIndices = new JSONArray(deletedFilesParam);
                for (int i = 0; i < deletedFileIndices.length(); i++) {
                    deletedFileIds.add(deletedFileIndices.getInt(i));
                }
            }

            // 삭제할 파일의 use_yn 값을 N으로 업데이트
            for (int fileIdx : deletedFileIds) {
                FileDTO fileDTO = FileDAO.getInstance().selectFileData(fileIdx);
                if (fileDTO != null) {
                    fileDTO.setUseYn("N");
					/* fileDTO.setModIdx(sessionUserIdx); */
                    FileDAO.getInstance().updateFileStatus(fileDTO);
                }
            }

            // 데이터베이스에서 게시글 수정
            boardDAO.updateBoard(boardDTO);
            
            FileUpload fileUpload = new FileUpload(request, Integer.parseInt(boardType), idx);
            fileUpload.fileUploadAndInsert();

            // 게시글 수정 완료 후 해당 게시판 목록 페이지로 이동
            response.sendRedirect("boardList" + boardType + ".do");
        } else if (requestURI.contains("deleteBoard.do")) {
            int idx = Integer.parseInt(request.getParameter("idx"));
            String boardType = request.getParameter("boardType");

            // 데이터베이스에서 게시글 삭제처리 (use_yn을 N으로)
            boardDAO.deleteBoard(idx);

            // 삭제 후 해당 게시판 목록 페이지로 리다이렉트
            response.sendRedirect("boardList" + boardType + ".do");
        }
    }
}

