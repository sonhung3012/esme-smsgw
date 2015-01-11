package com.fis.esme.core.entity;

public class ServicesInternalObject {
	private SMSMORoutingObject smsMORoutingObject;
	private String strSmsLogId;
	private String strMoLogId;
	private String strMsisdn;
	private String strSubId;
	private String strGroupId;
	private String strContent;
	
	public String getContent() {
		return strContent;
	}

	public void setContent(String strContent) {
		this.strContent = strContent;
	}

	public SMSMORoutingObject getSmsMORoutingObject() {
		return smsMORoutingObject;
	}

	public void setSmsMORoutingObject(SMSMORoutingObject smsMORoutingObject) {
		this.smsMORoutingObject = smsMORoutingObject;
	}

	public String getSmsLogId() {
		return strSmsLogId;
	}

	public void setSmsLogId(String strSmsLogId) {
		this.strSmsLogId = strSmsLogId;
	}

	public String getMoLogId() {
		return strMoLogId;
	}

	public void setMoLogId(String strMoLogId) {
		this.strMoLogId = strMoLogId;
	}

	public String getSubId() {
		return strSubId;
	}

	public void setSubId(String strSubId) {
		this.strSubId = strSubId;
	}

	public String getGroupId() {
		return strGroupId;
	}

	public void setGroupId(String strGroupId) {
		this.strGroupId = strGroupId;
	}

	public String getMsisdn() {
		return strMsisdn;
	}

	public void setMsisdn(String msisdn) {
		this.strMsisdn = msisdn;
	}
}
