
package com.fis.esme.persistence;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



/**
 * <p>Java class for esmeMessageContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeMessageContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esmeLanguage" type="{http://messagecontent.esme.fis.com/}esmeLanguage" minOccurs="0"/>
 *         &lt;element name="esmeMessage" type="{http://messagecontent.esme.fis.com/}esmeMessage" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="lastModify" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esmeMessageContent", propOrder = {
    "esmeLanguage",
    "esmeMessage",
    "id",
    "lastModify",
    "message"
})
public class EsmeMessageContent {

    protected EsmeLanguage esmeLanguage;
    protected EsmeMessage esmeMessage;
    protected long id;
    @XmlSchemaType(name = "dateTime")
    protected Date lastModify;
    protected String message;
    transient String code;
    transient String desciption;
    transient String name;
    transient String status;
    transient boolean select;
    public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code.trim().toUpperCase();
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    /**
     * Gets the value of the esmeLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeLanguage }
     *     
     */
    public EsmeLanguage getEsmeLanguage() {
        return esmeLanguage;
    }

    /**
     * Sets the value of the esmeLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeLanguage }
     *     
     */
    public void setEsmeLanguage(EsmeLanguage value) {
        this.esmeLanguage = value;
    }

    /**
     * Gets the value of the esmeMessage property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeMessage }
     *     
     */
    public EsmeMessage getEsmeMessage() {
        return esmeMessage;
    }

    /**
     * Sets the value of the esmeMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeMessage }
     *     
     */
    public void setEsmeMessage(EsmeMessage value) {
        this.esmeMessage = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the lastModify property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getLastModify() {
        return lastModify;
    }

    /**
     * Sets the value of the lastModify property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModify(Date value) {
        this.lastModify = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

}
