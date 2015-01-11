package com.fis.esme.core.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.bean.SmsBean;
import com.fis.esme.core.bean.SmsBeanFactory;
import com.fis.esme.core.bean.SmsBeanOracle;
import com.fis.esme.core.entity.VariableStatic;
import com.fis.esme.core.smsc.util.QueueMTManager;
import com.fss.sql.Database;
import com.fss.sql.OracleConnectionFactory;
import com.fss.thread.ParameterType;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;
import com.fss.util.StringUtil;

/**
 * 
 * @implement by HungVM
 * 
 */
public class SMSTransactionLogger extends ManageableThreadEx {

	// //////////////////////////////////////////////////////
	// Member variables
	// //////////////////////////////////////////////////////
	// private static final int DO_NOTHING = 0;
	private static final int ATTACH_FIRST_LEVEL_STORAGE = 1;
	private static final int ATTACH_NEXT_LEVEL_STORAGE = 2;
	private static final int ATTACH_CURRENT_LEVEL_STORAGE = 3;
	private int miStorageLevel;
	private int miFailureAction;
	private boolean mbDisplayStackTrace;
	private long mlLastAvaiable = 0;
	private long mlCheckAvaiable = 0;
	private int miSuccessCount = 0;
	private int miFailureCount = 0;
	private int miMaxCommitCount = 0;
	private boolean mbIsLogBeforeClose = false;
//	private OracleConnectionFactory pool = null;
//	private String mstrDBUrl;
//	private String mstrDBUserName;
//	private String mstrDBPassword;
	private int mintRetryNumber = 0;
	private int mintNumberThread = 1;
	// DIRECTION

	private static String SMS_DIRECTION_IN = "I";
	private static String SMS_DIRECTION_OUT = "O";
	private static String SMS_DIRECTION_BOTH = "IO";
	private static String UPDATE_SMS_SUCESS = "UPDATE_SMS_SUCESS";
	private static String UPDATE_SCHEDULE_FAIL_DELIVERY = "7";
	private static String UPDATE_SCHEDULE_FAIL_ACK = "4";
	private static String UPDATE_SCHEDULE_TIMEDOUT = "5";
	private QueueMTManager mlSMSQueueLevel;

