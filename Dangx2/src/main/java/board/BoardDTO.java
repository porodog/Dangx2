package board;

import java.sql.Timestamp;

public class BoardDTO {
    private int idx;               // 글번호
    private String title;          // 제목
    private String content;        // 내용
    private int file_idx;          // 첨부 파일 번호
    private int hit;               // 조회수
    private String board_type_cd;  // 게시판 유형 코드 (1쉼터 2입양 3후원)
    private String use_yn;         // 글 삭제 여부 Y/N
    private int reg_idx;           // 작성자 Id (DB상 reg_idx와 매핑)
    private Timestamp reg_date;         // 작성 날짜
    private int mod_idx;           // 수정한 사람
    private Timestamp mod_date;         // 수정 날짜
    private String user_id;        // 아이디 (DB상 user_id와 매핑)
    private int startIndex;        // 시작 게시글 번호 (페이지네이션)
    private int cntPerPage;        // 페이지당 노출 갯수

    private String searchKey;
    private String searchValue;
    
    public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	// Getter 및 Setter 메소드
    public int getIdx() {
        return idx;
    }

    public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}

	public void setMod_date(Timestamp mod_date) {
		this.mod_date = mod_date;
	}

	public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFile_idx() {
        return file_idx;
    }

    public void setFile_idx(int file_idx) {
        this.file_idx = file_idx;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public String getBoard_type_cd() {
        return board_type_cd;
    }

    public void setBoard_type_cd(String board_type_cd) {
        this.board_type_cd = board_type_cd;
    }

    public String getUse_yn() {
        return use_yn;
    }

    public void setUse_yn(String use_yn) {
        this.use_yn = use_yn;
    }

    public int getReg_idx() {
        return reg_idx;
    }

    public void setReg_idx(int reg_idx) {
        this.reg_idx = reg_idx;
    }

    public int getMod_idx() {
        return mod_idx;
    }

    public void setMod_idx(int mod_idx) {
        this.mod_idx = mod_idx;
    }

    public String getUser_id() {
        return user_id;
    }

    public Timestamp getReg_date() {
		return reg_date;
	}

	public Timestamp getMod_date() {
		return mod_date;
	}

	public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getCntPerPage() {
        return cntPerPage;
    }

    public void setCntPerPage(int cntPerPage) {
        this.cntPerPage = cntPerPage;
    }
}
