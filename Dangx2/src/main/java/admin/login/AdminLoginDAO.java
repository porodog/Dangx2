package admin.login;

import admin.common.AdminDTO;
import common.JDBConnect;

public class AdminLoginDAO extends JDBConnect {
	private static AdminLoginDAO instance;

	private AdminLoginDAO() {
        super();
    }
	
	public static AdminLoginDAO getInstance() {
		if (instance == null)
			instance = new AdminLoginDAO();
		return instance;
	}
	
	
	public AdminDTO doLoginProcess(String userId, String userPwd) {
		this.checkConnect();
		
		AdminDTO dto = null;

        try {
        	String query = "SELECT idx, admin_id, admin_name "
        				+ "from tb_admin "
        				+ "where admin_id = ? and admin_pwd = ? and use_yn = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, userId);
            psmt.setString(2, userPwd);
            psmt.setString(3, "Y");
            rs = psmt.executeQuery();

            if(rs.next()) {
            	dto = new AdminDTO();
            	dto.setIdx(rs.getInt("idx"));
            	dto.setAdminId(rs.getString("admin_id"));
            	dto.setAdminName(rs.getString("admin_name"));
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
