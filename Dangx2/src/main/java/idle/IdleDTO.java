package idle;

import java.io.Serializable;
import java.util.Date;

public class IdleDTO implements Serializable {

	private int idx;
	private String idleName;
	private String idleBreed;
	private String idleYear;
	private String idleGenderCd;
	private String idleNeuterYn;
	private String idleKg;
	private String idleRescueContent;
	private String idleCurrentContent;
	private int idleTypeCd;
	private String useYn;
	private int regIdx;
	private Date regDate;
	private int modIdx;
	private Date modDate;
	
	private String idleGenderName;
	private String imagePath;
	
	private int startIndex;
	private int cntPerPage;
	
	private int targetTypeCd;
	private String idleTypeName;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getIdleName() {
		return idleName;
	}
	public void setIdleName(String idleName) {
		this.idleName = idleName;
	}
	public String getIdleBreed() {
		return idleBreed;
	}
	public void setIdleBreed(String idleBreed) {
		this.idleBreed = idleBreed;
	}
	public String getIdleYear() {
		return idleYear;
	}
	public void setIdleYear(String idleYear) {
		this.idleYear = idleYear;
	}
	public String getIdleGenderCd() {
		return idleGenderCd;
	}
	public void setIdleGenderCd(String idleGenderCd) {
		this.idleGenderCd = idleGenderCd;
	}
	public String getIdleNeuterYn() {
		return idleNeuterYn;
	}
	public void setIdleNeuterYn(String idleNeuterYn) {
		this.idleNeuterYn = idleNeuterYn;
	}
	public String getIdleKg() {
		return idleKg;
	}
	public void setIdleKg(String idleKg) {
		this.idleKg = idleKg;
	}
	public String getIdleRescueContent() {
		return idleRescueContent;
	}
	public void setIdleRescueContent(String idleRescueContent) {
		this.idleRescueContent = idleRescueContent;
	}
	public String getIdleCurrentContent() {
		return idleCurrentContent;
	}
	public void setIdleCurrentContent(String idleCurrentContent) {
		this.idleCurrentContent = idleCurrentContent;
	}
	public int getIdleTypeCd() {
		return idleTypeCd;
	}
	public void setIdleTypeCd(int idleTypeCd) {
		this.idleTypeCd = idleTypeCd;
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
	public String getIdleGenderName() {
		return idleGenderName;
	}
	public void setIdleGenderName(String idleGenderName) {
		this.idleGenderName = idleGenderName;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	public int getTargetTypeCd() {
		return targetTypeCd;
	}
	public void setTargetTypeCd(int targetTypeCd) {
		this.targetTypeCd = targetTypeCd;
	}
	public String getIdleTypeName() {
		return idleTypeName;
	}
	public void setIdleTypeName(String idleTypeName) {
		this.idleTypeName = idleTypeName;
	}
	
	
}
