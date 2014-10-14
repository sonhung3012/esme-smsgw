package com.fis.esme.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BusinessUtil {
	public static final String stringShortDateFormat = "dd/MM/yyyy";

	public static final SimpleDateFormat simpleShortDateFormat = new SimpleDateFormat(
			stringShortDateFormat);

	public static final String stringDateFormat = "dd/MM/yyyy HH:mm:ss";

	public static final String stringSQLDateFormat = "dd/MM/yyyy HH24:MI:SS";

	public static final String msisdnStartsWith = "840,84,0";

	public static final String searchStartsWith = "@SWK-";
	public static final String noteqsystemalert = "SYSTEM_ALERT";

	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			stringDateFormat);

	public static String DateToString(Date date, String strFormat) {
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
			return simpleDateFormat.format(date);
		} else
			return null;
	}

	public static Date StringToDate(String dateString, String strFormat) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
			return simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String arrToString(String[] arr) {
		String str = "";
		for (String string : arr) {
			str += "," + string;
		}
		str = str.substring(1);
		return str;
	}

	public static String listIntegerToString(List<Integer> lstInt) {
		String strActionIDs = "";
		int lstSize = lstInt.size();
		for (int i = 0; i < lstSize; i++) {
			if (i == 0)
				strActionIDs += lstInt.get(i);
			else
				strActionIDs += "," + lstInt.get(i);
		}
		return strActionIDs;
	}

	public static String listLongToString(List<Long> lstInt) {
		String strActionIDs = "";
		int lstSize = lstInt.size();
		for (int i = 0; i < lstSize; i++) {
			if (i == 0)
				strActionIDs += lstInt.get(i);
			else
				strActionIDs += "," + lstInt.get(i);
		}
		return strActionIDs;
	}

	public static String listStringToString(List<String> lstInt) {
		String strActionIDs = "";
		int lstSize = lstInt.size();
		for (int i = 0; i < lstSize; i++) {
			if (i == 0)
				strActionIDs += lstInt.get(i);
			else
				strActionIDs += "," + lstInt.get(i);
		}
		return strActionIDs;
	}

	public static String cutMSISDNStartsWidth(String msisdn) {

		if (msisdn != null) {

			if (msisdn.startsWith("+"))
				msisdn = msisdn.replace("+", "");

			String[] arr = msisdnStartsWith.split(",");
			for (String string : arr) {
				if (msisdn.startsWith(string)) {
					msisdn = msisdn.replaceFirst(string, "");
					return msisdn;
				}
			}
		}
		return msisdn;
	}

	public static String checkStartsWith(String str) {
		if (str.startsWith(searchStartsWith))
			return str.replace(searchStartsWith, "").trim();
		return null;
	}
	
	public static boolean stringIsNullOrEmty(Object str) {
		if (str != null) {
			if (str.toString().length() <= 0)
				return true;
		} else
			return true;
		return false;
	}

	public static String nvl(String str, String strReturn) {
		if (str != null) {
			if (str.length() <= 0)
				return strReturn;
		} else
			return strReturn;
		return str;
	}
}
