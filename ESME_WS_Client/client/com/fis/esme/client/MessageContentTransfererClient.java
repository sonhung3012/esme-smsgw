package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.messagecontent.MessageContentTransferer;
import com.fis.esme.messagecontent.MessageContentTransfererService;

public class MessageContentTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://messagecontent.esme.fis.com/",
			"MessageContentTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.MESSAGE_CONTENT_PORT_KEY);

	private MessageContentTransfererClient() {

	}

	public static MessageContentTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		MessageContentTransfererService ss = new MessageContentTransfererService(url,
				SERVICE_NAME);
		MessageContentTransferer port = ss.getMessageContentTransfererPort();
		return port;
	}

}
