package com.fis.esme.core.smsc.util;

import java.util.HashMap;

import com.fss.mwallet.util.ReceiveMessage;

public class ReceiveMessageEx extends ReceiveMessage {
	public HashMap<String, String> property = new HashMap();

	public ReceiveMessageEx(org.smpp.pdu.PDU pdu) {
		super(pdu);
	}

	public java.util.Map getAttributes() {
		return property;
	}

	public void setAttribute(String arg0, String arg1) {
		property.put(arg0, arg1);
	}

	public String getAttribute(String arg1) {
		return property.get(arg1);
	}
}
