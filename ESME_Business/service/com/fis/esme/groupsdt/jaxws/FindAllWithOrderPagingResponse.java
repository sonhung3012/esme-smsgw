
package com.fis.esme.groupsdt.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Fri Oct 10 09:08:45 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "findAllWithOrderPagingResponse", namespace = "http://groupsdt.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAllWithOrderPagingResponse", namespace = "http://groupsdt.esme.fis.com/")

public class FindAllWithOrderPagingResponse {

    @XmlElement(name = "return")
    private java.util.List<com.fis.esme.persistence.Groups> _return;

    public java.util.List<com.fis.esme.persistence.Groups> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<com.fis.esme.persistence.Groups> new_return)  {
        this._return = new_return;
    }

}

