
package com.fis.esme.apparam;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.1
 * 2013-11-28T16:28:00.488+07:00
 * Generated source version: 2.4.1
 */

@WebFault(name = "Exception", targetNamespace = "http://apparam.esme.fis.com/")
public class Exception_Exception extends java.lang.Exception {
    
    private com.fis.esme.apparam.Exception exception;

    public Exception_Exception() {
        super();
    }
    
    public Exception_Exception(String message) {
        super(message);
    }
    
    public Exception_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Exception_Exception(String message, com.fis.esme.apparam.Exception exception) {
        super(message);
        this.exception = exception;
    }

    public Exception_Exception(String message, com.fis.esme.apparam.Exception exception, Throwable cause) {
        super(message, cause);
        this.exception = exception;
    }

    public com.fis.esme.apparam.Exception getFaultInfo() {
        return this.exception;
    }
}
