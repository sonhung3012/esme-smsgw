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
package com.fis.esme.core.smpp;

import java.util.Vector;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.fis.esme.core.bean.SmsBean;
import com.fis.esme.core.bean.SmsBeanOracle;
import com.fis.esme.core.services.SMPPServer;
import com.fis.esme.core.smpp.entity.SequenceObject;
import com.fis.esme.core.smpp.entity.TransactionDataObject;
import com.fss.util.StringUtil;

import org.smpp.*;
import org.smpp.debug.Debug;
import org.smpp.debug.Event;
import org.smpp.debug.FileLog;
import org.smpp.pdu.*;
import org.smpp.util.NotEnoughDataInByteBufferException;
import org.smpp.util.TerminatingZeroNotFoundException;

/**
 * Class <code>SimulatorPDUProcessor</code> gets the <code>Request</code> from
 * the client and creates the proper <code>Response</code> and sends it. At the
 * beginning it authenticates the client using information in the bind request
 * and list of users provided during construction of the processor. It also
 * stores messages sent from client and allows cancellation and replacement of
 * the messages.
 * 
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version 1.0, 20 Sep 2001
 * @see PDUProcessor
 * @see ESMEPDUProcessorFactory
 * @see SMSCSession
 * @see ShortMessageStore
 * @see Table
 */

/*
 * 20-09-01 ticp@logica.com added reference to the DeliveryInfoSender to support
 * automatic sending of delivery info PDUs
 */

public class ESMEPDUProcessor extends PDUProcessor {
	/**
	 * The session this processor uses for sending of PDUs.
	 */
	private SMSCSession session = null;

	/**
	 * The container for received messages.
	 */
	private Ehcache submitCache = null;

	/**
	 * The thread which sends delivery information for messages which require
	 * delivery information.
	 */
	private Ehcache deliveryCache = null;

	/**
	 * Indicates if the bound has passed.
	 */
	private boolean bound = false;

	/**
	 * The system id of the bounded ESME.
	 */
	private String systemId = null;

	private int cpId;

	/**
	 * If the information about processing has to be printed to the standard
	 * output.
	 */
	private boolean displayInfo = false;

	/**
	 * The message id assigned by simulator to submitted messages.
	 */
	private static int intMessageId = 2000;

	/**
	 * System id of this simulator sent to the ESME in bind response.
	 */
	private static final String SYSTEM_ID = "ESME";

	/**
	 * The name of attribute which contains the system id of ESME.
	 */
	private static final String SYSTEM_ID_ATTR = "name";

	/**
	 * The name of attribute which conatins password of ESME.
	 */
	private static final String PASSWORD_ATTR = "password";

	private Debug debug = SmppObject.getDebug();
	private Event event = SmppObject.getEvent();

	private SMPPServer server;
	private SequenceObject serverSequence;

	/**
	 * Constructs the PDU processor with given session, message store for
	 * storing of the messages and a table of users for authentication.
	 * 
	 * @param session
	 *            the sessin this PDU processor works for
	 * @param messageStore
	 *            the store for messages received from the client
	 * @param users
	 *            the list of users used for authenticating of the client
	 */
	public ESMEPDUProcessor(SMPPServer server, SMSCSession session,
			Ehcache submitCache) {
		this.server = server;
		this.session = session;
		this.submitCache = submitCache;
	}

