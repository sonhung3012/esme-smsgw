
package com.fis.esme.persistence;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;




/**
 * <p>Java class for esmeSchedulerAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeSchedulerAction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esmeMessage" type="{http://scheduleraction.esme.fis.com/}esmeMessage" minOccurs="0"/>
 *         &lt;element name="esmeScheduler" type="{http://scheduleraction.esme.fis.com/}esmeScheduler" minOccurs="0"/>
 *         &lt;element name="executingStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="failNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastDatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="schaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
@XmlType(name = "esmeSchedulerAction", propOrder = {
    "esmeMessage",
    "esmeScheduler",
    "executingStatus",
    "failNumber",
    "groupId",
    "lastDatetime",
    "schaId",
    "status"
})
public class EsmeSchedulerAction {

    protected EsmeMessage esmeMessage;
    protected EsmeScheduler esmeScheduler;
    protected String executingStatus;
    protected Integer failNumber;
    protected String groupId;
    protected Date lastDatetime;
    protected long schaId;
    protected String status;

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
     * Gets the value of the esmeScheduler property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeScheduler }
     *     
     */
    public EsmeScheduler getEsmeScheduler() {
        return esmeScheduler;
    }

    /**
     * Sets the value of the esmeScheduler property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeScheduler }
     *     
     */
    public void setEsmeScheduler(EsmeScheduler value) {
        this.esmeScheduler = value;
    }

    /**
     * Gets the value of the executingStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExecutingStatus() {
        return executingStatus;
    }

    /**
     * Sets the value of the executingStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExecutingStatus(String value) {
        this.executingStatus = value;
    }

    /**
     * Gets the value of the failNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFailNumber() {
        return failNumber;
    }

    /**
     * Sets the value of the failNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFailNumber(Integer value) {
        this.failNumber = value;
    }

    /**
     * Gets the value of the groupId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the value of the groupId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupId(String value) {
        this.groupId = value;
    }

    /**
     * Gets the value of the lastDatetime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getLastDatetime() {
        return lastDatetime;
    }

    /**
     * Sets the value of the lastDatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastDatetime(Date value) {
        this.lastDatetime = value;
    }

    /**
     * Gets the value of the schaId property.
     * 
     */
    public long getSchaId() {
        return schaId;
    }

    /**
     * Sets the value of the schaId property.
     * 
     */
    public void setSchaId(long value) {
        this.schaId = value;
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
		EsmeSchedulerAction scheduleraction = (EsmeSchedulerAction) obj;
		return schaId == scheduleraction.getSchaId();
	}
}
