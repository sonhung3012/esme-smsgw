package com.fis.esme.persistence;

public class EsmeSchedulerDetail implements java.io.Serializable{
	private long schedulerDetailId;
	private EsmeScheduler esmeScheduler;
	private String date;
	
	public EsmeSchedulerDetail() {
		super();
	}

	public EsmeSchedulerDetail(long schedulerDetailId,
			EsmeScheduler esmeScheduler, String date) {
		super();
		this.schedulerDetailId = schedulerDetailId;
		this.esmeScheduler = esmeScheduler;
		this.date = date;
	}

	public long getSchedulerDetailId() {
		return schedulerDetailId;
	}

	public void setSchedulerDetailId(long schedulerDetailId) {
		this.schedulerDetailId = schedulerDetailId;
	}

	public EsmeScheduler getEsmeScheduler() {
		return esmeScheduler;
	}

	public void setEsmeScheduler(EsmeScheduler esmeScheduler) {
		this.esmeScheduler = esmeScheduler;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
