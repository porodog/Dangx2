package bongsa;

import java.io.Serializable;
import java.util.Date;

public class BongsaDTO implements Serializable {
	
	private int idx;
	private String bongsaDate;
	private String useYn;
	private int regIdx;
	private Date regDate;
	private int modIdx;
	private Date modDate;
	private int bongsaCount;
	
	private String userId;
	private String userPhone;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getBongsaDate() {
		return bongsaDate;
	}
	public void setBongsaDate(String bongsaDate) {
		this.bongsaDate = bongsaDate;
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
	public int getBongsaCount() {
		return bongsaCount;
	}
	public void setBongsaCount(int bongsaCount) {
		this.bongsaCount = bongsaCount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	
	
}
