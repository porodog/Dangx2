package common;

import java.io.IOException;

import org.apache.tomcat.jakartaee.commons.lang3.ObjectUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.LoginDTO;


/**
 * 로그인 상태체크 필터
 */
@WebFilter({
	/* 봉사 */
	"/bongsa/calendar.do"
	,"/bongsa/form.do"
	,"/board/write.do"
	,"/writeForm.do"
	,"/editForm.do"
	})
public class LoginCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		LoginDTO sessionDTO = (LoginDTO) httpServletRequest.getSession().getAttribute("userInfo");
		if(ObjectUtils.isEmpty(sessionDTO)) {
			// 중요!!!!!!!! 로그인 후 해당페이지로 이동되도록 추가하기
			httpServletResponse.sendRedirect("/login/loginPage.do");
			return;
		}
		
		filterChain.doFilter(request, response);
		
	}

}
