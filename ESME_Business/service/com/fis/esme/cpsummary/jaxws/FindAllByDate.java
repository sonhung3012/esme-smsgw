
package com.fis.esme.cpsummary.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Thu Dec 19 16:33:37 ICT 2013
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "findAllByDate", namespace = "http://cpsummary.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAllByDate", namespace = "http://cpsummary.esme.fis.com/", propOrder = {"arg0", "arg1"})

public class FindAllByDate {

    @XmlElement(name = "arg0")
    private java.util.Date arg0;
    @XmlElement(name = "arg1")
    private java.util.Date arg1;

    public java.util.Date getArg0() {
        return this.arg0;
    }

    public void setArg0(java.util.Date newArg0)  {
        this.arg0 = newArg0;
    }

    public java.util.Date getArg1() {
        return this.arg1;
    }

    public void setArg1(java.util.Date newArg1)  {
        this.arg1 = newArg1;
    }

}

