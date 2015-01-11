package com.fis.esme.core.services.soap;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.axis.MessageContext;
import org.apache.log4j.Logger;

import com.fis.esme.core.entity.SubscriberObject;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

public class SendMT {
	private Logger logger = Logger.getLogger(SendMT.class);
	private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<String, Session>();
	private long messageTimeout = 5 * 60 * 1000;
	private long iSessionTimeout = 1 * 24 * 60 * 60 * 1000;
	private final java.text.SimpleDateFormat fmtDate = new java.text.SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	private static String RESPONSE_CODE_VALIDATE_USER = "CGW-01";
	private static String RESPONSE_CODE_VALIDATE_PARAMERTER = "CGW-02";
	private static String RESPONSE_CODE_INTERNAL_ERROR = "CGW-03";
	private SendMTInterface sendMTInterface = new SendMTInterface();

	private boolean login(String strUserName, String strPassword, String ip) {
		try {
			int userid = sendMTInterface.login(strUserName, strPassword);
			if (userid > 0) {
				// Build a Session and put to Sessions
				Session session = new Session();
				session.setUser(strUserName);
				session.setPassword(strPassword);
				session.setUserid(userid);
				session.setFirstSendRequest(System.currentTimeMillis());
				session.setClientAddress(ip);
				sessions.put(ip, session);
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	private void logout() {
		try {
			MessageContext messageContext = MessageContext.getCurrentContext();
			String remoteIP = (String) messageContext
					.getProperty("CLIENT_ADDRESS");
			Session session = sessions.remove(remoteIP);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public SendMTReponse charging(String requestid, String account,
			String pass, String msisdn, String shortcode, String commandcode,
			String message) {
		// /validate input parameters
		if (requestid == null || requestid == "") {
			SendMTReponse charingReponse = new SendMTReponse();
			charingReponse.setExcetedTime(fmtDate.format(new Date()));
			charingReponse.setResponseCode(RESPONSE_CODE_VALIDATE_PARAMERTER);
			charingReponse
					.setDescription("The paramerter can not be null. Parameter name: requestid");
			return charingReponse;
		}
		if (msisdn == null || msisdn == "") {
			SendMTReponse charingReponse = new SendMTReponse();
			charingReponse.setExcetedTime(fmtDate.format(new Date()));
			charingReponse.setResponseCode(RESPONSE_CODE_VALIDATE_PARAMERTER);
			charingReponse
					.setDescription("The paramerter can not be null. Parameter name: msisdn");
			return charingReponse;
		} else {
			if (msisdn.startsWith("84")) {
				msisdn = msisdn.substring(2);
			}
			if (msisdn.startsWith("0")) {
				msisdn = msisdn.substring(1);
			}
		}
		if (shortcode == null || shortcode == "") {
			SendMTReponse charingReponse = new SendMTReponse();
			charingReponse.setExcetedTime(fmtDate.format(new Date()));
			charingReponse.setResponseCode(RESPONSE_CODE_VALIDATE_PARAMERTER);
			charingReponse
					.setDescription("The paramerter can not be null. Parameter name: shortcode");
			return charingReponse;
		}
		if (commandcode == null || commandcode == "") {
			SendMTReponse charingReponse = new SendMTReponse();
			charingReponse.setExcetedTime(fmtDate.format(new Date()));
			charingReponse.setResponseCode(RESPONSE_CODE_VALIDATE_PARAMERTER);
			charingReponse
					.setDescription("The paramerter can not be null. Parameter name: commandcode");
			return charingReponse;
		}
		if (message == null || message == "") {
			SendMTReponse charingReponse = new SendMTReponse();
			charingReponse.setExcetedTime(fmtDate.format(new Date()));
			charingReponse.setResponseCode(RESPONSE_CODE_VALIDATE_PARAMERTER);
			charingReponse
					.setDescription("The paramerter can not be null. Parameter name: content");
			return charingReponse;
		}
		MessageContext context = MessageContext.getCurrentContext();
		// get the client ip address
		String remoteIP = (String) context.getProperty("CLIENT_ADDRESS");
		Session session = sessions.get(remoteIP);
		String sessionid;
		boolean loginReponse = true;
		if (session == null) {
			loginReponse = login(account, pass, remoteIP);
		} else {
			long fisrtResquest = session.getFirstSendRequest();
			if (!session.getUser().equalsIgnoreCase(account)
					|| !session.getPassword().equalsIgnoreCase(pass)
					|| System.currentTimeMillis() - fisrtResquest > iSessionTimeout) {
				sessions.remove(remoteIP);
				loginReponse = login(account, pass, remoteIP);
			}
		}
		if (!loginReponse) {
			SendMTReponse charingReponse = new SendMTReponse();
			charingReponse.setExcetedTime(fmtDate.format(new Date()));
			charingReponse.setResponseCode("SMSGW-LOGIN-01");
			charingReponse
					.setDescription("The authencation is not correct, from account :"
							+ account);
			return charingReponse;
		}
		Session currentsession = sessions.get(remoteIP);
		if (currentsession == null) {
			SendMTReponse charingReponse = new SendMTReponse();
			charingReponse.setExcetedTime(fmtDate.format(new Date()));
			charingReponse.setResponseCode(RESPONSE_CODE_INTERNAL_ERROR);
			charingReponse.setDescription("WS-103: session with ip :"
					+ remoteIP + " does not exist !");
			return charingReponse;
		} else {
			String strSubId = "0";
			String strGroupId = "0";
			SubscriberObject subscriberObject = sendMTInterface
					.getSubscriberInfo(msisdn);
			if (subscriberObject != null) {
				strSubId = subscriberObject.getSubscriberID();
				strGroupId = subscriberObject.getSubGroupID();
			}
			sendMTInterface.insertMT(requestid, message,
					String.valueOf(currentsession.getUserid()), shortcode,
					msisdn, commandcode, "0", strSubId, strGroupId);
			SendMTReponse charingReponse = new SendMTReponse();
			charingReponse.setExcetedTime(fmtDate.format(new Date()));
			charingReponse.setResponseCode("SMSGW-0000");
			charingReponse.setDescription("Sent message sucessfully !");
			return charingReponse;
		}
	}

	private static boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public static void clear() {
		sessions.clear();
	}

}
