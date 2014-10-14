package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.emsmt.EmsMtTransferer;
import com.fis.esme.emsmt.EmsMtTransfererService;

public class EmsMtTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://emsmt.esme.fis.com/", "EmsMtTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.EMS_MT_PORT_KEY);

	private EmsMtTransfererClient() {

	}

	public static EmsMtTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		EmsMtTransfererService ss = new EmsMtTransfererService(url,
				SERVICE_NAME);
		EmsMtTransferer port = ss.getEmsMtTransfererPort();
		return port;
	}

	
}
