
package com.fis.esme.subgroups.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Fri Oct 10 09:13:09 ICT 2014
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "checkExistedResponse", namespace = "http://subgroups.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkExistedResponse", namespace = "http://subgroups.esme.fis.com/")

public class CheckExistedResponse {

    @XmlElement(name = "return")
    private int _return;

    public int getReturn() {
        return this._return;
    }

    public void setReturn(int new_return)  {
        this._return = new_return;
    }

}

