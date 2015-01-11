package com.fis.esme.core.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fis.esme.core.entity.SubscriberObject;
import com.fis.esme.core.exception.LoginException;
import com.fis.esme.core.http.util.CipherUtils;
import com.fis.esme.core.services.HTTPServer;
import com.fss.util.StringUtil;
import com.logica.smpp.Data;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHandler implements HttpHandler {
	private HTTPServer httpServer = null;
	private static int Method_Not_Allowed = 405;
	private static int Forbidden = 403;
	private static int Request_Timeout = 408;
	private static int Unauthorized = 401;
	private static int OK = 200;
	private static int No_Content = 204;
	private static int Not_Acceptable = 406;
	private static int Internal_Server_Error = 500;
	public HTTPServer getHttpServer() {
		return httpServer;
	}
	public void setHttpServer(HTTPServer httpServer) {
		this.httpServer = httpServer;
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		String strRespondMessage = "";
		int responseStatus = OK;
		// Lay RequestMethod

		try {
			String strRequestMethod = StringUtil.nvl(t.getRequestMethod(), "");
			if (strRequestMethod.equalsIgnoreCase("POST")) {
				Map<String, String> mRequest = new HashMap<String, String>();
				Set<Entry<String, List<String>>> hSet = t.getRequestHeaders()
						.entrySet();
				Iterator<Entry<String, List<String>>> iTer = hSet.iterator();
				while (iTer.hasNext()) {
					Entry<String, List<String>> str = iTer.next();
					String strKey = str.getKey();
					String strValue = str.getValue().get(0);
					mRequest.put(strKey, strValue);
				}
				httpServer.logMonitor(mRequest.toString());
				String strCommand = StringUtil.nvl(mRequest.get("Cmd"), "");
				String strAccessKey = StringUtil.nvl(
						mRequest.get("Access_key"), "");
				String strCPName = StringUtil.nvl(mRequest.get("Cp_name"), "");
				String strCPPass = StringUtil.nvl(mRequest.get("Cp_pass"), "");
				String strAddress = t.getRemoteAddress().getAddress()
						.getHostAddress();
				if (strCommand.equalsIgnoreCase("login")) {
					int icpid = checkLogin(strAddress, strCPName, strCPPass);
					if (icpid > 0) {
						System.out.println("Login");
						// Generate AccessKey
						strAccessKey = generateKey(strAddress,
								String.valueOf(icpid), strCPName, strCPPass);
						strRespondMessage = strAccessKey;
					} else {
						responseStatus = Not_Acceptable;
						strRespondMessage = "Login fail, validate username and password !";
						t.setAttribute("desciption",
								"Login fail, validate username and password !");
					}
				} else if (strCommand.equalsIgnoreCase("submit")) {
					String strCpid = validateAccessToken(strAccessKey,
							strAddress, strCPName, strCPPass);
					if (!strCpid.equals("")) {
						System.out.println("Send");
						String strMsisdn = StringUtil.nvl(mRequest.get("Isdn"),
								"");
						strMsisdn = parseCalling(strMsisdn);
						String strMessage = StringUtil.nvl(
								mRequest.get("Message"), "");
						String strShortCode = StringUtil.nvl(
								mRequest.get("Shortcode"), "");
						String strCommandCode = StringUtil.nvl(
								mRequest.get("Commandcode"), "");
						String strRequestID = StringUtil.nvl(
								mRequest.get("Request_id"), "");
						String strRegisterDelivery = StringUtil.nvl(
								mRequest.get("Registerdelivery"), "0");
						String strSubId = "-1";
						String strGroupId = "-1";
						int validate = validateSubmitMsg(strShortCode,
								Integer.valueOf(strCpid), strCommandCode,
								strMessage, strMsisdn);
						SubscriberObject subscriberObject = httpServer.getSubscriberInfo(strMsisdn);
						if(subscriberObject != null)
						{
							strSubId =subscriberObject.getSubscriberID();
							strGroupId=subscriberObject.getSubGroupID();
						}
						if (validate > 0) {
							httpServer.insertSMSLog(strRequestID, strMsisdn,
									strCpid, strMessage,
									String.valueOf(validate), "6", "",strSubId,strGroupId);
							responseStatus = Forbidden;
							throw new Exception("VALIDATE_CODE-" + validate);
						}
						httpServer.insertMT(strRequestID, strMessage, strCpid,
								strShortCode, strMsisdn, strCommandCode,
								strRegisterDelivery,strSubId,strGroupId);
						System.out.println("Enqueued Isdn: " + strMsisdn);
						strRespondMessage = "Send_OK";
					} else {
						responseStatus = Unauthorized;
						strRespondMessage = "Cpid with Accesstoken have changed, pl. contact with admin !";
					}
				} else {
					responseStatus = No_Content;
					strRespondMessage = "Command is not correct";
					System.out.println("Command: " + strCommand
							+ " is not correct");
				}
			} else {
				// Sai Phuong thuc
				responseStatus = Method_Not_Allowed;
				System.out.println("Request Method is not correct: "
						+ strRequestMethod);
				strRespondMessage = "Request method is not correct";
			}
		} catch (LoginException e) {
			// TODO: handle exception
			responseStatus = e.getErrorCode();
			strRespondMessage = e.getErrorMessage();
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			responseStatus = Internal_Server_Error;
			strRespondMessage = e.getMessage();
			e.printStackTrace();
			httpServer.logMonitor(e.getMessage());
		}
		t.sendResponseHeaders(responseStatus, strRespondMessage.length());
		OutputStream os = t.getResponseBody();
		os.write(strRespondMessage.getBytes());
		os.close();
	}

	/**
	 * 
	 * @param strCalling
	 * @return
	 */
	private String parseCalling(String strCalling) {
		if (strCalling.startsWith("95")) {
			strCalling = strCalling.substring(2);
		}
		if (strCalling.startsWith("0")) {
			strCalling = strCalling.substring(1);
		}
		return strCalling;
	}

	private String validateAccessToken(String strAccessToken, String strIp,
			String CP_Name, String strCP_Pass) throws Exception {
		String strKey = CipherUtils.decrypt(strAccessToken);
		String[] arrKey = strKey.split("\\|");
		if (arrKey.length == 5) {
			if (strIp.equals(arrKey[0]) && CP_Name.equals(arrKey[1])
					&& strCP_Pass.equals(arrKey[2])) {
				String strExpiredTIme = arrKey[3].trim();
				String strCpid = arrKey[4].trim();
				long expiredTime = Long.parseLong(strExpiredTIme);
				if (expiredTime >= System.currentTimeMillis()) {
					return strCpid;
				} else {
					throw new Exception("SESSION_IS_EXPIRED");
				}
			} else {
				throw new Exception("ACCESSTOKEN_IS_NOT_CORRECT");
			}
		}
		throw new Exception("ACCESSTOKEN_IS_NOT_CORRECT");
	}

	private String generateKey(String strIp, String cpid, String strCpName,
			String strCPPass) {
		String strKey = strIp + "|" + strCpName + "|" + strCPPass + "|"
				+ (System.currentTimeMillis() + (1 * 24 * 60 * 60 * 1000))
				+ "|" + cpid;
		return CipherUtils.encrypt(strKey);
	}

	/**
	 * 
	 * @param shortcode
	 * @param cpId
	 * @param commandcode
	 * @param content
	 * @param isdn
	 * @return
	 */
	private int validateSubmitMsg(String shortcode, int cpId,
			String commandcode, String content, String isdn) {
		if (content == null || content.trim().equalsIgnoreCase("")) {
			return Data.ESME_RINVMSGLEN;
		}
		if (isdn == null || isdn.trim().equalsIgnoreCase("")) {
			return Data.ESME_RINVADR;
		}
		if (shortcode == null || shortcode.trim().equalsIgnoreCase("")) {
			return Data.ESME_RINVPARAM;
		}
		if (commandcode == null || commandcode.trim().equalsIgnoreCase("")) {
			return Data.ESME_RINVCMDID;
		}
		if (!httpServer.validateCPInfo(shortcode, commandcode, cpId)) {
			return Data.ESME_RINVPERMSG;
		}
		return 0;
	}

	private int checkLogin(String strIp, String strCpUser, String strCpPass)
			throws Exception {
		return httpServer.checkIdentity(strIp, strCpUser, strCpPass);
	}
}
