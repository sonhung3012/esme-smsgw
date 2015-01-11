package com.fis.esme.core.smsc.util;

public class SendSMSLog {
	public static final String WAIT_RESPONSE = "0";
	public static final String SUCCESSFULL = "0";
	public static final String SEND_TO_USER_FAIL = "7";
	public static final String SEND_TO_SMSC_SUCCESS = "8";
	public static final String SEND_SMSC_FAIL = "4";
	public static final String TIME_OUT_MSG = "5";
	

	private String SMS_STATUS = "";

	private String SMS_LOG_ID = "";

	private String HAS_ADV_MSG = "";

	/**
	 * 
	 * @param hasadv
	 */
	public void setHasAdvMsg(String hasadv) {
		HAS_ADV_MSG = hasadv;
	}

	/**
	 * 
	 * @return
	 */
	public String getHasAdvMsg() {
		return HAS_ADV_MSG;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		SMS_STATUS = status;
	}

	/**
	 * 
	 * @return
	 */
	public String getStatus() {
		return SMS_STATUS;
	}

	/**
	 * 
	 * @param logid
	 */
	public void setLogID(String logid) {
		SMS_LOG_ID = logid;
	}

	/**
	 * 
	 * @return
	 */
	public String getLogID() {
		return SMS_LOG_ID;
	}
}
