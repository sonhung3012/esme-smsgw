
package com.fis.esme.smslog.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Tue Nov 25 10:20:52 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "findAllNoPaperResponse", namespace = "http://smslog.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAllNoPaperResponse", namespace = "http://smslog.esme.fis.com/")

public class FindAllNoPaperResponse {

    @XmlElement(name = "return")
    private java.util.List<com.fis.esme.persistence.EsmeSmsLog> _return;

    public java.util.List<com.fis.esme.persistence.EsmeSmsLog> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<com.fis.esme.persistence.EsmeSmsLog> new_return)  {
        this._return = new_return;
    }

}

