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
 * $Source: /cvsroot/jsoapserver/jSoapServer/src/main/java/org/jSoapServer/SoapServer.java,v $
 * $Author: thelian $
 * $Date: 2010/05/06 10:53:20 $
 * $Revision: 1.3 $
 * ======================================================================= */

package com.fis.esme.core.services.soap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.WSDDEngineConfiguration;
import org.apache.axis.deployment.wsdd.WSDDDeployment;
import org.apache.axis.deployment.wsdd.WSDDDocument;
import org.apache.axis.server.AxisServer;
import org.apache.axis.utils.ClassUtils;
import org.apache.axis.utils.XMLUtils;
import org.apache.axis.utils.cache.ClassCache;
import org.jSoapServer.ISoapServer;
import org.jSoapServer.QSConfigReader;
import org.jSoapServer.utils.FileUtils;
import org.quickserver.net.AppException;
import org.quickserver.net.server.QuickServer;
import org.quickserver.util.xmlreader.QuickServerConfig;
import org.w3c.dom.Document;

/**
 * To integrate jSoapServer into your application you can do the following:<br>
 * 
 * <pre>
 * public class yourClass {
 * 	private SoapServer soapServer = null;
 * 
 * 	// Function to start jSoapServer
 * 	public void startSoapServer(String configFileName) throws Exception {
 * 		// create the server object
 * 		this.soapServer = new SoapServer();
 * 
 * 		// initialize server
 * 		if (!this.soapServer.initService(configFileName))
 * 			throw new Exception(&quot;SOAP server initialization failed&quot;);
 * 
 * 		// deploy service(s)
 * 		this.soapServer.deployRpcSoapService(org.myApp.myServiceClass.class, // service
 * 																				// class
 * 				&quot;myService&quot; // service name to use
 * 		);
 * 
 * 		// startup server
 * 		this.soapServer.startServer();
 * 	}
 * 
 * 	// Function to stop jSoapServer
 * 	public void stopSoapServer() throws Exception {
 * 		this.soapServer.stopServer();
 * 	}
 * }
 * </pre>
 * 
 * @author Martin Thelian
 * @version 0.3
 */
public class SoapServer extends QuickServer implements ISoapServer {
	private static final long serialVersionUID = 1L;

	/*
	 * ===================================================================
	 * JSoapServer Version
	 * ===================================================================
	 */
	public static final String VERSION = "0.3";

	/*
	 * ===================================================================
	 * Constants needed to address objects in the server object store
	 * ===================================================================
	 */
	/**
	 * A reference to the axis server engine
	 */
	public static final int STORE_AXIS_SERVER = 0;

	/**
	 * A reference to other jSoapServer specific settings
	 */
	public static final int STORE_JSOAPSERVER_SETTINGS = 1;

	/**
	 * Specifies if jSoapServer should support persistent http connections
	 */
	public static final String JSOAPSERVER_ENABLE_KEEP_ALIVE = "jSoapServer.keepAlive";

	/**
	 * Specifies if jSoapServer should generate the deployes servicelist when
	 * calling http://localhost:8090/?list=
	 */
	public static final String JSOAPSERVER_ENABLE_SERVICE_LIST = "jSoapServer.serviceList";

	/**
	 * The name of the resource file containing the default wsdd deployment
	 * string that is used by jSoapServer to deploy new RPC services.
	 * 
	 * @since 0.2.8
	 */
	public static String defaultServiceDeploymentStringFile = "deployRPC.wsdd";

	/**
	 * The {@link AxisServer Apache Axis server engine}
	 */
	protected AxisServer axisServer;

	/**
	 * The Logger class
	 */
	protected Logger logger = Logger.getLogger(SoapServer.class.getName());

	/**
	 * The server object store. This Object array contains
	 * <table border="1">
	 * <tr>
	 * <td>[0]</td>
	 * <td>the apache axis engine</td>
	 * </tr>
	 * <tr>
	 * <td>[1]</td>
	 * <td>a hashmap containing jSoapServer specific settings</td>
	 * </tr>
	 * </table>
	 */
	protected Object[] store = null;

	/**
	 * The constructor of this class.
	 */
	public SoapServer() {
		this(null);
	}

