
package com.fis.esme.groupsdt.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Fri Oct 10 09:08:45 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "update", namespace = "http://groupsdt.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "update", namespace = "http://groupsdt.esme.fis.com/")

public class Update {

    @XmlElement(name = "arg0")
    private com.fis.esme.persistence.Groups arg0;

    public com.fis.esme.persistence.Groups getArg0() {
        return this.arg0;
    }

    public void setArg0(com.fis.esme.persistence.Groups newArg0)  {
        this.arg0 = newArg0;
    }

}

