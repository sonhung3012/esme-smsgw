package com.fis.esme.util;

import java.util.Vector;

import com.fis.esme.admin.SessionData;
import com.fss.ddtp.DDTP;

public class LogUtil {
	public static void logAccess(String moduleName) {
		try {
			DDTP request = new DDTP();
			request.setString("strModule", moduleName);
			request.setString("ipAddress", SessionData.getAppClient()
					.getIpRemoteAddr());
			SessionData.getAppClient().sendRequest("AccessLogViewerBean",
					"logModuleAccess", request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void logActionInsert(String moduleName, String tableName,
			String primaryKeyName, String primaryKeyValue,
			Vector<Vector<String>> vObjectRelated) {
		try {
			DDTP request = new DDTP();
			request.setObject("ipAddress", SessionData.getAppClient()
					.getIpRemoteAddr());
			request.setString("primaryKeyValue", primaryKeyValue);
			request.setString("primaryKeyName", primaryKeyName);
			request.setString("tableName", tableName);
			request.setString("moduleName", moduleName);
			request.setObject("vObjectRelated", vObjectRelated);
			SessionData.getAppClient().sendRequest("CommonActionLog",
					"logInsert", request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void logActionDelete(String moduleName, String tableName,
			String primaryKeyName, String primaryKeyValue,
			Vector<Vector<String>> vObjectRelated) {
		 try
		 {
		 DDTP request = new DDTP();
		 request.setObject("ipAddress",
		 SessionData.getAppClient().getIpRemoteAddr());
		 request.setString("primaryKeyValue", primaryKeyValue);
		 request.setString("primaryKeyName", primaryKeyName);
		 request.setString("tableName", tableName);
		 request.setString("moduleName", moduleName);
		 request.setObject("vObjectRelated", vObjectRelated);
		 SessionData.getAppClient().sendRequest("CommonActionLog",
		 "logDelete", request);
		 }
		 catch (Exception e)
		 {
		 e.printStackTrace();
		 }
	}


	public static Vector logActionBeforeUpdate(String moduleName,
			String tableName, String primaryKeyName, String primaryKeyValue,
			Vector<Vector<String>> vObjectRelated) {
		 try
		 {
		 DDTP request = new DDTP();
		 request.setObject("ipAddress",
		 SessionData.getAppClient().getIpRemoteAddr());
		 request.setString("primaryKeyValue", primaryKeyValue);
		 request.setString("primaryKeyName", primaryKeyName);
		 request.setString("tableName", tableName);
		 request.setString("moduleName", moduleName);
		 request.setObject("vObjectRelated", vObjectRelated);
		 DDTP response =
		 SessionData.getAppClient().sendRequest("CommonActionLog",
		 "logBeforeUpdate", request);
		 return (Vector) response.getReturn();
		 }
		 catch (Exception e)
		 {
		 e.printStackTrace();
		return null;
		 }
	}

	
	public static void logActionAfterUpdate(Vector v) {
		try {
			DDTP request = new DDTP();
			request.setObject("ipAddress", SessionData.getAppClient()
					.getIpRemoteAddr());
			request.setVector("vObjectRelated", v);
			SessionData.getAppClient().sendRequest("CommonActionLog",
					"logAfterUpdate", request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Vector<String> makeVector(String[] strArr) {

		if (strArr.length > 0) {
			Vector<String> vChild = new Vector<String>();
			for (int i = 0; i < strArr.length; i++) {
				vChild.add(strArr[i]);
			}
			return vChild;
		}
		return null;
	}
}
