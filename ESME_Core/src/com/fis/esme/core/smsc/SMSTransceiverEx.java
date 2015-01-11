package com.fis.esme.core.smsc;

/*
 * Copyright (c) 1996-2001
 * Logica Mobile Networks Limited
 * All rights reserved.
 *
 * This software is distributed under Logica Open Source License Version 1.0
 * ("Licence Agreement"). You shall use it and distribute only in accordance
 * with the terms of the License Agreement.
 *
 */

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.smpp.Data;
import org.smpp.ServerPDUEvent;
import org.smpp.ServerPDUEventListener;
import org.smpp.Session;
import org.smpp.SmppObject;
import org.smpp.TCPIPConnection;
import org.smpp.debug.Debug;
import org.smpp.debug.DefaultDebug;
import org.smpp.debug.DefaultEvent;
import org.smpp.debug.Event;
import org.smpp.debug.FileDebug;
import org.smpp.debug.FileEvent;
import org.smpp.pdu.Address;
import org.smpp.pdu.AddressRange;
import org.smpp.pdu.BindReceiver;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransciever;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.DeliverSM;
import org.smpp.pdu.EnquireLink;
import org.smpp.pdu.EnquireLinkResp;
import org.smpp.pdu.PDU;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.Request;
import org.smpp.pdu.Response;

import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.pdu.Unbind;
import org.smpp.pdu.UnbindResp;
import org.smpp.pdu.ValueNotSetException;
import org.smpp.pdu.WrongLengthOfStringException;
import org.smpp.util.ByteBuffer;
import org.smpp.util.Queue;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.log.LogRecord;
import com.fis.esme.core.log.SMSLogRecord;
import com.fis.esme.core.services.SMPPServer;
import com.fis.esme.core.smpp.entity.TransactionDataObject;
import com.fis.esme.core.smsc.util.QueueMTManager;
import com.fis.esme.core.smsc.util.ReceiveMessageEx;
import com.fis.esme.core.smsc.util.SendSMSLog;
import com.fis.esme.core.util.GlobalParameter;
import com.fis.esme.core.util.LinkQueue;
import com.fss.mwallet.util.SubmitMessage;
import com.fss.thread.ParameterType;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;
import com.fss.util.StringUtil;

public class SMSTransceiverEx extends ManageableThreadEx {
	String syncMode;
	/**
	 * This is the SMPP session used for communication with SMSC.
	 */
	Session session = null;
	byte replaceIfPresentFlag = 0;
	/**
	 * If the application is bound to the SMSC.
	 */
	boolean bound = false;

	/**
	 * Address of the SMSC.
	 */
	String ipAddress = null;
	/**
	 * The port number to bind to on the SMSC server.
	 */
	int port = 0;
	/**
	 * The name which identifies you to SMSC.
	 */
	String systemId = null;
	/**
	 * The password for authentication to SMSC.
	 */
	String password = null;
	/**
	 * How you want to bind to the SMSC: transmitter (t), receiver (r) or
	 * transciever (tr). Transciever can both send messages and receive
	 * messages. Note, that if you bind as receiver you can still receive
	 * responses to you requests (submissions).
	 */
	String bindMode = "t";
	boolean blReciveDelivery = false;
	/**
	 * Indicates that the Session has to be asynchronous. Asynchronous Session
	 * means that when submitting a Request to the SMSC the Session does not
	 * wait for a response. Instead the Session is provided with an instance of
	 * implementation of ServerPDUListener from the smpp library which receives
	 * all PDUs received from the SMSC. It's application responsibility to match
	 * the received Response with sended Requests.
	 */
	boolean asynchronous = false;

	/**
	 * This is an instance of listener which obtains all PDUs received from the
	 * SMSC. Application doesn't have explicitly call Session's receive()
	 * function, all PDUs are passed to this application callback object. See
	 * documentation in Session, Receiver and ServerPDUEventListener classes
	 * form the SMPP library.
	 */
	SMPPMcaPDUEventListener pduListener = null;
	/**
	 * The range of addresses the smpp session will serve.
	 */
	AddressRange addressRange = new AddressRange();

	/*
	 * for information about these variables have a look in SMPP 3.4
	 * specification
	 */
	Address sourceAddress = new Address();
	Address destAddress = new Address();

	/**
	 * If you attemt to receive message, how long will the application wait for
	 * data.
	 */
	long receiveTimeout = Data.RECEIVE_BLOCKING;
	int enquireInterval = 60;
	long nextEnquireLink = 0;
	long lastReceiveEnquireLinkResp = 0;
	boolean hasError = false;
	String ignoreMessage = "";
	String[] paramIgnore;
	String mstrSystemType = "";
	int numOfDispatcher = 1;
	static final String dbgDir = "./";
	public static final int DSIM = 16;
	public static final int DSIMD = 17;
	public static final int DSIMD2 = 18;
	protected boolean debugOption = true;
	private String mstrDeliveryStatuskey = "stat";
	private String mstrDeliveryStatusValue = "0";
	/**
	 * The debug object.
	 */
	static Debug debug = null;
	/**
	 * The event object.
	 */
	static Event event = null;
	protected static byte DELIVERY_REPORT_SIGNAL = 4;
	private List<String> mlstWhiteList;
	LinkQueue<ServerPDUEvent> mSmscRequestEventQueue = new LinkQueue<ServerPDUEvent>(
			10000);
	private Map mapRequestAckCache = null;
	private LinkQueue maqMORequestQueue;
	private QueueMTManager mqlSMSCQueueManager = null;
	private CharsetEncoder encoder;
	private Ehcache submitCache;
	private Ehcache deliveryCache;

