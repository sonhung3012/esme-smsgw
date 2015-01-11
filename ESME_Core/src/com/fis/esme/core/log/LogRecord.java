package com.fis.esme.core.log;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import com.fss.queue.Attributable;
import com.fss.queue.Message;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c)
 * </p>
 * <p>
 * Company: FIS
 * </p>
 */

public class LogRecord implements Attributable {
	// //////////////////////////////////////////////////////
	// Constant
	// //////////////////////////////////////////////////////
	public static final String STATUS_SUCCESS = "0";
	public static final String STATUS_UNKNOWN = "1";
	// //////////////////////////////////////////////////////
	// Member variables
	// //////////////////////////////////////////////////////
	// them mot so thong tin de lay thoi gian theo Minisecons
	public static String CLIENT_RECEIVE_TIME = "CLIENT_RECEIVE_TIME";
	public static String CLIENT_RESPOND_TIME = "CLIENT_RESPOND_TIME";
	public static String SERVER_RECEIVE_TIME = "SERVER_RECEIVE_TIME";
	public static String SERVER_RESPOND_TIME = "SERVER_RESPOND_TIME";

	public static String BEFOR_ATTACT_QUEUE_TIME = "BEFOR_ATTACH_QUEUE_TIME";
	public static String ATTACTED_QUEUE_TIME = "ATTACHED_QUEUE_TIME";
	public static String BEGIN_ATTACH_QUEUE_TIME = "BEGIN_ATTACH_QUEUE_TIME";

	public static String BEFOR_DETACT_QUEUE_TIME = "ATTACHED_QUEUE_TIME";
	public static String DETACTED_QUEUE_TIME = "ATTACHED_QUEUE_TIME";
	// //////////////////////////////////////////////////////
	// Member variables
	// //////////////////////////////////////////////////////
	public static String ACTION_INSERT_TYPE = "INSERT_TYPE";
	public static String ACTION_UPDATE_TYPE = "UPDATE_TYPE";
	public static String ACTION_UPDATE_ACK_TYPE = "UPDATE_ACK_TYPE";
	public long mlStartTime = 0;
	// //////////////////////////////////////////////////////////
	private Vector mvtServerRequest = new Vector();
	private Vector mvtServerResponse = new Vector();
	private Date mdtRequest;
	private Date mdtResponse;
	private Date mdtServerRequest;
	private Date mdtServerResponse;
	private String mstrRequestID;
	private String mstrCommand;
	private String mstrCommandID;
	private String mstrSessionID;
	private String mstrClientProtocol;
	private String mstrIsdn;
	private String mstrDispatcherID;
	private String mstrServerProtocol;
	private String mstrStatus;
	private String mstrErrorLevel;
	private String mstrErrorDescription;
	private String mstrServiceNumber;
	private String mstrContent;
	private String mstrDirection;
	private String mstrB_msisdn;
	private String mstrType;
	private String mstrResponse;
	private String mstrRequest;
	private String mstrActionType;
	private String mstrSMSC_ID;
	private String mstrSMSC_MO_ID;
	private String mstrSMSC_MT_ID;
	private String mstrESMETransID;
	private String mstrRegisterDelivery;

	private String mstrUrl;
	private String mstrUsername;
	private String mstrPassword;
	
	private String mstrSubID;
	private String mstrGroupID;
	
	
	
	public String getSubID() {
		return mstrSubID;
	}

	public void setSubID(String mstrSubID) {
		this.mstrSubID = mstrSubID;
	}

	public String getGroupID() {
		return mstrGroupID;
	}

	public void setGroupID(String mstrGroupID) {
		this.mstrGroupID = mstrGroupID;
	}

	public String getUrl() {
		return mstrUrl;
	}

	public void setUrl(String mstrUrl) {
		this.mstrUrl = mstrUrl;
	}

	public String getUsername() {
		return mstrUsername;
	}

	public void setUsername(String mstrUsername) {
		this.mstrUsername = mstrUsername;
	}

	public String getPassword() {
		return mstrPassword;
	}

