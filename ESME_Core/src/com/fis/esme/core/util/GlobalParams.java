package com.fis.esme.core.util;

/**
 * <p>
 * Title: MCA Core
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * <p>
 * Company: FIS-SOT
 * </p>
 * 
 * @author LiemLT
 * @version 1.0
 */
public class GlobalParams
{
	// reasion id parameters global
	public static int REA_CLOSED_HLR = 1;
	public static int REA_CLOSED_SILENT = 2;
	public static int REA_CLOSED_TIMEOUT = 3;

	// session status parameters global
	public static int STATUS_ONL = 0;
	public static int STATUS_OFF = 1;
	public static int STATUS_TIMEOUT = 2;
	public static int STATUS_SENT = 3;

	// Attempt history type
	public static int ATTEMPT_HLR_ALERT = 1;
	public static int ATTEMPT_SMS_SILENT = 2;

	// Ack Status
	public static String ACK_NONE = "0";
	public static String ACK_OK = "1";
	public static String ACK_ERR = "2";

	// SMS type
	public static int SMS_SERVICE = 1;
	public static int SMS_ADVERTISE = 2;

	// Chars for replace message
	public static String MSISDN = "#MSISDN#";
	public static String CALLER_NAME = "#NAME#";
	public static String TOTAL_CALLS = "#TOTAL_CALLS#";
	public static String CALL_AMOUNT = "#CALL_AMOUNT#";
	public static String DATE_TIME = "#DATE_TIME#";
	public static String BODY_REPLACE = "#REPLACE#";
	public static String BODY_START = "$BEGIN";
	public static String BODY_END = "END$";
	public static String CONTENT = "#CONTENT#";
	public static String FWDER_MSISDN = "#FWDER_MSISDN#";

	public static String FORMAT_DATE_TIME = "dd-MM-yyyy HH:mm:ss";
	// Print statistic
	public static int miTimeout = 30; // 2 minutes;
	public static final String SUBSCRIBER_ADV_TYPE = "2";
	public static final String SUBSCRIBER_REAL_TYPE = "1";
}
