package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.smsc.SmscTransferer;
import com.fis.esme.smsc.SmscTransfererService;

public class SmscTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://smsc.esme.fis.com/", "SmscTransfererService");
	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SMSC_PORT_KEY);

	private SmscTransfererClient() {

	}

	public static SmscTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SmscTransfererService ss = new SmscTransfererService(url, SERVICE_NAME);
		SmscTransferer port = ss.getSmscTransfererPort();
		return port;
	}

	public static void main(String[] args) {
		System.out.println();
	}
}
