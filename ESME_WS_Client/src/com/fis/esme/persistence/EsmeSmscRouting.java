package com.fis.esme.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for esmeSmscRouting complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeSmscRouting">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esmeIsdnPrefix" type="{http://smscrouting.esme.fis.com/}esmeIsdnPrefix" minOccurs="0"/>
 *         &lt;element name="esmeSmsc" type="{http://smscrouting.esme.fis.com/}esmeSmsc" minOccurs="0"/>
 *         &lt;element name="smscRoutingId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esmeSmscRouting", propOrder = { "esmeIsdnPrefix", "esmeSmsc", "smscRoutingId" })
public class EsmeSmscRouting {

	protected EsmeIsdnPrefix esmeIsdnPrefix;
	protected EsmeSmsc esmeSmsc;
	protected long smscRoutingId;
	transient boolean select;

	/**
	 * Gets the value of the esmeIsdnPrefix property.
	 * 
	 * @return possible object is {@link EsmeIsdnPrefix }
	 * 
	 */
	public EsmeIsdnPrefix getEsmeIsdnPrefix() {

		return esmeIsdnPrefix;
	}

	/**
	 * Sets the value of the esmeIsdnPrefix property.
	 * 
	 * @param value
	 *            allowed object is {@link EsmeIsdnPrefix }
	 * 
	 */
	public void setEsmeIsdnPrefix(EsmeIsdnPrefix value) {

		this.esmeIsdnPrefix = value;
	}

	/**
	 * Gets the value of the esmeSmsc property.
	 * 
	 * @return possible object is {@link EsmeSmsc }
	 * 
	 */
	public EsmeSmsc getEsmeSmsc() {

		return esmeSmsc;
	}

	/**
	 * Sets the value of the esmeSmsc property.
	 * 
	 * @param value
	 *            allowed object is {@link EsmeSmsc }
	 * 
	 */
	public void setEsmeSmsc(EsmeSmsc value) {

		this.esmeSmsc = value;
	}

	/**
	 * Gets the value of the smscRoutingId property.
	 * 
	 */
	public long getSmscRoutingId() {

		return smscRoutingId;
	}

	/**
	 * Sets the value of the smscRoutingId property.
	 * 
	 */
	public void setSmscRoutingId(long value) {

		this.smscRoutingId = value;
	}

	public boolean isSelect() {

		return select;
	}

	public void setSelect(boolean select) {

		this.select = select;
	}

	// @Override
	// public String toString() {
	//
	// return this.esmeSmsc;
	// }
	//
	// public boolean equals(Object obj) {
	// if (this == obj) {
	// return true;
	// }
	// if ((obj == null) || (obj.getClass() != this.getClass())) {
	// return false;
	// }
	// // object must be Test at this point
	// EsmeSmsc service = (EsmeSmsc) obj;
	// return smscId == service.getSmscId();
	// }
	//
	// public int compareTo(EsmeSmsc service) {
	// String o1 = this.getShortcode();
	// String o2 = service.getName();
	// return StringUtil.compareVietnameseString(o1, o2);
	// }

	@Override
	public String toString() {

		return esmeSmsc.getName();
	}
}
