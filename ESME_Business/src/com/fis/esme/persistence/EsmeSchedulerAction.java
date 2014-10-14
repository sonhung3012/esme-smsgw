package com.fis.esme.persistence;

import java.util.Date;

public class EsmeSchedulerAction implements java.io.Serializable{
	
	private long schaId;
	private EsmeScheduler esmeScheduler;
	private EsmeMessage esmeMessage;
	private String groupId;
	private String status;
	private Date lastDatetime;
	private String executingStatus;
	private Integer failNumber;
	
	public EsmeSchedulerAction() {
		super();
	}

	public EsmeSchedulerAction(long schaId, EsmeScheduler esmeScheduler,
			EsmeMessage esmeMessage, String groupId, String status,
			Date lastDatetime, String executingStatus, Integer failNumber) {
		super();
		this.schaId = schaId;
		this.esmeScheduler = esmeScheduler;
		this.esmeMessage = esmeMessage;
		this.groupId = groupId;
		this.status = status;
		this.lastDatetime = lastDatetime;
		this.executingStatus = executingStatus;
		this.failNumber = failNumber;
	}

	public long getSchaId() {
		return schaId;
	}

	public void setSchaId(long schaId) {
		this.schaId = schaId;
	}

	public EsmeScheduler getEsmeScheduler() {
		return esmeScheduler;
	}

	public void setEsmeScheduler(EsmeScheduler esmeScheduler) {
		this.esmeScheduler = esmeScheduler;
	}

	public EsmeMessage getEsmeMessage() {
		return esmeMessage;
	}

	public void setEsmeMessage(EsmeMessage esmeMessage) {
		this.esmeMessage = esmeMessage;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastDatetime() {
		return lastDatetime;
	}

	public void setLastDatetime(Date lastDatetime) {
		this.lastDatetime = lastDatetime;
	}

	public String getExecutingStatus() {
		return executingStatus;
	}

	public void setExecutingStatus(String executingStatus) {
		this.executingStatus = executingStatus;
	}

	public Integer getFailNumber() {
		return failNumber;
	}

	public void setFailNumber(Integer failNumber) {
		this.failNumber = failNumber;
	}
	
}
