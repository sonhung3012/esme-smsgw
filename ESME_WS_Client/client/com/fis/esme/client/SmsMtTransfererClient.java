package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.smsmt.SmsMtTransferer;
import com.fis.esme.smsmt.SmsMtTransfererService;

public class SmsMtTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://smsmt.esme.fis.com/", "SmsMtTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SMS_MT_PORT_KEY);

	private SmsMtTransfererClient() {

	}

	public static SmsMtTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		SmsMtTransfererService ss = new SmsMtTransfererService(url,
				SERVICE_NAME);
		SmsMtTransferer port = ss.getSmsMtTransfererPort();
		
        org.apache.cxf.endpoint.Client client = ClientProxy.getClient(port);

        HTTPConduit httpConduit = (HTTPConduit)client.getConduit();

        HTTPClientPolicy policy = httpConduit.getClient();

        policy.setReceiveTimeout(0);
		
		return port;
	}

}
