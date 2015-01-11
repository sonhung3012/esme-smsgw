package com.fis.esme.core.bean.scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import com.fis.esme.core.services.scheduler.entity.Scheduler;
import com.fis.esme.core.services.scheduler.entity.SchedulerAction;
import com.fis.esme.core.services.scheduler.entity.SchedulerDetail;
import com.fss.sql.Database;

public class MSSchedulerBeanOracleBase extends SMSSchedulerBean {
	public String mstrSchedulerLogSequence = "SCHEDULER_HISTORY_SQ";

	public Vector getschedulerActionParam(Connection con, int scha_id) {
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public int updateSchedulerAction(Connection con, String actionStatus) {
		return 0;
	}

	/**
	 * 
	 * @param Maxnumber
	 * @param timeReload
	 * @return
	 */
	public Vector reloadActionforScheduler(Connection con, int Maxnumber,
			int timeReload) {
		return null;
	}

	/**
	 * @return
	 * 
	 */
	public Vector getSchedulerAction(Connection mcnMain, int schedulerID) {
		PreparedStatement pstmSchedulerAction = null;
		try {
			String strSqlCommand = " SELECT CAMPAIGN.CUSTOMER_ID,CAMPAIGN_DETAIL.GROUP_ID,C.CAMPAIGN_ID,C.MESSAGE_ID,MESSAGE,CAMPAIGN.ADV_ID FROM CAMPAIGN_SCHEDULER C "
				+" INNER JOIN ESME_MESSAGE M ON C.MESSAGE_ID=M.MESSAGE_ID AND M.STATUS='1' "
				+" INNER JOIN ESME_MESSAGE_CONTENT T ON T.MESSAGE_ID=M.MESSAGE_ID "
				+" INNER JOIN CAMPAIGN ON CAMPAIGN.STATUS='1' AND (CAMPAIGN.EFFECT_DATE IS NULL OR CAMPAIGN.EFFECT_DATE <= SYSDATE) AND (CAMPAIGN.EXPIRE_DATE IS NULL OR CAMPAIGN.EXPIRE_DATE > SYSDATE) "
				+" INNER JOIN CAMPAIGN_DETAIL ON CAMPAIGN_DETAIL.CAMPAIGN_ID=CAMPAIGN.CAMPAIGN_ID "
				+" WHERE SCHEDULER_ID=?";
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
			String strSqlCommand = "UPDATE SCHEDULER SET SCHEDULER_STATUS =?  WHERE SCHEDULER_ID= ?";
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
			String strSqlCommand = "SELECT SCHEDULER_DETAIL_ID, SCHEDULER_ID, VALUE FROM SCHEDULER_DETAIL WHERE SCHEDULER_ID =? ";
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
			String strSqlCommand = " SELECT SCHEDULER_ID,NAME,TYPE, TIME,STATUS,SCHEDULER_STATUS FROM SCHEDULER WHERE STATUS ='1' AND TYPE IN('2','3') AND SCHEDULER_STATUS IN ('1','2') ";
			strSqlCommand += " UNION ALL ";
			strSqlCommand += " SELECT SCHEDULER_ID,NAME,TYPE, TIME,STATUS,SCHEDULER_STATUS FROM SCHEDULER WHERE STATUS ='1' AND TYPE ='1' AND SCHEDULER_STATUS='1' ";
			strSqlCommand += " UNION ALL ";
			strSqlCommand += " SELECT S.SCHEDULER_ID,S.NAME,S.TYPE, TIME,S.STATUS,S.SCHEDULER_STATUS  FROM SCHEDULER S "
					+ " INNER JOIN SCHEDULER_DETAIL D ON S.SCHEDULER_ID=D.SCHEDULER_ID "
					+ " WHERE S.STATUS ='1' AND S.TYPE ='4' AND S.SCHEDULER_STATUS IN('1') AND  D.VALUE = TO_CHAR(SYSDATE,'YYYYMMDD')  GROUP BY(S.SCHEDULER_ID,S.NAME,S.TYPE, TIME,S.STATUS,S.SCHEDULER_STATUS)";
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
			String resultCode, String desription, int totalSuccess,
			int totalFail, int campaignID) {
		PreparedStatement pstmInsertScheduler = null;
		try {
			String strSqlCommand = "INSERT INTO SCHEDULER_HISTORY(SCHEDULER_LOG_ID,RESULT_CODE,CREATED_DATE,DESCRIPTION,TOTAL_SUCCESS,TOTAL_FAIL,CAMPAIGN_ID,SCHEDULE_ID) VALUES(SCHEDULER_HISTORY_SEQ.NEXTVAL,?,SYSDATE,?,?,?,?,?) ";
			pstmInsertScheduler = mcnMain.prepareStatement(strSqlCommand);
			pstmInsertScheduler.setString(1, resultCode);
			pstmInsertScheduler.setString(2, desription);
			pstmInsertScheduler.setInt(3, totalSuccess);
			pstmInsertScheduler.setInt(4, totalFail);
			pstmInsertScheduler.setInt(5, campaignID);
			pstmInsertScheduler.setInt(6, schedulerID);
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
		return Database.getSequenceValue(con, sequenceName);
	}

	@Override
	public void insertSchedulerLog(Connection mcnMain, int schedulerID,
			String fileURL, String httpDir, String result, String status) {
		// TODO Auto-generated method stub
		
	}

}
