package com.fis.esme.smscrouting;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2013-12-11T17:17:39.968+07:00
 * Generated source version: 2.4.1
 * 
 */
@WebServiceClient(name = "SmscRoutingTransfererService", 
                  wsdlLocation = "http://localhost:1080/ESME_Business/services/SmscRoutingTransfererPort?wsdl",
                  targetNamespace = "http://smscrouting.esme.fis.com/") 
public class SmscRoutingTransfererService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://smscrouting.esme.fis.com/", "SmscRoutingTransfererService");
    public final static QName SmscRoutingTransfererPort = new QName("http://smscrouting.esme.fis.com/", "SmscRoutingTransfererPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:1080/ESME_Business/services/SmscRoutingTransfererPort?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SmscRoutingTransfererService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:1080/ESME_Business/services/SmscRoutingTransfererPort?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SmscRoutingTransfererService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SmscRoutingTransfererService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SmscRoutingTransfererService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns SmscRoutingTransferer
     */
    @WebEndpoint(name = "SmscRoutingTransfererPort")
    public SmscRoutingTransferer getSmscRoutingTransfererPort() {
        return super.getPort(SmscRoutingTransfererPort, SmscRoutingTransferer.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SmscRoutingTransferer
     */
    @WebEndpoint(name = "SmscRoutingTransfererPort")
    public SmscRoutingTransferer getSmscRoutingTransfererPort(WebServiceFeature... features) {
        return super.getPort(SmscRoutingTransfererPort, SmscRoutingTransferer.class, features);
    }

}
