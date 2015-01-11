package com.fis.esme.core.entity;

public class SMSCMTRountingObject {
	private int smsc_id;
	private String code;
	private String defaul_short_code;
	private String shortcode;
	private String prefix_value;
	private boolean shortcode_routing = false;

	
	
	public boolean isShortcode_routing() {
		return shortcode_routing;
	}

	public void setShortcode_routing(boolean shortcode_routing) {
		this.shortcode_routing = shortcode_routing;
	}

	public int getSmsc_id() {
		return smsc_id;
	}

	public void setSmsc_id(int smsc_id) {
		this.smsc_id = smsc_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDefaul_short_code() {
		return defaul_short_code;
	}

	public void setDefaul_short_code(String defaul_short_code) {
		this.defaul_short_code = defaul_short_code;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public String getPrefix_value() {
		return prefix_value;
	}

	public void setPrefix_value(String prefix_value) {
		this.prefix_value = prefix_value;
	}

}
