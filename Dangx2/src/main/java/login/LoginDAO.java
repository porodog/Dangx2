package login;

import common.JDBConnect;

public class LoginDAO extends JDBConnect {
	
	private static LoginDAO instance;

	private LoginDAO() {
        super();
    }
	
	public static LoginDAO getInstance() {
		if (instance == null)
			instance = new LoginDAO();
		return instance;
	}
	
	
	public LoginDTO doLoginProcess(String userId, String userPwd) {
		this.checkConnect();
		
        LoginDTO dto = null;

        try {
        	String query = "SELECT idx, user_id, user_name, user_phone, user_email, user_post, user_addr, user_addr_dtl FROM tb_user WHERE user_id=? AND user_pwd=?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, userId);
            psmt.setString(2, userPwd);
            rs = psmt.executeQuery();

            if(rs.next()) {
            	dto = new LoginDTO();
            	dto.setIdx(rs.getInt("idx"));
                dto.setUserId(rs.getString("user_id"));
                dto.setUserName(rs.getString("user_name"));
                dto.setUserPhone(rs.getString("user_phone"));
                dto.setUserEmail(rs.getString("user_email"));
                dto.setUserPost(rs.getString("user_post"));
                dto.setUserAddr(rs.getString("user_addr"));
                dto.setUserAddrDtl(rs.getString("user_addr_dtl"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
			this.close();
		}
        
        return dto; 
    }
}
