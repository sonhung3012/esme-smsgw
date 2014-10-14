package com.fis.esme.smsrouting;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2013-12-04T16:42:42.372+07:00
 * Generated source version: 2.4.1
 * 
 */
@WebService(targetNamespace = "http://smsrouting.esme.fis.com/", name = "SmsRoutingTransferer")
@XmlSeeAlso({ObjectFactory.class})
public interface SmsRoutingTransferer {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "findAllWithoutParameter", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.FindAllWithoutParameter")
    @WebMethod
    @ResponseWrapper(localName = "findAllWithoutParameterResponse", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.FindAllWithoutParameterResponse")
    public java.util.List<com.fis.esme.persistence.EsmeSmsRouting> findAllWithoutParameter() throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "countAll", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.CountAll")
    @WebMethod
    @ResponseWrapper(localName = "countAllResponse", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.CountAllResponse")
    public int countAll();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkConstraints", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.CheckConstraints")
    @WebMethod
    @ResponseWrapper(localName = "checkConstraintsResponse", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.CheckConstraintsResponse")
    public boolean checkConstraints(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Long arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "findAllWithParameter", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.FindAllWithParameter")
    @WebMethod
    @ResponseWrapper(localName = "findAllWithParameterResponse", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.FindAllWithParameterResponse")
    public java.util.List<com.fis.esme.persistence.EsmeSmsRouting> findAllWithParameter(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.SearchEntity arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        com.fis.esme.persistence.EsmeSmsRouting arg1
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "count", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.Count")
    @WebMethod
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.CountResponse")
    public int count(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.SearchEntity arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        com.fis.esme.persistence.EsmeSmsRouting arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        boolean arg2
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkExisted", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.CheckExisted")
    @WebMethod
    @ResponseWrapper(localName = "checkExistedResponse", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.CheckExistedResponse")
    public int checkExisted(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.SearchEntity arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        com.fis.esme.persistence.EsmeSmsRouting arg1
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.Add")
    @WebMethod
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.AddResponse")
    public java.lang.Long add(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeSmsRouting arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "findAllWithOrderPaging", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.FindAllWithOrderPaging")
    @WebMethod
    @ResponseWrapper(localName = "findAllWithOrderPagingResponse", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.FindAllWithOrderPagingResponse")
    public java.util.List<com.fis.esme.persistence.EsmeSmsRouting> findAllWithOrderPaging(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.SearchEntity arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        com.fis.esme.persistence.EsmeSmsRouting arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        boolean arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        int arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        int arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        boolean arg6
    ) throws Exception_Exception;

    @RequestWrapper(localName = "update", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.Update")
    @WebMethod
    @ResponseWrapper(localName = "updateResponse", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.UpdateResponse")
    public void update(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeSmsRouting arg0
    ) throws Exception_Exception;

    @RequestWrapper(localName = "delete", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.Delete")
    @WebMethod
    @ResponseWrapper(localName = "deleteResponse", targetNamespace = "http://smsrouting.esme.fis.com/", className = "com.fis.esme.smsrouting.DeleteResponse")
    public void delete(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeSmsRouting arg0
    ) throws Exception_Exception;
}
