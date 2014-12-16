package com.fis.esme.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for esmeSmsRouting complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeSmsRouting">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esmeCp" type="{http://smsrouting.esme.fis.com/}esmeCp" minOccurs="0"/>
 *         &lt;element name="esmeServices" type="{http://smsrouting.esme.fis.com/}esmeServices" minOccurs="0"/>
 *         &lt;element name="esmeShortCode" type="{http://smsrouting.esme.fis.com/}esmeShortCode" minOccurs="0"/>
 *         &lt;element name="esmeSmsCommand" type="{http://smsrouting.esme.fis.com/}esmeSmsCommand" minOccurs="0"/>
 *         &lt;element name="routingId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esmeSmsRouting", propOrder = { "esmeCp", "esmeServices", "esmeShortCode", "esmeSmsCommand", "routingId" })
public class EsmeSmsRouting {

	protected EsmeCp esmeCp;
	protected EsmeServices esmeServices;
	protected EsmeShortCode esmeShortCode;
	protected EsmeSmsCommand esmeSmsCommand;
	protected long routingId;

	transient boolean select;

	/**
	 * Gets the value of the esmeCp property.
	 * 
	 * @return possible object is {@link EsmeCp }
	 * 
	 */
	public EsmeCp getEsmeCp() {

		return esmeCp;
	}

	/**
	 * Sets the value of the esmeCp property.
	 * 
	 * @param value
	 *            allowed object is {@link EsmeCp }
	 * 
	 */
	public void setEsmeCp(EsmeCp value) {

		this.esmeCp = value;
	}

	/**
	 * Gets the value of the esmeServices property.
	 * 
	 * @return possible object is {@link EsmeServices }
	 * 
	 */
	public EsmeServices getEsmeServices() {

		return esmeServices;
	}

	/**
	 * Sets the value of the esmeServices property.
	 * 
	 * @param value
	 *            allowed object is {@link EsmeServices }
	 * 
	 */
	public void setEsmeServices(EsmeServices value) {

		this.esmeServices = value;
	}

	/**
	 * Gets the value of the esmeShortCode property.
	 * 
	 * @return possible object is {@link EsmeShortCode }
	 * 
	 */
	public EsmeShortCode getEsmeShortCode() {

		return esmeShortCode;
	}

	/**
	 * Sets the value of the esmeShortCode property.
	 * 
	 * @param value
	 *            allowed object is {@link EsmeShortCode }
	 * 
	 */
	public void setEsmeShortCode(EsmeShortCode value) {

		this.esmeShortCode = value;
	}

	/**
	 * Gets the value of the esmeSmsCommand property.
	 * 
	 * @return possible object is {@link EsmeSmsCommand }
	 * 
	 */
	public EsmeSmsCommand getEsmeSmsCommand() {

		return esmeSmsCommand;
	}

	/**
	 * Sets the value of the esmeSmsCommand property.
	 * 
	 * @param value
	 *            allowed object is {@link EsmeSmsCommand }
	 * 
	 */
	public void setEsmeSmsCommand(EsmeSmsCommand value) {

		this.esmeSmsCommand = value;
	}

	/**
	 * Gets the value of the routingId property.
	 * 
	 */
	public long getRoutingId() {

		return routingId;
	}

	/**
	 * Sets the value of the routingId property.
	 * 
	 */
	public void setRoutingId(long value) {

		this.routingId = value;
	}

	public boolean isSelect() {

		return select;
	}

	public void setSelect(boolean select) {

		this.select = select;
	}

	@Override
	public String toString() {

		return esmeCp.getCode();
	}
}
