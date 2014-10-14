
package com.fis.esme.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.util.StringUtil;


/**
 * <p>Java class for esmeLanguage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeLanguage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esmeMessageContents" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isDefault" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="languageId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "esmeLanguage", propOrder = {
    "code",
    "esmeMessageContents",
    "isDefault",
    "languageId",
    "name",
    "status"
})
public class EsmeLanguage {

    protected String code;
    @XmlElement(nillable = true)
    protected List<Object> esmeMessageContents;
    protected String isDefault;
    protected long languageId;
    protected String name;
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
     * Gets the value of the esmeMessageContents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esmeMessageContents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsmeMessageContents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getEsmeMessageContents() {
        if (esmeMessageContents == null) {
            esmeMessageContents = new ArrayList<Object>();
        }
        return this.esmeMessageContents;
    }

    /**
     * Gets the value of the isDefault property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the value of the isDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsDefault(String value) {
    	if ("1".equals(value) || "true".equalsIgnoreCase(value))
		{
    		this.isDefault = "1";
		}
    	else
    	{
    		this.isDefault = "0";
    	}
    }

    /**
     * Gets the value of the languageId property.
     * 
     */
    public long getLanguageId() {
        return languageId;
    }

    /**
     * Sets the value of the languageId property.
     * 
     */
    public void setLanguageId(long value) {
        this.languageId = value;
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
    public String toString()
    {
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
		EsmeLanguage service = (EsmeLanguage) obj;
		return languageId == service.getLanguageId();
	}

	
	public int compareTo(EsmeLanguage service)
	{
		String o1 = this.getName();
		String o2 = service.getName();
		return StringUtil.compareVietnameseString(o1, o2);
	}

}
