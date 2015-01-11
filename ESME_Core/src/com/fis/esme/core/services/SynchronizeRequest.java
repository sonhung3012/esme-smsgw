package com.fis.esme.core.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.fis.esme.core.app.ThreadSplitBase;
import com.fis.esme.core.bean.SmsBean;
import com.fis.esme.core.bean.SmsBeanFactory;
import com.fis.esme.core.bean.SmsBeanOracle;
import com.fis.esme.core.entity.SMSCMORoutingUtil;
import com.fis.esme.core.entity.SMSCMTRountingObject;
import com.fis.esme.core.entity.SMSMOObject;
import com.fis.esme.core.entity.SMSMORoutingObject;
import com.fis.esme.core.entity.ServicesInternalObject;
import com.fis.esme.core.entity.SubscriberObject;
import com.fis.esme.core.entity.VariableStatic;
import com.fis.esme.core.log.LogRecord;
import com.fis.esme.core.smpp.util.Unicode;
import com.fis.esme.core.smsc.util.QueueMOManager;
import com.fis.esme.core.smsc.util.QueueMTManager;
import com.fis.esme.core.util.LinkQueue;
import com.fss.mwallet.util.ReceiveMessage;
import com.fss.sql.Database;
import com.fss.util.AppException;
import com.fss.util.StringUtil;

/**
 * 
 * @implement by HungVM
 * 
 */
public class SynchronizeRequest extends ThreadSplitBase {
	private int iSecondTimeout = 300;
	private static final String MSG_TYPE = "DataType";
	private LinkQueue<ReceiveMessage> mqMORequestQueue = null;
	private QueueMOManager qQueueMOManager;
	private QueueMTManager qQueueMTManager;
	private LinkQueue<ServicesInternalObject> lqMessageQueueInter;
	private SmsBean bean = null;

	public void beforeSession() throws Exception {
		setAutoConnectDb(true);
		super.beforeSession();
		qQueueMOManager = getThreadManager().getMOQueue();
		qQueueMTManager = getThreadManager().getQueueMTManager();
		mqMORequestQueue = qQueueMOManager.getRequestMOQueue();
		lqMessageQueueInter = getThreadManager().getMessageQueueInter();
		bean = SmsBeanFactory.getSmsBeanFactory(getThreadManager()
				.getDatabaseMode());
	}

	public void afterSession() throws Exception {
		super.afterSession();
	}

