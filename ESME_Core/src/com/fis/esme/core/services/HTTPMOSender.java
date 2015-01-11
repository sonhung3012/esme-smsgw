package com.fis.esme.core.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.Properties;

import org.quickserver.net.AppException;

import com.fis.esme.core.app.AppManager;
import com.fis.esme.core.app.ThreadSplitBase;
import com.fis.esme.core.bean.SmsBean;
import com.fis.esme.core.bean.SmsBeanFactory;
import com.fis.esme.core.bean.SmsBeanOracle;
import com.fis.esme.core.entity.VariableStatic;
import com.fis.esme.core.log.LogRecord;
import com.fis.esme.core.smsc.util.QueueMOManager;
import com.fis.esme.core.smsc.util.QueueMTManager;
import com.fis.esme.core.util.LinkQueue;
import com.fss.sql.Database;

public class HTTPMOSender extends ThreadSplitBase {
	public HTTPMOSender() {
	}

	private LinkQueue qMessageQueue = null;
	private QueueMTManager mlSMSQueueLevel;
	private QueueMOManager qQueueMOManager;
	private int count = 0;
	protected Properties mprtDeliverSM = null;

	@Override
	public void beforeSession() throws Exception {
		// TODO Auto-generated method stub
		setAutoConnectDb(true);
		super.beforeSession();
		qMessageQueue = getThreadManager().getLqMessageMOQueue();
		mlSMSQueueLevel = getThreadManager().getQueueMTManager();
		qQueueMOManager = getThreadManager().getMOQueue();
		mprtDeliverSM = ((HTTPSenderManager) mParentThreadManager)
				.getProperties();
	}

	@Override
	public void processSession() throws Exception {
		// TODO Auto-generated method stub
		while (isThreadRunning()) {
			int intFailCount = 0;
			LogRecord requestMessage = (LogRecord) qQueueMOManager
					.detach(getProperties());
			if (requestMessage != null) {
				count = 0;
				try {
					if (requestMessage != null) {
						String isdn = requestMessage.getIsdn();
						String message = requestMessage.getContent();
						String shortcode = requestMessage.getB_msisdn();
						String requestId = requestMessage.getRequestID();
						String commandcode = requestMessage.getCommand();
						String cpid = requestMessage.getDispatcherID();
						String subid = requestMessage.getSubID();
						String groupid = requestMessage.getGroupID();
						String status = String
								.valueOf(VariableStatic.SMS_LOG_STATUS_MO.SEND_SUCCESS
										.getValue());
						String errorcode = "";
						try {
							String response = sendMessage(
									requestMessage.getUrl(),
									requestMessage.getUsername(),
									requestMessage.getPassword(), isdn,
									message, shortcode, commandcode, requestId);
							if (!response.equalsIgnoreCase("") && !response.equalsIgnoreCase("Authenticated")) {
								// submit message with response content is
								// response
								submitMT(requestId, response, cpid, shortcode,
										isdn, commandcode,
										requestMessage.getRegisterDelivery(),"0",
										requestMessage.getSMSC_MO_ID(),subid, groupid);

							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							status = String
									.valueOf(VariableStatic.SMS_LOG_STATUS_MO.CP_DONT_RECEIVER
											.getValue());
							errorcode = e.getMessage();
						}
						requestMessage
								.setActionType(LogRecord.ACTION_UPDATE_TYPE);
						requestMessage.setErrorDescription(errorcode);
						requestMessage.setStatus(status);
						getThreadManager().attachLogRecord(requestMessage);
						debugMonitor("Sended sms to cp :status," + status
								+ ",isdn ," + requestMessage.getIsdn()
								+ ", shortcode ," + requestMessage.getContent()
								+ ",logid :" + requestMessage.getSessionID(), 3);
					} else {
						try {
							Thread.sleep(20);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					logMonitor(e.getMessage());
					e.printStackTrace();
					intFailCount++;
					throw e;
				}
			}
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private void submitMT(String requestid, String message, String cp_id,
			String shortcode, String msisdn, String commandcode,
			String registerDl, String strESMETransID,
			String strMOSequenceNumber, String subid, String groupuid) {
		Connection con = null;
		try {
			con = AppManager.getAppConnection();
			SmsBeanFactory.getSmsBeanFactory().insertSMSMT(con, requestid,
					message, cp_id, shortcode, msisdn, commandcode, registerDl,
					strESMETransID, strMOSequenceNumber, subid, groupuid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			debugMonitor(
					"Submit MT'Response faid with message:" + e.getMessage(), 0);
		} finally {
			Database.closeObject(con);
		}

	}

	private String sendMessage(String strUrl, String strUsername,
			String strPassword, String strIsdn, String strMessage,
			String shortcode, String commandcode, String request_id)
			throws Exception {
		HttpURLConnection connection = null;
		String respond = "";
		try {
			URL url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60 * 1000);
			connection.setRequestProperty("Username", strUsername);
			connection.addRequestProperty("Password", strPassword);
			connection.addRequestProperty("Isdn", strIsdn);
			connection.addRequestProperty("Message", strMessage);
			connection.addRequestProperty("Shortcode", shortcode);
			connection.addRequestProperty("Commandcode", commandcode);
			connection.addRequestProperty("Request_id", request_id);
			connection.connect();
			if (connection.getResponseCode() == 200) {
				InputStream stream = connection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						stream));
				String strLine;
				while ((strLine = br.readLine()) != null) {
					respond += strLine;
				}
				br.close();
			} else {
				throw new AppException("Connection fail with ResponseCode:"
						+ connection.getResponseCode());
			}
			return respond;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public Properties getProperties() {
		return mprtDeliverSM;
	}

	/**
	 * @overwrite
	 */
	protected void afterSession() throws Exception {
		// bean.close();
		qMessageQueue = null;
		super.afterSession();
	}
	public static void main(String [] agm)
	{
		
	}
}
