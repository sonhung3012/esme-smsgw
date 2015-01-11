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
import org.smpp.ServerPDUEvent;
import org.smpp.debug.Event;
import org.smpp.pdu.SubmitSM;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.http.GSMHTTPServer;
import com.fis.esme.core.smsc.util.QueueMTManager;
import com.fis.esme.core.util.LinkQueue;
import com.fss.mwallet.util.SubmitMessage;
import com.fss.thread.ParameterType;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;
import com.fss.util.StringUtil;

public class SMSGSMTransceiver extends ManageableThreadEx {
	private List<String> mlstWhiteList;
	LinkQueue<ServerPDUEvent> mSmscRequestEventQueue = new LinkQueue<ServerPDUEvent>(
			10000);
	private Map mapRequestAckCache = null;
	private LinkQueue maqMORequestQueue;
	private QueueMTManager mqlSMSCQueueManager = null;
	private CharsetEncoder encoder;
	private Ehcache submitCache;
	private Ehcache deliveryCache;
	private GSMHTTPServer GsmHTTPServer=null;
	private int numOfDispatcher = 1;
	private String ipAddress = null;
	private int port = 0;
	private String mstrMobilePrefix ="95";
	private String mstrMobileUnPrefix ="0";
	
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
				mobileNumber = mobileNumber.substring(mstrMobileUnPrefix.length());
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
								formatMessage(submitMessage.getExtendedMessage()));
					} else {
						Thread.sleep(100);
					}
				} catch (Exception e) {

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


	// HTTP GET request
	private void sendGet(String PhoneNumber, String Text) throws Exception {
		String USER_AGENT = "Mozilla/5.0";
		// ?PhoneNumber=0943818191&Text=hungvm123
		String url = "http://" + ipAddress + ":" + port + "/?PhoneNumber="
				+ PhoneNumber + "&Text=" + Text;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}

	public Vector getParameterDefinition() {
		Vector vtReturn = new Vector();
		vtReturn.addElement(createParameterDefinition("NumOfDispatcher", "",
				ParameterType.PARAM_TEXTBOX_MASK, "99999", ""));
		vtReturn.addElement(createParameterDefinition("ip-address", "",
				ParameterType.PARAM_TEXTBOX_MAX, "15", ""));
		vtReturn.addElement(createParameterDefinition("port", "",
				ParameterType.PARAM_TEXTBOX_MASK, "99999", ""));
		vtReturn.addAll(super.getParameterDefinition());
		return vtReturn;
	}

	/**
	 * 
	 */
	public void fillParameter() throws AppException {
		byte ton;
		byte npi;
		String addr;
		int rcvTimeout;
		numOfDispatcher = loadUnsignedInteger("NumOfDispatcher");
		ipAddress = loadMandatory("ip-address");
		port = loadUnsignedInteger("port");
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

}
