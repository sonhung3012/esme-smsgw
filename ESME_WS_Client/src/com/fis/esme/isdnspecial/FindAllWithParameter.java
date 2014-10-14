
package com.fis.esme.isdnspecial;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fis.esme.persistence.EsmeIsdnSpecial;
import com.fis.esme.persistence.SearchEntity;


/**
 * <p>Java class for findAllWithParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="findAllWithParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://isdnspecial.esme.fis.com/}searchEntity" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://isdnspecial.esme.fis.com/}esmeIsdnSpecial" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAllWithParameter", propOrder = {
    "arg0",
    "arg1"
})
public class FindAllWithParameter {

    protected SearchEntity arg0;
    protected EsmeIsdnSpecial arg1;

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
     *     {@link EsmeIsdnSpecial }
     *     
     */
    public EsmeIsdnSpecial getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeIsdnSpecial }
     *     
     */
    public void setArg1(EsmeIsdnSpecial value) {
        this.arg1 = value;
    }

}
