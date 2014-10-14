package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.servicesummary.ServiceSummaryTransferer;
import com.fis.esme.servicesummary.ServiceSummaryTransfererService;

public class ServiceSummaryTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://servicesummary.esme.fis.com/",
			"ServiceSummaryTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SERVICE_SUMMARY_PORT_KEY);

	private ServiceSummaryTransfererClient() {

	}

	public static ServiceSummaryTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		ServiceSummaryTransfererService ss = new ServiceSummaryTransfererService(
				url, SERVICE_NAME);
		ServiceSummaryTransferer port = ss.getServiceSummaryTransfererPort();
		return port;
	}

}
