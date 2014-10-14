package com.fis.esme.persistence;

import java.util.Date;

public class EsmeEmsMt implements java.io.Serializable{
	private long mtId;
	private Date requestTime;
	private String message;
	private EsmeCp esmeCp;
	private String shortCode;
	private String msisdn;
	private String status;
	private long retryNumber;
	private Date lastRetryTime;
	private String commandCode;
	private String registerDeliveryReport;
	private long reloadNumber;
	private EsmeFileUpload esmeFileUpload;
	private EsmeSmsLog esmeSmsLog;
	private EsmeEmsMo esmeEmsMo;
	private EsmeSubscriber esmeSubscriber;
	private EsmeGroups esmeGroups;
	
	public EsmeEmsMt() {
		super();
	}

	public EsmeEmsMt(long mtId, Date requestTime, String message,
			EsmeCp esmeCp, String shortCode, String msisdn, String status,
			long retryNumber, Date lastRetryTime, String commandCode,
			String registerDeliveryReport, long reloadNumber,
			EsmeFileUpload esmeFileUpload, EsmeSmsLog esmeSmsLog,
			EsmeEmsMo esmeEmsMo, EsmeSubscriber esmeSubscriber,
			EsmeGroups esmeGroups) {
		super();
		this.mtId = mtId;
		this.requestTime = requestTime;
		this.message = message;
		this.esmeCp = esmeCp;
		this.shortCode = shortCode;
		this.msisdn = msisdn;
		this.status = status;
		this.retryNumber = retryNumber;
		this.lastRetryTime = lastRetryTime;
		this.commandCode = commandCode;
		this.registerDeliveryReport = registerDeliveryReport;
		this.reloadNumber = reloadNumber;
		this.esmeFileUpload = esmeFileUpload;
		this.esmeSmsLog = esmeSmsLog;
		this.esmeEmsMo = esmeEmsMo;
		this.esmeSubscriber = esmeSubscriber;
		this.esmeGroups = esmeGroups;
	}

	public long getMtId() {
		return mtId;
	}

	public void setMtId(long mtId) {
		this.mtId = mtId;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EsmeCp getEsmeCp() {
		return esmeCp;
	}

	public void setEsmeCp(EsmeCp esmeCp) {
		this.esmeCp = esmeCp;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
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

	public long getRetryNumber() {
		return retryNumber;
	}

	public void setRetryNumber(long retryNumber) {
		this.retryNumber = retryNumber;
	}

	public Date getLastRetryTime() {
		return lastRetryTime;
	}

	public void setLastRetryTime(Date lastRetryTime) {
		this.lastRetryTime = lastRetryTime;
	}

	public String getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	public String getRegisterDeliveryReport() {
		return registerDeliveryReport;
	}

	public void setRegisterDeliveryReport(String registerDeliveryReport) {
		this.registerDeliveryReport = registerDeliveryReport;
	}

	public long getReloadNumber() {
		return reloadNumber;
	}

	public void setReloadNumber(long reloadNumber) {
		this.reloadNumber = reloadNumber;
	}

	public EsmeFileUpload getEsmeFileUpload() {
		return esmeFileUpload;
	}

	public void setEsmeFileUpload(EsmeFileUpload esmeFileUpload) {
		this.esmeFileUpload = esmeFileUpload;
	}

	public EsmeSmsLog getEsmeSmsLog() {
		return esmeSmsLog;
	}

	public void setEsmeSmsLog(EsmeSmsLog esmeSmsLog) {
		this.esmeSmsLog = esmeSmsLog;
	}

	public EsmeEmsMo getEsmeEmsMo() {
		return esmeEmsMo;
	}

	public void setEsmeEmsMo(EsmeEmsMo esmeEmsMo) {
		this.esmeEmsMo = esmeEmsMo;
	}

	public EsmeSubscriber getEsmeSubscriber() {
		return esmeSubscriber;
	}

	public void setEsmeSubscriber(EsmeSubscriber esmeSubscriber) {
		this.esmeSubscriber = esmeSubscriber;
	}

	public EsmeGroups getEsmeGroups() {
		return esmeGroups;
	}

	public void setEsmeGroups(EsmeGroups esmeGroups) {
		this.esmeGroups = esmeGroups;
	}

}