	/**
	 * Depending on the <code>commandId</code> of the <code>request</code>
	 * creates the proper response. The first request must be a
	 * <code>BindRequest</code> with the correct parameters.
	 * 
	 * @param request
	 *            the request from client
	 * @throws Exception 
	 */
	public void clientRequest(Request request){
		debug.write("SimulatorPDUProcessor.clientRequest() "
				+ request.debugString());
		Response response;
		int commandStatus;
		int commandId = request.getCommandId();
		session.updateLastRequestTime();
		try {
			display("client request: " + request.debugString());
			if (!bound) { // the first PDU must be bound request
				if (commandId == Data.BIND_TRANSMITTER
						|| commandId == Data.BIND_RECEIVER
						|| commandId == Data.BIND_TRANSCEIVER) {
					commandStatus = checkIdentity((BindRequest) request);

					if (commandStatus == 0) { // authenticated
						// firstly generate proper bind response
						BindResponse bindResponse = (BindResponse) request
								.getResponse();
						bindResponse.setSystemId(SYSTEM_ID);

						// and send it to the client via serverResponse
						serverResponse(bindResponse);
						// success => bound
						bound = true;
					} else { // system id not authenticated
						// get the response
						response = request.getResponse();
						// set it the error command status
						response.setCommandStatus(commandStatus);
						// and send it to the client via serverResponse
						serverResponse(response);
						// bind failed, stopping the session
						session.stop();
					}
				} else {
					// the request isn't a bound req and this is wrong: if not
					// bound, then the server expects bound PDU
					if (request.canResponse()) {
						// get the response
						response = request.getResponse();
						response.setCommandStatus(Data.ESME_RINVBNDSTS);
						// and send it to the client via serverResponse
						serverResponse(response);
					} else {
						// cannot respond to a request which doesn't have
						// a response :-(
					}
					// bind failed, stopping the session
					session.stop();
				}
			} else { // already bound, can receive other PDUs
				if (request.canResponse()) {
					response = request.getResponse();
					switch (commandId) { // for selected PDUs do extra steps
					case Data.SUBMIT_SM:
						SubmitSMResp submitResponse = (SubmitSMResp) response;
						String strCommandCode = null;
						String strMOSequenceNumber = null;
						submitResponse.setMessageId(assignMessageId());
						try {
							String strOptData = ((SubmitSM) request)
									.getCallbackNumAtag().removeCString();
							if (strOptData != null && !strOptData.equals("")) {
								strCommandCode = getOptionalValue(strOptData,
										"command", " ", "=");
								if (strCommandCode != null) {
									strMOSequenceNumber = getOptionalValue(
											strOptData, "mo_seq", " ", "=");
									response.setCommandStatus(Data.ESME_ROK);
								} else {
									response.setCommandStatus(Data.ESME_RMISSINGOPTPARAM);
								}
							} else {
								response.setCommandStatus(Data.ESME_RMISSINGOPTPARAM);
							}
						} catch (ValueNotSetException e1) {
							e1.printStackTrace();
							response.setCommandStatus(Data.ESME_RMISSINGOPTPARAM);
						} catch (NotEnoughDataInByteBufferException e1) {
							e1.printStackTrace();
							response.setCommandStatus(Data.ESME_RMISSINGOPTPARAM);
						} catch (TerminatingZeroNotFoundException e1) {
							e1.printStackTrace();
							response.setCommandStatus(Data.ESME_RMISSINGOPTPARAM);
						}

						server.debugMonitor(
								"Submit info - Cpid : "
										+ cpId
										+ ",ShortMessage : "
										+ ((SubmitSM) request)
												.getShortMessage()
										+ ", CommandCode : "
										+ strCommandCode
										+ ", isdn :"
										+ ((SubmitSM) request).getDestAddr()
												.getAddress(), 2);
						if (response.getCommandStatus() == Data.ESME_ROK) {
							int errorcode = validateSubmitMsg(
									((SubmitSM) request).getSourceAddr()
											.getAddress(), cpId,
									strCommandCode,
									((SubmitSM) request).getShortMessage(),
									((SubmitSM) request).getDestAddr()
											.getAddress());
							if (errorcode > 0) {
								response.setCommandStatus(errorcode);
								try {
									server.insertSMSLog(String.valueOf(request
											.getSequenceNumber()),
											((SubmitSM) request).getDestAddr()
													.getAddress(), String
													.valueOf(cpId),
											((SubmitSM) request)
													.getShortMessage(), String
													.valueOf(errorcode), "6",
											"","-1","-1");
								} catch (Exception e) {
									e.printStackTrace();
									server.debugMonitor(e.getMessage(), 1);
								}
								break;
							}
						}

						byte registeredDelivery = (byte) (((SubmitSM) request)
								.getRegisteredDelivery() & Data.SM_SMSC_RECEIPT_MASK);
						if (registeredDelivery == Data.SM_SMSC_RECEIPT_REQUESTED) {
							TransactionDataObject transObj = new TransactionDataObject();
							transObj.setESMETransID(submitResponse
									.getMessageId());
							transObj.setCPID(String.valueOf(cpId));
							submitCache.put(new Element(transObj
									.getESMETransID(), transObj));
						}

						String strMsisdn = parseCalling(((SubmitSM) request)
								.getDestAddr().getAddress());
						
						server.insertMT(String.valueOf(request
								.getSequenceNumber()), ((SubmitSM) request)
								.getShortMessage(), String.valueOf(cpId),
								((SubmitSM) request).getSourceAddr()
										.getAddress(), strMsisdn,
								strCommandCode, String
										.valueOf(registeredDelivery),
								submitResponse.getMessageId(),
								strMOSequenceNumber,"-1","-1");
						break;
					case Data.UNBIND:
						// do nothing, just respond and after sending
						// the response stop the session
						break;
					case Data.ENQUIRE_LINK:
						break;
					default:
						response.setCommandStatus(Data.ESME_RINVCMDID);
						// and send it to the client via serverResponse
					}
					// send the prepared response
					serverResponse(response);
					if (commandId == Data.UNBIND) {
						// unbind causes stopping of the session
						session.stop();
					}
				} else {
					// can't respond => nothing to do :-)
				}
			}
		} catch (Exception e) {
			event.write(e, "");
		}
	}

