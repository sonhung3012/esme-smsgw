
package com.fis.esme.cp.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Fri Nov 29 10:00:31 ICT 2013
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "checkExistedResponse", namespace = "http://cp.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkExistedResponse", namespace = "http://cp.esme.fis.com/")

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

