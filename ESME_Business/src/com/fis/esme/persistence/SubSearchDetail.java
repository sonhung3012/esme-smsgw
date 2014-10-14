package com.fis.esme.persistence;

import java.util.Date;

public class SubSearchDetail implements java.io.Serializable {

	private Date toDate;
	private Date fromDate;
	private String msisdn;
	private Long serviceId;
	private Long commandId;
	private Long shortcodeId;
	
	public SubSearchDetail(){
		
	}
	public SubSearchDetail(Date toDate, Date fromDate, String msisdn,
			Long serviceId, Long commandId, Long shortcodeId) {
		super();
		this.toDate = toDate;
		this.fromDate = fromDate;
		this.msisdn = msisdn;
		this.serviceId = serviceId;
		this.commandId = commandId;
		this.shortcodeId = shortcodeId;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getCommandId() {
		return commandId;
	}

	public void setCommandId(Long commandId) {
		this.commandId = commandId;
	}

	public Long getShortcodeId() {
		return shortcodeId;
	}

	public void setShortcodeId(Long shortcodeId) {
		this.shortcodeId = shortcodeId;
	}
}