	public SoapServer(AxisServer theAxisServer) {
		// call parent constructor
		super();

		// init jSoapServer
		initSoapServer(theAxisServer);
	}

	/**
	 * Initializes jSoapServer
	 * <ul>
	 * <li>initializes the apache axis engine</li>
	 * <li>initializes the quickserver object store</li>
	 * <li>initializes the server banner that is printed on startup</li>
	 * 
	 * @param theAxisServer
	 */
	protected void initSoapServer(AxisServer theAxisServer) {
		this.logger.info("Initializing jSoapServer ...");

		// creating an axiserver
		if (theAxisServer == null) {
			this.axisServer = initAxisServer();
		} else {
			this.axisServer = theAxisServer;
		}

		// creating an object store and store a reference to the axisServer in
		// it
		HashMap jsoapServerSettings = new HashMap();
		jsoapServerSettings.put(JSOAPSERVER_ENABLE_KEEP_ALIVE, Boolean.TRUE);
		jsoapServerSettings.put(JSOAPSERVER_ENABLE_SERVICE_LIST, Boolean.TRUE);

		this.store = new Object[] { this.axisServer, jsoapServerSettings };
		this.setStoreObjects(this.store);

		// set a customized server banner
		this.setServerBanner("-------------------------------\r\n" + "Name : "
				+ this.getName() + "\r\n" + "Port : " + this.getPort() + "\r\n"
				+ "-------------------------------");
	}

	/**
	 * With this function the jSoapServer connection keep-alive support can be
	 * enabled or disabled.
	 * 
	 * @param status
	 */
	public void setConnectionKeepAliveSupport(boolean status) {
		((HashMap) this.store[STORE_JSOAPSERVER_SETTINGS]).put(
				JSOAPSERVER_ENABLE_KEEP_ALIVE, Boolean.valueOf(status));
	}

	public void setAllowServiceListGeneration(boolean status) {
		((HashMap) this.store[STORE_JSOAPSERVER_SETTINGS]).put(
				JSOAPSERVER_ENABLE_SERVICE_LIST, Boolean.valueOf(status));
	}

	public void startServer() throws AppException {
		this.logger.info("Starting jSoapServer V" + VERSION);
		deployRpcSoapService(
				com.fis.esme.core.services.soap.SendMT.class,
				"SendMT");
		SendMT.clear();
		super.startServer();
	}

	/**
	 * Initializes the Apache Axis server engine
	 * 
	 * @return the created {@link AxisServer Apache Axis server engine}
	 */
	protected AxisServer initAxisServer() {
		this.logger.info("Initializing the axis server engine ...");

		// creating an axiserver
		AxisServer engine = new AxisServer();

		// setting some options ...
		engine.setShouldSaveConfig(false);

		// returnes the newly created apache axis engine
		return engine;
	}

	/**
	 * Loads the wsdd deployment template string from a file.<br>
	 * The name of the template file to load is queried using function
	 * {@link #getDeploymentFileName(String, String)}<br>
	 * 
	 * This function tries to load the template file
	 * <ol>
	 * <li>from the file system
	 * <li>using the classloader that loaded SoapServer
	 * <li>using the system classloader
	 * <li>using SoapServer.getClass().getResourceAsStream(java.lang.String)
	 * </ol>
	 * 
	 * @param serviceClassName
	 *            the class that should be deployed as service
	 * @param serviceName
	 *            the name that should be used for the deployment
	 * 
	 * @return the wsdd deployment template string, e.g.
	 * 
	 *         <pre>
	 * &lt;deployment 
	 *  xmlns="http://xml.apache.org/axis/wsdd/" 
	 *  xmlns:java="http://xml.apache.org/axis/wsdd/providers/java" &gt;  
	 * 	&lt;service name="@serviceName@" provider="java:RPC"&gt;
	 *       	&lt;parameter name="className" value="@serviceClassName@" /&gt;
	 * 	&lt;parameter name="allowedMethods" value="*" /&gt;
	 * &lt;/deployment&gt;
	 * </pre>
	 * 
	 * @throws IOException
	 *             if the deployment string could not be loaded
	 * @since 0.2.8
	 */
	public String loadDeploymentStringTemplate(String serviceClassName,
			String serviceName) throws IOException {
		String deploymentString = null;

		// getting the name of the file containing the deployment string
		String deploymentFileName = this.getDeploymentFileName(
				serviceClassName, serviceName);

		// determine if the jSoapServer is on the classpath

		// if no file name was set we use the default file
		if (deploymentFileName == null)
			deploymentFileName = defaultServiceDeploymentStringFile;

		InputStream deploymentStream = null;
		File deploymentFile = new File(deploymentFileName);
		if (deploymentFile.exists()) {
			// load the deployment template file from file
			deploymentStream = new FileInputStream(deploymentFile);
		} else {
			// try to load the deployment template file from classpath
			deploymentStream = ClassUtils.getResourceAsStream(SoapServer.class,
					deploymentFileName, true);
		}

		// read template
		deploymentString = FileUtils.loadStringFromStream(deploymentStream);
		return deploymentString;
	}

