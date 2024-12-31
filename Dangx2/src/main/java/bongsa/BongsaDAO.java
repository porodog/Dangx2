package bongsa;

import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.JDBConnect;

public class BongsaDAO extends JDBConnect {

	private static BongsaDAO instance;
	
	private BongsaDAO() {
        super();
    }
	
	public static BongsaDAO getInstance() {
		if (instance == null) {
			instance = new BongsaDAO();
		}
		
		return instance;
	}
	
	public List<BongsaDTO> selectBongsaList(int userIdx) {
		this.checkConnect();
		
		List<BongsaDTO> bongsaDTOList = null;
		
		try {
			String query = "select 	idx "
						+ "			, date_format(bongsa_date, '%Y-%m-%d') as bongsa_date "
						+ "			, (select count(*) from tb_bongsa_info tbi where tbi.bongsa_idx=tb.idx)+1 as bongsa_count "
						+ "from tb_bongsa tb "
						+ "where reg_idx = ? "
						+ "and (date_format(date_sub(now(), interval 2 month), '%Y%m')<=date_format(reg_date, '%Y%m') "
						+ "		or date_format(reg_date, '%Y%m')<=date_format(date_add(now(), interval 1 month), '%Y%m'))"
						+ "and use_yn = ?";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, userIdx);
			psmt.setString(2, "Y");
			rs = psmt.executeQuery();
			
			bongsaDTOList = new ArrayList<BongsaDTO>();
			
			while(rs.next()) {
				BongsaDTO bongsaDTO = new BongsaDTO();
				bongsaDTO.setIdx(rs.getInt(1));
				bongsaDTO.setBongsaDate(rs.getString(2));
				bongsaDTO.setBongsaCount(rs.getInt(3));
				bongsaDTOList.add(bongsaDTO);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return bongsaDTOList;
	}
	
	public BongsaDTO selectBongsaData(int bongsaIdx, int userIdx) {
		this.checkConnect();
		
		BongsaDTO bongsaDTO = null;
		
		try {
			String query = "select idx, "
						+ "		   date_format(bongsa_date, '%Y-%m-%d') as bongsa_date "
						+ "from tb_bongsa "
						+ "where idx = ? and reg_idx = ? and use_yn = ?";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, bongsaIdx);
			psmt.setInt(2, userIdx);
			psmt.setString(3, "Y");
			
			rs = psmt.executeQuery();
			
			bongsaDTO = new BongsaDTO();
			
			if(rs.next()) {
				bongsaDTO.setIdx(rs.getInt(1));
				bongsaDTO.setBongsaDate(rs.getString(2));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return bongsaDTO;
	}
	
	public List<BongsaInfoDTO> selectBongsaInfoData(int bongsaIdx) {
		this.checkConnect();
		
		List<BongsaInfoDTO> bongsaInfoDTOList = null;
		
		try {
			String query = "select bongsa_name, "
						+ "			bongsa_phone "
						+ "from tb_bongsa_info "
						+ "where bongsa_idx = ? "
						+ "order by bongsa_order ASC";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, bongsaIdx);
			rs = psmt.executeQuery();
			
			bongsaInfoDTOList = new ArrayList<BongsaInfoDTO>();
			
			while(rs.next()) {
				BongsaInfoDTO bongsaInfoDTO = new BongsaInfoDTO();
				bongsaInfoDTO.setBongsaName(rs.getString(1));
				bongsaInfoDTO.setBongsaPhone(rs.getString(2));
				bongsaInfoDTOList.add(bongsaInfoDTO);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return bongsaInfoDTOList;
	}
	
	public int selectBongsaDataCountByDate(String bongsaDate, int userIdx) {
		this.checkConnect();
		
		int result = 0;
        
        try {
        	String query = "select count(*) from tb_bongsa "
        				+ "where reg_idx = ? and bongsa_date = ? and use_yn = ?";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, userIdx);
            psmt.setDate(2, Date.valueOf(bongsaDate));
            psmt.setString(3, "Y");
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
	
	public int selectBongsaDataCountByIdx(int bongsaIdx, int userIdx) {
		this.checkConnect();
		
		int result = 0;
        
        try {
        	String query = "select count(*) from tb_bongsa "
        				+ "where idx = ? and reg_idx = ? and use_yn = ?";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, bongsaIdx);
            psmt.setInt(2, userIdx);
            psmt.setString(3, "Y");
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
	
	public int insertBongsa(BongsaDTO bongsaDTO) {
		this.checkConnect();
		
        int bongsaIdx = 0;
        
        try {
        	// tb_bongsa
        	// insert 후 key값 조회
        	String query = "insert into tb_bongsa(bongsa_date, reg_idx, mod_idx) values (?, ?, ?)";
            psmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            psmt.setDate(1, Date.valueOf(bongsaDTO.getBongsaDate()));
            psmt.setInt(2, bongsaDTO.getRegIdx());
            psmt.setInt(3, bongsaDTO.getModIdx());
            psmt.executeUpdate();
            
            rs = psmt.getGeneratedKeys();
            if(rs.next()) {
            	bongsaIdx = rs.getInt(1);
            }
		}
        catch (Exception e) {
        	bongsaIdx = -1;
            e.printStackTrace();
        }
        finally {
			this.close();
		}
        
        return bongsaIdx;
    }
	
	public int insertBongsaInfo(BongsaInfoDTO bongsaInfoDTO) {
		this.checkConnect();
		
		int result = 0;
		
		try {
        	// tb_bongsa_info
        	String query = "insert into tb_bongsa_info(bongsa_idx, bongsa_name, bongsa_phone, bongsa_order) "
        				+ "values (?, ?, ?, ?)";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, bongsaInfoDTO.getBongsaIdx());
            psmt.setString(2, bongsaInfoDTO.getBongsaName());
            psmt.setString(3, bongsaInfoDTO.getBongsaPhone());
            psmt.setInt(4, bongsaInfoDTO.getBongsaOrder());
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
	
	public int updateBongsa(int bongsaIdx, int userIdx) {
		this.checkConnect();
		
		int result = 0;
		
		try {
        	String query = "update tb_bongsa "
	        			+ "set mod_idx = ?, mod_date = now() "
	        			+ "where idx = ? and reg_idx = ? and use_yn = ?";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, userIdx);
            psmt.setInt(2, bongsaIdx);
            psmt.setInt(3, userIdx);
            psmt.setString(4, "Y");
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
	
	public int deleteBongsa(int bongsaIdx, int userIdx) {
		this.checkConnect();
		
		int result = 0;
		
		try {
//        	String query = "delete from tb_bongsa "
//        				+ "where idx = ? and reg_idx = ?";
			String query = "update tb_bongsa "
					+ "set use_yn = ?, mod_idx = ?, mod_date = now() "
    				+ "where idx = ? and reg_idx = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, "N");
            psmt.setInt(2, userIdx);
            psmt.setInt(3, bongsaIdx);
            psmt.setInt(4, userIdx);
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
	
	public int deleteBongsaInfo(int bongsaIdx) {
		this.checkConnect();
		
		int result = 0;
		
		try {
        	String query = "delete from tb_bongsa_info "
        				+ "where bongsa_idx = ?";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, bongsaIdx);
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
