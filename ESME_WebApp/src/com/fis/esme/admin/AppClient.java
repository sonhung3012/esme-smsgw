package com.fis.esme.admin;

import java.io.Serializable;
import java.util.Properties;
import java.util.Vector;

import com.fis.esme.MainApplication;
import com.fss.ddtp.DDTP;
import com.fss.ddtp.ExpireListener;
import com.fss.ddtp.ServletTransmitter;
import com.fss.util.CollectionUtil;
import com.fss.util.Global;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: FSS-FPT
 * </p>
 * 
 * @author Nguyen Trong Dang
 * @version 1.0
 */

public class AppClient implements Serializable {
	// private static MainApplication INSTANCE;

	private static Properties mprtConfig = new Properties();
	private ServletTransmitter transmitter;
	public Vector mvtPolicy = null;
	private Vector vtSVAddress = new Vector();
	public Vector vtApdomain = null;

	static {
		try {
			Properties prt = Global
					.loadProperties("com/fis/esme/admin/ClientConfig.properties");
			setSessionValue("Server.Host", prt.getProperty("Server.Host"));
			setSessionValue("Server.Class", prt.getProperty("Server.Class"));
			setSessionValue("Encrypt.Algorithm",
					prt.getProperty("Encrypt.Algorithm"));
//			setSessionValue("Link.Report", prt.getProperty("Link.Report"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset(MainApplication app) {
		// Reset transmitter
		transmitter = new ServletTransmitter();
		transmitter.setPackage("com.fis.admin.bean.");
		transmitter.setServletHost(getSessionValue("Server.Host"));
		transmitter.setServerClass(getSessionValue("Server.Class"));

		// INSTANCE = app;

		if (app instanceof ExpireListener)
			transmitter.setExpireListener((ExpireListener) app);
	}

	// //////////////////////////////////////////////////////
	/**
	 * Return client parameter
	 * 
	 * @param strParam
	 *            parameter name
	 * @return parameter value
	 */
	// //////////////////////////////////////////////////////
	public static String getSessionValue(String strParam) {
		return mprtConfig.getProperty(strParam, "");
	}

	// //////////////////////////////////////////////////////
	/**
	 * Set client parameter
	 * 
	 * @param strParam
	 *            parameter name
	 * @param strValue
	 *            parameter value
	 * @author edit by hungdv
	 */
	// //////////////////////////////////////////////////////
	public static void setSessionValue(String strParam, String strValue) {
		mprtConfig.setProperty(strParam, strValue);
	}

	public DDTP sendRequest(String strClass, String strFunctionName,
			DDTP request) throws Exception {
		return transmitter.sendRequest(strClass, strFunctionName, request);
	}

	public String getSessionKey() {
		return transmitter.getSessionKey();
	}

	// //////////////////////////////////////////////////////
	/**
	 * @author Ly Tuan Anh
	 * @param strName
	 *            String
	 * @return String
	 * @throws Exception
	 */
	// //////////////////////////////////////////////////////
	public String getPolicy(String strName) {
		try {
			DDTP request = new DDTP();
			DDTP response = sendRequest("CommonBean", "getPolicy", request);
			Vector vtPolicy = (Vector) response.getReturn();
			Vector vtValue = new Vector();
			vtValue.addElement(strName);
			int iIndex = CollectionUtil.binarySearch2DVector(vtPolicy, vtValue,
					new int[] { 0 });
			if (iIndex >= 0) {
				vtValue = (Vector) vtPolicy.elementAt(iIndex);
				return vtValue.elementAt(1).toString();
			}
			return "";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strSessionKey
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setSessionKey(String strSessionKey) {
		transmitter.setSessionKey(strSessionKey);
	}

	public void setSessionApdomain(Vector pvtApdomain) {
		vtApdomain = pvtApdomain;
	}

	// code,name, type, status, value, reason_type,group_code
	public Vector getSessionApdomain() {
		return vtApdomain;
	}

	public Vector getSessionApdomain(String p_Type) {
		Vector vctReturn = new Vector();
		Vector vctRow = new Vector();
		for (int i = 0; i < vtApdomain.size(); i++) {
			if (((Vector) vtApdomain.get(i)).get(2).toString().equals(p_Type)) {
				vctRow = new Vector();
				vctRow.add(((Vector) vtApdomain.get(i)).get(0).toString()); // code
				vctRow.add(((Vector) vtApdomain.get(i)).get(1).toString()); // name
				vctRow.add(((Vector) vtApdomain.get(i)).get(4).toString()); // value
				vctReturn.add(vctRow);
			}
		}
		return vctReturn;
	}

	public Vector getSessionApdomain(String p_Type, String p_Code[]) {
		Vector vctReturn = new Vector();
		Vector vctRow = new Vector();
		Vector vtCode = new Vector();
		for (int i = 0; i < p_Code.length; i++) {
			vtCode.add(p_Code[i]);
		}

		for (int i = 0; i < vtApdomain.size(); i++) {
			if (((Vector) vtApdomain.get(i)).get(2).toString().equals(p_Type)
					&& vtCode.indexOf(((Vector) vtApdomain.get(i)).get(0)
							.toString()) >= 0) {
				vctRow = new Vector();
				vctRow.add(((Vector) vtApdomain.get(i)).get(0).toString());
				vctRow.add(((Vector) vtApdomain.get(i)).get(1).toString());
				vctRow.add(((Vector) vtApdomain.get(i)).get(4).toString());
				vctReturn.add(vctRow);
			}
		}
		return vctReturn;
	}

	public Vector getSessionApdomain(String p_Type, String p_Code) {
		Vector vctReturn = new Vector();
		Vector vctRow = new Vector();
		for (int i = 0; i < vtApdomain.size(); i++)

		{
			if (((Vector) vtApdomain.get(i)).get(2).toString().equals(p_Type)
					&& ((Vector) vtApdomain.get(i)).get(0).toString()
							.equals(p_Code)) {
				vctRow = new Vector();
				vctRow.add(((Vector) vtApdomain.get(i)).get(0).toString());
				vctRow.add(((Vector) vtApdomain.get(i)).get(1).toString());
				vctRow.add(((Vector) vtApdomain.get(i)).get(4).toString());
				vctReturn.add(vctRow);
			}
		}
		return vctReturn;
	}

	public void setVtSVAddress(Vector p_vtSVAddress) {
		vtSVAddress = p_vtSVAddress;
	}

	public Vector getVtSVAddress() {
		return vtSVAddress;
	}

	public static String padLeft(String s, char t, int length) {
		int stringlength = length - s.length();
		String temple = "";
		for (int i = 0; i < stringlength; i++) {
			temple = t + temple;
		}
		return temple + s;
	}

	public static String getPermission(String moduleName) {
		return SessionData.getPermission(moduleName);
	}

	// public static void setMainApplication(MainApplication app)
	// {
	// INSTANCE = app;
	// }

	public static String getIpRemoteAddr() {
		return SessionData.getClientAddress();
	}

	// public static MainApplication getApplication()
	// {
	// return INSTANCE;
	// }

}

final class AppCacheKey {
	public Object objKey;

	public AppCacheKey(Object key) {
		objKey = key;
	}

	public int hashCode() {
		return objKey.hashCode();
	}

	public boolean equals(Object obj) {
		return (obj instanceof AppCacheKey)
				&& (objKey.equals(((AppCacheKey) obj).objKey));
	}

}
