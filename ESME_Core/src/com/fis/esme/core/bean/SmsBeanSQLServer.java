package com.fis.esme.core.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.fis.esme.core.entity.SubscriberObject;
import com.fss.sql.Database;
import com.fss.util.StringUtil;

public class SmsBeanSQLServer extends SmsBean {


	public SmsBeanSQLServer() {

	}

	/**
	 * 
	 * @param status
	 * @param mo_id
	 * @return
	 */
	public int updateSMSMORetry(Connection mcnMain, String status, String mo_id) {
		PreparedStatement pstmtUpdateMORetry = null;
		try {
			String strUpdateSMSMOSRetry = " update esme_sms_mo set status = ?,RETRY_NUMBER = ISNULL(RETRY_NUMBER,0)+1, last_update= getdate() where mo_id = ?  ";
			pstmtUpdateMORetry = mcnMain.prepareStatement(strUpdateSMSMOSRetry);
			pstmtUpdateMORetry.setString(1, status);
			pstmtUpdateMORetry.setString(2, mo_id);
			return pstmtUpdateMORetry.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmtUpdateMORetry);
		}
		return 0;
	}

	/**
	 * 
	 * @param status
	 * @param mo_id
	 * @return
	 */
	public int updateSMSMOStatus(Connection mcnMain, String status, String mo_id) {
		PreparedStatement pstmtUpdateMOStatus = null;
		try {
			// update mo status
			String strUpdateSMSMOStatus = " update esme_sms_mo set status = ?, last_update=getdate() where mo_id = ?  ";
			pstmtUpdateMOStatus = mcnMain
					.prepareStatement(strUpdateSMSMOStatus);
			pstmtUpdateMOStatus.setString(1, status);
			pstmtUpdateMOStatus.setString(2, mo_id);
			return pstmtUpdateMOStatus.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmtUpdateMOStatus);
		}
		return 0;
	}

	/**
	 * 
	 * @param status
	 * @param error_code
	 * @param log_id
	 * @return
	 */
	public int updateSMSLogStatus(Connection mcnMain, String status,
			String error_code, String log_id, String tranid) {
		PreparedStatement pstmtUpdateSMSLogStatus = null;
		try {
			// update status sms log
			String strUpdateSMSLogStatus = "update esme_sms_log set status =? ";
			if (error_code != null && error_code != "") {
				strUpdateSMSLogStatus += ", error_code= '" + error_code + "'";
			}
			if (tranid != "" && !tranid.equalsIgnoreCase("")) {
				strUpdateSMSLogStatus += ", ack_id= '" + tranid + "'";
			}
			strUpdateSMSLogStatus += " ,last_update=getdate() where log_id =? ";
			pstmtUpdateSMSLogStatus = mcnMain
					.prepareStatement(strUpdateSMSLogStatus);
			pstmtUpdateSMSLogStatus.setString(1, status);
			pstmtUpdateSMSLogStatus.setString(2, log_id);
			return pstmtUpdateSMSLogStatus.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmtUpdateSMSLogStatus);
		}
		return 0;
	}

	/**
	 * 
	 * @param status
	 * @param ack_id
	 * @param log_id
	 * @return
	 */
	public int updateSMSLogTranID(Connection mcnMain, String status,
			String smsc_delivery_id, String ack_id) {
		PreparedStatement pstmtUpdateSMSLogTranid = null;
		try {
			// update delivery report
			String strUpdateSMSLogTranid = " update esme_sms_log set status =?,SMSC_DELIVERY_ID=?,ack_date = getdate(),last_update=getdate() where ack_id =? ";
			pstmtUpdateSMSLogTranid = mcnMain
					.prepareStatement(strUpdateSMSLogTranid);
			pstmtUpdateSMSLogTranid.setString(1, status);
			pstmtUpdateSMSLogTranid.setString(2, smsc_delivery_id);
			pstmtUpdateSMSLogTranid.setString(3, ack_id);
			return pstmtUpdateSMSLogTranid.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeObject(pstmtUpdateSMSLogTranid);
		}
		return 0;
	}

	/**
	 * 
	 * @param iScheduleId
	 * @param strContent
	 * @throws Exception
	 */
	public int updateSMSRetryStatus(Connection mcnMain, int status,
			String sms_log_id, int iMtId) throws Exception {
		PreparedStatement pstmtUpdateRetryStatus = null;
		try {
			String strSqlUpdateRetryStatus = " UPDATE ESME_SMS_MT SET STATUS = ? ,sms_log_id=?, RETRY_NUMBER = ISNULL(RETRY_NUMBER,0)+1 ,  LAST_RETRY_TIME = getdate() WHERE MT_ID = ? ";
			pstmtUpdateRetryStatus = mcnMain
					.prepareStatement(strSqlUpdateRetryStatus);
			pstmtUpdateRetryStatus.setInt(1, status);
			pstmtUpdateRetryStatus.setString(2, sms_log_id);
			pstmtUpdateRetryStatus.setInt(3, iMtId);
			return pstmtUpdateRetryStatus.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmtUpdateRetryStatus);
		}
		return -1;
	}

	public int updateSMSReloadStatus(Connection mcnMain, String status,
			int iMtId) throws Exception {
		PreparedStatement pstmtUpdateReloadStatus = null;
		try {
			String strSqlUpdateReloadStatus = " UPDATE ESME_SMS_MT SET STATUS = ? , RELOAD_NUMBER = ISNULL(RETRY_NUMBER,0)+1 ,  LAST_RETRY_TIME = getdate() WHERE MT_ID = ? ";
			pstmtUpdateReloadStatus = mcnMain
					.prepareStatement(strSqlUpdateReloadStatus);
			pstmtUpdateReloadStatus.setString(1, status);
			pstmtUpdateReloadStatus.setInt(2, iMtId);
			return pstmtUpdateReloadStatus.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmtUpdateReloadStatus);
		}
		return -1;
	}

	/**
	 * 
	 * @param smslogId
	 * @param request_id
	 * @param msisdn
	 * @param status
	 * @param type
	 * @param has_response
	 * @param service_id
	 * @param service_parent_id
	 * @param service_roor_id
	 * @param cp_id
	 * @param smsc_id
	 * @param short_code_id
	 * @param command_id
	 * @param ack_id
	 * @param ack_date
	 * @param sms_content
	 * @param total_sms
	 * @param source_sms_id
	 * @param error_code
	 * @throws Exception
	 */
	public void insertSMSLog(Connection mcnMain,String smslogId, String request_id, String msisdn,
			String status, String type, String response_id, String service_id,
			String service_parent_id, String service_roor_id, String cp_id,
			String smsc_id, String short_code_id, String command_id,
			String ack_id, String ack_date, String sms_content,
			String total_sms, String source_sms_id, String error_code,
			String strFileUploadID, String subId, String groupID,int customerid,int advid,int campaignid)
			throws Exception {
		PreparedStatement pstmtInsertSMSLog = null;
		try {
			String strSqlSMSLog = " INSERT INTO esme_sms_log (log_id,request_id,msisdn,request_time,status,type, "
					+ " response_id,service_id,service_parent_id,service_roor_id,cp_id,smsc_id,short_code_id,command_id, "
					+ " ack_id,ack_date,last_update,sms_content,total_sms,source_sms_id,error_code, file_upload_id,sub_id,group_id,customer_id,adv_id,campaign_id) "
					+ " VALUES(?,?,?,getdate(),?,?,?,?,?,?,?,?,?,?,?,?,getdate(),?,?,?,?,?,?,?,?,?,?) ";
			pstmtInsertSMSLog = mcnMain.prepareStatement(strSqlSMSLog);
			pstmtInsertSMSLog.setString(1, smslogId);
			pstmtInsertSMSLog.setString(2, request_id);
			pstmtInsertSMSLog.setString(3, msisdn);
			pstmtInsertSMSLog.setString(4, status);
			pstmtInsertSMSLog.setString(5, type);
			pstmtInsertSMSLog.setString(6, response_id);
			pstmtInsertSMSLog.setString(7, service_id);
			pstmtInsertSMSLog.setString(8, service_parent_id);
			pstmtInsertSMSLog.setString(9, service_roor_id);
			pstmtInsertSMSLog.setString(10, cp_id);
			pstmtInsertSMSLog.setString(11, smsc_id);
			pstmtInsertSMSLog.setString(12, short_code_id);
			pstmtInsertSMSLog.setString(13, command_id);
			pstmtInsertSMSLog.setString(14, ack_id);
			pstmtInsertSMSLog.setString(15, ack_date);
			pstmtInsertSMSLog.setString(16, sms_content);
			pstmtInsertSMSLog.setString(17, total_sms);
			pstmtInsertSMSLog.setString(18, source_sms_id);
			pstmtInsertSMSLog.setString(19, error_code);
			pstmtInsertSMSLog.setString(20, strFileUploadID);
			pstmtInsertSMSLog.setString(21, subId);
			pstmtInsertSMSLog.setString(22, groupID);
			pstmtInsertSMSLog.setInt(23, customerid);
			pstmtInsertSMSLog.setInt(24, advid);
			pstmtInsertSMSLog.setInt(25, campaignid);
			pstmtInsertSMSLog.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		} finally {
			Database.closeObject(pstmtInsertSMSLog);
		}

	}

	/**
	 * 
	 * @param requestid
	 * @param message
	 * @param status
	 * @param msisdn
	 * @param shortcode
	 * @param retry_number
	 * @param type
	 * @param reason
	 * @return
	 */
	public int insertSMSMO(Connection mcnMain, String mo_id, String requestid,
			String message, String status, String msisdn, String shortcode,
			String retry_number, String type, String reason, int service_id,
			int service_parent_id, int service_roor_id, int cp_id, int smsc_id,
			int short_code_id, int command_id, String sms_log_id,
			String protocal, String subid, String groupid) {
		PreparedStatement pstmInsertMO = null;
		try {
			String strSqlCommand = " insert into esme_sms_mo(request_id,message,request_time,status,msisdn,short_code,retry_number,last_update,type,reason,mo_id,service_id,service_parent_id,service_roor_id,cp_id,smsc_id,short_code_id,command_id,SMS_LOG_ID,protocal,sub_id,group_id) "
					+ " values(?,?,getdate(),?,?,?,?,getdate(),?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			pstmInsertMO = mcnMain.prepareStatement(strSqlCommand);
			pstmInsertMO.setString(1, requestid);
			pstmInsertMO.setString(2, message);
			pstmInsertMO.setString(3, status);
			pstmInsertMO.setString(4, msisdn);
			pstmInsertMO.setString(5, shortcode);
			pstmInsertMO.setString(6, retry_number);
			pstmInsertMO.setString(7, type);
			pstmInsertMO.setString(8, reason);
			pstmInsertMO.setString(9, mo_id);
			pstmInsertMO.setInt(10, service_id);
			pstmInsertMO.setInt(11, service_parent_id);
			pstmInsertMO.setInt(12, service_roor_id);
			pstmInsertMO.setInt(13, cp_id);
			pstmInsertMO.setInt(14, smsc_id);
			pstmInsertMO.setInt(15, short_code_id);
			pstmInsertMO.setInt(16, command_id);
			pstmInsertMO.setString(17, sms_log_id);
			pstmInsertMO.setString(18, protocal);
			pstmInsertMO.setString(19, subid);
			pstmInsertMO.setString(20, groupid);
			return pstmInsertMO.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			Database.closeObject(pstmInsertMO);
		}
		return -1;
	}

	/**
	 * 
	 * @param requestid
	 * @param message
	 * @param cp_id
	 * @param shortcode
	 * @param msisdn
	 * @param commandcode
	 * @param registerDl
	 * @return
	 * @throws Exception 
	 */
	public int insertSMSMT(Connection mcnMain, String requestid,
			String message, String cp_id, String shortcode, String msisdn,
			String commandcode, String registerDl, String strESMETransID,
			String strMOSequenceNumber, String subid, String groupuid) throws Exception {
		PreparedStatement pstmInsertMT = null;
		try {
			String strSqlCommand = " insert into ESME_SMS_MT(MT_ID,REQUEST_ID,REQUEST_TIME,MESSAGE,CP_ID,SHORT_CODE,MSISDN,RETRY_NUMBER,COMMAND_CODE,REGISTER_DELIVERY_REPORT,STATUS, ESME_TRANS_ID, MO_SEQUENCE_NUMBER,sub_id,group_id)"
					+ " values ( ?,?,getdate(),?, ?,?,?,'0',?,?,'0', ?, ?,?,?) ";
			pstmInsertMT = mcnMain.prepareStatement(strSqlCommand);
			pstmInsertMT.setString(1, getSequence(mcnMain, "sms_mt_seq"));
			pstmInsertMT.setString(2, requestid);
			pstmInsertMT.setString(3, message);
			pstmInsertMT.setString(4, cp_id);
			pstmInsertMT.setString(5, shortcode);
			pstmInsertMT.setString(6, msisdn);
			pstmInsertMT.setString(7, commandcode);
			pstmInsertMT.setString(8, registerDl);
			pstmInsertMT.setString(9, strESMETransID);
			pstmInsertMT.setString(10, strMOSequenceNumber);
			pstmInsertMT.setString(11, subid);
			pstmInsertMT.setString(12, groupuid);
			return pstmInsertMT.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			Database.closeObject(pstmInsertMT);
		}
	}

	public Vector loadPassword(Connection mcnMain, String strUser) {
		PreparedStatement pstmLoadPassword = null;
		try {
			String strSqlCommand = "SELECT A.CP_ID, A.USERNAME, A.PASSWORD "
					+ "FROM ESME_CP A WHERE A.STATUS = 1 "
					+ "AND A.USERNAME = ?";
			pstmLoadPassword = mcnMain.prepareStatement(strSqlCommand);
			pstmLoadPassword.setString(1, strUser);
			Vector vtData = Database.convertToVector(pstmLoadPassword
					.executeQuery());
			return (Vector) vtData.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Database.closeObject(pstmLoadPassword);
		}
	}

	public int updateMTForMO(Connection mcnMain,String strResponseID, String strLogID,
			String strMSISDN) throws Exception {
		PreparedStatement pstmUpdateMTForMO = null;
		try {
			String strSqlCommand = "UPDATE ESME_SMS_LOG SET RESPONSE_ID = ? "
					+ "WHERE LOG_ID = ? AND MSISDN = ?";
			pstmUpdateMTForMO = mcnMain.prepareStatement(strSqlCommand);
			pstmUpdateMTForMO.setString(1, strResponseID);
			pstmUpdateMTForMO.setString(2, strLogID);
			pstmUpdateMTForMO.setString(3, strMSISDN);
			return pstmUpdateMTForMO.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmUpdateMTForMO);
		}
		return 0;

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
	
	public Vector getSubcriberID(Connection con, String isdn) {
		PreparedStatement pstmtSelectSubsciber = null;
		try {
			String strCommand = " select s.sub_id,g.group_id from subscriber s,sub_group map,groups g "
					+ " where s.status='1' and s.msisdn=? and s.sub_id=map.sub_id "
					+ " and map.group_id=g.group_id and g.status ='1'";
			pstmtSelectSubsciber = con.prepareStatement(strCommand);
			pstmtSelectSubsciber.setString(1, isdn);
			return Database
					.convertToVector(pstmtSelectSubsciber.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmtSelectSubsciber);
		}
		return null;
	}
	public SubscriberObject getSubscriberInfo(Connection con, String isdn) {
		try {
			Vector vt =getSubcriberID(con, isdn);
			if (vt != null && vt.size() > 0) {
				SubscriberObject subscriberObject = new SubscriberObject();
				Vector vtTemp = (Vector) vt.get(0);
				subscriberObject.setMsisdn(isdn);
				subscriberObject.setSubGroupID(StringUtil.nvl(vtTemp.get(1),
						"-1"));
				subscriberObject.setSubscriberID(StringUtil.nvl(vtTemp.get(0),
						"-1"));
				return subscriberObject;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}