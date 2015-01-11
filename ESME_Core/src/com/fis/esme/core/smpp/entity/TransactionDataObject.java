package com.fis.esme.core.smpp.entity;

import java.io.Serializable;

public class TransactionDataObject implements Serializable {
	
	private String mstrESMESequenceNumber;
	private String mstrESMETransID;
	private String mstrSMSCTransID;
	private String mstrCPID;
	private String mstrSMSCID;
	
	public String getESMESequenceNumber() {
		return mstrESMESequenceNumber;
	}
	
	public void setESMESequenceNumber(String strESMESequenceNumber) {
		mstrESMESequenceNumber = strESMESequenceNumber;
	}
	
	public String getSMSCTransID() {
		return mstrSMSCTransID;
	}
	
	public void setSMSCTransID(String strSMSCTransID) {
		mstrSMSCTransID = strSMSCTransID;
	}
	
	public String getCPID() {
		return mstrCPID;
	}
	
	public void setCPID(String strCPID) {
		mstrCPID = strCPID;
	}
	
	public String getSMSCID() {
		return mstrSMSCID;
	}
	
	public void setSMSCID(String strSMSCID) {
		mstrSMSCID = strSMSCID;
	}
	
	public String getESMETransID() {
		return mstrESMETransID;
	}
	
	public void setESMETransID(String strESMETransID) {
		mstrESMETransID = strESMETransID;
	}
}