package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.smscparam.SmscParamTransferer;
import com.fis.esme.smscparam.SmscParamTransfererService;


public class SmscParamTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://smscparam.esme.fis.com/", "SmscParamTransfererService");
	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SMSC_PARAM_PORT_KEY);

	private SmscParamTransfererClient() {

	}

	public static SmscParamTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SmscParamTransfererService ss = new SmscParamTransfererService(url,
				SERVICE_NAME);
		SmscParamTransferer port = ss.getSmscParamTransfererPort();
		return port;
	}

	public static void main(String[] args) {
		System.out.println();
	}
}
