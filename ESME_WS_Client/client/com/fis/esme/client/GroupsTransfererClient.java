package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.groups.GroupsTransfererService;
import com.fis.esme.groups.GroupsTransferer;
import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;

public class GroupsTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://groups.esme.fis.com/", "GroupsTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.GROUPS_PORT_KEY);

	private GroupsTransfererClient() {

	}

	public static GroupsTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		GroupsTransfererService ss = new GroupsTransfererService(url,
				SERVICE_NAME);
		GroupsTransferer port = ss.getGroupsTransfererPort();
		return port;
	}

}
