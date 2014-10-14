package com.fis.esme.sso;

import com.fss.util.StringUtil;

public class Authenticate {

	private TelnetWrapper telnet = null;
	private String host = "10.252.7.5";
	private int port = 314;
	private int timeout = 10000;
	private static String mstrPrompt = "Ready";
	private static String mstrSeparator = "#";
	public static String SSO_SUCCESSFULL = "0";

	public SSOObject sendChecking(String strCheckCommand) throws Exception {
		try {
			telnet = new TelnetWrapper(host, port, timeout);
			telnet.receiveUntil(mstrPrompt);
			telnet.send(strCheckCommand);
			String strResponse = new String(telnet.receiveUntil(mstrPrompt));
			strResponse = strResponse.substring(0, strResponse.length()
					- mstrPrompt.length());
			strResponse = StringUtil.replaceAll(strResponse, "\r", "");
			strResponse = StringUtil.replaceAll(strResponse, "\n", "");
			String[] arrResponse = strResponse.split(mstrSeparator);
			if (arrResponse != null && arrResponse.length >= 2) {
				String status = arrResponse[0];
				String description = arrResponse[1];
				SSOObject sso = new SSOObject();
				sso.status = status;
				sso.description = description;
				return sso;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
		return null;
	}

	public static void main(String[] agm) throws Exception {
		String strCheckCommand = "islogin#84909090909#sessionid#111231231293812904823094 \n";
		Authenticate s = new Authenticate();
		SSOObject sso = s.sendChecking(strCheckCommand);
		System.out.println("status : " + sso.status);
		System.out.println("des : " + sso.description);
		if (sso.status.equalsIgnoreCase(SSO_SUCCESSFULL)) {
			System.out.println("Dang nhap thanh cong ! ");
		}
	}
}
