package bongsa;

import java.io.Serializable;

public class BongsaInfoDTO implements Serializable {
	
	private int bongsaIdx;
	private String bongsaName;
	private String bongsaPhone;
	private int bongsaOrder;
	
	
	public int getBongsaIdx() {
		return bongsaIdx;
	}
	public void setBongsaIdx(int bongsaIdx) {
		this.bongsaIdx = bongsaIdx;
	}
	public String getBongsaName() {
		return bongsaName;
	}
	public void setBongsaName(String bongsaName) {
		this.bongsaName = bongsaName;
	}
	public String getBongsaPhone() {
		return bongsaPhone;
	}
	public void setBongsaPhone(String bongsaPhone) {
		this.bongsaPhone = bongsaPhone;
	}
	public int getBongsaOrder() {
		return bongsaOrder;
	}
	public void setBongsaOrder(int bongsaOrder) {
		this.bongsaOrder = bongsaOrder;
	}
	
	
}
