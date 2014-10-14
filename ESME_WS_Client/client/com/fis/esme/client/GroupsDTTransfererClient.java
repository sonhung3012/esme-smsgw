package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.groupsdt.GroupsDTTransferer;
import com.fis.esme.groupsdt.GroupsDTTransfererService;

public class GroupsDTTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://groupsdt.esme.fis.com/", "GroupsDTTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.GROUPSDT_PORT_KEY);

	private GroupsDTTransfererClient() {

	}

	public static GroupsDTTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		GroupsDTTransfererService ss = new GroupsDTTransfererService(url,
				SERVICE_NAME);
		GroupsDTTransferer port = ss.getGroupsDTTransfererPort();
		return port;
	}

}
