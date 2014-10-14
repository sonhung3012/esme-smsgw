package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.smsrouting.SmsRoutingTransferer;
import com.fis.esme.smsrouting.SmsRoutingTransfererService;

public class SmsRoutingTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://smsrouting.esme.fis.com/", "SmsRoutingTransfererService");
	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SMS_ROUTING_PORT_KEY);

	private SmsRoutingTransfererClient() {

	}

	public static SmsRoutingTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SmsRoutingTransfererService ss = new SmsRoutingTransfererService(url,
				SERVICE_NAME);
		SmsRoutingTransferer port = ss.getSmsRoutingTransfererPort();
		return port;
	}

}
