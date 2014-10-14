package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.scheduleraction.SchedulerActionTransfererService;
import com.fis.esme.scheduleraction.SchedulerActionTransferer;
import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;

public class SchedulerActionTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://scheduleraction.esme.fis.com/", "SchedulerActionTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SCHEDULER_ACTION_PORT_KEY);

	private SchedulerActionTransfererClient() {

	}

	public static SchedulerActionTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SchedulerActionTransfererService ss = new SchedulerActionTransfererService(url,
				SERVICE_NAME);
		SchedulerActionTransferer port = ss.getSchedulerActionTransfererPort();
		return port;
	}

}
