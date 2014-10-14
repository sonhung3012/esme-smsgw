package com.fis.esme.persistence;

import java.util.Date;

public class EsmeEmsMo implements java.io.Serializable{
	private long moId;
	private String message;
	private Date requestTime;
	private String status;
	private String msisdn;
	private String shortCode;
	private Integer retryNumber;
	private Date lastUpdate;
	private String type;
	private String reason;
	private Integer protocal;
	private EsmeSmsLog esmeSmsLog;
	private EsmeSmsCommand esmeSmsCommand;
	private EsmeShortCode esmeShortCode;
	private EsmeSmsc esmeSmsc;
	private EsmeCp esmeCp;
	private EsmeServices esmeServicesRoot;
	private EsmeServices esmeServicesParent;
	private EsmeServices esmeServices;
	private EsmeSubscriber esmeSubscriber;
	private EsmeGroups esmeGroups;
	
	public EsmeEmsMo() {
		super();
	}

	public EsmeEmsMo(long moId, String message, Date requestTime,
			String status, String msisdn, String shortCode,
			Integer retryNumber, Date lastUpdate, String type, String reason,
			Integer protocal, EsmeSmsLog esmeSmsLog,
			EsmeSmsCommand esmeSmsCommand, EsmeShortCode esmeShortCode,
			EsmeSmsc esmeSmsc, EsmeCp esmeCp, EsmeServices esmeServicesRoot,
			EsmeServices esmeServicesParent, EsmeServices esmeServices,
			EsmeSubscriber esmeSubscriber, EsmeGroups esmeGroups) {
		super();
		this.moId = moId;
		this.message = message;
		this.requestTime = requestTime;
		this.status = status;
		this.msisdn = msisdn;
		this.shortCode = shortCode;
		this.retryNumber = retryNumber;
		this.lastUpdate = lastUpdate;
		this.type = type;
		this.reason = reason;
		this.protocal = protocal;
		this.esmeSmsLog = esmeSmsLog;
		this.esmeSmsCommand = esmeSmsCommand;
		this.esmeShortCode = esmeShortCode;
		this.esmeSmsc = esmeSmsc;
		this.esmeCp = esmeCp;
		this.esmeServicesRoot = esmeServicesRoot;
		this.esmeServicesParent = esmeServicesParent;
		this.esmeServices = esmeServices;
		this.esmeSubscriber = esmeSubscriber;
		this.esmeGroups = esmeGroups;
	}

	public long getMoId() {
		return moId;
	}

	public void setMoId(long moId) {
		this.moId = moId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public Integer getRetryNumber() {
		return retryNumber;
	}

	public void setRetryNumber(Integer retryNumber) {
		this.retryNumber = retryNumber;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getProtocal() {
		return protocal;
	}

	public void setProtocal(Integer protocal) {
		this.protocal = protocal;
	}

	public EsmeSmsLog getEsmeSmsLog() {
		return esmeSmsLog;
	}

	public void setEsmeSmsLog(EsmeSmsLog esmeSmsLog) {
		this.esmeSmsLog = esmeSmsLog;
	}

	public EsmeSmsCommand getEsmeSmsCommand() {
		return esmeSmsCommand;
	}

	public void setEsmeSmsCommand(EsmeSmsCommand esmeSmsCommand) {
		this.esmeSmsCommand = esmeSmsCommand;
	}

	public EsmeShortCode getEsmeShortCode() {
		return esmeShortCode;
	}

	public void setEsmeShortCode(EsmeShortCode esmeShortCode) {
		this.esmeShortCode = esmeShortCode;
	}

	public EsmeSmsc getEsmeSmsc() {
		return esmeSmsc;
	}

	public void setEsmeSmsc(EsmeSmsc esmeSmsc) {
		this.esmeSmsc = esmeSmsc;
	}

	public EsmeCp getEsmeCp() {
		return esmeCp;
	}

	public void setEsmeCp(EsmeCp esmeCp) {
		this.esmeCp = esmeCp;
	}

	public EsmeServices getEsmeServicesRoot() {
		return esmeServicesRoot;
	}

	public void setEsmeServicesRoot(EsmeServices esmeServicesRoot) {
		this.esmeServicesRoot = esmeServicesRoot;
	}

	public EsmeServices getEsmeServicesParent() {
		return esmeServicesParent;
	}

	public void setEsmeServicesParent(EsmeServices esmeServicesParent) {
		this.esmeServicesParent = esmeServicesParent;
	}

	public EsmeServices getEsmeServices() {
		return esmeServices;
	}

	public void setEsmeServices(EsmeServices esmeServices) {
		this.esmeServices = esmeServices;
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
