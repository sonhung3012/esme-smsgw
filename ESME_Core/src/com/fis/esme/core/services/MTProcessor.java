package com.fis.esme.core.services;

import java.sql.SQLException;

import com.fis.esme.core.app.ThreadSplitBase;
import com.fis.esme.core.bean.SmsBean;
import com.fis.esme.core.bean.SmsBeanFactory;
import com.fis.esme.core.entity.SMSCMORoutingUtil;
import com.fis.esme.core.entity.SMSCMTRountingObject;
import com.fis.esme.core.entity.SMSMORoutingObject;
import com.fis.esme.core.entity.SMSMTObject;
import com.fis.esme.core.entity.VariableStatic;
import com.fis.esme.core.entity.VariableStatic.SMS_LOG_TYPE;
import com.fis.esme.core.log.LogRecord;
import com.fis.esme.core.smsc.util.QueueMTManager;
import com.fis.esme.core.util.LinkQueue;

public class MTProcessor extends ThreadSplitBase {

	public MTProcessor() {

	}

	private String strSource_Address = "";
	private int count = 0;
	public boolean blValidateServiceForMT = false;
	public boolean blDefaulShortcode = false;
	private LinkQueue qMessageQueue = null;
	private QueueMTManager mlSMSQueueLevel;
	private SmsBean bean = null;

	private String subCalling(String strCalling) {

		if (strCalling.startsWith("0")) {
			strCalling = strCalling.substring(1);
		}
		return strCalling;
	}

	@Override
	public void beforeSession() throws Exception {

		// TODO Auto-generated method stub
		setAutoConnectDb(true);
		super.beforeSession();
		bean = SmsBeanFactory.getSmsBeanFactory(getThreadManager().getDatabaseMode());
		blValidateServiceForMT = getThreadManager().getValidateServiceMT();
		qMessageQueue = getThreadManager().getSMSMTQueue();
		mlSMSQueueLevel = getThreadManager().getQueueMTManager();
	}

