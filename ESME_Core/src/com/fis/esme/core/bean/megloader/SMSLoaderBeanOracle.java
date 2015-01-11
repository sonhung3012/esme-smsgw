package com.fis.esme.core.bean.megloader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import com.fss.sql.Database;

public class SMSLoaderBeanOracle extends SMSLoaderBean{
	public Vector getMOMessage(Connection con, int iRowNumber,
			int intPartitionRank, int intRetryRowNumber, int intRetrySpace,
			int intRetryNumber) throws Exception{
		PreparedStatement pstmtSelectData = null;
		try {
			String strSelect = " SELECT MO_ID,REQUEST_ID,MESSAGE,REQUEST_TIME,STATUS,MSISDN,SHORT_CODE,RETRY_NUMBER,LAST_UPDATE,TYPE,1 LOAD_TYPE, "
					+ " SERVICE_ID,SERVICE_PARENT_ID,SERVICE_ROOR_ID,CP_ID,SMSC_ID,SHORT_CODE_ID,COMMAND_ID,SMS_LOG_ID,PROTOCAL FROM ESME_SMS_MO "
					+ " WHERE STATUS =0 AND REQUEST_TIME BETWEEN SYSDATE - "
					+ intPartitionRank + "	AND SYSDATE + 0.1 ";

			if (iRowNumber > 0) {
				strSelect += " AND rownum <= " + iRowNumber;
			}

			String strLoadFailMsgSQL = " UNION ALL"
					+ " SELECT MO_ID,REQUEST_ID,MESSAGE,REQUEST_TIME,STATUS,MSISDN,SHORT_CODE,RETRY_NUMBER,LAST_UPDATE,TYPE,2 LOAD_TYPE,"
					+ " SERVICE_ID,SERVICE_PARENT_ID,SERVICE_ROOR_ID,CP_ID,SMSC_ID,SHORT_CODE_ID,COMMAND_ID,SMS_LOG_ID,PROTOCAL  FROM ESME_SMS_MO "
					+ " WHERE STATUS in (2,3) AND REQUEST_TIME BETWEEN SYSDATE - "
					+ intPartitionRank + "	AND SYSDATE + 0.1 ";
			if (intRetrySpace > 0) {
				strLoadFailMsgSQL += " AND LAST_UPDATE <= SYSDATE - 1/(24 * 60) * "
						+ intRetrySpace;
			}

			if (intRetryNumber > 0) {
				strLoadFailMsgSQL += " AND RETRY_NUMBER <= " + intRetryNumber;
			}

			if (intRetryRowNumber > 0) {
				strLoadFailMsgSQL += " AND rownum <= " + intRetryRowNumber;
			}

			strSelect += strLoadFailMsgSQL;
			pstmtSelectData = con.prepareStatement(strSelect);
			return Database.convertToVector(pstmtSelectData.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			// TODO: handle exception

		} finally {
			Database.closeObject(pstmtSelectData);
		}
	}

	public int updateMOStatus(Connection con, String mt_id, String status) {
		PreparedStatement pstmtUpdateStatus = null;
		try {
			String strUpdateStatus = " Update ESME_SMS_MO set status = ?, LAST_UPDATE=sysdate where MO_ID = ? ";
			pstmtUpdateStatus = con.prepareStatement(strUpdateStatus);
			pstmtUpdateStatus.setString(1, status);
			pstmtUpdateStatus.setString(2, mt_id);
			return pstmtUpdateStatus.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		} finally {
			Database.closeObject(pstmtUpdateStatus);
		}
	}

	public Vector getMTMessage(Connection con, int iRowNumber,
			int intPartitionRank, int intRetryRowNumber, int intRetrySpace,
			int intRetryNumber, int intReloadSpace, int intReloadNumber)throws Exception{
		PreparedStatement pstmtSelectData = null;
		try {
			String strSelect = " SELECT MT_ID,REQUEST_ID,REQUEST_TIME,MESSAGE,CP_ID,SHORT_CODE, "
					+ " MSISDN,RETRY_NUMBER,COMMAND_CODE,REGISTER_DELIVERY_REPORT,LAST_RETRY_TIME,STATUS,1 LOAD_TYPE, SMS_LOG_ID, ESME_TRANS_ID, MO_SEQUENCE_NUMBER, FILE_UPLOAD_ID,SUB_ID,GROUP_ID,CUSTOMER_ID,ADV_ID,CAMPAIGN_ID FROM ESME_SMS_MT "
					+ " WHERE STATUS =0 AND REQUEST_TIME BETWEEN SYSDATE - "
					+ intPartitionRank + "	AND SYSDATE + 0.1 ";

			if (iRowNumber > 0) {
				strSelect += " AND rownum <= " + iRowNumber;
			}

			String strLoadFailMsgSQL = " UNION ALL"
					+ " SELECT MT_ID,REQUEST_ID,REQUEST_TIME,MESSAGE,CP_ID,SHORT_CODE, "
					+ " MSISDN,RETRY_NUMBER,COMMAND_CODE,REGISTER_DELIVERY_REPORT,LAST_RETRY_TIME,STATUS,2 LOAD_TYPE, SMS_LOG_ID, ESME_TRANS_ID, MO_SEQUENCE_NUMBER, FILE_UPLOAD_ID,SUB_ID,GROUP_ID,CUSTOMER_ID,ADV_ID,CAMPAIGN_ID FROM ESME_SMS_MT "
					+ " WHERE STATUS =2 AND REQUEST_TIME BETWEEN SYSDATE - "
					+ intPartitionRank + "	AND SYSDATE + 0.1 ";
			if (intRetrySpace > 0) {
				strLoadFailMsgSQL += " AND LAST_RETRY_TIME <= SYSDATE - 1/(24 * 60) * "
						+ intRetrySpace;
			}

			if (intRetryNumber > 0) {
				strLoadFailMsgSQL += " AND RETRY_NUMBER <= " + intRetryNumber;
			}

			if (intRetryRowNumber > 0) {
				strLoadFailMsgSQL += " AND rownum <= " + intRetryRowNumber;
			}

			if (intReloadSpace <= 0)
				intReloadSpace = 3;

			String strNotloadedMsgSQL = " UNION ALL"
					+ " SELECT MT_ID,REQUEST_ID,REQUEST_TIME,MESSAGE,CP_ID,SHORT_CODE, "
					+ " MSISDN,RETRY_NUMBER,COMMAND_CODE,REGISTER_DELIVERY_REPORT,LAST_RETRY_TIME,STATUS,3 LOAD_TYPE, SMS_LOG_ID, ESME_TRANS_ID, MO_SEQUENCE_NUMBER, FILE_UPLOAD_ID,SUB_ID,GROUP_ID,CUSTOMER_ID,ADV_ID,CAMPAIGN_ID FROM ESME_SMS_MT "
					+ " WHERE STATUS =3 AND REQUEST_TIME BETWEEN SYSDATE - "
					+ intPartitionRank + "	AND SYSDATE + 0.1 ";
			if (intReloadSpace > 0) {
				strNotloadedMsgSQL += " AND LAST_RETRY_TIME <= SYSDATE - 1/24/60 * "
						+ intReloadSpace;
			}
			if (intReloadNumber > 0) {
				strNotloadedMsgSQL += " AND RELOAD_NUMBER <= " + intRetryNumber;
			}

			if (intRetryRowNumber > 0) {
				strNotloadedMsgSQL += " AND rownum <= " + intRetryRowNumber;
			}

			strSelect += strLoadFailMsgSQL;

			if (intReloadNumber > 0) {
				strSelect += strNotloadedMsgSQL;
			}
			pstmtSelectData = con.prepareStatement(strSelect);
			return Database.convertToVector(pstmtSelectData.executeQuery());

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			throw e;
		} finally {
			Database.closeObject(pstmtSelectData);
		}

	}

	public int updateMTStatus(Connection con, String mt_id, String status,
			String type) throws SQLException {
		PreparedStatement pstmtUpdateStatus = null;
		try {
			String strUpdateStatus = " UPDATE ESME_SMS_MT SET STATUS = ?, LAST_RETRY_TIME=SYSDATE WHERE MT_ID = ? ";
			pstmtUpdateStatus=con.prepareStatement(strUpdateStatus);
			pstmtUpdateStatus.setString(1, status);
			pstmtUpdateStatus.setString(2, mt_id);
			return pstmtUpdateStatus.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmtUpdateStatus);
		}

	}
}
