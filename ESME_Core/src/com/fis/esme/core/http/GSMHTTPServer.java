package com.fis.esme.core.http;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.app.ThreadManagerEx;
import com.fis.esme.core.util.LinkQueue;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

public class GSMHTTPServer {
	private int iPort;
	private String strSubUrl = "";
	private HttpServer server = null;
	public LinkQueue MORequestQueue;
	public ManageableThreadEx manageableThread = null;

	public static void main(String[] args) {
		GSMHTTPServer ser = new GSMHTTPServer(8800, "/");
		try {
			ser.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void debugMonitor(String strLogContent, int iDebugLevel) {
		manageableThread.debugMonitor(strLogContent, iDebugLevel);
	}

	public GSMHTTPServer(int iPort, String strSubUrl) {
		this.iPort = iPort;
		this.strSubUrl = strSubUrl;
	}

	public void start() throws Exception {
		SMSHTTPHandler handler = new SMSHTTPHandler();
		handler.setMORequestQueue(MORequestQueue);
		handler.server=this;
		InetSocketAddress socket = new InetSocketAddress(iPort);
		server = HttpServer.create(socket, 0);
		server.createContext(strSubUrl, handler);
		server.setExecutor(null); // creates a default executor
		server.start();
	}

	public void stop() throws Exception {
		this.server.stop(0);
	}

}
