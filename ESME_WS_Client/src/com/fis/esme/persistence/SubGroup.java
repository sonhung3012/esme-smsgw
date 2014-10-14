
package com.fis.esme.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for subGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="subGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="subId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subGroup", propOrder = {
    "groupId",
    "subId"
})
public class SubGroup {

    protected long groupId;
    protected long subId;

    /**
     * Gets the value of the groupId property.
     * 
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * Sets the value of the groupId property.
     * 
     */
    public void setGroupId(long value) {
        this.groupId = value;
    }

    /**
     * Gets the value of the subId property.
     * 
     */
    public long getSubId() {
        return subId;
    }

    /**
     * Sets the value of the subId property.
     * 
     */
    public void setSubId(long value) {
        this.subId = value;
    }

}
