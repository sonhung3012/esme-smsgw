package com.fis.esme.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for apParam complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="apParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "apParam", propOrder = { "description", "parName", "parType", "parValue" })
public class ApParam {

	protected String description;
	protected String parName;
	protected String parType;
	protected String parValue;
	transient boolean select;

	public boolean isSelect() {

		return select;
	}

	public void setSelect(boolean select) {

		this.select = select;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {

		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {

		this.description = value;
	}

	/**
	 * Gets the value of the parName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getParName() {

		return parName;
	}

	/**
	 * Sets the value of the parName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setParName(String value) {

		this.parName = value.trim().toUpperCase();
	}

	/**
	 * Gets the value of the parType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getParType() {

		return parType;
	}

	/**
	 * Sets the value of the parType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setParType(String value) {

		this.parType = value.trim().toUpperCase();
	}

	/**
	 * Gets the value of the parValue property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getParValue() {

		return parValue;
	}

	/**
	 * Sets the value of the parValue property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setParValue(String value) {

		this.parValue = value.trim();
	}

	@Override
	public String toString() {

		return parType;
	}
}
