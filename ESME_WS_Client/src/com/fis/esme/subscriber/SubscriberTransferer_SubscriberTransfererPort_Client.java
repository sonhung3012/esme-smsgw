
package com.fis.esme.subscriber;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2014-09-30T17:26:21.647+07:00
 * Generated source version: 2.4.1
 * 
 */
public final class SubscriberTransferer_SubscriberTransfererPort_Client {

    private static final QName SERVICE_NAME = new QName("http://subscriber.esme.fis.com/", "SubscriberTransfererService");

    private SubscriberTransferer_SubscriberTransfererPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SubscriberTransfererService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        SubscriberTransfererService ss = new SubscriberTransfererService(wsdlURL, SERVICE_NAME);
        SubscriberTransferer port = ss.getSubscriberTransfererPort();  
        
        {
        System.out.println("Invoking update...");
        com.fis.esme.persistence.EsmeSubscriber _update_arg0 = new com.fis.esme.persistence.EsmeSubscriber();
        _update_arg0.setAddress("Address-1238713626");
        _update_arg0.setBirthDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.717+07:00"));
        _update_arg0.setCreateDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.719+07:00"));
        _update_arg0.setEmail("Email807196455");
        _update_arg0.setMsisdn("Msisdn-94725065");
        _update_arg0.setSex("Sex-1689801584");
        _update_arg0.setStatus("Status-106479265");
        _update_arg0.setSubId(1198581518584524229l);
        try {
            port.update(_update_arg0);

        } catch (Exception_Exception e) { 
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking findAllWithOrderPaging...");
        com.fis.esme.persistence.EsmeSubscriber _findAllWithOrderPaging_arg0 = new com.fis.esme.persistence.EsmeSubscriber();
        _findAllWithOrderPaging_arg0.setAddress("Address-1536427432");
        _findAllWithOrderPaging_arg0.setBirthDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.725+07:00"));
        _findAllWithOrderPaging_arg0.setCreateDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.725+07:00"));
        _findAllWithOrderPaging_arg0.setEmail("Email-370235202");
        _findAllWithOrderPaging_arg0.setMsisdn("Msisdn617193901");
        _findAllWithOrderPaging_arg0.setSex("Sex9063772");
        _findAllWithOrderPaging_arg0.setStatus("Status-1407832067");
        _findAllWithOrderPaging_arg0.setSubId(7652113973666051956l);
        java.lang.String _findAllWithOrderPaging_arg1 = "_findAllWithOrderPaging_arg1-569734778";
        boolean _findAllWithOrderPaging_arg2 = false;
        int _findAllWithOrderPaging_arg3 = -842338648;
        int _findAllWithOrderPaging_arg4 = -783080873;
        boolean _findAllWithOrderPaging_arg5 = false;
        try {
            java.util.List<com.fis.esme.persistence.EsmeSubscriber> _findAllWithOrderPaging__return = port.findAllWithOrderPaging(_findAllWithOrderPaging_arg0, _findAllWithOrderPaging_arg1, _findAllWithOrderPaging_arg2, _findAllWithOrderPaging_arg3, _findAllWithOrderPaging_arg4, _findAllWithOrderPaging_arg5);
            System.out.println("findAllWithOrderPaging.result=" + _findAllWithOrderPaging__return);

        } catch (Exception_Exception e) { 
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking findAllWithoutParameter...");
        try {
            java.util.List<com.fis.esme.persistence.EsmeSubscriber> _findAllWithoutParameter__return = port.findAllWithoutParameter();
            System.out.println("findAllWithoutParameter.result=" + _findAllWithoutParameter__return);

        } catch (Exception_Exception e) { 
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking add...");
        com.fis.esme.persistence.EsmeSubscriber _add_arg0 = new com.fis.esme.persistence.EsmeSubscriber();
        _add_arg0.setAddress("Address1433974001");
        _add_arg0.setBirthDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.728+07:00"));
        _add_arg0.setCreateDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.729+07:00"));
        _add_arg0.setEmail("Email1426754615");
        _add_arg0.setMsisdn("Msisdn1318744884");
        _add_arg0.setSex("Sex-1353355081");
        _add_arg0.setStatus("Status105135343");
        _add_arg0.setSubId(7957618915937397600l);
        try {
            java.lang.Long _add__return = port.add(_add_arg0);
            System.out.println("add.result=" + _add__return);

        } catch (Exception_Exception e) { 
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking checkConstraints...");
        java.lang.Long _checkConstraints_arg0 = Long.valueOf(-1443553376567413427l);
        boolean _checkConstraints__return = port.checkConstraints(_checkConstraints_arg0);
        System.out.println("checkConstraints.result=" + _checkConstraints__return);


        }
        {
        System.out.println("Invoking delete...");
        com.fis.esme.persistence.EsmeSubscriber _delete_arg0 = new com.fis.esme.persistence.EsmeSubscriber();
        _delete_arg0.setAddress("Address-1127016471");
        _delete_arg0.setBirthDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.731+07:00"));
        _delete_arg0.setCreateDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.732+07:00"));
        _delete_arg0.setEmail("Email1926759166");
        _delete_arg0.setMsisdn("Msisdn1854624530");
        _delete_arg0.setSex("Sex-370375213");
        _delete_arg0.setStatus("Status1595436162");
        _delete_arg0.setSubId(-4446811417308326454l);
        try {
            port.delete(_delete_arg0);

        } catch (Exception_Exception e) { 
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking count...");
        com.fis.esme.persistence.EsmeSubscriber _count_arg0 = new com.fis.esme.persistence.EsmeSubscriber();
        _count_arg0.setAddress("Address-1736849239");
        _count_arg0.setBirthDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.733+07:00"));
        _count_arg0.setCreateDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.734+07:00"));
        _count_arg0.setEmail("Email-1954724074");
        _count_arg0.setMsisdn("Msisdn1414804954");
        _count_arg0.setSex("Sex367095960");
        _count_arg0.setStatus("Status1357530446");
        _count_arg0.setSubId(-5311434635148053035l);
        boolean _count_arg1 = true;
        int _count__return = port.count(_count_arg0, _count_arg1);
        System.out.println("count.result=" + _count__return);


        }
        {
        System.out.println("Invoking checkExisted...");
        com.fis.esme.persistence.EsmeSubscriber _checkExisted_arg0 = new com.fis.esme.persistence.EsmeSubscriber();
        _checkExisted_arg0.setAddress("Address-2112543026");
        _checkExisted_arg0.setBirthDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.735+07:00"));
        _checkExisted_arg0.setCreateDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:26:21.736+07:00"));
        _checkExisted_arg0.setEmail("Email-885791036");
        _checkExisted_arg0.setMsisdn("Msisdn-1394517979");
        _checkExisted_arg0.setSex("Sex-1380704757");
        _checkExisted_arg0.setStatus("Status-1202298572");
        _checkExisted_arg0.setSubId(8709465826917457306l);
        int _checkExisted__return = port.checkExisted(_checkExisted_arg0);
        System.out.println("checkExisted.result=" + _checkExisted__return);


        }

        System.exit(0);
    }

}
