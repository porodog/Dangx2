package common.file;

import java.io.Serializable;
import java.util.Date;

public class FileDTO implements Serializable {
	
	private int idx;
	private String saveName;
	private String orgName;
	private String ext;
	private String path;
	private String size;
	private int fileOrder;
	private int targetTypeCd;
	private int targetIdx;
	private String useYn;
	private int regIdx;
	private Date regDate;
	private int modIdx;
	private Date modDate;
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getFileOrder() {
		return fileOrder;
	}
	public void setFileOrder(int fileOrder) {
		this.fileOrder = fileOrder;
	}
	public int getTargetTypeCd() {
		return targetTypeCd;
	}
	public void setTargetTypeCd(int targetTypeCd) {
		this.targetTypeCd = targetTypeCd;
	}
	public int getTargetIdx() {
		return targetIdx;
	}
	public void setTargetIdx(int targetIdx) {
		this.targetIdx = targetIdx;
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
	
	
}
