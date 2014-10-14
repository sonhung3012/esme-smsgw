package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.shortcode.ShortCodeTransferer;
import com.fis.esme.shortcode.ShortCodeTransfererService;

public class ShortCodeTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://shortcode.esme.fis.com/", "ShortCodeTransfererService");
	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SHORT_CODE_PORT_KEY);

	private ShortCodeTransfererClient() {

	}

	public static ShortCodeTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		ShortCodeTransfererService ss = new ShortCodeTransfererService(url,
				SERVICE_NAME);
		ShortCodeTransferer port = ss.getShortCodeTransfererPort();
		return port;
	}

}
