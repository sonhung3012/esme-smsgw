package com.fis.esme.core.util;

import java.util.Date;
import java.util.Hashtable;

public class GlobalParameter
{
	public static int iNumMissCall = 4;
	public static int iNumDayMissCall = 4;
	public static int iNumRetryTime = 10;	
	public static int iNumMiliSeconDelay = 1000;
	public static long mlSynchronizeInterval = 300000;
	public static String strChannelCode = "SMS";
	public static String strChannelId = "";
	public static String strSystemErrorInteractionCode = "SYSTEM_EROR";
	public static Hashtable<String, String> htbSystemErrorMessage = new Hashtable<String, String>();
	public static Hashtable mhtbCommandParams = new Hashtable();
	public static String	  mstrLanguageDefaul  = "1";
	public static Hashtable mhtbResponse = new Hashtable();
	public static Hashtable mhtbMappingResponse = new Hashtable();
	public static Hashtable mhtbLanguageCode = new Hashtable();
	public static Hashtable mhtbPromMessage = new Hashtable();
	public static Hashtable mhtbStub = new Hashtable();
	public static String[] arrISDN;
    public static String ERROR_CODE="ERROR_CODE"; 
    public static String ERROR_MSG="ERROR_MSG"; 
    public static String SUB_ID="SUB_ID"; 
    public static String LANG_ID="LANG_ID"; 
    public static String SUCCESS = "1"; 
    public static String FAIL = "0"; 
    public static final String WAIT_RESPONSE = "-1"; // Tráº¡ng thÃ¡i Ä‘ang Ä‘Æ°á»£c sá»­ lÃ½ vÃ  Ä‘ang Ä‘á»£i káº¿t quáº£ cá»§a bÆ°á»›c tiáº¿p theo.
    public static final String SUCCESSFULL = "1"; // Tráº¡ng thÃ¡i gá»­i thÃ nh cÃ´ng 
    public static final String SEND_TO_SMSC_FAIL = "2"; // Tráº¡ng thÃ¡i gá»­i tá»›i SMSC lá»—i EG. Do lá»—i káº¿t ná»‘i vá»›i SMSC ...
    public static final String SEND_SMSC_FAIL = "3"; // Tráº¡ng thÃ¡i SMSC gá»­i cho ngÆ°á»�i dÃ¹ng lá»—i 
    public static final String ORTHER_RESION = "4"; // Tráº¡ng thÃ¡i khÃ¡c náº¿u cÃ³ . 
    
    public static final String ADV_SUBSCRIBER = "2"; // gia tri cua advsubtype. 
    public static final String MCA_SUBSCRIBER = "1"; // gia tri cua mcasubtype. 
    public static final String SUBSCRIBER_TYPE = "SUBSCRIBER_TYPE"; // key . 
    
    public static String SYSTEM_ERROR_CODE = "MCA_ERROR_CODE_NOT-CORRECT"; 
	public static String FORMAT_DATE_MISSCALL = "dd-MM-yyyy HH:mm:ss";
    public static java.text.SimpleDateFormat fmtFull = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    public static char LIST_MSG_SEPARATOR='$';
    public static char VARIABLE_MSG_SEPARATOR='#';
    public static String INDEX_MSG_SEPARATOR="%i%";
    
    public static String RESPONSE_PARAM_NAME="RESPONSE_PARAM_TEXT";
    public static String RESPONSE_PARAM_PHONEBOOK="PARAM_PHONEBOOK";
    
    
	public static String SCHEDULER_MODE_DIRECTLY = "1";
	public static String SCHEDULER_MODE_WEEKLY = "2";
	public static String SCHEDULER_MODE_MONTHY = "3";
	public static String SCHEDULER_MODE_THELONG = "4";
	public static final String SCHEDULER_STATUS_FAIL = "3";
	public static final String SCHEDULER_STATUS_DONE = "2";
	public static final String SCHEDULER_STATUS_INITAL = "0";
	public static final String SCHEDULER_STATUS_PROCESSING = "1";
	
	public static java.text.SimpleDateFormat fmtDateformatPartem = new java.text.SimpleDateFormat(
	"yyyy/MM/dd HH:mm:ss");

public static java.text.SimpleDateFormat fmtHoursPartem = new java.text.SimpleDateFormat(
	"HHmmss");

public static java.text.SimpleDateFormat fmtDateStringFormat = new java.text.SimpleDateFormat(
	"yyyyMMdd");

public static String formatHours(Date value) {
try {
	return fmtHoursPartem.format(value);
} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}
return null;
}

public static String formatDate(Date value) {
try {
	return fmtDateStringFormat.format(value);
} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}
return null;
}

public static Date formatDate(Object value) {
try {
	String strValue = formatString(value);
	return fmtDateformatPartem.parse(strValue);
} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}
return null;
}

/**
* 
* @param value
* @return
*/
public static int formatInt(Object value) {
try {
	return Integer.valueOf(formatString(value));
} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}
return -1;
}

/**
* 
* @param value
* @return
*/
public static int formatInt(String value) {
try {
	return Integer.valueOf(value);
} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}
return -1;
}

/**
* 
* @param value
* @return
*/
public static String formatString(Object value) {
try {
	return String.valueOf(value);
} catch (Exception e) {
	e.printStackTrace();
	// TODO: handle exception
}
return null;
}
	
}
