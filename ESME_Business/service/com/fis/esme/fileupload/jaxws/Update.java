
package com.fis.esme.fileupload.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Sat Dec 14 11:26:02 ICT 2013
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "update", namespace = "http://fileupload.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "update", namespace = "http://fileupload.esme.fis.com/")

public class Update {

    @XmlElement(name = "arg0")
    private com.fis.esme.persistence.EsmeFileUpload arg0;

    public com.fis.esme.persistence.EsmeFileUpload getArg0() {
        return this.arg0;
    }

    public void setArg0(com.fis.esme.persistence.EsmeFileUpload newArg0)  {
        this.arg0 = newArg0;
    }

}

