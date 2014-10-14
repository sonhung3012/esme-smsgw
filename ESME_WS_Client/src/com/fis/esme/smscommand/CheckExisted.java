
package com.fis.esme.smscommand;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.persistence.EsmeSmsCommand;


/**
 * <p>Java class for checkExisted complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="checkExisted">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://smscommand.esme.fis.com/}esmeSmsCommand" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkExisted", propOrder = {
    "arg0"
})
public class CheckExisted {

    protected EsmeSmsCommand arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeSmsCommand }
     *     
     */
    public EsmeSmsCommand getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeSmsCommand }
     *     
     */
    public void setArg0(EsmeSmsCommand value) {
        this.arg0 = value;
    }

}
