package com.fis.esme.message;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2014-11-14T17:30:40.307+07:00
 * Generated source version: 2.4.1
 * 
 */
@WebServiceClient(name = "MessageTransfererService", 
                  wsdlLocation = "file:/media/sonhung/MOUNTHERO/FIS-TES/Ubuntu/Ubuntu-FIS-TES-Projects/git-ESME/ESME_Business/WebContent/wsdl/messagetransferer.wsdl",
                  targetNamespace = "http://message.esme.fis.com/") 
public class MessageTransfererService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://message.esme.fis.com/", "MessageTransfererService");
    public final static QName MessageTransfererPort = new QName("http://message.esme.fis.com/", "MessageTransfererPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/media/sonhung/MOUNTHERO/FIS-TES/Ubuntu/Ubuntu-FIS-TES-Projects/git-ESME/ESME_Business/WebContent/wsdl/messagetransferer.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(MessageTransfererService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/media/sonhung/MOUNTHERO/FIS-TES/Ubuntu/Ubuntu-FIS-TES-Projects/git-ESME/ESME_Business/WebContent/wsdl/messagetransferer.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public MessageTransfererService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public MessageTransfererService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MessageTransfererService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns MessageTransferer
     */
    @WebEndpoint(name = "MessageTransfererPort")
    public MessageTransferer getMessageTransfererPort() {
        return super.getPort(MessageTransfererPort, MessageTransferer.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MessageTransferer
     */
    @WebEndpoint(name = "MessageTransfererPort")
    public MessageTransferer getMessageTransfererPort(WebServiceFeature... features) {
        return super.getPort(MessageTransfererPort, MessageTransferer.class, features);
    }

}