	public void processSession() throws AppException {
		ReceiveMessage receiveMessage = null;
		while (isThreadRunning()
				&& (receiveMessage = mqMORequestQueue
						.dequeueWait(iSecondTimeout)) != null) {
			String strRequestMoId = "";
			String strSmsLogId = "";
			SubscriberObject subscriberObject = null;
			try {
				if (receiveMessage != null) {
					strRequestMoId = bean.getSequence(mcnMain, "mo_seq");
					strSmsLogId = bean.getSequence(mcnMain, "sms_log_seq");
					String callingNumber = parseCalling(receiveMessage
							.getStrCalling().trim());
					String requestid = StringUtil.nvl(
							receiveMessage.getAttribute("request_id"), "0");
					String calledNumber = receiveMessage.getStrCalled().trim();
					String strShortMessage = receiveMessage.getContent().trim();
					String smscid = String.valueOf(receiveMessage
							.getAttribute("smsc_id"));
					String strSubId = "-1";
					String strGroupId = "-1";

					if (strShortMessage == null
							|| strShortMessage.equalsIgnoreCase("")) {
						processValidatedCommand(
								VariableStatic.SMS_MO_RESPONSE_NULL_MSG,
								callingNumber, strRequestMoId, calledNumber,
								receiveMessage, smscid, strSubId, strGroupId);
						continue;
					}
					strShortMessage = Unicode
							.spliNullCharacter(strShortMessage);
					strShortMessage = Unicode
							.rejectSpecialCharacter(strShortMessage);
					for (int i = 0; i < 3; i++) {
						strShortMessage = strShortMessage.replaceAll("  ", " ");
					}
					SMSMORoutingObject smsMORoutingObject = parseCommand(
							calledNumber, strShortMessage);

					if (getThreadManager().getCheckSubscriber()) {
						subscriberObject = bean.getSubscriberInfo(mcnMain,
								callingNumber);
						if (subscriberObject != null) {
							strSubId = subscriberObject.getSubscriberID();
							strGroupId = subscriberObject.getSubGroupID();
						}
					}
					if (smsMORoutingObject == null) {
						processValidatedCommand(
								VariableStatic.SMS_MO_RESPONSE_NOT_SUPPORT_CMD,
								callingNumber, strRequestMoId, calledNumber,
								receiveMessage, smscid, strSubId, strGroupId);
						String strStatus = String
								.valueOf(VariableStatic.SMS_MO_STATUS.FAIL_VALIDATE_INFO
										.getValue());
						// insert smsmo
						bean.insertSMSMO(mcnMain, strRequestMoId, requestid,
								strShortMessage, strStatus, callingNumber,
								calledNumber, "1", "0",
								"fail for validate routing !", 0, 0, 0, 0, 0,
								0, 0, strSmsLogId, "0", strSubId, strGroupId);
						// insert smslog
						bean.insertSMSLog(
								mcnMain,
								strSmsLogId,
								requestid,
								callingNumber,
								String.valueOf(strStatus),
								String.valueOf(VariableStatic.SMS_LOG_TYPE.MO
										.getValue()),
								"0",
								"0",
								"0",
								"0",
								"0",
								"0",
								"0",
								"0",
								"0",
								null,
								strShortMessage,
								String.valueOf(strShortMessage.length() / 160 + 1),
								strRequestMoId,
								VariableStatic.SMS_MO_RESPONSE_NOT_ROUTING_INFO,
								"0", strSubId, strGroupId,0,0,0);
						debugMonitor(
								"Reveiver message from smsc : smslog- "
										+ strSmsLogId
										+ ", content id : "
										+ strShortMessage
										+ ",isdn : "
										+ callingNumber
										+ ", shortcode:"
										+ calledNumber
										+ ", status :"
										+ VariableStatic.SMS_MO_RESPONSE_NOT_ROUTING_INFO,
								3);
						continue;
					}
					// //////////////////////////////////////////////////////////
					LogRecord logRecord = prepareLogRecord(callingNumber,
							strShortMessage, strSmsLogId);
					logRecord.setB_msisdn(calledNumber);
					logRecord.setCommandID(String.valueOf(smsMORoutingObject
							.getCommand_id()));
					logRecord.setCommand(smsMORoutingObject.getCmd_code());
					logRecord.setDirection(VariableStatic.SMS_DIRECTION_IN);
					logRecord.setDispatcherID(String.valueOf(smsMORoutingObject
							.getCp_id()));
					logRecord.setRequestID(requestid);
					logRecord.setSMSC_MO_ID(strRequestMoId);
					logRecord.setSMSC_ID(smscid);
					logRecord.setAttribute(MSG_TYPE,
							smsMORoutingObject.getProtocal());
					logRecord.setUrl(smsMORoutingObject.getUrl());
					logRecord.setUsername(smsMORoutingObject.getUsername());
					logRecord.setPassword(smsMORoutingObject.getPassword());
					logRecord.setSubID(strSubId);
					logRecord.setGroupID(strGroupId);
					String strStatus = String
							.valueOf(VariableStatic.SMS_MO_STATUS.SEND_SUCCESS
									.getValue());
					String strSmsLogStatus = String
							.valueOf(VariableStatic.SMS_LOG_STATUS_MO.INSERT_NEW
									.getValue());

					ServicesInternalObject servicesInternalObject = new ServicesInternalObject();
					servicesInternalObject.setSubId(strSubId);
					servicesInternalObject.setGroupId(strGroupId);
					servicesInternalObject.setMsisdn(callingNumber);
					servicesInternalObject
							.setSmsMORoutingObject(smsMORoutingObject);
					servicesInternalObject.setMoLogId(strRequestMoId);
					servicesInternalObject.setSmsLogId(strSmsLogId);
					servicesInternalObject.setContent(strShortMessage);
					try {
						attachMessage(smsMORoutingObject.getProtocal(),
								logRecord, servicesInternalObject);
						if (smsMORoutingObject.getProtocal().equalsIgnoreCase(
								VariableStatic.PROTOCAL_WEB_HANDLER)
								|| smsMORoutingObject
										.getProtocal()
										.equalsIgnoreCase(
												VariableStatic.PROTOCAL_WEB_INTERNAL)) {
							// do nothing because this kind of message for
							// wwebsite process no
							// need to process anymore.
							strSmsLogStatus = "1";
						}
						if(smsMORoutingObject.getProtocal().equalsIgnoreCase(
								VariableStatic.PROTOCAL_WEB_HANDLER))
						{
							String strResponseMsg = getMessageResonses(VariableStatic.SMS_WEB_CONFIRM);
							bean.insertSMSMT(mcnMain, strSmsLogId, strResponseMsg,String.valueOf(smsMORoutingObject.getCp_id()),
									smsMORoutingObject.getShort_code_code(), callingNumber,smsMORoutingObject.getCmd_code(), "0",
									"0", strSmsLogId, strSubId, strGroupId);
						}
							
					} catch (Exception e) {
						// TODO: handle exception
						debugMonitor(e.getMessage(), 1);
						strStatus = String
								.valueOf(VariableStatic.SMS_MO_STATUS.FAIL_AND_RETRY
										.getValue());
						strSmsLogStatus = strStatus;
					} finally {
						// insert sms mo
						bean.insertSMSMO(mcnMain, strRequestMoId, requestid,
								strShortMessage, strStatus, callingNumber,
								calledNumber, "1",
								smsMORoutingObject.getProtocal(),
								"First attach",
								smsMORoutingObject.getService_id(),
								smsMORoutingObject.getParent_service_id(),
								smsMORoutingObject.getRoot_id(),
								smsMORoutingObject.getCp_id(),
								Integer.valueOf(smscid),
								smsMORoutingObject.getShort_code_id(),
								smsMORoutingObject.getCommand_id(),
								strSmsLogId, smsMORoutingObject.getProtocal(),
								strSubId, strGroupId);
						// insert sms log
						bean.insertSMSLog(
								mcnMain,
								strSmsLogId,
								requestid,
								callingNumber,
								String.valueOf(strSmsLogStatus),
								String.valueOf(VariableStatic.SMS_LOG_TYPE.MO
										.getValue()),
								smsMORoutingObject.getProtocal(),
								String.valueOf(smsMORoutingObject
										.getService_id()),
								String.valueOf(smsMORoutingObject
										.getParent_service_id()),
								String.valueOf(smsMORoutingObject.getRoot_id()),
								String.valueOf(smsMORoutingObject.getCp_id()),
								smscid, String.valueOf(smsMORoutingObject
										.getShort_code_id()), String
										.valueOf(smsMORoutingObject
												.getCommand_id()), "0", null,
								strShortMessage, String.valueOf(strShortMessage
										.length() / 160 + 1), strRequestMoId,
								"0", "0", strSubId, strGroupId,0,0,0);
					}
					debugMonitor("Reveiver message from smsc : smslog- "
							+ strSmsLogId + ",isdn : " + callingNumber
							+ ", content id : " + strShortMessage + ",isdn : "
							+ callingNumber + ", shortcode:" + calledNumber
							+ ", status :" + strStatus + ",QueueSize :"
							+ mqMORequestQueue.getSize(), 3);
					Thread.sleep(10);
				} else {
					Thread.sleep(100);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				throw new AppException(ex, ex.getMessage());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void attachMessage(String SmsType, LogRecord logRecord,
			ServicesInternalObject servicesInternalObject) throws Exception {
		if (SmsType.equalsIgnoreCase(VariableStatic.PROTOCAL_WEB_HANDLER)) {
			// sent a mt response for confirm receiver successfull.
		} else if (SmsType
				.equalsIgnoreCase(VariableStatic.PROTOCAL_WEB_INTERNAL)) {
			// add to queue process
			lqMessageQueueInter.enqueueNotify(servicesInternalObject);
		} else {
			qQueueMOManager.attach(logRecord);
		}
	}

	/**
	 * 
	 * @param logRecord
	 * @param smscId
	 * @throws Exception
	 */
	private void submitMessage(LogRecord logRecord, String smscId)
			throws Exception {
		try {
			qQueueMTManager.attachSubmitMessage(logRecord, smscId);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
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
	 * 
	 * @param shortcode
	 * @param commandcode
	 * @return
	 * @throws Exception
	 */
	protected SMSMORoutingObject parseCommand(String shortcode,
			String commandcode) throws Exception {
		SMSMORoutingObject smsMORoutingObject = SMSCMORoutingUtil
				.getServiceInfo(shortcode, commandcode, getThreadManager()
						.getSMSMORouting());
		return smsMORoutingObject;
	}

	/**
	 * 
	 * @param strResponseCode
	 * @param isdn
	 * @param smsLogSeq
	 * @param shortcode
	 * @return
	 * @throws Exception
	 */
	private void processValidatedCommand(String strResponseCode, String isdn,
			String smsLogSeq, String shortcode, ReceiveMessage receiveMessage,
			String smscid, String subid, String groupid) throws Exception {
		SMSCMTRountingObject smsCMTRountingObject = null;
		String strResponseMsg = null;
		try {
			strResponseMsg = getMessageResonse(strResponseCode);
			bean.insertSMSMT(mcnMain, smsLogSeq, strResponseMsg, "0",
					shortcode, isdn, VariableStatic.DEFAULT_COMMAND_CODE, "0",
					"0", smsLogSeq, subid, groupid);
		} catch (Exception e) {
			debugMonitor(e.getMessage(), 0);
			// TODO: handle exception
			// throw e;
		} finally {
		}
	}

	/**
	 * 
	 * @param strResponseCode
	 * @return
	 */

	private String getMessageResonse(String strResponseCode) {
		return getThreadManager().getSystemParameterMessage(
				VariableStatic.PARAM_SYSTEM_TYPE_SYSTEM, strResponseCode);
	}

	private String getMessageResonses(String strResponseCode) {
		return getThreadManager().getSystemParameterMessage(
				VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE, strResponseCode);
	}
	
	/**
	 * 
	 * @param strCalling
	 * @return
	 */
	private String parseCalling(String strCalling) {
		if (strCalling.startsWith("95")) {
			strCalling = strCalling.substring(2);
		}
		if (strCalling.startsWith("0")) {
			strCalling = strCalling.substring(1);
		}
		return strCalling;
	}

	/**
	 * 
	 * @param cn
	 * @param sequenceName
	 * @return
	 */
	public String getSequence(Connection cn, String sequenceName) {
		try {
			return Database.getSequenceValue(cn, sequenceName);
		} catch (Exception ex) {
			return null;
		}
	}
	public static void main(String [] agm)
	{
		
	}
}
