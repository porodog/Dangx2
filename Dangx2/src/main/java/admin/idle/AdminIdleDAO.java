package admin.idle;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.JDBConnect;
import idle.IdleDTO;
import idle.IdleReplyDTO;

public class AdminIdleDAO extends JDBConnect {
	private static AdminIdleDAO instance;

	private AdminIdleDAO() {
        super();
    }
	
	public static AdminIdleDAO getInstance() {
		if (instance == null)
			instance = new AdminIdleDAO();
		return instance;
	}
	
	
	public List<IdleDTO> selectIdleList(int idleTypeCd) {
		this.checkConnect();
		
		List<IdleDTO> idleDTOList = null;
		
		try {
			String query = "select idx, idle_name, idle_year, use_yn, reg_date "
						+ "from tb_idle "
						+ "where idle_type_cd = ? "
						+ "order by idx desc";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idleTypeCd);
			rs = psmt.executeQuery();
			
			idleDTOList = new ArrayList<IdleDTO>();
			
			while(rs.next()) {
				IdleDTO idleDTO = new IdleDTO();
				idleDTO.setIdx(rs.getInt("idx"));
				idleDTO.setIdleName(rs.getString("idle_name"));
				idleDTO.setIdleYear(rs.getString("idle_year"));
				idleDTO.setUseYn(rs.getString("use_yn"));
				idleDTO.setRegDate(rs.getDate("reg_date"));
				idleDTOList.add(idleDTO);
			}
		}
		catch (Exception e) {
			idleDTOList = null;
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return idleDTOList;
    }
	
	public int insertIdleData(IdleDTO idleDTO) {
		this.checkConnect();
		
		int result = 0;
		
		try {
			String query = "insert into tb_idle"
						+ "(idle_name, idle_breed, idle_year, idle_gender_cd, idle_neuter_yn, "
						+ "idle_kg, idle_current_content, idle_type_cd, reg_idx, mod_idx) values "
						+ "(?, ?, ?, ?, ?, "
						+ "?, ?, ?, ?, ?)";
			psmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			psmt.setString(1, idleDTO.getIdleName());
			psmt.setString(2, idleDTO.getIdleBreed());
			psmt.setString(3, idleDTO.getIdleYear());
			psmt.setString(4, idleDTO.getIdleGenderCd());
			psmt.setString(5, idleDTO.getIdleNeuterYn());
			psmt.setString(6, idleDTO.getIdleKg());
			psmt.setString(7, idleDTO.getIdleCurrentContent());
			psmt.setInt(8, idleDTO.getIdleTypeCd());
			psmt.setInt(9, idleDTO.getRegIdx());
			psmt.setInt(10, idleDTO.getModIdx());		
			psmt.executeUpdate();
			
			rs = psmt.getGeneratedKeys();
            if(rs.next()) {
            	result = rs.getInt(1);
            }
		}
		catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return result;
	}
	
	public int deleteIdleData(IdleDTO idleDTO) {
		this.checkConnect();
		
		int result = 0;
		
		try {
			String query = "update tb_idle "
						+ "set use_yn = ?, mod_idx = ?, mod_date = now() "
						+ "where idx = ?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, "N");
			psmt.setInt(2, idleDTO.getModIdx());
			psmt.setInt(3, idleDTO.getIdx());
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return result;
	}
	
	public List<IdleReplyDTO> selectIdleReplyList(int idleTypeCd) {
		this.checkConnect();
		
		List<IdleReplyDTO> idleReplyDTOList = null;
		
		try {
			String query = "select tir.idx, tir.content, tu.user_id, tir.use_yn, tir.reg_date "
						+ "from tb_idle_reply tir "
						+ "inner join tb_idle ti "
						+ "	on tir.idle_idx = ti.idx and ti.idle_type_cd = ? "
						+ "left outer join  tb_user tu "
						+ "	on tir.reg_idx = tu.idx "
						+ "order by tir.idx desc";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idleTypeCd);
			rs = psmt.executeQuery();
			
			idleReplyDTOList = new ArrayList<IdleReplyDTO>();
			
			while(rs.next()) {
				IdleReplyDTO idleReplyDTO = new IdleReplyDTO();
				idleReplyDTO.setIdx(rs.getInt("idx"));
				idleReplyDTO.setContent(rs.getString("content"));
				idleReplyDTO.setUserId(rs.getString("user_id"));
				idleReplyDTO.setUseYn(rs.getString("use_yn"));
				idleReplyDTO.setRegDate(rs.getDate("reg_date"));
				idleReplyDTOList.add(idleReplyDTO);
			}
		}
		catch (Exception e) {
			idleReplyDTOList = null;
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return idleReplyDTOList;
    }
	
	public int deleteIdleReplyData(IdleReplyDTO idleReplyDTO) {
		this.checkConnect();
		
		int result = 0;
		
		try {
			String query = "update tb_idle_reply "
						+ "set use_yn = ?, mod_idx = ?, mod_date = now() "
						+ "where idx = ?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, "N");
			psmt.setInt(2, idleReplyDTO.getModIdx());
			psmt.setInt(3, idleReplyDTO.getIdx());
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return result;
	}
}
