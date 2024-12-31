package membership;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

import common.SHA256;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.LoginDTO;

@WebServlet({"/member/memberjoin.do",
             "/member/memberProcess.do",
             "/member/checkDuplicateId.do",
             "/member/findId.do",
             "/member/findPassword.do",
             "/member/resetPassword.do",
             "/member/sendEmailAuthCode.do",
             "/member/verifyAuthCode.do",
             "/member/sendEmailAuthCodeForRecovery.do",
             "/member/updateMemberInfo.do", 
             "/member/deleteMember.do",
             "/member/verifyPassword.do"
})
public class MemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 항상 UTF-8 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String requestURI = request.getRequestURI();
        
        // 세션 확인이 필요한 요청을 먼저 처리
        boolean requiresSession = requestURI.endsWith("/updateMemberInfo.do") || 
                                  requestURI.endsWith("/deleteMember.do") ||
        						  requestURI.endsWith("/verifyPassword.do");
        
        // 세션에서 로그인된 사용자 정보 가져오기 (세션 검증이 필요한 경우만)
        LoginDTO userInfo = null;
        if (requiresSession) {
            userInfo = (LoginDTO) request.getSession().getAttribute("userInfo");

            // 세션 만료 확인
            if (userInfo == null) {
                String requestedWithHeader = request.getHeader("X-Requested-With");

                // Ajax 요청인지 확인
                if ("XMLHttpRequest".equals(requestedWithHeader)) {
                    // Ajax 요청일 경우 세션 만료 JSON 반환
                    response.setContentType("application/json; charset=UTF-8");
                    response.getWriter().write("{\"error\": \"세션이 만료되었습니다.\"}");
                } else {
                    // 일반 요청일 경우 로그인 페이지로 리다이렉트
                    response.sendRedirect("/jsp/login/loginPage.jsp");
                }
                return;
            }
        }

        // 요청 URI에 따라 처리 분기
        if (requestURI.endsWith("/memberProcess.do")) {
            processMemberJoin(request, response);
        } else if (requestURI.endsWith("/checkDuplicateId.do")) {
            checkDuplicateId(request, response);
        } else if (requestURI.endsWith("/findId.do")) {
            processFindId(request, response); // 아이디 찾기 처리
        } else if (requestURI.endsWith("/findPassword.do")) { // 비밀번호 찾기
            processFindPassword(request, response);
        } else if (requestURI.endsWith("/resetPassword.do")) { // 비밀번호 변경
            resetPassword(request, response);  // 이 부분은 세션이 필요
        } else if (requestURI.endsWith("/sendEmailAuthCode.do")) { // 이메일 인증 코드 전송
            sendEmailAuthCode(request, response);
        } else if (requestURI.endsWith("/verifyAuthCode.do")) { // 이메일 인증 코드 검증
            verifyAuthCode(request, response);
        } else if (requestURI.endsWith("/sendEmailAuthCodeForRecovery.do")) { // 비밀번호 찾기용 이메일 인증 코드
            sendEmailAuthCodeForRecovery(request, response);
        } else if (requestURI.endsWith("/updateMemberInfo.do")) { // 회원 정보 수정
            updateMemberInfo(request, response);  // 이 부분은 세션이 필요
        } else if (requestURI.endsWith("/deleteMember.do")) { // 회원 탈퇴
            deleteMember(request, response);  // 이 부분은 세션이 필요
        } else if (requestURI.endsWith("/verifyPassword.do")) { // 회원 탈퇴
        	verifyPassword(request, response);  // 이 부분은 세션이 필요
        } else {
            // 유효하지 않은 요청에 대한 처리
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다.");
            return;
        }
    }
    
    // 회원가입 처리
    private void processMemberJoin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        Boolean isAuthenticated = (Boolean) request.getSession().getAttribute("isAuthenticated");
        if (isAuthenticated == null || !isAuthenticated) {
            request.setAttribute("error", "이메일 인증이 완료되지 않았습니다.");
            request.getRequestDispatcher("../jsp/member/memberjoin.jsp").forward(request, response);
            return;
        }

        // 파라미터로 입력값 받기
        String userId = request.getParameter("userId");
        String userPwd = request.getParameter("userPwd");
        String userPwd2 = request.getParameter("userPwd2");
        String userName = request.getParameter("userName");
        String userPhone = request.getParameter("userPhone");
        String userEmail = request.getParameter("userEmail");
        String userPost = request.getParameter("userPost");
        String userAddr = request.getParameter("userAddr");
        String userAddrDtl = request.getParameter("userAddrDtl");
        String userGenderCd = request.getParameter("userGenderCd");
        
        // 이메일 중복 체크
        boolean isEmailDuplicate = MemberDAO.getInstance().isDuplicateEmail(userEmail);
        if (isEmailDuplicate) {
            request.setAttribute("error", "이미 가입된 이메일입니다.");
            request.getRequestDispatcher("../jsp/member/memberjoin.jsp").forward(request, response);
            return;
        }

        // 서버 측 유효성 검사
        String errorMessage = validateInput(userId, userPwd, userPwd2, userName, userPhone, userEmail);

        if (errorMessage != null) {
            // 유효성 검사 실패 시 회원가입 페이지로 리다이렉트 및 에러 메시지 전달
            request.setAttribute("error", errorMessage);
            request.getRequestDispatcher("../jsp/member/memberjoin.jsp").forward(request, response);
            return;
        }
        

        // DTO 객체 생성 및 SHA-256 해시 처리
        MemberDTO dto = new MemberDTO();
        SHA256 sha256 = new SHA256();

        try {
            dto.setUserId(userId);
            dto.setUserPwd(sha256.encrypt(userPwd));
            dto.setUserName(userName);
            dto.setUserPhone(userPhone);
            dto.setUserEmail(userEmail);
            dto.setUserPost(userPost);
            dto.setUserAddr(userAddr);
            dto.setUserAddrDtl(userAddrDtl);
            dto.setUserGenderCd(userGenderCd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // DAO 싱글톤 구현을 통해 dto 전달
        int result = MemberDAO.getInstance().JoinMember(dto);

        // 가입 성공시 메인 페이지로 리다이렉트
        if (result == 1) {
        	request.setAttribute("successMessage", "환영합니다, 회원가입이 완료되었습니다.");
            request.getRequestDispatcher("/jsp/member/joinSuccess.jsp").forward(request, response);
        } else {
            // 실패시 다시 회원가입 페이지로 리다이렉트
        	request.setAttribute("errorMessage", "회원가입에 실패했습니다.");
            request.getRequestDispatcher("../jsp/member/memberjoin.jsp").forward(request, response);
        }
    }

 // 아이디 중복 확인 처리
    private void checkDuplicateId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");

        // JSON 응답 생성
        JSONObject jsonResponse = new JSONObject();

        try {
            // 데이터베이스에서 중복된 아이디가 있는지 확인
            boolean isDuplicate = MemberDAO.getInstance().isDuplicateUserId(userId);

            // JSON 응답 작성
            jsonResponse.put("isDuplicate", isDuplicate);
            jsonResponse.put("message", isDuplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.");

            // JSON 형식으로 응답 반환
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(jsonResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("error", "서버 오류가 발생했습니다.");
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 상태코드 설정
            response.getWriter().write(jsonResponse.toString());
        }
    }

    // 입력값 유효성 검사
    private String validateInput(String userId, String userPwd, String userPwd2, String userName, String userPhone, String userEmail) {
        // 아이디 유효성 검사 (4~12자 영문자 또는 숫자)
        if (userId == null || !userId.matches("^[A-Za-z0-9]{4,12}$")) {
            return "아이디는 영문자 또는 숫자 4~12자로 입력해주세요.";
        }

        // 비밀번호 유효성 검사 (8~16자, 영문, 숫자, 특수문자 포함)
        if (userPwd == null || !userPwd.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$")) {
            return "비밀번호는 8~16자로, 영문, 숫자, 특수문자를 포함해야 합니다.";
        }

        // 비밀번호 확인 유효성 검사
        if (!userPwd.equals(userPwd2)) {
            return "비밀번호가 일치하지 않습니다.";
        }

        // 이름 유효성 검사
        if (userName == null || userName.trim().isEmpty()) {
            return "이름을 입력해주세요.";
        }

        // 핸드폰 번호 유효성 검사
        if (userPhone == null || !userPhone.matches("^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$")) {
            return "유효한 핸드폰 번호를 입력해주세요.";
        }

        // 이메일 유효성 검사
        if (userEmail == null || !userEmail.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            return "유효한 이메일 주소를 입력해주세요.";
        }

        // 모든 검사 통과
        return null;
    }
    
 // 아이디 찾기 메서드
    private void processFindId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");

        // DAO에서 이름과 이메일로 아이디 찾기
        String userId = MemberDAO.getInstance().findUserIdByNameAndEmail(userName, userEmail);

        if (userId != null) {
            // 아이디를 찾았을 경우
            request.setAttribute("foundId", userId);
            RequestDispatcher rd = request.getRequestDispatcher("../jsp/member/displayFoundId.jsp"); // 아이디 결과 페이지로 이동
            rd.forward(request, response);
        } else {
            // 아이디를 찾지 못했을 경우 에러 메시지와 함께 다시 입력 페이지로
            request.setAttribute("errorMessage", "입력하신 정보와 일치하는 아이디가 없습니다.");
            RequestDispatcher rd = request.getRequestDispatcher("../jsp/member/FindID.jsp");
            rd.forward(request, response);
        }
    }
 // 비밀번호 찾기 메서드
    private void processFindPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String userId = request.getParameter("userId");
        String userEmail = request.getParameter("userEmail");


        // DAO에서 이름, 아이디, 이메일로 사용자 확인
        boolean isUserValid = MemberDAO.getInstance().validateUserForPasswordReset(userName, userId, userEmail);

        if (isUserValid) {
            // 사용자 정보가 유효하면 비밀번호 재설정 페이지로 이동
            request.setAttribute("userId", userId);  // userId를 비밀번호 재설정 페이지로 전달
            request.getRequestDispatcher("/jsp/member/findnewpwd.jsp").forward(request, response);
        } else {
            // 사용자 정보가 유효하지 않으면 오류 메시지와 함께 다시 입력 페이지로
            request.setAttribute("errorMessage", "입력하신 정보와 일치하는 사용자가 없습니다.");
            request.getRequestDispatcher("../jsp/member/FindPassWord.jsp").forward(request, response);
        }
    }
    
 // 비밀번호 재설정 메서드
    private void resetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 System.out.println(">>>>> Entering resetPassword method >>>>>");  // 메서드 진입 여부 확인

    	    String userId = request.getParameter("userId");
    	    String newPassword = request.getParameter("newPassword");
    	    String confirmPassword = request.getParameter("confirmPassword");

    	    // 입력받은 값이 제대로 전달되고 있는지 확인
    	    System.out.println("userId: " + userId);
    	    System.out.println("newPassword: " + newPassword);
    	    System.out.println("confirmPassword: " + confirmPassword);

    	    // 비밀번호 확인
    	    if (!newPassword.equals(confirmPassword)) {
    	        request.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
    	        request.getRequestDispatcher("../jsp/member/findnewpwd.jsp").forward(request, response);
    	        return;
    	    }

    	    // 비밀번호 유효성 검사
    	    String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$";
    	    if (!newPassword.matches(passwordPattern)) {
    	        request.setAttribute("errorMessage", "비밀번호는 8~16자, 영문, 숫자, 특수문자를 포함해야 합니다.");
    	        request.getRequestDispatcher("../jsp/member/findnewpwd.jsp").forward(request, response);
    	        return;
    	    }

    	    // 비밀번호 해싱 및 업데이트
    	    SHA256 sha256 = new SHA256();
    	    try {
    	        System.out.println(">>>>> sha256.encrypt(" + newPassword + ") >>>>>");
    	        String hashedPassword = sha256.encrypt(newPassword);
    	        System.out.println("Hashed Password: " + hashedPassword);

    	        // 비밀번호 업데이트
    	        int result = MemberDAO.getInstance().updateUserPassword(userId, hashedPassword);

    	        if (result == 1) {
    	        	request.setAttribute("successMessage", "비밀번호가 정상적으로 변경되었습니다.");
    	            request.getRequestDispatcher("../jsp/member/passwordChangeSuccess.jsp").forward(request, response);
    	        } else {
    	            request.setAttribute("errorMessage", "비밀번호 변경에 실패했습니다.");
    	            request.getRequestDispatcher("../jsp/member/findnewpwd.jsp").forward(request, response);
    	        }
    	    } catch (NoSuchAlgorithmException e) {
    	        e.printStackTrace();
    	        request.setAttribute("errorMessage", "비밀번호 해싱 중 오류가 발생했습니다.");
    	        request.getRequestDispatcher("../jsp/member/findnewpwd.jsp").forward(request, response);
    	    }
    	}

    
    private void sendEmailAuthCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String userEmail = request.getParameter("userEmail");


        // 데이터베이스에서 이메일 중복 여부 확인 (회원가입과 중복 여부 확인)
        boolean isEmailDuplicate = MemberDAO.getInstance().isDuplicateEmail(userEmail);
        if (isEmailDuplicate) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "이미 가입된 이메일입니다.");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        // 인증번호 생성 및 이메일 발송
        String authCode = AuthCodeGenerator.generateAuthCode();
        EmailService emailService = new EmailService();
        boolean emailSent = emailService.sendAuthCode(userEmail, authCode);

        JSONObject jsonResponse = new JSONObject();
        response.setContentType("application/json; charset=UTF-8");

        if (emailSent) {
            request.getSession().setAttribute("authCode", authCode);
            jsonResponse.put("success", true);
            jsonResponse.put("message", "인증번호가 이메일로 발송되었습니다.");
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "이메일 발송에 실패했습니다.");
        }

        response.getWriter().write(jsonResponse.toString());
    }

    
 // 이메일 인증 발송 메서드
    private void sendEmailAuthCodeForRecovery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("userEmail");
        String purpose = request.getParameter("purpose");  // "id" 또는 "password" 값으로 목적 확인

        // 이미 인증된 사용자인지 확인 (목적에 따라 세션에서 확인)
        Boolean isAuthenticated = null;
        if ("id".equals(purpose)) {
            isAuthenticated = (Boolean) request.getSession().getAttribute("isAuthenticatedForId");
        } else if ("password".equals(purpose)) {
            isAuthenticated = (Boolean) request.getSession().getAttribute("isAuthenticatedForPassword");
        }

        // 이미 인증이 완료된 경우
        if (isAuthenticated != null && isAuthenticated) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "이미 인증이 완료되었습니다.");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        // 이메일 등록 여부 확인
        boolean isEmailRegistered = MemberDAO.getInstance().isEmailRegistered(userEmail);
        if (!isEmailRegistered) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "해당 이메일로 가입된 사용자가 없습니다.");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        // 인증번호 생성 및 이메일 발송 로직
        String authCode = AuthCodeGenerator.generateAuthCode();
        EmailService emailService = new EmailService();
        boolean emailSent = emailService.sendAuthCode(userEmail, authCode);

        System.out.println(userEmail + "/" + authCode);  // 디버깅용 출력

        JSONObject jsonResponse = new JSONObject();
        response.setContentType("application/json; charset=UTF-8");

        if (emailSent) {
            // 인증 코드 세션에 저장
            request.getSession().setAttribute("authCode", authCode);
            jsonResponse.put("success", true);
            jsonResponse.put("message", "인증번호가 이메일로 발송되었습니다.");

            // 인증 목적에 따라 세션 상태 설정
            if ("id".equals(purpose)) {
                request.getSession().setAttribute("isAuthenticatedForId", true);  // 아이디 찾기용 인증 완료
            } else if ("password".equals(purpose)) {
                request.getSession().setAttribute("isAuthenticatedForPassword", true);  // 비밀번호 찾기용 인증 완료
            }
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "이메일 발송에 실패했습니다.");
        }

        response.getWriter().write(jsonResponse.toString());
    }
    
    
    private void verifyAuthCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String inputAuthCode = request.getParameter("authCode");

        // 세션에서 저장된 인증번호 가져오기
        String savedAuthCode = (String) request.getSession().getAttribute("authCode");

        JSONObject result = new JSONObject();

        // 디버깅: 세션과 사용자가 입력한 인증번호 출력
        System.out.println("Saved Auth Code: " + savedAuthCode);
        System.out.println("Input Auth Code: " + inputAuthCode);

        // 인증번호 비교 (대소문자 구분 없음)
        if (savedAuthCode != null && savedAuthCode.equalsIgnoreCase(inputAuthCode)) {
            result.put("isAuthSuccess", true);
            // 인증 성공 시 세션에 인증 성공 상태 저장
            request.getSession().setAttribute("isAuthenticated", true);
        } else {
            result.put("isAuthSuccess", false);
        }

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(result.toString());
    }
    
    private void verifyEmailAuthCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputAuthCode = request.getParameter("authCode");

        // 세션에 저장된 인증번호와 비교
        String storedAuthCode = (String) request.getSession().getAttribute("authCode");
        boolean isAuthSuccess = storedAuthCode != null && storedAuthCode.equals(inputAuthCode);
    
        
        // 결과를 JSON 형식으로 응답
        JSONObject result = new JSONObject();
        result.put("isAuthSuccess", isAuthSuccess);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(result.toString());

        // 인증이 성공하면 세션에 인증 성공 상태 저장
        if (isAuthSuccess) {
            request.getSession().setAttribute("isAuthenticated", true);
        }
    }
    
    
    private void updateMemberInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 세션에서 사용자 정보 가져오기
        LoginDTO userInfo = (LoginDTO) request.getSession().getAttribute("userInfo");

        if (userInfo == null) {
            response.sendRedirect("/jsp/login/loginPage.jsp");
            return;
        }

        // 파라미터 값 받기
        String newPassword = request.getParameter("userPwd");
        String phone = request.getParameter("userPhone");
        String postCode = request.getParameter("userPost");
        String addr = request.getParameter("userAddr");
        String addrDtl = request.getParameter("userAddrDtl");

        // 유효성 검사
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            if (!isValidPassword(newPassword)) {
                request.setAttribute("errorMessage", "비밀번호가 유효하지 않습니다.");
                request.getRequestDispatcher("/jsp/member/myPage.jsp").forward(request, response);
                return;
            }
        }

        if (!isValidPhoneNumber(phone)) {
            request.setAttribute("errorMessage", "핸드폰 번호가 유효하지 않습니다.");
            request.getRequestDispatcher("/jsp/member/myPage.jsp").forward(request, response);
            return;
        }

        // 비밀번호 변경이 필요한지 확인
        boolean updatePassword = newPassword != null && !newPassword.trim().isEmpty();

        MemberDAO dao = MemberDAO.getInstance(); // 싱글톤 패턴 적용

        boolean updateSuccess = false;
        if (updatePassword) {
            // 비밀번호 유효성 검사 및 암호화
            SHA256 sha256 = new SHA256();
            try {
                String encryptedPassword = sha256.encrypt(newPassword);
                // 비밀번호와 함께 사용자 정보 업데이트
                updateSuccess = dao.updateMemberInfoWithPassword(userInfo.getUserId(), encryptedPassword, phone, postCode, addr, addrDtl);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "비밀번호 암호화 중 오류가 발생했습니다.");
                request.getRequestDispatcher("/jsp/member/myPage.jsp").forward(request, response);
                return;
            }
        } else {
            // 비밀번호 변경 없이 다른 정보만 수정
            updateSuccess = dao.updateMemberInfo(userInfo.getUserId(), phone, postCode, addr, addrDtl);
        }

        if (updateSuccess) {
            // 수정된 정보를 다시 가져와 세션에 저장
            MemberDTO updatedMemberInfo = dao.getMemberInfo(userInfo.getUserId());

            if (updatedMemberInfo != null) {
                // 수정된 회원 정보를 세션에 저장
                LoginDTO updatedLoginInfo = new LoginDTO();
                updatedLoginInfo.setUserId(updatedMemberInfo.getUserId());
                updatedLoginInfo.setUserName(updatedMemberInfo.getUserName());
                updatedLoginInfo.setUserPhone(updatedMemberInfo.getUserPhone());
                updatedLoginInfo.setUserEmail(updatedMemberInfo.getUserEmail());
                updatedLoginInfo.setUserPost(updatedMemberInfo.getUserPost());
                updatedLoginInfo.setUserAddr(updatedMemberInfo.getUserAddr());
                updatedLoginInfo.setUserAddrDtl(updatedMemberInfo.getUserAddrDtl());

                // 세션 업데이트
                request.getSession().setAttribute("userInfo", updatedLoginInfo);
                
                // 성공 메시지를 세션에 저장
                request.getSession().setAttribute("successMessage", "회원 정보가 성공적으로 수정되었습니다.");

                // 페이지 리다이렉트
                response.sendRedirect("/jsp/member/myPage.jsp");
            } else {
                request.setAttribute("errorMessage", "수정된 회원 정보를 가져오지 못했습니다.");
                request.getRequestDispatcher("/jsp/member/myPage.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "회원 정보 수정에 실패했습니다.");
            request.getRequestDispatcher("/jsp/member/myPage.jsp").forward(request, response);
        }
    }

    // 서버 측 비밀번호 유효성 검사 함수
    private boolean isValidPassword(String password) {
        // 클라이언트와 동일한 패턴
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$";
        return password.matches(passwordPattern);
    }

    // 서버 측 핸드폰 번호 유효성 검사 함수
    private boolean isValidPhoneNumber(String phone) {
        // 클라이언트와 동일한 패턴
        String phonePattern = "^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$";
        return phone.matches(phonePattern);
    }



    private void deleteMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 세션에서 사용자 정보 가져오기
        LoginDTO userInfo = (LoginDTO) request.getSession().getAttribute("userInfo");

        // 세션 유효성 확인
        if (userInfo == null) {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\": \"세션이 만료되었습니다.\"}");
            return;
        }

        // 입력받은 비밀번호 확인
        String inputPassword = request.getParameter("userPwd");
        if (inputPassword == null || inputPassword.trim().isEmpty()) {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\": \"비밀번호를 입력해주세요.\"}");
            return;
        }

        MemberDAO dao = MemberDAO.getInstance();
        SHA256 sha256 = new SHA256();
        String hashedPassword;

        try {
            // 입력된 비밀번호를 해시
            hashedPassword = sha256.encrypt(inputPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\": \"비밀번호 해싱 중 오류가 발생했습니다.\"}");
            return;
        }

        // 비밀번호 검증
        boolean isPasswordCorrect = dao.checkPassword(userInfo.getUserId(), hashedPassword);

        if (isPasswordCorrect) {
            boolean deleteSuccess = dao.deleteMember(userInfo.getUserId());

            if (deleteSuccess) {
                // 세션 종료 전에 응답을 먼저 작성
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"message\": \"탈퇴되었습니다.\", \"redirectUrl\": \"/main/mainPage.do\"}");

                // 세션 무효화
                request.getSession().invalidate();
            } else {
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"error\": \"회원 탈퇴에 실패했습니다.\"}");
            }
        } else {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\": \"비밀번호가 일치하지 않습니다.\"}");
        }
    }
    
    private void verifyPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputPassword = request.getParameter("userPwd");

        if (inputPassword == null || inputPassword.trim().isEmpty()) {
            request.setAttribute("errorMessage", "비밀번호를 입력해주세요.");
            request.getRequestDispatcher("../jsp/member/myPagelogin.jsp").forward(request, response);
            return;
        }

        MemberDAO dao = MemberDAO.getInstance();
        
        // 세션에서 로그인된 사용자 정보 가져오기
        LoginDTO userInfo = (LoginDTO) request.getSession().getAttribute("userInfo");

        if (userInfo == null) {
            request.setAttribute("errorMessage", "세션이 만료되었습니다. 다시 로그인 해주세요.");
            response.sendRedirect("../jsp/login/loginPage.jsp");
            return;
        }

        // 비밀번호 해싱 객체 생성
        SHA256 sha256 = new SHA256();
        String hashedPassword = null;

        try {
            // 입력된 비밀번호를 해시
            hashedPassword = sha256.encrypt(inputPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "비밀번호 해싱 중 오류가 발생했습니다.");
            request.getRequestDispatcher("../jsp/member/myPagelogin.jsp").forward(request, response);
            return;
        }

        // 해시된 비밀번호를 데이터베이스와 비교
        boolean isPasswordCorrect = dao.checkPassword(userInfo.getUserId(), hashedPassword);

        // 로그로 세션 확인 (디버깅용)
        System.out.println("userInfo: " + userInfo);
        System.out.println("passwordVerified: " + request.getSession().getAttribute("passwordVerified"));

        if (isPasswordCorrect) {
            // 비밀번호가 맞으면 세션에 플래그를 설정하고 마이페이지로 이동
            request.getSession().setAttribute("passwordVerified", true);
            response.sendRedirect("../jsp/member/myPage.jsp");
        } else {
            // 비밀번호가 틀리면 오류 메시지를 설정하고 다시 입력 페이지로 리다이렉트
            request.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            request.getRequestDispatcher("../jsp/member/myPagelogin.jsp").forward(request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // UTF-8 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String requestURI = request.getRequestURI();

        // /memberjoin.do 요청에 대한 처리
        if (requestURI.endsWith("/memberjoin.do")) {
            RequestDispatcher rd = request.getRequestDispatcher("../jsp/member/memberjoin.jsp");
            rd.forward(request, response);
            return;  // 이후 코드를 실행하지 않도록 return 추가
        }

        // 세션에서 비밀번호 검증 여부 확인
        Boolean isVerified = (Boolean) request.getSession().getAttribute("passwordVerified");

        if (isVerified == null || !isVerified) {
            // 비밀번호 검증을 하지 않았다면 비밀번호 입력 페이지로 이동
            response.sendRedirect("/jsp/member/enterPassword.jsp");
        } else {
            // 비밀번호 검증이 완료되었다면 마이페이지로 이동
            request.getRequestDispatcher("/jsp/member/myPage.jsp").forward(request, response);
        }
    }
}
