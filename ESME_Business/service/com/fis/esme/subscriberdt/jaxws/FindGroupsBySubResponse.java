
package com.fis.esme.subscriberdt.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Thu Oct 16 12:16:29 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "findGroupsBySubResponse", namespace = "http://subscriberdt.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findGroupsBySubResponse", namespace = "http://subscriberdt.esme.fis.com/")

public class FindGroupsBySubResponse {

    @XmlElement(name = "return")
    private java.util.List<com.fis.esme.persistence.Groups> _return;

    public java.util.List<com.fis.esme.persistence.Groups> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<com.fis.esme.persistence.Groups> new_return)  {
        this._return = new_return;
    }

}

