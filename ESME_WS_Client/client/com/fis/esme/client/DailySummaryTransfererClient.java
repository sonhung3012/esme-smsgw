package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.dailysummary.DailySummaryTransferer;
import com.fis.esme.dailysummary.DailySummaryTransfererService;

public class DailySummaryTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://dailysummary.esme.fis.com/",
			"DailySummaryTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.DAILY_SUMMARY_PORT_KEY);

	private DailySummaryTransfererClient() {

	}

	public static DailySummaryTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		DailySummaryTransfererService ss = new DailySummaryTransfererService(
				url, SERVICE_NAME);
		DailySummaryTransferer port = ss.getDailySummaryTransfererPort();
		return port;
	}

}
