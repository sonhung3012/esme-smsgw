package com.fis.esme.core.app;

import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;

import org.boris.winrun4j.AbstractService;
import org.boris.winrun4j.ServiceException;

import com.fis.esme.core.util.LogOutputStream;
import com.fss.dictionary.Dictionary;
import com.fss.sql.Database;
import com.fss.thread.ProcessorListener;
import com.fss.thread.ThreadManager;
import com.fss.thread.ThreadProcessor;
import com.fss.util.FileUtil;

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
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * <p>
 * Company: FIS-SOT
 * </p>
 * 
 * @author HungVM
 * @version 1.0
 */
public class AppManager implements ProcessorListener {

	public static final String CONFIG_FILE_NAME = "conf/ServerConfig.txt";
	private static Dictionary mdc;
	private static ThreadManagerEx cs = null;

	/**
	 * getConnection
	 * 
	 * @return Connection
	 * @throws Exception
	 * @todo Implement this com.fss.thread.ProcessorListener method
	 */
	
	

	public Connection getConnection() throws Exception {
		if (cs == null || cs.getDatabaseMode().equalsIgnoreCase("ORACLE")) {
			return Database.getConnection(mdc.getString("Url"),
					mdc.getString("UserName"), mdc.getString("Password"));
		} else if (cs.getDatabaseMode().equalsIgnoreCase("SQLSERVER")) {
			return Database.getConnection(mdc.getString("Driver"),
					mdc.getString("Url"), mdc.getString("UserName"),
					mdc.getString("Password"));
		}

		return Database.getConnection(mdc.getString("Url"),
				mdc.getString("UserName"), mdc.getString("Password"));

	}

	public static Connection getAppConnection() throws Exception {
		if (cs == null || cs.getDatabaseMode().equalsIgnoreCase("ORACLE")) {
			return Database.getConnection(mdc.getString("Url"),
					mdc.getString("UserName"), mdc.getString("Password"));
		} else if (cs.getDatabaseMode().equalsIgnoreCase("SQLSERVER")) {
			return Database.getConnection(mdc.getString("Driver"),
					mdc.getString("Url"), mdc.getString("UserName"),
					mdc.getString("Password"));
		}
		return Database.getConnection(mdc.getString("Url"),
				mdc.getString("UserName"), mdc.getString("Password"));
	}

	/**
	 * onCreate
	 * 
	 * @param threadProcessor
	 *            ThreadProcessor
	 * @throws Exception
	 * @todo Implement this com.fss.thread.ProcessorListener method
	 */
	public void onCreate(ThreadProcessor threadProcessor) throws Exception {
		threadProcessor.mcnMain = getConnection();
	}

	/**
	 * onOpen
	 * 
	 * @param threadProcessor
	 *            ThreadProcessor
	 * @throws Exception
	 * @todo Implement this com.fss.thread.ProcessorListener method
	 */
	public void onOpen(ThreadProcessor threadProcessor) throws Exception {
		threadProcessor.mcnMain = getConnection();
	}

