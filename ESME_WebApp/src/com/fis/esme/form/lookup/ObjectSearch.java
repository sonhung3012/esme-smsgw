package com.fis.esme.form.lookup;

import com.fis.esme.classes.SearchDetail;

public class ObjectSearch extends SearchDetail {
	private String tfMsisdn;
	private String cbbShortCode;
	private String cbbService;
	private String cbbCommand;

	public String getTfMsisdn() {
		return tfMsisdn;
	}

	public void setTfMsisdn(String tfMsisdn) {
		this.tfMsisdn = tfMsisdn;
	}

	public String getCbbShortCode() {
		return cbbShortCode;
	}

	public void setCbbShortCode(String cbbShortCode) {
		this.cbbShortCode = cbbShortCode;
	}

	public String getCbbService() {
		return cbbService;
	}

	public void setCbbService(String cbbService) {
		this.cbbService = cbbService;
	}

	public String getCbbCommand() {
		return cbbCommand;
	}

	public void setCbbCommand(String cbbCommand) {
		this.cbbCommand = cbbCommand;
	}

	public ObjectSearch() {
	}

}