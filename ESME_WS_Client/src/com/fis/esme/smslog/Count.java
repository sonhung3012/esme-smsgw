
package com.fis.esme.smslog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.SubSearchDetail;


/**
 * <p>Java class for count complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="count">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://smslog.esme.fis.com/}subSearchDetail" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://smslog.esme.fis.com/}esmeSmsLog" minOccurs="0"/>
 *         &lt;element name="arg2" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "count", propOrder = {
    "arg0",
    "arg1",
    "arg2"
})
public class Count {

    protected SubSearchDetail arg0;
    protected EsmeSmsLog arg1;
    protected boolean arg2;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link SubSearchDetail }
     *     
     */
    public SubSearchDetail getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubSearchDetail }
     *     
     */
    public void setArg0(SubSearchDetail value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeSmsLog }
     *     
     */
    public EsmeSmsLog getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeSmsLog }
     *     
     */
    public void setArg1(EsmeSmsLog value) {
        this.arg1 = value;
    }

    /**
     * Gets the value of the arg2 property.
     * 
     */
    public boolean isArg2() {
        return arg2;
    }

    /**
     * Sets the value of the arg2 property.
     * 
     */
    public void setArg2(boolean value) {
        this.arg2 = value;
    }

}
