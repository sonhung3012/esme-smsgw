
package com.fis.esme.groups.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Fri Oct 10 09:00:21 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "checkConstraintsResponse", namespace = "http://groups.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkConstraintsResponse", namespace = "http://groups.esme.fis.com/")

public class CheckConstraintsResponse {

    @XmlElement(name = "return")
    private boolean _return;

    public boolean getReturn() {
        return this._return;
    }

    public void setReturn(boolean new_return)  {
        this._return = new_return;
    }

}

