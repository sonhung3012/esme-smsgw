/*
 *  jSoapServer is a Java library implementing a multi-threaded
 *  soap server which can be easily integrated into java applications
 *  to provide a SOAP Interface for external programmers.
 *  
 *  Copyright (C) 2007 Martin Thelian
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *  
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *  
 *  For more information, please email thelian@users.sourceforge.net
 */

/* =======================================================================
 * Revision Control Information
 * $Source: /cvsroot/jsoapserver/jSoapServer/src/main/java/org/jSoapServer/SoapHandler.java,v $
 * $Author: thelian $
 * $Date: 2012/04/12 16:23:15 $
 * $Revision: 1.3 $
 * ======================================================================= */

package com.fis.esme.core.services.soap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.namespace.QName;
import org.jSoapServer.SoapData;
import org.apache.axis.AxisFault;
import org.apache.axis.ConfigurationException;
import org.apache.axis.Constants;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ServiceDesc;
import org.apache.axis.encoding.Base64;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.message.SOAPFault;
import org.apache.axis.server.AxisServer;
import org.apache.axis.transport.http.ChunkedInputStream;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.axis.utils.XMLUtils;
import org.apache.commons.httpclient.ContentLengthInputStream;
import org.jSoapServer.SoapServerException;
import org.jSoapServer.http.HttpHeaders;
import org.jSoapServer.http.HttpRequestLine;
import org.jSoapServer.http.HttpResponseLine;
import org.jSoapServer.utils.FileUtils;
import org.quickserver.net.server.ClientBinaryHandler;
import org.quickserver.net.server.ClientEventHandler;
import org.quickserver.net.server.ClientExtendedEventHandler;
import org.quickserver.net.server.ClientHandler;
import org.quickserver.net.server.DataMode;
import org.quickserver.net.server.DataType;
import org.quickserver.util.xmlreader.QuickServerConfig;
import org.w3c.dom.Document;

/**
 * 
 * <b>Hint:</b> If you are using jSoapServer without the xml configuration file,
 * ensure that you have registered this class using the following commands
 * 
 * <code>
 *  mySoapServer.setClientBinaryHandler("org.jSoapServer.SoapHandler");
 *  mySoapServer.setClientEventHandler("org.jSoapServer.SoapHandler");
 * </code>
 * 
 * @author Martin Thelian
 */
public  class SoapHandler implements ClientEventHandler, ClientBinaryHandler, ClientExtendedEventHandler {        
    /*
     * Constants for http headers
     */
    public static final String HTTP_HEADER_SOAPACTION = "soapaction";

    
    /* ============================================================
     * Other object fields
     * ============================================================ */    
    /**
     * The logger class that is used by the command handler 
     */
    static Logger logger = Logger.getLogger(SoapHandler.class.getName());
    
    /**
     * The apache axis server engine that is used to process the received soap request messages
     * and to generate soap response messages that will be send back as execution results
     */
    private AxisServer engine = null;
    

    /**
     * @see ClientExtendedEventHandler#handleMaxAuthTry(ClientHandler)
     */
    public void handleMaxAuthTry(ClientHandler handler) throws IOException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see ClientExtendedEventHandler#handleMaxConnection(ClientHandler)
     * @since 0.2.8
     */
    public boolean handleMaxConnection(ClientHandler handler) throws IOException {
        // create the response line
        HttpResponseLine respLine = new HttpResponseLine(
                HttpResponseLine.STATUS_503_Service_Unavailable,
                "Service Unavailable",
                HttpRequestLine.HTTP_1_0
        );
        
        // create the response header
        String respBody = handler.getMaxConnectionMsg();        
        HttpHeaders respHeaders = getResponseHeaders(
                handler, 
                "text/plain; charset=UTF-8", 
                false, 
                null, 
                respBody.length()
        );
        
        // add the retry after header
        // retry it in 30 seconds
        respHeaders.addHeader(HttpHeaders.RETRY_AFTER, "30");
        
        // write out data
        OutputStream out = handler.getOutputStream();
        out.write(respLine.getBytes());
        out.write(respHeaders.getBytes());
        out.write(respBody.getBytes("UTF-8"));
        out.flush();
        
        // close the connection
        handler.closeConnection();
        
        return false;
    }


