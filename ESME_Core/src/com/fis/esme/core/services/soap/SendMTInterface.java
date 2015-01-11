package com.fis.esme.core.services.soap;

import java.sql.Connection;
import java.util.Vector;
import com.fis.esme.core.app.AppManager;
import com.fis.esme.core.app.ThreadManagerEx;
import com.fis.esme.core.bean.SmsBean;
import com.fis.esme.core.bean.SmsBeanFactory;
import com.fis.esme.core.entity.SubscriberObject;
import com.fis.esme.core.exception.LoginException;
import com.fss.sql.Database;
import com.fss.util.StringUtil;
import com.logica.smpp.Data;

public class SendMTInterface {
	private SmsBean bean = null;

	public SendMTInterface() {
		bean = SmsBeanFactory.getSmsBeanFactory(ThreadManagerEx
				.getDatabaseMode());
	}

	public int login(String username, String password) {
		try {
			return checkIdentity(username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	private Vector getUserInfo(String userid) {
		Connection con = null;
		try {
			con = AppManager.getAppConnection();
			return bean.loadPassword(con, userid);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(con);
		}
		return null;

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
			String registerDl, String subid, String groupid) {
		Connection con = null;
		try {
			con = AppManager.getAppConnection();
			bean.insertSMSMT(con, requestid, message, cp_id, shortcode, msisdn,
					commandcode, registerDl, "0", "0", subid, groupid);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
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
			con = AppManager.getAppConnection();
			String smsLogSeq = Database.getSequenceValue(con, "sms_log_seq");
			bean.insertSMSLog(con, smsLogSeq, request_id, msisdn, status, "2",
					"0", "0", "0", "0", cp_id, "0", "0", "0", "0", null,
					sms_content, "0", "0", error_code, strFileUploadID, subid,
					groupid,0,0,0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(con);
		}
	}

	public boolean validateCPInfo(String shortcode, String commandcode, int cpId) {
		return true;
	}

	public SubscriberObject getSubscriberInfo(String isdn) {
		Connection con = null;
		try {
			con = AppManager.getAppConnection();
			if (ThreadManagerEx.getCheckSubscriber()) {
				SubscriberObject subscriberObject = bean.getSubscriberInfo(con,
						isdn);
				return subscriberObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(con);
		}
		return null;
	}

	public int checkIdentity(String strCpUser, String strCpPass)
			throws Exception {
		int commandStatus = Data.ESME_ROK;
		int cpId = 0;
		Vector vtUser = getUserInfo(strCpUser);
		if (vtUser != null) {
			String password = vtUser.get(2).toString();
			try {
				strCpPass = StringUtil.encrypt(strCpPass, getAlgorithm());
				if (!strCpPass.equals(password)) {
					commandStatus = Data.ESME_RINVPASWD;
					System.out.println("system id " + strCpUser
							+ " not authenticated. Invalid password.");
					System.out.println("not authenticated " + strCpUser
							+ " -- invalid password");
				} else {
					cpId = Integer.valueOf(vtUser.get(0).toString());
					System.out.println("authenticated " + strCpUser);
				}
			} catch (Exception e) {
				e.printStackTrace();
				commandStatus = Data.ESME_RSYSERR;
			}
		} else {
			commandStatus = Data.ESME_RINVSYSID;
			System.out.println("system id " + strCpUser
					+ " not authenticated -- not found");
			System.out.println("not authenticated " + strCpUser
					+ " -- user not found");
		}
		if (commandStatus != Data.ESME_ROK) {
			throw new LoginException(commandStatus, "Login Fail, code :"
					+ commandStatus);
		}
		return cpId;
	}

	public String getAlgorithm() {
		return "SHA";
	}
}
