package main;

import java.util.ArrayList;
import java.util.List;

import board.BoardDTO;
import bongsa.BongsaDAO;
import bongsa.BongsaDTO;
import common.JDBConnect;

public class MainDAO extends JDBConnect {

	private static MainDAO instance;
	
	private MainDAO() {
        super();
    }
	
	public static MainDAO getInstance() {
		if (instance == null) {
			instance = new MainDAO();
		}
		
		return instance;
	}
	
	public List<BoardDTO> selectMainBoardList(int boardTypeCd) {
		this.checkConnect();
		
		List<BoardDTO> boardDTOList = null;
		
		try {
			String query = "select idx, title, board_type_cd, reg_date "
						+ "from tb_board "
						+ "where board_type_cd = ? and use_yn = ? "
						+ "order by idx desc "
						+ "limit 3";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, boardTypeCd);
			psmt.setString(2, "Y");
			rs = psmt.executeQuery();
			
			boardDTOList = new ArrayList<BoardDTO>();
			
			while(rs.next()) {
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setIdx(rs.getInt("idx"));
				boardDTO.setTitle(rs.getString("title"));
				boardDTO.setBoard_type_cd(rs.getString("board_type_cd"));
				boardDTO.setReg_date(rs.getTimestamp("reg_date"));
				boardDTOList.add(boardDTO);
			}
		}
		catch (Exception e) {
			boardDTOList = null;
			e.printStackTrace();
		}
		finally {
			this.close();
		}
		
		return boardDTOList;
	}
}
