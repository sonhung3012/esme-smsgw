
package com.fis.esme.emsmt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.esme.persistence.EsmeEmsMt;
import com.fis.esme.persistence.EsmeFileUpload;
import com.fis.esme.persistence.EsmeGroups;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.EsmeSubscriber;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fis.esme.emsmt package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Count_QNAME = new QName("http://emsmt.esme.fis.com/", "count");
    private final static QName _FindByMtID_QNAME = new QName("http://emsmt.esme.fis.com/", "findByMtID");
    private final static QName _FindAllWithoutParameter_QNAME = new QName("http://emsmt.esme.fis.com/", "findAllWithoutParameter");
    private final static QName _FindAllWithoutParameterResponse_QNAME = new QName("http://emsmt.esme.fis.com/", "findAllWithoutParameterResponse");
    private final static QName _Update_QNAME = new QName("http://emsmt.esme.fis.com/", "update");
    private final static QName _CountResponse_QNAME = new QName("http://emsmt.esme.fis.com/", "countResponse");
    private final static QName _Exception_QNAME = new QName("http://emsmt.esme.fis.com/", "Exception");
    private final static QName _AddResponse_QNAME = new QName("http://emsmt.esme.fis.com/", "addResponse");
    private final static QName _CheckConstraints_QNAME = new QName("http://emsmt.esme.fis.com/", "checkConstraints");
    private final static QName _Add_QNAME = new QName("http://emsmt.esme.fis.com/", "add");
    private final static QName _FindAllWithOrderPaging_QNAME = new QName("http://emsmt.esme.fis.com/", "findAllWithOrderPaging");
    private final static QName _CheckConstraintsResponse_QNAME = new QName("http://emsmt.esme.fis.com/", "checkConstraintsResponse");
    private final static QName _FindAllWithOrderPagingResponse_QNAME = new QName("http://emsmt.esme.fis.com/", "findAllWithOrderPagingResponse");
    private final static QName _UpdateResponse_QNAME = new QName("http://emsmt.esme.fis.com/", "updateResponse");
    private final static QName _DeleteResponse_QNAME = new QName("http://emsmt.esme.fis.com/", "deleteResponse");
    private final static QName _CheckExistedResponse_QNAME = new QName("http://emsmt.esme.fis.com/", "checkExistedResponse");
    private final static QName _FindByMtIDResponse_QNAME = new QName("http://emsmt.esme.fis.com/", "findByMtIDResponse");
    private final static QName _CheckExisted_QNAME = new QName("http://emsmt.esme.fis.com/", "checkExisted");
    private final static QName _Delete_QNAME = new QName("http://emsmt.esme.fis.com/", "delete");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fis.esme.emsmt
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Update }
     * 
     */
    public Update createUpdate() {
        return new Update();
    }

    /**
     * Create an instance of {@link CountResponse }
     * 
     */
    public CountResponse createCountResponse() {
        return new CountResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link Count }
     * 
     */
    public Count createCount() {
        return new Count();
    }

    /**
     * Create an instance of {@link FindByMtID }
     * 
     */
    public FindByMtID createFindByMtID() {
        return new FindByMtID();
    }

    /**
     * Create an instance of {@link FindAllWithoutParameter }
     * 
     */
    public FindAllWithoutParameter createFindAllWithoutParameter() {
        return new FindAllWithoutParameter();
    }

    /**
     * Create an instance of {@link FindAllWithoutParameterResponse }
     * 
     */
    public FindAllWithoutParameterResponse createFindAllWithoutParameterResponse() {
        return new FindAllWithoutParameterResponse();
    }

    /**
     * Create an instance of {@link CheckConstraintsResponse }
     * 
     */
    public CheckConstraintsResponse createCheckConstraintsResponse() {
        return new CheckConstraintsResponse();
    }

    /**
     * Create an instance of {@link CheckConstraints }
     * 
     */
    public CheckConstraints createCheckConstraints() {
        return new CheckConstraints();
    }

    /**
     * Create an instance of {@link Add }
     * 
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link FindAllWithOrderPaging }
     * 
     */
    public FindAllWithOrderPaging createFindAllWithOrderPaging() {
        return new FindAllWithOrderPaging();
    }

    /**
     * Create an instance of {@link DeleteResponse }
     * 
     */
    public DeleteResponse createDeleteResponse() {
        return new DeleteResponse();
    }

    /**
     * Create an instance of {@link FindByMtIDResponse }
     * 
     */
    public FindByMtIDResponse createFindByMtIDResponse() {
        return new FindByMtIDResponse();
    }

    /**
     * Create an instance of {@link CheckExistedResponse }
     * 
     */
    public CheckExistedResponse createCheckExistedResponse() {
        return new CheckExistedResponse();
    }

    /**
     * Create an instance of {@link FindAllWithOrderPagingResponse }
     * 
     */
    public FindAllWithOrderPagingResponse createFindAllWithOrderPagingResponse() {
        return new FindAllWithOrderPagingResponse();
    }

    /**
     * Create an instance of {@link UpdateResponse }
     * 
     */
    public UpdateResponse createUpdateResponse() {
        return new UpdateResponse();
    }

    /**
     * Create an instance of {@link Delete }
     * 
     */
    public Delete createDelete() {
        return new Delete();
    }

    /**
     * Create an instance of {@link CheckExisted }
     * 
     */
    public CheckExisted createCheckExisted() {
        return new CheckExisted();
    }

    /**
     * Create an instance of {@link EsmeSubscriber }
     * 
     */
    public EsmeSubscriber createEsmeSubscriber() {
        return new EsmeSubscriber();
    }

    /**
     * Create an instance of {@link EsmeEmsMo }
     * 
     */
    public EsmeEmsMo createEsmeEmsMo() {
        return new EsmeEmsMo();
    }

    /**
     * Create an instance of {@link EsmeCp }
     * 
     */
    public EsmeCp createEsmeCp() {
        return new EsmeCp();
    }

    /**
     * Create an instance of {@link EsmeSmsc }
     * 
     */
    public EsmeSmsc createEsmeSmsc() {
        return new EsmeSmsc();
    }

    /**
     * Create an instance of {@link EsmeEmsMt }
     * 
     */
    public EsmeEmsMt createEsmeEmsMt() {
        return new EsmeEmsMt();
    }

    /**
     * Create an instance of {@link EsmeSmsCommand }
     * 
     */
    public EsmeSmsCommand createEsmeSmsCommand() {
        return new EsmeSmsCommand();
    }

    /**
     * Create an instance of {@link EsmeSmsLog }
     * 
     */
    public EsmeSmsLog createEsmeSmsLog() {
        return new EsmeSmsLog();
    }

    /**
     * Create an instance of {@link EsmeShortCode }
     * 
     */
    public EsmeShortCode createEsmeShortCode() {
        return new EsmeShortCode();
    }

    /**
     * Create an instance of {@link EsmeServices }
     * 
     */
    public EsmeServices createEsmeServices() {
        return new EsmeServices();
    }

    /**
     * Create an instance of {@link EsmeFileUpload }
     * 
     */
    public EsmeFileUpload createEsmeFileUpload() {
        return new EsmeFileUpload();
    }

    /**
     * Create an instance of {@link EsmeGroups }
     * 
     */
    public EsmeGroups createEsmeGroups() {
        return new EsmeGroups();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Count }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "count")
    public JAXBElement<Count> createCount(Count value) {
        return new JAXBElement<Count>(_Count_QNAME, Count.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByMtID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "findByMtID")
    public JAXBElement<FindByMtID> createFindByMtID(FindByMtID value) {
        return new JAXBElement<FindByMtID>(_FindByMtID_QNAME, FindByMtID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithoutParameter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "findAllWithoutParameter")
    public JAXBElement<FindAllWithoutParameter> createFindAllWithoutParameter(FindAllWithoutParameter value) {
        return new JAXBElement<FindAllWithoutParameter>(_FindAllWithoutParameter_QNAME, FindAllWithoutParameter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithoutParameterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "findAllWithoutParameterResponse")
    public JAXBElement<FindAllWithoutParameterResponse> createFindAllWithoutParameterResponse(FindAllWithoutParameterResponse value) {
        return new JAXBElement<FindAllWithoutParameterResponse>(_FindAllWithoutParameterResponse_QNAME, FindAllWithoutParameterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Update }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "update")
    public JAXBElement<Update> createUpdate(Update value) {
        return new JAXBElement<Update>(_Update_QNAME, Update.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "countResponse")
    public JAXBElement<CountResponse> createCountResponse(CountResponse value) {
        return new JAXBElement<CountResponse>(_CountResponse_QNAME, CountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "addResponse")
    public JAXBElement<AddResponse> createAddResponse(AddResponse value) {
        return new JAXBElement<AddResponse>(_AddResponse_QNAME, AddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckConstraints }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "checkConstraints")
    public JAXBElement<CheckConstraints> createCheckConstraints(CheckConstraints value) {
        return new JAXBElement<CheckConstraints>(_CheckConstraints_QNAME, CheckConstraints.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Add }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "add")
    public JAXBElement<Add> createAdd(Add value) {
        return new JAXBElement<Add>(_Add_QNAME, Add.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithOrderPaging }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "findAllWithOrderPaging")
    public JAXBElement<FindAllWithOrderPaging> createFindAllWithOrderPaging(FindAllWithOrderPaging value) {
        return new JAXBElement<FindAllWithOrderPaging>(_FindAllWithOrderPaging_QNAME, FindAllWithOrderPaging.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckConstraintsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "checkConstraintsResponse")
    public JAXBElement<CheckConstraintsResponse> createCheckConstraintsResponse(CheckConstraintsResponse value) {
        return new JAXBElement<CheckConstraintsResponse>(_CheckConstraintsResponse_QNAME, CheckConstraintsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithOrderPagingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "findAllWithOrderPagingResponse")
    public JAXBElement<FindAllWithOrderPagingResponse> createFindAllWithOrderPagingResponse(FindAllWithOrderPagingResponse value) {
        return new JAXBElement<FindAllWithOrderPagingResponse>(_FindAllWithOrderPagingResponse_QNAME, FindAllWithOrderPagingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "updateResponse")
    public JAXBElement<UpdateResponse> createUpdateResponse(UpdateResponse value) {
        return new JAXBElement<UpdateResponse>(_UpdateResponse_QNAME, UpdateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "deleteResponse")
    public JAXBElement<DeleteResponse> createDeleteResponse(DeleteResponse value) {
        return new JAXBElement<DeleteResponse>(_DeleteResponse_QNAME, DeleteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckExistedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "checkExistedResponse")
    public JAXBElement<CheckExistedResponse> createCheckExistedResponse(CheckExistedResponse value) {
        return new JAXBElement<CheckExistedResponse>(_CheckExistedResponse_QNAME, CheckExistedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByMtIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "findByMtIDResponse")
    public JAXBElement<FindByMtIDResponse> createFindByMtIDResponse(FindByMtIDResponse value) {
        return new JAXBElement<FindByMtIDResponse>(_FindByMtIDResponse_QNAME, FindByMtIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckExisted }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "checkExisted")
    public JAXBElement<CheckExisted> createCheckExisted(CheckExisted value) {
        return new JAXBElement<CheckExisted>(_CheckExisted_QNAME, CheckExisted.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Delete }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://emsmt.esme.fis.com/", name = "delete")
    public JAXBElement<Delete> createDelete(Delete value) {
        return new JAXBElement<Delete>(_Delete_QNAME, Delete.class, null, value);
    }

}
