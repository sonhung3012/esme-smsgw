package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.schedulerdetail.SchedulerDetailTransfererService;
import com.fis.esme.schedulerdetail.SchedulerDetailTransferer;
import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;

public class SchedulerDetailTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://schedulerdetail.esme.fis.com/", "SchedulerDetailTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SCHEDULER_DETAIL_PORT_KEY);

	private SchedulerDetailTransfererClient() {

	}

	public static SchedulerDetailTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SchedulerDetailTransfererService ss = new SchedulerDetailTransfererService(url,
				SERVICE_NAME);
		SchedulerDetailTransferer port = ss.getSchedulerDetailTransfererPort();
		return port;
	}

}
