
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

@XmlRootElement(name = "add", namespace = "http://subscriberdt.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "add", namespace = "http://subscriberdt.esme.fis.com/", propOrder = {"arg0", "arg1"})

public class Add {

    @XmlElement(name = "arg0")
    private com.fis.esme.persistence.Subscriber arg0;
    @XmlElement(name = "arg1")
    private long arg1;

    public com.fis.esme.persistence.Subscriber getArg0() {
        return this.arg0;
    }

    public void setArg0(com.fis.esme.persistence.Subscriber newArg0)  {
        this.arg0 = newArg0;
    }

    public long getArg1() {
        return this.arg1;
    }

    public void setArg1(long newArg1)  {
        this.arg1 = newArg1;
    }

}

