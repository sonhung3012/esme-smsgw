
package com.fis.esme.persistence;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for esmeSmsMt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeSmsMt">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="commandCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="fileUploadId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="lastRetryTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mtId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="registerDeliveryReport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reloadNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="retryNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="shortCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "esmeSmsMt", propOrder = {
    "commandCode",
    "cpId",
    "fileUploadId",
    "lastRetryTime",
    "message",
    "msisdn",
    "mtId",
    "registerDeliveryReport",
    "reloadNumber",
    "requestId",
    "requestTime",
    "retryNumber",
    "shortCode",
    "status"
})
public class EsmeSmsMt {

    protected String commandCode;
    protected long cpId;
    protected Long fileUploadId;
    @XmlSchemaType(name = "dateTime")
    protected Date lastRetryTime;
    protected String message;
    protected String msisdn;
    protected long mtId;
    protected String registerDeliveryReport;
    protected Integer reloadNumber;
    protected Long requestId;
    @XmlSchemaType(name = "dateTime")
    protected Date requestTime;
    protected Integer retryNumber;
    protected String shortCode;
    protected String status;

    /**
     * Gets the value of the commandCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommandCode() {
        return commandCode;
    }

    /**
     * Sets the value of the commandCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommandCode(String value) {
        this.commandCode = value;
    }

    /**
     * Gets the value of the cpId property.
     * 
     */
    public long getCpId() {
        return cpId;
    }

    /**
     * Sets the value of the cpId property.
     * 
     */
    public void setCpId(long value) {
        this.cpId = value;
    }

    /**
     * Gets the value of the fileUploadId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFileUploadId() {
        return fileUploadId;
    }

    /**
     * Sets the value of the fileUploadId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFileUploadId(Long value) {
        this.fileUploadId = value;
    }

    /**
     * Gets the value of the lastRetryTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getLastRetryTime() {
        return lastRetryTime;
    }

    /**
     * Sets the value of the lastRetryTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastRetryTime(Date value) {
        this.lastRetryTime = value;
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

    /**
     * Gets the value of the msisdn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * Sets the value of the msisdn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsisdn(String value) {
        this.msisdn = value;
    }

    /**
     * Gets the value of the mtId property.
     * 
     */
    public long getMtId() {
        return mtId;
    }

    /**
     * Sets the value of the mtId property.
     * 
     */
    public void setMtId(long value) {
        this.mtId = value;
    }

    /**
     * Gets the value of the registerDeliveryReport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisterDeliveryReport() {
        return registerDeliveryReport;
    }

    /**
     * Sets the value of the registerDeliveryReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisterDeliveryReport(String value) {
        this.registerDeliveryReport = value;
    }

    /**
     * Gets the value of the reloadNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReloadNumber() {
        return reloadNumber;
    }

    /**
     * Sets the value of the reloadNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReloadNumber(Integer value) {
        this.reloadNumber = value;
    }

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRequestId(Long value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the requestTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getRequestTime() {
        return requestTime;
    }

    /**
     * Sets the value of the requestTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestTime(Date value) {
        this.requestTime = value;
    }

    /**
     * Gets the value of the retryNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRetryNumber() {
        return retryNumber;
    }

    /**
     * Sets the value of the retryNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRetryNumber(Integer value) {
        this.retryNumber = value;
    }

    /**
     * Gets the value of the shortCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortCode() {
        return shortCode;
    }

    /**
     * Sets the value of the shortCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortCode(String value) {
        this.shortCode = value;
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

}
