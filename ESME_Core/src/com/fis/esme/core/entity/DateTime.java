package com.fis.esme.core.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	public static final SimpleDateFormat sdfFormatDateFull = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat sdfFormatDateSimple = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final String INPUT_DATE_NOT_CORRECT = "INPUT_DATE_NOT_CORRECT";

	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date formatSimpleDate(String strDate) {
		try {
			Date date = sdfFormatDateSimple.parse(strDate);
			return date;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date formatFullDate(String strDate) {
		try {
			Date date = sdfFormatDateFull.parse(strDate);
			return date;
		} catch (Exception e) {
		}
		return null;
	}
}
