
package com.fis.esme.language.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Thu Nov 28 15:04:24 ICT 2013
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "edit", namespace = "http://language.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "edit", namespace = "http://language.esme.fis.com/")

public class Edit {

    @XmlElement(name = "arg0")
    private com.fis.esme.persistence.EsmeLanguage arg0;

    public com.fis.esme.persistence.EsmeLanguage getArg0() {
        return this.arg0;
    }

    public void setArg0(com.fis.esme.persistence.EsmeLanguage newArg0)  {
        this.arg0 = newArg0;
    }

}

