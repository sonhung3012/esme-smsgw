package com.fis.esme.core.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.services.soap.SoapServer;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: FSS-FPT
 * </p>
 * 
 * @author HungVM
 * @version 1.0
 */

public class AxisGateway extends ManageableThreadEx {
	// //////////////////////////////////////////////////////
	// Override
	// //////////////////////////////////////////////////////
	private SoapServer soapSever = new SoapServer();

	public void beforeSession() {
		try {
			// configuring the server properties
			String confFile = "conf" + File.separator + "jSoapServer.xml";
			if (soapSever.initService(confFile)) {
				soapSever.startServer();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @throws Exception
	 */
	// //////////////////////////////////////////////////////
	public void afterSession() {
		try {
			soapSever.stopService();
			soapSever.stopServer();
			super.afterSession();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void processSession() throws Exception {
		// TODO Auto-generated method stub
		while (miThreadCommand != ThreadConstant.THREAD_STOP) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public static void main(String[] agm) {
		AxisGateway axis=new AxisGateway();
		axis.beforeSession();
	}

}
