package com.fis.esme.core.bean.scheduler;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.fis.esme.core.services.scheduler.entity.Scheduler;
import com.fis.esme.core.services.scheduler.entity.SchedulerAction;
import com.fis.esme.core.services.scheduler.entity.SchedulerActionOracle;
import com.fis.esme.core.services.scheduler.entity.SchedulerDetail;
import com.fis.esme.core.util.GlobalParameter;

public class SMSSchedulerBeanOracleEx {
	private MSSchedulerBeanOracleBase taskBean = new MSSchedulerBeanOracleBase();

	public List<Scheduler> getScheduler(Connection con) {
		// TODO Auto-generated method stub
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

	public List<SchedulerActionOracle> getSchedulerAction(Connection con,
			int schedulerID) {
		try {
			Vector vtSchedulerAction = taskBean.getSchedulerAction(con,
					schedulerID);
			List<SchedulerActionOracle> lsSchedulerAction = new ArrayList<SchedulerActionOracle>();
			if (vtSchedulerAction != null && vtSchedulerAction.size() > 0) {
				for (int i = 0; i < vtSchedulerAction.size(); i++) {
					Vector vtTemp = (Vector) vtSchedulerAction.get(i);
					if (vtTemp != null && vtTemp.size() > 0) {
						try {
							int CUSTOMER_ID = GlobalParameter.formatInt(vtTemp
									.get(0));
							int GROUP_ID = GlobalParameter.formatInt(vtTemp
									.get(1));
							int CAMPAIGN_ID = GlobalParameter.formatInt(vtTemp
									.get(2));
							int MESSAGE_ID = GlobalParameter.formatInt(vtTemp
									.get(3));
							String MESSAGE = GlobalParameter
									.formatString(vtTemp.get(4));
							int ADV_ID = GlobalParameter.formatInt(vtTemp
									.get(5));

							SchedulerActionOracle schedulerAction = new SchedulerActionOracle();
							schedulerAction.setCUSTOMER_ID(CUSTOMER_ID);
							schedulerAction.setGROUP_ID(GROUP_ID);
							schedulerAction.setCAMPAIGN_ID(CAMPAIGN_ID);
							schedulerAction.setMESSAGE_ID(MESSAGE_ID);
							schedulerAction.setMESSAGE(MESSAGE);
							schedulerAction.setADV_ID(ADV_ID);
							lsSchedulerAction.add(schedulerAction);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
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

	public int updateScheduler(Connection mcnMain, int scheduleriD,
			String status) {
		// TODO Auto-generated method stub
		try {
			return taskBean.updateScheduler(mcnMain, scheduleriD, status);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return -1;
		}
	}

}
