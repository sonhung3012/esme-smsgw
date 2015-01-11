package com.fis.esme.core.bean.subscriber;

import com.fis.esme.core.app.ThreadManagerEx;
import com.fis.esme.core.bean.megloader.SMSLoaderBean;
import com.fis.esme.core.bean.megloader.SMSLoaderBeanOracle;
import com.fis.esme.core.bean.megloader.SMSLoaderBeanSQLServer;

public class SMSSubscriberFactory {
	public static SMSSubscriber getSmsSubscriberBean(String factoryMode) {
		if (factoryMode.equalsIgnoreCase("SQLSERVER")) {
			return new SMSSubscriberSQLServer();
		} else {
			return new SMSSubscriberOracle();
		}
	}

	public static SMSSubscriber getSmsSubscriberBean() {
		String factoryMode = ThreadManagerEx.getDatabaseMode();
		if (factoryMode.equalsIgnoreCase("SQLSERVER")) {
			return new SMSSubscriberSQLServer();
		} else {
			return new SMSSubscriberOracle();
		}
	}
}
