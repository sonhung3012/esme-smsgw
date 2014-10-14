
package com.fis.esme.language;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.persistence.EsmeLanguage;


/**
 * <p>Java class for getDefaultLanguageResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDefaultLanguageResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://language.esme.fis.com/}esmeLanguage" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDefaultLanguageResponse", propOrder = {
    "_return"
})
public class GetDefaultLanguageResponse {

    @XmlElement(name = "return")
    protected EsmeLanguage _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeLanguage }
     *     
     */
    public EsmeLanguage getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeLanguage }
     *     
     */
    public void setReturn(EsmeLanguage value) {
        this._return = value;
    }

}
