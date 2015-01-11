package com.fis.esme.core.services.scheduler.entity;

import java.util.Comparator;
import java.util.Date;

public class SchedulerDetail implements Comparable {
	private int SCHEDULER_DETAIL_ID;
	private int SCHEDULER_ID;
	private String DATE;

	public int getSCHEDULER_DETAIL_ID() {
		return SCHEDULER_DETAIL_ID;
	}

	public void setSCHEDULER_DETAIL_ID(int sCHEDULER_DETAIL_ID) {
		this.SCHEDULER_DETAIL_ID = sCHEDULER_DETAIL_ID;
	}

	public int getSCHEDULER_ID() {
		return SCHEDULER_ID;
	}

	public void setSCHEDULER_ID(int sCHEDULER_ID) {
		this.SCHEDULER_ID = sCHEDULER_ID;
	}

	public String getDATE() {
		return DATE;
	}

	public void setDATE(String dATE) {
		this.DATE = dATE;
	}

	@Override
	public int compareTo(Object arg1) {
		// TODO Auto-generated method stub
		try {
			if (((SchedulerDetail) this).getSCHEDULER_DETAIL_ID() > ((SchedulerDetail) arg1)
					.getSCHEDULER_DETAIL_ID()) {
				return 1;
			} else if (((SchedulerDetail) this).getSCHEDULER_DETAIL_ID() < ((SchedulerDetail) arg1)
					.getSCHEDULER_DETAIL_ID()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
}
