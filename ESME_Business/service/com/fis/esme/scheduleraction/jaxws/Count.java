
package com.fis.esme.scheduleraction.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Thu Oct 02 14:53:03 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "count", namespace = "http://scheduleraction.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "count", namespace = "http://scheduleraction.esme.fis.com/", propOrder = {"arg0", "arg1"})

public class Count {

    @XmlElement(name = "arg0")
    private com.fis.esme.persistence.EsmeSchedulerAction arg0;
    @XmlElement(name = "arg1")
    private boolean arg1;

    public com.fis.esme.persistence.EsmeSchedulerAction getArg0() {
        return this.arg0;
    }

    public void setArg0(com.fis.esme.persistence.EsmeSchedulerAction newArg0)  {
        this.arg0 = newArg0;
    }

    public boolean getArg1() {
        return this.arg1;
    }

    public void setArg1(boolean newArg1)  {
        this.arg1 = newArg1;
    }

}

