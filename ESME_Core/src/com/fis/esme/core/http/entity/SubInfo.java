package com.fis.esme.core.http.entity;

import java.util.ArrayList;

import com.fss.util.AppException;

public class SubInfo {
	public String strCommandCode;
	public String strShortCode;
	public String strMsisdn;
	public String strMessage;
	public String strUrl;
	public ArrayList<SubInfo> lstSub = new ArrayList<SubInfo>();

	public SubInfo() {

	}

	public String getCommandCode() {
		return strCommandCode;
	}

	public void setCommandCode(String strCommandCode) {
		this.strCommandCode = strCommandCode;
	}

	public void analyzing() throws Exception {
		String[] arrIsdn = strMsisdn.split("\\|");
		String[] arrMessage = strMessage.split("\\|");

		if (arrIsdn.length != arrMessage.length) {
			throw new AppException("Input is not correct!");
		}
		for (int i = 0; i < arrIsdn.length; i++) {
			SubInfo objSub = new SubInfo();
			objSub.strMsisdn = arrIsdn[i];
			objSub.strMessage = arrMessage[i];

			lstSub.add(objSub);
		}
	}
}
