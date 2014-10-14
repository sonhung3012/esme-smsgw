package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.subscriber.SubscriberTransfererService;
import com.fis.esme.subscriber.SubscriberTransferer;
import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;

public class SubscriberTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://subscriber.esme.fis.com/", "SubscriberTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SUBSCRIBER_PORT_KEY);

	private SubscriberTransfererClient() {

	}

	public static SubscriberTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SubscriberTransfererService ss = new SubscriberTransfererService(url,
				SERVICE_NAME);
		SubscriberTransferer port = ss.getSubscriberTransfererPort();
		return port;
	}

}
