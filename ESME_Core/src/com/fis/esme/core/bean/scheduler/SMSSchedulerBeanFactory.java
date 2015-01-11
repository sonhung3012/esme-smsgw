package com.fis.esme.core.bean.scheduler;

import com.fis.esme.core.app.ThreadManagerEx;
import com.fis.esme.core.bean.megloader.SMSLoaderBean;
import com.fis.esme.core.bean.megloader.SMSLoaderBeanOracle;
import com.fis.esme.core.bean.megloader.SMSLoaderBeanSQLServer;

public class SMSSchedulerBeanFactory {
	public static SMSSchedulerBean getSmsBeanFactory(String factoryMode) {
		if (factoryMode.equalsIgnoreCase("SQLSERVER")) {
			return new SMSSchedulerBeanSQLServer();
		} else {
			return new SMSSchedulerBeanOracle();
		}
	}

	public static SMSSchedulerBean getSmsBeanFactory() {
		String factoryMode = ThreadManagerEx.getDatabaseMode();
		if (factoryMode.equalsIgnoreCase("SQLSERVER")) {
			return new SMSSchedulerBeanSQLServer();
		} else {
			return new SMSSchedulerBeanOracle();
		}
	}
}
