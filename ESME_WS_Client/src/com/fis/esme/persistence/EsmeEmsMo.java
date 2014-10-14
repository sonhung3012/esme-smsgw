
package com.fis.esme.persistence;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for esmeEmsMo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeEmsMo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esmeCp" type="{http://emsmo.esme.fis.com/}esmeCp" minOccurs="0"/>
 *         &lt;element name="esmeGroups" type="{http://emsmo.esme.fis.com/}esmeGroups" minOccurs="0"/>
 *         &lt;element name="esmeServices" type="{http://emsmo.esme.fis.com/}esmeServices" minOccurs="0"/>
 *         &lt;element name="esmeServicesParent" type="{http://emsmo.esme.fis.com/}esmeServices" minOccurs="0"/>
 *         &lt;element name="esmeServicesRoot" type="{http://emsmo.esme.fis.com/}esmeServices" minOccurs="0"/>
 *         &lt;element name="esmeShortCode" type="{http://emsmo.esme.fis.com/}esmeShortCode" minOccurs="0"/>
 *         &lt;element name="esmeSmsCommand" type="{http://emsmo.esme.fis.com/}esmeSmsCommand" minOccurs="0"/>
 *         &lt;element name="esmeSmsLog" type="{http://emsmo.esme.fis.com/}esmeSmsLog" minOccurs="0"/>
 *         &lt;element name="esmeSmsc" type="{http://emsmo.esme.fis.com/}esmeSmsc" minOccurs="0"/>
 *         &lt;element name="esmeSubscriber" type="{http://emsmo.esme.fis.com/}esmeSubscriber" minOccurs="0"/>
 *         &lt;element name="lastUpdate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="protocal" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="retryNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="shortCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "esmeEmsMo", propOrder = {
    "esmeCp",
    "esmeGroups",
    "esmeServices",
    "esmeServicesParent",
    "esmeServicesRoot",
    "esmeShortCode",
    "esmeSmsCommand",
    "esmeSmsLog",
    "esmeSmsc",
    "esmeSubscriber",
    "lastUpdate",
    "message",
    "moId",
    "msisdn",
    "protocal",
    "reason",
    "requestTime",
    "retryNumber",
    "shortCode",
    "status",
    "type"
})
public class EsmeEmsMo {

    protected EsmeCp esmeCp;
    protected EsmeGroups esmeGroups;
    protected EsmeServices esmeServices;
    protected EsmeServices esmeServicesParent;
    protected EsmeServices esmeServicesRoot;
    protected EsmeShortCode esmeShortCode;
    protected EsmeSmsCommand esmeSmsCommand;
    protected EsmeSmsLog esmeSmsLog;
    protected EsmeSmsc esmeSmsc;
    protected EsmeSubscriber esmeSubscriber;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdate;
    protected String message;
    protected long moId;
    protected String msisdn;
    protected Integer protocal;
    protected String reason;
    protected Date requestTime;
    protected Integer retryNumber;
    protected String shortCode;
    protected String status;
    protected String type;

	transient String mtMessage;
    transient Date mtLastRetryTime;
    transient String mtStatus;
    transient EsmeEmsMt esmeEmsMt;    
    transient boolean select;
    transient String groupName;    
    
    
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getMtMessage() {
		return mtMessage;
	}

	public void setMtMessage(String mtMessage) {
		this.mtMessage = mtMessage;
	}

	public Date getMtLastRetryTime() {
		return mtLastRetryTime;
	}

	public void setMtLastRetryTime(Date mtLastRetryTime) {
		this.mtLastRetryTime = mtLastRetryTime;
	}

	public String getMtStatus() {
		return mtStatus;
	}

	public void setMtStatus(String mtStatus) {
		this.mtStatus = mtStatus;
	}

	public EsmeEmsMt getEsmeEmsMt() {
		return esmeEmsMt;
	}

	public void setEsmeEmsMt(EsmeEmsMt esmeEmsMt) {
		this.esmeEmsMt = esmeEmsMt;
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
     * Gets the value of the esmeServicesParent property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeServices }
     *     
     */
    public EsmeServices getEsmeServicesParent() {
        return esmeServicesParent;
    }

    /**
     * Sets the value of the esmeServicesParent property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeServices }
     *     
     */
    public void setEsmeServicesParent(EsmeServices value) {
        this.esmeServicesParent = value;
    }

    /**
     * Gets the value of the esmeServicesRoot property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeServices }
     *     
     */
    public EsmeServices getEsmeServicesRoot() {
        return esmeServicesRoot;
    }

    /**
     * Sets the value of the esmeServicesRoot property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeServices }
     *     
     */
    public void setEsmeServicesRoot(EsmeServices value) {
        this.esmeServicesRoot = value;
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
     * Gets the value of the lastUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdate() {
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
    public void setLastUpdate(XMLGregorianCalendar value) {
        this.lastUpdate = value;
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
     * Gets the value of the moId property.
     * 
     */
    public long getMoId() {
        return moId;
    }

    /**
     * Sets the value of the moId property.
     * 
     */
    public void setMoId(long value) {
        this.moId = value;
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
     * Gets the value of the protocal property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProtocal() {
        return protocal;
    }

    /**
     * Sets the value of the protocal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProtocal(Integer value) {
        this.protocal = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		// object must be Test at this point
		EsmeEmsMo cp = (EsmeEmsMo) obj;
		return moId == cp.getMoId();
	}	
}
