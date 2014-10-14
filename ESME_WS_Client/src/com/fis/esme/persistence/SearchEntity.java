package com.fis.esme.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for searchEntity complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="searchEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="fromDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listInteger" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listLong" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listString" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="onoff" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="roleId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="subId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="subcType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="switchCase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="values" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchEntity", propOrder = { "accountId", "fromDate", "key",
		"listInteger", "listLong", "listString", "msisdn", "onoff", "roleId",
		"subId", "subcType", "switchCase", "toDate", "values" })
public class SearchEntity {

	protected Long accountId;
	@XmlSchemaType(name = "dateTime")
	protected Date fromDate;
	protected String key;
	@XmlElement(nillable = true)
	protected List<Integer> listInteger;
	@XmlElement(nillable = true)
	protected List<Long> listLong;
	@XmlElement(nillable = true)
	protected List<String> listString;
	protected String msisdn;
	protected Boolean onoff;
	protected Integer roleId;
	protected Long subId;
	protected String subcType;
	protected String switchCase;
	@XmlSchemaType(name = "dateTime")
	protected Date toDate;
	protected String values;

	/**
	 * Gets the value of the accountId property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * Sets the value of the accountId property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setAccountId(Long value) {
		this.accountId = value;
	}

	/**
	 * Gets the value of the fromDate property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * Sets the value of the fromDate property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setFromDate(Date value) {
		this.fromDate = value;
	}

	/**
	 * Gets the value of the key property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the value of the key property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setKey(String value) {
		this.key = value;
	}

	/**
	 * Gets the value of the listInteger property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the listInteger property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getListInteger().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Integer }
	 * 
	 * 
	 */
	public List<Integer> getListInteger() {
		if (listInteger == null) {
			listInteger = new ArrayList<Integer>();
		}
		return this.listInteger;
	}

	/**
	 * Gets the value of the listLong property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the listLong property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getListLong().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Long }
	 * 
	 * 
	 */
	public List<Long> getListLong() {
		if (listLong == null) {
			listLong = new ArrayList<Long>();
		}
		return this.listLong;
	}

	/**
	 * Gets the value of the listString property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the listString property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getListString().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getListString() {
		if (listString == null) {
			listString = new ArrayList<String>();
		}
		return this.listString;
	}

	/**
	 * Gets the value of the msisdn property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * Sets the value of the msisdn property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMsisdn(String value) {
		this.msisdn = value;
	}

	/**
	 * Gets the value of the onoff property.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isOnoff() {
		return onoff;
	}

	/**
	 * Sets the value of the onoff property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setOnoff(Boolean value) {
		this.onoff = value;
	}

	/**
	 * Gets the value of the roleId property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * Sets the value of the roleId property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setRoleId(Integer value) {
		this.roleId = value;
	}

	/**
	 * Gets the value of the subId property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getSubId() {
		return subId;
	}

	/**
	 * Sets the value of the subId property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setSubId(Long value) {
		this.subId = value;
	}

	/**
	 * Gets the value of the subcType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSubcType() {
		return subcType;
	}

	/**
	 * Sets the value of the subcType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSubcType(String value) {
		this.subcType = value;
	}

	/**
	 * Gets the value of the switchCase property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSwitchCase() {
		return switchCase;
	}

	/**
	 * Sets the value of the switchCase property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSwitchCase(String value) {
		this.switchCase = value;
	}

	/**
	 * Gets the value of the toDate property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * Sets the value of the toDate property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setToDate(Date value) {
		this.toDate = value;
	}

	/**
	 * Gets the value of the values property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValues() {
		return values;
	}

	/**
	 * Sets the value of the values property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setValues(String value) {
		this.values = value;
	}

}
