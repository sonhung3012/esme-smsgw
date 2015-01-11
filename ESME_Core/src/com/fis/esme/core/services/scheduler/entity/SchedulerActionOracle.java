package com.fis.esme.core.services.scheduler.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quickserver.net.AppException;

public class SchedulerActionOracle {
	private int CUSTOMER_ID;
	private int GROUP_ID;
	private String MESSAGE;
	private int CAMPAIGN_ID;
	private int MESSAGE_ID;
	private int ADV_ID;
	
	
	
	public int getADV_ID() {
		return ADV_ID;
	}

	public void setADV_ID(int aDV_ID) {
		ADV_ID = aDV_ID;
	}

	public int getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}

	public void setCUSTOMER_ID(int cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}

	public int getGROUP_ID() {
		return GROUP_ID;
	}

	public void setGROUP_ID(int gROUP_ID) {
		GROUP_ID = gROUP_ID;
	}

	public String getMESSAGE() {
		return MESSAGE;
	}

	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}

	public int getCAMPAIGN_ID() {
		return CAMPAIGN_ID;
	}

	public void setCAMPAIGN_ID(int cAMPAIGN_ID) {
		CAMPAIGN_ID = cAMPAIGN_ID;
	}

	public int getMESSAGE_ID() {
		return MESSAGE_ID;
	}

	public void setMESSAGE_ID(int mESSAGE_ID) {
		MESSAGE_ID = mESSAGE_ID;
	}

}
