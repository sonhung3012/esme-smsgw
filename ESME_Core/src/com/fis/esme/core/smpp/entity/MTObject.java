package com.fis.esme.core.smpp.entity;

import java.io.Serializable;
import java.util.Vector;

public class MTObject implements Serializable {

	private String mstrCPSequenceNumber;
	private String mstrESMESequenceNumber;
	private String mstrESMEMessageID;
	private String mstrSourceAddress;
	private String mstrDestinationAddress;
	private boolean mbDeliveryRegister;
	private String mstrContent;
	
	public MTObject(Vector vtData) {
		mstrCPSequenceNumber = vtData.get(0).toString();
		mstrESMESequenceNumber = vtData.get(1).toString();
		mstrESMEMessageID = vtData.get(2).toString();
		mstrSourceAddress = vtData.get(3).toString();
		mstrDestinationAddress = vtData.get(4).toString();
		String strDeliveryRegister = vtData.get(5).toString();
		if(strDeliveryRegister.equals("1")) {
			mbDeliveryRegister = true;
		} else {
			mbDeliveryRegister = false;
		}
		mstrContent = vtData.get(6).toString();
	}

	public String getCPSequenceNumber() {
		return mstrCPSequenceNumber;
	}

	public void setCPSequenceNumber(String strCPSequenceNumber) {
		mstrCPSequenceNumber = strCPSequenceNumber;
	}

	public String getESMESequenceNumber() {
		return mstrESMESequenceNumber;
	}

	public void setESMESequenceNumber(String strESMESequenceNumber) {
		mstrESMESequenceNumber = strESMESequenceNumber;
	}

	public String getESMEMessageID() {
		return mstrESMEMessageID;
	}

	public void setESMEMessageID(String strESMEMessageID) {
		mstrESMEMessageID = strESMEMessageID;
	}

	public String getSourceAddress() {
		return mstrSourceAddress;
	}

	public void setSourceAddress(String strSourceAddress) {
		mstrSourceAddress = strSourceAddress;
	}

	public String getDestinationAddress() {
		return mstrDestinationAddress;
	}

	public void setDestinationAddress(String strDestinationAddress) {
		mstrDestinationAddress = strDestinationAddress;
	}

	public boolean isDeliveryRegister() {
		return mbDeliveryRegister;
	}

	public void setDeliveryRegister(boolean bDeliveryRegister) {
		mbDeliveryRegister = bDeliveryRegister;
	}
	
	public String getContent() {
		return mstrContent;
	}

	public void setContent(String strContent) {
		mstrContent = strContent;
	}
}