	public void setPassword(String mstrPassword) {
		this.mstrPassword = mstrPassword;
	}
	
	public String getRegisterDelivery() {
		return mstrRegisterDelivery;
	}

	public void setRegisterDelivery(String strRegisterDelivery) {
		mstrRegisterDelivery = strRegisterDelivery;
	}
	
	public String getESMETransID() {
		return mstrESMETransID;
	}

	public void setESMETransID(String strESMETransID) {
		mstrESMETransID = strESMETransID;
	}
	
	public String getSMSC_MO_ID() {
		return mstrSMSC_MO_ID;
	}

	public void setSMSC_MO_ID(String mstrSMSC_MO_ID) {
		this.mstrSMSC_MO_ID = mstrSMSC_MO_ID;
	}

	public String getSMSC_MT_ID() {
		return mstrSMSC_MT_ID;
	}

	public void setSMSC_MT_ID(String mstrSMSC_MT_ID) {
		this.mstrSMSC_MT_ID = mstrSMSC_MT_ID;
	}

	private Hashtable mattProperty = new Hashtable();

	/**
	 * 
	 * @return
	 */
	public long getStartTime() {
		return mlStartTime;
	}

	/**
	 * 
	 * @param starttime
	 */
	public void setStartTime(long starttime) {
		mlStartTime = starttime;
	}

	/**
	 * 
	 */
	public LogRecord() {
		mlStartTime = System.currentTimeMillis();
	}

	/**
	 * 
	 * @return
	 */
	public String getActionType() {
		return mstrActionType;
	}

	/**
	 * 
	 * @param actiontype
	 */
	public void setActionType(String actiontype) {
		mstrActionType = actiontype;
	}

	public void setServiceNumber(String ServiceNumber) {
		mstrServiceNumber = ServiceNumber;
	}

	public String getServiceNumber() {
		return mstrServiceNumber;
	}

	public void setType(String Type) {
		mstrType = Type;
	}

	public String getType() {
		return mstrType;
	}

	public void setB_msisdn(String B_msisdn) {
		mstrB_msisdn = B_msisdn;
	}

	public String getB_msisdn() {
		return mstrB_msisdn;
	}

	public void setDirection(String Direction) {
		mstrDirection = Direction;
	}

	public String getDirection() {
		return mstrDirection;
	}

	public void setContent(String Content) {
		mstrContent = Content;
		mdtRequest = new Date();
	}

