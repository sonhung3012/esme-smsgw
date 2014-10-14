package com.fis.esme.subscriber;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2014-09-30T17:26:21.767+07:00
 * Generated source version: 2.4.1
 * 
 */
@WebService(targetNamespace = "http://subscriber.esme.fis.com/", name = "SubscriberTransferer")
@XmlSeeAlso({ObjectFactory.class})
public interface SubscriberTransferer {

    @RequestWrapper(localName = "update", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.Update")
    @WebMethod
    @ResponseWrapper(localName = "updateResponse", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.UpdateResponse")
    public void update(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeSubscriber arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "findAllWithOrderPaging", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.FindAllWithOrderPaging")
    @WebMethod
    @ResponseWrapper(localName = "findAllWithOrderPagingResponse", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.FindAllWithOrderPagingResponse")
    public java.util.List<com.fis.esme.persistence.EsmeSubscriber> findAllWithOrderPaging(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeSubscriber arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        boolean arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        int arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        int arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        boolean arg5
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "findAllWithoutParameter", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.FindAllWithoutParameter")
    @WebMethod
    @ResponseWrapper(localName = "findAllWithoutParameterResponse", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.FindAllWithoutParameterResponse")
    public java.util.List<com.fis.esme.persistence.EsmeSubscriber> findAllWithoutParameter() throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.Add")
    @WebMethod
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.AddResponse")
    public java.lang.Long add(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeSubscriber arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkConstraints", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.CheckConstraints")
    @WebMethod
    @ResponseWrapper(localName = "checkConstraintsResponse", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.CheckConstraintsResponse")
    public boolean checkConstraints(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Long arg0
    );

    @RequestWrapper(localName = "delete", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.Delete")
    @WebMethod
    @ResponseWrapper(localName = "deleteResponse", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.DeleteResponse")
    public void delete(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeSubscriber arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "count", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.Count")
    @WebMethod
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.CountResponse")
    public int count(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeSubscriber arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        boolean arg1
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkExisted", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.CheckExisted")
    @WebMethod
    @ResponseWrapper(localName = "checkExistedResponse", targetNamespace = "http://subscriber.esme.fis.com/", className = "com.fis.esme.subscriber.CheckExistedResponse")
    public int checkExisted(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeSubscriber arg0
    );
}
