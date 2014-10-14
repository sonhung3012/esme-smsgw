
package com.fis.esme.commandsummary;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.1
 * 2013-12-20T10:48:11.549+07:00
 * Generated source version: 2.4.1
 */

@WebFault(name = "Exception", targetNamespace = "http://commandsummary.esme.fis.com/")
public class Exception_Exception extends java.lang.Exception {
    
    private com.fis.esme.commandsummary.Exception exception;

    public Exception_Exception() {
        super();
    }
    
    public Exception_Exception(String message) {
        super(message);
    }
    
    public Exception_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Exception_Exception(String message, com.fis.esme.commandsummary.Exception exception) {
        super(message);
        this.exception = exception;
    }

    public Exception_Exception(String message, com.fis.esme.commandsummary.Exception exception, Throwable cause) {
        super(message, cause);
        this.exception = exception;
    }

    public com.fis.esme.commandsummary.Exception getFaultInfo() {
        return this.exception;
    }
}
