package com.fis.esme.core.smsc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import net.sf.ehcache.Ehcache;

import org.smpp.Data;
import org.smpp.ServerPDUEvent;
import org.smpp.debug.Event;
import org.smpp.pdu.SubmitSM;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.http.GSMHTTPServer;
import com.fis.esme.core.smsc.util.QueueMTManager;
import com.fis.esme.core.util.AsnUtil;
import com.fis.esme.core.util.LinkQueue;
import com.fss.mwallet.util.SubmitMessage;
import com.fss.thread.ParameterType;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;
import com.fss.util.StringUtil;

public class SMSHTTPTransceiver extends ManageableThreadEx {
	private List<String> mlstWhiteList;
	LinkQueue<ServerPDUEvent> mSmscRequestEventQueue = new LinkQueue<ServerPDUEvent>(
			10000);
	private Map mapRequestAckCache = null;
	private LinkQueue maqMORequestQueue;
	private QueueMTManager mqlSMSCQueueManager = null;
	private CharsetEncoder encoder;
	private Ehcache submitCache;
	private Ehcache deliveryCache;
	private GSMHTTPServer GsmHTTPServer = null;
	private int numOfDispatcher = 1;
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

	public void beforeSession() throws Exception {
		mqlSMSCQueueManager = getThreadManager().getQueueMTManager();
		mapRequestAckCache = mqlSMSCQueueManager.getRequestAckCache();
		maqMORequestQueue = getThreadManager().getMOQueue().getRequestMOQueue();
		mqlSMSCQueueManager.addDispatcher(this);
		encoder = Charset.forName("SCGSM").newEncoder();
		submitCache = getThreadManager().getSubmitCache();
		deliveryCache = getThreadManager().getDeliveryCache();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void processSession() throws Exception {
		SMSTransmitter[] smsTransmitter = new SMSTransmitter[numOfDispatcher];
		try {
			for (int i = 0; i < numOfDispatcher; i++) {
				logMonitor("Start receiver process " + i);
				GsmHTTPServer = new GSMHTTPServer(8800, "/");
				GsmHTTPServer.manageableThread = this;
				GsmHTTPServer.MORequestQueue = maqMORequestQueue;
				GsmHTTPServer.start();
				logMonitor("Start transmitter process " + i);
				smsTransmitter[i] = new SMSTransmitter(i);
				smsTransmitter[i].start();
			}
			while (miThreadCommand != ThreadConstant.THREAD_STOP) {
				fillLogFile();
				Thread.sleep(500);
			}
		} finally {
			GsmHTTPServer.stop();
			for (int i = 0; i < numOfDispatcher; i++) {
				if (smsTransmitter[i] != null) {
					smsTransmitter[i].stopNow();
				}
				Thread.sleep(100);
			}
		}
	}

	public void afterSession() throws Exception {
		mlstWhiteList = null;
		mprtDeliverSM = null;
		mqlSMSCQueueManager.removeDispatcher(this);
	}

	public class SMSTransmitter extends Thread {
		boolean bRunning = true;
		int iSenderIndex = 0;

		public void stopNow() {
			bRunning = false;
		}

		public SMSTransmitter(int index) {
			iSenderIndex = index;
		}

		public boolean isRunning() {
			return bRunning;
		}

		public String formatSendingMobileNo(String mobileNumber) {

			if (mobileNumber.startsWith(mstrMobileUnPrefix)) {
				mobileNumber = mobileNumber.substring(mstrMobileUnPrefix
						.length());
			}

			if (!mobileNumber.startsWith(mstrMobilePrefix)) {
				mobileNumber = mstrMobilePrefix + mobileNumber;
			}
			return mobileNumber;
		}

		private String formatMessage(String strInput) {
			if (strInput != null) {
				String strContent = StringUtil.replaceAll(strInput, "\\t",
						"    ");
				strContent = StringUtil.replaceAll(strContent, "\\n", "\n");
				strContent = StringUtil.replaceAll(strContent, "\\r", " ");
				return strContent;
			}
			return "";
		}

		public void run() {
			logMonitor("Transmitter process " + iSenderIndex + " started");
			SubmitMessage submitMessage = null;
			while (miThreadCommand != ThreadConstant.THREAD_STOP) {
				try {
					// su ly long message
					submitMessage = mqlSMSCQueueManager
							.getSubmitMessage(getProperties());
					long iSequenceNumber = -1;
					if (submitMessage != null) {
						sendGet(formatSendingMobileNo(submitMessage
								.getSubmitSM().getDestAddr().getAddress()),
								formatMessage(submitMessage
										.getExtendedMessage()),
								Data.ENC_UTF16_BE);
						//
						// sendGet2(formatSendingMobileNo(submitMessage
						// .getSubmitSM().getDestAddr().getAddress()),
						// formatMessage(submitMessage
						// .getExtendedMessage()), Data.ENC_UTF8);

					} else {
						Thread.sleep(100);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}// end while (bRunning)
			logMonitor("Transmitter process " + iSenderIndex + " stopped");
		}
	}

	private boolean onlyGSM7BitCharacter(String strContent) {
		return encoder.canEncode(strContent);
	}

	private boolean sendGet2(String PhoneNumber, String Text, String Unicode)
			throws Exception {
		String USER_AGENT = "Mozilla/5.0";
		URL obj = new URL(mstrURL);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		connection.setRequestMethod("POST");
		connection.setConnectTimeout(1220);
		// connection.set
		// connection.set
		// connection.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		connection.setRequestProperty("UserName",
				java.net.URLEncoder.encode(mstrUsername));
		connection.addRequestProperty("Password",
				java.net.URLEncoder.encode(mstrPassword));
		connection.addRequestProperty("FromAddr", mstrFromAddr);
		connection.addRequestProperty("DestNo", PhoneNumber);

		String context = java.net.URLEncoder.encode(Text);
		context = context.replaceAll("\\n", "");
		context = context.replaceAll("\\r", "");
		connection.addRequestProperty("msg", context);
		connection.addRequestProperty("SrcAddrTON", mstrSrcAddrTON);
		connection.addRequestProperty("DestAddrTON", mstrDestAddrTON);
		connection.addRequestProperty("SrcAddrNPI", mstrSrcAddrNPI);
		connection.addRequestProperty("DestAddrNPI", mstrDestAddrNPI);

		debugMonitor("Sending: " + connection.getRequestProperties(), 5);
		connection.connect();
		int responseCode = connection.getResponseCode();
		System.out.println("\nSending 'GET' request to URL "
				+ connection.getContentEncoding());
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

	// HTTP GET request
	private boolean sendGet(String PhoneNumber, String Text, String Unicode)
			throws Exception {
		String USER_AGENT = "Mozilla/5.0";
		// ?PhoneNumber=0943818191&Text=hungvm123

		String url = "";
		int iMaxLengthSingleMessage = 160;
		int iMaxLengthMultipleMessage = 153;
		String strEncoding = Data.ENC_ASCII;
		byte bDataCoding = 0x00;

		// Neu tin nhan co chua ky tu ngoai bang GSM 7 bit
		if (!onlyGSM7BitCharacter(Text)) {
			iMaxLengthSingleMessage = 70;
			iMaxLengthMultipleMessage = 66;
			url = mstrURL + "?UserName=" + mstrUsername + "&Password="
					+ mstrPassword + "&FromAddr=" + mstrFromAddr + "&DestNo="
					+ PhoneNumber + "&dcs=8&udhi=1&bin_msg="
					+ AsnUtil.bytesToHexString(Text.getBytes(Unicode))
					+ "&SrcAddrTON=" + mstrSrcAddrTON + "&SrcAddrNPI="
					+ mstrSrcAddrNPI + "&DestAddrTON=" + mstrDestAddrTON
					+ "&DestAddrNPI=" + mstrDestAddrNPI;
		} else {

			String msg = java.net.URLEncoder.encode(Text);
			url = mstrURL + "?UserName=" + mstrUsername + "&Password="
					+ mstrPassword + "&FromAddr=" + mstrFromAddr + "&DestNo="
					+ PhoneNumber + "&msg=" + msg + "&SrcAddrTON="
					+ mstrSrcAddrTON + "&SrcAddrNPI=" + mstrSrcAddrNPI
					+ "&DestAddrTON=" + mstrDestAddrTON + "&DestAddrNPI="
					+ mstrDestAddrNPI;
		}

		System.out.println("\nSending 'GET' request to URL encode " + Unicode
				+ " URL :" + url);

//		 return true;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL encode " + Unicode
				+ " URL :" + url);
		System.out.println("Response Code : " + responseCode);
		debugMonitor("Submit message: " + Text + " with response Code:"
				+ responseCode, 9);
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

	public Vector getParameterDefinition() {
		Vector vtReturn = new Vector();
		vtReturn.addElement(createParameterDefinition("NumOfDispatcher", "",
				ParameterType.PARAM_TEXTBOX_MASK, "99999", ""));
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
		vtReturn.addAll(super.getParameterDefinition());
		return vtReturn;
	}

	/**
	 * 
	 */
	public void fillParameter() throws AppException {
		numOfDispatcher = loadUnsignedInteger("NumOfDispatcher");
		mstrURL = loadMandatory("URL");
		mstrUsername = loadMandatory("UserName");
		mstrPassword = loadMandatory("Password");
		mstrFromAddr = loadMandatory("FromAddr");
		mstrSrcAddrTON = loadMandatory("SrcAddrTON");
		mstrSrcAddrNPI = loadMandatory("SrcAddrNPI");
		mstrDestAddrTON = loadMandatory("DestAddrTON");
		mstrDestAddrNPI = loadMandatory("DestAddrNPI");
		mprtDeliverSM = new Properties();
		mprtDeliverSM.put("DataType", mstrThreadID);
		super.fillParameter();
	}

	public Properties getProperties() {
		return mprtDeliverSM;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	protected String getThreadTableName() {
		return "ESME_SMSC";
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	protected String getThreadParamTableName() {
		return "ESME_SMSC_PARAM";
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	protected String getThreadNameFieldName() {
		return "NAME";
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	protected String getThreadIDFieldName() {
		return "SMSC_ID";
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	protected String getParameterNameFieldName() {
		return "NAME";
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	protected String getParameterValueFieldName() {
		return "VALUE";
	}

	public static void main(String[] agm) {

	}
}
