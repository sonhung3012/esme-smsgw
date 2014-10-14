
package com.fis.esme.smscparam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.persistence.EsmeSmscParam;
import com.fis.esme.persistence.SearchEntity;


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
 *         &lt;element name="arg0" type="{http://smscparam.esme.fis.com/}searchEntity" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://smscparam.esme.fis.com/}esmeSmscParam" minOccurs="0"/>
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

    protected SearchEntity arg0;
    protected EsmeSmscParam arg1;
    protected boolean arg2;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link SearchEntity }
     *     
     */
    public SearchEntity getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchEntity }
     *     
     */
    public void setArg0(SearchEntity value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeSmscParam }
     *     
     */
    public EsmeSmscParam getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeSmscParam }
     *     
     */
    public void setArg1(EsmeSmscParam value) {
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
