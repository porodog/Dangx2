package common.file;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({
	/* get */
	"/common/file/download.do"

	/*=================*/
	
	/* post */

	})
public class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FileServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		
		if(RequestURI.endsWith("/download.do")) { // 다운로드
			FileUpload fileUpload = new FileUpload();
			fileUpload.fileDownload(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
