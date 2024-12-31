package board;

import common.JDBConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO extends JDBConnect {

    private static BoardDAO instance;

    BoardDAO() {
        super(); // JDBConnect의 생성자를 호출하여 연결 초기화
    }

    public static BoardDAO getInstance() {
        if (instance == null) {
            instance = new BoardDAO();
        }
        return instance;
    }

 // 게시글 조회 (idx)
    public BoardDTO getBoardByIdx(int idx) {
        BoardDTO boardDTO = null;
        String sql = "SELECT tb.idx, tb.title, tb.content, tb.board_type_cd, tb.reg_date, tu.user_id, tb.reg_idx "
        			+ "FROM dangx2.tb_board tb "
        			+ "left outer join tb_user tu "
        			+ "on tb.reg_idx = tu.idx "
        			+ "WHERE tb.idx = ? AND tb.use_yn = 'Y'";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idx);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    boardDTO = new BoardDTO();
                    boardDTO.setIdx(rs.getInt("idx"));
                    boardDTO.setTitle(rs.getString("title"));
                    boardDTO.setContent(rs.getString("content"));
                    boardDTO.setBoard_type_cd(rs.getString("board_type_cd"));
					/* boardDTO.setUser_id(rs.getString("user_id")); */
					/* boardDTO.setHit(rs.getInt("hit")); */
                    boardDTO.setReg_date(rs.getTimestamp("reg_date"));
					/* boardDTO.setMod_date(rs.getTimestamp("mod_date")); */
                    boardDTO.setUser_id(rs.getString("user_id"));
                    boardDTO.setReg_idx(rs.getInt("reg_idx"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 더 구체적인 에러 메시지를 출력할 수 있습니다.
        }

        return boardDTO;
    }
    
    // 게시판 리스트 정보 가져오기
    public List<BoardDTO> getBoards(BoardDTO boardDTO) {
        this.checkConnect(); // 연결 상태를 확인

        List<BoardDTO> boards = new ArrayList<>();
        String sql = "SELECT "
        			+ "tb.idx, tb.title, tu.user_id, tb.board_type_cd, tb.reg_date "
        			+ "FROM dangx2.tb_board tb "
        			+ "inner join tb_user tu "
        			+ "on tb.reg_idx = tu.idx "
        			+ "WHERE tb.board_type_cd = ? AND tb.use_yn = 'Y' "
        			+ "AND "+ boardDTO.getSearchKey() + " LIKE ? "
        			+ "ORDER BY tb.idx DESC "
        			+ "LIMIT ? offset ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, boardDTO.getBoard_type_cd());  // board_type_cd 추가
            pstmt.setString(2, "%"+boardDTO.getSearchValue()+"%");
            pstmt.setInt(3, boardDTO.getCntPerPage());
            pstmt.setInt(4, boardDTO.getStartIndex());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BoardDTO board = new BoardDTO();
                    board.setIdx(rs.getInt("idx"));
                    board.setTitle(rs.getString("title"));
					board.setUser_id(rs.getString("user_id"));
                    board.setBoard_type_cd(rs.getString("board_type_cd"));
                    board.setReg_date(rs.getTimestamp("reg_date"));
                    boards.add(board);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }


    // 게시글 수 받아오기
    public int getTotalBoardsCount(BoardDTO boardDTO) {
        int count = 0;
        String sql = "SELECT COUNT(*) "
        		+ "FROM dangx2.tb_board tb "
        		+ "inner join tb_user tu "
        		+ "on tb.reg_idx = tu.idx "
        		+ "WHERE tb.board_type_cd = ? AND tb.use_yn = 'Y' "
        		+ "AND "+ boardDTO.getSearchKey() + " LIKE ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, boardDTO.getBoard_type_cd());  // board_type_cd 추가
            pstmt.setString(2, "%"+boardDTO.getSearchValue()+"%");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // 글쓰기
    public int insertBoard(BoardDTO boardDTO) {
        String sql = "INSERT INTO tb_board (title, content, board_type_cd, reg_idx, mod_idx) VALUES (?, ?, ?, ?, ?)";

        int boardIdx = 0;
        
        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, boardDTO.getTitle());
            pstmt.setString(2, boardDTO.getContent());
            pstmt.setString(3, boardDTO.getBoard_type_cd());
            pstmt.setInt(4, boardDTO.getReg_idx());
            pstmt.setInt(5, boardDTO.getMod_idx());
            pstmt.executeUpdate();
            
            rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
            	boardIdx = rs.getInt(1);
            }
            System.out.println("게시글이 성공적으로 등록되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return boardIdx;
    }
    
    //글 수정하기
    public boolean updateBoard(BoardDTO boardDTO) {
        // tb_board 테이블 수정 쿼리
        String updateBoardSql = "UPDATE tb_board SET title = ?, content = ?, board_type_cd = ?, mod_date = now() WHERE idx = ?";
        // tb_file 테이블 수정 쿼리
        String updateFileSql = "UPDATE tb_file SET target_type_cd = ? WHERE target_idx = ?"; // target_idx는 tb_board의 idx와 같은 값을 가짐

        try {
            // 트랜잭션 시작
            con.setAutoCommit(false);

            // tb_board 업데이트
            try (PreparedStatement pstmt = con.prepareStatement(updateBoardSql)) {
                pstmt.setString(1, boardDTO.getTitle());
                pstmt.setString(2, boardDTO.getContent());
                pstmt.setString(3, boardDTO.getBoard_type_cd()); // 수정할 board_type_cd 값 설정
                pstmt.setInt(4, boardDTO.getIdx());
                pstmt.executeUpdate();
            }

            // tb_file 업데이트
            try (PreparedStatement pstmt = con.prepareStatement(updateFileSql)) {
                pstmt.setString(1, boardDTO.getBoard_type_cd()); // tb_board의 board_type_cd를 tb_file의 target_type_cd로 설정
                pstmt.setInt(2, boardDTO.getIdx()); // target_idx는 게시글의 idx로 설정
                pstmt.executeUpdate();
            }

            // 트랜잭션 커밋
            con.commit();
            return true; // 모든 업데이트가 성공적으로 완료됨
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // 트랜잭션 롤백
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                // 원래의 auto-commit 상태로 복원
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false; // 업데이트 실패
    }    
    //검색 3종
    public List<BoardDTO> searchBoards(String searchKey, String searchValue) {
        List<BoardDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_board WHERE " + searchKey + " LIKE ?";  

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchValue + "%");  // 검색 값 포함
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                BoardDTO dto = new BoardDTO();
                dto.setIdx(rs.getInt("idx"));
                dto.setTitle(rs.getString("title"));
                dto.setUser_id(rs.getString("user_id"));
                dto.setReg_date(rs.getTimestamp("reg_date"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //게시글 삭제 처리 use_yn을 Y에서 N으로
    public void deleteBoard(int idx) {
        String sql = "UPDATE tb_board "
        		+ "SET use_yn = 'N' "
        		+ "WHERE idx = ? ";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idx);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //첨부파일 삭제 처리
    public void deleteBoardFile(int target_idx) {
        String sql = "UPDATE tb_file "
        		+ "SET use_yn = 'N' "
        		+ "WHERE target_idx = ? ";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, target_idx);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public List<BoardReplyDTO> selectBoardReplyList(int boardIdx) {
    	List<BoardReplyDTO> replyDTOList = null;
    	try {
        	String query = "select tir.idx, tir.content, tir.reg_idx, tu.user_id, date_format(tir.reg_date, '%y.%m.%d %H:%i') as reg_dt "
	        			+ "from tb_board_reply tir "
	        			+ "inner join tb_user tu "
	        			+ "on tir.reg_idx = tu.idx and tu.use_yn = 'Y' "
	        			+ "where tir.board_idx = ? "
	        			+ "and tir.use_yn = ? "
	        			+ "order by tir.idx DESC";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, boardIdx);
			psmt.setString(2, "Y");
            rs = psmt.executeQuery();
            replyDTOList = new ArrayList<BoardReplyDTO>();
            while(rs.next()) {
            	BoardReplyDTO boardReplyDTO = new BoardReplyDTO();
            	boardReplyDTO.setIdx(rs.getInt(1));
            	boardReplyDTO.setContent(rs.getString(2));
            	boardReplyDTO.setRegIdx(rs.getInt(3));
            	boardReplyDTO.setUserId(rs.getString(4));
            	boardReplyDTO.setRegDt(rs.getString(5));
            	replyDTOList.add(boardReplyDTO);
            }
		}
        catch (Exception e) {
        	replyDTOList = null;
            e.printStackTrace();
        }
    	return replyDTOList;
    }
    
    
    public int insertBoardReply(BoardReplyDTO boardReplyDTO) {
    	int result = 0;
		
		try {
        	String query = "insert into tb_board_reply (board_idx, content, reg_idx, mod_idx) "
        				+ "values (?, ?, ?, ?)";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, boardReplyDTO.getBoardIdx());
            psmt.setString(2, boardReplyDTO.getContent());
			psmt.setInt(3, boardReplyDTO.getRegIdx());
			psmt.setInt(4, boardReplyDTO.getModIdx());
            result = psmt.executeUpdate();
		}
        catch (Exception e) {
        	result = -1;
            e.printStackTrace();
        }
//        finally {
//			this.close();
//		}
		
		return result;
    }
    
    public int updateBoardReply(BoardReplyDTO boardReplyDTO) {
		int result = 0;
		
		try {
        	String query = "update tb_board_reply "
	        			+ "set content = ?, mod_idx = ?, mod_date = now() "
	        			+ "where idx = ? and reg_idx = ? and use_yn = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, boardReplyDTO.getContent());
            psmt.setInt(2, boardReplyDTO.getModIdx());
			psmt.setInt(3, boardReplyDTO.getIdx());
			psmt.setInt(4, boardReplyDTO.getRegIdx());
			psmt.setString(5, "Y");
            result = psmt.executeUpdate();
		}
        catch (Exception e) {
        	result = -1;
            e.printStackTrace();
        }
//        finally {
//			this.close();
//		}
		
		return result;
	}
    
    public int deleteBoardReply(BoardReplyDTO boardReplyDTO) {
		int result = 0;
		
		try {
        	String query = "update tb_board_reply "
	        			+ "set use_yn = ?, mod_idx = ?, mod_date = now() "
	        			+ "where idx = ? and reg_idx = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, "N");
            psmt.setInt(2, boardReplyDTO.getModIdx());
            psmt.setInt(3, boardReplyDTO.getIdx());
			psmt.setInt(4, boardReplyDTO.getRegIdx());
            result = psmt.executeUpdate();
		}
        catch (Exception e) {
        	result = -1;
            e.printStackTrace();
        }
//        finally {
//			this.close();
//		}
		
		return result;
	}
    
}
