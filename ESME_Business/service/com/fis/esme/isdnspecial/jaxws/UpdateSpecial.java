
package com.fis.esme.isdnspecial.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Wed Feb 26 15:09:28 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "updateSpecial", namespace = "http://isdnspecial.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateSpecial", namespace = "http://isdnspecial.esme.fis.com/", propOrder = {"arg0", "arg1"})

public class UpdateSpecial {

    @XmlElement(name = "arg0")
    private com.fis.esme.persistence.EsmeIsdnSpecial arg0;
    @XmlElement(name = "arg1")
    private java.lang.String arg1;

    public com.fis.esme.persistence.EsmeIsdnSpecial getArg0() {
        return this.arg0;
    }

    public void setArg0(com.fis.esme.persistence.EsmeIsdnSpecial newArg0)  {
        this.arg0 = newArg0;
    }

    public java.lang.String getArg1() {
        return this.arg1;
    }

    public void setArg1(java.lang.String newArg1)  {
        this.arg1 = newArg1;
    }

}

