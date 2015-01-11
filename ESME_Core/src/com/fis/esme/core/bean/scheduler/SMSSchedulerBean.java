package com.fis.esme.core.bean.scheduler;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.fis.esme.core.services.scheduler.entity.Scheduler;
import com.fis.esme.core.services.scheduler.entity.SchedulerAction;
import com.fis.esme.core.services.scheduler.entity.SchedulerDetail;
import com.fis.esme.core.smpp.entity.GlobalParameter;

public abstract class SMSSchedulerBean {
	public abstract void insertSchedulerLog(Connection mcnMain,
			int schedulerID, String fileURL, String httpDir, String result,
			String status);

	public abstract List<Scheduler> getScheduler(Connection con);

	public abstract List<SchedulerAction> getSchedulerAction(Connection con,
			int schedulerID);

	/**
	 * 
	 * @param con
	 * @param schedulerID
	 * @return
	 */
	public abstract List<SchedulerDetail> getSchedulerDetail(Connection con,
			int schedulerID);

	/**
	 * 
	 * @param mcnMain
	 * @param scheduleriD
	 * @param status
	 * @return
	 */
	public abstract int updateScheduler(Connection mcnMain, int scheduleriD,
			String status);
}
