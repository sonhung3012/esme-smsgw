package com.fis.esme.core.services;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.tempuri.BasicHttpServiceStub;

import com.fis.esme.core.app.AppManager;
import com.fis.esme.core.app.ThreadManagerEx;
import com.fis.esme.core.bean.subscriber.SMSSubscriber;
import com.fis.esme.core.bean.subscriber.SMSSubscriberFactory;
import com.fis.esme.core.entity.VariableStatic;
import com.fss.sql.Database;

public class ServicesInternalProcessor {

	public String help(String isdn, String content, String commandcode,
			String shortcode, String url) {
		try {
			String strResponse = ThreadManagerEx.getSystemParameterMessage(
					VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
					VariableStatic.HELP_CODE);
			if (strResponse == null || strResponse == "") {
				System.out.println("Msg Type :"
						+ VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE
						+ ", Msg Code :" + VariableStatic.HELP_CODE
						+ " is not defind");
				return "cam on ban da nhan tin len he thong, chung toi se phan hoi sau it phut !";
			}
			return strResponse;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	public String getProfileStatus(String isdn, String content,
			String commandcode, String shortcode, String url) {
		try {
			String strReturn = "";
			if (content == null || content == "") {
				strReturn = ThreadManagerEx.getSystemParameterMessage(
						VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
						VariableStatic.VALIDATE_MABIENNHAN_CODE);
				return strReturn;
			} else {
				String strSeparateChar = " ";
				if (content.contains("_")) {
					strSeparateChar = "_";
				} else if (content.contains(":")) {
					strSeparateChar = ":";
				}

				String[] arrMBN = content.split(strSeparateChar);
				String strMBN = null;
				if (arrMBN != null && arrMBN.length > 1) {
					strMBN = arrMBN[1].trim();
				}
				if (strMBN == null || strMBN == "") {
					strReturn = ThreadManagerEx.getSystemParameterMessage(
							VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
							VariableStatic.VALIDATE_MABIENNHAN_CODE);
					return strReturn;
				}
				// / goi ham dang lay thong tin
				try {
					strReturn = callProfileService(shortcode, isdn, url, strMBN);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
					strReturn = ThreadManagerEx.getSystemParameterMessage(
							VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
							VariableStatic.DEFAULT_ALERT_CODE);
				}
			}

			return strReturn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	public String callProfileService(String shortcode, String msisdn,
			String url, String mabn) throws Exception {
		try {
			URL urladress = new URL(url);
			BasicHttpServiceStub stub = new BasicHttpServiceStub(urladress,
					null);
			String strNodung = stub.replySmsTinhTrangHoSo(msisdn, shortcode,
					mabn);
			return strNodung;
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}

	public String regSubscriber(String isdn, String content,
			String commandcode, String shortcode, String url) {
		try {
			String strReturn = "";
			if (content == null || content == "") {
				strReturn = ThreadManagerEx.getSystemParameterMessage(
						VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
						VariableStatic.VALIDATE_GROUP_INPUT);
				return strReturn;
			} else {
				String strSeparateChar = " ";
				if (content.contains("_")) {
					strSeparateChar = "_";
				} else if (content.contains(":")) {
					strSeparateChar = ":";
				}

				String[] arrGroupName = content.split(strSeparateChar);
				String strGroupName = null;
				if (arrGroupName != null && arrGroupName.length > 1) {
					strGroupName = arrGroupName[1];
				}
				if (strGroupName == null || strGroupName == "") {
					strReturn = ThreadManagerEx.getSystemParameterMessage(
							VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
							VariableStatic.VALIDATE_GROUP_INPUT);
					return strReturn;
				}
				// // goi dang ky thue bao vao db
				try {
					strReturn = regSubscriberInternal(strGroupName, isdn);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
					strReturn = ThreadManagerEx.getSystemParameterMessage(
							VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
							VariableStatic.REG_SUBSCRIBER_FAIL);
				}
			}

			return strReturn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	public String unRegAll(String isdn, String content, String commandcode,
			String shortcode, String url) {
		try {
			String strReturn = "";
			if (content == null || content == "") {
				strReturn = ThreadManagerEx.getSystemParameterMessage(
						VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
						VariableStatic.VALIDATE_GROUP_INPUT);
				return strReturn;
			}
			try {
				strReturn = unRegSubscriberInternal(null, isdn);

			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				strReturn = ThreadManagerEx.getSystemParameterMessage(
						VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
						VariableStatic.UNREG_SUBSCRIBER_FAIL);
			}

			return strReturn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	public String unRegSubscriber(String isdn, String content,
			String commandcode, String shortcode, String url) {
		try {
			String strReturn = "";
			if (content == null || content == "") {
				strReturn = ThreadManagerEx.getSystemParameterMessage(
						VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
						VariableStatic.VALIDATE_GROUP_INPUT);
				return strReturn;
			} else {
				String strSeparateChar = " ";
				if (content.contains("_")) {
					strSeparateChar = "_";
				} else if (content.contains(":")) {
					strSeparateChar = ":";
				}

				String[] arrGroupName = content.split(strSeparateChar);
				String strGroupName = null;
				if (arrGroupName != null && arrGroupName.length > 1) {
					strGroupName = arrGroupName[1];
				}
				if (strGroupName == null || strGroupName == "") {
					strReturn = ThreadManagerEx.getSystemParameterMessage(
							VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
							VariableStatic.VALIDATE_GROUP_INPUT);
					return strReturn;
				}
				// // goi dang ky thue bao vao db
				try {
					strReturn = unRegSubscriberInternal(strGroupName, isdn);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
					strReturn = ThreadManagerEx.getSystemParameterMessage(
							VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
							VariableStatic.UNREG_SUBSCRIBER_FAIL);
				}
			}

			return strReturn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	private String unRegSubscriberInternal(String strGroupCode, String strMsisdn)
			throws Exception {
		Connection con = null;
		try {
			con = AppManager.getAppConnection();
			SMSSubscriber SMSSubscriberBean = SMSSubscriberFactory
					.getSmsSubscriberBean();
			String groupid = SMSSubscriberBean
					.checkGroupExis(con, strGroupCode);
			if (groupid == null || groupid == "") {
				return ThreadManagerEx.getSystemParameterMessage(
						VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
						VariableStatic.VALIDATE_GROUP_N0T_EXIS);
			}
			String subid = SMSSubscriberBean.checkSubscriberExis(con,
					strMsisdn, groupid);
			if (subid == null && subid == "") {
				return ThreadManagerEx.getSystemParameterMessage(
						VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
						VariableStatic.UNREG_SUBSCRIBER_NO_EXIS);
			}
			SMSSubscriberBean.deleteSubscriber(con, strMsisdn, groupid);
			return ThreadManagerEx.getSystemParameterMessage(
					VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
					VariableStatic.UNREG_SUBSCRIBER_SUCESSFULL);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			Database.closeObject(con);
		}
	}

	public String regSubscriberInternal(String strGroupCode, String strMsisdn)
			throws Exception {
		Connection con = null;
		try {
			con = AppManager.getAppConnection();
			SMSSubscriber SMSSubscriberBean = SMSSubscriberFactory
					.getSmsSubscriberBean();
			String groupid = SMSSubscriberBean
					.checkGroupExis(con, strGroupCode);
			if (groupid == null || groupid == "") {
				return ThreadManagerEx.getSystemParameterMessage(
						VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
						VariableStatic.VALIDATE_GROUP_N0T_EXIS);
			}
			String subid = SMSSubscriberBean.checkSubscriberExis(con,
					strMsisdn, groupid);
			if (subid != null && subid != "") {
				return ThreadManagerEx.getSystemParameterMessage(
						VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
						VariableStatic.VALIDATE_SUBSCRIBER_EXIS);
			}
			SMSSubscriberBean.regSubscriber(con, groupid, strMsisdn);
			return ThreadManagerEx.getSystemParameterMessage(
					VariableStatic.PARAM_SYSTEM_TYPE_RESPONSE,
					VariableStatic.REG_SUBSCRIBER_SUCESSFULL);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			Database.closeObject(con);
		}

	}
}
