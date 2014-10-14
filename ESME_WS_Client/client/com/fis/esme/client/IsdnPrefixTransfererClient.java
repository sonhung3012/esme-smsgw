package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.isdnprefix.IsdnPrefixTransferer;
import com.fis.esme.isdnprefix.IsdnPrefixTransfererService;

public class IsdnPrefixTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://isdnprefix.esme.fis.com/", "IsdnPrefixTransfererService");
	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.ISDN_PREFIX_PORT_KEY);

	private IsdnPrefixTransfererClient() {

	}

	public static IsdnPrefixTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		IsdnPrefixTransfererService ss = new IsdnPrefixTransfererService(url,
				SERVICE_NAME);
		IsdnPrefixTransferer port = ss.getIsdnPrefixTransfererPort();
		return port;
	}

	public static void main(String[] args) {
		System.out.println();
	}
}
