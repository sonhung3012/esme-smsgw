package com.fis.esme.core.app;

import java.util.Properties;
import java.util.Vector;

import com.fss.thread.ManageableThread;
import com.fss.thread.ParameterType;
import com.fss.util.AppException;

/**
 * <p>
 * Title: MCA Core
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * 
 * <p>
 * Company: FIS-SOT
 * </p>
 * 
 * @author HungVM
 * @version 1.0
 */
public abstract class ManageableThreadEx extends ManageableThread {
	private int miDebugLevel = 0;
	protected Properties mprtDeliverSM = null;

	public ThreadManagerEx getThreadManager() {
		return ((ThreadManagerEx) mmgrMain);
	}

	public void fillParameter() throws AppException {
		miDebugLevel = loadInteger("DebugLevel");
		super.fillParameter();
		fillLogFile();
	}

	public Vector getParameterDefinition() {
		Vector vtReturn = super.getParameterDefinition();
		vtReturn.addElement(createParameterDefinition("DebugLevel", "",
				ParameterType.PARAM_TEXTBOX_MASK, "9", ""));
		return vtReturn;
	}

	public void debugMonitor(String strLogContent, int iDebugLevel) {
		if (iDebugLevel <= miDebugLevel) {
			logMonitor("(B" + String.valueOf(iDebugLevel) + ")" + strLogContent);
		}
	}

	public Properties getProperties() {
		return mprtDeliverSM;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isOpen() {
		return true;
	}
	public static void main(String [] agm)
	{
		System.out.println("Print any thing");
	}
}
