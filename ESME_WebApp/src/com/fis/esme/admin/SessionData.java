package com.fis.esme.admin;

import java.io.Serializable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.fis.esme.MainApplication;
import com.fss.util.CollectionUtil;
import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;

public class SessionData implements TransactionListener, Serializable {
	private boolean loggedIn;
	private Vector vtPermission = new Vector();
	private String userName;
	private String clientAddress;
	private String currentMSISDN = null;

	private Application app;
	private AppClient appClient;
	private static ThreadLocal<SessionData> instance = new ThreadLocal<SessionData>();
	private static ThreadLocal<MainApplication> currentApplication = new ThreadLocal<MainApplication>();

	public SessionData(Application app) {
		this.app = app;
		instance.set(this);
		appClient = new AppClient();
		currentApplication.set((MainApplication) this.app);
	}

	public static Object getInstance() {
		return instance.get();
	}

	@Override
	public void transactionStart(Application application, Object transactionData) {
		if (this.app == application) {
			instance.set(this);
			currentApplication.set((MainApplication) app);
			if (instance.get().clientAddress == null) {
				if (transactionData instanceof HttpServletRequest) {
					HttpServletRequest httpServletRequest = (HttpServletRequest) transactionData;
					instance.get().clientAddress = httpServletRequest
							.getRemoteAddr();
				}
			}
		}
	}

	@Override
	public void transactionEnd(Application application, Object transactionData) {
		if (this.app == application) {
			instance.set(null);
			currentApplication.remove();
		}
	}

	public static MainApplication getCurrentApplication() {
		return currentApplication.get();
	}

	public static String getUserName() {
		return instance.get().userName;
	}

	public static void setUserName(String userName) {
		instance.get().userName = userName;
	}

	public static boolean isLoggedIn() {
		return instance.get().loggedIn;
	}

	public static void setLoggedIn(boolean loggedIn) {
		instance.get().loggedIn = loggedIn;
	}

	public static String getCurrentMSISDN() {
		return instance.get().currentMSISDN;
	}

	public static void setCurrentMSISDN(String currentMSISDN) {
		instance.get().currentMSISDN = currentMSISDN;
	}

	public static String getPermission(String strModuleName) {
		if (instance.get().vtPermission == null
				|| instance.get().vtPermission.size() == 0)
			return "";
		Vector vtValue = CollectionUtil.filterSorted2DVector(
				instance.get().vtPermission, new Object[] { strModuleName },
				new int[] { 0 });
		String strReturn = "";
		for (int i = 0; i < vtValue.size(); i++)
			strReturn += (String) ((Vector) vtValue.elementAt(i)).elementAt(1);
		

		return strReturn;
//		 return "IUDEFS";
	}

	public static void setPermissionInfo(Vector vtValue) {
		if (vtValue == null) {
			vtValue = new Vector();
		}
		CollectionUtil.sort2DVector(vtValue, new int[] { 0, 1 });
		instance.get().vtPermission = vtValue;
	}

	public static void clearPermissionInfo() {
		instance.get().vtPermission = new Vector();
	}

	public static String getClientAddress() {
		return instance.get().clientAddress;
	}

	public static void setClientAddress(String clientAddress) {
		instance.get().clientAddress = clientAddress;
	}

	public static AppClient getAppClient() {
		return instance.get().appClient;
	}

	public static void setAppClient(AppClient appClient) {
		instance.get().appClient = appClient;
	}
}
