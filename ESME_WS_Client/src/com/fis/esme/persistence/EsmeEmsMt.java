
package com.fis.esme.persistence;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



/**
 * <p>Java class for esmeEmsMt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeEmsMt">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="commandCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esmeCp" type="{http://emsmt.esme.fis.com/}esmeCp" minOccurs="0"/>
 *         &lt;element name="esmeEmsMo" type="{http://emsmt.esme.fis.com/}esmeEmsMo" minOccurs="0"/>
 *         &lt;element name="esmeFileUpload" type="{http://emsmt.esme.fis.com/}esmeFileUpload" minOccurs="0"/>
 *         &lt;element name="esmeGroups" type="{http://emsmt.esme.fis.com/}esmeGroups" minOccurs="0"/>
 *         &lt;element name="esmeSmsLog" type="{http://emsmt.esme.fis.com/}esmeSmsLog" minOccurs="0"/>
 *         &lt;element name="esmeSubscriber" type="{http://emsmt.esme.fis.com/}esmeSubscriber" minOccurs="0"/>
 *         &lt;element name="lastRetryTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mtId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="registerDeliveryReport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reloadNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="retryNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
@XmlType(name = "esmeEmsMt", propOrder = {
    "commandCode",
    "esmeCp",
    "esmeEmsMo",
    "esmeFileUpload",
    "esmeGroups",
    "esmeSmsLog",
    "esmeSubscriber",
    "lastRetryTime",
    "message",
    "msisdn",
    "mtId",
    "registerDeliveryReport",
    "reloadNumber",
    "requestTime",
    "retryNumber",
    "shortCode",
    "status"
})
public class EsmeEmsMt {

    protected String commandCode;
    protected EsmeCp esmeCp;
    protected EsmeEmsMo esmeEmsMo;
    protected EsmeFileUpload esmeFileUpload;
    protected EsmeGroups esmeGroups;
    protected EsmeSmsLog esmeSmsLog;
    protected EsmeSubscriber esmeSubscriber;
    protected Date lastRetryTime;
    protected String message;
    protected String msisdn;
    protected long mtId;
    protected String registerDeliveryReport;
    protected long reloadNumber;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestTime;
    protected long retryNumber;
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
     * Gets the value of the esmeEmsMo property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeEmsMo }
     *     
     */
    public EsmeEmsMo getEsmeEmsMo() {
        return esmeEmsMo;
    }

    /**
     * Sets the value of the esmeEmsMo property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeEmsMo }
     *     
     */
    public void setEsmeEmsMo(EsmeEmsMo value) {
        this.esmeEmsMo = value;
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
     * Gets the value of the esmeGroups property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeGroups }
     *     
     */
    public EsmeGroups getEsmeGroups() {
        return esmeGroups;
    }

    /**
     * Sets the value of the esmeGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeGroups }
     *     
     */
    public void setEsmeGroups(EsmeGroups value) {
        this.esmeGroups = value;
    }

    /**
     * Gets the value of the esmeSmsLog property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeSmsLog }
     *     
     */
    public EsmeSmsLog getEsmeSmsLog() {
        return esmeSmsLog;
    }

    /**
     * Sets the value of the esmeSmsLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeSmsLog }
     *     
     */
    public void setEsmeSmsLog(EsmeSmsLog value) {
        this.esmeSmsLog = value;
    }

    /**
     * Gets the value of the esmeSubscriber property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeSubscriber }
     *     
     */
    public EsmeSubscriber getEsmeSubscriber() {
        return esmeSubscriber;
    }

    /**
     * Sets the value of the esmeSubscriber property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeSubscriber }
     *     
     */
    public void setEsmeSubscriber(EsmeSubscriber value) {
        this.esmeSubscriber = value;
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
     */
    public long getReloadNumber() {
        return reloadNumber;
    }

    /**
     * Sets the value of the reloadNumber property.
     * 
     */
    public void setReloadNumber(long value) {
        this.reloadNumber = value;
    }

    /**
     * Gets the value of the requestTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestTime() {
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
    public void setRequestTime(XMLGregorianCalendar value) {
        this.requestTime = value;
    }

    /**
     * Gets the value of the retryNumber property.
     * 
     */
    public long getRetryNumber() {
        return retryNumber;
    }

    /**
     * Sets the value of the retryNumber property.
     * 
     */
    public void setRetryNumber(long value) {
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

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		// object must be Test at this point
		EsmeEmsMt cp = (EsmeEmsMt) obj;
		return mtId == cp.getMtId();
	}    
}
