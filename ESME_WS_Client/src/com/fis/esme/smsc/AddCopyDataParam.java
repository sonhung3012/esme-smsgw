
package com.fis.esme.smsc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.persistence.EsmeSmsc;


/**
 * <p>Java class for addCopyDataParam complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addCopyDataParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://smsc.esme.fis.com/}esmeSmsc" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://smsc.esme.fis.com/}esmeSmsc" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addCopyDataParam", propOrder = {
    "arg0",
    "arg1"
})
public class AddCopyDataParam {

    protected EsmeSmsc arg0;
    protected EsmeSmsc arg1;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeSmsc }
     *     
     */
    public EsmeSmsc getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeSmsc }
     *     
     */
    public void setArg0(EsmeSmsc value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeSmsc }
     *     
     */
    public EsmeSmsc getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeSmsc }
     *     
     */
    public void setArg1(EsmeSmsc value) {
        this.arg1 = value;
    }

}
