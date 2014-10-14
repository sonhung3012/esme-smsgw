package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.isdnspecial.IsdnSpecialTransferer;
import com.fis.esme.isdnspecial.IsdnSpecialTransfererService;

public class IsdnSpecialTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://isdnspecial.esme.fis.com/", "IsdnSpecialTransfererService");
	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.ISDN_SPECIAL_PORT_KEY);

	private IsdnSpecialTransfererClient() {

	}

	public static IsdnSpecialTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		IsdnSpecialTransfererService ss = new IsdnSpecialTransfererService(url,
				SERVICE_NAME);
		IsdnSpecialTransferer port = ss.getIsdnSpecialTransfererPort();
		return port;
	}

}
