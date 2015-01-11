package com.fis.esme.core.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.fis.esme.core.entity.SubscriberObject;
import com.fss.sql.Database;

public abstract class SmsBean extends AbstractBean{
	/**
	 * 
	 * @param status
	 * @param mo_id
	 * @return
	 */
	public abstract int updateSMSMORetry(Connection mcnMain, String status, String mo_id);

	/**
	 * 
	 * @param status
	 * @param mo_id
	 * @return
	 */
	public abstract int updateSMSMOStatus(Connection mcnMain, String status, String mo_id) ;

	/**
	 * 
	 * @param status
	 * @param error_code
	 * @param log_id
	 * @return
	 */
	public abstract int updateSMSLogStatus(Connection mcnMain, String status,
			String error_code, String log_id, String tranid) ;
	/**
	 * 
	 * @param status
	 * @param ack_id
	 * @param log_id
	 * @return
	 */
	public abstract int updateSMSLogTranID(Connection mcnMain, String status,
			String smsc_delivery_id, String ack_id);

	/**
	 * 
	 * @param iScheduleId
	 * @param strContent
	 * @throws Exception
	 */
	public abstract int updateSMSRetryStatus(Connection mcnMain, int status,
			String sms_log_id, int iMtId) throws Exception ;

	public abstract int updateSMSReloadStatus(Connection mcnMain, String status,
			int iMtId) throws Exception ;
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
	public abstract void insertSMSLog(Connection mcnMain,String smslogId, String request_id, String msisdn,
			String status, String type, String response_id, String service_id,
			String service_parent_id, String service_roor_id, String cp_id,
			String smsc_id, String short_code_id, String command_id,
			String ack_id, String ack_date, String sms_content,
			String total_sms, String source_sms_id, String error_code,
			String strFileUploadID, String subId, String groupID,int customerid,int advid,int campaignid)
			throws Exception ;

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
	public abstract int insertSMSMO(Connection mcnMain, String mo_id, String requestid,
			String message, String status, String msisdn, String shortcode,
			String retry_number, String type, String reason, int service_id,
			int service_parent_id, int service_roor_id, int cp_id, int smsc_id,
			int short_code_id, int command_id, String sms_log_id,
			String protocal, String subid, String groupid);

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
	 */
	public abstract int insertSMSMT(Connection mcnMain, String requestid,
			String message, String cp_id, String shortcode, String msisdn,
			String commandcode, String registerDl, String strESMETransID,
			String strMOSequenceNumber, String subid, String groupuid) throws Exception;

	public abstract Vector loadPassword(Connection mcnMain, String strUser) ;

	public abstract int updateMTForMO(Connection mcnMain,String strResponseID, String strLogID,
			String strMSISDN) throws Exception;

	public abstract String getSequence(Connection con, String sequenceName)
			throws Exception ;
	
	public abstract Vector getSubcriberID(Connection con, String isdn) ;

	public abstract SubscriberObject getSubscriberInfo(Connection con, String isdn);
	
	public  int insertSMSMTEx(Connection mcnMain, String requestid,
			String message, String cp_id, String shortcode, String msisdn,
			String commandcode, String registerDl, String strESMETransID,
			String strMOSequenceNumber, String subid, String groupuid,int customerId,int campaign_id,int adv_id){return 0;} ;
}
