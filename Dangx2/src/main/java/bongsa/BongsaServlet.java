package bongsa;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.file.FileUpload;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.LoginDTO;


@WebServlet({
	/* get */
	"/bongsa/about.do"
	,"/bongsa/calendar.do"
	,"/bongsa/form.do"
	
	/*=================*/
	
	/* post */
	,"/bongsa/insert.do"
	,"/bongsa/modify.do"
	,"/bongsa/delete.do"
	})
public class BongsaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public BongsaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/about.do")) { // 봉사신청안내 페이지
			request.getRequestDispatcher("../jsp/bongsa/about.jsp").forward(request, response);
		}
		else if(RequestURI.endsWith("/calendar.do")) { // 봉사신청 캘린터 페이지
			selectBongsaList(request);
			request.getRequestDispatcher("../jsp/bongsa/calendar.jsp").forward(request, response);
		}
		else if(RequestURI.endsWith("/form.do")) { // 봉사신청 정보입력 페이지
			String idx = request.getParameter("idx");
			if(idx!=null) {
				// 수정페이지 -> 봉사 데이터 조회
				// + 데이터가 없다면 달력화면으로 이동
				BongsaDTO bongsaDTO = selectBongsaData(request);
				if(bongsaDTO==null) {
					response.sendRedirect("/bongsa/calendar.do");
					return;
				}
			}
			else {
				// 등록 페이지 -> 오늘날짜기준 +1일부터 등록가능
				// + 해당날짜에 등록한 봉사데이터가 있다면 달력화면으로 이동 
				if(!compareToDateAndCheckHistoryData(request)) {
					response.sendRedirect("/bongsa/calendar.do");
					return;
				}
			}
			request.getRequestDispatcher("../jsp/bongsa/form.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("../jsp/bongsa/about.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/insert.do")) { // 최초 등록
			// 등록 후, idx을 가지고 수정화면으로
			// 실패 시, error값 가지고 폼 화면으로
			int bongsaIdx = insertBongsaData(request);
			
			// 등록 후 달력화면 이동으로 변경
			//String bongsaDate = request.getParameter("bongsaDate");
			//String queryString = bongsaIdx>0?("?idx="+bongsaIdx):("?date="+bongsaDate+"&error=1");
			//response.sendRedirect("/bongsa/form.do"+queryString);
			response.sendRedirect("/bongsa/calendar.do");
		}
		else if(RequestURI.endsWith("/modify.do")) { // 정보 수정
			// 수정 후, idx을 가지고 수정화면으로
			// 실패 시, error값 가지고 폼 화면으로
			int result = updateBongsaData(request);
			int bongsaIdx = Integer.parseInt(request.getParameter("idx"));
			String queryString = "?idx="+bongsaIdx;
			if(result<1) {
				queryString += "&error=1";
			}
			
			// 수정 후 달력화면 이동으로 변경
			//response.sendRedirect("/bongsa/form.do"+queryString);
			response.sendRedirect("/bongsa/calendar.do");
		}
		else if(RequestURI.endsWith("/delete.do")) { // 정보 삭제
			int result = deleteBongsaData(request);
			if(result<1) {
				int bongsaIdx = Integer.parseInt(request.getParameter("idx"));
				String queryString = "?idx="+ bongsaIdx + "&error=1";
				response.sendRedirect("/bongsa/form.do"+queryString);
			}
			response.sendRedirect("/bongsa/calendar.do");
		}
	}
	
	private void selectBongsaList(HttpServletRequest request) {
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		
		List<BongsaDTO> bongsaDTOList = BongsaDAO.getInstance().selectBongsaList(sessionDTO.getIdx());
		
		String result = null;
		
		if(bongsaDTOList!=null && bongsaDTOList.size()>0) {
			JSONArray jsonArray = new JSONArray();
			for(BongsaDTO dto : bongsaDTOList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("title", "신청인원: "+dto.getBongsaCount()+"명");
				jsonObject.put("url", "/bongsa/form.do?idx="+dto.getIdx());
				jsonObject.put("start", dto.getBongsaDate());
				//jsonObject.put("color", "#008000");
				
				jsonArray.put(jsonObject);
			}
			result = jsonArray.toString();
		}
		
		request.setAttribute("bongsaList", result);
	}
	
	private BongsaDTO selectBongsaData(HttpServletRequest request) {
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		
		int bongsaIdx = Integer.parseInt(request.getParameter("idx"));
		int userIdx = sessionDTO.getIdx();
		
		//기본정보
		BongsaDTO bongsaDTO = BongsaDAO.getInstance().selectBongsaData(bongsaIdx, userIdx);
		
		//구성원정보
		List<BongsaInfoDTO> bongsaInfoDTOList = BongsaDAO.getInstance().selectBongsaInfoData(bongsaIdx);
		
		request.setAttribute("bongsaDTO", bongsaDTO);
		request.setAttribute("bongsaInfoDTOList", bongsaInfoDTOList);
		
		return bongsaDTO;
	}
	
	private int insertBongsaData(HttpServletRequest request) {
		int result = 0;
		
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		
		BongsaDTO bongsaDTO = new BongsaDTO();
		bongsaDTO.setBongsaDate(request.getParameter("bongsaDate"));
		bongsaDTO.setRegIdx(sessionDTO.getIdx());
		bongsaDTO.setModIdx(sessionDTO.getIdx());
		
		result = BongsaDAO.getInstance().insertBongsa(bongsaDTO); // tb_bongsa idx
		if(result>0) {
			insertBongsaInfoData(request, result);
		}
		
		return result;
	}
	
	private int insertBongsaInfoData(HttpServletRequest request, int bongsaIdx) {
		int result = 0;
		
		String[] bongsaNameArr = request.getParameterValues("bongsaName");
		String[] bongsaPhoneArr = request.getParameterValues("bongsaPhone");
		
		for(int i=0; i<bongsaNameArr.length; i++) {
			if(!bongsaNameArr[i].isBlank() && !bongsaPhoneArr[i].isBlank()) {
				BongsaInfoDTO bongsaInfoDTO = new BongsaInfoDTO();
				bongsaInfoDTO.setBongsaIdx(bongsaIdx);
				bongsaInfoDTO.setBongsaName(bongsaNameArr[i]);
				bongsaInfoDTO.setBongsaPhone(bongsaPhoneArr[i]);
				bongsaInfoDTO.setBongsaOrder(i+1);
				
				result += BongsaDAO.getInstance().insertBongsaInfo(bongsaInfoDTO);
			}
		}
		return result;
	}
	
	private int updateBongsaData(HttpServletRequest request) {
		int result = 0;
		
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
		// tb_bongsa update
		int bongsaIdx = Integer.parseInt(request.getParameter("idx"));
		int userIdx = sessionDTO.getIdx();
		
		result += BongsaDAO.getInstance().updateBongsa(bongsaIdx, userIdx);
		
		// tb_bongsa_info delete > new insert
		if(result>0) {
			BongsaDAO.getInstance().deleteBongsaInfo(bongsaIdx);
			insertBongsaInfoData(request, bongsaIdx);
		}
		
		return result;
	}
	
	private int deleteBongsaData(HttpServletRequest request) {
		int result = 0;
		
		LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");

		int bongsaIdx = Integer.parseInt(request.getParameter("idx"));
		int userIdx = sessionDTO.getIdx();

		// 사용자가 등록한 정보가 맞는지 체크 후
		result = BongsaDAO.getInstance().selectBongsaDataCountByIdx(bongsaIdx, userIdx);
		if(result>0) {
			// 구성원정보 삭제 > 봉사정보 삭제 (외래키)
			// delete -> update로 변경(use_yn => N)
			//BongsaDAO.getInstance().deleteBongsaInfo(bongsaIdx);
			result = BongsaDAO.getInstance().deleteBongsa(bongsaIdx, userIdx);
		}
		
		return result;
	}
	
	private boolean compareToDateAndCheckHistoryData(HttpServletRequest request) {
		boolean result = false;
		try {
			LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
			
			// 날짜비교
			String date = request.getParameter("date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date from = sdf.parse(sdf.format(new Date()));
			Date to = sdf.parse(date);
			if(from.compareTo(to)<0) {
				// 데이터이력 조회
				int dataCount = BongsaDAO.getInstance().selectBongsaDataCountByDate(date, sessionDTO.getIdx());
				if(dataCount==0) {
					result = true;					
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

}
