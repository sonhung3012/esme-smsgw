
package com.fis.esme.smscrouting.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Wed Dec 11 17:15:48 ICT 2013
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "checkConstraints", namespace = "http://smscrouting.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkConstraints", namespace = "http://smscrouting.esme.fis.com/")

public class CheckConstraints {

    @XmlElement(name = "arg0")
    private java.lang.Long arg0;

    public java.lang.Long getArg0() {
        return this.arg0;
    }

    public void setArg0(java.lang.Long newArg0)  {
        this.arg0 = newArg0;
    }

}

