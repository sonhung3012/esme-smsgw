package com.fis.esme.core.entity;

import java.util.Vector;

public class SMSMORoutingInfo {
	private String shortcode;
	private Vector<SMSMORoutingObject> vtSMSMORouting = new Vector<SMSMORoutingObject>();

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public Vector<SMSMORoutingObject> getVtSMSMORouting() {
		return vtSMSMORouting;
	}

	public void setVtSMSMORouting(Vector<SMSMORoutingObject> vtSMSMORouting) {
		this.vtSMSMORouting = vtSMSMORouting;
	}

}
