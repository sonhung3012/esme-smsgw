package com.fis.esme.core.services.scheduler.entity;

/*
 * cron4j - A pure Java cron-like scheduler
 * 
 * Copyright (C) 2007-2010 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version
 * 2.1, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License 2.1 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License version 2.1 along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

import com.fis.esme.core.app.AppManager;
import com.fis.esme.core.bean.SmsBeanFactory;
import com.fis.esme.core.bean.scheduler.SMSSchedulerBean;
import com.fis.esme.core.bean.scheduler.SMSSchedulerBeanFactory;
import com.fis.esme.core.bean.scheduler.SMSSchedulerBeanOracleEx;
import com.fis.esme.core.bean.subscriber.SMSSubscriberFactory;
import com.fis.esme.core.util.GlobalParameter;
import com.fss.sql.Database;
import com.fss.util.AppException;
import com.fss.util.StringUtil;

import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

/**
 * This task counts from 1 to 30.
 */
public class SchedulerTaskEx extends SchedulerTask {
	protected int intSchedulerID;
	public String mstrShortcode;
	public String mstrCommandcode;
	public String mstrCpID;
	protected String strSchedulerName;
	protected SchedulerSynchronizedThreadEx schedulerLoader = null;
	protected String strFilename = "";
	private SMSSchedulerBeanOracleEx bean=new SMSSchedulerBeanOracleEx();
	
	public void setSchedulerID(int id) {
		this.intSchedulerID = id;
	}

	public String getSchedulerName() {
		return this.strSchedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.strSchedulerName = schedulerName;
	}

	public int updateSchedulerFail() {
		Connection con = null;
		try {
			con = AppManager.getAppConnection();
			return SMSSchedulerBeanFactory.getSmsBeanFactory().updateScheduler(
					con, getSchedulerID(),
					GlobalParameter.SCHEDULER_STATUS_FAIL);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return -1;
		} finally {
			Database.closeObject(con);
		}
	}

	public int updateSchedulerInProgress() {
		Connection con = null;
		try {
			con = AppManager.getAppConnection();
			return SMSSchedulerBeanFactory.getSmsBeanFactory().updateScheduler(
					con, getSchedulerID(),
					GlobalParameter.SCHEDULER_STATUS_PROCESSING);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return -1;
		} finally {
			Database.closeObject(con);
		}
	}

	public int updateSchedulerDone() {
		Connection con = null;
		try {
			con = AppManager.getAppConnection();
			return SMSSchedulerBeanFactory.getSmsBeanFactory().updateScheduler(
					con, getSchedulerID(),
					GlobalParameter.SCHEDULER_STATUS_DONE);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return -1;
		} finally {
			Database.closeObject(con);
		}
	}

	public int getSchedulerID() {
		return this.intSchedulerID;
	}

	public void setSchedulerLoader(SchedulerSynchronizedThreadEx loader) {
		this.schedulerLoader = loader;
	}

	public boolean canBePaused() {
		return true;
	}

	public boolean canBeStopped() {
		return true;
	}

	public boolean supportsCompletenessTracking() {
		return true;
	}

	public boolean supportsStatusTracking() {
		return true;
	}

	public void execute(TaskExecutionContext context) throws RuntimeException {
		Connection con = null;
		try {
			schedulerLoader.debugMonitor("Start executing schedule named:"
					+ getSchedulerName(), 9);
			con = AppManager.getAppConnection();
			List<SchedulerActionOracle> lsSchedulerAction = bean.getSchedulerAction(con,
							getSchedulerID());
			if (lsSchedulerAction == null || lsSchedulerAction.size() <= 0) {
				schedulerLoader.debugMonitor(
						"The Schedule named:" + getSchedulerName()
								+ " not have actions need to run!", 9);
			}
			String strResult = "";
			for (int i = 0; i < lsSchedulerAction.size(); i++) {
				SchedulerActionOracle schedulerAction = lsSchedulerAction.get(i);
				strResult += insertSMS2Send(con, schedulerAction);
			}
			if (strResult == null || strResult.equalsIgnoreCase("")) {
				strResult = "Task named:" + getSchedulerName()
						+ " no records executed! ";
			}
			SMSSchedulerBeanFactory.getSmsBeanFactory().insertSchedulerLog(con,
					getSchedulerID(), "", "", strResult, "1");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(con);
		}
	}

	public String insertSMS2Send(Connection con, SchedulerActionOracle schedulerObject) {
		try {
			String messageContent = schedulerObject.getMESSAGE();
			String strResult = "";
				Vector vtSubscriber = SMSSubscriberFactory
						.getSmsSubscriberBean().getSubscriberGroups(con,
								String.valueOf(schedulerObject.getGROUP_ID()));
				int iSucessfullCounter = 0;
				int iFailCounter = 0;
				if (vtSubscriber != null && vtSubscriber.size() > 0) {
					for (int j = 0; j < vtSubscriber.size(); j++) {
						Vector vtOnerow = (Vector) vtSubscriber.get(j);
						String subid = StringUtil.nvl(vtOnerow.get(0), "0");
						String msisdn = StringUtil.nvl(vtOnerow.get(1), "0");
						try {
							SmsBeanFactory.getSmsBeanFactoryEx().insertSMSMTEx(con,
									"0", messageContent, mstrCpID,
									mstrShortcode, msisdn, mstrCommandcode,
									"0", "0", "0", subid,
									String.valueOf(schedulerObject.getGROUP_ID()),schedulerObject.getCUSTOMER_ID(),schedulerObject.getCAMPAIGN_ID(),schedulerObject.getADV_ID());
							iSucessfullCounter++;
						} catch (Exception e) {
							// TODO: handle exception
							iFailCounter++;
						}
					}

					String display = "Task named:" + getSchedulerName()
							+ " executed with groupid:"
							+ String.valueOf(schedulerObject.getGROUP_ID())
							+ " Total sucessfull :" + iSucessfullCounter
							+ " and Total FailCounter :" + iFailCounter;
					strResult += display + "\n";
					schedulerLoader.debugMonitor(display, 9);

				}
			return strResult;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return null;
		}

	}

	protected void writeFile(String filename, String datas) throws IOException {
		File file = new File(filename);
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(datas);
		bw.close();
		fw.close();
	}
}
