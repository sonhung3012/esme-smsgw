package com.fis.esme.core.smpp.entity;

import java.io.Serializable;
import java.util.Hashtable;

public class DatabaseObject implements Serializable {

	public static final String INSERT_ACTION_AUDIT = "INSERT_ACTION_AUDIT";
	public static final String INSERT_SMS_SCHEDULE = "INSERT_SMS_SCHEDULE";
	
	private String mstrDatabaseAction = "";
	private Hashtable<String, Object> mhtbValue = new Hashtable<String, Object>();
	
	public DatabaseObject(String strDatabaseAction, Hashtable<String, Object> htbValue) {
		mstrDatabaseAction = strDatabaseAction;
		mhtbValue = htbValue;
	}
	
	public String getDatabaseAction() {
		return mstrDatabaseAction;
	}

	public void setDatabaseAction(String strDatabaseAction) {
		mstrDatabaseAction = strDatabaseAction;
	}
	
	public void setValue(Hashtable<String, Object> htbValue) {
		mhtbValue = htbValue;
	}
	
	public Hashtable<String, Object> getValue() {
		return mhtbValue;
	}
}