package com.fis.esme.core.entity;

import java.io.Serializable;
import java.util.Vector;

import com.fss.util.StringUtil;

public class CPObject implements Serializable {
	
	private String mstrCPID;
	private String mstrReceiveURLMsg;
	private String mstrReceiveUsername;
	private String mstrReceivePassword;
	
	public CPObject(Vector vtData) {
		mstrCPID = vtData.get(0).toString();
		mstrReceiveURLMsg = StringUtil.nvl(vtData.get(1), "");
		mstrReceiveUsername = StringUtil.nvl(vtData.get(2), "");
		mstrReceivePassword = StringUtil.nvl(vtData.get(3), "");
	}

	public String getCPID() {
		return mstrCPID;
	}

	public void setCPID(String strCPID) {
		mstrCPID = strCPID;
	}

	public String getReceiveURLMsg() {
		return mstrReceiveURLMsg;
	}

	public void setReceiveURLMsg(String strReceiveURLMsg) {
		mstrReceiveURLMsg = strReceiveURLMsg;
	}

	public String getReceiveUsername() {
		return mstrReceiveUsername;
	}

	public void setReceiveUsername(String strReceiveUsername) {
		mstrReceiveUsername = strReceiveUsername;
	}

	public String getReceivePassword() {
		return mstrReceivePassword;
	}

	public void setReceivePassword(String strReceivePassword) {
		mstrReceivePassword = strReceivePassword;
	}
}