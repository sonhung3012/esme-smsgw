package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.emsmo.EmsMoTransferer;
import com.fis.esme.emsmo.EmsMoTransfererService;

public class EmsMoTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://emsmo.esme.fis.com/", "EmsMoTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.EMS_MO_PORT_KEY);

	private EmsMoTransfererClient() {

	}

	public static EmsMoTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		EmsMoTransfererService ss = new EmsMoTransfererService(url,
				SERVICE_NAME);
		EmsMoTransferer port = ss.getEmsMoTransfererPort();
		return port;
	}

	
}
