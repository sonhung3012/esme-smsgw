
package com.fis.esme.persistence;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for esmeSmsLog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeSmsLog">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ackDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ackId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esmeCp" type="{http://smslog.esme.fis.com/}esmeCp" minOccurs="0"/>
 *         &lt;element name="esmeFileUpload" type="{http://smslog.esme.fis.com/}esmeFileUpload" minOccurs="0"/>
 *         &lt;element name="esmeServices" type="{http://smslog.esme.fis.com/}esmeServices" minOccurs="0"/>
 *         &lt;element name="esmeShortCode" type="{http://smslog.esme.fis.com/}esmeShortCode" minOccurs="0"/>
 *         &lt;element name="esmeSmsCommand" type="{http://smslog.esme.fis.com/}esmeSmsCommand" minOccurs="0"/>
 *         &lt;element name="esmeSmsc" type="{http://smslog.esme.fis.com/}esmeSmsc" minOccurs="0"/>
 *         &lt;element name="lastUpdate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="logId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="responseId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="serviceParentId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="serviceRoorId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="smsContent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceSmsId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalSms" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
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
@XmlType(name = "esmeSmsLog", propOrder = {
    "ackDate",
    "ackId",
    "errorCode",
    "esmeCp",
    "esmeFileUpload",
    "esmeServices",
    "esmeShortCode",
    "esmeSmsCommand",
    "esmeSmsc",
    "lastUpdate",
    "logId",
    "msisdn",
    "requestId",
    "requestTime",
    "responseId",
    "serviceParentId",
    "serviceRoorId",
    "smsContent",
    "sourceSmsId",
    "status",
    "totalSms",
    "type"
})
public class EsmeSmsLog {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ackDate;
    protected Long ackId;
    protected String errorCode;
    protected EsmeCp esmeCp;
    protected EsmeFileUpload esmeFileUpload;
    protected EsmeServices esmeServices;
    protected EsmeShortCode esmeShortCode;
    protected EsmeSmsCommand esmeSmsCommand;
    protected EsmeSmsc esmeSmsc;
    @XmlSchemaType(name = "dateTime")
    protected Date lastUpdate;
    protected long logId;
    protected String msisdn;
    protected Long requestId;
    @XmlSchemaType(name = "dateTime")
    protected Date requestTime;
    protected Long responseId;
    protected Long serviceParentId;
    protected Long serviceRoorId;
    protected String smsContent;
    protected Long sourceSmsId;
    protected String status;
    protected Byte totalSms;
    protected String type;

    /**
     * Gets the value of the ackDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAckDate() {
        return ackDate;
    }

    /**
     * Sets the value of the ackDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAckDate(XMLGregorianCalendar value) {
        this.ackDate = value;
    }

    /**
     * Gets the value of the ackId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAckId() {
        return ackId;
    }

    /**
     * Sets the value of the ackId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAckId(Long value) {
        this.ackId = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the esmeCp property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeCp }
     *     
     */
    public EsmeCp getEsmeCp() {
        return esmeCp;
    }

    /**
     * Sets the value of the esmeCp property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeCp }
     *     
     */
    public void setEsmeCp(EsmeCp value) {
        this.esmeCp = value;
    }

    /**
     * Gets the value of the esmeFileUpload property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeFileUpload }
     *     
     */
    public EsmeFileUpload getEsmeFileUpload() {
        return esmeFileUpload;
    }

    /**
     * Sets the value of the esmeFileUpload property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeFileUpload }
     *     
     */
    public void setEsmeFileUpload(EsmeFileUpload value) {
        this.esmeFileUpload = value;
    }

    /**
     * Gets the value of the esmeServices property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeServices }
     *     
     */
    public EsmeServices getEsmeServices() {
        return esmeServices;
    }

    /**
     * Sets the value of the esmeServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeServices }
     *     
     */
    public void setEsmeServices(EsmeServices value) {
        this.esmeServices = value;
    }

    /**
     * Gets the value of the esmeShortCode property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeShortCode }
     *     
     */
    public EsmeShortCode getEsmeShortCode() {
        return esmeShortCode;
    }

    /**
     * Sets the value of the esmeShortCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeShortCode }
     *     
     */
    public void setEsmeShortCode(EsmeShortCode value) {
        this.esmeShortCode = value;
    }

    /**
     * Gets the value of the esmeSmsCommand property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeSmsCommand }
     *     
     */
    public EsmeSmsCommand getEsmeSmsCommand() {
        return esmeSmsCommand;
    }

    /**
     * Sets the value of the esmeSmsCommand property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeSmsCommand }
     *     
     */
    public void setEsmeSmsCommand(EsmeSmsCommand value) {
        this.esmeSmsCommand = value;
    }

    /**
     * Gets the value of the esmeSmsc property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeSmsc }
     *     
     */
    public EsmeSmsc getEsmeSmsc() {
        return esmeSmsc;
    }

    /**
     * Sets the value of the esmeSmsc property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeSmsc }
     *     
     */
    public void setEsmeSmsc(EsmeSmsc value) {
        this.esmeSmsc = value;
    }

    /**
     * Gets the value of the lastUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the value of the lastUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdate(Date value) {
        this.lastUpdate = value;
    }

    /**
     * Gets the value of the logId property.
     * 
     */
    public long getLogId() {
        return logId;
    }

    /**
     * Sets the value of the logId property.
     * 
     */
    public void setLogId(long value) {
        this.logId = value;
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
     * Gets the value of the responseId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getResponseId() {
        return responseId;
    }

    /**
     * Sets the value of the responseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setResponseId(Long value) {
        this.responseId = value;
    }

    /**
     * Gets the value of the serviceParentId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getServiceParentId() {
        return serviceParentId;
    }

    /**
     * Sets the value of the serviceParentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setServiceParentId(Long value) {
        this.serviceParentId = value;
    }

    /**
     * Gets the value of the serviceRoorId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getServiceRoorId() {
        return serviceRoorId;
    }

    /**
     * Sets the value of the serviceRoorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setServiceRoorId(Long value) {
        this.serviceRoorId = value;
    }

    /**
     * Gets the value of the smsContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsContent() {
        return smsContent;
    }

    /**
     * Sets the value of the smsContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsContent(String value) {
        this.smsContent = value;
    }

    /**
     * Gets the value of the sourceSmsId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSourceSmsId() {
        return sourceSmsId;
    }

    /**
     * Sets the value of the sourceSmsId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSourceSmsId(Long value) {
        this.sourceSmsId = value;
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
     * Gets the value of the totalSms property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getTotalSms() {
        return totalSms;
    }

    /**
     * Sets the value of the totalSms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setTotalSms(Byte value) {
        this.totalSms = value;
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
