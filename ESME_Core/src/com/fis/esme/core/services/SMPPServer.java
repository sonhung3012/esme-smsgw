package com.fis.esme.core.services;

import java.sql.Connection;
import java.util.Properties;
import java.util.Vector;

import net.sf.ehcache.Ehcache;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.bean.SmsBean;
import com.fis.esme.core.bean.SmsBeanFactory;
import com.fis.esme.core.bean.SmsBeanOracle;
import com.fis.esme.core.entity.SMSCMORoutingUtil;
import com.fis.esme.core.entity.SMSMORoutingObject;
import com.fis.esme.core.entity.VariableStatic;
import com.fis.esme.core.log.LogRecord;
import com.fis.esme.core.smpp.ESMEPDUProcessor;
import com.fis.esme.core.smpp.ESMEPDUProcessorFactory;
import com.fis.esme.core.smpp.PDUProcessorGroup;
import com.fis.esme.core.smpp.SMSCListener;
import com.fis.esme.core.smpp.SMSCSession;
import com.fis.esme.core.smpp.entity.SequenceObject;
import com.fis.esme.core.smsc.util.QueueMOManager;
import com.fss.sql.Database;
import com.fss.thread.ParameterType;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;
import org.smpp.pdu.Address;
import org.smpp.pdu.DeliverSM;
import org.smpp.pdu.Request;

public class SMPPServer extends ManageableThreadEx {

	public static final int DSIM = 16;
	public static final int DSIMD = 17;
	public static final int DSIMD2 = 18;

	private SMSCListener smscListener = null;
	private ESMEPDUProcessorFactory factory = null;
	private static PDUProcessorGroup processors = new PDUProcessorGroup();
	private Ehcache submitCache = null;
	private Ehcache deliveryCache = null;
	private SequenceObject serverSequence = null;

	private boolean displayInfo = true;
	private int port;
	private int sessionTimeout;
	private String mstrAlgorithm = "SHA";
	private long checkPeriod = 5000; // miliseconds
	private SmsBean databaseBean = null;

	private Thread checkExpire = null;
	private QueueMOManager qQueueMOManager = null;

	@Override
	public void fillParameter() throws AppException {
		port = loadInteger("SMPPServerPort");
		sessionTimeout = loadInteger("SessionTimeout") * 1000;
		mstrAlgorithm = loadMandatory("Algorithm");
		super.fillParameter();
		fillLogFile();
	}

	@Override
	public Vector getParameterDefinition() {
		Vector vtReturn = super.getParameterDefinition();
		vtReturn.addElement(createParameterDefinition("SMPPServerPort", "",
				ParameterType.PARAM_TEXTBOX_MAX, "",
				"SMPP Server port to listen client connect"));
		vtReturn.addElement(createParameterDefinition("SessionTimeout", "",
				ParameterType.PARAM_TEXTBOX_MAX, "",
				"session timeout (unit: seconds)"));
		vtReturn.addElement(createParameterDefinition("Algorithm", "",
				ParameterType.PARAM_TEXTBOX_MAX, "",
				"algorithm use to authentication"));
		return vtReturn;
	}

	@Override
	protected void beforeSession() throws Exception {
		super.beforeSession();
		submitCache = getThreadManager().getSubmitCache();
		deliveryCache = getThreadManager().getDeliveryCache();
		databaseBean = SmsBeanFactory.getSmsBeanFactory(getThreadManager()
				.getDatabaseMode());
		serverSequence = new SequenceObject();
		smscListener = new SMSCListener(port, true);
		factory = new ESMEPDUProcessorFactory(this, processors, submitCache,
				deliveryCache, serverSequence);
		factory.setDisplayInfo(displayInfo);
		smscListener.setPDUProcessorFactory(factory);
		qQueueMOManager = getThreadManager().getMOQueue();
		qQueueMOManager.addDispatcher(this);

		mprtDeliverSM = new Properties();
		mprtDeliverSM.put("DataType", VariableStatic.PROTOCAL_TYPE_SMPP);
	}

	public String getAlgorithm() {
		return mstrAlgorithm;
	}

