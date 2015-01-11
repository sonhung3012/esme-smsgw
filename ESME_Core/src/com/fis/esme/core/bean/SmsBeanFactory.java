package com.fis.esme.core.bean;

import com.fis.esme.core.app.ThreadManagerEx;

public class SmsBeanFactory {
	public static SmsBean getSmsBeanFactory(String factoryMode) {
		if (factoryMode.equalsIgnoreCase("SQLSERVER")) {
			return new SmsBeanSQLServer();
		} else {
			return new SmsBeanOracle();
		}
	}

	public static SmsBean getSmsBeanFactoryEx() {
		return new SmsBeanOracleEx();
	}

	public static SmsBean getSmsBeanFactory() {
		String factoryMode = ThreadManagerEx.getDatabaseMode();
		if (factoryMode.equalsIgnoreCase("SQLSERVER")) {
			return new SmsBeanSQLServer();
		} else {
			return new SmsBeanOracle();
		}
	}
}
