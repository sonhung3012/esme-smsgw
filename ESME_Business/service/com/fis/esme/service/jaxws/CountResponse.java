
package com.fis.esme.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.4.1
 * Wed Nov 27 16:35:53 ICT 2013
 * Generated source version: 2.4.1
 */

@XmlRootElement(name = "countResponse", namespace = "http://service.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "countResponse", namespace = "http://service.esme.fis.com/")

public class CountResponse {

    @XmlElement(name = "return")
    private int _return;

    public int getReturn() {
        return this._return;
    }

    public void setReturn(int new_return)  {
        this._return = new_return;
    }

}

