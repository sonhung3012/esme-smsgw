
package com.fis.esme.emsmt.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Tue Sep 30 18:00:58 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "update", namespace = "http://emsmt.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "update", namespace = "http://emsmt.esme.fis.com/")

public class Update {

    @XmlElement(name = "arg0")
    private com.fis.esme.persistence.EsmeEmsMt arg0;

    public com.fis.esme.persistence.EsmeEmsMt getArg0() {
        return this.arg0;
    }

    public void setArg0(com.fis.esme.persistence.EsmeEmsMt newArg0)  {
        this.arg0 = newArg0;
    }

}

