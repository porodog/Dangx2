package idle;

import java.util.ArrayList;
import java.util.List;

import common.JDBConnect;

public class IdleDAO extends JDBConnect {

	private static IdleDAO instance;
	
	private IdleDAO() {
        super();
    }
	
	public static IdleDAO getInstance() {
		if (instance == null) {
			instance = new IdleDAO();
		}
		
		return instance;
	}
	
	public List<IdleDTO> selectIdleList(IdleDTO idleDTO) {
		this.checkConnect();
		
		List<IdleDTO> idleDTOList = null;
		
		try {
			String query = "select ti.idx, ti.idle_name, ti.idle_breed, "
						+ "			ti.idle_year, concat(tf.path, tf.save_name) as image_path "
						+ "from tb_idle ti "
						+ "left outer join tb_file tf "
						+ "	on tf.target_type_cd = ? and tf.target_idx = ti.idx and tf.file_order = 1 "
						+ "where ti.idle_type_cd = ? and ti.use_yn = ? "
						+ "order by ti.idx DESC "
						+ "limit ? offset ?";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idleDTO.getTargetTypeCd()); //tb_file type
			psmt.setInt(2, idleDTO.getIdleTypeCd()); // tb_idle type
			psmt.setString(3, "Y");
			psmt.setInt(4, idleDTO.getCntPerPage());
			psmt.setInt(5, idleDTO.getStartIndex());
			rs = psmt.executeQuery();
			
			idleDTOList = new ArrayList<IdleDTO>();
			
			while(rs.next()) {
				IdleDTO dto = new IdleDTO();
				dto.setIdx(rs.getInt(1));
				dto.setIdleName(rs.getString(2));
				dto.setIdleBreed(rs.getString(3));
				dto.setIdleYear(rs.getString(4));
				dto.setImagePath(rs.getString(5));
				idleDTOList.add(dto);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return idleDTOList;
	}
	
	public int selectIdleListCount(IdleDTO idleDTO) {
		this.checkConnect();
		
		int result = 0;
        
        try {
        	String query = "select count(*) "
						+ "from tb_idle ti "
						+ "where ti.idle_type_cd = ? and ti.use_yn = ?";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, idleDTO.getIdleTypeCd());
			psmt.setString(2, "Y");
            rs = psmt.executeQuery();
            
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
	
	public IdleDTO selectIdleData(IdleDTO idleDTO) {
		this.checkConnect();
		
		IdleDTO result = null;
		
		try {
        	String query = "select idx, "
        				+ " idle_name, "
	        			+ "	idle_breed, "
	        			+ "	idle_year, "
	        			+ "	(select tcc.cd_name from tb_common_code tcc "
	        			+ "  where tcc.group_cd = 'idle_gender_cd' and tcc.cd = ti.idle_gender_cd and tcc.use_yn = 'Y'"
	        			+ "  ) as idle_gender_name, "
	        			+ "	idle_neuter_yn, "
	        			+ "	idle_kg, "
	        			+ "	idle_rescue_content, "
	        			+ "	idle_current_content, "
	        			+ " idle_type_cd "
	        			+ "from tb_idle ti "
	        			+ "where idx = ? "
	        			+ "and idle_type_cd = ? "
	        			+ "and use_yn = ?";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, idleDTO.getIdx());
            psmt.setInt(2, idleDTO.getIdleTypeCd());
			psmt.setString(3, "Y");
            rs = psmt.executeQuery();
            
            result = new IdleDTO();
            if(rs.next()) {
            	result.setIdx(rs.getInt(1));
            	result.setIdleName(rs.getString(2));
            	result.setIdleBreed(rs.getString(3));
            	result.setIdleYear(rs.getString(4));
            	result.setIdleGenderName(rs.getString(5));
            	result.setIdleNeuterYn(rs.getString(6));
            	result.setIdleKg(rs.getString(7));
            	result.setIdleRescueContent(rs.getString(8));
            	result.setIdleCurrentContent(rs.getString(9));
            	result.setIdleTypeCd(rs.getInt(10));
            }
		}
        catch (Exception e) {
        	result = null;
            e.printStackTrace();
        }
        finally {
			this.close();
		}
		
		return result;
	}
	
	public List<IdleReplyDTO> selectIdleReplyList(int idleIdx) {
		this.checkConnect();
		
		List<IdleReplyDTO> idleReplyDTOList = null;
		
		try {
        	String query = "select tir.idx, tir.content, tir.reg_idx, tir.reg_date, tu.user_id, date_format(tir.reg_date, '%y.%m.%d %H:%i') as reg_dt "
	        			+ "from tb_idle_reply tir "
	        			+ "inner join tb_user tu "
	        			+ "on tir.reg_idx = tu.idx and tu.use_yn = 'Y' "
	        			+ "where tir.idle_idx = ? "
	        			+ "and tir.use_yn = ? "
	        			+ "order by tir.idx DESC";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, idleIdx);
			psmt.setString(2, "Y");
            rs = psmt.executeQuery();
            idleReplyDTOList = new ArrayList<IdleReplyDTO>();
            while(rs.next()) {
            	IdleReplyDTO idleReplyDTO = new IdleReplyDTO();
            	idleReplyDTO.setIdx(rs.getInt(1));
            	idleReplyDTO.setContent(rs.getString(2));
            	idleReplyDTO.setRegIdx(rs.getInt(3));
            	idleReplyDTO.setRegDate(rs.getDate(4));
            	idleReplyDTO.setUserId(rs.getString(5));
            	idleReplyDTO.setRegDt(rs.getString(6));
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
	
	public int deleteIdleReply(IdleReplyDTO idleReplyDTO) {
		this.checkConnect();
		
		int result = 0;
		
		try {
        	String query = "update tb_idle_reply "
	        			+ "set use_yn = ?, mod_idx = ?, mod_date = now() "
	        			+ "where idx = ? and reg_idx = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, "N");
            psmt.setInt(2, idleReplyDTO.getModIdx());
            psmt.setInt(3, idleReplyDTO.getIdx());
			psmt.setInt(4, idleReplyDTO.getRegIdx());
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
	
	public int updateIdleReply(IdleReplyDTO idleReplyDTO) {
		this.checkConnect();
		
		int result = 0;
		
		try {
        	String query = "update tb_idle_reply "
	        			+ "set content = ?, mod_idx = ?, mod_date = now() "
	        			+ "where idx = ? and reg_idx = ? and use_yn = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, idleReplyDTO.getContent());
            psmt.setInt(2, idleReplyDTO.getModIdx());
			psmt.setInt(3, idleReplyDTO.getIdx());
			psmt.setInt(4, idleReplyDTO.getRegIdx());
			psmt.setString(5, "Y");
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
	
	public int insertIdleReply(IdleReplyDTO idleReplyDTO) {
		this.checkConnect();
		
		int result = 0;
		
		try {
        	String query = "insert into tb_idle_reply (idle_idx, content, reg_idx, mod_idx) "
        				+ "values (?, ?, ?, ?)";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, idleReplyDTO.getIdleIdx());
            psmt.setString(2, idleReplyDTO.getContent());
			psmt.setInt(3, idleReplyDTO.getRegIdx());
			psmt.setInt(4, idleReplyDTO.getModIdx());
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
