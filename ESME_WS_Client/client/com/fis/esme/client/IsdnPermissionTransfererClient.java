package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.isdnpermission.IsdnPermissionTransferer;
import com.fis.esme.isdnpermission.IsdnPermissionTransfererService;

public class IsdnPermissionTransfererClient {
	private static final QName SERVICE_NAME = new QName("http://isdnpermission.esme.fis.com/", "IsdnPermissionTransfererService");
	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.ISDN_PERMISSION_PORT_KEY);

	private IsdnPermissionTransfererClient() {

	}

	public static IsdnPermissionTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		IsdnPermissionTransfererService ss = new IsdnPermissionTransfererService(url,
				SERVICE_NAME);
		IsdnPermissionTransferer port = ss.getIsdnPermissionTransfererPort();
		return port;
	}

}