	// //////////////////////////////////////////////////////
	public Vector getParameterDefinition() {
		Vector vtReturn = new Vector();


		vtReturn.addElement(createParameterDefinition("NumberThread", "",
				ParameterType.PARAM_TEXTBOX_MAX, "10", "Number of Threads"));
//		vtReturn.addElement(createParameterDefinition("DBUrl", "",
//				ParameterType.PARAM_TEXTBOX_MAX, "256",
//				"Connection url of database"));
//		vtReturn.addElement(createParameterDefinition("DBUserName", "",
//				ParameterType.PARAM_TEXTBOX_MAX, "256", "DBA user name"));
//		vtReturn.addElement(createParameterDefinition("DBPassword", "",
//				ParameterType.PARAM_PASSWORD, "100",
//				"Password of DBA user name"));
		vtReturn.addElement(createParameterDefinition("CommitCount", "",
				ParameterType.PARAM_TEXTBOX_MASK, "999",
				"Commit after number of transaction"));
		vtReturn.addElement(createParameterDefinition("RetryNumber", "",
				ParameterType.PARAM_TEXTBOX_MASK, "999",
				"retrynumber when update smslog'status fail !"));
		vtReturn.addElement(createParameterDefinition(
				"ReloadCommand",
				"",
				ParameterType.PARAM_TEXTBOX_MAX,
				"512",
				"List of command_id will be treated as reload command (such as recharge, m); separated by comma (',') or semi colon (';')"));
		Vector vtValue = new Vector();
		vtValue.addElement("0");
		vtValue.addElement("1");
		vtValue.addElement("2");
		vtValue.addElement("3");
		vtReturn.addElement(createParameterDefinition("StorageLevel", "",
				ParameterType.PARAM_COMBOBOX, vtValue,
				"Specify storage level containt logrecord belong to this logger"));
		vtReturn.addElement(createParameterDefinition(
				"EmptyDuration",
				"",
				ParameterType.PARAM_TEXTBOX_MASK,
				"999990",
				"Amount of time that togging storage can be empty. If over, system will send error to user. Value of 0 mean unlimited"));
		vtValue = new Vector();
		vtValue.addElement("Do nothing");
		vtValue.addElement("Attach record to first level storage");
		vtValue.addElement("Attach record to next level storage");
		vtValue.addElement("Attach record to current level storage");
		vtReturn.addElement(createParameterDefinition("FailureAction", "",
				ParameterType.PARAM_COMBOBOX, vtValue,
				"Action will be performed with failure log record"));
		vtValue = new Vector();
		vtValue.addElement("Y");
		vtValue.addElement("N");
		vtReturn.addElement(createParameterDefinition("DisplayStackTrace", "",
				ParameterType.PARAM_COMBOBOX, vtValue,
				"Display stacktrace if error occured?"));
		vtReturn.addAll(super.getParameterDefinition());

		for (int iIndex = vtReturn.size() - 1; iIndex >= 0; iIndex--) {
			String strParameterName = (String) ((Vector) vtReturn
					.elementAt(iIndex)).elementAt(0);
			if (strParameterName.equals("ConnectDB")) {
				vtReturn.removeElementAt(iIndex);
			}
		}

		return vtReturn;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @throws AppException
	 */
	// //////////////////////////////////////////////////////
	public void fillParameter() throws AppException {
//		mstrDBUrl = loadMandatory("DBUrl");
//		mstrDBUserName = loadMandatory("DBUserName");
//		mstrDBPassword = loadMandatory("DBPassword");
		mintNumberThread = loadInteger("NumberThread");
		miMaxCommitCount = loadInteger("CommitCount");
		mintRetryNumber = loadInteger("RetryNumber");
		miStorageLevel = loadUnsignedInteger("StorageLevel");
		String strFailureAction = loadMandatory("FailureAction");
		miFailureAction = 0;
		if (strFailureAction.equals("Attach record to first level storage")) {
			miFailureAction = 1;
		} else if (strFailureAction
				.equals("Attach record to next level storage")) {
			miFailureAction = 2;
		} else if (strFailureAction
				.equals("Attach record to current level storage")) {
			miFailureAction = 3;
		}
		mbDisplayStackTrace = loadYesNo("DisplayStackTrace").equals("Y");
		if (miStorageLevel >= 3 && miFailureAction == ATTACH_NEXT_LEVEL_STORAGE) {
			throw new AppException(
					"Cannot set failure action attach next level when storage level is last level");
		}
		mlSMSQueueLevel = getThreadManager().getQueueMTManager();
		super.fillParameter();
		mbAutoConnectDB = true;
	}

	// //////////////////////////////////////////////////////
	/**
	 *
	 */
	// //////////////////////////////////////////////////////
	public void run() {
		try {
			super.run();
		} finally {
//			if (pool != null) {
//				try {
//					pool.close();
//				} catch (SQLException ex) {
//				}
//				pool = null;
//			}
		}
	}

	public class LogDBRecord implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (miThreadCommand != ThreadConstant.THREAD_STOP
					&& !mmgrMain.isServerLocked()) {
				try {
					// Fill log file
					fillLogFile();
					// Do logging
					doLogging();
					for (int iIndex = 0; iIndex < miDelayTime
							&& miThreadCommand != ThreadConstant.THREAD_STOP; iIndex++) {
						Thread.sleep(1000); // Time unit is second
					}

					if (mbIsLogBeforeClose) {
						break;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					logMonitor(e.getMessage());
					// throw e;
				}
			}

		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @throws Exception
	 */
	// //////////////////////////////////////////////////////
	protected void processSession() throws Exception {
		mlLastAvaiable = System.currentTimeMillis();
		if (mintNumberThread == 0) {
			mintNumberThread = 1;
		}
		for (int i = 0; i < mintNumberThread; i++) {
			LogDBRecord logrecord = new LogDBRecord();
			Thread thread = new Thread(logrecord);
			thread.start();
		}

		while (miThreadCommand != ThreadConstant.THREAD_STOP
				&& !mmgrMain.isServerLocked()) {
			// // Fill log file
			fillLogFile();
			// // Do logging
			// doLogging();
			for (int iIndex = 0; iIndex < miDelayTime
					&& miThreadCommand != ThreadConstant.THREAD_STOP; iIndex++) {
				Thread.sleep(1000); // Time unit is second
			}

			if (mbIsLogBeforeClose) {
				break;
			}
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @throws Exception
	 */
	// //////////////////////////////////////////////////////
	private void doLogging() throws Exception {
		Connection cn = null;
		miSuccessCount = 0;
		miFailureCount = 0;
		SmsBean bean = null;
		Vector vtLogged = new Vector();
		try {

			try {// Create pool
//				if (pool == null) {
//					pool = new OracleConnectionFactory(mstrDBUrl,
//							mstrDBUserName, mstrDBPassword, 1);
//				}
				// Open connection & create transaction
				cn = getThreadManager().getConnection();
			} catch (Exception e) {
				// TODO: handle exception
				logMonitor(e.getMessage());
				e.printStackTrace();
				throw e;
			}
			cn.setAutoCommit(true);
			// init bean for update tables
			bean = SmsBeanFactory.getSmsBeanFactory(getThreadManager()
					.getDatabaseMode());
			// process updates
			logAllAvailableRecord(cn, bean, vtLogged);
			// Commit transaction
			// cn.commit();
			// cn.setAutoCommit(true);
			// Report completed
			if (miSuccessCount > 0 || miFailureCount > 0) {
				logMonitor("Logging completed"
						+ (miSuccessCount > 0 ? ("\r\n\t" + miSuccessCount + " records was logged")
								: "")
						+ (miFailureCount > 0 ? ("\r\n\t" + miFailureCount + " records was rejected")
								: ""));
			}
		} catch (Exception e) {
			// Report first
			e.printStackTrace();

			// Reject all logged record
			while (vtLogged.size() > 0) {
				LogRecord record = (LogRecord) vtLogged.remove(0);
				reject(record);
				miFailureCount++;
			}

			// Reject all available record
			LogRecord record = null;
			while ((record = getThreadManager().detachLogRecord()) != null) {
				reject(record);
				miFailureCount++;
			}

			// Rollback
			if (cn != null) {
				cn.rollback();
				cn.setAutoCommit(true);
			}

			// Show log
			if (miFailureCount > 0) {
				logMonitor("Logging completed\r\n\t" + miFailureCount
						+ " records was rejected");
			}
		} finally {
//			if (pool != null) {
//				pool.close();
//				pool = null;
//			}
			Database.closeObject(cn);
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param record
	 *            LogRecord
	 */
	// //////////////////////////////////////////////////////
	protected void reject(LogRecord record) {
		if (miFailureAction == ATTACH_FIRST_LEVEL_STORAGE) {
			getThreadManager().attachLogRecord(record);
		} else if (miFailureAction == ATTACH_NEXT_LEVEL_STORAGE) {
			getThreadManager().attachLogRecord(record, miStorageLevel + 1);
		} else if (miFailureAction == ATTACH_CURRENT_LEVEL_STORAGE) {
			getThreadManager().attachLogRecord(record, miStorageLevel);
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param stmt
	 *            PreparedStatement
	 * @param stmtDetail
	 *            PreparedStatement
	 * @param vtLogged
	 *            Vector
	 * @return Map
	 */
	// //////////////////////////////////////////////////////
	private Map logAllAvailableRecord(Connection cn, SmsBean bean,
			Vector vtLogged) throws Exception {
		// Create session info
		Map mapSession = new LinkedHashMap();
		LogRecord record = null;
		miSuccessCount = 0;
		miFailureCount = 0;
		int iCommitCount = 0;
		while ((record = getThreadManager().detachLogRecord(miStorageLevel)) != null) {
			try {
				// Log record
				if (logOneRecord(cn,record, bean)) {
					miSuccessCount++;
					vtLogged.addElement(record);
				} else {
					miFailureCount++;
				}
				iCommitCount++;
				if (iCommitCount >= miMaxCommitCount) {
					cn.commit();
					iCommitCount = 0;
				}
			} catch (SQLException e) {
				miFailureCount++;
				if (mbDisplayStackTrace) {
					e.printStackTrace();
				}
				reject(record);
				throw e;
			} catch (Exception e) {
				miFailureCount++;
				if (mbDisplayStackTrace) {
					e.printStackTrace();
				}
				reject(record);
			}
		}
		return mapSession;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param stat
	 *            PreparedStatement
	 * @param iIndex
	 *            int
	 * @param str
	 *            String
	 * @param iLimit
	 *            int
	 * @throws SQLException
	 */
	// //////////////////////////////////////////////////////
	private void StatementSetString(PreparedStatement stat, int iIndex,
			String str, int iLimit) throws SQLException {
		if (iLimit > 0) {
			stat.setString(iIndex, correctParameterLength(str, iLimit));
		} else {
			stat.setString(iIndex, str);
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param record
	 *            LogRecord
	 * @param stmt
	 *            PreparedStatement
	 * @param stmtDetail
	 *            PreparedStatement
	 * @param inf
	 *            SessionRequestInfo
	 * @return boolean
	 * @throws Exception
	 */
	// //////////////////////////////////////////////////////
	private boolean logOneRecord(Connection con,Object record, SmsBean bean)
			throws Exception {
		final java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat(
				"yyyy-dd-MM HH:mm:ss");
		String SMS_LOG_ID = "";
		String SUB_ID = "";
		String SERVICE_ID = "0";
		String ACTION_ID = "0";
		String LANGUAGE_ID = "1";
		String SESSION_ID = "";
		String TYPE = "2";
		String MSISDN = "";
		String B_MSISDN = "";
		String SERVICE_NUMBER = "";
		String DIRECTION = "";
		String TRANS_DATETIME = "";
		String SMS_CONTENT = "";
		String STATUS = "";
		String SMSC_ID = "";
		String SMSC_MT_ID = "";
		String TRANS_ID = "0";
		String strError = "";
		String strActionType = "";
		String strSmsScheduleID = "";
		String strSubType = "";
		boolean blUpdateMOStatus4Retry = false;
		boolean blUpdateScheduleByTransId = false;

		if (record instanceof SMSLogRecord) {
			SMSLogRecord smsMessage = (SMSLogRecord) record;
			if (smsMessage != null) {
				MSISDN = smsMessage.getIsdn();
				STATUS = smsMessage.getStatus();
				SMSC_ID = StringUtil.nvl(smsMessage.getSMSC_ID(), "0");
				TRANS_ID = smsMessage.getCommandID();
				int successCount = bean.updateSMSLogTranID(con,STATUS, SMSC_ID,
						TRANS_ID);
				if (successCount == 0) {
					if (smsMessage.getRetryCount() < mintRetryNumber) {
						Thread.sleep(2000);
						smsMessage.increaseRetryCount();
						getThreadManager().attachLogRecord(smsMessage);
						logMonitor("Retried CommandID :"
								+ String.valueOf(TRANS_ID) + " Called :"
								+ MSISDN + " ,retried time:  "
								+ smsMessage.getRetryCount());
					}
				}
			}
		} else if (record instanceof LogRecord) {
			LogRecord logRecord = (LogRecord) record;
			strActionType = logRecord.getActionType();
			if (strActionType != ""
					&& strActionType
							.equalsIgnoreCase(LogRecord.ACTION_UPDATE_TYPE)) {
				STATUS = logRecord.getStatus();
				String strErrorCode = logRecord.getErrorDescription();
				SMS_LOG_ID = logRecord.getSessionID();
				SMSC_ID = StringUtil.nvl(logRecord.getSMSC_ID(), "0");
				TRANS_ID = StringUtil.nvl(logRecord.getCommandID(), "");
				try {
					int iUpdateCount = bean.updateSMSLogStatus(con,STATUS,
							strErrorCode, SMS_LOG_ID, TRANS_ID);
					if (iUpdateCount > 0) {
						if (logRecord.getStatus().equalsIgnoreCase(
								UPDATE_SCHEDULE_FAIL_DELIVERY)
								|| logRecord.getStatus().equalsIgnoreCase(
										UPDATE_SCHEDULE_FAIL_ACK)
								|| logRecord.getStatus().equalsIgnoreCase(
										UPDATE_SCHEDULE_TIMEDOUT)) {

							blUpdateScheduleByTransId = true;
						}
						debugMonitor(
								"Updated CommandID :"
										+ String.valueOf(TRANS_ID)
										+ ", Called :" + MSISDN + ", status: "
										+ STATUS + " ,success:  "
										+ iUpdateCount + ", sucessfully !", 3);
					}

					if (iUpdateCount > 0 && STATUS != null && STATUS != "") {
						String strDirection = logRecord.getDirection();
						if (strDirection != null
								&& strDirection != ""
								&& strDirection
										.equalsIgnoreCase(SMS_DIRECTION_IN)
								&& (STATUS.equalsIgnoreCase("3") || STATUS
										.equalsIgnoreCase("2"))) {
							int successCount = bean.updateSMSMOStatus(con,STATUS,
									logRecord.getSMSC_MO_ID());

							debugMonitor("Updated SMS_MO for retry, MO_ID :"
									+ String.valueOf(logRecord.getSMSC_MO_ID())
									+ ", Called :" + logRecord.getIsdn()
									+ ", status: " + STATUS + " ,success:  "
									+ successCount + ", sucessfully !", 3);

						} else if (strDirection != null
								&& strDirection != ""
								&& strDirection
										.equalsIgnoreCase(SMS_DIRECTION_OUT)
								&& (STATUS.equalsIgnoreCase("3") || STATUS
										.equalsIgnoreCase("2"))) {
							int successCount = bean.updateSMSRetryStatus(
									con,Integer.valueOf(STATUS),SMS_LOG_ID,
									Integer.valueOf(logRecord.getSMSC_MT_ID()));

							debugMonitor("Updated SMS_MT for retry, MT_ID :"
									+ String.valueOf(logRecord.getSMSC_MT_ID())
									+ ", Called :" + logRecord.getIsdn()
									+ ", status: " + STATUS + " ,success:  "
									+ successCount + ", sucessfully !", 3);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					strError = e.toString();
				}
			}

			else {
				debugMonitor("Action type : " + strActionType
						+ " not support !", 3);
			}

		}

		if (blUpdateScheduleByTransId) {
			try {
				bean.updateSMSMOStatus(con,String
						.valueOf(VariableStatic.SMS_MO_STATUS.FAIL_AND_RETRY
								.getValue()), SMSC_MT_ID);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (strError != null && strError.length() > 0) {
			logMonitor(strError);
			if (strError.contains("SQLException"))
				throw new SQLException(strError);
			throw new Exception(strError);
		}
		return true;
	}

	/**
	 * 
	 * @param strShortMessage
	 * @return
	 */
	private long getMessageID(String strShortMessage) {
		int beginIndex = strShortMessage.indexOf(":") + 1;
		int endIndex = strShortMessage.indexOf(" ");
		String strMessageID = strShortMessage.substring(beginIndex, endIndex);
		return Long.parseLong(strMessageID);
	}

	// //////////////////////////////////////////////////////
	/**
	 * if length of strParam more than limited length
	 * 
	 * @param strParame
	 *            String
	 * @param iLimit
	 *            int
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public static String correctParameterLength(String strParame, int iLimit) {
		if (strParame == null) {
			return "";
		}
		if (strParame.length() > iLimit) {
			return strParame.substring(0, iLimit);
		}
		return strParame;
	}

	// //////////////////////////////////////////////////////
	/**
	 *
	 */
	// //////////////////////////////////////////////////////
	public void setIsLogBeforeClose() {
		mbIsLogBeforeClose = true;
	}
	public static void main(String [] agm)
	{
		
	}
}
