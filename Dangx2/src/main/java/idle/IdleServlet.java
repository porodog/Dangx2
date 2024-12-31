package idle;

import java.io.IOException;
import java.util.List;

import common.file.FileDAO;
import common.file.FileDTO;
import common.page.Pagination;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.LoginDTO;

@WebServlet({
	/* get */
	"/idle/shelter/about.do"
	,"/idle/shelter/list.do"
	,"/idle/shelter/view.do"
	
	,"/idle/protect/about.do"
	,"/idle/protect/list.do"
	,"/idle/protect/view.do"
	
	,"/idle/adoption/about.do"
	,"/idle/adoption/list.do"
	,"/idle/adoption/view.do"
	
	/*=================*/
	
	/* post */
	,"/idle/reply/insert.do"
	,"/idle/reply/modify.do"
	,"/idle/reply/delete.do"
	})
public class IdleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IdleServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		String menuType = RequestURI.split("/")[2];
		
		if(RequestURI.endsWith("/about.do")) { // 소개 페이지
			request.setAttribute("menuType", menuType);
			request.getRequestDispatcher("../../jsp/idle/about.jsp").forward(request, response);
		}
		else if(RequestURI.endsWith("/list.do")) { // 목록 페이지
			selectIdleList(request);
			request.getRequestDispatcher("../../jsp/idle/list.jsp").forward(request, response);
		}
		else if(RequestURI.endsWith("/view.do")) { // 상세 페이지
			IdleDTO idleDTO = selectIdleData(request);
			if(idleDTO==null) {
				response.sendRedirect("/idle/"+menuType+"/list.do");
				return;
			}
			request.getRequestDispatcher("../../jsp/idle/view.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		String menuType = (String) request.getParameter("menuType");
		String idleIdx = (String) request.getParameter("idleIdx");
		
		if(RequestURI.endsWith("/insert.do")) { // 댓글 등록
			insertReplyData(request);
			response.sendRedirect("/idle/"+menuType+"/view.do?idx="+idleIdx);
		}
		else if(RequestURI.endsWith("/modify.do")) { // 댓글 수정
			updateReplyData(request);
			response.sendRedirect("/idle/"+menuType+"/view.do?idx="+idleIdx);
		}
		else if(RequestURI.endsWith("/delete.do")) {
			deleteReplyData(request);
			response.sendRedirect("/idle/"+menuType+"/view.do?idx="+idleIdx);
		}
	}
	
	private int deleteReplyData(HttpServletRequest request) {
		int result = 0;
		
		int idx = Integer.parseInt(request.getParameter("replyIdx"));
		
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		int userIdx = sessionDTO.getIdx();
		
		IdleReplyDTO idleReplyDTO = new IdleReplyDTO();
		idleReplyDTO.setIdx(idx);
		idleReplyDTO.setRegIdx(userIdx);
		idleReplyDTO.setModIdx(userIdx);
		
		result = IdleDAO.getInstance().deleteIdleReply(idleReplyDTO);
		
		return result;
	}
	
	private int updateReplyData(HttpServletRequest request) {
		int result = 0;
		
		int idx = Integer.parseInt(request.getParameter("replyIdx"));
		String content = request.getParameter("content");
		
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		int userIdx = sessionDTO.getIdx();
		
		IdleReplyDTO idleReplyDTO = new IdleReplyDTO();
		idleReplyDTO.setIdx(idx);
		idleReplyDTO.setContent(content);
		idleReplyDTO.setRegIdx(userIdx);
		idleReplyDTO.setModIdx(userIdx);
		
		result = IdleDAO.getInstance().updateIdleReply(idleReplyDTO);
		
		return result;
	}
	
	private int insertReplyData(HttpServletRequest request) {
		int result = 0;
		
		int idleIdx = Integer.parseInt(request.getParameter("idleIdx"));
		String content = request.getParameter("content");
		
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		int userIdx = sessionDTO.getIdx();
		
		IdleReplyDTO idleReplyDTO = new IdleReplyDTO();
		idleReplyDTO.setIdleIdx(idleIdx);
		idleReplyDTO.setContent(content);
		idleReplyDTO.setRegIdx(userIdx);
		idleReplyDTO.setModIdx(userIdx);
		
		result = IdleDAO.getInstance().insertIdleReply(idleReplyDTO);
		
		return result;
	}
	

	private void setIdleTypeCd(HttpServletRequest request, IdleDTO idleDTO) {
		String RequestURI = request.getRequestURI();
		String menuType = RequestURI.split("/")[2];
		//System.out.println("menuType :: " + menuType);
		
		int idleTypeCd = 0;
		int fileTargetCd = 0;
		
		if(menuType.equals("shelter")) {
			idleTypeCd = 1;
			fileTargetCd = 4;
		}
		else if(menuType.equals("protect")) {
			idleTypeCd = 2;
			fileTargetCd = 5;
		}
		else if(menuType.equals("adoption")) {
			idleTypeCd = 3;
			fileTargetCd = 6;
		}
		
		idleDTO.setIdleTypeCd(idleTypeCd);
		idleDTO.setTargetTypeCd(fileTargetCd);
		idleDTO.setIdleTypeName(menuType);
	}
	
	private void selectIdleList(HttpServletRequest request) {
		// 파라미터 조회
		String page = request.getParameter("page"); // 페이지 파라미터 조회
		page = (page == null) ? "1" : page;
		
		// 세팅
		IdleDTO idleDTO = new IdleDTO();
		setIdleTypeCd(request,  idleDTO);
		
		// 목록개수 조회
		int listCnt = IdleDAO.getInstance().selectIdleListCount(idleDTO);

		// 페이지 세팅
		int curPage = Integer.parseInt(page); // 현재페이지
		int pageSize = 9; // 사이즈
		Pagination pagination = new Pagination(listCnt, curPage, pageSize);
		idleDTO.setStartIndex(pagination.getStartIndex());
		idleDTO.setCntPerPage(pagination.getPageSize());

		// 목록 조회
		List<IdleDTO> idleDTOList = IdleDAO.getInstance().selectIdleList(idleDTO);

		// 전송
		request.setAttribute("idleDTO", idleDTO);
		request.setAttribute("idleDTOList", idleDTOList);
		request.setAttribute("pagination", pagination);
	}
	
	private IdleDTO selectIdleData(HttpServletRequest request) {
		// 파라미터 조회
		int idleIdx = Integer.parseInt(request.getParameter("idx"));
		
		// 세팅
		IdleDTO idleDTO = new IdleDTO();
		idleDTO.setIdx(idleIdx);
		setIdleTypeCd(request,  idleDTO);
		
		// 상세 조회
		IdleDTO dataDTO = IdleDAO.getInstance().selectIdleData(idleDTO);

		if (dataDTO != null) {
			// 이미지파일 조회
			List<FileDTO> fileDTOList = FileDAO.getInstance().selectFileList(idleDTO.getTargetTypeCd(), idleIdx);

			// 댓글 정보 조회
			List<IdleReplyDTO> idleReplyDTOList = IdleDAO.getInstance().selectIdleReplyList(idleIdx);

			// 전송
			request.setAttribute("idleDTO", idleDTO);
			request.setAttribute("dataDTO", dataDTO);
			request.setAttribute("fileDTOList", fileDTOList);
			request.setAttribute("idleReplyDTOList", idleReplyDTOList);
		}
		return dataDTO;
	}
	
	private int deleteShelterReply(HttpServletRequest request) {
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		int result = 0;

		int replyIdx = Integer.parseInt(request.getParameter("replyIdx"));
		int userIdx = sessionDTO.getIdx();

		IdleReplyDTO idleReplyDTO = new IdleReplyDTO();
		idleReplyDTO.setIdx(replyIdx);
		idleReplyDTO.setRegIdx(userIdx);

		result = IdleDAO.getInstance().deleteIdleReply(idleReplyDTO);

		return result;
	}

	private int insertShelterReply(HttpServletRequest request) {
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		int result = 0;

		int idleIdx = Integer.parseInt(request.getParameter("idleIdx"));
		String content = request.getParameter("content");
		int userIdx = sessionDTO.getIdx();

		IdleReplyDTO idleReplyDTO = new IdleReplyDTO();
		idleReplyDTO.setIdleIdx(idleIdx);
		idleReplyDTO.setContent(content);
		idleReplyDTO.setRegIdx(userIdx);
		idleReplyDTO.setModIdx(userIdx);

		result = IdleDAO.getInstance().insertIdleReply(idleReplyDTO);

		return result;
	}
	
}