	/**
	 * 
	 * @param strCalling
	 * @return
	 */
	private String parseCalling(String strCalling) {
		if (strCalling.startsWith("95")) {
			strCalling = strCalling.substring(2);
		}
		if (strCalling.startsWith("0")) {
			strCalling = strCalling.substring(1);
		}
		return strCalling;
	}

	/**
	 * Processes the response received from the client.
	 * 
	 * @param response
	 *            the response from client
	 */
	public void clientResponse(Response response) {
		debug.write("SimulatorPDUProcessor.clientResponse() "
				+ response.debugString());
		display("client response: " + response.debugString());
	}

	/**
	 * Sends a request to a client. For example, it can be used to send delivery
	 * info to the client.
	 * 
	 * @param request
	 *            the request to be sent to the client
	 */
	public void serverRequest(Request request) {
		debug.write("SimulatorPDUProcessor.serverRequest() "
				+ request.debugString());
		display("server request: " + request.debugString());
		session.send(request);
	}

	/**
	 * Send the response created by <code>clientRequest</code> to the client.
	 * 
	 * @param response
	 *            the response to send to client
	 */
	public void serverResponse(Response response) {
		debug.write("SimulatorPDUProcessor.serverResponse() "
				+ response.debugString());
		display("server response: " + response.debugString());
		session.send(response);
	}