    /**
     * @see ClientExtendedEventHandler#handleTimeout(ClientHandler)
     */
    public void handleTimeout(ClientHandler handler) throws SocketException, IOException {
        // TODO Auto-generated method stub
        
    }       
    
    
    /**
     * @param handler
     * @throws SocketTimeoutException
     * @throws IOException
     * 
     * @see org.quickserver.net.server.ClientCommandHandler#gotConnected(org.quickserver.net.server.ClientHandler)
     */
    public void gotConnected(ClientHandler handler) throws SocketTimeoutException, IOException {
        assert (handler != null) : "Clienthandler object must not be null";
        
        handler.setDataMode(DataMode.BINARY, DataType.IN);
        handler.setDataMode(DataMode.BINARY, DataType.OUT);
        
        if (logger.isLoggable(Level.FINE))
            logger.fine("Connection opened : " + handler.getSocket().getInetAddress().getHostName());
    }
    
    
    /**
     * @param handler
     * @throws IOException
     * 
     * @see org.quickserver.net.server.ClientCommandHandler#lostConnection(org.quickserver.net.server.ClientHandler)
     */
    public void lostConnection(ClientHandler handler) throws IOException {
        assert (handler != null) : "Clienthandler object must not be null";
        
        if (logger.isLoggable(Level.FINE))
            logger.fine("Connection lost : " + handler.getSocket().getInetAddress().getHostName());
    }
    
    
    /**
     * @param handler
     * @throws IOException
     * 
     * @see org.quickserver.net.server.ClientCommandHandler#closingConnection(org.quickserver.net.server.ClientHandler)
     */
    public void closingConnection(ClientHandler handler) throws IOException {
        assert (handler != null) : "Clienthandler object must not be null";
        
        handler.sendSystemMsg("Connection closed: "+
                handler.getSocket().getInetAddress().getHostName(), Level.FINE);
    }
    
    /**
     * 
     * @param handler
     * @param httpContentType
     * @param keepAlive
     * @param httpContentEncoding
     * @param httpContentLength
     * @return
     * @since 0.2.8
     */
    protected HttpHeaders getResponseHeaders(
            ClientHandler handler,
            String httpContentType,
            boolean keepAlive,
            String httpContentEncoding,
            long httpContentLength
    ) {
        HttpHeaders headers = new HttpHeaders();
        
        headers.addHeader(HttpHeaders.SERVER, handler.getServer().getName()); 
        headers.addHeader(HttpHeaders.DATE,(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)).format(new Date()));        
        headers.addHeader(HttpHeaders.CONNECTION,keepAlive?"keep-alive":"close");
        headers.addHeader(HttpHeaders.ALLOW,"GET, POST");
        headers.addHeader(HttpHeaders.X_POWERED_BY,"jSoapServer " + SoapServer.VERSION);
        headers.addHeader(HttpHeaders.CONTENT_TYPE,httpContentType);
        
        if (httpContentLength > 0) 
            headers.addHeader(HttpHeaders.CONTENT_LENGTH,Long.toString(httpContentLength));
        if (httpContentEncoding != null)
            headers.addHeader(HttpHeaders.CONTENT_ENCODING,httpContentEncoding);
        
        // TODO:
        
