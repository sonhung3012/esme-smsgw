
package com.fis.esme.messagecontent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.persistence.EsmeMessageContent;


/**
 * <p>Java class for findByMessageIdAndLanguageIdResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="findByMessageIdAndLanguageIdResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://messagecontent.esme.fis.com/}esmeMessageContent" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findByMessageIdAndLanguageIdResponse", propOrder = {
    "_return"
})
public class FindByMessageIdAndLanguageIdResponse {

    @XmlElement(name = "return")
    protected EsmeMessageContent _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeMessageContent }
     *     
     */
    public EsmeMessageContent getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeMessageContent }
     *     
     */
    public void setReturn(EsmeMessageContent value) {
        this._return = value;
    }

}