	/**
	 * Returns the name of the template file containing the default wsdd
	 * deployment string template that should be used by jSoapServer to deploy
	 * RPC services.
	 * 
	 * @param serviceClassName
	 *            the class that should be deployed as service
	 * @param serviceName
	 *            the name that should be used for the deployment
	 * @return the name of the template file, e.g.
	 *         <code>conf/deployRPC.wsdd</code>
	 * @since 0.2.8
	 */
	public String getDeploymentFileName(String serviceClassName,
			String serviceName) {
		return null;
	}

	/**
	 * This function has the same functionality as function
	 * {@link #deployRpcSoapService(String, String)}
	 * 
	 * @param serviceClass
	 *            the service class name to deploy
	 * @param serviceName
	 *            the name taht is used for deployment
	 * @return
	 */
	public boolean deployRpcSoapService(Class serviceClass, String serviceName) {
		final ClassCache classCache = this.axisServer.getClassCache();
		if (classCache != null) {
			classCache.registerClass(serviceClass.getName(), serviceClass);
		}
		return this.deployRpcSoapService(serviceClass.getName(), serviceName);
	}

	/**
	 * This function
	 * <ul>
	 * <li>loads the deployment template string using function
	 * {@link #loadDeploymentStringTemplate(String, String)}</li>
	 * <li>fetches a list of deployment properties using function
	 * {@link #getDeploymentProperties(String, String)}</li>
	 * <li>generates a deployment string using function
	 * {@link #generateDeploymentString(Properties, String)}</li>
	 * <li>deploys the service using the generated deployment string</li>
	 * 
	 * @param serviceClassName
	 *            the service class name to deploy
	 * @param serviceName
	 *            the name that is used for deployment<br>
	 *            If e.g. <code>test</code> is used, then the deployed service
	 *            is accessible using the url
	 *            <code>http://localhost:8090/test</code>
	 * 
	 * @return <code>true</code> if the service was deployed successfully
	 */
	public boolean deployRpcSoapService(String serviceClassName,
			String serviceName) {
		this.logger.info("Deploying class '" + serviceClassName
				+ "' as RCP service '" + serviceName + "'.");

		try {
			// getting the deployment template string to use
			String deploymentStringTemplate = loadDeploymentStringTemplate(
					serviceClassName, serviceName);

			// getting the deployment properties to use
			Properties deploymentProperties = getDeploymentProperties(
					serviceClassName, serviceName);

			// generate the deployment string from the template and the
			// properties
			String deploymentString = generateDeploymentString(
					deploymentProperties, deploymentStringTemplate);

			// deploy the serivce
			return deploySoapService(deploymentString, this.axisServer);
		} catch (Exception e) {
			this.logger.severe("Unable to deploy class '" + serviceClassName
					+ "' as RCP service '" + serviceName + "'. "
					+ e.getMessage());
			return false;
		}
	}

	/**
	 * Returns some properties which should be inserted into the deployment
	 * string template.<br>
	 * e.g.
	 * 
	 * <pre>
	 * serviceName = org.jSoapServer.SoapService
	 * serviceClassName = test
	 * </pre>
	 * 
	 * @param serviceClassName
	 *            the service class
	 * @param serviceName
	 *            the service name
	 * @return the properties list
	 * 
	 * @since 0.2.9
	 */
	protected Properties getDeploymentProperties(String serviceClassName,
			String serviceName) {
		Properties deplProperties = new Properties();

		deplProperties.put("serviceName", serviceName);
		deplProperties.put("serviceClassName", serviceClassName);

		return deplProperties;
	}

