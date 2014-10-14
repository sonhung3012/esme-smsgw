package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.commandsummary.CommandSummaryTransferer;
import com.fis.esme.commandsummary.CommandSummaryTransfererService;
import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;

public class CommandSummaryTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://commandsummary.esme.fis.com/", "CommandSummaryTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.COMMAND_SUMMARY_PORT_KEY);

	private CommandSummaryTransfererClient() {

	}

	public static CommandSummaryTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		CommandSummaryTransfererService ss = new CommandSummaryTransfererService(
				url, SERVICE_NAME);
		CommandSummaryTransferer port = ss.getCommandSummaryTransfererPort();
		return port;
	}

}
