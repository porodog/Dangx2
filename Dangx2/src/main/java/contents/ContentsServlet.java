package contents;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({
	/* get */
	"/contents/about.do"
	,"/contents/boost.do"
	})
public class ContentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ContentsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/about.do")) {
			request.getRequestDispatcher("../jsp/contents/about.jsp").forward(request, response);
		}
		else if(RequestURI.endsWith("/boost.do")) {
			request.getRequestDispatcher("../jsp/contents/boost.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
