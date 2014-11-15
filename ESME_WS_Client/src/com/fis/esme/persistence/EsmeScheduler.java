
package com.fis.esme.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esmeScheduler complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeScheduler">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scheduledBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="schedulerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="schedulerStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esmeScheduler", propOrder = {
    "description",
    "name",
    "scheduledBy",
    "schedulerId",
    "schedulerStatus",
    "status",
    "time",
    "type"
})
public class EsmeScheduler {

    protected String description;
    protected String name;
    protected String scheduledBy;
    protected long schedulerId;
    protected String schedulerStatus;
    protected String status;
    protected String time;
    protected String type;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
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
     * Gets the value of the scheduledBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduledBy() {
        return scheduledBy;
    }

    /**
     * Sets the value of the scheduledBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduledBy(String value) {
        this.scheduledBy = value;
    }

    /**
     * Gets the value of the schedulerId property.
     * 
     */
    public long getSchedulerId() {
        return schedulerId;
    }

    /**
     * Sets the value of the schedulerId property.
     * 
     */
    public void setSchedulerId(long value) {
        this.schedulerId = value;
    }

    /**
     * Gets the value of the schedulerStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchedulerStatus() {
        return schedulerStatus;
    }

    /**
     * Sets the value of the schedulerStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchedulerStatus(String value) {
        this.schedulerStatus = value;
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

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTime(String value) {
        this.time = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
