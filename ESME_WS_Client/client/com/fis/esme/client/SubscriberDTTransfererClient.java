package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.subscriberdt.SubscriberDTTransferer;
import com.fis.esme.subscriberdt.SubscriberDTTransfererService;

public class SubscriberDTTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://subscriberdt.esme.fis.com/",
			"SubscriberDTTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SUBSCRIBERDT_PORT_KEY);

	private SubscriberDTTransfererClient() {

	}

	public static SubscriberDTTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SubscriberDTTransfererService ss = new SubscriberDTTransfererService(
				url, SERVICE_NAME);
		SubscriberDTTransferer port = ss.getSubscriberDTTransfererPort();
		return port;
	}

}
