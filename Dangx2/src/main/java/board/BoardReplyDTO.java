package board;

import java.io.Serializable;
import java.sql.Timestamp;

public class BoardReplyDTO implements Serializable {
	private int idx;
	private int boardIdx;
	private String content;
	private String useYn;
	private int regIdx;
	private Timestamp regDate;
	private int modIdx;
	private Timestamp modDate;
	
	private String userId;
	private String regDt;
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public int getRegIdx() {
		return regIdx;
	}
	public void setRegIdx(int regIdx) {
		this.regIdx = regIdx;
	}
	public int getModIdx() {
		return modIdx;
	}
	public void setModIdx(int modIdx) {
		this.modIdx = modIdx;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public Timestamp getModDate() {
		return modDate;
	}
	public void setModDate(Timestamp modDate) {
		this.modDate = modDate;
	}
	
	
}
