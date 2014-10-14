
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.fis.esme.groups;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2014-09-30T17:25:06.087+07:00
 * Generated source version: 2.4.1
 * 
 */

@javax.jws.WebService(
                      serviceName = "GroupsTransfererService",
                      portName = "GroupsTransfererPort",
                      targetNamespace = "http://groups.esme.fis.com/",
                      wsdlLocation = "http://localhost:7080/ESME_Business/services/GroupsTransfererPort?wsdl",
                      endpointInterface = "com.fis.esme.groups.GroupsTransferer")
                      
public class GroupsTransfererImpl implements GroupsTransferer {

    private static final Logger LOG = Logger.getLogger(GroupsTransfererImpl.class.getName());

    /* (non-Javadoc)
     * @see com.fis.esme.groups.GroupsTransferer#update(com.fis.esme.groups.EsmeGroups  arg0 )*
     */
    public void update(com.fis.esme.persistence.EsmeGroups arg0) throws Exception_Exception    { 
        LOG.info("Executing operation update");
        System.out.println(arg0);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new Exception_Exception("Exception...");
    }

    /* (non-Javadoc)
     * @see com.fis.esme.groups.GroupsTransferer#count(com.fis.esme.groups.EsmeGroups  arg0 ,)boolean  arg1 )*
     */
    public int count(com.fis.esme.persistence.EsmeGroups arg0,boolean arg1) { 
        LOG.info("Executing operation count");
        System.out.println(arg0);
        System.out.println(arg1);
        try {
            int _return = 370096927;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.fis.esme.groups.GroupsTransferer#findAllWithOrderPaging(com.fis.esme.groups.EsmeGroups  arg0 ,)java.lang.String  arg1 ,)boolean  arg2 ,)int  arg3 ,)int  arg4 ,)boolean  arg5 )*
     */
    public java.util.List<com.fis.esme.persistence.EsmeGroups> findAllWithOrderPaging(com.fis.esme.persistence.EsmeGroups arg0,java.lang.String arg1,boolean arg2,int arg3,int arg4,boolean arg5) throws Exception_Exception    { 
        LOG.info("Executing operation findAllWithOrderPaging");
        System.out.println(arg0);
        System.out.println(arg1);
        System.out.println(arg2);
        System.out.println(arg3);
        System.out.println(arg4);
        System.out.println(arg5);
        try {
            java.util.List<com.fis.esme.persistence.EsmeGroups> _return = new java.util.ArrayList<com.fis.esme.persistence.EsmeGroups>();
            com.fis.esme.persistence.EsmeGroups _returnVal1 = new com.fis.esme.persistence.EsmeGroups();
            _returnVal1.setCreateDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:25:06.129+07:00"));
            _returnVal1.setDescription("Description942674833");
            _returnVal1.setGroupId(-4207136848371568813l);
            _returnVal1.setName("Name557621119");
            _returnVal1.setParentId(7888741472935126248l);
            _returnVal1.setRootId(6116607865435073000l);
            _returnVal1.setStatus("Status1251356943");
            _return.add(_returnVal1);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new Exception_Exception("Exception...");
    }

    /* (non-Javadoc)
     * @see com.fis.esme.groups.GroupsTransferer#findAllWithoutParameter(*
     */
    public java.util.List<com.fis.esme.persistence.EsmeGroups> findAllWithoutParameter() throws Exception_Exception    { 
        LOG.info("Executing operation findAllWithoutParameter");
        try {
            java.util.List<com.fis.esme.persistence.EsmeGroups> _return = new java.util.ArrayList<com.fis.esme.persistence.EsmeGroups>();
            com.fis.esme.persistence.EsmeGroups _returnVal1 = new com.fis.esme.persistence.EsmeGroups();
            _returnVal1.setCreateDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-09-30T17:25:06.131+07:00"));
            _returnVal1.setDescription("Description776642087");
            _returnVal1.setGroupId(-3626181409305307701l);
            _returnVal1.setName("Name1448425295");
            _returnVal1.setParentId(693743646500674283l);
            _returnVal1.setRootId(-3567247747860409340l);
            _returnVal1.setStatus("Status-2050157177");
            _return.add(_returnVal1);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new Exception_Exception("Exception...");
    }

    /* (non-Javadoc)
     * @see com.fis.esme.groups.GroupsTransferer#checkExisted(com.fis.esme.groups.EsmeGroups  arg0 )*
     */
    public int checkExisted(com.fis.esme.persistence.EsmeGroups arg0) { 
        LOG.info("Executing operation checkExisted");
        System.out.println(arg0);
        try {
            int _return = -1109361755;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.fis.esme.groups.GroupsTransferer#checkConstraints(java.lang.Long  arg0 )*
     */
    public boolean checkConstraints(java.lang.Long arg0) { 
        LOG.info("Executing operation checkConstraints");
        System.out.println(arg0);
        try {
            boolean _return = false;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.fis.esme.groups.GroupsTransferer#delete(com.fis.esme.groups.EsmeGroups  arg0 )*
     */
    public void delete(com.fis.esme.persistence.EsmeGroups arg0) throws Exception_Exception    { 
        LOG.info("Executing operation delete");
        System.out.println(arg0);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new Exception_Exception("Exception...");
    }

    /* (non-Javadoc)
     * @see com.fis.esme.groups.GroupsTransferer#add(com.fis.esme.groups.EsmeGroups  arg0 )*
     */
    public java.lang.Long add(com.fis.esme.persistence.EsmeGroups arg0) throws Exception_Exception    { 
        LOG.info("Executing operation add");
        System.out.println(arg0);
        try {
            java.lang.Long _return = Long.valueOf(-9184249792107831455l);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new Exception_Exception("Exception...");
    }

}
