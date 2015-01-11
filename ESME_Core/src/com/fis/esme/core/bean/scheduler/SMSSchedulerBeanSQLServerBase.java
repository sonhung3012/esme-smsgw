package com.fis.esme.core.bean.scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;
import com.fss.sql.Database;

public class SMSSchedulerBeanSQLServerBase {
	public String mstrSchedulerLogSequence = "SCHEDULER_HISTORY_SQ";

	public Vector getschedulerActionParam(Connection con, int scha_id) {
		PreparedStatement pstmSelectSchedulerActionParam = null;
		try {
			String strSqlCommand = "SELECT SAP_ID, SCHA_ID, MAP.AP_ID, VALUE,PA.NAME FROM PAM_SCHA_ACTION_PARAM MAP,PAM_ACTION_PARAMETER AP,PAM_PARAMETER PA WHERE SCHA_ID = ? AND MAP.AP_ID= AP.AP_ID AND PA.PARAMETER_ID=AP.PARAMETER_ID";
			pstmSelectSchedulerActionParam = con
					.prepareStatement(strSqlCommand);
			pstmSelectSchedulerActionParam.setInt(1, scha_id);
			return Database.convertToVector(pstmSelectSchedulerActionParam
					.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmSelectSchedulerActionParam);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public int updateSchedulerAction(Connection con,String actionStatus) {
		PreparedStatement pstmUpdateSchedulerAction = null;
		try {
			String strSqlCommand = "UPDATE SCHEDULER_ACTION SET LAST_DATETIME = GETDATE(),EXECUTING_STATUS =? AND EXECUTED_TIMES = EXECUTED_TIMES + 1  ";
			pstmUpdateSchedulerAction = con.prepareStatement(strSqlCommand);
			pstmUpdateSchedulerAction.setString(1, actionStatus);
			return pstmUpdateSchedulerAction.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmUpdateSchedulerAction);
		}
		return 0;
	}

	/**
	 * 
	 * @param Maxnumber
	 * @param timeReload
	 * @return
	 */
	public Vector reloadActionforScheduler(Connection con,int Maxnumber, int timeReload) {
		PreparedStatement pstmSchedulerActionFail = null;
		try {
			String strSqlCommand = "SELECT SCHA_ID,SCHEDULER_ID,MESSAGE_ID,GROUP_ID,STATUS,LAST_DATETIME,EXECUTING_STATUS, EXECUTED_TIMES FROM SCHEDULER_ACTION WHERE STATUS IN ('1') AND EXECUTING_STATUS IN ('1') AND EXECUTED_TIMES < ? AND LAST_DATETIME < GETDATE() - ? * 1/(24*60)";
			pstmSchedulerActionFail = con.prepareStatement(strSqlCommand);
			pstmSchedulerActionFail.setInt(1, Maxnumber);
			pstmSchedulerActionFail.setInt(2, timeReload);
			return Database.convertToVector(pstmSchedulerActionFail
					.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmSchedulerActionFail);
		}
		return null;
	}

	/**
	 * @return
	 * 
	 */
	public Vector getSchedulerAction(Connection mcnMain, int schedulerID) {
		PreparedStatement pstmSchedulerAction = null;
		try {
			String strSqlCommand = "SELECT SCHA_ID,SCHEDULER_ID,MESSAGE_ID,GROUP_ID,STATUS,LAST_DATETIME,EXECUTING_STATUS,EXECUTED_TIMES  FROM SCHEDULER_ACTION WHERE STATUS IN ('1') AND SCHEDULER_ID = ?";
			pstmSchedulerAction = mcnMain.prepareStatement(strSqlCommand);
			pstmSchedulerAction.setInt(1, schedulerID);
			return Database.convertToVector(pstmSchedulerAction.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmSchedulerAction);
		}
		return null;
	}

	/**
	 * 
	 * @param mcnMain
	 * @param status
	 * @return
	 */
	public int updateScheduler(Connection mcnMain, int schedulerID,
			String status) {
		PreparedStatement pstmUpdateScheduler = null;
		try {
			String strSqlCommand = "UPDATE SCHEDULER SET SCHEDULER_STATUS =?, LAST_UPDATE=GETDATE() WHERE SCHEDULER_ID= ?";
			pstmUpdateScheduler = mcnMain.prepareStatement(strSqlCommand);
			pstmUpdateScheduler.setString(1, status);
			pstmUpdateScheduler.setInt(2, schedulerID);
			return pstmUpdateScheduler.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmUpdateScheduler);
		}
		return -1;
	}

