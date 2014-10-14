package com.fis.esme.smslog;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2014-03-31T11:17:42.777+07:00
 * Generated source version: 2.4.1
 * 
 */
@WebServiceClient(name = "EsmeSmsLogTransfererService", 
                  wsdlLocation = "http://localhost:7080/ESME_Business/services/EsmeSmsLogTransfererPort?wsdl",
                  targetNamespace = "http://smslog.esme.fis.com/") 
public class EsmeSmsLogTransfererService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://smslog.esme.fis.com/", "EsmeSmsLogTransfererService");
    public final static QName EsmeSmsLogTransfererPort = new QName("http://smslog.esme.fis.com/", "EsmeSmsLogTransfererPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:7080/ESME_Business/services/EsmeSmsLogTransfererPort?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(EsmeSmsLogTransfererService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:7080/ESME_Business/services/EsmeSmsLogTransfererPort?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public EsmeSmsLogTransfererService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public EsmeSmsLogTransfererService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EsmeSmsLogTransfererService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns EsmeSmsLogTransferer
     */
    @WebEndpoint(name = "EsmeSmsLogTransfererPort")
    public EsmeSmsLogTransferer getEsmeSmsLogTransfererPort() {
        return super.getPort(EsmeSmsLogTransfererPort, EsmeSmsLogTransferer.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EsmeSmsLogTransferer
     */
    @WebEndpoint(name = "EsmeSmsLogTransfererPort")
    public EsmeSmsLogTransferer getEsmeSmsLogTransfererPort(WebServiceFeature... features) {
        return super.getPort(EsmeSmsLogTransfererPort, EsmeSmsLogTransferer.class, features);
    }

}
