
package com.fis.esme.language;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.fis.esme.persistence.EsmeLanguage;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fis.esme.language package. 
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

    private final static QName _FindAllWithOrderPagingResponse_QNAME = new QName("http://language.esme.fis.com/", "findAllWithOrderPagingResponse");
    private final static QName _DeleteAll_QNAME = new QName("http://language.esme.fis.com/", "deleteAll");
    private final static QName _FindAllResponse_QNAME = new QName("http://language.esme.fis.com/", "findAllResponse");
    private final static QName _CheckExisted_QNAME = new QName("http://language.esme.fis.com/", "checkExisted");
    private final static QName _CountAll_QNAME = new QName("http://language.esme.fis.com/", "countAll");
    private final static QName _Delete_QNAME = new QName("http://language.esme.fis.com/", "delete");
    private final static QName _GetDefaultLanguageResponse_QNAME = new QName("http://language.esme.fis.com/", "getDefaultLanguageResponse");
    private final static QName _Count_QNAME = new QName("http://language.esme.fis.com/", "count");
    private final static QName _FindAllWithoutParameter_QNAME = new QName("http://language.esme.fis.com/", "findAllWithoutParameter");
    private final static QName _Exception_QNAME = new QName("http://language.esme.fis.com/", "Exception");
    private final static QName _Add_QNAME = new QName("http://language.esme.fis.com/", "add");
    private final static QName _GetDefaultLanguage_QNAME = new QName("http://language.esme.fis.com/", "getDefaultLanguage");
    private final static QName _GetLanguageStatusResponse_QNAME = new QName("http://language.esme.fis.com/", "getLanguageStatusResponse");
    private final static QName _CheckConstraintsResponse_QNAME = new QName("http://language.esme.fis.com/", "checkConstraintsResponse");
    private final static QName _GetLanguageStatus_QNAME = new QName("http://language.esme.fis.com/", "getLanguageStatus");
    private final static QName _DeleteResponse_QNAME = new QName("http://language.esme.fis.com/", "deleteResponse");
    private final static QName _CheckExistedResponse_QNAME = new QName("http://language.esme.fis.com/", "checkExistedResponse");
    private final static QName _EditResponse_QNAME = new QName("http://language.esme.fis.com/", "editResponse");
    private final static QName _CountAllResponse_QNAME = new QName("http://language.esme.fis.com/", "countAllResponse");
    private final static QName _DeleteAllResponse_QNAME = new QName("http://language.esme.fis.com/", "deleteAllResponse");
    private final static QName _FindAllWithoutParameterResponse_QNAME = new QName("http://language.esme.fis.com/", "findAllWithoutParameterResponse");
    private final static QName _FindAll_QNAME = new QName("http://language.esme.fis.com/", "findAll");
    private final static QName _CountResponse_QNAME = new QName("http://language.esme.fis.com/", "countResponse");
    private final static QName _AddResponse_QNAME = new QName("http://language.esme.fis.com/", "addResponse");
    private final static QName _CheckConstraints_QNAME = new QName("http://language.esme.fis.com/", "checkConstraints");
    private final static QName _FindAllWithOrderPaging_QNAME = new QName("http://language.esme.fis.com/", "findAllWithOrderPaging");
    private final static QName _UpdateAllLanguageResponse_QNAME = new QName("http://language.esme.fis.com/", "updateAllLanguageResponse");
    private final static QName _UpdateAllLanguage_QNAME = new QName("http://language.esme.fis.com/", "updateAllLanguage");
    private final static QName _Edit_QNAME = new QName("http://language.esme.fis.com/", "edit");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fis.esme.language
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
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
     * Create an instance of {@link GetLanguageStatusResponse }
     * 
     */
    public GetLanguageStatusResponse createGetLanguageStatusResponse() {
        return new GetLanguageStatusResponse();
    }

    /**
     * Create an instance of {@link GetDefaultLanguage }
     * 
     */
    public GetDefaultLanguage createGetDefaultLanguage() {
        return new GetDefaultLanguage();
    }

    /**
     * Create an instance of {@link DeleteAll }
     * 
     */
    public DeleteAll createDeleteAll() {
        return new DeleteAll();
    }

    /**
     * Create an instance of {@link FindAllWithOrderPagingResponse }
     * 
     */
    public FindAllWithOrderPagingResponse createFindAllWithOrderPagingResponse() {
        return new FindAllWithOrderPagingResponse();
    }

    /**
     * Create an instance of {@link Delete }
     * 
     */
    public Delete createDelete() {
        return new Delete();
    }

    /**
     * Create an instance of {@link CountAll }
     * 
     */
    public CountAll createCountAll() {
        return new CountAll();
    }

    /**
     * Create an instance of {@link CheckExisted }
     * 
     */
    public CheckExisted createCheckExisted() {
        return new CheckExisted();
    }

    /**
     * Create an instance of {@link GetDefaultLanguageResponse }
     * 
     */
    public GetDefaultLanguageResponse createGetDefaultLanguageResponse() {
        return new GetDefaultLanguageResponse();
    }

    /**
     * Create an instance of {@link FindAllResponse }
     * 
     */
    public FindAllResponse createFindAllResponse() {
        return new FindAllResponse();
    }

    /**
     * Create an instance of {@link CountResponse }
     * 
     */
    public CountResponse createCountResponse() {
        return new CountResponse();
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
     * Create an instance of {@link FindAll }
     * 
     */
    public FindAll createFindAll() {
        return new FindAll();
    }

    /**
     * Create an instance of {@link UpdateAllLanguageResponse }
     * 
     */
    public UpdateAllLanguageResponse createUpdateAllLanguageResponse() {
        return new UpdateAllLanguageResponse();
    }

    /**
     * Create an instance of {@link UpdateAllLanguage }
     * 
     */
    public UpdateAllLanguage createUpdateAllLanguage() {
        return new UpdateAllLanguage();
    }

    /**
     * Create an instance of {@link Edit }
     * 
     */
    public Edit createEdit() {
        return new Edit();
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
     * Create an instance of {@link GetLanguageStatus }
     * 
     */
    public GetLanguageStatus createGetLanguageStatus() {
        return new GetLanguageStatus();
    }

    /**
     * Create an instance of {@link CountAllResponse }
     * 
     */
    public CountAllResponse createCountAllResponse() {
        return new CountAllResponse();
    }

    /**
     * Create an instance of {@link EditResponse }
     * 
     */
    public EditResponse createEditResponse() {
        return new EditResponse();
    }

    /**
     * Create an instance of {@link DeleteAllResponse }
     * 
     */
    public DeleteAllResponse createDeleteAllResponse() {
        return new DeleteAllResponse();
    }

    /**
     * Create an instance of {@link EsmeLanguage }
     * 
     */
    public EsmeLanguage createEsmeLanguage() {
        return new EsmeLanguage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithOrderPagingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "findAllWithOrderPagingResponse")
    public JAXBElement<FindAllWithOrderPagingResponse> createFindAllWithOrderPagingResponse(FindAllWithOrderPagingResponse value) {
        return new JAXBElement<FindAllWithOrderPagingResponse>(_FindAllWithOrderPagingResponse_QNAME, FindAllWithOrderPagingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "deleteAll")
    public JAXBElement<DeleteAll> createDeleteAll(DeleteAll value) {
        return new JAXBElement<DeleteAll>(_DeleteAll_QNAME, DeleteAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "findAllResponse")
    public JAXBElement<FindAllResponse> createFindAllResponse(FindAllResponse value) {
        return new JAXBElement<FindAllResponse>(_FindAllResponse_QNAME, FindAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckExisted }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "checkExisted")
    public JAXBElement<CheckExisted> createCheckExisted(CheckExisted value) {
        return new JAXBElement<CheckExisted>(_CheckExisted_QNAME, CheckExisted.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "countAll")
    public JAXBElement<CountAll> createCountAll(CountAll value) {
        return new JAXBElement<CountAll>(_CountAll_QNAME, CountAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Delete }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "delete")
    public JAXBElement<Delete> createDelete(Delete value) {
        return new JAXBElement<Delete>(_Delete_QNAME, Delete.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDefaultLanguageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "getDefaultLanguageResponse")
    public JAXBElement<GetDefaultLanguageResponse> createGetDefaultLanguageResponse(GetDefaultLanguageResponse value) {
        return new JAXBElement<GetDefaultLanguageResponse>(_GetDefaultLanguageResponse_QNAME, GetDefaultLanguageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Count }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "count")
    public JAXBElement<Count> createCount(Count value) {
        return new JAXBElement<Count>(_Count_QNAME, Count.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithoutParameter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "findAllWithoutParameter")
    public JAXBElement<FindAllWithoutParameter> createFindAllWithoutParameter(FindAllWithoutParameter value) {
        return new JAXBElement<FindAllWithoutParameter>(_FindAllWithoutParameter_QNAME, FindAllWithoutParameter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Add }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "add")
    public JAXBElement<Add> createAdd(Add value) {
        return new JAXBElement<Add>(_Add_QNAME, Add.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDefaultLanguage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "getDefaultLanguage")
    public JAXBElement<GetDefaultLanguage> createGetDefaultLanguage(GetDefaultLanguage value) {
        return new JAXBElement<GetDefaultLanguage>(_GetDefaultLanguage_QNAME, GetDefaultLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLanguageStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "getLanguageStatusResponse")
    public JAXBElement<GetLanguageStatusResponse> createGetLanguageStatusResponse(GetLanguageStatusResponse value) {
        return new JAXBElement<GetLanguageStatusResponse>(_GetLanguageStatusResponse_QNAME, GetLanguageStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckConstraintsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "checkConstraintsResponse")
    public JAXBElement<CheckConstraintsResponse> createCheckConstraintsResponse(CheckConstraintsResponse value) {
        return new JAXBElement<CheckConstraintsResponse>(_CheckConstraintsResponse_QNAME, CheckConstraintsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLanguageStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "getLanguageStatus")
    public JAXBElement<GetLanguageStatus> createGetLanguageStatus(GetLanguageStatus value) {
        return new JAXBElement<GetLanguageStatus>(_GetLanguageStatus_QNAME, GetLanguageStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "deleteResponse")
    public JAXBElement<DeleteResponse> createDeleteResponse(DeleteResponse value) {
        return new JAXBElement<DeleteResponse>(_DeleteResponse_QNAME, DeleteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckExistedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "checkExistedResponse")
    public JAXBElement<CheckExistedResponse> createCheckExistedResponse(CheckExistedResponse value) {
        return new JAXBElement<CheckExistedResponse>(_CheckExistedResponse_QNAME, CheckExistedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "editResponse")
    public JAXBElement<EditResponse> createEditResponse(EditResponse value) {
        return new JAXBElement<EditResponse>(_EditResponse_QNAME, EditResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "countAllResponse")
    public JAXBElement<CountAllResponse> createCountAllResponse(CountAllResponse value) {
        return new JAXBElement<CountAllResponse>(_CountAllResponse_QNAME, CountAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "deleteAllResponse")
    public JAXBElement<DeleteAllResponse> createDeleteAllResponse(DeleteAllResponse value) {
        return new JAXBElement<DeleteAllResponse>(_DeleteAllResponse_QNAME, DeleteAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithoutParameterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "findAllWithoutParameterResponse")
    public JAXBElement<FindAllWithoutParameterResponse> createFindAllWithoutParameterResponse(FindAllWithoutParameterResponse value) {
        return new JAXBElement<FindAllWithoutParameterResponse>(_FindAllWithoutParameterResponse_QNAME, FindAllWithoutParameterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "findAll")
    public JAXBElement<FindAll> createFindAll(FindAll value) {
        return new JAXBElement<FindAll>(_FindAll_QNAME, FindAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "countResponse")
    public JAXBElement<CountResponse> createCountResponse(CountResponse value) {
        return new JAXBElement<CountResponse>(_CountResponse_QNAME, CountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "addResponse")
    public JAXBElement<AddResponse> createAddResponse(AddResponse value) {
        return new JAXBElement<AddResponse>(_AddResponse_QNAME, AddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckConstraints }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "checkConstraints")
    public JAXBElement<CheckConstraints> createCheckConstraints(CheckConstraints value) {
        return new JAXBElement<CheckConstraints>(_CheckConstraints_QNAME, CheckConstraints.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllWithOrderPaging }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "findAllWithOrderPaging")
    public JAXBElement<FindAllWithOrderPaging> createFindAllWithOrderPaging(FindAllWithOrderPaging value) {
        return new JAXBElement<FindAllWithOrderPaging>(_FindAllWithOrderPaging_QNAME, FindAllWithOrderPaging.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAllLanguageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "updateAllLanguageResponse")
    public JAXBElement<UpdateAllLanguageResponse> createUpdateAllLanguageResponse(UpdateAllLanguageResponse value) {
        return new JAXBElement<UpdateAllLanguageResponse>(_UpdateAllLanguageResponse_QNAME, UpdateAllLanguageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAllLanguage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "updateAllLanguage")
    public JAXBElement<UpdateAllLanguage> createUpdateAllLanguage(UpdateAllLanguage value) {
        return new JAXBElement<UpdateAllLanguage>(_UpdateAllLanguage_QNAME, UpdateAllLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Edit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://language.esme.fis.com/", name = "edit")
    public JAXBElement<Edit> createEdit(Edit value) {
        return new JAXBElement<Edit>(_Edit_QNAME, Edit.class, null, value);
    }

}
