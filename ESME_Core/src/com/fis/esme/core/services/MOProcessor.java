package com.fis.esme.core.services;

import com.fis.esme.core.app.ThreadSplitBase;
import com.fis.esme.core.bean.SmsBean;
import com.fis.esme.core.bean.SmsBeanFactory;
import com.fis.esme.core.bean.SmsBeanOracle;
import com.fis.esme.core.entity.CPObject;
import com.fis.esme.core.entity.SMSMOObject;
import com.fis.esme.core.entity.VariableStatic;
import com.fis.esme.core.log.LogRecord;
import com.fis.esme.core.smsc.util.QueueMOManager;
import com.fis.esme.core.smsc.util.QueueMTManager;
import com.fis.esme.core.util.LinkQueue;
import com.fss.sql.Database;

public class MOProcessor extends ThreadSplitBase {
	public MOProcessor() {
	}

	private String strSource_Address = "";
	private int count = 0;
	private int lengthMessage = 0;
	public boolean blValidateServiceForMT = false;
	public boolean blDefaulShortcode = false;
	// xu ly tach tien trinh loaddb va tien trinh xu ly rieng
	private LinkQueue qMessageQueue = null;
	private QueueMTManager mlSMSQueueLevel;
	private QueueMOManager qQueueMOManager;
	private static final String MSG_TYPE = "DataType";
	private SmsBean bean = null;

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
	private void insertSMSLog(String smslogId, String request_id,
			String msisdn, String status, String type, String response_id,
			String service_id, String service_parent_id,
			String service_roor_id, String cp_id, String smsc_id,
			String short_code_id, String command_id, String ack_id,
			String ack_date, String sms_content, String total_sms,
			String source_sms_id, String error_code, String strFileUploadID,
			String subid, String groupid) throws Exception {
		try {
			bean.insertSMSLog(mcnMain, smslogId, request_id, msisdn, status,
					type, response_id, service_id, service_parent_id,
					service_roor_id, cp_id, smsc_id, short_code_id, command_id,
					ack_id, ack_date, sms_content, total_sms, source_sms_id,
					error_code, strFileUploadID, subid, groupid,0,0,0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}

	}

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
		bean = SmsBeanFactory.getSmsBeanFactory(getThreadManager()
				.getDatabaseMode());
		qMessageQueue = getThreadManager().getLqMessageMOQueue();
		mlSMSQueueLevel = getThreadManager().getQueueMTManager();
		qQueueMOManager = getThreadManager().getMOQueue();
	}

	@Override
	public void processSession() throws Exception {
		// TODO Auto-generated method stub
		SMSMOObject SMSObject = null;
		while (isThreadRunning()
				&& (SMSObject = (SMSMOObject) qMessageQueue
						.dequeueWait(VariableStatic.iSecondTimeout)) != null) {
			int intFailCount = 0;
			count = 0;
			try {
				if (SMSObject != null) {
					try {
						int mo_id = SMSObject.getMo_id();
						int request_id = SMSObject.getRequest_id();
						String message = SMSObject.getMessage();
						String status = SMSObject.getStatus();
						String msisdn = SMSObject.getMsisdn();
						String short_code = SMSObject.getShort_code();
						int retry_number = SMSObject.getRetry_number();
						String loadtype = SMSObject.getLoadtype();
						int service_id = SMSObject.getService_id();
						int service_parent_id = SMSObject
								.getService_parent_id();
						int service_roor_id = SMSObject.getService_roor_id();
						int cp_id = SMSObject.getCp_id();
						int smsc_id = SMSObject.getSmsc_id();
						int command_id = SMSObject.getCommand_id();
						int short_code_id = SMSObject.getShort_code_id();
						String strSmsLogId = SMSObject.getSmsLogId();
						String protocal = SMSObject.getProtocal();
						// //////////////////////////////////////////////////////////
						LogRecord logRecord = prepareLogRecord(msisdn, message,
								strSmsLogId);
						logRecord.setB_msisdn(short_code);
						logRecord.setCommandID(String.valueOf(command_id));
						logRecord.setDirection(VariableStatic.SMS_DIRECTION_IN);
						logRecord.setDispatcherID(String.valueOf(cp_id));
						logRecord.setRequestID(String.valueOf(request_id));
						logRecord.setSMSC_MO_ID(String.valueOf(mo_id));
						logRecord.setSMSC_ID(String.valueOf(smsc_id));
						CPObject cpObj = getThreadManager().getCP(
								String.valueOf(cp_id));
						if (cpObj != null) {
							logRecord.setUrl(cpObj.getReceiveURLMsg());
							logRecord.setUsername(cpObj.getReceiveUsername());
							logRecord.setPassword(cpObj.getReceivePassword());
						}
						// logRecord.setRequest(msg)
						logRecord.setAttribute(MSG_TYPE, protocal);
						String strStatus = String
								.valueOf(VariableStatic.SMS_MO_STATUS.SEND_SUCCESS
										.getValue());
						try {
							qQueueMOManager.attach(logRecord);
						} catch (Exception e) {
							// TODO: handle exception
							strStatus = String
									.valueOf(VariableStatic.SMS_MO_STATUS.FAIL_AND_RETRY
											.getValue());
						} finally {
							// update sms mo
							bean.updateSMSMORetry(mcnMain,strStatus,
									String.valueOf(mo_id));
							debugMonitor("Logid : " + strSmsLogId + ",mo_id : "
									+ mo_id + ", status :" + strStatus, 3);
							bean.updateSMSLogStatus(mcnMain,strStatus, "", strSmsLogId,
									"");
						}

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

	/**
	 * 
	 * @param msisdn
	 * @param strMsg
	 * @param smsLogSeq
	 * @return
	 */
	private LogRecord prepareLogRecord(String msisdn, String strMsg,
			String smsLogSeq) {
		LogRecord logRecord = new LogRecord();
		logRecord.setIsdn(msisdn);
		logRecord.setContent(strMsg);
		logRecord.setSessionID(smsLogSeq);
		return logRecord;
	}

	/**
	 * @overwrite
	 */
	protected void afterSession() throws Exception {
		super.afterSession();
		qMessageQueue = null;
	}
	public static void main(String [] agm)
	{
		
	}
}
