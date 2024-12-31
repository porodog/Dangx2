package common.file;

import java.util.ArrayList;
import java.util.List;

import common.JDBConnect;

public class FileDAO extends JDBConnect {
	private static FileDAO instance;
	
	private FileDAO() {
        super();
    }
	
	public static FileDAO getInstance() {
		if (instance == null) {
			instance = new FileDAO();
		}
		
		return instance;
	}
	
	public int insertFile(FileDTO fileDTO) {
		this.checkConnect();
		
		int result = 0;
        
        try {
        	String query = "insert into tb_file"
	        			+ "(save_name, org_name, path, file_order, target_type_cd, target_idx, reg_idx, mod_idx)"
	        			+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
            psmt = con.prepareStatement(query);
            psmt.setString(1, fileDTO.getSaveName());
            psmt.setString(2, fileDTO.getOrgName());
            psmt.setString(3, fileDTO.getPath());
            psmt.setInt(4, fileDTO.getFileOrder());
            psmt.setInt(5, fileDTO.getTargetTypeCd());
            psmt.setInt(6, fileDTO.getTargetIdx());
            psmt.setInt(7, fileDTO.getRegIdx());
            psmt.setInt(8, fileDTO.getModIdx());
            
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
	
	public List<FileDTO> selectFileList(int targetTypeCd, int targetIdx) {
		this.checkConnect();
		List<FileDTO> fileDTOList = null;
		
		try {
        	String query = "select idx, save_name, org_name, path "
	        			+ "from tb_file "
	        			+ "where target_type_cd = ? "
	        			+ "and target_idx = ? "
	        			+ "and use_yn = ? "
	        			+ "order by idx ASC";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, targetTypeCd);
            psmt.setInt(2, targetIdx);
            psmt.setString(3, "Y");
            
            rs = psmt.executeQuery();
            fileDTOList = new ArrayList<FileDTO>();
            while(rs.next()) {
            	FileDTO fileDTO = new FileDTO();
            	fileDTO.setIdx(rs.getInt(1));
            	fileDTO.setSaveName(rs.getString(2));
            	fileDTO.setOrgName(rs.getString(3));
            	fileDTO.setPath(rs.getString(4));
            	fileDTOList.add(fileDTO);
            }
		}
        catch (Exception e) {
        	fileDTOList = null;
            e.printStackTrace();
        }
        finally {
			this.close();
		}
		
		return fileDTOList;
	}
	
	public FileDTO selectFileData(int fileIdx) {
		this.checkConnect();
		FileDTO fileDTO = null;
		
		try {
        	String query = "select idx, save_name, org_name, path "
	        			+ "from tb_file "
	        			+ "where idx = ? "
	        			+ "and use_yn = ? ";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, fileIdx);
            psmt.setString(2, "Y");
            
            rs = psmt.executeQuery();
            
            if(rs.next()) {
	        	fileDTO = new FileDTO();
	        	fileDTO.setIdx(rs.getInt(1));
	        	fileDTO.setSaveName(rs.getString(2));
	        	fileDTO.setOrgName(rs.getString(3));
	        	fileDTO.setPath(rs.getString(4));
            }
		}
        catch (Exception e) {
        	fileDTO = null;
            e.printStackTrace();
        }
        finally {
			this.close();
		}
		
		return fileDTO;
	}
	
	public int deleteFileByFileIdx(FileDTO fileDTO) {
		// 파일 삭제 시
		// fileDTO << modIdx, idx
		this.checkConnect();
		int result = 0;
		
		try {
        	String query = "update tb_file "
	        			+ "set use_yn = ?, mod_idx = ?, mod_date = now() "
	        			+ "where idx = ? and use_yn = 'Y'";
            psmt = con.prepareStatement(query);
            psmt.setString(1, "N");
            psmt.setInt(2, fileDTO.getModIdx());
            psmt.setInt(3, fileDTO.getIdx());
            
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
	
	public int deleteFileByTargetIdx(FileDTO fileDTO) {
		// 글 삭제 시
		// fileDTO << targetTypeCd, targetIdx
		this.checkConnect();
		int result = 0;
		
		try {
        	String query = "update tb_file "
	        			+ "set use_yn = ?, mod_idx = ?, mod_date = now() "
	        			+ "where target_type_cd = ? and target_idx = ? and use_yn = 'Y'";
            psmt = con.prepareStatement(query);
            psmt.setString(1, "N");
            psmt.setInt(2, fileDTO.getModIdx());
            psmt.setInt(3, fileDTO.getTargetTypeCd());
            psmt.setInt(4, fileDTO.getTargetIdx());
            
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
	
	// use_yn 값을 업데이트하는 메서드 추가
    public int updateFileStatus(FileDTO fileDTO) {
        this.checkConnect();
        int result = 0;

        try {
            String query = "update tb_file "
                    + "set use_yn = ?, mod_idx = ?, mod_date = now() "
                    + "where idx = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, fileDTO.getUseYn());
            psmt.setInt(2, fileDTO.getModIdx());
            psmt.setInt(3, fileDTO.getIdx());

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
