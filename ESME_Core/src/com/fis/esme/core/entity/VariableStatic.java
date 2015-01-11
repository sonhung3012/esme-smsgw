package com.fis.esme.core.entity;

public class VariableStatic {
	public static final int iSecondTimeout = 180;

	public static final int iNumMiliSeconDelay = 100;

	public static String SMS_LOG_VALIDATE_SMS_SERVICE = "VALIDATE_SMS_SERVICE";
	public static String SMS_LOG_VALIDATE_CPID = "VALIDATE_CPID";
	public static String SMS_LOG_ISDN_BLACKLIST = "ISDN_BLACKLIST";
	public static String SMS_LOG_VALIDATE_ISDN_PREFIX = "VALIDATE_ISDN_PREFIX";
	public static String SMS_LOG_NO_SMSC_AVARIABLE = "NO_SMSC_AVARIABLE";

	public static String SMS_MO_RESPONSE_NULL_MSG = "RESPONSE_NULL_MSG";
	public static String SMS_MO_RESPONSE_NOT_SUPPORT_CMD = "RESPONSE_NOT_SUPPORT_CMD";
	public static String SMS_MO_RESPONSE_NOT_ROUTING_INFO = "RESPONSE_NOT_ROUTING_INFO";
	public static String SMS_MO_RESPONSE_SYSTEM_ERROR = "RESPONSE_SYSTEM_ERROR";
	
	public static String SMS_WEB_CONFIRM = "SMS_WEB_CONFIRM";

	public static String TYPE_BLACK_LIST = "1";
	public static String TYPE_WHILE_LIST = "2";

	public static String PROTOCAL_TYPE_SMPP = "1";
	public static String PROTOCAL_TYPE_HTTP = "2";
	public static String PROTOCAL_WEB_HANDLER = "3";
	public static String PROTOCAL_WEB_INTERNAL = "4";

	public static String SMS_DIRECTION_IN = "I";
	public static String SMS_DIRECTION_OUT = "O";
	public static String SMS_DIRECTION_BOTH = "IO";

	public static String DEFAULT_COMMAND_CODE = "SYSTEM";
	public static String DEFAULT_ALERT_CODE = "SYSTEM_ALERT";

	public static String PARAM_SYSTEM_TYPE_MSG = "TYPE_MSG";
	public static String PARAM_SYSTEM_TYPE_SYSTEM = "TYPE_SYSTEM";
	public static String PARAM_SYSTEM_TYPE_RESPONSE = "TYPE_RESPONSE";
	public static String HELP_CODE = "HELP_CODE";
	public static String VALIDATE_MABIENNHAN_CODE = "VALIDATE_CODE";
	// / cac ma cho phan dang ky thue bao
	public static String VALIDATE_GROUP_INPUT = "VALIDATE_GROUP_INPUT";
	public static String VALIDATE_GROUP_N0T_EXIS = "GROUP_N0T_EXIS";
	public static String VALIDATE_SUBSCRIBER_EXIS = "SUBSCRIBER_EXIS";
	public static String REG_SUBSCRIBER_SUCESSFULL = "REG_SUBSCRIBER";
	public static String REG_SUBSCRIBER_FAIL = "REG_SUBSCRIBER_FAIL";
	// cac ma cho ham huy dich vu
	public static String UNREG_SUBSCRIBER_NO_EXIS = "SUBSCRIBER_NO_EXIS";
	public static String UNREG_SUBSCRIBER_SUCESSFULL = "UNREG_SUBSCRIBER";
	public static String UNREG_SUBSCRIBER_FAIL = "UNREG_SUBSCRIBER_FAIL";

	public enum SMS_LOG_TYPE {

		MO(new Integer("1")), MT(new Integer("2"));

		private final Integer flg;

		SMS_LOG_TYPE(Integer flg) {
			this.flg = flg;
		}

		public Integer getValue() {
			return flg;
		}
	}

	public enum SMS_LOG_STATUS_MO {

		INSERT_NEW(new Integer("0")), LOADED(new Integer("9")), SEND_SUCCESS(
				new Integer("1")), FAIL_VALIDATE(new Integer("2")), CP_DONT_RECEIVER(
				new Integer("3"));

		private final Integer flg;

		SMS_LOG_STATUS_MO(Integer flg) {
			this.flg = flg;
		}

		public Integer getValue() {
			return flg;
		}
	}

	public enum SMS_LOG_STATUS {

		SUCCESS_CONFIRM(new Integer("0")), LOADED(new Integer("9")), SEND_SUCCESS(
				new Integer("1")), FAIL_VALIDATE_INFO(new Integer("2")), USER_DONT_RECEIVER(
				new Integer("3")), SMSC_SENDED(new Integer("4")), SMSC_ACKED(
				new Integer("5"));

		private final Integer flg;

		SMS_LOG_STATUS(Integer flg) {
			this.flg = flg;
		}

		public Integer getValue() {
			return flg;
		}
	}

	/**
	 * 
	 * @author Hero
	 * 
	 */
	public enum SMS_MO_STATUS {

		INSERT_NEW(new Integer("0")), LOADED(new Integer("9")), SEND_SUCCESS(
				new Integer("1")), FAIL_AND_RETRY(new Integer("2")), CP_DONT_RECEIVER(
				new Integer("3")), FAIL_VALIDATE_INFO(new Integer("6")), Retrying(
				new Integer("7"));

		private final Integer flg;

		SMS_MO_STATUS(Integer flg) {
			this.flg = flg;
		}

		public Integer getValue() {
			return flg;
		}
	}

	/**
	 * 
	 * @author Hero
	 * 
	 */
	public enum SMS_MT_STATUS {

		INSERT_NEW(new Integer("0")), LOADED(new Integer("9")), SEND_SUCCESS(
				new Integer("1")), FAIL_AND_RETRY(new Integer("2")), USER_DONT_RECEIVER(
				new Integer("3")), FAIL_VALIDATE_INFO(new Integer("6"));

		private final Integer flg;

		SMS_MT_STATUS(Integer flg) {
			this.flg = flg;
		}

		public Integer getValue() {
			return flg;
		}
	}

}
