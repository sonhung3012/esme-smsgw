
package com.fis.esme.emsmt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.persistence.EsmeEmsMt;


/**
 * <p>Java class for findByMtIDResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="findByMtIDResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://emsmt.esme.fis.com/}esmeEmsMt" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findByMtIDResponse", propOrder = {
    "_return"
})
public class FindByMtIDResponse {

    @XmlElement(name = "return")
    protected EsmeEmsMt _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeEmsMt }
     *     
     */
    public EsmeEmsMt getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeEmsMt }
     *     
     */
    public void setReturn(EsmeEmsMt value) {
        this._return = value;
    }

}
