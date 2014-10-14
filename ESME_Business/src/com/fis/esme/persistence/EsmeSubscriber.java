/**
 * 
 */
package com.fis.esme.persistence;

import java.util.Date;

/**
 * @author danchoi
 *
 */
public class EsmeSubscriber implements java.io.Serializable{
	
	private long subId;
	private String msisdn;
	private String status;
	private String email;
	private String sex;
	private Date createDate;
	private String address;
	private Date birthDate;
	
	public EsmeSubscriber() {
		super();
	}

	public EsmeSubscriber(long subId, String msisdn, String status,
			String email, String sex, Date createDate, String address,
			Date birthDate) {
		super();
		this.subId = subId;
		this.msisdn = msisdn;
		this.status = status;
		this.email = email;
		this.sex = sex;
		this.createDate = createDate;
		this.address = address;
		this.birthDate = birthDate;
	}

	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	
}
