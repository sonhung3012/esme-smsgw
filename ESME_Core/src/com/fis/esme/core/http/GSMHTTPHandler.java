package com.fis.esme.core.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.smpp.pdu.DeliverSM;

import com.fis.esme.core.entity.SubscriberObject;
import com.fis.esme.core.exception.LoginException;
import com.fis.esme.core.http.util.CipherUtils;
import com.fis.esme.core.services.HTTPServer;
import com.fis.esme.core.smsc.util.ReceiveMessageEx;
import com.fis.esme.core.util.LinkQueue;
import com.fss.util.StringUtil;
import com.logica.smpp.Data;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GSMHTTPHandler implements HttpHandler {
	private LinkQueue maqMORequestQueue;
	private int sequenceNumber = 1;

	public void setMORequestQueue(LinkQueue MORequestQueue)
	{
		maqMORequestQueue=MORequestQueue;
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String requestMethod = exchange.getRequestMethod();
		if (requestMethod.equalsIgnoreCase("GET")) {
			Map<String, String> params = queryToMap(exchange.getRequestURI()
					.getQuery());
			System.out.println(params.toString());
			try {
				DeliverSM deliverSM = new DeliverSM();
				deliverSM.setCommandId(1);
				deliverSM.setSequenceNumber(sequenceNumber++);
				deliverSM.setShortMessage(params.get("FullMessage"));
				deliverSM.setSourceAddr(params.get("Sender"));
				deliverSM.setDestAddr(params.get("Prefix"));
				ReceiveMessageEx receiveMessage = new ReceiveMessageEx(
						deliverSM);
				receiveMessage.setAttribute("request_id",
						"" + deliverSM.getSequenceNumber());
				receiveMessage.setAttribute("DataType", "DeliverSM");
				maqMORequestQueue.enqueueNotify(receiveMessage);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Content-Type", "text/plain");
			exchange.sendResponseHeaders(200, 0);
			OutputStream responseBody = exchange.getResponseBody();
			responseBody.close();
		}
	}

	public Map<String, String> queryToMap(String query) {
		Map<String, String> result = new HashMap<String, String>();
		for (String param : query.split("&")) {
			String pair[] = param.split("=");
			if (pair.length > 1) {
				result.put(pair[0], pair[1]);
			} else {
				result.put(pair[0], "");
			}
		}
		return result;
	}

	/**
	 * 
	 * @param strCalling
	 * @return
	 */
	private String parseCalling(String strCalling) {
		if (strCalling.startsWith("95")) {
			strCalling = strCalling.substring(2);
		}
		if (strCalling.startsWith("0")) {
			strCalling = strCalling.substring(1);
		}
		return strCalling;
	}

}
