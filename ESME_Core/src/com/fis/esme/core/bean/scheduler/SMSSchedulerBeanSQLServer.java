package com.fis.esme.core.bean.scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.fis.esme.core.services.scheduler.entity.Scheduler;
import com.fis.esme.core.services.scheduler.entity.SchedulerAction;
import com.fis.esme.core.services.scheduler.entity.SchedulerDetail;
import com.fis.esme.core.util.GlobalParameter;
import com.fss.sql.Database;

public class SMSSchedulerBeanSQLServer extends SMSSchedulerBean {
	private SMSSchedulerBeanSQLServerBase taskBean = new SMSSchedulerBeanSQLServerBase();

	public void insertSchedulerLog(Connection mcnMain, int schedulerID,
			String fileURL,String httpDir,String result,String status) {
		taskBean.insertSchedulerLog(mcnMain, schedulerID, fileURL, httpDir,result,status);
	}

	public List<Scheduler> getScheduler(Connection con) {
		try {
			Vector vtScheduler = taskBean.getScheduler(con);
			if (vtScheduler != null && vtScheduler.size() > 0) {
				List<Scheduler> lsScheduler = new ArrayList<Scheduler>();
				for (int i = 0; i < vtScheduler.size(); i++) {
					Vector vtTemp = (Vector) vtScheduler.get(i);
					int SCHEDULER_ID = GlobalParameter.formatInt(vtTemp.get(0));
					String NAME = GlobalParameter.formatString(vtTemp.get(1));
					String TYPE = GlobalParameter.formatString(vtTemp.get(2));
					String TIME = GlobalParameter.formatString(vtTemp.get(3));
					String STATUS = GlobalParameter.formatString(vtTemp.get(4));
					String SCHEDULER_STATUS = GlobalParameter
							.formatString(vtTemp.get(5));
					Scheduler scheduler = new Scheduler();
					scheduler.setSCHEDULER_ID(SCHEDULER_ID);
					scheduler.setNAME(NAME);
					scheduler.setTYPE(TYPE);
					scheduler.setTIME(TIME);
					scheduler.setSTATUS(STATUS);
					scheduler.setSCHEDULER_STATUS(SCHEDULER_STATUS);
					// load scheduler detail
					List<SchedulerDetail> lsSchedulerDetail = getSchedulerDetail(
							con, SCHEDULER_ID);
					scheduler.setSchedulerDetail(lsSchedulerDetail);
					lsScheduler.add(scheduler);
				}
				return lsScheduler;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	public List<SchedulerAction> getSchedulerAction(Connection con,
			int schedulerID) {
		try {
			Vector vtSchedulerAction = taskBean.getSchedulerAction(con,
					schedulerID);
			List<SchedulerAction> lsSchedulerAction = new ArrayList<SchedulerAction>();
			if (vtSchedulerAction != null && vtSchedulerAction.size() > 0) {
				for (int i = 0; i < vtSchedulerAction.size(); i++) {
					Vector vtTemp = (Vector) vtSchedulerAction.get(i);
					if (vtTemp != null && vtTemp.size() > 0) {
						try {
							int SCHA_ID = GlobalParameter.formatInt(vtTemp
									.get(0));
							int SCHEDULER_ID = GlobalParameter.formatInt(vtTemp
									.get(1));
							int MESSAGE_ID = GlobalParameter.formatInt(vtTemp
									.get(2));
							String GROUP_ID = GlobalParameter.formatString(vtTemp
									.get(3));
							String STATUS = GlobalParameter.formatString(vtTemp
									.get(4));
							Date LAST_DATETIME = GlobalParameter
									.formatDate(vtTemp.get(5));
							String EXECUTING_STATUS = GlobalParameter
									.formatString(vtTemp.get(6));
							int EXECUTED_TIMES = GlobalParameter.formatInt(vtTemp
									.get(7));
							SchedulerAction schedulerAction = new SchedulerAction();
							schedulerAction.setSCHA_ID(SCHA_ID);
							schedulerAction.setSCHEDULER_ID(SCHEDULER_ID);
							schedulerAction.setMESSAGE_ID(MESSAGE_ID);
							schedulerAction.setGROUP_ID(GROUP_ID);
							schedulerAction.setSTATUS(STATUS);
							schedulerAction.setLAST_DATETIME(LAST_DATETIME);
							schedulerAction.setEXECUTING_STATUS(EXECUTING_STATUS);
							schedulerAction.setEXECUTED_TIMES(EXECUTED_TIMES);
							lsSchedulerAction.add(schedulerAction);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}
			return lsSchedulerAction;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}


	/**
	 * 
	 * @param con
	 * @param schedulerID
	 * @return
	 */
	public List<SchedulerDetail> getSchedulerDetail(Connection con,
			int schedulerID) {
		try {
			Vector vtSchedulerDetail = taskBean.getSchedulerDetail(con,
					schedulerID);
			List<SchedulerDetail> lsSchedulerDetail = new ArrayList<SchedulerDetail>();
			if (vtSchedulerDetail != null && vtSchedulerDetail.size() > 0) {
				for (int i = 0; i < vtSchedulerDetail.size(); i++) {
					Vector vtTemp = (Vector) vtSchedulerDetail.get(i);
					if (vtTemp != null && vtTemp.size() > 0) {
						int SCHEDULER_DETAIL_ID = GlobalParameter
								.formatInt(vtTemp.get(0));
						int SCHEDULER_ID = GlobalParameter.formatInt(vtTemp
								.get(1));
						String DATE = GlobalParameter.formatString(vtTemp
								.get(2));
						SchedulerDetail schedulerDetail = new SchedulerDetail();
						schedulerDetail
								.setSCHEDULER_DETAIL_ID(SCHEDULER_DETAIL_ID);
						schedulerDetail.setSCHEDULER_ID(SCHEDULER_ID);
						schedulerDetail.setDATE(DATE);
						lsSchedulerDetail.add(schedulerDetail);
					}
				}
			}
			return lsSchedulerDetail;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 
	 * @param mcnMain
	 * @param scheduleriD
	 * @param status
	 * @return
	 */
	public int updateScheduler(Connection mcnMain, int scheduleriD,
			String status) {
		try {
			return taskBean.updateScheduler(mcnMain, scheduleriD, status);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return -1;
		}

	}
}