	/**
	 * Checks if the bind request contains valid system id and password. For
	 * this uses the table of users provided in the constructor of the
	 * <code>SimulatorPDUProcessor</code>. If the authentication fails, i.e. if
	 * either the user isn't found or the password is incorrect, the function
	 * returns proper status code.
	 * 
	 * @param request
	 *            the bind request as received from the client
	 * @return status code of the authentication; ESME_ROK if authentication
	 *         passed
	 * @throws Exception 
	 */
	private int checkIdentity(BindRequest request) throws Exception {
		int commandStatus = Data.ESME_ROK;
		Vector vtUser = server.getUserInfo(request.getSystemId());
		if (vtUser != null) {
			String password = vtUser.get(2).toString();
			try {
				String strCPPassword = StringUtil.encrypt(
						StringUtil.nvl(request.getPassword(), ""),
						server.getAlgorithm());
				if (!strCPPassword.equals(password)) {
					commandStatus = Data.ESME_RINVPASWD;
					debug.write("system id " + request.getSystemId()
							+ " not authenticated. Invalid password.");
					server.debugMonitor(
							"not authenticated " + request.getSystemId()
									+ " -- invalid password", 4);
				} else {
					cpId = Integer.valueOf(vtUser.get(0).toString());
					systemId = request.getSystemId();
					debug.write("system id " + systemId + " authenticated");
					server.debugMonitor("authenticated " + systemId, 4);
				}
			} catch (Exception e) {
				e.printStackTrace();
				commandStatus = Data.ESME_RSYSERR;
			}
		} else {
			commandStatus = Data.ESME_RINVSYSID;
			debug.write("system id " + request.getSystemId()
					+ " not authenticated -- not found");
			server.debugMonitor("not authenticated " + request.getSystemId()
					+ " -- user not found", 4);
		}
		return commandStatus;
	}

	/**
	 * Creates a unique message_id for each sms sent by a client to the smsc.
	 * 
	 * @return unique message id
	 */
	private String assignMessageId() {
		String messageId = String.valueOf(serverSequence.nextValue());
		return messageId;
	}

	/**
	 * Returns the session this PDU processor works for.
	 * 
	 * @return the session of this PDU processor
	 */
	public SMSCSession getSession() {
		return session;
	}

	/**
	 * Returns the system id of the client for whose is this PDU processor
	 * processing PDUs.
	 * 
	 * @return system id of client
	 */
	public String getSystemId() {
		return systemId;
	}

	public long getCPId() {
		return cpId;
	}

	/**
	 * Sets if the info about processing has to be printed on the standard
	 * output.
	 */
	public void setDisplayInfo(boolean on) {
		displayInfo = on;
	}

	/**
	 * Returns status of printing of processing info on the standard output.
	 */
	public boolean getDisplayInfo() {
		return displayInfo;
	}

	/**
	 * Sets the delivery info sender object which is used to generate and send
	 * delivery pdus for messages which require the delivery info as the outcome
	 * of their sending.
	 */
	public void setDeliveryCache(Ehcache deliveryCache) {
		this.deliveryCache = deliveryCache;
	}

	public void setServerSequence(SequenceObject serverSequence) {
		this.serverSequence = serverSequence;
	}

	private void display(String info) {
		if (getDisplayInfo()) {
			String sysId = getSystemId();
			if (sysId == null) {
				sysId = "";
			}
			System.out.println(FileLog.getLineTimeStamp() + " [" + sysId + "] "
					+ info);
		}
	}

	private String getOptionalValue(String strData, String strTag,
			String strSplitTag, String strSplitValue) {
		String[] strSplitStrings = strData.split(strSplitTag);
		for (int i = 0; i < strSplitStrings.length; i++) {
			String strTemp = strSplitStrings[i];
			String[] strTempSplitStrings = strTemp.split(strSplitValue);
			if (strTag.equalsIgnoreCase(strTempSplitStrings[0])) {
				return strTempSplitStrings[1];
			}
		}
		return null;
	}

	private int validateSubmitMsg(String shortcode, int cpId,
			String commandcode, String content, String isdn) {
		if (content == null || content.trim().equalsIgnoreCase("")) {
			return Data.ESME_RINVMSGLEN;
		}

		if (isdn == null || isdn.trim().equalsIgnoreCase("")) {
			return Data.ESME_RINVADR;
		}

		if (shortcode == null || shortcode.trim().equalsIgnoreCase("")) {
			return Data.ESME_RINVPARAM;
		}

		if (!server.validateCPInfo(shortcode, commandcode, cpId)) {
			return Data.ESME_RINVPERMSG;
		}
		return Data.ESME_ROK;
	}
}
