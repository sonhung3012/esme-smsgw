
package com.fis.esme.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esmeShortCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeShortCode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="desciption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esmeSmsRoutings" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mtFreeNumber" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="shortCodeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
@XmlType(name = "esmeShortCode", propOrder = {
    "code",
    "desciption",
    "esmeSmsRoutings",
    "mtFreeNumber",
    "price",
    "shortCodeId",
    "status"
})
public class EsmeShortCode {

    protected String code;
    protected String desciption;
    @XmlElement(nillable = true)
    protected List<Object> esmeSmsRoutings;
    protected Byte mtFreeNumber;
    protected Long price;
    protected long shortCodeId;
    protected String status;
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
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value.trim().toUpperCase();
    }

    /**
     * Gets the value of the desciption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesciption() {
        return desciption;
    }

    /**
     * Sets the value of the desciption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesciption(String value) {
        this.desciption = value;
    }

    /**
     * Gets the value of the esmeSmsRoutings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esmeSmsRoutings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsmeSmsRoutings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getEsmeSmsRoutings() {
        if (esmeSmsRoutings == null) {
            esmeSmsRoutings = new ArrayList<Object>();
        }
        return this.esmeSmsRoutings;
    }

    /**
     * Gets the value of the mtFreeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getMtFreeNumber() {
        return mtFreeNumber;
    }

    /**
     * Sets the value of the mtFreeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setMtFreeNumber(Byte value) {
        this.mtFreeNumber = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPrice(Long value) {
        this.price = value;
    }

    /**
     * Gets the value of the shortCodeId property.
     * 
     */
    public long getShortCodeId() {
        return shortCodeId;
    }

    /**
     * Sets the value of the shortCodeId property.
     * 
     */
    public void setShortCodeId(long value) {
        this.shortCodeId = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }
    
    @Override
    public String toString() {
    	
    	return this.code;
    }
    
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass()))
		{
			return false;
		}
		// object must be Test at this point
		EsmeShortCode service = (EsmeShortCode) obj;
		return shortCodeId == service.getShortCodeId();
	}


}
