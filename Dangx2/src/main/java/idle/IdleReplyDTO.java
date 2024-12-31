package idle;

import java.io.Serializable;
import java.sql.Date;

public class IdleReplyDTO implements Serializable {
	
	private int idx;
	private int idleIdx;
	private String content;
	private String useYn;
	private int regIdx;
	private Date regDate;
	private int modIdx;
	private Date modDate;
	
	private String userId;
	private String regDt;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getIdleIdx() {
		return idleIdx;
	}
	public void setIdleIdx(int idleIdx) {
		this.idleIdx = idleIdx;
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
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public int getModIdx() {
		return modIdx;
	}
	public void setModIdx(int modIdx) {
		this.modIdx = modIdx;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
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
	
	
}
