package com.fis.esme.core.services.scheduler.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quickserver.net.AppException;

public class SchedulerAction {
	private int SCHA_ID;
	private String Name;
	private int SCHEDULER_ID;
	private int MESSAGE_ID;
	private String GROUP_ID;
	private String STATUS;
	private Date LAST_DATETIME;
	private String EXECUTING_STATUS;
	private int EXECUTED_TIMES;
	private List<Integer> lsGroups;

	public List<Integer> getlsGroup() {
		return lsGroups;
	}

	public String getGROUP_ID() {
		return GROUP_ID;
	}

	public void setGROUP_ID(String gROUP_ID) throws AppException {
		try {
			GROUP_ID = gROUP_ID;
			String[] arrGroup = this.GROUP_ID.split(":");
			if (arrGroup == null || arrGroup.length <= 0) {
				throw new AppException(
						"The GroupID is not contains the seperated character, Ps. try contact with administator to fix this isue. thanks.");
			}
			lsGroups = new ArrayList<Integer>();
			for (int i = 0; i < arrGroup.length; i++) {
				lsGroups.add(Integer.valueOf(arrGroup[i]));
			}
		} catch (AppException e) {
			e.printStackTrace();
			throw e;
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getSCHA_ID() {
		return SCHA_ID;
	}

	public void setSCHA_ID(int sCHA_ID) {
		this.SCHA_ID = sCHA_ID;
	}

	public int getSCHEDULER_ID() {
		return this.SCHEDULER_ID;
	}

	public void setSCHEDULER_ID(int sCHEDULER_ID) {
		this.SCHEDULER_ID = sCHEDULER_ID;
	}

	public int getMESSAGE_ID() {
		return this.MESSAGE_ID;
	}

	public void setMESSAGE_ID(int aCTION_ID) {
		this.MESSAGE_ID = aCTION_ID;
	}

	public String getSTATUS() {
		return this.STATUS;
	}

	public void setSTATUS(String sTATUS) {
		this.STATUS = sTATUS;
	}

	public Date getLAST_DATETIME() {
		return this.LAST_DATETIME;
	}

	public void setLAST_DATETIME(Date lAST_DATETIME) {
		this.LAST_DATETIME = lAST_DATETIME;
	}

	public String getEXECUTING_STATUS() {
		return this.EXECUTING_STATUS;
	}

	public void setEXECUTING_STATUS(String eXECUTING_STATUS) {
		this.EXECUTING_STATUS = eXECUTING_STATUS;
	}

	public int getEXECUTED_TIMES() {
		return this.EXECUTED_TIMES;
	}

	public void setEXECUTED_TIMES(int eXECUTED_TIMES) {
		this.EXECUTED_TIMES = eXECUTED_TIMES;
	}

}
