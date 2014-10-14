package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.apparam.ApParamTransferer;
import com.fis.esme.apparam.ApParamTransfererService;
import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;

public class ApParamTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://apparam.esme.fis.com/", "ApParamTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.AP_PARAM_PORT_KEY);

	private ApParamTransfererClient() {

	}

	public static ApParamTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		ApParamTransfererService ss = new ApParamTransfererService(url,
				SERVICE_NAME);
		ApParamTransferer port = ss.getApParamTransfererPort();
		return port;
	}

}