	@Override
	public void processSession() throws Exception {

		// TODO Auto-generated method stub
		SMSMTObject SMSObject = null;
		while (isThreadRunning() && (SMSObject = (SMSMTObject) qMessageQueue.dequeueWait(VariableStatic.iSecondTimeout)) != null) {
			int intFailCount = 0;
			count = 0;
			try {
				if (SMSObject != null) {
					try {
						String strMsisdn = SMSObject.getMsisdn();
						String strMsg = SMSObject.getMessage();
						String commandcode = SMSObject.getCommand_code();
						String shortcode = SMSObject.getShort_code();
						int cp_id = SMSObject.getCp_id();
						String loadType = SMSObject.getLoadtype();
						String strESMETransID = SMSObject.getESMETransID();
						String strMOSequenceNumber = SMSObject.getMOSequenceNumber();
						int iSequestID = SMSObject.getRequest_id();
						int intCustomerID = SMSObject.getCustomerId();
						int intAdvID = SMSObject.getAdvId();
						int intCampaignID = SMSObject.getCampaignId();

						String smscid = "-1";
						int strStatus = VariableStatic.SMS_MT_STATUS.SEND_SUCCESS.getValue();
						String strReason = "";
						boolean error = false;
						int smsTotal = strMsg.length() / 160 + 1;
						SMSCMTRountingObject smsCMTRountingObject = null;
						debugMonitor("Debug :" + error + " ,MSISDN :" + SMSObject.getMsisdn() + ", status :" + strStatus + ", reasion : " + strReason, 3);

						// get service routing info
						SMSMORoutingObject smsMORoutingObject = SMSCMORoutingUtil.getServiceInfo(shortcode, commandcode, getThreadManager().getSMSMORouting());
						if (smsMORoutingObject == null) {
							smsMORoutingObject = new SMSMORoutingObject();
							smsMORoutingObject.setService_id(0);
							smsMORoutingObject.setRoot_id(0);
							smsMORoutingObject.setParent_service_id(0);
							smsMORoutingObject.setCommand_id(0);
							smsMORoutingObject.setShort_code_id(0);
							smsMORoutingObject.setService_id(0);
							if (blValidateServiceForMT) {
								strStatus = VariableStatic.SMS_MT_STATUS.FAIL_VALIDATE_INFO.getValue();
								strReason = VariableStatic.SMS_LOG_VALIDATE_SMS_SERVICE;
								error = true;
							}
						}
						if (blValidateServiceForMT && smsMORoutingObject != null && smsMORoutingObject.getCp_id() != cp_id) {
							strStatus = VariableStatic.SMS_MT_STATUS.FAIL_VALIDATE_INFO.getValue();
							strReason = VariableStatic.SMS_LOG_VALIDATE_CPID;
							error = true;
						}
						if (!error) {
							smsCMTRountingObject = SMSCMORoutingUtil.getSMSMTRouting(strMsisdn, shortcode, getThreadManager().getSMSMTRouting(), blDefaulShortcode);
							if (smsCMTRountingObject == null) {

								// insert sms log for error : validate isdn
								// Prefix
								// to smsc
								// update mt with status = 6
								strStatus = VariableStatic.SMS_MT_STATUS.FAIL_VALIDATE_INFO.getValue();
								strReason = VariableStatic.SMS_LOG_VALIDATE_ISDN_PREFIX;
								error = true;
							}
							// check blacklist
							if (!error && getThreadManager().checkBlackList(strMsisdn, String.valueOf(smsMORoutingObject.getService_id()))) {
								strStatus = VariableStatic.SMS_MT_STATUS.FAIL_VALIDATE_INFO.getValue();
								strReason = VariableStatic.SMS_LOG_ISDN_BLACKLIST;
								error = true;
							}
						}
						// String smsLogSeq = "";
						// if (SMSObject.getSms_log_id().equalsIgnoreCase("")) {
						String smsLogSeq = bean.getSequence(mcnMain, "sms_log_seq");
						// } else {
						// smsLogSeq = SMSObject.getSms_log_id();
						// }

						if (error) {
							insertSMSLog(smsLogSeq, String.valueOf(iSequestID), SMSObject.getMsisdn(), String.valueOf(strStatus), String.valueOf(SMS_LOG_TYPE.MT.getValue()), "0",
							        String.valueOf(smsMORoutingObject.getService_id()), String.valueOf(smsMORoutingObject.getParent_service_id()), String.valueOf(smsMORoutingObject.getRoot_id()),
							        String.valueOf(cp_id), smscid, String.valueOf(smsMORoutingObject.getShort_code_id()), String.valueOf(smsMORoutingObject.getCommand_id()), "0", null, strMsg,
							        String.valueOf(smsTotal), String.valueOf(SMSObject.getMt_id()), strReason, loadType, SMSObject.getFileUploadID(), SMSObject.getSubId(), SMSObject.getGroupId(),
							        intCustomerID, intAdvID, intCampaignID);

							// update MT for MO
							if (strMOSequenceNumber != null && !strMOSequenceNumber.equals("")) {
								bean.updateMTForMO(mcnMain, smsLogSeq, strMOSequenceNumber, strMsisdn);
							}

							// UPDATE SMS MT
							updateSMSRetryStatus(strStatus, smsLogSeq, SMSObject.getMt_id());

							debugMonitor("ErrorCode :" + error + " ,MSISDN :" + SMSObject.getMsisdn() + ", status :" + strStatus + ", reasion : " + strReason, 3);
							continue;
						}

						LogRecord logRecord = new LogRecord();
						logRecord.setIsdn(strMsisdn);
						logRecord.setResponseMsg(strMsg);
						logRecord.setRequestID(strESMETransID);
						logRecord.setRegisterDelivery(SMSObject.getRegister_delivery_report());
						logRecord.setSessionID(smsLogSeq);
						logRecord.setSMSC_ID(String.valueOf(smsCMTRountingObject.getSmsc_id()));
						// send MT to smsc
						try {
							mlSMSQueueLevel.attachSubmitMessage(logRecord, String.valueOf(smsCMTRountingObject.getSmsc_id()));
						} catch (Exception e1) {
							// TODO: handle exception
							// insert sms log for error : no smsc avariable for
							// that shortcode and command code
							// to smsc
							// update mt with status = 2
							e1.printStackTrace();
							strStatus = VariableStatic.SMS_MT_STATUS.FAIL_AND_RETRY.getValue();
							strReason = VariableStatic.SMS_LOG_NO_SMSC_AVARIABLE;
							error = true;
						}
						insertSMSLog(smsLogSeq, String.valueOf(iSequestID), SMSObject.getMsisdn(), String.valueOf(strStatus), String.valueOf(SMS_LOG_TYPE.MT.getValue()), "0",
						        String.valueOf(smsMORoutingObject.getService_id()), String.valueOf(smsMORoutingObject.getParent_service_id()), String.valueOf(smsMORoutingObject.getRoot_id()),
						        String.valueOf(smsMORoutingObject.getCp_id()), String.valueOf(smsCMTRountingObject.getSmsc_id()), String.valueOf(smsMORoutingObject.getShort_code_id()),
						        String.valueOf(smsMORoutingObject.getCommand_id()), "0", null, strMsg, String.valueOf(smsTotal), String.valueOf(SMSObject.getMt_id()), strReason, loadType,
						        SMSObject.getFileUploadID(), SMSObject.getSubId(), SMSObject.getGroupId(), intCustomerID, intAdvID, intCampaignID);

						// update MT for MO
						if (strMOSequenceNumber != null && !strMOSequenceNumber.equals("")) {
							bean.updateMTForMO(mcnMain, smsLogSeq, strMOSequenceNumber, strMsisdn);
						}
						// UPDATE SMS MT
						updateSMSRetryStatus(strStatus, smsLogSeq, SMSObject.getMt_id());
						debugMonitor(" MSISDN :" + SMSObject.getMsisdn() + ", status :" + strStatus + ", reasion : " + strReason, 3);
					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
						logMonitor(e.getMessage());
						throw e;
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logMonitor(e.getMessage());
					}
					count++;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else // if (SMSObject != null) {
				{
					try {
						Thread.sleep(miDelayTime * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}// end if (SMSObject != null) {
			} catch (Exception e) {
				// TODO: handle exception
				logMonitor(e.getMessage());
				e.printStackTrace();
				intFailCount++;
				throw e;
			}
		}
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void updateSMSRetryStatus(int status, String sms_log_id, int iMtId) throws Exception {

		bean.updateSMSRetryStatus(mcnMain, status, sms_log_id, iMtId);
	}

	public int updateSMSReloadStatus(String status, int iMtId) throws Exception {

		return bean.updateSMSReloadStatus(mcnMain, status, iMtId);
	}

	private void insertSMSLog(String smslogId, String request_id, String msisdn, String status, String type, String response_id, String service_id, String service_parent_id, String service_roor_id,
	        String cp_id, String smsc_id, String short_code_id, String command_id, String ack_id, String ack_date, String sms_content, String total_sms, String source_sms_id, String error_code,
	        String loadtype, String strFileUploadID, String subid, String groupid, int customerid, int advid, int campaignid) throws Exception {

		if (loadtype.equalsIgnoreCase("1")) {
			bean.insertSMSLog(mcnMain, smslogId, request_id, msisdn, status, type, response_id, service_id, service_parent_id, service_roor_id, cp_id, smsc_id, short_code_id, command_id, ack_id,
			        ack_date, sms_content, total_sms, source_sms_id, error_code, strFileUploadID, subid, groupid, customerid, advid, campaignid);
		} else {
			bean.updateSMSLogStatus(mcnMain, status, error_code, smslogId, "");
		}
	}

	/**
	 * @overwrite
	 */
	protected void afterSession() throws Exception {

		qMessageQueue = null;
		super.afterSession();

	}

	public static void main(String[] agm) {

	}
}
