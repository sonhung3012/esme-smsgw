package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.service.ServiceTransferer;
import com.fis.esme.service.ServiceTransfererService;

public class ServiceTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://service.esme.fis.com/", "ServiceTransfererService");
	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SERVICE_PORT_KEY);

	private ServiceTransfererClient() {

	}

	public static ServiceTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		ServiceTransfererService ss = new ServiceTransfererService(url,
				SERVICE_NAME);
		ServiceTransferer port = ss.getServiceTransfererPort();
		return port;
	}

}