	/**
	 * 
	 */
	public void beforeSession() throws Exception {

		mqlSMSCQueueManager = getThreadManager().getQueueMTManager();
		mapRequestAckCache = mqlSMSCQueueManager.getRequestAckCache();
		maqMORequestQueue = getThreadManager().getMOQueue().getRequestMOQueue();
		mqlSMSCQueueManager.addDispatcher(this);
		encoder = Charset.forName("SCGSM").newEncoder();
		submitCache = getThreadManager().getSubmitCache();
		deliveryCache = getThreadManager().getDeliveryCache();
		super.beforeSession();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void processSession() throws Exception {
		SMSReceiver[] smsReceiver = new SMSReceiver[numOfDispatcher];
		SMSTransmitter[] smsTransmitter = new SMSTransmitter[numOfDispatcher];
		try {
			hasError = false;
			logMonitor("Start binding to SMSC server....");
			bind();
			if (!bound) {
				return;
			}
			for (int i = 0; i < numOfDispatcher; i++) {
				if (bindMode.compareToIgnoreCase("t") == 0) {
					logMonitor("Start receiver process " + i);
					smsReceiver[i] = new SMSReceiver(i);
					smsReceiver[i].start();
					logMonitor("Start transmitter process " + i);
					smsTransmitter[i] = new SMSTransmitter(i);
					smsTransmitter[i].start();
				} else if (bindMode.compareToIgnoreCase("r") == 0) {
					logMonitor("Start receiver process " + i);
					smsReceiver[i] = new SMSReceiver(i);
					smsReceiver[i].start();
				} else if (bindMode.compareToIgnoreCase("tr") == 0) {
					logMonitor("Start receiver process " + i);
					smsReceiver[i] = new SMSReceiver(i);
					smsReceiver[i].start();
					logMonitor("Start transmitter process " + i);
					smsTransmitter[i] = new SMSTransmitter(i);
					smsTransmitter[i].start();
				}

			}
			lastReceiveEnquireLinkResp = System.currentTimeMillis();
			while (miThreadCommand != ThreadConstant.THREAD_STOP && !hasError) {
				if (shouldSendEnquireLink()) {
					enquireLink();
				}
				if (shouldReConnect()) {
					logMonitor("Wait message last more than usual, need to reconnect...");
					hasError = true;
				}
				fillLogFile();
				Thread.sleep(500);
			}
		} finally {
			for (int i = 0; i < numOfDispatcher; i++) {
				if (smsReceiver[i] != null) {
					smsReceiver[i].stopNow();
				}
				if (smsTransmitter[i] != null) {
					smsTransmitter[i].stopNow();
				}
				Thread.sleep(100);
			}
			unbind();
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isOpen() {
		return session.isOpened();
	}

	/**
 * 
 */
	public void afterSession() throws Exception {
		mlstWhiteList = null;
		mprtDeliverSM = null;
		mqlSMSCQueueManager.removeDispatcher(this);
		super.afterSession();
	}

	public void updateLastReceiveEnquireLinkResp() {
		lastReceiveEnquireLinkResp = System.currentTimeMillis();
	}

	public boolean shouldReConnect() {
		// Khi gui bang tin enquireLink xong ma 10s sau van chua co response thi
		// reconnect
		return nextEnquireLink - 4 * enquireInterval * 1000 > lastReceiveEnquireLinkResp;
	}

	public class SMSReceiver extends Thread {
		boolean bRunning = true;
		int iReceiverIndex = 0;

		public void stopNow() {
			bRunning = false;
		}

		public SMSReceiver(int index) {
			iReceiverIndex = index;
		}

		public boolean isRunning() {
			return bRunning;
		}

		public void run() {
			logMonitor("Receiver process " + iReceiverIndex + " started");
			while (bRunning) {
				try {
					receive();
					Thread.sleep(50);
				} catch (Exception e) {
					hasError = true;
					bRunning = false;
					logMonitor("Error occurs when getting message: " + e);
				}
			}
			logMonitor("Receiver process " + iReceiverIndex + " stopped");
		}
	}

	public boolean shouldSendEnquireLink() {
		return System.currentTimeMillis() >= nextEnquireLink;
	}

	public void updateTimeNextSendEnquireLink() {
		nextEnquireLink = System.currentTimeMillis() + enquireInterval * 1000;
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
			if (mobileNumber.startsWith("0")) {
				mobileNumber = mobileNumber.substring(1, mobileNumber.length());
				mobileNumber = "95" + mobileNumber;
			} else if (mobileNumber.startsWith("95")) {
				mobileNumber = mobileNumber;
			} else {
				mobileNumber = "95" + mobileNumber;
			}
			return mobileNumber;
		}

		private String[] SplitByWidth(String message, int leng) {
			int iLeng = leng;
			double iMessageNumber = Math.ceil(Double.valueOf(message.length())
					/ Double.valueOf(iLeng));
			int iNumber = (int) (iMessageNumber);
			String[] arrMessage = new String[iNumber];
			for (int i = 1; i <= iNumber; i++) {
				String msg = "";
				if (i == iNumber) {
					msg = message.substring((i - 1) * iLeng, message.length());
				} else {
					msg = message.substring((i - 1) * iLeng, i * iLeng);
				}
				arrMessage[i - 1] = msg;
			}
			return arrMessage;
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
			while (bRunning) {
				SubmitSM submitRequest = null;
				SubmitMessage submitMessage = null;
				try {
					// su ly long message
					submitMessage = mqlSMSCQueueManager
							.getSubmitMessage(getProperties());
					long iSequenceNumber = -1;
					if (submitMessage != null) {
						String Text_SMS = submitMessage.getExtendedMessage();
						if (Text_SMS == null) {
							logMonitor("Warning: Submit message is null. Don't send to subscriber through SMSC.");
							continue;
						}
						Text_SMS = formatMessage(Text_SMS);

						if (submitMessage.getRequestID() <= 0)
							iSequenceNumber = mqlSMSCQueueManager.getSequence();
						else {
							iSequenceNumber = submitMessage.getRequestID();
						}

						int iMaxLengthSingleMessage = 160;
						int iMaxLengthMultipleMessage = 153;
						String strEncoding = Data.ENC_ASCII;
						byte bDataCoding = 0x00;

						// Neu tin nhan co chua ky tu ngoai bang GSM 7 bit
						if (!onlyGSM7BitCharacter(Text_SMS)) {
							iMaxLengthSingleMessage = 70;
							iMaxLengthMultipleMessage = 66;
							strEncoding = Data.ENC_UTF16_BE;
							bDataCoding = 0x08;
						}
						if (Text_SMS.length() > iMaxLengthSingleMessage) {
							SubmitSM smRequest = submitMessage.getSubmitSM();
							// smRequest.setEsmClass((byte) 10);
							smRequest.setEsmClass((byte) Data.SM_UDH_GSM); // chï¿½
																			// \u00FD
																			// ph\u1EA3i
																			// Set
																			// UDHI
																			// Flag
																			// Data.SM_UDH_GSM=0ï¿½40
							String[] splittedMsg = this.SplitByWidth(Text_SMS,
									iMaxLengthMultipleMessage);

							int totalSegments = splittedMsg.length;
							String strBeforeFormat = smRequest.getDestAddr()
									.getAddress();
							for (int i = 0; i < totalSegments; i++) {
								ByteBuffer ed = new ByteBuffer();
								ed.appendByte((byte) 5); // UDH Length
								ed.appendByte((byte) (0 * 00)); // IE Identifier
								ed.appendByte((byte) 3); // IE Data Length
								ed.appendByte((byte) 2); // Reference Number
								ed.appendByte((byte) totalSegments); // Number
																		// of
																		// pieces
								ed.appendByte((byte) (i + 1)); // Sequence
																// number
								ed.appendString(splittedMsg[i], strEncoding);
								smRequest.setShortMessageData(ed);
								smRequest.setSourceAddr(sourceAddress);
								smRequest.getDestAddr().setNpi(
										(byte) destAddress.getNpi());
								smRequest.getDestAddr().setTon(
										(byte) destAddress.getTon());
								String strCalledNumber = formatSendingMobileNo(strBeforeFormat);
								smRequest.getDestAddr().setAddress(
										strCalledNumber);
								smRequest
										.setReplaceIfPresentFlag(replaceIfPresentFlag);
								smRequest
										.setSequenceNumber((int) iSequenceNumber);
								smRequest.setDataCoding(bDataCoding);
								if (Text_SMS.equals("")) {
									logMonitor("Warning: Submit message is empty. Don't send to subscriber through SMSC."
											+ smRequest.debugString());
									continue;
								}

								if (!checkWhiteList(strBeforeFormat)) {
									debugMonitor("Submit simulation data "
											+ smRequest.debugString(), 2);
									mqlSMSCQueueManager
											.processResponseForWhitelist(
													(int) iSequenceNumber,
													mstrThreadID);
									Thread.sleep(50);
									continue;
								}

								if (asynchronous) {
									session.submit(smRequest);
								} else {
									SubmitSMResp submitResponse = null;
									submitResponse = session.submit(smRequest);
									if (submitResponse != null) {
										Element cacheObj = submitCache
												.get(String
														.valueOf(iSequenceNumber));
										if (submitCache != null) {
											TransactionDataObject transObj = (TransactionDataObject) cacheObj
													.getValue();
											transObj.setSMSCTransID(submitResponse
													.getMessageId());
											transObj.setSMSCID(mstrThreadID);
											submitCache.remove(String
													.valueOf(iSequenceNumber));
											String strKey = transObj
													.getSMSCTransID()
													+ "_"
													+ transObj.getSMSCID();
											deliveryCache.put(new Element(
													strKey, transObj));
										}
										processResponse(submitResponse);
										debugMonitor(
												submitResponse.debugString(), 1);
									} else {
										logMonitor("No response from smsc.");
										hasError = true;
									}

								}
								debugMonitor(
										"Submit data "
												+ smRequest.debugString(), 2);
							}
						} else {
							submitRequest = submitMessage.getSubmitSM();
							submitRequest.setSourceAddr(sourceAddress);
							submitRequest
									.setShortMessage(Text_SMS, strEncoding);
							
							//logMonitor(""+submitRequest.getBody());
							logMonitor("debug:"+submitRequest.debugString());
							
							submitRequest.getDestAddr().setNpi(
									(byte) destAddress.getNpi());
							submitRequest.getDestAddr().setTon(
									(byte) destAddress.getTon());
							String strBeforeFormat = submitRequest
									.getDestAddr().getAddress();
							String strCalledNumber = formatSendingMobileNo(strBeforeFormat);
							submitRequest.getDestAddr().setAddress(
									strCalledNumber);
							submitRequest.setEsmClass((byte) 0);
							submitRequest.setDataCoding(bDataCoding);
							submitRequest
									.setSequenceNumber((int) iSequenceNumber);
							// additional paprameters
							if (!checkWhiteList(strBeforeFormat)) {
								debugMonitor("Submit simulation data "
										+ submitRequest.debugString(), 2);
								mqlSMSCQueueManager
										.processResponseForWhitelist(
												(int) iSequenceNumber,
												mstrThreadID);
								continue;
							}
							if (asynchronous) {
								session.submit(submitRequest);
							} else {
								SubmitSMResp submitResponse = null;
								submitResponse = session.submit(submitRequest);
								if (submitResponse != null) {
									Element cacheObj = submitCache.get(String
											.valueOf(iSequenceNumber));
									if (submitCache != null) {
										TransactionDataObject transObj = (TransactionDataObject) cacheObj
												.getValue();
										transObj.setSMSCTransID(submitResponse
												.getMessageId());
										transObj.setSMSCID(mstrThreadID);
										submitCache.remove(String
												.valueOf(iSequenceNumber));
										String strKey = transObj
												.getSMSCTransID()
												+ "_"
												+ transObj.getSMSCID();
										deliveryCache.put(new Element(strKey,
												transObj));
									}
									if (submitResponse.getCommandStatus() != Data.ESME_ROK) {
										logMonitor("submit is not sucess, content "
												+ submitResponse.debugString());
									} else {
										debugMonitor(
												submitResponse.debugString(), 1);
									}
									processResponse(submitResponse);
								} else {
									logMonitor("No response from smsc.");
									hasError = true;
								}
							}
							debugMonitor(
									"Submit data "
											+ submitRequest.debugString(), 2);
						}
					} else {
						Thread.sleep(miDelayTime * 1000);
					}
				}// end if (submitMessage != null)
				catch (WrongLengthOfStringException e) {
					// not session error, don't restart or reconnect smsc
					// connection
					if (submitRequest != null)
						logMonitor("Error occurs when sending message: " + e
								+ ", debug string "
								+ submitRequest.debugString());
					else
						logMonitor("Error occurs when sending message: " + e);
					e.printStackTrace();
				} catch (ValueNotSetException e) {
					hasError = true;
					bRunning = false;
					e.printStackTrace();
					if (submitRequest != null)
						logMonitor("Error occurs when sending message: " + e
								+ ", debug string "
								+ submitRequest.debugString());
					else
						logMonitor("Error occurs when sending message: " + e);
				} catch (TimeoutException e) {
					e.printStackTrace();
					hasError = true;
					bRunning = false;
					retryWhenSubmitFail(submitMessage, e);
					if (submitRequest != null)
						logMonitor("Error occurs when sending message: " + e
								+ ", debug string "
								+ submitRequest.debugString());
					else
						logMonitor("Error occurs when sending message: " + e);
				} catch (PDUException e) {
					e.printStackTrace();
					hasError = true;
					bRunning = false;
					retryWhenSubmitFail(submitMessage, e);
					if (submitRequest == null)
						logMonitor("Error occurs when sending message: " + e);
				} catch (IOException e) {
					e.printStackTrace();
					hasError = true;
					bRunning = false;
					retryWhenSubmitFail(submitMessage, e);
					if (submitRequest == null)
						logMonitor("Error occurs when sending message: " + e);
				} catch (WrongSessionStateException e) {
					e.printStackTrace();
					hasError = true;
					bRunning = false;
					retryWhenSubmitFail(submitMessage, e);
					if (submitRequest == null)
						logMonitor("Error occurs when sending message: " + e);
				} catch (Exception e) {
					// not session error, don't restart or reconnect smsc
					// connection
					e.printStackTrace();
					if (submitRequest != null)
						logMonitor("Error occurs when sending message: " + e
								+ ", debug string "
								+ submitRequest.debugString());
					else
						logMonitor("Error occurs when sending message: " + e);
				} finally {
					try {
						Thread.sleep(50);
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

	/**
	 * The first method called to start communication betwen an ESME and a SMSC.
	 * A new instance of <code>TCPIPConnection</code> is created and the IP
	 * address and port obtained from user are passed to this instance. New
	 * <code>Session</code> is created which uses the created
	 * <code>TCPIPConnection</code>. All the parameters required for a bind are
	 * set to the <code>BindRequest</code> and this request is passed to the
	 * <code>Session</code>'s <code>bind</code> method. If the call is
	 * successful, the application should be bound to the SMSC. See "SMPP
	 * Protocol Specification 3.4, 4.1 BIND Operation."
	 * 
	 * @see BindRequest
	 * @see BindResponse
	 * @see TCPIPConnection
	 * @see Session#bind(BindRequest)
	 * @see Session#bind(BindRequest,ServerPDUEventListener)
	 * @throws Exception
	 */
	public void bind() throws Exception {
		logMonitor("Binding with bind-mode: " + bindMode + " and sync-mode: "
				+ syncMode);
		try {
			if (bound) {
				this.unbind();
			}
			BindRequest request = null;
			BindResponse response = null;
			if (bindMode.compareToIgnoreCase("t") == 0) {
				request = new BindTransmitter();
			} else if (bindMode.compareToIgnoreCase("r") == 0) {
				request = new BindReceiver();
			} else if (bindMode.compareToIgnoreCase("tr") == 0) {
				request = new BindTransciever();
			} else {
				throw new Exception(
						"Invalid bind mode, expected t, r or tr, got "
								+ bindMode + ". Operation canceled.");
			}
			TCPIPConnection connection = new TCPIPConnection(ipAddress, port);
			connection.setReceiveTimeout(10 * 1000);
			connection.setCommsTimeout(60 * 1000);

			session = new Session(connection);
			// set values
			request.setSystemId(systemId);
			request.setPassword(password);
			request.setSystemType(mstrSystemType);
			request.setInterfaceVersion((byte) 0x34);
			request.setAddressRange(addressRange);
			// send the request
			logMonitor("Bind request " + getBindRequestInfor(request));
			if (asynchronous) {
				pduListener = new SMPPMcaPDUEventListener();
				response = session.bind(request, pduListener);
			} else {
				response = session.bind(request);
			}
			if (response != null) {
				logMonitor("Bind response " + response.debugString());
				if (response.getCommandStatus() == Data.ESME_ROK) {
					bound = true;
				} else {
					logMonitor("Bind failure with error code:"
							+ response.getCommandStatus());
				}
			} else {
				logMonitor("No response from smsc,please check the parammeters.");
			}
		} catch (Exception e) {
			logMonitor("Bind operation failed. ");
			throw e;
		}
	}

	private String getBindRequestInfor(BindRequest request) {
		String dbgs = "(bindreq: ";
		dbgs += request.getSystemId();
		dbgs += " ";
		dbgs += request.getSystemType();
		dbgs += " ";
		dbgs += Integer.toString(request.getInterfaceVersion());
		dbgs += " ";
		dbgs += request.getAddressRange().debugString();
		dbgs += ") ";
		return dbgs;
	}

	/**
	 * Ubinds (logs out) from the SMSC and closes the connection.
	 * 
	 * See "SMPP Protocol Specification 3.4, 4.2 UNBIND Operation."
	 * 
	 * @see Session#unbind()
	 * @see Unbind
	 * @see UnbindResp
	 */

	private void unbind() {
		logMonitor("Going to unbind");
		if (session == null) {
			return;
		}
		try {
			if (!bound) {
				logMonitor("Not bound, cannot unbind.");
				return;
			}
			// send the request
			UnbindResp response = session.unbind();
			logMonitor("Unbind response " + response == null ? "is null"
					: response.debugString());
			bound = false;
		} catch (Exception e) {
			logMonitor("Unbind operation failed. " + e);
		} finally {
			bound = false;
		}
	}

	/**
	 * Creates a new instance of <code>EnquireSM</code> class. This PDU is used
	 * to check that application level of the other party is alive. It can be
	 * sent both by SMSC and ESME. See "SMPP Protocol Specification 3.4, 4.11
	 * ENQUIRE_LINK Operation."
	 * 
	 * @see Session#enquireLink(EnquireLink)
	 * @see EnquireLink
	 * @see EnquireLinkResp
	 * @throws Exception
	 */
	private void enquireLink() throws Exception {
		EnquireLink request = new EnquireLink();
		EnquireLinkResp response;
		String strDebug = "";
		debugMonitor("Enquire Link pre request " + request.debugString(), 8);
		if (asynchronous) {
			session.enquireLink(request);
			if (bindMode.compareToIgnoreCase("r") == 0)
				strDebug = "Enquire Link requested (mo = "
						+ mSmscRequestEventQueue.getSize() + ")";
			else if (bindMode.compareToIgnoreCase("t") == 0)
				strDebug = "Enquire Link requested (mo = "
						+ mSmscRequestEventQueue.getSize() + ")" + " (mt = "
						+ mqlSMSCQueueManager.getQueueSize() + ")";
			else if (bindMode.compareToIgnoreCase("tr") == 0)
				strDebug = "Enquire Link requested (mo = "
						+ mSmscRequestEventQueue.getSize() + ")" + " (mt = "
						+ mqlSMSCQueueManager.getQueueSize() + ")";
			debugMonitor(strDebug, 8);
		} else {
			response = session.enquireLink(request);
			logMonitor("Enquire Link response " + response == null ? "No response."
					: response.debugString());
			if (response == null
					|| response.getCommandStatus() != Data.ESME_ROK) {
				hasError = true;

			} else {
				updateLastReceiveEnquireLinkResp();
			}
		}
		updateTimeNextSendEnquireLink();
	}

	/**
	 * Receives one PDU of any type from SMSC and prints it on the screen.
	 * 
	 * @see Session#receive()
	 * @see Response
	 * @see ServerPDUEvent
	 * @throws Exception
	 * @return boolean
	 */

	private boolean isReceiveDelivery() {
		return blReciveDelivery;
	}

	/**
	 * 
	 * @param strShortMessage
	 * @return
	 */
	private long getMessageID(String strShortMessage) {
		int beginIndex = strShortMessage.indexOf(":") + 1;
		int endIndex = strShortMessage.indexOf(" ");
		String strMessageID = strShortMessage.substring(beginIndex, endIndex);
		return Long.parseLong(strMessageID);
	}

	private String getDeliveryAtributed(String strShortMessage,
			String strAtribute) {
		int beginIndex = strShortMessage.indexOf(strAtribute)
				+ strAtribute.length() + 1;
		int icurrentIndex = beginIndex;
		String strValue = "";
		while (icurrentIndex < strShortMessage.length()) {
			char cTemp = strShortMessage.charAt(icurrentIndex);
			if (cTemp == ' ') {
				break;
			}
			strValue += cTemp;
			icurrentIndex++;
		}
		return strValue;
	}

	private boolean receive() throws Exception {
		PDU pdu = null;
		if (asynchronous) {
			ServerPDUEvent pduEvent = mSmscRequestEventQueue
					.dequeueWait((int) receiveTimeout / 1000);
			if (pduEvent != null) {
				pdu = pduEvent.getPDU();
			}
		} else {
			pdu = session.receive(receiveTimeout);
		}
		if (pdu == null) {
			return false;
		}
		if (pdu.isRequest()) {
			// sending response to SMSC
			Response response = ((Request) pdu).getResponse();
			debugMonitor("ACK to SMSC: " + response.debugString(), 2);
			// send default response
			if (isOpen()) {
				session.respond(response);
			}
			// enqueue data
			if (pdu instanceof DeliverSM) {
				DeliverSM deliverSM = (DeliverSM) pdu;
				// deliverSM.getDataCoding() == 0x08; --ucs2
				// 0x
				// Check Address Range

				String strEncoding = Data.ENC_ASCII;

				byte dataCoding = deliverSM.getDataCoding();
				if (dataCoding == 0x08) {
					strEncoding = Data.ENC_UTF16_BE;
				}

				String vstrSourceAddress = deliverSM.getSourceAddr()
						.getAddress();
				ReceiveMessageEx receiveMessage = new ReceiveMessageEx(pdu);
				byte esmClass = deliverSM.getEsmClass();
				String strContent = deliverSM.getShortMessage(strEncoding);
				debugMonitor("Content: " + strContent + " - datacoding: "
						+ dataCoding, 8);
				debugMonitor("Data:" + deliverSM.getData().getHexDump(), 8);
				debugMonitor("Body:" + deliverSM.getBody().getHexDump(), 8);

				if (esmClass == DELIVERY_REPORT_SIGNAL) {
					SMSLogRecord smsLog = new SMSLogRecord();
					String callingNumber = deliverSM.getSourceAddr()
							.getAddress();
					String strShortMessage = deliverSM.getShortMessage();
					debugMonitor(strShortMessage, 5);
					String strStar = getDeliveryAtributed(strShortMessage,
							mstrDeliveryStatuskey);
					if (strStar != null
							&& strStar != ""
							&& strStar
									.equalsIgnoreCase(mstrDeliveryStatusValue)) {
						smsLog.setStatus(SendSMSLog.SUCCESSFULL);
					} else {
						smsLog.setStatus(SendSMSLog.SEND_TO_USER_FAIL);
					}
					long commandid = getMessageID(strShortMessage);
					smsLog.setIsdn(callingNumber);
					smsLog.setCommandID(String.valueOf(commandid));
					smsLog.setSMSC_ID(mstrThreadID);
					getThreadManager().attachLogRecord(smsLog);
					debugMonitor("Received delivery report: " + "transid :"
							+ commandid + ",msisdn:" + callingNumber
							+ ",status :" + strStar, 3);
					String strKey = commandid + "_" + mstrThreadID;
					Element cacheObj = deliveryCache.get(strKey);
					if (cacheObj != null) {
						TransactionDataObject transObj = (TransactionDataObject) cacheObj
								.getValue();
						String strDeliveryReportContent = strShortMessage
								.replace(String.valueOf(commandid),
										transObj.getESMETransID());
						deliverSM.setShortMessage(strDeliveryReportContent);
						SMPPServer.serverRequest(
								Long.parseLong(transObj.getCPID()), deliverSM);
						deliveryCache.remove(strKey);
						debugMonitor(
								"send delivery report to CP "
										+ transObj.getCPID() + ":"
										+ deliverSM.debugString(), 3);
					}
				} else if (!vstrSourceAddress.equals("")) // lenh gui den tong
															// dai 9232
				{
					debugMonitor("Received data: " + deliverSM.debugString(), 1);
					receiveMessage.setAttribute("smsc_id", mstrThreadID);
					receiveMessage.setAttribute("request_id",
							String.valueOf(deliverSM.getSequenceNumber()));
					receiveMessage.setAttribute("DataType", "DeliverSM");

					try {
						maqMORequestQueue.enqueueNotify(receiveMessage);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				} else {
					debugMonitor(
							"Received unknown data: " + deliverSM.debugString(),
							3);
				}
			} else {
				debugMonitor("Received unknown data: " + pdu.debugString(), 3);
			}
		} else if (pdu.isResponse()) {
			if (pdu instanceof EnquireLinkResp) {
				debugMonitor(pdu.debugString(), 8);
				updateLastReceiveEnquireLinkResp();
			} else // Ack thue bao den he thong 9232
			{
				try {
					if (pdu instanceof PDU) {
						PDU pduLog = (PDU) pdu;
						int iSequence = pduLog.getSequenceNumber();
						SubmitSMResp ssm = (SubmitSMResp) pduLog;
						String s = ssm.getMessageId();
						Element cacheObj = submitCache.get(String
								.valueOf(iSequence));
						if (cacheObj != null) {
							TransactionDataObject transObj = (TransactionDataObject) cacheObj
									.getValue();
							transObj.setSMSCTransID(s);
							transObj.setSMSCID(mstrThreadID);
							submitCache.remove(String.valueOf(iSequence));
							String strKey = transObj.getSMSCTransID() + "_"
									+ transObj.getSMSCID();
							deliveryCache.put(new Element(strKey, transObj));
						}
						long commandid = -1;
						try {
							commandid = Long.parseLong(s);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						debugMonitor("Response pdu, requence : " + iSequence
								+ ",commandid :" + commandid, 5);
					}
					processResponse(pdu);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					logMonitor("Error occured: " + e.getMessage());
				}
			}
		}
		return true;
	}

	/**
	 * Implements simple PDU listener which handles PDUs received from SMSC. It
	 * puts the received requests into a queue and discards all received
	 * responses. Requests then can be fetched (should be) from the queue by
	 * calling to the method <code>getRequestEvent</code>.
	 * 
	 * @see Queue
	 * @see ServerPDUEvent
	 * @see ServerPDUEventListener
	 * @see SmppObject
	 */
	private class SMPPMcaPDUEventListener extends SmppObject implements
			ServerPDUEventListener {
		public void handleEvent(ServerPDUEvent event) {
			PDU pdu = event.getPDU();
			DeliverSM deliverSM = null;
			if (pdu.isRequest() || pdu.isResponse()) {
				mSmscRequestEventQueue.enqueueNotify(event);
			} else {
				logMonitor("pdu of unknown class (not request nor "
						+ "response) received, discarding " + pdu.debugString());
			}
		}
	}

	public Vector getParameterDefinition() {
		Vector vtReturn = new Vector();
		// //////////////////////////////////////////////////////
		Vector vtValue = new Vector();
		vtValue.addElement("Y");
		vtValue.addElement("N");
		vtReturn.addElement(createParameterDefinition("ReciveDelivery", "",
				ParameterType.PARAM_COMBOBOX, vtValue, "ReciveDelivery"));
		vtReturn.addElement(createParameterDefinition("smpp_debug", "",
				ParameterType.PARAM_COMBOBOX, vtValue, ""));
		vtReturn.addElement(createParameterDefinition("NumOfDispatcher", "",
				ParameterType.PARAM_TEXTBOX_MASK, "99999", ""));
		vtReturn.addElement(createParameterDefinition("ip-address", "",
				ParameterType.PARAM_TEXTBOX_MAX, "15", ""));
		vtReturn.addElement(createParameterDefinition("port", "",
				ParameterType.PARAM_TEXTBOX_MASK, "99999", ""));
		vtReturn.addElement(createParameterDefinition("system-id", "",
				ParameterType.PARAM_TEXTBOX_MAX, "30", ""));
		vtReturn.addElement(createParameterDefinition("password", "",
				ParameterType.PARAM_PASSWORD, "30", ""));
		vtReturn.addElement(createParameterDefinition("SystemType", "",
				ParameterType.PARAM_TEXTBOX_MAX, "30", ""));
		vtReturn.addElement(createParameterDefinition("addr-ton", "",
				ParameterType.PARAM_TEXTBOX_MASK, "9", ""));
		vtReturn.addElement(createParameterDefinition("addr-npi", "",
				ParameterType.PARAM_TEXTBOX_MASK, "9", ""));
		vtReturn.addElement(createParameterDefinition("address-range", "",
				ParameterType.PARAM_TEXTBOX_MAX, "15", ""));
		vtReturn.addElement(createParameterDefinition("source-ton", "",
				ParameterType.PARAM_TEXTBOX_MASK, "9", ""));
		vtReturn.addElement(createParameterDefinition("source-npi", "",
				ParameterType.PARAM_TEXTBOX_MASK, "9", ""));
		vtReturn.addElement(createParameterDefinition("source-address", "",
				ParameterType.PARAM_TEXTBOX_MAX, "15", ""));
		vtReturn.addElement(createParameterDefinition("destination-ton", "",
				ParameterType.PARAM_TEXTBOX_MASK, "9", ""));
		vtReturn.addElement(createParameterDefinition("destination-npi", "",
				ParameterType.PARAM_TEXTBOX_MASK, "9", ""));
		vtReturn.addElement(createParameterDefinition("bind-mode", "",
				ParameterType.PARAM_TEXTBOX_MAX, "2", ""));
		vtReturn.addElement(createParameterDefinition("receive-timeout", "",
				ParameterType.PARAM_TEXTBOX_MASK, "999", ""));
		vtReturn.addElement(createParameterDefinition("sync-mode", "",
				ParameterType.PARAM_TEXTBOX_MAX, "1", ""));
		vtReturn.addElement(createParameterDefinition("enquire-interval", "",
				ParameterType.PARAM_TEXTBOX_MASK, "999", ""));
		vtReturn.addElement(createParameterDefinition("PrefixWhiteList", "",
				ParameterType.PARAM_TEXTBOX_MAX, "", ""));
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
		systemId = loadMandatory("system-id");
		password = loadMandatory("password");
		mstrSystemType = loadMandatory("SystemType");
		ton = (byte) loadInteger("addr-ton");
		npi = (byte) loadInteger("addr-npi");
		addr = loadMandatory("address-range");
		String strDebugMode = loadYesNo("smpp_debug");
		if (strDebugMode.toUpperCase().equals("N")) {
			debugOption = false;
		} else {
			debugOption = true;
		}

		String strReceiveDv = loadYesNo("ReciveDelivery");
		if (strReceiveDv.toUpperCase().equals("Y")) {
			blReciveDelivery = true;
		}
		enquireInterval = loadInteger("enquire-interval");
		addressRange.setTon(ton);
		addressRange.setNpi(npi);
		try {
			addressRange.setAddressRange(addr);
		} catch (WrongLengthOfStringException e) {
			throw new AppException(
					"The length of address-range parameter is wrong.");
		}
		ton = (byte) loadInteger("source-ton");
		npi = (byte) loadInteger("source-npi");
		addr = loadMandatory("source-address");
		setAddressParameter("source-address", sourceAddress, ton, npi, addr);
		ton = (byte) loadInteger("destination-ton");
		npi = (byte) loadInteger("destination-npi");
		setAddressParameter("source-address", destAddress, ton, npi, addr);
		bindMode = loadMandatory("bind-mode");
		if (!bindMode.equalsIgnoreCase("t") && !bindMode.equalsIgnoreCase("r")
				&& !bindMode.equalsIgnoreCase("tr")) {
			throw new AppException("The bind-mode parameter is wrong. ");
		}
		if (receiveTimeout == Data.RECEIVE_BLOCKING) {
			rcvTimeout = -1;
		} else {
			rcvTimeout = ((int) receiveTimeout) / 1000;
		}
		rcvTimeout = loadInteger("receive-timeout");
		if (rcvTimeout == -1) {
			receiveTimeout = Data.RECEIVE_BLOCKING;
		} else {
			receiveTimeout = rcvTimeout * 1000;
		}
		syncMode = loadMandatory("sync-mode");
		if (syncMode.equalsIgnoreCase("s")) {
			asynchronous = false;
		} else if (syncMode.equalsIgnoreCase("a")) {
			asynchronous = true;
		} else {
			asynchronous = false;
		}
		String[] arrWhiteList = loadMandatory("PrefixWhiteList").split(",");
		mlstWhiteList = new Vector<String>();
		for (int i = 0; i < arrWhiteList.length; i++) {
			mlstWhiteList.add(arrWhiteList[i]);
		}
		mprtDeliverSM = new Properties();
		mprtDeliverSM.put("DataType", mstrThreadID);

		super.fillParameter();
	}

	/**
	 * Sets attributes of <code>Address</code> to the provided values.
	 * 
	 * @param descr
	 *            String
	 * @param address
	 *            Address
	 * @param ton
	 *            byte
	 * @param npi
	 *            byte
	 * @param addr
	 *            String
	 */
	private void setAddressParameter(String descr, Address address, byte ton,
			byte npi, String addr) {
		address.setTon(ton);
		address.setNpi(npi);
		try {
			address.setAddress(addr);
		} catch (WrongLengthOfStringException e) {
			logMonitor("The length of " + descr + " parameter is wrong.");
		}
	}

	public void initTCPIPMonitor() {
		if (debugOption) {
			debug = new FileDebug(dbgDir, "sim.dbg");
			event = new FileEvent(dbgDir, "sim.evt");
			SmppObject.setDebug(debug);
			SmppObject.setEvent(event);
			debug.activate();
			event.activate();
			debug.deactivate(DSIMD2);
		} else {
			debug = new DefaultDebug();
			event = new DefaultEvent();
			SmppObject.setDebug(debug);
			SmppObject.setEvent(event);
		}
	}

	public Properties getProperties() {
		return mprtDeliverSM;
	}

	private boolean checkWhiteList(String strMsisdn) {
		for (int i = 0; i < mlstWhiteList.size(); i++) {
			if (strMsisdn.startsWith(mlstWhiteList.get(i))) {
				return true;
			}
		}
		return false;
	}

	public String validateISDN(String isdn) {
		String strisdn = isdn;
		if (strisdn.startsWith("85")) {
			strisdn = strisdn.substring(2);
		}
		if (strisdn.startsWith("0")) {
			strisdn = strisdn.substring(1);
		}
		return strisdn;
	}

	private boolean retryWhenSubmitFail(SubmitMessage requestMsg, Exception e) {
		try {
			int iSequence = (int) requestMsg.getRequestID();
			Object ojRecord = mapRequestAckCache.get(iSequence);
			if (ojRecord != null && ojRecord instanceof LogRecord) {
				LogRecord record = (LogRecord) ojRecord;
				String strRetry = StringUtil.nvl(
						record.getAttribute("NumRetry"), "0");
				int iRetry = 0;
				try {
					iRetry = Integer.parseInt(strRetry);
				} catch (Exception esub) {
					iRetry = 0;
				}
				if (iRetry < GlobalParameter.iNumRetryTime)// do retry, other
															// send back to
															// customer
				{
					iRetry++;
					record.setAttribute("NumRetry", iRetry);
					Thread.sleep(GlobalParameter.iNumMiliSeconDelay);
					record.setAttribute("DataType", this.getThreadID());
					mqlSMSCQueueManager.attach(record);
					logMonitor("Error occured: Fail when submit messsage to smsc: "
							+ e.getMessage()
							+ "\n\tRecovery action: System will retry to send again (after "
							+ GlobalParameter.iNumMiliSeconDelay
							+ " ms) for request of "
							+ record.getIsdn()
							+ "("
							+ record.getResponseMsg()
							+ ")\n\tRetried time: "
							+ String.valueOf(iRetry)
							+ ", Total retried time: "
							+ String.valueOf(GlobalParameter.iNumRetryTime)
							+ ", Queue size: "
							+ mqlSMSCQueueManager.getQueueSize());

					synchronized (mapRequestAckCache) {
						mapRequestAckCache.remove(iSequence);
					}
					return true;
				} // end if (iRetry < GlobalParameter.iNumRetryTime)
			} // end if (ojRecord != null && ojRecord instanceof LogRecord) {
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
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

	public void processResponse(Object object) throws Exception {
		PDU pdu = null;
		if (object instanceof PDU) {
			pdu = (PDU) object;
			int iSequence = pdu.getSequenceNumber();
			SubmitSMResp ssm = (SubmitSMResp) pdu;
			String s = ssm.getMessageId();
			long commandid = -1;
			try {
				commandid = Long.parseLong(s);
			} catch (Exception e) {
				// TODO: handle exception
				commandid = System.currentTimeMillis();
			}
			Object ojRecord = mapRequestAckCache.get(String.valueOf(iSequence));
			if (ojRecord != null && ojRecord instanceof LogRecord) {
				LogRecord output = (LogRecord) ojRecord;
				synchronized (mapRequestAckCache) {
					mapRequestAckCache.remove(iSequence);
				}
				if (pdu.isOk()) {
					output.setStatus(SendSMSLog.SEND_TO_SMSC_SUCCESS);
				} else {
					output.setStatus(SendSMSLog.SEND_SMSC_FAIL);
				}
				output.setActionType(LogRecord.ACTION_UPDATE_TYPE);
				output.setCommandID(String.valueOf(commandid));
				getThreadManager().attachLogRecord(output);
			} else {
				return;
			}
		} else {
			throw new Exception("Object not instance of PDU");
		}
	}

	public static void main(String[] agm) {

	}
}
