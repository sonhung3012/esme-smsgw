package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.cp.CpTransferer;
import com.fis.esme.cp.CpTransfererService;

public class CpTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://cp.esme.fis.com/", "CpTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.CP_PORT_KEY);

	private CpTransfererClient() {

	}

	public static CpTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		CpTransfererService ss = new CpTransfererService(url,
				SERVICE_NAME);
		CpTransferer port = ss.getCpTransfererPort();
		return port;
	}

}
