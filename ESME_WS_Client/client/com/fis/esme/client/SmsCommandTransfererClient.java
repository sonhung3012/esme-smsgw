package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.smscommand.SmsCommandTransferer;
import com.fis.esme.smscommand.SmsCommandTransfererService;

public class SmsCommandTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://smscommand.esme.fis.com/", "SmsCommandTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SMS_COMMAND_PORT_KEY);

	private SmsCommandTransfererClient() {

	}

	public static SmsCommandTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SmsCommandTransfererService ss = new SmsCommandTransfererService(url,
				SERVICE_NAME);
		SmsCommandTransferer port = ss.getSmsCommandTransfererPort();
		return port;
	}

}
