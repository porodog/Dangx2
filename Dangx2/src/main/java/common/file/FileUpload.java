package common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.apache.tomcat.jakartaee.commons.lang3.ObjectUtils;

import admin.common.AdminDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import login.LoginDTO;


public class FileUpload {
	private static final String SEPARATOR = "/";
	//private static final String SEPARATOR = File.separator;
	private static final String UPLOAD_PATH = "D:"+SEPARATOR+"dangx2"+SEPARATOR+"upload"+SEPARATOR;
	private static final String FILE_TAG_NAME = "fileUpload"; //파일 태그명
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private int targetTypeCd;
	private int targetIdx;
	
	public FileUpload(HttpServletRequest request, int targetTypeCd, int targetIdx) {
		this.request = request;
		this.targetTypeCd = targetTypeCd;
		this.targetIdx = targetIdx;
	}
	
	public FileUpload() {
		
	}
	
	public int fileUploadAndInsert() {
		int result = 0;
		
		try {
			if(targetIdx<0) {
				throw new IOException("FILE UPLOAD FAILED :: NO TARGET IDX");
			}
			
			Collection<Part> partList = request.getParts();
			LoginDTO sessionDTO = (LoginDTO) request.getSession().getAttribute("userInfo");
			
			int regIdx = 0; //관리자는 idx값 0고정
			if(ObjectUtils.isEmpty(sessionDTO)) {
				AdminDTO adminSessionDTO = (AdminDTO) request.getSession().getAttribute("adminInfo");
				if(ObjectUtils.isEmpty(adminSessionDTO)) {
					throw new IOException("FILE UPLOAD FAILED :: NO SESSION");
				}
			}
			else {
				regIdx = sessionDTO.getIdx();
			}
			
			int fileOrder = 1;
			for(Part p : partList) {
				if(!p.getName().equals(FILE_TAG_NAME)) {
					continue;
				}
				
				String pHeader = p.getHeader("content-disposition");
				String[] pHArr = pHeader.split("filename=");
				
				String orgName = pHArr[1].trim().replace("\"", ""); //파일 원본명
				if(!orgName.isEmpty()) {
					//업로드 경로 세팅 -> 업로드 날짜별 폴더생성
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String detailPath = sdf.format(date)+SEPARATOR+targetTypeCd+SEPARATOR;
					String uploadPath = UPLOAD_PATH+detailPath;
					File file = new File(uploadPath);
					if (!file.exists()) {
						file.mkdirs();
					}
					
					//파일생성
					p.write(uploadPath+orgName);
					
					//파일명 변경
					String ext = orgName.substring(orgName.lastIndexOf("."));
					UUID uuid = UUID.randomUUID();
					String saveName = uuid+ext;
					//sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					//String saveName = "dangx2_"+sdf.format(date)+ext; //파일 변경명 -> 여러파일 등록 시 파일명 겹침
					file = new File(uploadPath+SEPARATOR+orgName);
					boolean fileRename = file.renameTo(new File(uploadPath+SEPARATOR+saveName));
					
					//tb_file insert
					if(fileRename) {
						FileDTO fileDTO = new FileDTO();
						fileDTO.setSaveName(saveName);
						fileDTO.setOrgName(orgName);
						//fileDTO.setPath(uploadPath);
						fileDTO.setPath(detailPath);
						fileDTO.setFileOrder(fileOrder++);
						fileDTO.setTargetTypeCd(targetTypeCd);
						fileDTO.setTargetIdx(targetIdx);
						fileDTO.setRegIdx(regIdx);
						fileDTO.setModIdx(regIdx);
						
						result += FileDAO.getInstance().insertFile(fileDTO);
					}
				}
			}
		} catch (IOException e) {
			result = -1;
			e.printStackTrace();
		} catch (ServletException e) {
			result = -1;
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int fileDownload(HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		
		try {
			int fileIdx = Integer.parseInt(request.getParameter("idx"));
			
			FileDTO fileDTO = FileDAO.getInstance().selectFileData(fileIdx);
			if(fileDTO!=null) {
				String savePath = UPLOAD_PATH + fileDTO.getPath();
				String saveName = fileDTO.getSaveName();
				String orgName = fileDTO.getOrgName();
				
				File file = new File(savePath, saveName);
				InputStream inStream = new FileInputStream(file);
				
				String client = request.getHeader("User-Agent");
				if(client.indexOf("WOW64")==-1) {
					orgName = new String(orgName.getBytes("UTF-8"), "ISO-8859-1");
				}
				else {
					orgName = new String(orgName.getBytes("KSC5601"), "ISO-8859-1");
				}
				
				response.reset();
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + orgName + "\"");
				response.setHeader("Content-Length", "" + file.length());
				
				OutputStream outStream = response.getOutputStream();
				
				byte b[] = new byte[(int)file.length()];
				int readBuffer = 0;
				while((readBuffer=inStream.read(b))>0) {
					outStream.write(b, 0, readBuffer);
				}
				
				inStream.close();
				outStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
