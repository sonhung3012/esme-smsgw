package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.smscrouting.SmscRoutingTransferer;
import com.fis.esme.smscrouting.SmscRoutingTransfererService;

public class SmscRoutingTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://smscrouting.esme.fis.com/", "SmscRoutingTransfererService");
	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SMSC_ROUTING_PORT_KEY);

	private SmscRoutingTransfererClient() {

	}

	public static SmscRoutingTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SmscRoutingTransfererService ss = new SmscRoutingTransfererService(url,
				SERVICE_NAME);
		SmscRoutingTransferer port = ss.getSmscRoutingTransfererPort();
		return port;
	}

	public static void main(String[] args) {
		System.out.println();
	}
}
