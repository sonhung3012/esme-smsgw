package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.cpsummary.CpSummaryTransferer;
import com.fis.esme.cpsummary.CpSummaryTransfererService;

public class CpSummaryTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://cpsummary.esme.fis.com/", "CpSummaryTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.CP_SUMMARY_PORT_KEY);

	private CpSummaryTransfererClient() {

	}

	public static CpSummaryTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		CpSummaryTransfererService ss = new CpSummaryTransfererService(url,
				SERVICE_NAME);
		CpSummaryTransferer port = ss.getCpSummaryTransfererPort();
		return port;
	}

}
