
package com.fis.esme.isdnprefix.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Mon Nov 24 16:01:35 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "delete", namespace = "http://isdnprefix.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delete", namespace = "http://isdnprefix.esme.fis.com/")

public class Delete {

    @XmlElement(name = "arg0")
    private com.fis.esme.persistence.EsmeIsdnPrefix arg0;

    public com.fis.esme.persistence.EsmeIsdnPrefix getArg0() {
        return this.arg0;
    }

    public void setArg0(com.fis.esme.persistence.EsmeIsdnPrefix newArg0)  {
        this.arg0 = newArg0;
    }

}

