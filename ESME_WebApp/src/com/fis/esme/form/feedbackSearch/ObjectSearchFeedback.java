package com.fis.esme.form.feedbackSearch;

import com.fis.esme.classes.SearchDetail;
import com.fis.esme.persistence.EsmeGroups;
import com.fis.esme.persistence.EsmeShortCode;

public class ObjectSearchFeedback extends SearchDetail {

	private String tfMsisdn;
	private EsmeShortCode cbbShortCode;
	private String tfMessage;
	private String tfFeedback;
	private String cbbStatus;
	private EsmeGroups cbbGroup;

	public String getTfMsisdn() {

		return tfMsisdn;
	}

	public void setTfMsisdn(String tfMsisdn) {

		this.tfMsisdn = tfMsisdn;
	}

	public EsmeShortCode getCbbShortCode() {

		return cbbShortCode;
	}

	public void setCbbShortCode(EsmeShortCode cbbShortCode) {

		this.cbbShortCode = cbbShortCode;
	}

	public String getTfMessage() {

		return tfMessage;
	}

	public void setTfMessage(String tfMessage) {

		this.tfMessage = tfMessage;
	}

	public String getTfFeedback() {

		return tfFeedback;
	}

	public void setTfFeedback(String tfFeedback) {

		this.tfFeedback = tfFeedback;
	}

	public String getCbbStatus() {

		return cbbStatus;
	}

	public void setCbbStatus(String cbbStatus) {

		this.cbbStatus = cbbStatus;
	}

	public EsmeGroups getCbbGroup() {

		return cbbGroup;
	}

	public void setCbbGroup(EsmeGroups cbbGroup) {

		this.cbbGroup = cbbGroup;
	}
}
