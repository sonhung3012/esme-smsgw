
package com.fis.esme.subscriberdt.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Thu Oct 16 17:09:33 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "findSubcribersByGroupResponse", namespace = "http://subscriberdt.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findSubcribersByGroupResponse", namespace = "http://subscriberdt.esme.fis.com/")

public class FindSubcribersByGroupResponse {

    @XmlElement(name = "return")
    private java.util.List<com.fis.esme.persistence.Subscriber> _return;

    public java.util.List<com.fis.esme.persistence.Subscriber> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<com.fis.esme.persistence.Subscriber> new_return)  {
        this._return = new_return;
    }

}

