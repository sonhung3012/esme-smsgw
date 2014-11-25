package com.fis.esme.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for esmeIsdnPrefix complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeIsdnPrefix">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esmeSmscRoutings" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prefixId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="prefixValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esmeIsdnPrefix", propOrder = { "description", "esmeSmscRoutings", "prefixId", "prefixValue", "status" })
public class EsmeIsdnPrefix {

	protected String description;
	@XmlElement(nillable = true)
	protected List<Object> esmeSmscRoutings;
	protected long prefixId;
	protected String prefixValue;
	protected String status;
	transient boolean select;

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
	 * Gets the value of the esmeSmscRoutings property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
	 * not a <CODE>set</CODE> method for the esmeSmscRoutings property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getEsmeSmscRoutings().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Object }
	 * 
	 * 
	 */
	public List<Object> getEsmeSmscRoutings() {

		if (esmeSmscRoutings == null) {
			esmeSmscRoutings = new ArrayList<Object>();
		}
		return this.esmeSmscRoutings;
	}

	/**
	 * Gets the value of the prefixId property.
	 * 
	 */
	public long getPrefixId() {

		return prefixId;
	}

	/**
	 * Sets the value of the prefixId property.
	 * 
	 */
	public void setPrefixId(long value) {

		this.prefixId = value;
	}

	/**
	 * Gets the value of the prefixValue property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPrefixValue() {

		return prefixValue;
	}

	/**
	 * Sets the value of the prefixValue property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPrefixValue(String value) {

		this.prefixValue = value;
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

	public boolean isSelect() {

		return select;
	}

	public void setSelect(boolean select) {

		this.select = select;
	}

}
