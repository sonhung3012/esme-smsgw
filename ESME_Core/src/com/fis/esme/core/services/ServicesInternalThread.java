package com.fis.esme.core.services;

import java.lang.reflect.Method;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.bean.SmsBean;
import com.fis.esme.core.bean.SmsBeanFactory;
import com.fis.esme.core.bean.SmsBeanOracle;
import com.fis.esme.core.entity.SMSMORoutingObject;
import com.fis.esme.core.entity.ServicesInternalObject;
import com.fis.esme.core.entity.VariableStatic;
import com.fis.esme.core.util.LinkQueue;
import com.fss.sql.Database;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;

public class ServicesInternalThread extends ManageableThreadEx {
	private LinkQueue<ServicesInternalObject> lqMessageQueueInter;
	private SmsBean bean = null;

	@Override
	public void processSession() throws Exception {
		// TODO Auto-generated method stub
		ServicesInternalObject servicesInternalObject = null;
		while (miThreadCommand != ThreadConstant.THREAD_STOP
				&& (servicesInternalObject = lqMessageQueueInter
						.dequeueWait(120)) != null) {
			try {
				String className = null;
				String functionName = null;
				String url = servicesInternalObject.getSmsMORoutingObject()
						.getUrl();
				if (url.toLowerCase().startsWith("http")) {
					className = "com.fis.esme.core.services.ServicesInternalProcessor";
					functionName = "getProfileStatus";
				} else {
					className = getClassName(url);
					functionName = getFunctionName(url);
				}
				String[] inputParams = new String[5];
				inputParams[0] = servicesInternalObject.getMsisdn();
				inputParams[1] = servicesInternalObject.getContent();
				inputParams[2] = servicesInternalObject.getSmsMORoutingObject()
						.getCmd_code();
				inputParams[3] = servicesInternalObject.getSmsMORoutingObject()
						.getShort_code_code();
				inputParams[4] = url;
				debugMonitor(
						className + "," + functionName + ","
								+ inputParams[0] + inputParams[1] + inputParams[2] +inputParams[3] +inputParams[4], 9);

				String strResponse = String.valueOf(process(className, functionName,
						inputParams));
				bean.insertSMSMT(mcnMain, servicesInternalObject.getSmsLogId(),
						strResponse, "0", servicesInternalObject
								.getSmsMORoutingObject().getShort_code_code(),
						servicesInternalObject.getMsisdn(),
						servicesInternalObject.getSmsMORoutingObject()
						.getCmd_code()==null? VariableStatic.DEFAULT_COMMAND_CODE:servicesInternalObject.getSmsMORoutingObject()
								.getCmd_code(), "0", "0", "0",
						servicesInternalObject.getSubId(),
						servicesInternalObject.getGroupId());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw e;
			}
			Thread.sleep(50);
		}
	}

	private String getClassName(String strClassname) {
		try {
			if (strClassname != null && strClassname != "") {
				int i = strClassname.lastIndexOf(".");
				if (i > 0) {
					return strClassname.substring(0, i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return strClassname;
	}

	private String getFunctionName(String strClassname) {
		try {
			if (strClassname != null && strClassname != "") {
				int i = strClassname.lastIndexOf(".");
				if (i > 0) {
					return strClassname.substring(i + 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return strClassname;
	}

	public void beforeSession() throws Exception {
		super.beforeSession();
		lqMessageQueueInter = getThreadManager().getMessageQueueInter();
		mcnMain = getThreadManager().getConnection();
		bean = SmsBeanFactory.getSmsBeanFactory(getThreadManager()
				.getDatabaseMode());
	}

	public void afterSession() throws Exception {
		super.afterSession();
		Database.closeObject(mcnMain);
	}

	public static void main(String[] amg) {
		try {
			ServicesInternalThread tesst = new ServicesInternalThread();
			String className = "com.fis.esme.core.services.ServicesInternalProcessor";
			//System.out.println(tesst.getClassName(className));
			//
			 String functionName = "getProfileStatus";
			 String[] inputParams = new String[5];
			 
				inputParams[0] = "0979108420";
				inputParams[1] = "PROFILE 9199191919";
				inputParams[2] = "PROFILE";
				inputParams[3] ="8888";
				inputParams[4] = "http://10.86.70.86:8100/Master/TinNhanService.svc?wsdl";
			 
			 
			 System.out.println(tesst.process(className, functionName,
			 inputParams));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * process
	 * 
	 * @return Object
	 * @param dispatcher
	 *            SoapDispatcherSplitThread
	 * @param className
	 *            String
	 * @param functionName
	 *            String
	 * @param inputParams
	 *            Object[]
	 * @throws Exception
	 */
	private Object process(String className, String functionName,
			Object[] inputParams) throws Exception {
		Object objMain = loadClassName(className);
		if (objMain == null) {
			throw new AppException("SMSGW-0014", "Could not find class '"
					+ className
					+ "' that declare for Dispatcher to process function ‘"
					+ functionName + "'");
		}
		// get class
		Class cls = objMain.getClass();
		// get Method
		Class[] types = new Class[inputParams.length];
		for (int i = 0; i < types.length; i++) {
			types[i] = java.lang.String.class;
		}
		Method method = cls.getMethod(functionName, types);
		// invoke method
		Object objReturn = null;
		try {
			objReturn = method.invoke(objMain, inputParams);
		} catch (Exception ex) {
			return ex;
		}
		return objReturn;
	}

	public Object loadClassName(String strClassName) throws Exception {
		try {
			Class cls = Class.forName(strClassName);
			java.lang.reflect.Constructor classStub = cls
					.getDeclaredConstructor();
			Object obj = classStub.newInstance(new Object[] {});
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
