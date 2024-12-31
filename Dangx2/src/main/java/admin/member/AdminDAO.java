package admin.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.JDBConnect;
import membership.MemberDTO;

public class AdminDAO extends JDBConnect {
    private static AdminDAO instance;

    // 싱글톤 패턴 구현
    private AdminDAO() {
        super();
    }

    public static AdminDAO getInstance() {
        if (instance == null)
            instance = new AdminDAO();
        return instance;
    }

    // 모든 회원 정보 가져오기
    public List<MemberDTO> getAllMembers() {
        this.checkConnect(); // 연결 확인
        List<MemberDTO> members = new ArrayList<MemberDTO>(); // 다수의 회원 정보를 담을 리스트
        String sql = "SELECT user_id, user_name, user_phone, user_email, user_post, user_addr, user_addr_dtl, user_gender_cd, reg_date FROM tb_user";

        try {
            psmt = con.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();
            
            // 쿼리 실행 후 결과가 있는지 확인
            if (!rs.isBeforeFirst()) {
                System.out.println("ResultSet is empty.");
            }

            while (rs.next()) {
            	MemberDTO member = new MemberDTO(); // MemberDTO 객체 생성
                member.setUserId(rs.getString("user_id"));
                member.setUserName(rs.getString("user_name"));
                member.setUserPhone(rs.getString("user_phone"));
                member.setUserEmail(rs.getString("user_email"));
                member.setUserPost(rs.getString("user_post"));
                member.setUserAddr(rs.getString("user_addr"));
                member.setUserAddrDtl(rs.getString("user_addr_dtl"));
                member.setUserGenderCd(rs.getString("user_gender_cd"));
                member.setRegDate(rs.getString("reg_date"));
                members.add(member); // 리스트에 회원 정보 추가

                // 디버깅용 출력문 추가
                System.out.println("User ID: " + member.getUserId() + " | Name: " + member.getUserName());
            }

            // 자원 해제
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (psmt != null) psmt.close(); // PreparedStatement 자원 해제
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total members retrieved: " + members.size());
        return members; // 리스트 반환
    }




    // 회원 삭제
    public void deleteMembers(List<String> memberIds) {
        this.checkConnect();
        String sql = "DELETE FROM tb_user WHERE user_id = ?";

        try {
            Connection conn = this.con;
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (String userId : memberIds) {
                pstmt.setString(1, userId);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
