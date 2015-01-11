package com.fis.esme.core.smsc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.smpp.pdu.PDU;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.pdu.WrongLengthOfStringException;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.exception.QueueLevelException;
import com.fis.esme.core.log.LogRecord;
import com.fis.esme.core.smsc.SMSTransceiverEx;
import com.fis.esme.core.util.LinkQueue;
import com.fss.mwallet.util.ReceiveMessage;
import com.fss.mwallet.util.SubmitMessage;
import com.fss.queue.Attributable;
import com.fss.util.AppException;
import com.fss.util.StringUtil;
import com.fss.util.WildcardFilter;

public abstract class QueueManager extends SMSCQueue {

	private static final long serialVersionUID = 1L;
	private static long iIndex = 1;

	private Vector mvtDispatcher = new Vector();

	public QueueManager(String[] strIndex) {
		super(strIndex);
	}


	public abstract HashMap getRequestAckCache();
	// public abstract void setRequestAckCache(HashMap requestACKCache);

	public abstract LinkQueue<LogRecord> getLoggerQueue();

	// public abstract void setLoggerQueue(LinkQueue<LogRecord> loggerQueue);

	// public abstract SortableVector getIsdnPrefix();
	// public abstract void setIsdnPrefix(SortableVector Prefix);

	public abstract LinkQueue<ReceiveMessage> getRequestMOQueue();

	// public abstract void setRequestMOQueue(LinkQueue<ReceiveMessage>
	// moQueue);

	// //////////////////////////////////////////////////////
	public void addDispatcher(ManageableThreadEx d) {
		mvtDispatcher.addElement(d);
	}