	public static void main(String[] argv) throws Exception {
		try {
			// Change system output to file
			AppManager lsn = new AppManager();
			// loadServerConfig(strConfigFilePath);
			mdc = new Dictionary(CONFIG_FILE_NAME);

			String strLogFile = mdc.getString("ErrorLog");
			String strOutputFile = mdc.getString("OutputFile");
			String strLogDir = mdc.getString("LogDir");

			if (!strLogDir.endsWith("/") || !strLogDir.endsWith("\\")) {
				strLogDir += "/";
			}
			File f = new File(strLogDir + strLogFile);
			FileUtil.forceFolderExist(f.getParent());

			int iLogKeepDay = Integer.parseInt(mdc
					.getString("KeepLogFileOnDay"));
			int iMaxFileToSave = Integer.parseInt(mdc
					.getString("MaxFileLogSave"));
			String strValidateServiceMT = mdc.getString("ValidateServiceForMT");
			boolean blValidateServiceMT = false;
			if (strValidateServiceMT != null && strValidateServiceMT != ""
					&& strValidateServiceMT.equalsIgnoreCase("1")) {
				blValidateServiceMT = true;
			}
			String strCheckSubscriber = mdc.getString("CheckSubscriber");
			boolean blCheckSubscriber = false;
			if (strCheckSubscriber != null && strCheckSubscriber != ""
					&& strCheckSubscriber.equalsIgnoreCase("1")) {
				blCheckSubscriber = true;
			}

			String strDatabaseType = mdc.getString("DatabaseType");

			PrintStream ps = new PrintStream(new LogOutputStream(strLogDir
					+ strLogFile, iLogKeepDay, iMaxFileToSave));
			PrintStream psOutput = new PrintStream(new LogOutputStream(
					strLogDir + strOutputFile, iLogKeepDay, iMaxFileToSave));
			System.setOut(psOutput);
			System.setErr(ps);
			// Start manager
			cs = new ThreadManagerEx(Integer.parseInt(mdc.getString("PortID")),
					lsn);
			cs.setLoadingMethod(ThreadManager.LOAD_FROM_FILE);
			cs.setValidateServiceMT(blValidateServiceMT);
			cs.setCheckSubscriber(blCheckSubscriber);
			cs.setDatabaseMode(strDatabaseType);
			cs.start();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	public String getParameter(String string) {
		return "";
	}

	// @Override
	// public int serviceMain(String[] arg0) throws ServiceException {
	// // TODO Auto-generated method stub
	// try {
	// // Change system output to file
	// AppManager lsn = new AppManager();
	// // loadServerConfig(strConfigFilePath);
	// mdc = new Dictionary(CONFIG_FILE_NAME);
	//
	// String strLogFile = mdc.getString("ErrorLog");
	// String strOutputFile = mdc.getString("OutputFile");
	// String strLogDir = mdc.getString("LogDir");
	//
	// if (!strLogDir.endsWith("/") || !strLogDir.endsWith("\\")) {
	// strLogDir += "/";
	// }
	// File f = new File(strLogDir + strLogFile);
	// FileUtil.forceFolderExist(f.getParent());
	//
	// int iLogKeepDay = Integer.parseInt(mdc
	// .getString("KeepLogFileOnDay"));
	// int iMaxFileToSave = Integer.parseInt(mdc
	// .getString("MaxFileLogSave"));
	// String strValidateServiceMT = mdc.getString("ValidateServiceForMT");
	// boolean blValidateServiceMT = false;
	// if (strValidateServiceMT != null && strValidateServiceMT != ""
	// && strValidateServiceMT.equalsIgnoreCase("1")) {
	// blValidateServiceMT = true;
	// }
	// String strCheckSubscriber = mdc.getString("CheckSubscriber");
	// boolean blCheckSubscriber = false;
	// if (strCheckSubscriber != null && strCheckSubscriber != ""
	// && strCheckSubscriber.equalsIgnoreCase("1")) {
	// blCheckSubscriber = true;
	// }
	//
	// String strDatabaseType = mdc.getString("DatabaseType");
	//
	// PrintStream ps = new PrintStream(new LogOutputStream(strLogDir
	// + strLogFile, iLogKeepDay, iMaxFileToSave));
	// PrintStream psOutput = new PrintStream(new LogOutputStream(
	// strLogDir + strOutputFile, iLogKeepDay, iMaxFileToSave));
	// System.setOut(psOutput);
	// System.setErr(ps);
	// // Start manager
	// ThreadManagerEx cs = new ThreadManagerEx(Integer.parseInt(mdc
	// .getString("PortID")), lsn);
	// cs.setLoadingMethod(ThreadManager.LOAD_FROM_FILE);
	// cs.setValidateServiceMT(blValidateServiceMT);
	// cs.setCheckSubscriber(blCheckSubscriber);
	// cs.setDatabaseMode(strDatabaseType);
	// cs.start();
	//
	// Stopper stoper=new Stopper();
	// Thread thread=new Thread(stoper);
	// thread.start();
	//
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// System.exit(1);
	// }
	// System.out.println("System is running !");
	// return 0;
	//
	// }
	//
	// public class Stopper implements Runnable {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// while (!shutdown) {
	// try {
	// Thread.sleep(6000L);
	// } catch (InterruptedException e) {
	// }
	// }
	// cs.closeAll();
	// System.out.println("System closed");
	// }
	//
	// }

}
