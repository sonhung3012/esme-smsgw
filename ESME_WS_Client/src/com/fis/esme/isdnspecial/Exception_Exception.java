
package com.fis.esme.isdnspecial;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.1
 * 2014-02-26T15:10:58.116+07:00
 * Generated source version: 2.4.1
 */

@WebFault(name = "Exception", targetNamespace = "http://isdnspecial.esme.fis.com/")
public class Exception_Exception extends java.lang.Exception {
    
    private com.fis.esme.isdnspecial.Exception exception;

    public Exception_Exception() {
        super();
    }
    
    public Exception_Exception(String message) {
        super(message);
    }
    
    public Exception_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Exception_Exception(String message, com.fis.esme.isdnspecial.Exception exception) {
        super(message);
        this.exception = exception;
    }

    public Exception_Exception(String message, com.fis.esme.isdnspecial.Exception exception, Throwable cause) {
        super(message, cause);
        this.exception = exception;
    }

    public com.fis.esme.isdnspecial.Exception getFaultInfo() {
        return this.exception;
    }
}