	/**
	 * This function uses a regular expression to find all deployment-variables
	 * (e.g. <code>@serviceClassName@</code> included in the deployment string
	 * and replaces it with the value of the corresponding property
	 * 
	 * @param deploymentProperties
	 *            the deployment properties returned by
	 *            {@link #getDeploymentProperties(String, String)}, e.g.
	 * 
	 *            <pre>
	 * serviceName = org.jSoapServer.SoapService
	 * serviceClassName = test
	 * </pre>
	 * @param deplStringTemplate
	 *            the deployment template returned by
	 *            {@link #loadDeploymentStringTemplate(String, String)}, e.g.
	 * 
	 *            <pre>
	 * &lt;deployment 
	 *  xmlns="http://xml.apache.org/axis/wsdd/" 
	 *  xmlns:java="http://xml.apache.org/axis/wsdd/providers/java" &gt;  
	 * 	&lt;service name="@serviceName@" provider="java:RPC"&gt;
	 *       	&lt;parameter name="className" value="@serviceClassName@" /&gt;
	 * 	&lt;parameter name="allowedMethods" value="*" /&gt;
	 * &lt;/deployment&gt;
	 * </pre>
	 * 
	 * @return the generated deployment string, e.g. <br>
	 * 
	 *         <pre>
	 * &lt;deployment 
	 *  mlns="http://xml.apache.org/axis/wsdd/" 
	 *  xmlns:java="http://xml.apache.org/axis/wsdd/providers/java" &gt;  
	 * 	&lt;service name="test" provider="java:RPC"&gt;
	 *       	&lt;parameter name="className" value="org.jSoapServer.SoapService" /&gt;
	 * 	&lt;parameter name="allowedMethods" value="*" /&gt;
	 * &lt;/deployment&gt;
	 * </pre>
	 * 
	 * @since 0.2.9
	 */
	protected String generateDeploymentString(Properties deploymentProperties,
			String deplStringTemplate) {
		Pattern templatePattern = Pattern.compile("@.+?@");
		Matcher matcher = templatePattern.matcher(deplStringTemplate);

		StringBuffer result = new StringBuffer();

		int matcherStartIdx = 0;
		while (matcher.find()) {
			// Get the next template variable name
			String templateKeyString = matcher.group();
			String templateKeyName = templateKeyString.substring(1,
					templateKeyString.length() - 1);
			String templateKeyValue = deploymentProperties.getProperty(
					templateKeyName, templateKeyString);

			// Get the group's indices
			result.append(deplStringTemplate.subSequence(matcherStartIdx,
					matcher.start()));
			result.append(templateKeyValue);

			matcherStartIdx = matcher.end();
		}
		result.append(deplStringTemplate.subSequence(matcherStartIdx,
				deplStringTemplate.length()));

		// return the result
		return result.toString();
	}

	/**
	 * Function to undeploy an already deployed soap service
	 * 
	 * @param serviceName
	 *            the name of the service that should be undeployed, e.g.
	 *            <code>test</code><br>
	 * @return <code>true</code> if the service was undeployed successfully
	 */
	public boolean undeployRpcSoapService(String serviceName) {
		// get the configuration of this axis engine
		EngineConfiguration config = this.axisServer.getConfig();

		if (config instanceof WSDDEngineConfiguration) {
			// get the current configuration of the Axis engine
			WSDDDeployment deploymentWSDD = ((WSDDEngineConfiguration) config)
					.getDeployment();

			// undeply services
			try {
				if (config.getService(new QName(serviceName)) != null) {
					deploymentWSDD.undeployService(new QName(serviceName));
					return true;
				}
				return false;
			} catch (org.apache.axis.ConfigurationException e) {
				this.logger.warning("Unable to undeploy service '"
						+ serviceName + "'");
			}

		}
		return false;

	}

