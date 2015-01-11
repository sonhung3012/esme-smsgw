package com.fis.esme.core.entity;

import java.util.Date;

public class SMSMOObject {

	private int mo_id;
	private int request_id;
	private int retry_number;
	private Date request_time;
	private Date last_update;
	private String message;
	private String short_code;
	private String type;
	private String msisdn;
	private String status;
	private String loadtype;
	private int service_id;
	private int service_parent_id;
	private int service_roor_id;
	private int cp_id;
	private int smsc_id;
	private int short_code_id;
	private int command_id;
	private String  smsLogId;
	private String  protocal;
	private String mstrSubId;
	private String mstrGroupId;

	public String getSubId() {
		return mstrSubId;
	}

	public void setSubId(String mstrSubId) {
		this.mstrSubId = mstrSubId;
	}

	public String getGroupId() {
		return mstrGroupId;
	}

	public void setGroupId(String mstrGroupId) {
		this.mstrGroupId = mstrGroupId;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	public String getSmsLogId() {
		return smsLogId;
	}

	public void setSmsLogId(String smsLogId) {
		this.smsLogId = smsLogId;
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public int getService_parent_id() {
		return service_parent_id;
	}

	public void setService_parent_id(int service_parent_id) {
		this.service_parent_id = service_parent_id;
	}

	public int getService_roor_id() {
		return service_roor_id;
	}

	public void setService_roor_id(int service_roor_id) {
		this.service_roor_id = service_roor_id;
	}

	public int getCp_id() {
		return cp_id;
	}

	public void setCp_id(int cp_id) {
		this.cp_id = cp_id;
	}

	public int getSmsc_id() {
		return smsc_id;
	}

	public void setSmsc_id(int smsc_id) {
		this.smsc_id = smsc_id;
	}

	public int getShort_code_id() {
		return short_code_id;
	}

	public void setShort_code_id(int short_code_id) {
		this.short_code_id = short_code_id;
	}

	public int getCommand_id() {
		return command_id;
	}

	public void setCommand_id(int command_id) {
		this.command_id = command_id;
	}

	public int getMo_id() {
		return mo_id;
	}

	public void setMo_id(int mo_id) {
		this.mo_id = mo_id;
	}

	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public int getRetry_number() {
		return retry_number;
	}

	public void setRetry_number(int retry_number) {
		this.retry_number = retry_number;
	}

	public Date getRequest_time() {
		return request_time;
	}

	public void setRequest_time(Date request_time) {
		this.request_time = request_time;
	}

	public Date getLast_update() {
		return last_update;
	}

	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getShort_code() {
		return short_code;
	}

	public void setShort_code(String short_code) {
		this.short_code = short_code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoadtype() {
		return loadtype;
	}

	public void setLoadtype(String loadtype) {
		this.loadtype = loadtype;
	}

}
