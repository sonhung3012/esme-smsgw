package com.fis.esme.core.bean.megloader;

import com.fis.esme.core.app.ThreadManagerEx;

public class SMSLoaderBeanFactory {
	public static SMSLoaderBean getSmsBeanFactory(String factoryMode) {
		if (factoryMode.equalsIgnoreCase("SQLSERVER")) {
			return new SMSLoaderBeanSQLServer();
		} else {
			return new SMSLoaderBeanOracle();
		}
	}

	public static SMSLoaderBean getSmsBeanFactory() {
		String factoryMode = ThreadManagerEx.getDatabaseMode();
		if (factoryMode.equalsIgnoreCase("SQLSERVER")) {
			return new SMSLoaderBeanSQLServer();
		} else {
			return new SMSLoaderBeanOracle();
		}
	}
}
