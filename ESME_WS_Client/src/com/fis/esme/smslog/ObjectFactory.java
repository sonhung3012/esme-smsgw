
package com.fis.esme.smslog;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeFileUpload;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.SubSearchDetail;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fis.esme.smslog package. 
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

    private final static QName _UpdateResponse_QNAME = new QName("http://smslog.esme.fis.com/", "updateResponse");
    private final static QName _GetCommandActiveResponse_QNAME = new QName("http://smslog.esme.fis.com/", "getCommandActiveResponse");
    private final static QName _CheckExisted_QNAME = new QName("http://smslog.esme.fis.com/", "checkExisted");
    private final static QName _Delete_QNAME = new QName("http://smslog.esme.fis.com/", "delete");
    private final static QName _FindAllResponse_QNAME = new QName("http://smslog.esme.fis.com/", "findAllResponse");
    private final static QName _GetServiceActive_QNAME = new QName("http://smslog.esme.fis.com/", "getServiceActive");
    private final static QName _FindAllNoPaperResponse_QNAME = new QName("http://smslog.esme.fis.com/", "findAllNoPaperResponse");
    private final static QName _Update_QNAME = new QName("http://smslog.esme.fis.com/", "update");
    private final static QName _Exception_QNAME = new QName("http://smslog.esme.fis.com/", "Exception");
    private final static QName _FindAllNoPaper_QNAME = new QName("http://smslog.esme.fis.com/", "findAllNoPaper");
    private final static QName _Count_QNAME = new QName("http://smslog.esme.fis.com/", "count");
    private final static QName _ReportInfo_QNAME = new QName("http://smslog.esme.fis.com/", "reportInfo");
    private final static QName _CheckConstraintsResponse_QNAME = new QName("http://smslog.esme.fis.com/", "checkConstraintsResponse");
    private final static QName _GetShortCodeActiveResponse_QNAME = new QName("http://smslog.esme.fis.com/", "getShortCodeActiveResponse");
    private final static QName _GetShortCodeActive_QNAME = new QName("http://smslog.esme.fis.com/", "getShortCodeActive");
    private final static QName _Add_QNAME = new QName("http://smslog.esme.fis.com/", "add");
    private final static QName _ReportInfoResponse_QNAME = new QName("http://smslog.esme.fis.com/", "reportInfoResponse");
    private final static QName _CheckExistedResponse_QNAME = new QName("http://smslog.esme.fis.com/", "checkExistedResponse");
    private final static QName _DeleteResponse_QNAME = new QName("http://smslog.esme.fis.com/", "deleteResponse");
    private final static QName _GetServiceActiveResponse_QNAME = new QName("http://smslog.esme.fis.com/", "getServiceActiveResponse");
    private final static QName _LookUpInfo_QNAME = new QName("http://smslog.esme.fis.com/", "lookUpInfo");
    private final static QName _AddResponse_QNAME = new QName("http://smslog.esme.fis.com/", "addResponse");
    private final static QName _LookUpInfoResponse_QNAME = new QName("http://smslog.esme.fis.com/", "lookUpInfoResponse");
    private final static QName _CountResponse_QNAME = new QName("http://smslog.esme.fis.com/", "countResponse");
    private final static QName _GetCommandActive_QNAME = new QName("http://smslog.esme.fis.com/", "getCommandActive");
    private final static QName _FindAll_QNAME = new QName("http://smslog.esme.fis.com/", "findAll");
    private final static QName _CheckConstraints_QNAME = new QName("http://smslog.esme.fis.com/", "checkConstraints");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fis.esme.smslog
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
     * Create an instance of {@link FindAllNoPaperResponse }
     * 
     */
    public FindAllNoPaperResponse createFindAllNoPaperResponse() {
        return new FindAllNoPaperResponse();
    }

    /**
     * Create an instance of {@link Count }
     * 
     */
    public Count createCount() {
        return new Count();
    }

    /**
     * Create an instance of {@link FindAllNoPaper }
     * 
     */
    public FindAllNoPaper createFindAllNoPaper() {
        return new FindAllNoPaper();
    }

    /**
     * Create an instance of {@link GetShortCodeActiveResponse }
     * 
     */
    public GetShortCodeActiveResponse createGetShortCodeActiveResponse() {
        return new GetShortCodeActiveResponse();
    }

    /**
     * Create an instance of {@link CheckConstraintsResponse }
     * 
     */
    public CheckConstraintsResponse createCheckConstraintsResponse() {
        return new CheckConstraintsResponse();
    }

    /**
     * Create an instance of {@link ReportInfo }
     * 
     */
    public ReportInfo createReportInfo() {
        return new ReportInfo();
    }

    /**
     * Create an instance of {@link Add }
     * 
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link GetShortCodeActive }
     * 
     */
    public GetShortCodeActive createGetShortCodeActive() {
        return new GetShortCodeActive();
    }

    /**
     * Create an instance of {@link GetCommandActiveResponse }
     * 
     */
    public GetCommandActiveResponse createGetCommandActiveResponse() {
        return new GetCommandActiveResponse();
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
     * Create an instance of {@link GetServiceActive }
     * 
     */
    public GetServiceActive createGetServiceActive() {
        return new GetServiceActive();
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
     * Create an instance of {@link GetCommandActive }
     * 
     */
    public GetCommandActive createGetCommandActive() {
        return new GetCommandActive();
    }

    /**
     * Create an instance of {@link LookUpInfo }
     * 
     */
    public LookUpInfo createLookUpInfo() {
        return new LookUpInfo();
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link LookUpInfoResponse }
     * 
     */
    public LookUpInfoResponse createLookUpInfoResponse() {
        return new LookUpInfoResponse();
    }

    /**
     * Create an instance of {@link FindAll }
     * 
     */
    public FindAll createFindAll() {
        return new FindAll();
    }

    /**
     * Create an instance of {@link CheckConstraints }
     * 
     */
    public CheckConstraints createCheckConstraints() {
        return new CheckConstraints();
    }

    /**
     * Create an instance of {@link DeleteResponse }
     * 
     */
    public DeleteResponse createDeleteResponse() {
        return new DeleteResponse();
    }

    /**
     * Create an instance of {@link ReportInfoResponse }
     * 
     */
    public ReportInfoResponse createReportInfoResponse() {
        return new ReportInfoResponse();
    }

    /**
     * Create an instance of {@link CheckExistedResponse }
     * 
     */
    public CheckExistedResponse createCheckExistedResponse() {
        return new CheckExistedResponse();
    }

    /**
     * Create an instance of {@link GetServiceActiveResponse }
     * 
     */
    public GetServiceActiveResponse createGetServiceActiveResponse() {
        return new GetServiceActiveResponse();
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
     * Create an instance of {@link SubSearchDetail }
     * 
     */
    public SubSearchDetail createSubSearchDetail() {
        return new SubSearchDetail();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "updateResponse")
    public JAXBElement<UpdateResponse> createUpdateResponse(UpdateResponse value) {
        return new JAXBElement<UpdateResponse>(_UpdateResponse_QNAME, UpdateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommandActiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "getCommandActiveResponse")
    public JAXBElement<GetCommandActiveResponse> createGetCommandActiveResponse(GetCommandActiveResponse value) {
        return new JAXBElement<GetCommandActiveResponse>(_GetCommandActiveResponse_QNAME, GetCommandActiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckExisted }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "checkExisted")
    public JAXBElement<CheckExisted> createCheckExisted(CheckExisted value) {
        return new JAXBElement<CheckExisted>(_CheckExisted_QNAME, CheckExisted.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Delete }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "delete")
    public JAXBElement<Delete> createDelete(Delete value) {
        return new JAXBElement<Delete>(_Delete_QNAME, Delete.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "findAllResponse")
    public JAXBElement<FindAllResponse> createFindAllResponse(FindAllResponse value) {
        return new JAXBElement<FindAllResponse>(_FindAllResponse_QNAME, FindAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServiceActive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "getServiceActive")
    public JAXBElement<GetServiceActive> createGetServiceActive(GetServiceActive value) {
        return new JAXBElement<GetServiceActive>(_GetServiceActive_QNAME, GetServiceActive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllNoPaperResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "findAllNoPaperResponse")
    public JAXBElement<FindAllNoPaperResponse> createFindAllNoPaperResponse(FindAllNoPaperResponse value) {
        return new JAXBElement<FindAllNoPaperResponse>(_FindAllNoPaperResponse_QNAME, FindAllNoPaperResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Update }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "update")
    public JAXBElement<Update> createUpdate(Update value) {
        return new JAXBElement<Update>(_Update_QNAME, Update.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllNoPaper }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "findAllNoPaper")
    public JAXBElement<FindAllNoPaper> createFindAllNoPaper(FindAllNoPaper value) {
        return new JAXBElement<FindAllNoPaper>(_FindAllNoPaper_QNAME, FindAllNoPaper.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Count }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "count")
    public JAXBElement<Count> createCount(Count value) {
        return new JAXBElement<Count>(_Count_QNAME, Count.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReportInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "reportInfo")
    public JAXBElement<ReportInfo> createReportInfo(ReportInfo value) {
        return new JAXBElement<ReportInfo>(_ReportInfo_QNAME, ReportInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckConstraintsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "checkConstraintsResponse")
    public JAXBElement<CheckConstraintsResponse> createCheckConstraintsResponse(CheckConstraintsResponse value) {
        return new JAXBElement<CheckConstraintsResponse>(_CheckConstraintsResponse_QNAME, CheckConstraintsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShortCodeActiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "getShortCodeActiveResponse")
    public JAXBElement<GetShortCodeActiveResponse> createGetShortCodeActiveResponse(GetShortCodeActiveResponse value) {
        return new JAXBElement<GetShortCodeActiveResponse>(_GetShortCodeActiveResponse_QNAME, GetShortCodeActiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShortCodeActive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "getShortCodeActive")
    public JAXBElement<GetShortCodeActive> createGetShortCodeActive(GetShortCodeActive value) {
        return new JAXBElement<GetShortCodeActive>(_GetShortCodeActive_QNAME, GetShortCodeActive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Add }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "add")
    public JAXBElement<Add> createAdd(Add value) {
        return new JAXBElement<Add>(_Add_QNAME, Add.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReportInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "reportInfoResponse")
    public JAXBElement<ReportInfoResponse> createReportInfoResponse(ReportInfoResponse value) {
        return new JAXBElement<ReportInfoResponse>(_ReportInfoResponse_QNAME, ReportInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckExistedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "checkExistedResponse")
    public JAXBElement<CheckExistedResponse> createCheckExistedResponse(CheckExistedResponse value) {
        return new JAXBElement<CheckExistedResponse>(_CheckExistedResponse_QNAME, CheckExistedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "deleteResponse")
    public JAXBElement<DeleteResponse> createDeleteResponse(DeleteResponse value) {
        return new JAXBElement<DeleteResponse>(_DeleteResponse_QNAME, DeleteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServiceActiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "getServiceActiveResponse")
    public JAXBElement<GetServiceActiveResponse> createGetServiceActiveResponse(GetServiceActiveResponse value) {
        return new JAXBElement<GetServiceActiveResponse>(_GetServiceActiveResponse_QNAME, GetServiceActiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookUpInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "lookUpInfo")
    public JAXBElement<LookUpInfo> createLookUpInfo(LookUpInfo value) {
        return new JAXBElement<LookUpInfo>(_LookUpInfo_QNAME, LookUpInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "addResponse")
    public JAXBElement<AddResponse> createAddResponse(AddResponse value) {
        return new JAXBElement<AddResponse>(_AddResponse_QNAME, AddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookUpInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "lookUpInfoResponse")
    public JAXBElement<LookUpInfoResponse> createLookUpInfoResponse(LookUpInfoResponse value) {
        return new JAXBElement<LookUpInfoResponse>(_LookUpInfoResponse_QNAME, LookUpInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "countResponse")
    public JAXBElement<CountResponse> createCountResponse(CountResponse value) {
        return new JAXBElement<CountResponse>(_CountResponse_QNAME, CountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommandActive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "getCommandActive")
    public JAXBElement<GetCommandActive> createGetCommandActive(GetCommandActive value) {
        return new JAXBElement<GetCommandActive>(_GetCommandActive_QNAME, GetCommandActive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "findAll")
    public JAXBElement<FindAll> createFindAll(FindAll value) {
        return new JAXBElement<FindAll>(_FindAll_QNAME, FindAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckConstraints }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://smslog.esme.fis.com/", name = "checkConstraints")
    public JAXBElement<CheckConstraints> createCheckConstraints(CheckConstraints value) {
        return new JAXBElement<CheckConstraints>(_CheckConstraints_QNAME, CheckConstraints.class, null, value);
    }

}
