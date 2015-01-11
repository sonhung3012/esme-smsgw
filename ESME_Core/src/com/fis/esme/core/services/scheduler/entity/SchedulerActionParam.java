package com.fis.esme.core.services.scheduler.entity;

public class SchedulerActionParam {
	private	int SAP_ID;
	private	int SCHA_ID;
	private	int AP_ID;
	private	String VALUE;
	private	String NAME;

	public String getName() {
		return this.NAME;
	}

	public void setName(String name) {
		this.NAME = name;
	}

	public int getSAP_ID() {
		return SAP_ID;
	}

	public void setSAP_ID(int sAP_ID) {
		this.SAP_ID = sAP_ID;
	}

	public int getSCHA_ID() {
		return SCHA_ID;
	}

	public void setSCHA_ID(int sCHA_ID) {
		this.SCHA_ID = sCHA_ID;
	}

	public int getAP_ID() {
		return AP_ID;
	}

	public void setAP_ID(int aP_ID) {
		this.AP_ID = aP_ID;
	}

	public String getVALUE() {
		return VALUE;
	}

	public void setVALUE(String vALUE) {
		this.VALUE = vALUE;
	}

}