        return headers;
    }
    
    /**
     * @param handler
     * @param httpStatusCode
     * @param httpStatusText
     * @param httpContentType
     * @param httpBody
     * @param supportsGzip
     * @throws IOException
     */
    public void sendResponse(
            ClientHandler handler, 
            int httpStatusCode, 
            String httpStatusText,
            String httpContentType,
            byte[] httpBody) 
    throws IOException 
    {
        assert (handler != null) 		 : "Clienthandler object must not be null";
        assert (httpStatusCode > 100) 	 : "Invalid http status code";
        assert (httpStatusText  != null) : "Invalid http status text";
        assert (httpContentType != null) : "Invalid http content type";
        
        
        // getting a handler to the client data object
        SoapData data= (SoapData) handler.getClientData();
                
        // determine if keep alive should be used
        Object[] store = handler.getServer().getStoreObjects();
        boolean keepAliveEnabled =  ((Boolean)((HashMap) store[SoapServer.STORE_JSOAPSERVER_SETTINGS]).get(SoapServer.JSOAPSERVER_ENABLE_KEEP_ALIVE)).booleanValue();
        boolean keepAliveRequested = data.httpHeaders.keepAlive(data.httpReqLine);
        
        boolean connectionKeepAlive = keepAliveEnabled && keepAliveRequested;
        String contentEncoding = null;
        int contentLength = (httpBody == null)? 0:httpBody.length;
        byte[] contentBody = httpBody;
        
        // if we should use content encoding, we now have to encode the body yet
        String acceptEncoding = data.httpHeaders.getHeader(HttpHeaders.ACCEPT_ENCODING);
        if (acceptEncoding != null && httpBody != null) {               
            OutputStream compressedOut = null;            
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream(contentLength);
            
            if (data.httpHeaders.acceptEncoding(HttpHeaders.HTTP_CONTENT_ENCODING_GZIP)) {
                compressedOut = new GZIPOutputStream(byteArray);
                contentEncoding = HttpHeaders.HTTP_CONTENT_ENCODING_GZIP;
            } else if (data.httpHeaders.acceptEncoding(HttpHeaders.HTTP_CONTENT_ENCODING_COMPRESS)) {
                compressedOut = new ZipOutputStream(byteArray);
                ((ZipOutputStream)compressedOut).putNextEntry(new ZipEntry("compressed output"));
                contentEncoding = HttpHeaders.HTTP_CONTENT_ENCODING_COMPRESS;                
            } else {
                acceptEncoding = null;
                compressedOut = byteArray;
            }

            compressedOut.write(httpBody);
            compressedOut.flush();
            compressedOut.close();

            // getting the new content length
            contentLength = byteArray.size();
            
            // getting the compressed body
            contentBody = byteArray.toByteArray();
        }
        
        // create the response line
        HttpResponseLine responseLine = new HttpResponseLine(
                httpStatusCode,
                httpStatusText,
                HttpRequestLine.HTTP_1_0
        );        
        
        // creating the response http headers
        HttpHeaders responseHeaders = getResponseHeaders(
                handler, 
                httpContentType, 
                connectionKeepAlive, 
                contentEncoding, 
                contentLength
        );
        
        // write out the data
        OutputStream out = handler.getOutputStream();
        out.write(responseLine.getBytes());
        out.write(responseHeaders.getBytes());
        out.write(contentBody);
        out.flush();        
        
        // close the connection if necessary
        if (!connectionKeepAlive) {
            handler.closeConnection();
        }    
    }
    
    /**
     * 
     * @param handler
     * @param command
     * @param data
     * @throws IOException
     * 
     * @since 0.25
     */
    private void readHttpHeader(SoapData data) throws IOException  {
        
        // reading the http headers
        data.httpHeaders = HttpHeaders.readFrom(data.inputStream);   	
    }
    
    /**
     * 
     * @param handler
     * @param command
     * @param data
     * @return
     * @throws IOException 
     * @throws IOException
     * 
     * @since 0.25
     */
    private void readHttpCommandLine(SoapData data) throws SoapServerException, IOException  {
                
        // reading the request line
        data.httpReqLine = HttpRequestLine.readFrom(data.inputStream);
        
        // getting the soap service name
        String reqPath = data.httpReqLine.getPath();
        if (reqPath.startsWith("/")) reqPath = reqPath.substring(1);
        data.serviceName = reqPath;
        
    }
    
    /**
     * @param handler
     * @param data
     * @since 0.2.5
     */
    protected void processHttpHeader(ClientHandler handler, SoapData data) {       
        try {   
            String keepAliveTimeoutStr = data.httpHeaders.getHeader(HttpHeaders.KEEP_ALIVE);
            if (keepAliveTimeoutStr != null) {
                try {
                    long keepAliveTimeout = Long.valueOf(keepAliveTimeoutStr).longValue();
                    handler.getSocket().setSoTimeout((int)keepAliveTimeout*1000);
                    return;
                } catch (NumberFormatException numEx) { 
                    /* timeout value could not be parsed */
                }
            }
            handler.getSocket().setSoTimeout(300*1000);
        } catch (SocketException e) {
            /* ignore this error */
        }
    }
    
    /**
     * 
     * @param handler
     * @param data
     * @throws SoapServerException
     * @throws IOException
     * 
     * @since 0.2.6
     */
    protected void setHttpBodyByMethodParam(SoapData data) throws IOException {
        
        String methodName = data.httpReqLine.getParamValue("method");        
        
        StringBuffer fakeBody = new StringBuffer();        
        fakeBody.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"").append(Constants.URI_SOAP12_ENV).append("\">")
                    .append("<SOAP-ENV:Body>")
                        .append("<").append(methodName).append(">")
                            // TODO: what about additional params
                        .append("</").append(methodName).append(">")
                    .append("</SOAP-ENV:Body>")
                .append("</SOAP-ENV:Envelope>");
        
        data.httpBody.write(fakeBody.toString().getBytes("UTF-8"));
    }
    
    /**
     * @param data
     * @throws IOException
     * 
     * @since 0.2.5
     * @since 0.2.8 : support for transfer and content encoded request bodies
     */
    protected void readHttpBody(SoapData data) throws SoapServerException {
        if (data.httpReqLine.getMethodName().equals(HttpRequestLine.HTTP_METHOD_GET)) return;
        
        InputStream body = null;
        try {
            // getting the content length
            long contentLength = data.httpHeaders.getContentLength();
            
            /* =====================================================================
             * Handling of TRANSFER-ENCODING
             * ===================================================================== */
            String transferEncoding = data.httpHeaders.getTransferEncoding();
            
            if (transferEncoding != null) {
                // read the body using transfer encoding
                transferEncoding = transferEncoding.toLowerCase().trim();
                if (transferEncoding.equals(HttpHeaders.TRANSFER_ENCODING_CHUNKED)) {
                    body = new ChunkedInputStream(data.inputStream);
                } else {                    
                    SoapHandler.logger.warning("Unsupported transfer-encoding: "+ transferEncoding);
                    throw new SoapServerException("Unsupported transfer-encoding: " + transferEncoding,501,"Unimplemented");
                }
            } else if (contentLength > 0) {
                // read exactly content length bytes
                body = new ContentLengthInputStream(data.inputStream, contentLength);
            } else {
                // read until EOF
                body = data.inputStream;
            }       
            
            /* =====================================================================
             * Handling of CONTENT-ENCODING
             * ===================================================================== */
            String contentEncoding = data.httpHeaders.getContentEncoding();
            if (contentEncoding != null) {
                if (contentEncoding.equals(HttpHeaders.HTTP_CONTENT_ENCODING_GZIP)) {
                    body = new GZIPInputStream(body);
                } else if (contentEncoding.equals(HttpHeaders.HTTP_CONTENT_ENCODING_COMPRESS)) {
                    ZipInputStream zipIn = new ZipInputStream(body);
                    long entrySize = zipIn.getNextEntry().getSize();
                    if (entrySize == -1) {
                        body = zipIn;
                    } else {
                        body = new ContentLengthInputStream(zipIn,entrySize);
                    }
                } else if (contentEncoding.equals(HttpHeaders.HTTP_CONTENT_ENCODING_DEFLATE)) {
                    body = new InflaterInputStream(body);                    
                } else {
                    throw new SoapServerException("Unsupported content encoding: " + contentEncoding,415,"Unsupported Media Type");
                }
            }
            
            /* =====================================================================
             * READ THE BODY
             * ===================================================================== */
            if (body != null) {
                FileUtils.copy(body, data.httpBody);                       
            }            
        } catch (IOException e) {
            throw new SoapServerException("Unable to read the request body",400,"Bad Request"); 
        }
    }
    
    public void handleBinary(ClientHandler handler, byte[] command) throws SocketTimeoutException, IOException {

        assert (handler != null) : "The commandhandler object must not be null";
        
        try {
			 // getting a handler to the client data object
            SoapData data = (SoapData) handler.getClientData();
			// reseting the data object
            data.clean();
				
            data.inputStream = new PushbackInputStream(handler.getInputStream(),Math.max(512,command.length));
            data.inputStream.unread(command);
            data.clientAddress = handler.getHostAddress();
            
			if(this.engine==null) {
				// getting a reference to the axis engine
				Object[] store = handler.getServer().getStoreObjects();
				this.engine = (AxisServer) store[SoapServer.STORE_AXIS_SERVER];
			}            
            
            // reading the http request line send by the client
            readHttpCommandLine(data);
            
            // reading the http headers send by the client
            readHttpHeader(data);
            processHttpHeader(handler,data);
            
            // reading the http body send by the client
            readHttpBody(data);
            
            // generating the soap message context
            MessageContext msgContext = null;
            
            // generate the message context object
            msgContext = this.generateMessageContext(handler,data);
            
            
            if (data.httpReqLine.getMethodName().equals(HttpRequestLine.HTTP_METHOD_GET)) {
                if (data.httpReqLine.getParamCount() == 0 || data.httpReqLine.hasParam("wsdl")) {
                    this.engine.generateWSDL(msgContext);
                    Document doc = (Document) msgContext.getProperty("WSDL");
                    
                    if (doc != null) {
                        // Converting the the wsdl document into a byte-array
                        String responseDoc = XMLUtils.DocumentToString(doc);
                        
                        this.sendResponse(handler,200, "OK", "text/xml; charset=utf-8", responseDoc.getBytes("UTF-8"));
                        return;                                            
                    }
                } else if (data.httpReqLine.hasParam("method")) {
                    // generating a fake request body
                    this.setHttpBodyByMethodParam(data);
                } else if (data.httpReqLine.hasParam("list")) {
                    String[] serviceList = generateServiceList(); 
                    this.sendResponse(handler,200, "OK", serviceList[0],serviceList[1].getBytes("UTF-8"));
                    return;
                } else {                
                    this.sendResponse(handler,500, "Internal Server Error", "text/plain; charset=UTF-8", "Unable to generate the WSDL file".getBytes("UTF-8"));
                    return;
                }
            }
            
            /*
             * Generating a SOAP Request Message Object from the XML document
             * and store it into the message context
             */
            // TODO: this seems not to work with the full content-type ....
            String mimeType = data.httpHeaders.getMimeType();
            ByteArrayInputStream bodyInputStream = new ByteArrayInputStream(data.httpBody.toByteArray());
            Message requestMsg = new Message(bodyInputStream, false, mimeType, null);
            msgContext.setRequestMessage(requestMsg);
            
            Message responseMsg = null;
            int    serviceInvocationStatusCode = 200;
            String serviceInvocationStatusText = "OK";
            try {
                // invoking the service
                this.engine.invoke(msgContext);
                
                // Retrieve the response from Axis
                responseMsg = msgContext.getResponseMessage();       
            } catch (Exception ex) {
				logger.log(Level.WARNING,"Unexpected error while invoking the webservice",ex);

				AxisFault af;
                if (ex instanceof AxisFault) {
                    af = (AxisFault) ex;
                    
                    QName faultCode = af.getFaultCode();
                    if (Constants.FAULT_SOAP12_SENDER.equals(faultCode))  {
                        serviceInvocationStatusCode = 400;
                        serviceInvocationStatusText = "Bad request";
                    } else if ("Server.Unauthorized".equals(faultCode.getLocalPart()))  {
                        serviceInvocationStatusCode = 401; 
                        serviceInvocationStatusText = "Unauthorized";
                    } else {
                        serviceInvocationStatusCode = 500; 
                        serviceInvocationStatusText = "Internal server error";
                    }                    
                } else {                 
                    serviceInvocationStatusCode = 500; 
                    serviceInvocationStatusText = "Internal server error";
                    af = AxisFault.makeFault(ex);
                }
                
                // There may be headers we want to preserve in the
                // response message - so if it's there, just add the
                // FaultElement to it. Otherwise, make a new one.
                responseMsg = msgContext.getResponseMessage();
                if (responseMsg == null) {
                    responseMsg = new Message(af);
                    responseMsg.setMessageContext(msgContext);
                }  else  {
                    try {
                        SOAPEnvelope env = responseMsg.getSOAPEnvelope();
                        env.clearBody();
                        env.addBodyElement(new SOAPFault(af));
                    } catch (AxisFault fault)  {
                        // Should never reach here!
                    }
                }                
            }
            
            if (responseMsg != null) {
                ByteArrayOutputStream theWriter = new ByteArrayOutputStream(); 
                responseMsg.writeTo(theWriter);
                
                // getting the content type of the response
                String respContentType = responseMsg.getContentType(msgContext.getSOAPConstants());
                
                // send back the result
                this.sendResponse(handler,serviceInvocationStatusCode, serviceInvocationStatusText, respContentType, theWriter.toByteArray());
                return;                              
            }
            
            this.sendResponse(handler,500, "Internal Server Error", "text/plain; charset=UTF-8","Unable to invoke the service".getBytes("UTF-8"));
            return;
        } catch (Exception e) {
        	logger.log(Level.WARNING,"Unexpected error while sending back the result.",e);
        	
            int httpStatusCode = 500;
            String httpStatusText = "Internal Server Error";
            
            // set the http statuscode and text properly
            if (e instanceof SoapServerException) {
                httpStatusCode = ((SoapServerException)e).httpStatusCode;
                httpStatusText = ((SoapServerException)e).httpStatusText;
            }            
            
            // this is an unexpected error. So we have to ensure to close the connection
			SoapData sd = ((SoapData)handler.getClientData());
			if(sd!=null) sd.httpHeaders.setKeepAlive(false);
            
            // sending response message to the client
            this.sendResponse(handler,httpStatusCode, httpStatusText, "text/plain; charset=UTF-8", e.getMessage().getBytes("UTF-8"));
            return;               
        } catch (Error e) {
            e.printStackTrace();
        }
        
        
    }
    
    /**
     * 
     * @return
     * @throws ConfigurationException
     * @since 0.2.6
     */
    private String[] generateServiceList() throws ConfigurationException {
        StringBuffer serviceList = new StringBuffer();
        serviceList.append("<html><body><h1>Deployed services</h1>\r\n");
                   
        
        serviceList.append("<table border=\"1\">")
                   .append("<tr bgcolor=\"lightgrey\">" +
                                "<td>Service name</td>" +
                                "<td>Operation</td>" +
                                "<td>WSDL</td>" +
                           "</tr>\r\n");
        Iterator serviceIter = this.engine.getConfig().getDeployedServices();
        while (serviceIter.hasNext()) {
            ServiceDesc serviceDescription = (ServiceDesc)serviceIter.next();
            
            serviceList.append("<tr>")
                       .append("<td><b>").append(serviceDescription.getName()).append("</b>")
                       .append("&nbsp;[").append(serviceDescription.getStyle()).append("]")
                       .append("</td>")
                       .append("<td><table>");
            
            ArrayList operations = serviceDescription.getOperations();
            for (int i=0; i < operations.size(); i++) {
                OperationDesc op = (OperationDesc)operations.get(i);
                
                serviceList.append("<tr>")
                           .append("<td><b>").append(op.getName()).append("</b></td>")
                           .append("<td><tt>").append(op.getMethod()).append("</tt></td>")
                           .append("</tr>\r\n");
                
            }
            
            serviceList.append("</table></td><td><a href=\"/").append(serviceDescription.getName()).append("?wsdl\">WSDL</a>")
                       .append("</tr>\r\n");
        }
        serviceList.append("</table>");
        
        serviceList.append("</body></html>");
        return new String[]{"text/html",serviceList.toString()};
    }
    
    /**
     * This function is used to generate a SOAP {@link MessageContext} object
     * that will be handed over to the service class that will be invoked for
     * a soap call.
     * Therefore this {@link MessageContext} object can be used to pass 
     * information and metadata to the service class.
     * 
     * @param handler
     * @param data 
     * @return the generated {@link MessageContext}
     * @throws Exception
     */
    private MessageContext generateMessageContext(ClientHandler handler, SoapData data) throws Exception {
        try {
            // getting the current server configuration
            QuickServerConfig config = handler.getServer().getConfig();
            
            // create and initialize a message context
            MessageContext msgContext = new MessageContext(this.engine);
            msgContext.setTransportName("http");
            //        msgContext.setProperty(Constants.MC_REALPATH,      path.toString());
            //        msgContext.setProperty(Constants.MC_RELATIVE_PATH, path.toString());
            //        msgContext.setProperty(Constants.MC_JWS_CLASSDIR,  "jwsClasses");
            //        msgContext.setProperty(Constants.MC_HOME_DIR,      "."); 

            // setting the client address
            msgContext.setProperty(SoapData.CLIENT_ADDRESS,data.clientAddress);
            
            // setting the proper soap service endpoint
            String httpHostHeader = data.httpHeaders.getHost();
            msgContext.setProperty(MessageContext.TRANS_URL, 
                    ((config.getSecure().getEnable())?"https":"http") + "://" + httpHostHeader + "/"+ data.serviceName);
            msgContext.setProperty(SoapData.SOAP_SERVICE_NAME,data.serviceName);
            // TODO: should we also set WSDLGEN_SERV_LOC_URL?
            
            // setting the proper http version
            msgContext.setProperty(MessageContext.HTTP_TRANSPORT_VERSION,data.httpReqLine.getVer());
     
            // setting the proper soap service name                    
            msgContext.setTargetService(data.serviceName);
            
            // TODO: maybe we should also use the SOAPAction here
            
            // TODO: set connection timeout
            //msgContext.setTimeout(arg0);
            
            // TODO: support sessions via http cookies
            // MessageContext.setSession()
            
            // TODO: do we need to set HTTPConstants.MC_ACCEPT_GZIP?
            
            // doing http authentication
            // TODO: move this into the httpHeader class
            if (data.httpHeaders.containsHeader(HTTPConstants.HEADER_AUTHORIZATION)) {
                String authString = data.httpHeaders.getHeader(HTTPConstants.HEADER_AUTHORIZATION);
                if (authString != null) {
                    authString = authString.trim();
                    
                    // doing basic authentication
                    if (authString.startsWith("Basic ")) {
                        // cuting of the "Basic " prefix
                        authString = authString.substring(6);
                        
                        // base64 decoding
                        authString = new String(Base64.decode(authString));
                        
                        // splitting into username + pwd
                        int pos = authString.indexOf(":");
                        if (pos != -1) {
                            msgContext.setUsername(authString.substring(0,pos));
                            msgContext.setPassword(authString.substring(pos+1));
                        } else {
                            SoapHandler.logger.warning("Invalid Http Authentication string");
                        }
                        
                    } else {
                        // Unsupported http authorization 
                        SoapHandler.logger.warning("Unsupported Http Authentication Method");
                    }
                }
            }
            
            
            return msgContext;
        } catch (Exception e) {
            throw new Exception ("Unable to generate the message context. ",e);
        }
    }
}