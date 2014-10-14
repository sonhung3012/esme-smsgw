package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.subgroups.SubGroupsTransferer;
import com.fis.esme.subgroups.SubGroupsTransfererService;

public class SubGroupsTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://subgroups.esme.fis.com/", "SubGroupsTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SUBGROUP_PORT_KEY);

	private SubGroupsTransfererClient() {

	}

	public static SubGroupsTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SubGroupsTransfererService ss = new SubGroupsTransfererService(url,
				SERVICE_NAME);
		SubGroupsTransferer port = ss.getSubGroupsTransfererPort();
		return port;
	}

}
