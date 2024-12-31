package admin.common;

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
	"/admin/*"
	})
public class AdminLoginCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		request.setCharacterEncoding("UTF-8");
		String RequestURI = httpServletRequest.getRequestURI();
		
		if(!RequestURI.endsWith("/login.do")) {
			AdminDTO sessionDTO = (AdminDTO) httpServletRequest.getSession().getAttribute("adminInfo");
			if(ObjectUtils.isEmpty(sessionDTO)) {
				httpServletResponse.sendRedirect("/admin/login.do");
				return;
			}
			
			httpServletRequest.setAttribute("menuURI", RequestURI);
		}
		
		filterChain.doFilter(request, response);
	}

}
