package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.language.LanguageTransferer;
import com.fis.esme.language.LanguageTransfererService;

public class LanguageTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://language.esme.fis.com/", "languageTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.LANGUAGE_PORT_KEY);

	private LanguageTransfererClient() {

	}

	public static LanguageTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		LanguageTransfererService ss = new LanguageTransfererService(url,
				SERVICE_NAME);
		LanguageTransferer port = ss.getLanguageTransfererPort();
		return port;
	}

}
