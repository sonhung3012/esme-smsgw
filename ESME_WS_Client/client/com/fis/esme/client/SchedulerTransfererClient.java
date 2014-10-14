package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.scheduler.SchedulerTransfererService;
import com.fis.esme.scheduler.SchedulerTransferer;
import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;

public class SchedulerTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://scheduler.esme.fis.com/", "SchedulerTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SCHEDULER_PORT_KEY);

	private SchedulerTransfererClient() {

	}

	public static SchedulerTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SchedulerTransfererService ss = new SchedulerTransfererService(url,
				SERVICE_NAME);
		SchedulerTransferer port = ss.getSchedulerTransfererPort();
		return port;
	}

}