	public Vector getUserInfo(String userid) throws Exception {
		Connection con = getThreadManager().getConnection();
		return databaseBean.loadPassword(con, userid);
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
	 * @throws Exception
	 */
	public void insertMT(String requestid, String message, String cp_id,
			String shortcode, String msisdn, String commandcode,
			String registerDl, String strESMETransID,
			String strMOSequenceNumber, String subid, String groupid)
			throws Exception {
		Connection con = null;
		try {
			con = getThreadManager().getConnection();
			databaseBean.insertSMSMT(con, requestid, message, cp_id, shortcode,
					msisdn, commandcode, registerDl, strESMETransID,
					strMOSequenceNumber, subid, groupid);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			debugMonitor(e.getMessage(), 1);
			throw e;
		} finally {
			Database.closeObject(con);
		}
	}

	public void insertSMSLog(String request_id, String msisdn, String cp_id,
			String sms_content, String error_code, String status,
			String strFileUploadID, String subid, String groupid)
			throws Exception {
		Connection con = null;
		try {
			con = getThreadManager().getConnection();
			String smsLogSeq = Database.getSequenceValue(con, "sms_log_seq");
			databaseBean.insertSMSLog(con, smsLogSeq, request_id, msisdn,
					status, "2", "0", "0", "0", "0", cp_id, "0", "0", "0", "0",
					null, sms_content, "0", "0", error_code, strFileUploadID,
					subid, groupid,0,0,0);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			// TODO: handle exception
		} finally {
			Database.closeObject(con);
		}
	}

	@Override
	protected void processSession() throws Exception {
		smscListener.start();
		ExpireManager expireManager = new ExpireManager();
		checkExpire = new Thread(expireManager);
		checkExpire.start();
		while (miThreadCommand != ThreadConstant.THREAD_STOP) {
			try {
				LogRecord requestMessage = (LogRecord) qQueueMOManager
						.detach(getProperties());
				if (requestMessage != null) {
					DeliverSM deliverSM = new DeliverSM();
					deliverSM.setShortMessage(requestMessage.getContent());
					deliverSM.setSequenceNumber(Integer.valueOf(requestMessage
							.getSessionID()));
					deliverSM.setSourceAddr(new Address(requestMessage
							.getIsdn()));
					deliverSM.setDestAddr(new Address(requestMessage
							.getB_msisdn()));
					String status = String
							.valueOf(VariableStatic.SMS_LOG_STATUS_MO.SEND_SUCCESS
									.getValue());
					String errorcode = "";
					try {
						serverRequest(Integer.valueOf(requestMessage
								.getDispatcherID()), deliverSM);
					} catch (Exception e) {
						// TODO: handle exception
						status = String
								.valueOf(VariableStatic.SMS_LOG_STATUS_MO.CP_DONT_RECEIVER
										.getValue());
						errorcode = e.getMessage();
					}
					requestMessage.setActionType(LogRecord.ACTION_UPDATE_TYPE);
					requestMessage.setErrorDescription(errorcode);
					requestMessage.setStatus(status);
					getThreadManager().attachLogRecord(requestMessage);
					debugMonitor("Sended sms to cp :status=" + status
							+ ", isdn=" + requestMessage.getIsdn()
							+ ", shortcode=" + requestMessage.getContent()
							+ ", logid=" + requestMessage.getSessionID(), 3);
				} else {
					try {
						Thread.sleep(20);
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}

	public static void serverRequest(String strCPUser, Request request)
			throws Exception {
		boolean canSend = false;
		if (processors != null && processors.count() > 0) {
			for (int i = 0; i < processors.count(); i++) {
				ESMEPDUProcessor processor = (ESMEPDUProcessor) processors
						.get(i);
				if (processor.isActive()
						&& processor.getSystemId().equals(strCPUser)) {
					processor.serverRequest(request);
					canSend = true;
					break;
				}
			}
		}
		if (!canSend) {
			throw new Exception("Cannot find avaiable processor with user: "
					+ strCPUser);
		}
	}

	public static void serverRequest(long lCPID, Request request)
			throws Exception {
		boolean canSend = false;
		if (processors != null && processors.count() > 0) {
			for (int i = 0; i < processors.count(); i++) {
				ESMEPDUProcessor processor = (ESMEPDUProcessor) processors
						.get(i);
				if (processor.isActive() && processor.getCPId() == lCPID) {
					processor.serverRequest(request);
					canSend = true;
					break;
				}
			}
		}
		if (!canSend) {
			throw new Exception("Cannot find avaiable processor with cpid: "
					+ lCPID);
		}
	}

	@Override
	protected void afterSession() throws Exception {
		qQueueMOManager.removeDispatcher(this);
		if (smscListener != null) {
			debugMonitor("Stopping listener...", 5);
			synchronized (processors) {
				int procCount = processors.count();
				ESMEPDUProcessor proc;
				SMSCSession session;
				for (int i = 0; i < procCount; i++) {
					proc = (ESMEPDUProcessor) processors.get(i);
					session = proc.getSession();
					debugMonitor(
							"Stopping session " + i + ": " + proc.getSystemId()
									+ " ...", 5);
					session.stop();
				}
			}
			smscListener.stop();
			smscListener = null;
			debugMonitor("Stopped.", 6);
		}

		checkExpire.stop();
		super.afterSession();
	}

	public Properties getProperties() {
		return mprtDeliverSM;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isOpen() {
		return ((miThreadCommand != ThreadConstant.THREAD_STOP) && (smscListener != null));
	}

	public class ExpireManager implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				long nextCheck = System.currentTimeMillis();
				while (miThreadCommand != ThreadConstant.THREAD_STOP) {
					if (System.currentTimeMillis() >= nextCheck) {
						nextCheck += checkPeriod;
						if (processors != null && processors.count() > 0) {
							for (int i = 0; i < processors.count(); i++) {
								ESMEPDUProcessor processor = (ESMEPDUProcessor) processors
										.get(i);
								if (!processor.isActive()
										|| (System.currentTimeMillis()
												- processor.getSession()
														.getLastRequestTime() > sessionTimeout)) {
									processor.getSession().stop();
									processors.remove(processor);
								}
							}
						}
					} else {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
						}
					}
				}
			} catch (Exception e) {
			}
		}
	}

	public boolean validateCPInfo(String shortcode, String commandcode, int cpId) {
		SMSMORoutingObject smsMORoutingObject = SMSCMORoutingUtil
				.getServiceInfo(shortcode, commandcode, getThreadManager()
						.getSMSMORouting());
		if (getThreadManager().getValidateServiceMT()) {
			if (smsMORoutingObject == null) {
				return false;
			} else if (smsMORoutingObject.getCp_id() != cpId) {
				return false;
			}
		}
		return true;
	}

}