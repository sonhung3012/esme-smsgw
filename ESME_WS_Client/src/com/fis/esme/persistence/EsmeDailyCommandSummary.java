
package com.fis.esme.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esmeDailyCommandSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeDailyCommandSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="commandId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="summaryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="totalSms" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsAckFail" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsIncome" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsIncomeCpReceived" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsIncomeFail" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsIncomeHandler" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsIncomeHasResponse" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsIncomeIncorrect" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsMobileNoReveived" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsMobileReceived" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsOutbound" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsSubmitFail" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalSmsWaitingDelivery" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esmeDailyCommandSummary", propOrder = {
    "commandId",
    "summaryDate",
    "totalSms",
    "totalSmsAckFail",
    "totalSmsIncome",
    "totalSmsIncomeCpReceived",
    "totalSmsIncomeFail",
    "totalSmsIncomeHandler",
    "totalSmsIncomeHasResponse",
    "totalSmsIncomeIncorrect",
    "totalSmsMobileNoReveived",
    "totalSmsMobileReceived",
    "totalSmsOutbound",
    "totalSmsSubmitFail",
    "totalSmsWaitingDelivery"
})
public class EsmeDailyCommandSummary {

    protected Long commandId;
    @XmlSchemaType(name = "dateTime")
    protected java.util.Date summaryDate;
    protected Long totalSms;
    protected Long totalSmsAckFail;
    protected Long totalSmsIncome;
    protected Long totalSmsIncomeCpReceived;
    protected Long totalSmsIncomeFail;
    protected Long totalSmsIncomeHandler;
    protected Long totalSmsIncomeHasResponse;
    protected Long totalSmsIncomeIncorrect;
    protected Long totalSmsMobileNoReveived;
    protected Long totalSmsMobileReceived;
    protected Long totalSmsOutbound;
    protected Long totalSmsSubmitFail;
    protected Long totalSmsWaitingDelivery;

    /**
     * Gets the value of the commandId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCommandId() {
        return commandId;
    }

    /**
     * Sets the value of the commandId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCommandId(Long value) {
        this.commandId = value;
    }

    /**
     * Gets the value of the summaryDate property.
     * 
     * @return
     *     possible object is
     *     {@link java.util.Date }
     *     
     */
    public java.util.Date getSummaryDate() {
        return summaryDate;
    }

    /**
     * Sets the value of the summaryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.util.Date }
     *     
     */
    public void setSummaryDate(java.util.Date value) {
        this.summaryDate = value;
    }

    /**
     * Gets the value of the totalSms property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSms() {
        return totalSms;
    }

    /**
     * Sets the value of the totalSms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSms(Long value) {
        this.totalSms = value;
    }

    /**
     * Gets the value of the totalSmsAckFail property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsAckFail() {
        return totalSmsAckFail;
    }

    /**
     * Sets the value of the totalSmsAckFail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsAckFail(Long value) {
        this.totalSmsAckFail = value;
    }

    /**
     * Gets the value of the totalSmsIncome property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsIncome() {
        return totalSmsIncome;
    }

    /**
     * Sets the value of the totalSmsIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsIncome(Long value) {
        this.totalSmsIncome = value;
    }

    /**
     * Gets the value of the totalSmsIncomeCpReceived property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsIncomeCpReceived() {
        return totalSmsIncomeCpReceived;
    }

    /**
     * Sets the value of the totalSmsIncomeCpReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsIncomeCpReceived(Long value) {
        this.totalSmsIncomeCpReceived = value;
    }

    /**
     * Gets the value of the totalSmsIncomeFail property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsIncomeFail() {
        return totalSmsIncomeFail;
    }

    /**
     * Sets the value of the totalSmsIncomeFail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsIncomeFail(Long value) {
        this.totalSmsIncomeFail = value;
    }

    /**
     * Gets the value of the totalSmsIncomeHandler property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsIncomeHandler() {
        return totalSmsIncomeHandler;
    }

    /**
     * Sets the value of the totalSmsIncomeHandler property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsIncomeHandler(Long value) {
        this.totalSmsIncomeHandler = value;
    }

    /**
     * Gets the value of the totalSmsIncomeHasResponse property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsIncomeHasResponse() {
        return totalSmsIncomeHasResponse;
    }

    /**
     * Sets the value of the totalSmsIncomeHasResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsIncomeHasResponse(Long value) {
        this.totalSmsIncomeHasResponse = value;
    }

    /**
     * Gets the value of the totalSmsIncomeIncorrect property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsIncomeIncorrect() {
        return totalSmsIncomeIncorrect;
    }

    /**
     * Sets the value of the totalSmsIncomeIncorrect property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsIncomeIncorrect(Long value) {
        this.totalSmsIncomeIncorrect = value;
    }

    /**
     * Gets the value of the totalSmsMobileNoReveived property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsMobileNoReveived() {
        return totalSmsMobileNoReveived;
    }

    /**
     * Sets the value of the totalSmsMobileNoReveived property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsMobileNoReveived(Long value) {
        this.totalSmsMobileNoReveived = value;
    }

    /**
     * Gets the value of the totalSmsMobileReceived property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsMobileReceived() {
        return totalSmsMobileReceived;
    }

    /**
     * Sets the value of the totalSmsMobileReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsMobileReceived(Long value) {
        this.totalSmsMobileReceived = value;
    }

    /**
     * Gets the value of the totalSmsOutbound property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsOutbound() {
        return totalSmsOutbound;
    }

    /**
     * Sets the value of the totalSmsOutbound property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsOutbound(Long value) {
        this.totalSmsOutbound = value;
    }

    /**
     * Gets the value of the totalSmsSubmitFail property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsSubmitFail() {
        return totalSmsSubmitFail;
    }

    /**
     * Sets the value of the totalSmsSubmitFail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsSubmitFail(Long value) {
        this.totalSmsSubmitFail = value;
    }

    /**
     * Gets the value of the totalSmsWaitingDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalSmsWaitingDelivery() {
        return totalSmsWaitingDelivery;
    }

    /**
     * Sets the value of the totalSmsWaitingDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalSmsWaitingDelivery(Long value) {
        this.totalSmsWaitingDelivery = value;
    }

}
