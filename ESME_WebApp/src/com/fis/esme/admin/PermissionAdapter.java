package com.fis.esme.admin;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

import com.fss.ddtp.DDTP;

public class PermissionAdapter implements Serializable {

	private DDTP request;
	private DDTP response;

	public PermissionAdapter() {
		request = new DDTP();
	}

	public Vector queryMenuData(int rootid) throws Exception {
		request = new DDTP();
		response = SessionData.getAppClient().sendRequest("PermissionBean",
				"queryMenuData", request);
		return (Vector) response.getReturn();
	}
}
