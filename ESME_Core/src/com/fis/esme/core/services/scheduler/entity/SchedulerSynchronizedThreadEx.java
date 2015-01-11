package com.fis.esme.core.services.scheduler.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.fis.esme.core.app.AppManager;
import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.bean.scheduler.SMSSchedulerBean;
import com.fis.esme.core.bean.scheduler.SMSSchedulerBeanFactory;
import com.fss.sql.Database;
import com.fss.thread.ParameterType;
import com.fss.thread.ParameterUtil;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;
import com.fss.util.StringUtil;
import com.fis.esme.core.util.GlobalParameter;

public class SchedulerSynchronizedThreadEx extends ManageableThreadEx {

	private SMSSchedulerBean schedulerHandler;
	private Hashtable<Integer, Scheduler> tbScheduler = new Hashtable<Integer, Scheduler>();
	private Hashtable<Integer, String> tbSchedulerStore = new Hashtable<Integer, String>();
	// TODO Auto-generated method stub
	// Prepares the listener.
	private SchedulerTaskListener listener = new SchedulerTaskListener();
	// Prepares the task.

	// Creates the scheduler.
	private it.sauronsoftware.cron4j.Scheduler scheduler = new it.sauronsoftware.cron4j.Scheduler();

	private boolean blssh = false;
	private String strCommandcode;
	private String strShortCode;
	private String strCPID;

	public void fillParameter() throws AppException {
		strCommandcode = loadMandatory("CommandCode");
		strShortCode = loadMandatory("ShortCode");
		strCPID = loadMandatory("CPID");
		super.fillParameter();
	}

	public Vector getParameterDefinition() {
		Vector vtReturn = super.getParameterDefinition();
		vtReturn.addElement(createParameterDefinition("CommandCode", "",
				ParameterType.PARAM_TEXTBOX_MAX, "",
				"The CommandCode for logger information"));
		vtReturn.addElement(createParameterDefinition("ShortCode", "",
				ParameterType.PARAM_TEXTBOX_MAX, "",
				"The ShortCode for logger information"));
		vtReturn.addElement(createParameterDefinition("CPID", "",
				ParameterType.PARAM_TEXTBOX_MAX, "",
				"The Content Provider Identify for logger information"));
		return vtReturn;
	}

	// Registers the listener.
	@Override
	protected void beforeSession() throws Exception {
		// TODO Auto-generated method stub
		schedulerHandler = SMSSchedulerBeanFactory.getSmsBeanFactory();
		scheduler = new it.sauronsoftware.cron4j.Scheduler();
		scheduler.addSchedulerListener(listener);
		scheduler.start();
		super.beforeSession();
	}

