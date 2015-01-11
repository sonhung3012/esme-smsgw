package com.fis.esme.core.services;

import java.util.Map;
import java.util.Vector;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.log.LogRecord;
import com.fis.esme.core.smsc.util.SendSMSLog;
import com.fis.esme.core.util.LinkQueue;
import com.fss.sql.Database;
import com.fss.thread.ParameterType;
import com.fss.util.AppException;

public class ACKMessageExpire extends ManageableThreadEx {
	private Map mapRequestAckCache = null;
	private LinkQueue<LogRecord> mlqQutputQueue = null;
	private int miMiliSecondResponseTimeout = 120 * 1000;
	private int miSecondResponseTimeout = 120;
	int iTotalExpire = 0;

	public void fillParameter() throws AppException {
		miSecondResponseTimeout = loadInteger("ResponseTimeout");
		miMiliSecondResponseTimeout = miSecondResponseTimeout * 1000;
		super.fillParameter();
	}

	public Vector getParameterDefinition() {

		Vector vtReturn = super.getParameterDefinition();
		vtReturn.addElement(createParameterDefinition("ResponseTimeout", "",
				ParameterType.PARAM_TEXTBOX_MASK, "99999", "seconds"));

		return vtReturn;
	}

	public void beforeSession() throws Exception {
		super.beforeSession();
		if (mcnMain == null) {
			mcnMain = getThreadManager().getConnection();
		}
		mlqQutputQueue = getThreadManager().getPrimaryLoggingStorage();
		mapRequestAckCache = getThreadManager().getRequestAckCache();

		logMonitor("Start check message cache expire - Queue size: "
				+ mapRequestAckCache.size());
	}

	@Override
	protected void processSession() throws Exception {
		iTotalExpire = 0;

		try {
			Object[] arrKeys = null;
			synchronized (mapRequestAckCache) {
				arrKeys = mapRequestAckCache.keySet().toArray();
			}
			for (int i = 0; i < arrKeys.length; i++) {
				String strKey = arrKeys[i].toString();
				int intKey = 0;
				try {
					intKey = Integer.valueOf(strKey);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				Object value = null;
				synchronized (mapRequestAckCache) {
					value = mapRequestAckCache.get(intKey);
				}
				if (value != null && value instanceof LogRecord) {
					LogRecord log = (LogRecord) value;
					if (System.currentTimeMillis() - log.mlStartTime > miMiliSecondResponseTimeout) {
						synchronized (mapRequestAckCache) {
							mapRequestAckCache.remove(intKey);
						}
						log.setStatus(SendSMSLog.TIME_OUT_MSG);
						mlqQutputQueue.enqueueNotify(log);
						iTotalExpire++;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			logMonitor("Have " + iTotalExpire + " message cache is expired.");

		}
	}

	public void afterSession() throws Exception {
		mlqQutputQueue = null;
		mapRequestAckCache = null;
		Database.closeObject(mcnMain);
		mcnMain=null;
		super.afterSession();
	}
	public static void main(String [] agm)
	{
		
	}
}
