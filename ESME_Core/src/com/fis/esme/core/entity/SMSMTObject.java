package com.fis.esme.core.entity;

import java.util.Date;

public class SMSMTObject {
	private int mt_id;
	private int request_id;
	private int cp_id;
	private String short_code;
	private int retry_number;
	private int reload_number;
	private Date request_time;
	private Date last_retry_time;
	private String message;
	private String msisdn;
	private String command_code;
	private String register_delivery_report;
	private String status;
	private String sms_log_id;
	private String loadtype;
	private String mstrESMETransID;
	private String mstrMOSequenceNumber;
	private String mstrFileUploadID;
	private String mstrSubId;
	private String mstrGroupId;
	private int mintCustomerId;
	private int mintCampaignId;
	private int mintAdvId;
	
	
	public int getCustomerId() {
		return mintCustomerId;
	}

	public void setCustomerId(int mintCustomerId) {
		this.mintCustomerId = mintCustomerId;
	}

	public int getCampaignId() {
		return mintCampaignId;
	}

	public void setCampaignId(int mintCampaignId) {
		this.mintCampaignId = mintCampaignId;
	}

	public int getAdvId() {
		return mintAdvId;
	}

	public void setAdvId(int mintAdvId) {
		this.mintAdvId = mintAdvId;
	}

	public String getSubId() {
		if (mstrSubId == null || mstrSubId == "") {
			return "0";
		} else
			return mstrSubId;
	}

	public void setSubId(String mstrSubId) {
		this.mstrSubId = mstrSubId;
	}

	public String getGroupId() {
		if (mstrSubId == null || mstrSubId == "") {
			return "0";
		} else
			return mstrGroupId;
	}

	public void setGroupId(String mstrGroupId) {
		this.mstrGroupId = mstrGroupId;
	}

	public String getSms_log_id() {
		return sms_log_id;
	}

	public void setSms_log_id(String sms_log_id) {
		this.sms_log_id = sms_log_id;
	}

	public String getLoadtype() {
		return loadtype;
	}

	public void setLoadtype(String loadtype) {
		this.loadtype = loadtype;
	}

	public int getMt_id() {
		return mt_id;
	}

	public void setMt_id(int mt_id) {
		this.mt_id = mt_id;
	}

	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public int getCp_id() {
		return cp_id;
	}

	public void setCp_id(int cp_id) {
		this.cp_id = cp_id;
	}

	public String getShort_code() {
		return short_code;
	}

	public void setShort_code(String short_code_id) {
		this.short_code = short_code_id;
	}

	public int getRetry_number() {
		return retry_number;
	}

	public void setRetry_number(int retry_number) {
		this.retry_number = retry_number;
	}

	public int getReload_number() {
		return reload_number;
	}

	public void setReload_number(int reload_number) {
		this.reload_number = reload_number;
	}

	public Date getRequest_time() {
		return request_time;
	}

	public void setRequest_time(Date request_time) {
		this.request_time = request_time;
	}

	public Date getLast_retry_time() {
		return last_retry_time;
	}

	public void setLast_retry_time(Date last_retry_time) {
		this.last_retry_time = last_retry_time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getCommand_code() {
		return command_code;
	}

	public void setCommand_code(String command_code) {
		this.command_code = command_code;
	}

	public String getRegister_delivery_report() {
		return register_delivery_report;
	}

	public void setRegister_delivery_report(String register_delivery_report) {
		this.register_delivery_report = register_delivery_report;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getESMETransID() {
		return mstrESMETransID;
	}

	public void setESMETransID(String strESMETransID) {
		mstrESMETransID = strESMETransID;
	}

	public String getMOSequenceNumber() {
		return mstrMOSequenceNumber;
	}

	public void setMOSequenceNumber(String strMOSequenceNumber) {
		mstrMOSequenceNumber = strMOSequenceNumber;
	}

	public String getFileUploadID() {
		return mstrFileUploadID;
	}

	public void setFileUploadID(String strFileUploadID) {
		mstrFileUploadID = strFileUploadID;
	}
}