	@Override
	protected void processSession() throws Exception {
		int mintCounter = 1;
		while (miThreadCommand != ThreadConstant.THREAD_STOP) {
			try {
				mcnMain = getThreadManager().getConnection();
				List<Scheduler> lsScheduler = schedulerHandler
						.getScheduler(mcnMain);
				if (lsScheduler != null && lsScheduler.size() > 0) {
					for (int i = 0; i < lsScheduler.size(); i++) {
						Scheduler schedulerObject = lsScheduler.get(i);
						String time = schedulerObject.getTIME();
						String type = schedulerObject.getTYPE();
						String name = schedulerObject.getNAME();
						int schedulerID = schedulerObject.getSCHEDULER_ID();
						String strDate = getDatePattern(
								schedulerObject.getSchedulerDetail());
						String strSchedulerPartern = getTimeSchedulerPattern(
								type, schedulerObject.getMinis(),
								schedulerObject.getHours(), strDate);
						Scheduler cacheScheduler = tbScheduler.get(schedulerID);
						if (cacheScheduler == null) {
							schedulerObject.setCounter(mintCounter);
							debugMonitor(
									"add scheduler:"
											+ schedulerObject.getNAME()
											+ ", with time:" + time + ",type:"
											+ type, 9);
							tbScheduler.put(schedulerObject.getSCHEDULER_ID(),
									schedulerObject);
							SchedulerTaskEx task = new SchedulerTaskEx();
							task.setSchedulerLoader(this);
							task.setSchedulerID(schedulerID);
							task.setSchedulerName(name);
							task.mstrCommandcode = strCommandcode;
							task.mstrShortcode = strShortCode;
							task.mstrCpID = strCPID;
							String scheduledID = scheduler.schedule(
									strSchedulerPartern, task);
							tbSchedulerStore.put(schedulerID, scheduledID);
						} else {
							cacheScheduler.setCounter(mintCounter);
							boolean blModified = checkSchedulerModified(
									cacheScheduler.getSchedulerDetail(),
									schedulerObject.getSchedulerDetail());
							boolean blModifiedTime = false;
							if (!cacheScheduler.getTIME().equalsIgnoreCase(
									schedulerObject.getTIME())) {
								blModifiedTime = true;
							}
							if (!cacheScheduler.getTYPE().equalsIgnoreCase(
									schedulerObject.getTYPE())) {
								blModifiedTime = true;
							}
							if (blModified || blModifiedTime) {
								schedulerObject.setCounter(mintCounter);
								tbScheduler.put(
										schedulerObject.getSCHEDULER_ID(),
										schedulerObject);
								String scheduledId = tbSchedulerStore
										.get(schedulerObject.getSCHEDULER_ID());
								scheduler.reschedule(scheduledId,
										strSchedulerPartern);
								debugMonitor("update scheduler:"
										+ schedulerObject.getNAME()
										+ ", with time:" + time + ",type:"
										+ type, 9);

								continue;
							}
						}
					}
				}
				try {
					Set st = tbScheduler.keySet();
					Iterator itr = st.iterator();
					while (itr.hasNext()) {
						int key = (Integer) itr.next();
						Scheduler schedulerObject = tbScheduler.get(key);
						if (schedulerObject.getCounter() < mintCounter) {
							synchronized (tbScheduler) {
								tbScheduler.remove(key);
								itr = st.iterator();
							}
							String scheduledId = tbSchedulerStore.remove(key);
							scheduler.deschedule(scheduledId);
							debugMonitor(
									"remove scheduler:"
											+ schedulerObject.getNAME()
											+ ", with time:"
											+ schedulerObject.getTIME()
											+ ",type:"
											+ schedulerObject.getTYPE(), 9);

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logMonitor(e.getMessage());
					// TODO: handle exception
				}
				mintCounter++;
				Thread.sleep(miDelayTime * 1000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logMonitor(e.getMessage());
				throw e;
			} finally {
				mcnMain.close();
				Database.closeObject(mcnMain);
			}

		}

	}

	private String getDatePattern(List<SchedulerDetail> lsScheduler) {
		String strDatePattern = "";
		for (int i = 0; lsScheduler != null && i < lsScheduler.size(); i++) {
			SchedulerDetail schedulerDetail = lsScheduler.get(i);
				strDatePattern += schedulerDetail.getDATE() + ",";
		}
		if (strDatePattern != null && strDatePattern != "") {
			return strDatePattern.substring(0, strDatePattern.length() - 1);
		}
		return null;
	}
	
	
	private String getMonthPattern(List<SchedulerDetail> lsScheduler, String type) {
		String strDatePattern = "";

		for (int i = 0; lsScheduler != null && i < lsScheduler.size(); i++) {
			SchedulerDetail schedulerDetail = lsScheduler.get(i);
			if (type
					.equalsIgnoreCase(GlobalParameter.SCHEDULER_MODE_THELONG)) {
					strDatePattern += schedulerDetail.getDATE().substring(2,4) + ",";
			} else
				strDatePattern += schedulerDetail.getDATE() + ",";
		}
		if (strDatePattern != null && strDatePattern != "") {
			return strDatePattern.substring(0, strDatePattern.length() - 1);
		}
		return null;
	}

	private String getTimeSchedulerPattern(String Schedulermode, String minis,
			String hours, String lsDate) {
		String[] arrScheduler = new String[5];
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		int intDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int intMonth = cal.get(Calendar.MONTH);
		int intDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (Schedulermode
				.equalsIgnoreCase(GlobalParameter.SCHEDULER_MODE_DIRECTLY)) {
			arrScheduler[0] = minis;
			arrScheduler[1] = hours;
			// arrScheduler[2] = "*";
			// arrScheduler[3] = "*";
			arrScheduler[2] = String.valueOf(intDayOfMonth);
			arrScheduler[3] = "*";
			arrScheduler[4] = "*";
		} else if (Schedulermode
				.equalsIgnoreCase(GlobalParameter.SCHEDULER_MODE_WEEKLY)) {
			arrScheduler[0] = minis;
			arrScheduler[1] = hours;
			arrScheduler[2] = "*";
			arrScheduler[3] = "*";
			arrScheduler[4] = lsDate;
		} else if (Schedulermode
				.equalsIgnoreCase(GlobalParameter.SCHEDULER_MODE_MONTHY)) {
			arrScheduler[0] = minis;
			arrScheduler[1] = hours;
			arrScheduler[2] = lsDate;
			arrScheduler[3] = "*";
			arrScheduler[4] = "*";
		} else if (Schedulermode
				.equalsIgnoreCase(GlobalParameter.SCHEDULER_MODE_THELONG)) {
			arrScheduler[0] = minis;
			arrScheduler[1] = hours;
			arrScheduler[2] = "*";
			arrScheduler[3] = "*";
			arrScheduler[4] = "*";
		}
		String strTimePartern = "";
		for (int i = 0; i < arrScheduler.length; i++) {
			strTimePartern += arrScheduler[i] + " ";
		}
		return strTimePartern.trim();
	}

	/**
	 * 
	 * @param lsOldSchedulerDetail
	 * @param lsNewSchedulerDetail
	 * @return
	 */
	public boolean checkSchedulerModified(
			List<SchedulerDetail> lsOldSchedulerDetail,
			List<SchedulerDetail> lsNewSchedulerDetail) {
		if (lsOldSchedulerDetail == null && lsNewSchedulerDetail == null) {
			return false;
		} else if (lsOldSchedulerDetail != null
				&& lsOldSchedulerDetail.size() > 0
				&& lsNewSchedulerDetail != null
				&& lsNewSchedulerDetail.size() > 0) {
			if (lsOldSchedulerDetail.size() != lsNewSchedulerDetail.size()) {
				return true;
			} else {
				Collections.sort(lsOldSchedulerDetail);
				Collections.sort(lsNewSchedulerDetail);
				for (int i = 0; i < lsOldSchedulerDetail.size(); i++) {
					SchedulerDetail schedulerDetailOld = lsOldSchedulerDetail
							.get(i);
					SchedulerDetail schedulerDetailOldNew = lsNewSchedulerDetail
							.get(i);
					if (!schedulerDetailOld.getDATE().equalsIgnoreCase(
							schedulerDetailOldNew.getDATE())) {
						return true;
					}
				}
			}
		} else {
			return false;
		}
		return false;

	}

	/**
	 * 
	 * @param schedulerID
	 * @return
	 */
	public List<SchedulerAction> getJobData(int schedulerID) {
		Connection con = null;
		try {
			con = getConnection();
			List<SchedulerAction> lsSchedulerAction = schedulerHandler
					.getSchedulerAction(con, schedulerID);
			return lsSchedulerAction;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			Database.closeObject(con);
		}
		return null;
	}

	public void insertLog(int schedulerID, String fileURL, String httpDir,
			String result, String status) {
		Connection con = null;
		try {
			con = getConnection();
			schedulerHandler.insertSchedulerLog(con, schedulerID, fileURL,
					httpDir, result, status);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(con);
		}
	}

	public int updateScheduler(int schedulerID, String status) {
		Connection con = null;
		try {
			con = getConnection();
			return schedulerHandler.updateScheduler(con, schedulerID, status);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(con);
		}
		return -1;
	}

	protected void afterSession() throws Exception {
		tbScheduler.clear();
		tbSchedulerStore.clear();
		scheduler.stop();
		scheduler = null;
		super.afterSession();
	}
	
	public static void main(String [] agm)
	{
		
	}
	
}
