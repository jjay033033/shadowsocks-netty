package priv.lmoon.shadowsupdate.vo;

import java.util.Map;

public class ServerConfigVO {
	
	private String id;
	
	private String url;
	
	private String begin;
	
	private String end;
	
	private int type;
	
	//TextServer参数
//	private String serverIpBegin;
//	private String serverIpEnd;
//	private String passwordBegin;
//	private String passwordEnd;
//	private String encryptionBegin;
//	private String encryptionEnd;
//	private String serverPortBegin;
//	private String serverPortEnd;
	
	private Map<Integer,ConfParam> params;
	
	

	//PicServer参数
	private String picUrlBegin;
	private String picUrlEnd;
	private String severPicFlag;
	

	public String getPicUrlBegin() {
		return picUrlBegin;
	}

	public void setPicUrlBegin(String picUrlBegin) {
		this.picUrlBegin = picUrlBegin;
	}

	public String getPicUrlEnd() {
		return picUrlEnd;
	}

	public void setPicUrlEnd(String picUrlEnd) {
		this.picUrlEnd = picUrlEnd;
	}

	public String getSeverPicFlag() {
		return severPicFlag;
	}

	public void setSeverPicFlag(String severPicFlag) {
		this.severPicFlag = severPicFlag;
	}

//	public String getServerIpBegin() {
//		return serverIpBegin;
//	}
//
//	public void setServerIpBegin(String serverIpBegin) {
//		this.serverIpBegin = serverIpBegin;
//	}
//
//	public String getServerIpEnd() {
//		return serverIpEnd;
//	}
//
//	public void setServerIpEnd(String serverIpEnd) {
//		this.serverIpEnd = serverIpEnd;
//	}
//
//	public String getPasswordBegin() {
//		return passwordBegin;
//	}
//
//	public void setPasswordBegin(String passwordBegin) {
//		this.passwordBegin = passwordBegin;
//	}
//
//	public String getPasswordEnd() {
//		return passwordEnd;
//	}
//
//	public void setPasswordEnd(String passwordEnd) {
//		this.passwordEnd = passwordEnd;
//	}
//
//	public String getEncryptionBegin() {
//		return encryptionBegin;
//	}
//
//	public void setEncryptionBegin(String encryptionBegin) {
//		this.encryptionBegin = encryptionBegin;
//	}
//
//	public String getEncryptionEnd() {
//		return encryptionEnd;
//	}
//
//	public void setEncryptionEnd(String encryptionEnd) {
//		this.encryptionEnd = encryptionEnd;
//	}
//
//	public String getServerPortBegin() {
//		return serverPortBegin;
//	}
//
//	public void setServerPortBegin(String serverPortBegin) {
//		this.serverPortBegin = serverPortBegin;
//	}
//
//	public String getServerPortEnd() {
//		return serverPortEnd;
//	}
//
//	public void setServerPortEnd(String serverPortEnd) {
//		this.serverPortEnd = serverPortEnd;
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public Map<Integer, ConfParam> getParams() {
		return params;
	}

	public void setParams(Map<Integer, ConfParam> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "ServerConfigVO [id=" + id + ", url=" + url + ", begin=" + begin + ", end=" + end + ", type=" + type
				+ ", params=" + params + ", picUrlBegin=" + picUrlBegin + ", picUrlEnd=" + picUrlEnd + ", severPicFlag="
				+ severPicFlag + "]";
	}

	
}
