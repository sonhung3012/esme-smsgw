
package com.fis.esme.smscparam.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Sat Dec 14 11:33:15 ICT 2013
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "editDetail", namespace = "http://smscparam.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "editDetail", namespace = "http://smscparam.esme.fis.com/", propOrder = {"arg0", "arg1"})

public class EditDetail {

    @XmlElement(name = "arg0")
    private com.fis.esme.persistence.EsmeSmscParam arg0;
    @XmlElement(name = "arg1")
    private com.fis.esme.persistence.EsmeSmscParam arg1;

    public com.fis.esme.persistence.EsmeSmscParam getArg0() {
        return this.arg0;
    }

    public void setArg0(com.fis.esme.persistence.EsmeSmscParam newArg0)  {
        this.arg0 = newArg0;
    }

    public com.fis.esme.persistence.EsmeSmscParam getArg1() {
        return this.arg1;
    }

    public void setArg1(com.fis.esme.persistence.EsmeSmscParam newArg1)  {
        this.arg1 = newArg1;
    }

}

