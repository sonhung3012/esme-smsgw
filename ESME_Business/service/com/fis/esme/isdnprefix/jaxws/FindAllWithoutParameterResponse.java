
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

@XmlRootElement(name = "findAllWithoutParameterResponse", namespace = "http://isdnprefix.esme.fis.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAllWithoutParameterResponse", namespace = "http://isdnprefix.esme.fis.com/")

public class FindAllWithoutParameterResponse {

    @XmlElement(name = "return")
    private java.util.List<com.fis.esme.persistence.EsmeIsdnPrefix> _return;

    public java.util.List<com.fis.esme.persistence.EsmeIsdnPrefix> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<com.fis.esme.persistence.EsmeIsdnPrefix> new_return)  {
        this._return = new_return;
    }

}

