package membership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.JDBConnect;
// db연결 상속
public class MemberDAO extends JDBConnect{
	private static MemberDAO instance;
	//싱글톤 패턴 구현 
	private MemberDAO() {
        super();
    }
	
	public static MemberDAO getInstance() {
		if (instance == null)
			instance = new MemberDAO();
		return instance;
	}	
	
public int JoinMember(MemberDTO dto) {
		this.checkConnect();
		int result = 0;
	
	 try {
		 
         // SQL 쿼리 작성
         String sql = "INSERT INTO tb_user (user_id, user_pwd, user_name, user_phone, user_email, user_post, user_addr, user_addr_dtl, user_gender_cd) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
         // sql 쿼리 준비
         psmt = con.prepareStatement(sql);
         psmt.setString(1, dto.getUserId());
         psmt.setString(2, dto.getUserPwd());
         psmt.setString(3, dto.getUserName());
         psmt.setString(4, dto.getUserPhone());
         psmt.setString(5, dto.getUserEmail());
         psmt.setString(6, dto.getUserPost());
         psmt.setString(7, dto.getUserAddr());
         psmt.setString(8, dto.getUserAddrDtl());
         psmt.setString(9, dto.getUserGenderCd());

         // 쿼리 실행
         result = psmt.executeUpdate();
     } 
      catch (Exception e) {
    	  //예외처리 
         e.printStackTrace();
         System.out.println("<h2>SQL 오류가 발생했습니다.</h2>");
     } finally {
         this.close(); 
         //자원 반납
     }
	return result;
	
  }
//아이디 중복 확인 메소드
public boolean isDuplicateUserId(String userId) {
	this.checkConnect();
	
 boolean isDuplicate = false;
 String sql = "SELECT COUNT(*) FROM tb_user WHERE user_id = ?";
 
 try {
 	Connection conn = this.con; 
 	PreparedStatement pstmt = conn.prepareStatement(sql);
 	
     pstmt.setString(1, userId);
     try (ResultSet rs = pstmt.executeQuery()) {
         if (rs.next() && rs.getInt(1) > 0) {
             isDuplicate = true; // 아이디가 이미 존재함
         }
     }
 } catch (Exception e) {
     e.printStackTrace();
 }
 return isDuplicate;
}


public String findUserIdByNameAndEmail(String userName, String userEmail) {
    this.checkConnect(); // DB 연결 체크
    String userId = null;
    String sql = "SELECT user_id FROM tb_user WHERE user_name = ? AND user_email = ?";

    try {
    	Connection conn = this.con;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, userName);
        pstmt.setString(2, userEmail);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                userId = rs.getString("user_id"); // 해당 아이디를 찾으면 저장
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return userId;
}

public boolean validateUserForPasswordReset(String userName, String userId, String userEmail) {
    this.checkConnect();
    String sql = "SELECT COUNT(*) FROM tb_user WHERE user_name = ? AND user_id = ? AND user_email = ?";
    boolean isValid = false;

    try {
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, userName);
        pstmt.setString(2, userId);
        pstmt.setString(3, userEmail);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            isValid = true; // User is valid
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return isValid;
}

public int updateUserPassword(String userId, String newPassword) {
	this.checkConnect();
    String sql = "UPDATE tb_user SET user_pwd = ? WHERE user_id = ?";
    int result = 0;

    try {
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, newPassword);
        pstmt.setString(2, userId);
        result = pstmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return result;
}

public boolean isDuplicateEmail(String email) {
	this.checkConnect();
    String sql = "SELECT COUNT(*) FROM tb_user WHERE user_email = ?"; // tb_user로 테이블명 수정
    try {
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, email);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // 이메일이 존재하면 true
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false; // 중복된 이메일이 없을 경우

}

public boolean isEmailRegistered(String email) {
    this.checkConnect();
    String sql = "SELECT COUNT(*) FROM tb_user WHERE user_email = ?";
    try {
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, email);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // 이메일이 존재하면 true
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false; // 이메일이 없으면 false
}

//비밀번호와 함께 회원 정보 수정 메서드
public boolean updateMemberInfoWithPassword(String userId, String newPassword, String phone, String postCode, String addr, String addrDtl) {
	this.checkConnect();
    String sql = "UPDATE tb_user SET user_pwd = ?, user_phone = ?, user_post = ?, user_addr = ?, user_addr_dtl = ?, mod_date = NOW() WHERE user_id = ?";

    try {
        psmt = con.prepareStatement(sql);
        psmt.setString(1, newPassword); // 새 비밀번호
        psmt.setString(2, phone);       // 핸드폰 번호
        psmt.setString(3, postCode);    // 우편번호
        psmt.setString(4, addr);        // 주소
        psmt.setString(5, addrDtl);     // 상세주소
        psmt.setString(6, userId);      // 사용자 아이디

        int result = psmt.executeUpdate();
        return result > 0; // 업데이트 성공 여부 반환
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

// 비밀번호 제외 회원 정보 수정 메서드
public boolean updateMemberInfo(String userId, String phone, String postCode, String addr, String addrDtl) {
	this.checkConnect();
    String sql = "UPDATE tb_user SET user_phone = ?, user_post = ?, user_addr = ?, user_addr_dtl = ?, mod_date = NOW() WHERE user_id = ?";

    try {
        psmt = con.prepareStatement(sql);
        psmt.setString(1, phone);       // 핸드폰 번호
        psmt.setString(2, postCode);    // 우편번호
        psmt.setString(3, addr);        // 주소
        psmt.setString(4, addrDtl);     // 상세주소
        psmt.setString(5, userId);      // 사용자 아이디

        int result = psmt.executeUpdate();
        return result > 0; // 업데이트 성공 여부 반환
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

// 회원 탈퇴 메서드
public boolean deleteMember(String userId) {
	this.checkConnect();
    String sql = "DELETE FROM tb_user WHERE user_id = ?";

    try {
        psmt = con.prepareStatement(sql);
        psmt.setString(1, userId);
        int result = psmt.executeUpdate();
        return result > 0; // 삭제 성공 여부 반환
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

// 비밀번호 확인 메서드
public boolean checkPassword(String userId, String inputPassword) {
	this.checkConnect();
    String sql = "SELECT user_pwd FROM tb_user WHERE user_id = ?";

    try {
        psmt = con.prepareStatement(sql);
        psmt.setString(1, userId);
        ResultSet rs = psmt.executeQuery();

        if (rs.next()) {
            String storedPassword = rs.getString("user_pwd"); // 컬럼명 수정
            return storedPassword.equals(inputPassword); // 비밀번호가 일치하는지 확인
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

// 새로운 메서드: 회원 정보 조회 메서드 추가
public MemberDTO getMemberInfo(String userId) {
    this.checkConnect();
    MemberDTO member = null;
    String sql = "SELECT user_id, user_name, user_phone, user_email, user_post, user_addr, user_addr_dtl FROM tb_user WHERE user_id = ?";

    try {
        psmt = con.prepareStatement(sql);
        psmt.setString(1, userId);
        ResultSet rs = psmt.executeQuery();

        if (rs.next()) {
            member = new MemberDTO();
            member.setUserId(rs.getString("user_id"));
            member.setUserName(rs.getString("user_name"));
            member.setUserPhone(rs.getString("user_phone"));
            member.setUserEmail(rs.getString("user_email"));
            member.setUserPost(rs.getString("user_post"));
            member.setUserAddr(rs.getString("user_addr"));
            member.setUserAddrDtl(rs.getString("user_addr_dtl"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return member;
}


}

