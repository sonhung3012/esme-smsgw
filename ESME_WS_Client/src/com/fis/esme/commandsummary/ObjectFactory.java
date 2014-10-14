
package com.fis.esme.commandsummary;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.fis.esme.persistence.EsmeDailyCommandSummary;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fis.esme.commandsummary package. 
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

    private final static QName _FindAllByDateResponse_QNAME = new QName("http://commandsummary.esme.fis.com/", "findAllByDateResponse");
    private final static QName _FindAllInDateByField_QNAME = new QName("http://commandsummary.esme.fis.com/", "findAllInDateByField");
    private final static QName _FindAllInDateByFieldResponse_QNAME = new QName("http://commandsummary.esme.fis.com/", "findAllInDateByFieldResponse");
    private final static QName _FindAllByDateByListFieldResponse_QNAME = new QName("http://commandsummary.esme.fis.com/", "findAllByDateByListFieldResponse");
    private final static QName _FindAllByDateAndIdResponse_QNAME = new QName("http://commandsummary.esme.fis.com/", "findAllByDateAndIdResponse");
    private final static QName _FindAllByDateByListField_QNAME = new QName("http://commandsummary.esme.fis.com/", "findAllByDateByListField");
    private final static QName _Exception_QNAME = new QName("http://commandsummary.esme.fis.com/", "Exception");
    private final static QName _FindAllByDate_QNAME = new QName("http://commandsummary.esme.fis.com/", "findAllByDate");
    private final static QName _FindAllByDateAndId_QNAME = new QName("http://commandsummary.esme.fis.com/", "findAllByDateAndId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fis.esme.commandsummary
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FindAllByDate }
     * 
     */
    public FindAllByDate createFindAllByDate() {
        return new FindAllByDate();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link FindAllByDateAndId }
     * 
     */
    public FindAllByDateAndId createFindAllByDateAndId() {
        return new FindAllByDateAndId();
    }

    /**
     * Create an instance of {@link FindAllByDateByListFieldResponse }
     * 
     */
    public FindAllByDateByListFieldResponse createFindAllByDateByListFieldResponse() {
        return new FindAllByDateByListFieldResponse();
    }

    /**
     * Create an instance of {@link FindAllInDateByFieldResponse }
     * 
     */
    public FindAllInDateByFieldResponse createFindAllInDateByFieldResponse() {
        return new FindAllInDateByFieldResponse();
    }

    /**
     * Create an instance of {@link FindAllByDateByListField }
     * 
     */
    public FindAllByDateByListField createFindAllByDateByListField() {
        return new FindAllByDateByListField();
    }

    /**
     * Create an instance of {@link FindAllByDateAndIdResponse }
     * 
     */
    public FindAllByDateAndIdResponse createFindAllByDateAndIdResponse() {
        return new FindAllByDateAndIdResponse();
    }

    /**
     * Create an instance of {@link FindAllInDateByField }
     * 
     */
    public FindAllInDateByField createFindAllInDateByField() {
        return new FindAllInDateByField();
    }

    /**
     * Create an instance of {@link FindAllByDateResponse }
     * 
     */
    public FindAllByDateResponse createFindAllByDateResponse() {
        return new FindAllByDateResponse();
    }

    /**
     * Create an instance of {@link EsmeDailyCommandSummary }
     * 
     */
    public EsmeDailyCommandSummary createEsmeDailyCommandSummary() {
        return new EsmeDailyCommandSummary();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllByDateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://commandsummary.esme.fis.com/", name = "findAllByDateResponse")
    public JAXBElement<FindAllByDateResponse> createFindAllByDateResponse(FindAllByDateResponse value) {
        return new JAXBElement<FindAllByDateResponse>(_FindAllByDateResponse_QNAME, FindAllByDateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllInDateByField }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://commandsummary.esme.fis.com/", name = "findAllInDateByField")
    public JAXBElement<FindAllInDateByField> createFindAllInDateByField(FindAllInDateByField value) {
        return new JAXBElement<FindAllInDateByField>(_FindAllInDateByField_QNAME, FindAllInDateByField.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllInDateByFieldResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://commandsummary.esme.fis.com/", name = "findAllInDateByFieldResponse")
    public JAXBElement<FindAllInDateByFieldResponse> createFindAllInDateByFieldResponse(FindAllInDateByFieldResponse value) {
        return new JAXBElement<FindAllInDateByFieldResponse>(_FindAllInDateByFieldResponse_QNAME, FindAllInDateByFieldResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllByDateByListFieldResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://commandsummary.esme.fis.com/", name = "findAllByDateByListFieldResponse")
    public JAXBElement<FindAllByDateByListFieldResponse> createFindAllByDateByListFieldResponse(FindAllByDateByListFieldResponse value) {
        return new JAXBElement<FindAllByDateByListFieldResponse>(_FindAllByDateByListFieldResponse_QNAME, FindAllByDateByListFieldResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllByDateAndIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://commandsummary.esme.fis.com/", name = "findAllByDateAndIdResponse")
    public JAXBElement<FindAllByDateAndIdResponse> createFindAllByDateAndIdResponse(FindAllByDateAndIdResponse value) {
        return new JAXBElement<FindAllByDateAndIdResponse>(_FindAllByDateAndIdResponse_QNAME, FindAllByDateAndIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllByDateByListField }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://commandsummary.esme.fis.com/", name = "findAllByDateByListField")
    public JAXBElement<FindAllByDateByListField> createFindAllByDateByListField(FindAllByDateByListField value) {
        return new JAXBElement<FindAllByDateByListField>(_FindAllByDateByListField_QNAME, FindAllByDateByListField.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://commandsummary.esme.fis.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllByDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://commandsummary.esme.fis.com/", name = "findAllByDate")
    public JAXBElement<FindAllByDate> createFindAllByDate(FindAllByDate value) {
        return new JAXBElement<FindAllByDate>(_FindAllByDate_QNAME, FindAllByDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllByDateAndId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://commandsummary.esme.fis.com/", name = "findAllByDateAndId")
    public JAXBElement<FindAllByDateAndId> createFindAllByDateAndId(FindAllByDateAndId value) {
        return new JAXBElement<FindAllByDateAndId>(_FindAllByDateAndId_QNAME, FindAllByDateAndId.class, null, value);
    }

}
