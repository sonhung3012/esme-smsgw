package com.fis.esme.core.http;


import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;


public class ESMEHttpServer
{
	private int	   iPort;
	private String	strSubUrl = "";
	private HttpServer server    = null;
	
	public static void main(String[] args)
	{
		ESMEHttpServer ser = new ESMEHttpServer(8000, "/post");
		try
		{
			ser.start();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public ESMEHttpServer(int iPort, String strSubUrl)
	{
		this.iPort = iPort;
		this.strSubUrl = strSubUrl;
	}
	
	public void start() throws Exception
	{
		MyHandler handler = new MyHandler();
		InetSocketAddress socket = new InetSocketAddress(iPort);
		server = HttpServer.create(socket, 0);
		server.createContext(strSubUrl, handler);
		server.setExecutor(null); // creates a default executor
		server.start();
	}
	
	public void stop() throws Exception
	{
		this.server.stop(0);
	}
	
	private void sslServer()
	{
		try
		{
			// setup the socket address
			InetSocketAddress address = new InetSocketAddress(
					InetAddress.getLocalHost(), 8080);
			
			// initialise the HTTPS server
			HttpsServer httpsServer = HttpsServer.create(address, 0);
			javax.net.ssl.SSLContext sslContext = javax.net.ssl.SSLContext
					.getInstance("TLS");
			
			// initialise the keystore
			char[] password = "simulator".toCharArray();
			KeyStore ks = KeyStore.getInstance("JKS");
			FileInputStream fis = new FileInputStream("lig.keystore");
			ks.load(fis, password);
			
			// setup the key manager factory
			javax.net.ssl.KeyManagerFactory kmf = javax.net.ssl.KeyManagerFactory
					.getInstance("SunX509");
			kmf.init(ks, password);
			
			// setup the trust manager factory
			javax.net.ssl.TrustManagerFactory tmf = javax.net.ssl.TrustManagerFactory
					.getInstance("SunX509");
			tmf.init(ks);
			
			// setup the HTTPS context and parameters
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(),
					null);
			
			httpsServer.setHttpsConfigurator(new HttpsConfigurator(
					sslContext)
			{
				public void configure(HttpsParameters params)
				{
					try
					{
						// initialise the SSL context
						javax.net.ssl.SSLContext c = javax.net.ssl.SSLContext
								.getDefault();
						SSLEngine engine = c.createSSLEngine();
						params.setNeedClientAuth(false);
						params.setCipherSuites(engine
								.getEnabledCipherSuites());
						params.setProtocols(engine.getEnabledProtocols());
						
						// get the default parameters
						SSLParameters defaultSSLParameters = c
								.getDefaultSSLParameters();
						params.setSSLParameters(defaultSSLParameters);
					}
					catch (Exception ex)
					{
						// ILogger log = new LoggerFactory().getLogger();
						// log.exception(ex);
						System.out.println("Failed to create HTTPS port");
					}
				}
			});
			httpsServer.start();
			System.out.println("Server Started");
			// LigServer server = new LigServer(httpsServer);
			// joinableThreadList.add(server.getJoinableThread());
		}
		catch (Exception exception)
		{
			// log.exception(exception);
			System.out.println("Failed to create HTTPS server on port "
					+ "8080 of localhost");
		}
	}
	
	private void startClient()
	{
	}
}
