package com.fis.esme.utils;

import java.util.Date;
import java.util.List;

public class SearchEntity {
	private String key;
	private String msisdn;
	private String subcType;
	private Date fromDate;
	private Date toDate;
	private List<Long> listLong;
	private List<String> listString;
	private List<Integer> listInteger;
	
	private String values;

	private Long accountId;
	private Integer roleId;
	private Long subId;

	private String switchCase;
	
	private Boolean onoff;

	public SearchEntity() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getSubcType() {
		return subcType;
	}

	public void setSubcType(String subcType) {
		this.subcType = subcType;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Long> getListLong() {
		return listLong;
	}

	public void setListLong(List<Long> listLong) {
		this.listLong = listLong;
	}

	public List<String> getListString() {
		return listString;
	}

	public void setListString(List<String> listString) {
		this.listString = listString;
	}

	public List<Integer> getListInteger() {
		return listInteger;
	}

	public void setListInteger(List<Integer> listInteger) {
		this.listInteger = listInteger;
	}

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getSwitchCase() {
		return switchCase;
	}

	public void setSwitchCase(String switchCase) {
		this.switchCase = switchCase;
	}

	public Boolean isOnoff() {
		return onoff;
	}

	public void setOnoff(Boolean onoff) {
		this.onoff = onoff;
	}

}
