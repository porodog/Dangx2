package admin.idle;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import common.file.FileUpload;
import idle.IdleDTO;
import idle.IdleReplyDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({
	/* get */
	"/admin/idle/1/list.do"
	,"/admin/idle/1/form.do"
	,"/admin/idle/1/replyList.do"
	
	,"/admin/idle/3/list.do"
	,"/admin/idle/3/form.do"
	,"/admin/idle/3/replyList.do"
	
	
	/*=================*/
	
	/* post */
	,"/admin/idle/1/insert.do"
	,"/admin/idle/3/insert.do"
	
	,"/admin/idle/1/delete.do"
	,"/admin/idle/3/delete.do"
	
	,"/admin/idle/1/replyDelete.do"
	,"/admin/idle/3/replyDelete.do"
	})
public class AdminIdleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AdminIdleServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/list.do")) {
			int idleTypeCd = 1;
			if(RequestURI.endsWith("/3/list.do")) {
				idleTypeCd = 3;
			}
			selectIdleList(request, idleTypeCd);
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin/idle/list.jsp");
			rd.forward(request, response);
		}
		else if(RequestURI.endsWith("/replyList.do")) {
			int idleTypeCd = 1;
			if(RequestURI.endsWith("/3/replyList.do")) {
				idleTypeCd = 3;
			}
			selectIdleReplyList(request, idleTypeCd);
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin/idle/replyList.jsp");
			rd.forward(request, response);
		}
		else if(RequestURI.endsWith("/form.do")) {
			int idleTypeCd = 1;
			if(RequestURI.endsWith("/3/form.do")) {
				idleTypeCd = 3;
			}
			request.setAttribute("idleTypeCd", idleTypeCd);
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin/idle/form.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/insert.do")) {
			int idleTypeCd = 1;
			if(RequestURI.endsWith("/3/insert.do")) {
				idleTypeCd = 3;
			}
			int result = insertIdleData(request, idleTypeCd);
			String queryString = "";
			if(result<1) {
				queryString = "?error=1";
			}
			response.sendRedirect("/admin/idle/"+idleTypeCd+"/list.do" + queryString);
		}
		else if(RequestURI.endsWith("/delete.do")) {
			int idleTypeCd = 1;
			if(RequestURI.endsWith("/3/delete.do")) {
				idleTypeCd = 3;
			}
			int result = deleteIdleData(request);
			String queryString = "";
			if(result<1) {
				queryString = "?error=1";
			}
			response.sendRedirect("/admin/idle/"+idleTypeCd+"/list.do" + queryString);
		}
		else if(RequestURI.endsWith("/replyDelete.do")) {
			int idleTypeCd = 1;
			if(RequestURI.endsWith("/3/replyDelete.do")) {
				idleTypeCd = 3;
			}
			int result = deleteIdleReplyData(request);
			String queryString = "";
			if(result<1) {
				queryString = "?error=1";
			}
			response.sendRedirect("/admin/idle/"+idleTypeCd+"/replyList.do" + queryString);
		}
	}

	
	private void selectIdleList(HttpServletRequest request, int idleTypeCd) {
		List<IdleDTO> idleDTOList = AdminIdleDAO.getInstance().selectIdleList(idleTypeCd);
		request.setAttribute("idleDTOList", idleDTOList);
		request.setAttribute("idleTypeCd", idleTypeCd);
	}
	
	private void selectIdleReplyList(HttpServletRequest request, int idleTypeCd) {
		List<IdleReplyDTO> idleReplyDTOList = AdminIdleDAO.getInstance().selectIdleReplyList(idleTypeCd);
		request.setAttribute("idleReplyDTOList", idleReplyDTOList);
		request.setAttribute("idleTypeCd", idleTypeCd);
	}
	
	private int insertIdleData(HttpServletRequest request, int idleTypeCd) {
		int idleIdx = 0;
		int adminIdx = 0;
		
		String idleName = request.getParameter("idleName");
		String idleBreed = request.getParameter("idleBreed");
		String idleYear = request.getParameter("idleYear");
		String idleKg = request.getParameter("idleKg");
		String idleGenderCd = request.getParameter("idleGenderCd");
		String idleNeuterYn = request.getParameter("idleNeuterYn");
		String content = request.getParameter("content");
		
		IdleDTO idleDTO = new IdleDTO();
		idleDTO.setIdleName(idleName);
		idleDTO.setIdleBreed(idleBreed);
		idleDTO.setIdleYear(idleYear);
		idleDTO.setIdleKg(idleKg);
		idleDTO.setIdleGenderCd(idleGenderCd);
		idleDTO.setIdleNeuterYn(idleNeuterYn);
		idleDTO.setIdleCurrentContent(content);
		idleDTO.setRegIdx(adminIdx);
		idleDTO.setModIdx(adminIdx);
		idleDTO.setIdleTypeCd(idleTypeCd);
		
		idleIdx = AdminIdleDAO.getInstance().insertIdleData(idleDTO);
	
		if(idleIdx>0) {
			int fileTargetCd = 4;
			if(idleTypeCd==3) {
				fileTargetCd = 6;
			}
			FileUpload fileUpload = new FileUpload(request, fileTargetCd, idleIdx);
	        fileUpload.fileUploadAndInsert();
		}
        
        return idleIdx;
	}
	
	public int deleteIdleData(HttpServletRequest request) {
		int result = 0;
		
		try {
			String jsonStr = request.getParameter("idleIdxs");
		    
		    JSONArray jsonArr = new JSONArray();
		    JSONParser parser = new JSONParser();
		    
		    // 문자열 형식의 데이터를 JSONArray로 가공
		    jsonArr = (JSONArray)parser.parse(jsonStr);
		    
			// 데이터의 길이만큼 반복 및 JSONObject로 변환하며 확인
		    for(int i=0;i<jsonArr.size();i++){
		    	JSONObject jsonObj = (JSONObject) jsonArr.get(i);
		        int idx = Integer.parseInt((String) jsonObj.get("idx"));
		        IdleDTO idleDTO = new IdleDTO();
		        idleDTO.setIdx(idx);
		        idleDTO.setModIdx(0);
		        result = AdminIdleDAO.getInstance().deleteIdleData(idleDTO);
		    }
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int deleteIdleReplyData(HttpServletRequest request) {
		int result = 0;
		
		try {
			String jsonStr = request.getParameter("replyIdxs");
		    
		    JSONArray jsonArr = new JSONArray();
		    JSONParser parser = new JSONParser();
		    
		    // 문자열 형식의 데이터를 JSONArray로 가공
		    jsonArr = (JSONArray)parser.parse(jsonStr);
		    
			// 데이터의 길이만큼 반복 및 JSONObject로 변환하며 확인
		    for(int i=0;i<jsonArr.size();i++){
		    	JSONObject jsonObj = (JSONObject) jsonArr.get(i);
		        int idx = Integer.parseInt((String) jsonObj.get("idx"));
		        IdleReplyDTO idleReplyDTO = new IdleReplyDTO();
		        idleReplyDTO.setIdx(idx);
		        idleReplyDTO.setModIdx(0);
		        result = AdminIdleDAO.getInstance().deleteIdleReplyData(idleReplyDTO);
		    }
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
