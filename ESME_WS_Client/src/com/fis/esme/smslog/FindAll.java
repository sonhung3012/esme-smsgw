
package com.fis.esme.smslog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.SubSearchDetail;


/**
 * <p>Java class for findAll complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="findAll">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://smslog.esme.fis.com/}subSearchDetail" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://smslog.esme.fis.com/}esmeSmsLog" minOccurs="0"/>
 *         &lt;element name="arg2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arg3" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="arg4" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="arg5" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="arg6" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAll", propOrder = {
    "arg0",
    "arg1",
    "arg2",
    "arg3",
    "arg4",
    "arg5",
    "arg6"
})
public class FindAll {

    protected SubSearchDetail arg0;
    protected EsmeSmsLog arg1;
    protected String arg2;
    protected boolean arg3;
    protected int arg4;
    protected int arg5;
    protected boolean arg6;

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
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg2() {
        return arg2;
    }

    /**
     * Sets the value of the arg2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg2(String value) {
        this.arg2 = value;
    }

    /**
     * Gets the value of the arg3 property.
     * 
     */
    public boolean isArg3() {
        return arg3;
    }

    /**
     * Sets the value of the arg3 property.
     * 
     */
    public void setArg3(boolean value) {
        this.arg3 = value;
    }

    /**
     * Gets the value of the arg4 property.
     * 
     */
    public int getArg4() {
        return arg4;
    }

    /**
     * Sets the value of the arg4 property.
     * 
     */
    public void setArg4(int value) {
        this.arg4 = value;
    }

    /**
     * Gets the value of the arg5 property.
     * 
     */
    public int getArg5() {
        return arg5;
    }

    /**
     * Sets the value of the arg5 property.
     * 
     */
    public void setArg5(int value) {
        this.arg5 = value;
    }

    /**
     * Gets the value of the arg6 property.
     * 
     */
    public boolean isArg6() {
        return arg6;
    }

    /**
     * Sets the value of the arg6 property.
     * 
     */
    public void setArg6(boolean value) {
        this.arg6 = value;
    }

}
