package admin.board;

import java.util.ArrayList;
import java.util.List;

import board.BoardDTO;
import board.BoardReplyDTO;
import common.JDBConnect;

public class AdminBoardDAO extends JDBConnect {
	private static AdminBoardDAO instance;

	private AdminBoardDAO() {
        super();
    }
	
	public static AdminBoardDAO getInstance() {
		if (instance == null)
			instance = new AdminBoardDAO();
		return instance;
	}
	
	public List<BoardDTO> selectBoardList(int boardTypeCd) {
		this.checkConnect();
		
		List<BoardDTO> boardDTOList = new ArrayList<>();
		
		try {
			String query = "SELECT idx, title, content, reg_date, use_yn "
						+ "FROM tb_board "
						+ "WHERE board_type_cd = ? "
						+ "ORDER BY idx DESC";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, boardTypeCd);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setIdx(rs.getInt("idx"));
				boardDTO.setTitle(rs.getString("title"));
				boardDTO.setContent(rs.getString("content"));
				boardDTO.setReg_date(rs.getTimestamp("reg_date"));
				boardDTO.setUse_yn(rs.getString("use_yn"));
				boardDTOList.add(boardDTO);
			}
		} catch (Exception e) {
			boardDTOList = null;
			e.printStackTrace();
		} finally {
			this.close();
		}
		
		return boardDTOList;
	}
	
	public int deleteBoardData(BoardDTO boardDTO) {
		this.checkConnect();
		
		int result = 0;
		
		try {
			String query = "UPDATE tb_board "
					+ "SET use_yn = ?, mod_idx = ?, mod_date = NOW() "
					+ "WHERE idx = ?";
		psmt = con.prepareStatement(query);
		psmt.setString(1, "N");
		psmt.setInt(2, boardDTO.getMod_idx());
		psmt.setInt(3, boardDTO.getIdx());
			result = psmt.executeUpdate();
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		} finally {
			this.close();
		}
		
		return result;
	}
	
	public List<BoardReplyDTO> selectBoardReplyList(int boardTypeCd) {
		this.checkConnect();
		
		List<BoardReplyDTO> boardReplyDTOList = new ArrayList<>();
		
		try {
			String query = "SELECT tir.idx, tir.content, tu.user_id, tir.use_yn, tir.reg_date "
						+ "FROM tb_board_reply tir "
						+ "INNER JOIN tb_board ti ON tir.board_idx = ti.idx AND ti.board_type_cd = ? "
						+ "LEFT JOIN tb_user tu ON tir.reg_idx = tu.idx "
						+ "ORDER BY tir.idx DESC";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, boardTypeCd);
			rs = psmt.executeQuery();
				
			while(rs.next()) {
				BoardReplyDTO boardReplyDTO = new BoardReplyDTO();
				boardReplyDTO.setIdx(rs.getInt("idx"));
				boardReplyDTO.setContent(rs.getString("content"));
				boardReplyDTO.setUserId(rs.getString("user_id"));
				boardReplyDTO.setUseYn(rs.getString("use_yn"));
				/* boardReplyDTO.setReg_date(rs.getDate("reg_date")); */
				boardReplyDTOList.add(boardReplyDTO);
			}
		} catch (Exception e) {
			boardReplyDTOList = null;
			e.printStackTrace();
		} finally {
			this.close();
		}
		
		return boardReplyDTOList;
	}
	
	public int deleteBoardReplyData(BoardReplyDTO boardReplyDTO) {
		this.checkConnect();
		
		int result = 0;
		
		try {
			String query = "UPDATE tb_board_reply "
						+ "SET use_yn = ?, mod_idx = ?, mod_date = NOW() "
						+ "WHERE idx = ?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, "N");
			psmt.setInt(2, boardReplyDTO.getModIdx());
			psmt.setInt(3, boardReplyDTO.getIdx());
			result = psmt.executeUpdate();
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		} finally {
			this.close();
		}
		
		return result;
	}
}