	public String getContent() {
		return mstrContent;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return int
	 */
	// //////////////////////////////////////////////////////
	public int getErrorLevelAsInt() {
		try {
			return Integer.parseInt(mstrErrorLevel);
		} catch (Exception ex) {
			return 9;
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @throws Exception
	 */
	// //////////////////////////////////////////////////////
	public void correctData() throws Exception {
		// Status
		if (mstrStatus == null) {
			mstrStatus = LogRecord.STATUS_UNKNOWN;
		}
		// Request date
		if (mdtServerRequest == null) {
			mdtServerRequest = new Date();
		}
		if (mdtRequest == null) {
			mdtRequest = mdtServerRequest;
		}

		// Response date
		if (mdtServerResponse == null) {
			mdtServerResponse = new Date();
		}
		if (mdtResponse == null) {
			mdtResponse = mdtServerResponse;
		}

		// Others
		if (mstrIsdn == null) {
			mstrIsdn = "";
		}
		if (mstrDispatcherID == null) {
			mstrDispatcherID = "";
		}
		if (mstrServerProtocol == "") {
			mstrServerProtocol = "";
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return Date
	 */
	// //////////////////////////////////////////////////////
	public Date getRequestDate() {
		return mdtRequest;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return Date
	 */
	// //////////////////////////////////////////////////////
	public Date getServerRequestDate() {
		return mdtServerRequest;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return Date
	 */
	// //////////////////////////////////////////////////////
	public Date getResponseDate() {
		return mdtResponse;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return Date
	 */
	// //////////////////////////////////////////////////////
	public Date getServerResponseDate() {
		return mdtServerResponse;
	}

	public String getRequestMsg() {
		return mstrRequest;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return Message
	 */

	/**
	 * 
	 * @param msg
	 */
	public void setRequestMsg(String msg) {
		mstrRequest = msg;
		mdtRequest = new Date();
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param msg
	 *            Message
	 */
	// //////////////////////////////////////////////////////
	public void setRequest(Message msg) {
		mdtRequest = new Date();
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return Vector
	 */
	// //////////////////////////////////////////////////////
	public Vector getServerRequest() {
		return mvtServerRequest;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param msg
	 *            Message
	 */
	// //////////////////////////////////////////////////////
	public void addServerRequest(Message msg) {
		mvtServerRequest.add(msg);
		if (mdtServerRequest == null) {
			mdtServerRequest = new Date();
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getResponseMsg() {
		return mstrResponse;
	}

	// //////////////////////////////////////////////////////

	/**
	 * 
	 * @param msg
	 */
	public void setResponseMsg(String msg) {
		mstrResponse = msg;
		mdtResponse = new Date();
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param msg
	 *            Message
	 */
	// //////////////////////////////////////////////////////
	public void setResponse(Message msg) {
		mdtResponse = new Date();
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return Message
	 */
	// //////////////////////////////////////////////////////
	public Vector getServerResponse() {
		return mvtServerResponse;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param msg
	 *            Message
	 */
	// //////////////////////////////////////////////////////
	public void addServerResponse(Message msg) {
		mvtServerResponse.add(msg);
		mdtServerResponse = new Date();
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getCommand() {
		return mstrCommand;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strCommand
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setCommand(String strCommand) {
		mstrCommand = strCommand.toLowerCase();
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getRequestID() {
		return mstrRequestID;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strRequestID
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setRequestID(String strRequestID) {
		mstrRequestID = strRequestID;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getSessionID() {
		return mstrSessionID;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strSessionID
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setSessionID(String strSessionID) {
		mstrSessionID = strSessionID;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getClientProtocol() {
		return mstrClientProtocol;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strClientProtocol
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setClientProtocol(String strClientProtocol) {
		mstrClientProtocol = strClientProtocol;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getIsdn() {
		return mstrIsdn;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strIsdn
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setIsdn(String strIsdn) {
		mstrIsdn = validateISDN(strIsdn);
	}

	public String validateISDN(String isdn) {
		String strisdn = isdn;
		if (strisdn.startsWith("84")) {
			strisdn = strisdn.substring(2);
		}
		if (strisdn.startsWith("0")) {
			strisdn = strisdn.substring(1);
		}
		return strisdn;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getDispatcherID() {
		return mstrDispatcherID;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strDispatcherID
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setDispatcherID(String strDispatcherID) {
		mstrDispatcherID = strDispatcherID;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getServerProtocol() {
		return mstrServerProtocol;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strServerProtocol
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setServerProtocol(String strServerProtocol) {
		mstrServerProtocol = strServerProtocol;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getStatus() {
		return mstrStatus;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strStatus
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setStatus(String strStatus) {
		mstrStatus = strStatus;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getErrorLevel() {
		return mstrErrorLevel;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strErrorLevel
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setErrorLevel(String strErrorLevel) {
		mstrErrorLevel = strErrorLevel;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getErrorDescription() {
		return mstrErrorDescription;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strErrorDescription
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setErrorDescription(String strErrorDescription) {
		mstrErrorDescription = strErrorDescription;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getCommandID() {
		return mstrCommandID;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strCommandID
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setCommandID(String strCommandID) {
		mstrCommandID = strCommandID;
	}

	public void setSMSC_ID(String str) {
		mstrSMSC_ID = str;
	}

	public String getSMSC_ID() {
		return mstrSMSC_ID;
	}

	@Override
	public Object getAttribute(String arg0) {
		return mattProperty.get(arg0);
	}

	@Override
	public Map getAttributes() {
		return mattProperty;
	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		mattProperty.put(arg0, arg1);
	}

	@Override
	public void setAttributes(Map arg0) {
		mattProperty.putAll(arg0);
	}
}
