package com.fis.esme.core.services.scheduler.entity;

import java.util.Date;
import java.util.List;

public class Scheduler {
	private int mintCounter ;
	
	private	int SCHEDULER_ID;
	private	String NAME;
	private	String TYPE;
	private	String TIME;
	private	String STATUS;
	private	String SCHEDULER_STATUS;
	private	Date LAST_UPDATE;
	private	List<SchedulerDetail> lsSchedulerDetail;
	private List<SchedulerAction> lsSchedulerAction;
	
	public int getCounter() {
		return mintCounter;
	}

	public void setCounter(int mintCounter) {
		this.mintCounter = mintCounter;
	}

	private String Minis;
	private String Hours;

	public void splitTime() {
		String time = getTIME();
		setHours(time.split(":")[0]);
		setMinis(time.split(":")[1]);
	}

	public String getMinis() {
		return Minis;
	}

	public void setMinis(String minis) {
		Minis = minis;
	}

	public String getHours() {
		return this.Hours;
	}

	public void setHours(String hours) {
		this.Hours = hours;
	}

	public int getSCHEDULER_ID() {
		return this.SCHEDULER_ID;
	}

	public void setSCHEDULER_ID(int SCHEDULER_ID) {
		this.SCHEDULER_ID = SCHEDULER_ID;
	}

	public String getNAME() {
		return this.NAME;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getTYPE() {
		return this.TYPE;
	}

	public void setTYPE(String TYPE) {
		this.TYPE = TYPE;
	}

	public String getTIME() {
		return this.TIME;
	}

	public void setTIME(String TIME) {
		this.TIME = TIME;
		splitTime();
	}

	public String getSTATUS() {
		return this.STATUS;
	}

	public void setSTATUS(String STATUS) {
		this.STATUS = STATUS;
	}

	public String getSCHEDULER_STATUS() {
		return this.SCHEDULER_STATUS;
	}

	public void setSCHEDULER_STATUS(String SCHEDULER_STATUS) {
		this.SCHEDULER_STATUS = SCHEDULER_STATUS;
	}

	public Date getLAST_UPDATE() {
		return this.LAST_UPDATE;
	}

	public void setLAST_UPDATE(Date SAST_UPDATE) {
		this.LAST_UPDATE = SAST_UPDATE;
	}

	public List<SchedulerDetail> getSchedulerDetail() {
		return this.lsSchedulerDetail;
	}

	public void setSchedulerDetail(List<SchedulerDetail> lsSchedulerDetail) {
		this.lsSchedulerDetail = lsSchedulerDetail;
	}

	public List<SchedulerAction> getSchedulerAction() {
		return this.lsSchedulerAction;
	}

	public void setSchedulerAction(List<SchedulerAction> lsSchedulerAction) {
		this.lsSchedulerAction = lsSchedulerAction;
	}

}