	/**
	 * 
	 * @param mcnMain
	 * @param schedulerID
	 * @return
	 */
	public Vector getSchedulerDetail(Connection mcnMain, int schedulerID) {
		PreparedStatement pstmSchedulerDetail = null;
		try {
			String strSqlCommand = "SELECT SCHEDULER_DETAIL_ID, SCHEDULER_ID, DATE FROM SCHEDULER_DETAIL WHERE SCHEDULER_ID =? ";
			pstmSchedulerDetail = mcnMain.prepareStatement(strSqlCommand);
			pstmSchedulerDetail.setInt(1, schedulerID);
			return Database.convertToVector(pstmSchedulerDetail.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmSchedulerDetail);
		}
		return null;
	}

	/**
	 * 
	 * @param mcnMain
	 * @return
	 */
	public Vector getScheduler(Connection mcnMain) {
		PreparedStatement pstmScheduler = null;
		try {
			String strSqlCommand = " SELECT SCHEDULER_ID,NAME,TYPE, TIME,STATUS,SCHEDULER_STATUS FROM SCHEDULER WHERE STATUS ='1' AND TYPE IN('2','3') AND SCHEDULER_STATUS IN ('0','2') ";
			strSqlCommand += " UNION ALL ";
			strSqlCommand += " SELECT SCHEDULER_ID,NAME,TYPE, TIME,STATUS,SCHEDULER_STATUS FROM SCHEDULER WHERE STATUS ='1' AND TYPE ='1' AND SCHEDULER_STATUS='0' ";
			pstmScheduler = mcnMain.prepareStatement(strSqlCommand);
			return Database.convertToVector(pstmScheduler.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmScheduler);
		}
		return null;
	}

	public void insertSchedulerLog(Connection mcnMain, int schedulerID,
			String fileURL, String httpDir, String result, String status) {
		PreparedStatement pstmInsertScheduler = null;
		try {
			String strSchedulerHis = getSequence(mcnMain,
					mstrSchedulerLogSequence);
			int intSchedulerHis = Integer.valueOf(strSchedulerHis);
			String strSqlCommand = "INSERT INTO SCHEDULER_HISTORY(HISTORY_ID,SCHEDULER_ID,ACTION_TIME,FILE_URL,HTTP_URL,RESULT,STATUS) VALUES(?,?,?,?,?,?,?) ";
			pstmInsertScheduler = mcnMain.prepareStatement(strSqlCommand);
			pstmInsertScheduler.setInt(1, intSchedulerHis);
			pstmInsertScheduler.setInt(2, schedulerID);
			java.sql.Timestamp time = new Timestamp(
					new java.util.Date().getTime());
			pstmInsertScheduler.setTimestamp(3, time);
			pstmInsertScheduler.setString(4, fileURL);
			pstmInsertScheduler.setString(5, httpDir);
			pstmInsertScheduler.setString(6, result);
			pstmInsertScheduler.setString(7, status);
			pstmInsertScheduler.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmInsertScheduler);
		}
	}

	public String getSequence(Connection con, String sequenceName)
			throws Exception {
		PreparedStatement pstmSequence = null;
		try {
			pstmSequence = con.prepareStatement("SELECT NEXT VALUE FOR "
					+ sequenceName);
			ResultSet rsSequence = pstmSequence.executeQuery();
			Vector vtTemp = Database.convertToVector(rsSequence);
			return String.valueOf(((Vector) vtTemp.elementAt(0)).get(0));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmSequence);
		}
		return null;
	}
}
