package admin.bongsa;

import java.util.ArrayList;
import java.util.List;

import bongsa.BongsaDTO;
import bongsa.BongsaInfoDTO;
import common.JDBConnect;

public class AdminBongsaDAO extends JDBConnect {
	private static AdminBongsaDAO instance;

	private AdminBongsaDAO() {
        super();
    }
	
	public static AdminBongsaDAO getInstance() {
		if (instance == null)
			instance = new AdminBongsaDAO();
		return instance;
	}
	
	
	public List<BongsaDTO> selectBongsaList() {
		this.checkConnect();
		
		List<BongsaDTO> bongsaDTOList = null;
		
		try {
			String query = "select tb.idx, date_format(tb.bongsa_date, '%Y-%m-%d') as bongsa_date, tu.user_id, "
						+ "(select count(*) from tb_bongsa_info tbi where tbi.bongsa_idx = tb.idx) as bongsa_count, "
						+ "tb.use_yn, tb.reg_date "
						+ "from tb_bongsa tb "
						+ "inner join tb_user tu on tb.reg_idx = tu.idx";
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();
			
			bongsaDTOList = new ArrayList<BongsaDTO>();
			
			while(rs.next()) {
				BongsaDTO bongsaDTO = new BongsaDTO();
				bongsaDTO.setIdx(rs.getInt("idx"));
				bongsaDTO.setBongsaDate(rs.getString("bongsa_date"));
				bongsaDTO.setUserId(rs.getString("user_id"));
				bongsaDTO.setBongsaCount(rs.getInt("bongsa_count"));
				bongsaDTO.setUseYn(rs.getString("use_yn"));
				bongsaDTO.setRegDate(rs.getDate("reg_date"));
				bongsaDTOList.add(bongsaDTO);
			}
		}
		catch (Exception e) {
			bongsaDTOList = null;
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return bongsaDTOList;
    }
	
	public BongsaDTO selectBongsaData(int bongsaIdx) {
		this.checkConnect();
		
		BongsaDTO bongsaDTO = null;
		
		try {
			String query = "select tb.idx, date_format(tb.bongsa_date, '%Y-%m-%d') as bongsa_date, "
						+ "tu.user_id, tb.use_yn, tb.reg_date, tu.user_phone "
						+ "from tb_bongsa tb "
						+ "inner join tb_user tu on tb.reg_idx = tu.idx "
						+ "where tb.idx = ?";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, bongsaIdx);
			rs = psmt.executeQuery();
			
			bongsaDTO = new BongsaDTO();
			
			if(rs.next()) {
				bongsaDTO.setIdx(rs.getInt("idx"));
				bongsaDTO.setBongsaDate(rs.getString("bongsa_date"));
				bongsaDTO.setUserId(rs.getString("user_id"));
				bongsaDTO.setUseYn(rs.getString("use_yn"));
				bongsaDTO.setRegDate(rs.getDate("reg_date"));
				bongsaDTO.setUserPhone(rs.getString("user_phone"));
			}
		}
		catch (Exception e) {
			bongsaDTO = null;
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return bongsaDTO;
	}
	
	public List<BongsaInfoDTO> selectBongsaInfoData(int bongsaIdx) {
		this.checkConnect();
		
		List<BongsaInfoDTO> BongsaInfoDTOList = null;
		
		try {
			String query = "select bongsa_name, bongsa_phone "
						+ "from tb_bongsa_info "
						+ "where bongsa_idx = ? "
						+ "order by bongsa_order ASC";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, bongsaIdx);
			rs = psmt.executeQuery();
			
			BongsaInfoDTOList = new ArrayList<BongsaInfoDTO>();
			
			if(rs.next()) {
				BongsaInfoDTO dto = new BongsaInfoDTO();
				dto.setBongsaName(rs.getString(1));
				dto.setBongsaPhone(rs.getString(2));
				BongsaInfoDTOList.add(dto);
			}
		}
		catch (Exception e) {
			BongsaInfoDTOList = null;
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return BongsaInfoDTOList;
	}
}
