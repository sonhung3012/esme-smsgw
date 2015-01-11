package com.fis.esme.core.services.soap;

public class SendMTReponse implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6241259229037197107L;
	private String strResponseCode;
	private String strExecutedTime;
	private String strDescription;

	public String getDescription() {
		return strDescription;
	}

	public void setDescription(String Description) {
		this.strDescription = Description;
	}

	public String getResponseCode() {
		return strResponseCode;
	}

	public void setResponseCode(String strResponseCode) {
		this.strResponseCode = strResponseCode;
	}

	public String getExecutedTime() {
		return strExecutedTime;
	}

	public void setExcetedTime(String strExcetedTime) {
		this.strExecutedTime = strExcetedTime;
	}

}
