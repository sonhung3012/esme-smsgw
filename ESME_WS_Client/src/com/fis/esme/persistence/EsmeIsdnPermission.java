
package com.fis.esme.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esmeIsdnPermission complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeIsdnPermission">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esmeIsdnSpecial" type="{http://isdnpermission.esme.fis.com/}esmeIsdnSpecial" minOccurs="0"/>
 *         &lt;element name="esmeServices" type="{http://isdnpermission.esme.fis.com/}esmeServices" minOccurs="0"/>
 *         &lt;element name="permissionId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esmeIsdnPermission", propOrder = {
    "esmeIsdnSpecial",
    "esmeServices",
    "permissionId",
    "type"
})
public class EsmeIsdnPermission {

    protected EsmeIsdnSpecial esmeIsdnSpecial;
    protected EsmeServices esmeServices;
    protected long permissionId;
    protected String type;

    /**
     * Gets the value of the esmeIsdnSpecial property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeIsdnSpecial }
     *     
     */
    public EsmeIsdnSpecial getEsmeIsdnSpecial() {
        return esmeIsdnSpecial;
    }

    /**
     * Sets the value of the esmeIsdnSpecial property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeIsdnSpecial }
     *     
     */
    public void setEsmeIsdnSpecial(EsmeIsdnSpecial value) {
        this.esmeIsdnSpecial = value;
    }

    /**
     * Gets the value of the esmeServices property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeServices }
     *     
     */
    public EsmeServices getEsmeServices() {
        return esmeServices;
    }

    /**
     * Sets the value of the esmeServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeServices }
     *     
     */
    public void setEsmeServices(EsmeServices value) {
        this.esmeServices = value;
    }

    /**
     * Gets the value of the permissionId property.
     * 
     */
    public long getPermissionId() {
        return permissionId;
    }

    /**
     * Sets the value of the permissionId property.
     * 
     */
    public void setPermissionId(long value) {
        this.permissionId = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
