
package com.fis.esme.isdnprefix.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Mon Dec 02 15:49:29 ICT 2013
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "addResponse", namespace = "http://isdnprefix.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addResponse", namespace = "http://isdnprefix.esme.fis.com/")

public class AddResponse {

    @XmlElement(name = "return")
    private java.lang.Long _return;

    public java.lang.Long getReturn() {
        return this._return;
    }

    public void setReturn(java.lang.Long new_return)  {
        this._return = new_return;
    }

}

