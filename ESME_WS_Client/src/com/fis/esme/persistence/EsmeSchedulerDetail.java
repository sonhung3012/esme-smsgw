
package com.fis.esme.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



/**
 * <p>Java class for esmeSchedulerDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esmeSchedulerDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esmeScheduler" type="{http://schedulerdetail.esme.fis.com/}esmeScheduler" minOccurs="0"/>
 *         &lt;element name="schedulerDetailId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esmeSchedulerDetail", propOrder = {
    "date",
    "esmeScheduler",
    "schedulerDetailId"
})
public class EsmeSchedulerDetail {

    protected String date;
    protected EsmeScheduler esmeScheduler;
    protected long schedulerDetailId;

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the esmeScheduler property.
     * 
     * @return
     *     possible object is
     *     {@link EsmeScheduler }
     *     
     */
    public EsmeScheduler getEsmeScheduler() {
        return esmeScheduler;
    }

    /**
     * Sets the value of the esmeScheduler property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsmeScheduler }
     *     
     */
    public void setEsmeScheduler(EsmeScheduler value) {
        this.esmeScheduler = value;
    }

    /**
     * Gets the value of the schedulerDetailId property.
     * 
     */
    public long getSchedulerDetailId() {
        return schedulerDetailId;
    }

    /**
     * Sets the value of the schedulerDetailId property.
     * 
     */
    public void setSchedulerDetailId(long value) {
        this.schedulerDetailId = value;
    }
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass()))
		{
			return false;
		}
		// object must be Test at this point
		EsmeSchedulerDetail schedulerdetail = (EsmeSchedulerDetail) obj;
		return schedulerDetailId == schedulerdetail.getSchedulerDetailId();
	}
}
