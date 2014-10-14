package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.message.MessageTransferer;
import com.fis.esme.message.MessageTransfererService;

public class MessageTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://message.esme.fis.com/", "MessageTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.MESSAGE_PORT_KEY);

	private MessageTransfererClient() {

	}

	public static MessageTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		MessageTransfererService ss = new MessageTransfererService(url,
				SERVICE_NAME);
		MessageTransferer port = ss.getMessageTransfererPort();
		return port;
	}

}
