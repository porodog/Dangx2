package common;

import java.io.IOException;

import org.apache.tomcat.jakartaee.commons.lang3.ObjectUtils;
import org.apache.tomcat.jakartaee.commons.lang3.StringUtils;

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
 * 로그인,회원가입 이전페이지 설정 필터
 */
@WebFilter({
	"/login/loginPage.do"
	,"/member/memberjoin.do"
	})
public class LoginPrevPageFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		String RequestURI = httpServletRequest.getRequestURI();
		StringBuffer requestURL = httpServletRequest.getRequestURL();
		String getURL = requestURL.substring(0, requestURL.indexOf(RequestURI));
		String referer = httpServletRequest.getHeader("Referer");
		System.out.println(referer);
		if(StringUtils.isBlank(referer) 
				|| referer.contains("/loginPage.do") 
				|| referer.contains("/memberjoin.do")
				|| referer.contains("/agRee.jsp")
				|| referer.contains("/memberjoin.jsp")
				|| referer.contains("/FindID.jsp")
				|| referer.contains("/FIndPassWord.jsp")
			) {
			referer = "/main/mainPage.do";
		}
		else {
			referer = referer.substring(getURL.length());
		}
		
		//System.out.println("fin uri :: " + referer);
		LoginDTO sessionDTO = (LoginDTO) httpServletRequest.getSession().getAttribute("userInfo");
		
		if(RequestURI.endsWith("/loginPage.do")) {
			if(!ObjectUtils.isEmpty(sessionDTO)) {
				httpServletResponse.sendRedirect(referer);
				return;
			}
			else {
				String prevPage = (String) httpServletRequest.getSession().getAttribute("prevPage");
				if(StringUtils.isBlank(prevPage) || !prevPage.equals(referer)) {
					httpServletRequest.getSession().setAttribute("prevPage", referer);
				}
			}
		}
		else if(RequestURI.endsWith("/memberjoin.do")) {
			if(!ObjectUtils.isEmpty(sessionDTO)) {
				httpServletResponse.sendRedirect(referer);
				return;
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
