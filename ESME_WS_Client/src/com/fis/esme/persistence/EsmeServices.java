
package com.fis.esme.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.util.StringUtil;


/**
 * <p>Java class for esmeServices complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeServices">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="desciption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esmeSmsRoutings" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="rootId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="serviceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
@XmlType(name = "esmeServices", propOrder = {
    "desciption",
    "esmeSmsRoutings",
    "name",
    "parentId",
    "rootId",
    "serviceId",
    "status"
})
public class EsmeServices {

    protected String desciption;
    @XmlElement(nillable = true)
    protected List<Object> esmeSmsRoutings;
    protected String name;
    protected Long parentId;
    protected Long rootId;
    protected long serviceId;
    protected String status;
    transient boolean select;

    public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
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
        this.name = value.trim();
    }

    /**
     * Gets the value of the parentId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Sets the value of the parentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setParentId(Long value) {
        this.parentId = value;
    }

    /**
     * Gets the value of the rootId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRootId() {
        return rootId;
    }

    /**
     * Sets the value of the rootId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRootId(Long value) {
        this.rootId = value;
    }

    /**
     * Gets the value of the serviceId property.
     * 
     */
    public long getServiceId() {
        return serviceId;
    }

    /**
     * Sets the value of the serviceId property.
     * 
     */
    public void setServiceId(long value) {
        this.serviceId = value;
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
		EsmeServices service = (EsmeServices) obj;
		return serviceId == service.getServiceId();
	}

	
	public int compareTo(EsmeServices service)
	{
		String o1 = this.getName();
		String o2 = service.getName();
		return StringUtil.compareVietnameseString(o1, o2);
	}

}
