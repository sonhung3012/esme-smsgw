package com.fis.esme.persistence;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fis.esme.util.StringUtil;

/**
 * <p>
 * Java class for esmeCp complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="esmeCp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createDatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="defaultShortCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="desciption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="protocal" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *         &lt;element name="receivePassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiveUrlMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiveUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esmeCp", propOrder = { "code", "cpId", "createDatetime",
		"defaultShortCode", "desciption", "password", "protocal",
		"receivePassword", "receiveUrlMsg", "receiveUsername", "status",
		"username" })
public class EsmeCp {

	protected String code;
	protected long cpId;
	@XmlSchemaType(name = "dateTime")
	protected Date createDatetime;
	protected String defaultShortCode;
	protected String desciption;
	protected String password;
	protected byte protocal;
	protected String receivePassword;
	protected String receiveUrlMsg;
	protected String receiveUsername;
	protected String status;
	protected String username;
	transient boolean select;

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	/**
	 * Gets the value of the code property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the value of the code property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCode(String value) {
		this.code = value.trim().toUpperCase();
	}

	/**
	 * Gets the value of the cpId property.
	 * 
	 */
	public long getCpId() {
		return cpId;
	}

	/**
	 * Sets the value of the cpId property.
	 * 
	 */
	public void setCpId(long value) {
		this.cpId = value;
	}

	/**
	 * Gets the value of the createDatetime property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public Date getCreateDatetime() {
		return createDatetime;
	}

	/**
	 * Sets the value of the createDatetime property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setCreateDatetime(Date value) {
		this.createDatetime = value;
	}

	/**
	 * Gets the value of the defaultShortCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDefaultShortCode() {
		return defaultShortCode;
	}

	/**
	 * Sets the value of the defaultShortCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDefaultShortCode(String value) {
		this.defaultShortCode = value;
	}

	/**
	 * Gets the value of the desciption property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDesciption() {
		return desciption;
	}

	/**
	 * Sets the value of the desciption property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDesciption(String value) {
		this.desciption = value;
	}

	/**
	 * Gets the value of the password property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the value of the password property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPassword(String value) {
		this.password = value;
	}

	/**
	 * Gets the value of the protocal property.
	 * 
	 */
	public byte getProtocal() {
		return protocal;
	}

	/**
	 * Sets the value of the protocal property.
	 * 
	 */
	public void setProtocal(byte value) {
		this.protocal = value;
	}

	/**
	 * Gets the value of the receivePassword property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReceivePassword() {
		return receivePassword;
	}

	/**
	 * Sets the value of the receivePassword property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReceivePassword(String value) {
		this.receivePassword = value;
	}

	/**
	 * Gets the value of the receiveUrlMsg property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReceiveUrlMsg() {
		return receiveUrlMsg;
	}

	/**
	 * Sets the value of the receiveUrlMsg property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReceiveUrlMsg(String value) {
		this.receiveUrlMsg = value;
	}

	/**
	 * Gets the value of the receiveUsername property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReceiveUsername() {
		return receiveUsername;
	}

	/**
	 * Sets the value of the receiveUsername property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReceiveUsername(String value) {
			this.receiveUsername = (value == null) ? value : value.trim();
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setStatus(String value) {
		this.status = value;
	}

	/**
	 * Gets the value of the username property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the value of the username property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUsername(String value) {
			this.username = (value == null) ? value : value.trim();
	}

	@Override
	public String toString() {
		return this.code;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		// object must be Test at this point
		EsmeCp cp = (EsmeCp) obj;
		return cpId == cp.getCpId();
	}

	public int compareTo(EsmeCp cp) {
		String o1 = this.getCode();
		String o2 = cp.getCode();
		return StringUtil.compareVietnameseString(o1, o2);
	}

}