	public boolean deploySoapService(String deploymentString,
			AxisServer theAxisServer) {
		// convert WSDD file string into bytestream for furhter processing
		InputStream deploymentStream = null;
		if (deploymentString != null) {
			deploymentStream = new ByteArrayInputStream(
					deploymentString.getBytes());
			Document root = null;

			try {
				// build XML document from stream
				root = XMLUtils.newDocument(deploymentStream);

				// parse WSDD file
				WSDDDocument wsddDoc = new WSDDDocument(root);

				// get the configuration of this axis engine
				EngineConfiguration config = theAxisServer.getConfig();

				if (config instanceof WSDDEngineConfiguration) {
					// get the current configuration of the Axis engine
					WSDDDeployment deploymentWSDD = ((WSDDEngineConfiguration) config)
							.getDeployment();

					// undeply unneeded standard services
					deploymentWSDD.undeployService(new QName("Version"));
					deploymentWSDD.undeployService(new QName("AdminService"));

					// deploy the new service
					// an existing service with the same name gets deleted
					wsddDoc.deploy(deploymentWSDD);
				}
			} catch (Exception e) {
				this.logger.log(Level.SEVERE, "Could not deploy service.", e);
				return false;
			}
		} else {
			this.logger
					.severe("Service deployment string is NULL! SOAP Service not deployed.");
			return false;
		}
		return true;
	}

	/**
	 * Read the server settings from {@link File}
	 * 
	 * @param configFileName
	 *            the file name
	 * @return <code>true</code> if initialization was done successfully
	 * @throws Exception
	 */
	public boolean initService(String configFileName) throws Exception {
		if ((configFileName == null) || (configFileName.length() == 0))
			throw new Exception(
					"The config-file name must not be null or empty.");

		this.logger.info("Initializing basic services ...");
		return super.initService(new Object[] { configFileName });
	}

	/**
	 * Read the server settings from {@link URL}
	 * 
	 * @param configFileURL
	 *            the {@link URL} to the config file
	 * @return <code>true</code> if initialization was done successfully
	 * @throws Exception
	 * 
	 * @since 0.2.10
	 */
	public boolean initService(URL configFileURL) throws Exception {
		if (configFileURL == null)
			throw new Exception(
					"The config-file URL must not be null or empty.");

		this.logger.info("Initializing basic services ...");
		QuickServerConfig conf = QSConfigReader.read(configFileURL);
		return super.initService(conf);
	}

	/**
	 * Read the server settings from {@link InputStream}
	 * 
	 * @param configFileStream
	 *            the {@link InputStream} to read the config file
	 * @return <code>true</code> if initialization was done successfully
	 * @throws Exception
	 * 
	 * @since 0.2.10
	 */
	public boolean initService(InputStream configFileStream) throws Exception {
		if (configFileStream == null)
			throw new Exception("The config-file must not be null or empty.");

		this.logger.info("Initializing basic services ...");
		QuickServerConfig conf = QSConfigReader.read(configFileStream);
		return super.initService(conf);
	}

	/**
	 * Read the server settings from {@link File}
	 * 
	 * @param configFile
	 *            the config file
	 * @return <code>true</code> if initialization was done successfully
	 * @throws Exception
	 * 
	 * @since 0.2.10
	 */
	public boolean initService(File configFile) throws Exception {
		if (configFile == null)
			throw new Exception("The config-file must not be null or empty.");

		this.logger.info("Initializing basic services ...");
		QuickServerConfig conf = QSConfigReader.read(configFile);
		return super.initService(conf);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			// creating a new soap server
			SoapServer myServer = new SoapServer();

			// configuring the server properties
			String confFile = "conf" + File.separator + "jSoapServer.xml";
			if (myServer.initService(confFile)) {
				// TODO: reading out application specific configuration and
				// process it ...
				// ApplicationConfiguration myConfig =
				// myServer.getConfig().getApplicationConfiguration();

				// deploing all needed services
				myServer.deployRpcSoapService(
						com.fis.esme.core.services.soap.SendMT.class,
						"SendMT");

				// the soap server
				myServer.startServer();

				// starting the quickserver admin server if configured
				// QSAdminServerConfig adminConfig =
				// myServer.getConfig().getQSAdminServerConfig();
				// if (adminConfig != null) myServer.startQSAdminServer();
			}
		} catch (Exception e) {
			System.err.println("Error in server : " + e);
			e.printStackTrace();

		}
	}
}
