
package com.fis.esme.cpsummary.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Thu Dec 19 16:33:36 ICT 2013
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "findAllByDateAndIdResponse", namespace = "http://cpsummary.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAllByDateAndIdResponse", namespace = "http://cpsummary.esme.fis.com/")

public class FindAllByDateAndIdResponse {

    @XmlElement(name = "return")
    private java.util.List<com.fis.esme.persistence.EsmeDailyCpSummary> _return;

    public java.util.List<com.fis.esme.persistence.EsmeDailyCpSummary> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<com.fis.esme.persistence.EsmeDailyCpSummary> new_return)  {
        this._return = new_return;
    }

}

