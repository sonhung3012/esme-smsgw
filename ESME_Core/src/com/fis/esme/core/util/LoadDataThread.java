package com.fis.esme.core.util;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fss.thread.ManageableThread;
import java.util.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.fss.util.WildcardFilter;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.io.File;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fss.util.FileUtil;
import com.fss.thread.ThreadConstant;
import java.util.Vector;

import org.smpp.Data;

import com.fss.thread.ParameterType;
import com.fss.util.AppException;

/**
 * <p>
 * Title:
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
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class LoadDataThread extends ManageableThreadEx {
	protected String mstrImportDir;
	protected String mstrBackupDir;
	protected String mstrWildcard;
	public int miMaxQueue = 1000;
	private String mstrURL = null;
	private int port = 0;
	private String mstrMobilePrefix = "95";
	private String mstrMobileUnPrefix = "0";
	private String mstrUsername;
	private String mstrPassword;
	private String mstrFromAddr;
	private String mstrSrcAddrTON;
	private String mstrSrcAddrNPI;
	private String mstrDestAddrTON;
	private String mstrDestAddrNPI;
	private String mstrMobileNoCode = "DestNo";
	private String mstrMsgCode = "msg";
	private String strmobile = "959799779825";

	public LoadDataThread() {
		mstrImportDir = "import/";
		mstrBackupDir = "backup/";
		mstrWildcard = "*.txt";
	}

	public Vector getParameterDefinition() {
		Vector vtReturn = new Vector();
		vtReturn.addElement(createParameterDefinition("ImportDir", "",
				ParameterType.PARAM_TEXTBOX_MAX, "99999", ""));
		vtReturn.addElement(createParameterDefinition("BackupDir", "",
				ParameterType.PARAM_TEXTBOX_MAX, "99999", ""));
		vtReturn.addElement(createParameterDefinition("URL", "",
				ParameterType.PARAM_TEXTBOX_MAX, "15", ""));
		vtReturn.addElement(createParameterDefinition("UserName", "",
				ParameterType.PARAM_TEXTBOX_MAX, "50", ""));
		vtReturn.addElement(createParameterDefinition("Password", "",
				ParameterType.PARAM_PASSWORD, "100", ""));
		vtReturn.addElement(createParameterDefinition("FromAddr", "",
				ParameterType.PARAM_TEXTBOX_MAX, "10", ""));
		vtReturn.addElement(createParameterDefinition("SrcAddrTON", "",
				ParameterType.PARAM_TEXTBOX_MAX, "2", ""));
		vtReturn.addElement(createParameterDefinition("SrcAddrNPI", "",
				ParameterType.PARAM_TEXTBOX_MAX, "2", ""));
		vtReturn.addElement(createParameterDefinition("DestAddrTON", "",
				ParameterType.PARAM_TEXTBOX_MAX, "2", ""));
		vtReturn.addElement(createParameterDefinition("DestAddrNPI", "",
				ParameterType.PARAM_TEXTBOX_MAX, "2", ""));
		vtReturn.addElement(createParameterDefinition("MaxQueue", "",
				ParameterType.PARAM_TEXTBOX_MAX, "10000", ""));

		vtReturn.addElement(createParameterDefinition("Mobile", "",
				ParameterType.PARAM_TEXTBOX_MAX, "10000", ""));

		vtReturn.addAll(super.getParameterDefinition());
		return vtReturn;
	}

	/**
	 * 
	 */
	public void fillParameter() throws AppException {
		miMaxQueue = loadUnsignedInteger("MaxQueue");
		mstrImportDir = loadMandatory("ImportDir");
		mstrBackupDir = loadMandatory("BackupDir");
		mstrURL = loadMandatory("URL");
		mstrUsername = loadMandatory("UserName");
		mstrPassword = loadMandatory("Password");
		mstrFromAddr = loadMandatory("FromAddr");
		mstrSrcAddrTON = loadMandatory("SrcAddrTON");
		mstrSrcAddrNPI = loadMandatory("SrcAddrNPI");
		mstrDestAddrTON = loadMandatory("DestAddrTON");
		mstrDestAddrNPI = loadMandatory("DestAddrNPI");
		strmobile = loadMandatory("Mobile");
		mprtDeliverSM = new Properties();
		mprtDeliverSM.put("DataType", mstrThreadID);
		super.fillParameter();
	}

	
	private boolean sendGet2(String url) throws Exception {
		String USER_AGENT = "Mozilla/5.0";
		URL obj = new URL(url);
		//HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		// optional default is GET
//		con.setRequestMethod("GET");
//		// add request header
//		con.setRequestProperty("User-Agent", USER_AGENT);
//		
		
		HttpURLConnection	connection = (HttpURLConnection) obj.openConnection();
		connection.setRequestMethod("POST");

		connection.setConnectTimeout(220);
		connection.setRequestProperty("UserName", mstrUsername);
		connection.addRequestProperty("Password", mstrPassword);
		connection.addRequestProperty("FromAddr", mstrFromAddr);
//		connection.addRequestProperty("DestNo", accessToken);
//		
//		//connection.addRequestProperty("dcs", alSubscriber.get(i).getMSISDN());
//		connection.addRequestProperty("msg", contentObj.getContent());

		connection.addRequestProperty("SrcAddrTON", mstrSrcAddrTON);
		connection.addRequestProperty("DestAddrTON", mstrDestAddrTON);
		connection.addRequestProperty("SrcAddrNPI", mstrSrcAddrNPI);
		connection.addRequestProperty("DestAddrNPI", mstrDestAddrNPI);
		debugMonitor("Sending: " + connection.getRequestProperties(), 5);
		connection.connect();

		int responseCode = connection.getResponseCode();
		System.out.println("\nSending 'GET' request to URL " + url);
		System.out.println("Response Code : " + responseCode);
		// debugMonitor("Response Code : : " + responseCode, 9);
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			// print result
			debugMonitor("Response finally :" + response.toString(), 9);
			System.out.println("Response finally :" + response.toString());
			connection.disconnect();
			in.close();
			return true;
		} else {
			return false;
		}

	}

	
	private boolean sendGet1(String url) throws Exception {
		String USER_AGENT = "Mozilla/5.0";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL " + url);
		System.out.println("Response Code : " + responseCode);
		// debugMonitor("Response Code : : " + responseCode, 9);
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			// print result
			debugMonitor("Response finally :" + response.toString(), 9);
			System.out.println("Response finally :" + response.toString());
			con.disconnect();
			in.close();
			return true;
		} else {
			return false;
		}

	}

	// HTTP GET request
	private boolean sendGet(String context, String mobile) throws Exception {
		String USER_AGENT = "Mozilla/5.0";
		// ?PhoneNumber=0943818191&Text=hungvm123
		context = context.replaceAll("\\n", "");
		context = context.replaceAll("\\r", "");
		// context = context.replaceAll("\\0", "");
		String url = mstrURL + "?UserName=" + mstrUsername + "&Password="
				+ mstrPassword + "&FromAddr=" + mstrFromAddr + "&DestNo="
				+ mobile + "&dcs=8&udhi=1&bin_msg=\\u"
				+ AsnUtil.bytesToHexString(context.getBytes(Data.ENC_UTF16_LE))
				+ "&SrcAddrTON=" + mstrSrcAddrTON + "&SrcAddrNPI="
				+ mstrSrcAddrNPI + "&DestAddrTON=" + mstrDestAddrTON
				+ "&DestAddrNPI=" + mstrDestAddrNPI;

		debugMonitor(
				"LE:"
						+ AsnUtil.bytesToHexString(context
								.getBytes(Data.ENC_UTF16_LE)), 9);

		debugMonitor(
				"BE:"
						+ AsnUtil.bytesToHexString(context
								.getBytes(Data.ENC_UTF16_BE)), 9);

		return true;
		// URL obj = new URL(url);
		// HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// // optional default is GET
		// con.setRequestMethod("GET");
		// // add request header
		// con.setRequestProperty("User-Agent", USER_AGENT);
		// int responseCode = con.getResponseCode();
		// System.out.println("\nSending 'GET' request to URL " + url);
		// System.out.println("Response Code : " + responseCode);
		// // debugMonitor("Response Code : : " + responseCode, 9);
		// if (responseCode == 200) {
		// BufferedReader in = new BufferedReader(new InputStreamReader(
		// con.getInputStream()));
		// String inputLine;
		// StringBuffer response = new StringBuffer();
		// while ((inputLine = in.readLine()) != null) {
		// response.append(inputLine);
		// }
		// // print result
		// debugMonitor("Response finally :" + response.toString(), 9);
		// System.out.println("Response finally :" + response.toString());
		// con.disconnect();
		// in.close();
		// return true;
		// } else {
		// return false;
		// }

	}

	public void prinfencoder(String strinput) {
		try {

			// e18099e18082e181a4e1809ce180ace18095e180ab
			// e18099e18082e181a4e1809ce180ace18095e180ab

			// fffe1910021064101c102c1015102b10
			// 1910021064101c102c1015102b10
			// .out.println(java.net.URLEncoder.encode(strinput,Data.ENC_UTF8));
			// System.out.println(java.net.URLEncoder.encode(strinput,Data.ENC_UTF16_LE));
			// System.out.println(java.net.URLEncoder.encode(strinput,Data.ENC_UTF16_LE));
			System.out.println(AsnUtil.bytesToHexString(strinput
					.getBytes(Data.ENC_UTF8)));
			System.out.println(AsnUtil.bytesToHexString(strinput
					.getBytes(Data.ENC_UTF16_LE)));
			System.out.println(AsnUtil.bytesToHex(strinput
					.getBytes(Data.ENC_UTF16_LE)));

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void beforeSession() {
		System.out.println("");
	}

	public void processSession() {
		while (miThreadCommand != ThreadConstant.THREAD_STOP) {
			File fl = new File(mstrImportDir);
			String strFileList[] = fl.list(new WildcardFilter(mstrWildcard));
			if (strFileList != null && strFileList.length > 0) {
				Arrays.sort(strFileList);
				int iFileCount = strFileList.length;
				for (int iFileIndex = 0; iFileIndex < iFileCount; iFileIndex++) {
					try {
						while (getThreadManager().getQueuezise() > miMaxQueue) {
							Thread.sleep(2000);
						}
						LoadFile(strFileList[iFileIndex]);
					} catch (Exception ex) {
						Logger.getLogger(LoadDataThread.class.getName()).log(
								Level.SEVERE, null, ex);

					}
				}
			} else {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ex) {
					Logger.getLogger(LoadDataThread.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}
	}

	public void afterSession() {

	}

	public void LoadFile(String strFileName) throws Exception {
		try {
			String strLine = null;
			// BufferedReader in = null;
			debugMonitor(strFileName, 7);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(mstrImportDir + strFileName)));
			// in = new BufferedReader( new InputStreamReader(new
			// FileInputStream(mstrImportDir + strFileName)),"UTF-8");
			while ((strLine = in.readLine()) != null) {
				while (getThreadManager().getQueuezise() > miMaxQueue) {
					Thread.sleep(20);
				}
				// sendGet(strLine, strmobile);
				sendGet1(strLine);
				// prinfencoder(strLine);
				// getThreadManager().attachQueue(strLine);
			}
			in.close();
			fileConvertedHandler(strFileName);
		} catch (IOException ex) {
			Logger.getLogger(LoadDataThread.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	private void fileConvertedHandler(String strFileName) throws Exception {
		String backupType = "Daily";
		try {
			if (!mstrBackupDir.equals("")) {
				FileUtil.backup(mstrImportDir, mstrBackupDir, strFileName,
						strFileName, backupType);
				File flSrc = new File(mstrImportDir + strFileName);
				if (flSrc.exists()) {
					flSrc.delete();
				}
			} else {
				try {
					FileUtil.deleteFile(mstrImportDir + strFileName);
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
