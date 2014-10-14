
package com.fis.esme.smsmt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsMt;
import com.fis.esme.persistence.EsmeSmsRouting;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fis.esme.smsmt package. 
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

    private final static QName _CheckExistedResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "checkExistedResponse");
    private final static QName _DeleteResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "deleteResponse");
    private final static QName _FindByCP_QNAME = new QName("http://smsmt.esme.fis.com/", "findByCP");
    private final static QName _FindByCommand_QNAME = new QName("http://smsmt.esme.fis.com/", "findByCommand");
    private final static QName _StopUploadResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "stopUploadResponse");
    private final static QName _FindBySmsRoutingResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "FindBySmsRoutingResponse");
    private final static QName _FindByShortCodeResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "findByShortCodeResponse");
    private final static QName _AddResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "addResponse");
    private final static QName _FindByShortCode_QNAME = new QName("http://smsmt.esme.fis.com/", "findByShortCode");
    private final static QName _CountResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "countResponse");
    private final static QName _FindAllWithoutParameterResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "findAllWithoutParameterResponse");
    private final static QName _FindByCommandResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "findByCommandResponse");
    private final static QName _FindAllWithOrderPaging_QNAME = new QName("http://smsmt.esme.fis.com/", "findAllWithOrderPaging");
    private final static QName _CheckConstraints_QNAME = new QName("http://smsmt.esme.fis.com/", "checkConstraints");
    private final static QName _DeleteByFileUploadIdResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "deleteByFileUploadIdResponse");
    private final static QName _UpdateResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "updateResponse");
    private final static QName _FindAllWithOrderPagingResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "findAllWithOrderPagingResponse");
    private final static QName _StopUpload_QNAME = new QName("http://smsmt.esme.fis.com/", "stopUpload");
    private final static QName _DeleteByFileUploadId_QNAME = new QName("http://smsmt.esme.fis.com/", "deleteByFileUploadId");
    private final static QName _CheckExisted_QNAME = new QName("http://smsmt.esme.fis.com/", "checkExisted");
    private final static QName _Delete_QNAME = new QName("http://smsmt.esme.fis.com/", "delete");
    private final static QName _AddMultiProcessResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "addMultiProcessResponse");
    private final static QName _Exception_QNAME = new QName("http://smsmt.esme.fis.com/", "Exception");
    private final static QName _Update_QNAME = new QName("http://smsmt.esme.fis.com/", "update");
    private final static QName _FindAllWithoutParameter_QNAME = new QName("http://smsmt.esme.fis.com/", "findAllWithoutParameter");
    private final static QName _Count_QNAME = new QName("http://smsmt.esme.fis.com/", "count");
    private final static QName _FindByCPResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "findByCPResponse");
    private final static QName _CheckConstraintsResponse_QNAME = new QName("http://smsmt.esme.fis.com/", "checkConstraintsResponse");
    private final static QName _FindBySmsRouting_QNAME = new QName("http://smsmt.esme.fis.com/", "FindBySmsRouting");
    private final static QName _AddMultiProcess_QNAME = new QName("http://smsmt.esme.fis.com/", "addMultiProcess");
    private final static QName _Add_QNAME = new QName("http://smsmt.esme.fis.com/", "add");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fis.esme.smsmt
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
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link AddMultiProcessResponse }
     * 
     */
    public AddMultiProcessResponse createAddMultiProcessResponse() {
        return new AddMultiProcessResponse();
    }

    /**
     * Create an instance of {@link FindByCPResponse }
     * 
     */
    public FindByCPResponse createFindByCPResponse() {
        return new FindByCPResponse();
    }

    /**
     * Create an instance of {@link Count }
     * 
     */
    public Count createCount() {
        return new Count();
    }

    /**
     * Create an instance of {@link FindAllWithoutParameter }
     * 
     */
    public FindAllWithoutParameter createFindAllWithoutParameter() {
        return new FindAllWithoutParameter();
    }

    /**
     * Create an instance of {@link CheckConstraintsResponse }
     * 
     */
    public CheckConstraintsResponse createCheckConstraintsResponse() {
        return new CheckConstraintsResponse();
    }

    /**
     * Create an instance of {@link Add }
     * 
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link AddMultiProcess }
     * 
     */
    public AddMultiProcess createAddMultiProcess() {
        return new AddMultiProcess();
    }

    /**
     * Create an instance of {@link FindBySmsRouting }
     * 
     */
    public FindBySmsRouting createFindBySmsRouting() {
        return new FindBySmsRouting();
    }

    /**
     * Create an instance of {@link DeleteByFileUploadIdResponse }
     * 
     */
    public DeleteByFileUploadIdResponse createDeleteByFileUploadIdResponse() {
        return new DeleteByFileUploadIdResponse();
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
     * Create an instance of {@link CheckExisted }
     * 
     */
    public CheckExisted createCheckExisted() {
        return new CheckExisted();
    }

    /**
     * Create an instance of {@link Delete }
     * 
     */
    public Delete createDelete() {
        return new Delete();
    }

    /**
     * Create an instance of {@link StopUpload }
     * 
     */
    public StopUpload createStopUpload() {
        return new StopUpload();
    }

    /**
     * Create an instance of {@link DeleteByFileUploadId }
     * 
     */
    public DeleteByFileUploadId createDeleteByFileUploadId() {
        return new DeleteByFileUploadId();
    }

    /**
     * Create an instance of {@link CountResponse }
     * 
     */
    public CountResponse createCountResponse() {
        return new CountResponse();
    }

    /**
     * Create an instance of {@link FindByShortCode }
     * 
     */
    public FindByShortCode createFindByShortCode() {
        return new FindByShortCode();
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link FindAllWithoutParameterResponse }
     * 
     */
    public FindAllWithoutParameterResponse createFindAllWithoutParameterResponse() {
        return new FindAllWithoutParameterResponse();
    }

    /**
     * Create an instance of {@link CheckConstraints }
     * 
     */
    public CheckConstraints createCheckConstraints() {
        return new CheckConstraints();
    }

    /**
     * Create an instance of {@link FindAllWithOrderPaging }
     * 
     */
    public FindAllWithOrderPaging createFindAllWithOrderPaging() {
        return new FindAllWithOrderPaging();
    }

    /**
     * Create an instance of {@link FindByCommandResponse }
     * 
     */
    public FindByCommandResponse createFindByCommandResponse() {
        return new FindByCommandResponse();
    }

    /**
     * Create an instance of {@link DeleteResponse }
     * 
     */
    public DeleteResponse createDeleteResponse() {
        return new DeleteResponse();
    }

    /**
     * Create an instance of {@link CheckExistedResponse }
     * 
     */
    public CheckExistedResponse createCheckExistedResponse() {
        return new CheckExistedResponse();
    }

    /**
     * Create an instance of {@link FindByCP }
     * 
     */
    public FindByCP createFindByCP() {
        return new FindByCP();
    }

    /**
     * Create an instance of {@link StopUploadResponse }
     * 
     */
    public StopUploadResponse createStopUploadResponse() {
        return new StopUploadResponse();
    }

    /**
     * Create an instance of {@link FindByCommand }
     * 
     */
    public FindByCommand createFindByCommand() {
        return new FindByCommand();
    }

    /**
     * Create an instance of {@link FindByShortCodeResponse }
     * 
     */
    public FindByShortCodeResponse createFindByShortCodeResponse() {
        return new FindByShortCodeResponse();
    }

    /**
     * Create an instance of {@link FindBySmsRoutingResponse }
     * 
     */
    public FindBySmsRoutingResponse createFindBySmsRoutingResponse() {
        return new FindBySmsRoutingResponse();
    }

    /**
     * Create an instance of {@link EsmeCp }
     * 
     */
    public EsmeCp createEsmeCp() {
        return new EsmeCp();
    }

    /**
     * Create an instance of {@link EsmeSmsCommand }
     * 
     */
    public EsmeSmsCommand createEsmeSmsCommand() {
        return new EsmeSmsCommand();
    }

    /**
     * Create an instance of {@link EsmeSmsMt }
     * 
     */
    public EsmeSmsMt createEsmeSmsMt() {
        return new EsmeSmsMt();
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
     * Create an instance of {@link EsmeSmsRouting }
     * 
     */
    public EsmeSmsRouting createEsmeSmsRouting() {
        return new EsmeSmsRouting();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckExistedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "checkExistedResponse")
    public JAXBElement<CheckExistedResponse> createCheckExistedResponse(CheckExistedResponse value) {
        return new JAXBElement<CheckExistedResponse>(_CheckExistedResponse_QNAME, CheckExistedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "deleteResponse")
    public JAXBElement<DeleteResponse> createDeleteResponse(DeleteResponse value) {
        return new JAXBElement<DeleteResponse>(_DeleteResponse_QNAME, DeleteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByCP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "findByCP")
    public JAXBElement<FindByCP> createFindByCP(FindByCP value) {
        return new JAXBElement<FindByCP>(_FindByCP_QNAME, FindByCP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByCommand }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "findByCommand")
    public JAXBElement<FindByCommand> createFindByCommand(FindByCommand value) {
        return new JAXBElement<FindByCommand>(_FindByCommand_QNAME, FindByCommand.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StopUploadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "stopUploadResponse")
    public JAXBElement<StopUploadResponse> createStopUploadResponse(StopUploadResponse value) {
        return new JAXBElement<StopUploadResponse>(_StopUploadResponse_QNAME, StopUploadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBySmsRoutingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "FindBySmsRoutingResponse")
    public JAXBElement<FindBySmsRoutingResponse> createFindBySmsRoutingResponse(FindBySmsRoutingResponse value) {
        return new JAXBElement<FindBySmsRoutingResponse>(_FindBySmsRoutingResponse_QNAME, FindBySmsRoutingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByShortCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "findByShortCodeResponse")
    public JAXBElement<FindByShortCodeResponse> createFindByShortCodeResponse(FindByShortCodeResponse value) {
        return new JAXBElement<FindByShortCodeResponse>(_FindByShortCodeResponse_QNAME, FindByShortCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "addResponse")
    public JAXBElement<AddResponse> createAddResponse(AddResponse value) {
        return new JAXBElement<AddResponse>(_AddResponse_QNAME, AddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByShortCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "findByShortCode")
    public JAXBElement<FindByShortCode> createFindByShortCode(FindByShortCode value) {
        return new JAXBElement<FindByShortCode>(_FindByShortCode_QNAME, FindByShortCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "countResponse")
    public JAXBElement<CountResponse> createCountResponse(CountResponse value) {
        return new JAXBElement<CountResponse>(_CountResponse_QNAME, CountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithoutParameterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "findAllWithoutParameterResponse")
    public JAXBElement<FindAllWithoutParameterResponse> createFindAllWithoutParameterResponse(FindAllWithoutParameterResponse value) {
        return new JAXBElement<FindAllWithoutParameterResponse>(_FindAllWithoutParameterResponse_QNAME, FindAllWithoutParameterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByCommandResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "findByCommandResponse")
    public JAXBElement<FindByCommandResponse> createFindByCommandResponse(FindByCommandResponse value) {
        return new JAXBElement<FindByCommandResponse>(_FindByCommandResponse_QNAME, FindByCommandResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithOrderPaging }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "findAllWithOrderPaging")
    public JAXBElement<FindAllWithOrderPaging> createFindAllWithOrderPaging(FindAllWithOrderPaging value) {
        return new JAXBElement<FindAllWithOrderPaging>(_FindAllWithOrderPaging_QNAME, FindAllWithOrderPaging.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckConstraints }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "checkConstraints")
    public JAXBElement<CheckConstraints> createCheckConstraints(CheckConstraints value) {
        return new JAXBElement<CheckConstraints>(_CheckConstraints_QNAME, CheckConstraints.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteByFileUploadIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "deleteByFileUploadIdResponse")
    public JAXBElement<DeleteByFileUploadIdResponse> createDeleteByFileUploadIdResponse(DeleteByFileUploadIdResponse value) {
        return new JAXBElement<DeleteByFileUploadIdResponse>(_DeleteByFileUploadIdResponse_QNAME, DeleteByFileUploadIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "updateResponse")
    public JAXBElement<UpdateResponse> createUpdateResponse(UpdateResponse value) {
        return new JAXBElement<UpdateResponse>(_UpdateResponse_QNAME, UpdateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithOrderPagingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "findAllWithOrderPagingResponse")
    public JAXBElement<FindAllWithOrderPagingResponse> createFindAllWithOrderPagingResponse(FindAllWithOrderPagingResponse value) {
        return new JAXBElement<FindAllWithOrderPagingResponse>(_FindAllWithOrderPagingResponse_QNAME, FindAllWithOrderPagingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StopUpload }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "stopUpload")
    public JAXBElement<StopUpload> createStopUpload(StopUpload value) {
        return new JAXBElement<StopUpload>(_StopUpload_QNAME, StopUpload.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteByFileUploadId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "deleteByFileUploadId")
    public JAXBElement<DeleteByFileUploadId> createDeleteByFileUploadId(DeleteByFileUploadId value) {
        return new JAXBElement<DeleteByFileUploadId>(_DeleteByFileUploadId_QNAME, DeleteByFileUploadId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckExisted }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "checkExisted")
    public JAXBElement<CheckExisted> createCheckExisted(CheckExisted value) {
        return new JAXBElement<CheckExisted>(_CheckExisted_QNAME, CheckExisted.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Delete }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "delete")
    public JAXBElement<Delete> createDelete(Delete value) {
        return new JAXBElement<Delete>(_Delete_QNAME, Delete.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMultiProcessResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "addMultiProcessResponse")
    public JAXBElement<AddMultiProcessResponse> createAddMultiProcessResponse(AddMultiProcessResponse value) {
        return new JAXBElement<AddMultiProcessResponse>(_AddMultiProcessResponse_QNAME, AddMultiProcessResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Update }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "update")
    public JAXBElement<Update> createUpdate(Update value) {
        return new JAXBElement<Update>(_Update_QNAME, Update.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithoutParameter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "findAllWithoutParameter")
    public JAXBElement<FindAllWithoutParameter> createFindAllWithoutParameter(FindAllWithoutParameter value) {
        return new JAXBElement<FindAllWithoutParameter>(_FindAllWithoutParameter_QNAME, FindAllWithoutParameter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Count }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "count")
    public JAXBElement<Count> createCount(Count value) {
        return new JAXBElement<Count>(_Count_QNAME, Count.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByCPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "findByCPResponse")
    public JAXBElement<FindByCPResponse> createFindByCPResponse(FindByCPResponse value) {
        return new JAXBElement<FindByCPResponse>(_FindByCPResponse_QNAME, FindByCPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckConstraintsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "checkConstraintsResponse")
    public JAXBElement<CheckConstraintsResponse> createCheckConstraintsResponse(CheckConstraintsResponse value) {
        return new JAXBElement<CheckConstraintsResponse>(_CheckConstraintsResponse_QNAME, CheckConstraintsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBySmsRouting }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "FindBySmsRouting")
    public JAXBElement<FindBySmsRouting> createFindBySmsRouting(FindBySmsRouting value) {
        return new JAXBElement<FindBySmsRouting>(_FindBySmsRouting_QNAME, FindBySmsRouting.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMultiProcess }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "addMultiProcess")
    public JAXBElement<AddMultiProcess> createAddMultiProcess(AddMultiProcess value) {
        return new JAXBElement<AddMultiProcess>(_AddMultiProcess_QNAME, AddMultiProcess.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Add }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smsmt.esme.fis.com/", name = "add")
    public JAXBElement<Add> createAdd(Add value) {
        return new JAXBElement<Add>(_Add_QNAME, Add.class, null, value);
    }

}
