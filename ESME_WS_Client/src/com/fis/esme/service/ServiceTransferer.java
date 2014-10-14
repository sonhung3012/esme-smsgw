package com.fis.esme.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2013-11-27T16:40:50.201+07:00
 * Generated source version: 2.4.1
 * 
 */
@WebService(targetNamespace = "http://service.esme.fis.com/", name = "ServiceTransferer")
@XmlSeeAlso({ObjectFactory.class})
public interface ServiceTransferer {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkConstraints", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.CheckConstraints")
    @WebMethod
    @ResponseWrapper(localName = "checkConstraintsResponse", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.CheckConstraintsResponse")
    public boolean checkConstraints(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Long arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "findAllWithOrderPaging", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.FindAllWithOrderPaging")
    @WebMethod
    @ResponseWrapper(localName = "findAllWithOrderPagingResponse", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.FindAllWithOrderPagingResponse")
    public java.util.List<com.fis.esme.persistence.EsmeServices> findAllWithOrderPaging(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeServices arg0,
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
    @RequestWrapper(localName = "checkExisted", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.CheckExisted")
    @WebMethod
    @ResponseWrapper(localName = "checkExistedResponse", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.CheckExistedResponse")
    public int checkExisted(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeServices arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "findAllWithoutParameter", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.FindAllWithoutParameter")
    @WebMethod
    @ResponseWrapper(localName = "findAllWithoutParameterResponse", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.FindAllWithoutParameterResponse")
    public java.util.List<com.fis.esme.persistence.EsmeServices> findAllWithoutParameter() throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.Add")
    @WebMethod
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.AddResponse")
    public java.lang.Long add(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeServices arg0
    ) throws Exception_Exception;

    @RequestWrapper(localName = "delete", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.Delete")
    @WebMethod
    @ResponseWrapper(localName = "deleteResponse", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.DeleteResponse")
    public void delete(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeServices arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "count", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.Count")
    @WebMethod
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.CountResponse")
    public int count(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeServices arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        boolean arg1
    );

    @RequestWrapper(localName = "update", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.Update")
    @WebMethod
    @ResponseWrapper(localName = "updateResponse", targetNamespace = "http://service.esme.fis.com/", className = "com.fis.esme.service.UpdateResponse")
    public void update(
        @WebParam(name = "arg0", targetNamespace = "")
        com.fis.esme.persistence.EsmeServices arg0
    ) throws Exception_Exception;
}
