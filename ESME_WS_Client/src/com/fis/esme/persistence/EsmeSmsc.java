
package com.fis.esme.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.util.StringUtil;


/**
 * <p>Java class for esmeSmsc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeSmsc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="className" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="defaulShortCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="desciption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esmeSmscParams" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="esmeSmscRoutings" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smscId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="startupType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "esmeSmsc", propOrder = {
    "className",
    "code",
    "defaulShortCode",
    "desciption",
    "esmeSmscParams",
    "esmeSmscRoutings",
    "name",
    "smscId",
    "startupType",
    "status"
})
public class EsmeSmsc {

    protected String className;
    protected String code;
    protected String defaulShortCode;
    protected String desciption;
    @XmlElement(nillable = true)
    protected List<Object> esmeSmscParams;
    @XmlElement(nillable = true)
    protected List<Object> esmeSmscRoutings;
    protected String name;
    protected long smscId;
    protected String startupType;
    protected String status;
    transient boolean select;

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

    /**
     * Gets the value of the className property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the value of the className property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassName(String value) {
        this.className = value;
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
        this.code = value;
    }

    /**
     * Gets the value of the defaulShortCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaulShortCode() {
        return defaulShortCode;
    }

    /**
     * Sets the value of the defaulShortCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaulShortCode(String value) {
        this.defaulShortCode = value;
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
     * Gets the value of the esmeSmscParams property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esmeSmscParams property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsmeSmscParams().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getEsmeSmscParams() {
        if (esmeSmscParams == null) {
            esmeSmscParams = new ArrayList<Object>();
        }
        return this.esmeSmscParams;
    }

    /**
     * Gets the value of the esmeSmscRoutings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esmeSmscRoutings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsmeSmscRoutings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the smscId property.
     * 
     */
    public long getSmscId() {
        return smscId;
    }

    /**
     * Sets the value of the smscId property.
     * 
     */
    public void setSmscId(long value) {
        this.smscId = value;
    }

    /**
     * Gets the value of the startupType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartupType() {
        return startupType;
    }

    /**
     * Sets the value of the startupType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartupType(String value) {
        this.startupType = value;
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
    	
    	return this.name;
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
		EsmeSmsc  service = (EsmeSmsc) obj;
		return smscId == service.getSmscId();
	}

	
	public int compareTo(EsmeSmsc service)
	{
		String o1 = this.getName();
		String o2 = service.getName();
		return StringUtil.compareVietnameseString(o1, o2);
	}
}
