
package com.fis.esme.subscriber.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Tue Sep 30 17:20:29 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "findAllWithOrderPaging", namespace = "http://subscriber.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAllWithOrderPaging", namespace = "http://subscriber.esme.fis.com/", propOrder = {"arg0", "arg1", "arg2", "arg3", "arg4", "arg5"})

public class FindAllWithOrderPaging {

    @XmlElement(name = "arg0")
    private com.fis.esme.persistence.EsmeSubscriber arg0;
    @XmlElement(name = "arg1")
    private java.lang.String arg1;
    @XmlElement(name = "arg2")
    private boolean arg2;
    @XmlElement(name = "arg3")
    private int arg3;
    @XmlElement(name = "arg4")
    private int arg4;
    @XmlElement(name = "arg5")
    private boolean arg5;

    public com.fis.esme.persistence.EsmeSubscriber getArg0() {
        return this.arg0;
    }

    public void setArg0(com.fis.esme.persistence.EsmeSubscriber newArg0)  {
        this.arg0 = newArg0;
    }

    public java.lang.String getArg1() {
        return this.arg1;
    }

    public void setArg1(java.lang.String newArg1)  {
        this.arg1 = newArg1;
    }

    public boolean getArg2() {
        return this.arg2;
    }

    public void setArg2(boolean newArg2)  {
        this.arg2 = newArg2;
    }

    public int getArg3() {
        return this.arg3;
    }

    public void setArg3(int newArg3)  {
        this.arg3 = newArg3;
    }

    public int getArg4() {
        return this.arg4;
    }

    public void setArg4(int newArg4)  {
        this.arg4 = newArg4;
    }

    public boolean getArg5() {
        return this.arg5;
    }

    public void setArg5(boolean newArg5)  {
        this.arg5 = newArg5;
    }

}

