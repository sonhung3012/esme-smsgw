package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.smslog.EsmeSmsLogTransferer;
import com.fis.esme.smslog.EsmeSmsLogTransfererService;

public class SmsLogTransfererClient {
	private static final QName SERVICE_NAME = new QName(
			"http://smslog.esme.fis.com/", "EsmeSmsLogTransfererService");
	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.SMS_LOG_PORT_KEY);

	private SmsLogTransfererClient() {

	}

	public static EsmeSmsLogTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		EsmeSmsLogTransfererService ss = new EsmeSmsLogTransfererService(url,
				SERVICE_NAME);
		EsmeSmsLogTransferer port = ss.getEsmeSmsLogTransfererPort();
		
		org.apache.cxf.endpoint.Client client = ClientProxy.getClient(port);

        HTTPConduit httpConduit = (HTTPConduit)client.getConduit();

        HTTPClientPolicy policy = httpConduit.getClient();

        policy.setReceiveTimeout(0);
        
		return port;
	}

	public static void main(String[] args) {
		System.out.println();
	}
}