	// //////////////////////////////////////////////////////
	public void removeDispatcher(ManageableThreadEx d) {
		if (mvtDispatcher.size() > 0) {
			for (int iIndex = 0; iIndex < mvtDispatcher.size(); iIndex++) {
				ManageableThreadEx dispatcher = (ManageableThreadEx) mvtDispatcher
						.elementAt(iIndex);
				if (dispatcher.getThreadID() == d.getThreadID()) {
					synchronized (mvtDispatcher) {
						mvtDispatcher.remove(iIndex);
					}
				}
			}
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 */
	public boolean checkDispatcherAvailable(Attributable msg)
			throws AppException {
		for (int iIndex = 0; iIndex < mvtDispatcher.size(); iIndex++) {
			ManageableThreadEx dispatcher = (ManageableThreadEx) mvtDispatcher
					.elementAt(iIndex);
			if (matchAttributes(msg.getAttributes(), dispatcher.getProperties())) {
				if (dispatcher.isOpen()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param nationIsdn
	 * @param mapPrefix
	 * @return
	 */
	private String getDispatherAvaiablefromISDN(String isdn,
			SortableVector mapPrefix) {
		for (int i = 0; i < mapPrefix.getKeys().size(); i++) {
			String strISDNConfig = String.valueOf(mapPrefix.getKeys().get(i));
			String strThreadID = mapPrefix.getValue(strISDNConfig);
			if (WildcardFilter.match(strISDNConfig, isdn)) {
				return strThreadID;
			}
		}
		return "";
	}

	/**
	 * 
	 * @param iSequence
	 * @param strSMSCId
	 * @throws Exception
	 */
	public void processResponseForWhitelist(int iSequence, String strSMSCId)
			throws Exception {
		Object ojRecord = getRequestAckCache().get(iSequence);
		if (ojRecord != null && ojRecord instanceof LogRecord) {
			LogRecord output = (LogRecord) ojRecord;
			synchronized (getRequestAckCache()) {
				getRequestAckCache().remove(iSequence);
			}
			output.setStatus(SendSMSLog.SUCCESSFULL);
			output.setCommandID(String.valueOf(System.currentTimeMillis()));
			output.setSMSC_ID(strSMSCId);
			getLoggerQueue().enqueueNotify(output);
		} else {
			return;
		}
	}

	/**
	 * 
	 * @param object
	 * @throws Exception
	 */
	public void processResponse(Object object) throws Exception {
		PDU pdu = null;
		if (object instanceof PDU) {
			pdu = (PDU) object;
			int iSequence = pdu.getSequenceNumber();
			SubmitSMResp ssm = (SubmitSMResp) pdu;
			String s = ssm.getMessageId();
			long commandid = -1;
			try {
				commandid = Long.parseLong(s);
			} catch (Exception e) {
				// TODO: handle exception
				commandid = System.currentTimeMillis();
			}
			Object ojRecord = getRequestAckCache().get(String.valueOf(iSequence));
			if (ojRecord != null && ojRecord instanceof LogRecord) {
				LogRecord output = (LogRecord) ojRecord;
				synchronized (getRequestAckCache()) {
					getRequestAckCache().remove(iSequence);
				}
				if (pdu.isOk()) {
					output.setStatus(SendSMSLog.SEND_TO_SMSC_SUCCESS);
				} else {
					output.setStatus(SendSMSLog.SEND_SMSC_FAIL);
				}
				output.setActionType(LogRecord.ACTION_UPDATE_TYPE);
				output.setCommandID(String.valueOf(commandid));
				getLoggerQueue().enqueueNotify(output);
			} else {
				return;
			}
		} else {
			throw new Exception("Object not instance of PDU");
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param msg
	 *            Attributable
	 * @throws Exception
	 */
	// //////////////////////////////////////////////////////
	public synchronized void attachSubmitMessage(Attributable msg,
			String ThreadID) throws Exception {
		// Check dispatcher
		if (ThreadID != null && ThreadID != "") {
			msg.setAttribute("DataType", ThreadID);
		} else {
			throw new QueueLevelException(
					"SMSC ID is null, can not routing info :" + ThreadID);
		}
		if (!checkDispatcherAvailable(msg)) {
			throw new QueueLevelException(
					"Can't find smsc sender to attach for SMSC_ID:" + ThreadID);
		}
		super.attach(msg);
	}

	/**
	 * 
	 * @param Attribute
	 * @return
	 * @throws Exception
	 */

	public SubmitMessage getSubmitMessage(Map Attribute) throws Exception {
		Object object = dequeue(Attribute);
		LogRecord logRecord = (LogRecord) object;
		if (logRecord == null) {
			return null;
		}
		if (logRecord.getIsdn() == null) {
			return null;
		}
		if (logRecord.getResponseMsg() == null) {
			return null;
		}
		SubmitSM submitSM = new SubmitSM();
		String targetAddress = StringUtil.nvl(logRecord.getIsdn(), "");
		try {
			if (!targetAddress.equals("")) {
				submitSM.getDestAddr().setAddress(targetAddress);
				String strRegisterDelivery = logRecord.getRegisterDelivery();
				if("1".equals(strRegisterDelivery)) {
					submitSM.setRegisteredDelivery((byte) 1);
				} else {
					submitSM.setRegisteredDelivery((byte) 0);
				}
				SubmitMessage submitMessage = new SubmitMessage(submitSM);
				submitMessage.setExtendedMessage(StringUtil.nvl(
						logRecord.getResponseMsg(), ""));
				String strRequestId = logRecord.getRequestID();
				if(strRequestId == null || strRequestId.equals("")) {
					strRequestId = String.valueOf(getSequence());
				}
				logRecord.setStartTime(System.currentTimeMillis());
				synchronized (getRequestAckCache()) {
					getRequestAckCache().put(strRequestId, logRecord);
				}
					
				submitMessage.setRequestID(Long.parseLong(strRequestId));
				return submitMessage;
			}
		} catch (WrongLengthOfStringException ex) {
			System.err.println("QueueLevel.getSubmitMessage: target="
					+ targetAddress + ",message=" + logRecord.getResponseMsg());
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}

	public synchronized long getSequence() {
		return iIndex++;
	}
}
