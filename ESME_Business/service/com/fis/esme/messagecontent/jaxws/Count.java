
package com.fis.esme.messagecontent.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Mon Dec 02 16:39:56 ICT 2013
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "count", namespace = "http://messagecontent.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "count", namespace = "http://messagecontent.esme.fis.com/", propOrder = {"arg0", "arg1"})

public class Count {

    @XmlElement(name = "arg0")
    private com.fis.esme.persistence.EsmeMessageContent arg0;
    @XmlElement(name = "arg1")
    private boolean arg1;

    public com.fis.esme.persistence.EsmeMessageContent getArg0() {
        return this.arg0;
    }

    public void setArg0(com.fis.esme.persistence.EsmeMessageContent newArg0)  {
        this.arg0 = newArg0;
    }

    public boolean getArg1() {
        return this.arg1;
    }

    public void setArg1(boolean newArg1)  {
        this.arg1 = newArg1;
    }

}

