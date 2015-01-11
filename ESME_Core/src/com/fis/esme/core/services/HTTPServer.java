package com.fis.esme.core.services;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.util.Properties;
import java.util.Vector;

import net.sf.ehcache.Ehcache;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.bean.SmsBean;
import com.fis.esme.core.bean.SmsBeanFactory;
import com.fis.esme.core.bean.SmsBeanOracle;
import com.fis.esme.core.entity.SMSCMORoutingUtil;
import com.fis.esme.core.entity.SMSMORoutingObject;
import com.fis.esme.core.entity.SubscriberObject;
import com.fis.esme.core.entity.VariableStatic;
import com.fis.esme.core.exception.LoginException;
import com.fis.esme.core.http.MyHandler;
import com.fis.esme.core.smpp.entity.SequenceObject;
import com.fis.esme.core.smsc.util.QueueMOManager;
import com.fss.sql.Database;
import com.fss.thread.ParameterType;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;
import com.fss.util.StringUtil;
import com.logica.smpp.Data;
import com.logica.smpp.pdu.Request;
import com.sun.net.httpserver.HttpServer;

public class HTTPServer extends ManageableThreadEx {
	private Ehcache submitCache = null;
	private Ehcache deliveryCache = null;
	private boolean displayInfo = true;
	private int port;
	private long checkPeriod = 1000;
	private SmsBean databaseBean = null;
	private String strSubUrl = "";
	private HttpServer server = null;
	private String mstrAlgorithm = "SHA";
	private long lLastUpdateTime = 0;

	@Override
	public void fillParameter() throws AppException {
		port = loadInteger("HTTPPort");
		mstrAlgorithm = loadMandatory("Algorithm");
		super.fillParameter();
		fillLogFile();
	}

	@Override
	public Vector getParameterDefinition() {
		Vector vtReturn = super.getParameterDefinition();
		vtReturn.addElement(createParameterDefinition("HTTPPort", "",
				ParameterType.PARAM_TEXTBOX_MAX, "",
				"SMPP Server port to listen client connect"));
		vtReturn.addElement(createParameterDefinition("Algorithm", "",
				ParameterType.PARAM_TEXTBOX_MAX, "",
				"algorithm use to authentication"));
		return vtReturn;
	}

	@Override
	protected void beforeSession() throws Exception {
		super.beforeSession();
		initServer();
		databaseBean = SmsBeanFactory.getSmsBeanFactory(getThreadManager()
				.getDatabaseMode());
	}

	public void initServer() throws Exception {
		MyHandler handler = new MyHandler();
		strSubUrl = "/submit";
		InetSocketAddress socket = new InetSocketAddress(port);
		server = HttpServer.create(socket, 0);
		server.createContext(strSubUrl, handler);
		server.setExecutor(null); // creates a default executor
		handler.setHttpServer(this);
	}

	public void stopHttpServer() throws Exception {
		this.server.stop(0);
	}

	public Vector getUserInfo(String userid) {
		Connection con = null;
		try {
			con = getThreadManager().getConnection();
			lLastUpdateTime = System.currentTimeMillis();
			return databaseBean.loadPassword(con, userid);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(con);
		}
		return null;

	}

	public String getAlgorithm() {
		return mstrAlgorithm;
	}

	/**
	 * 
	 * @param requestid
	 * @param message
	 * @param cp_id
	 * @param shortcode
	 * @param msisdn
	 * @param commandcode
	 * @param registerDl
	 * @throws Exception
	 */
	public void insertMT(String requestid, String message, String cp_id,
			String shortcode, String msisdn, String commandcode,
			String registerDl, String subid, String groupid) {
		Connection con = null;
		try {
			con = getThreadManager().getConnection();
			databaseBean.insertSMSMT(con, requestid, message, cp_id, shortcode,
					msisdn, commandcode, registerDl, "", "", subid, groupid);
			lLastUpdateTime = System.currentTimeMillis();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			debugMonitor(e.getMessage(), 1);
		} finally {
			Database.closeObject(con);
		}
	}

	public void insertSMSLog(String request_id, String msisdn, String cp_id,
			String sms_content, String error_code, String status,
			String strFileUploadID, String subid, String groupid)
			throws Exception {
		Connection con = null;
		try {
			con = getThreadManager().getConnection();
			String smsLogSeq = Database.getSequenceValue(con, "sms_log_seq");
			databaseBean.insertSMSLog(con, smsLogSeq, request_id, msisdn,
					status, "2", "0", "0", "0", "0", cp_id, "0", "0", "0", "0",
					null, sms_content, "0", "0", error_code, strFileUploadID,
					subid, groupid,0,0,0);
			lLastUpdateTime = System.currentTimeMillis();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(con);
		}
	}

	@Override
	protected void processSession() throws Exception {
		server.start();
		while (miThreadCommand != ThreadConstant.THREAD_STOP) {
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw e;
			}
		}
	}

	public static void sendDeliveryReport(Request request) {

	}

	@Override
	protected void afterSession() throws Exception {
		server.stop(0);
		super.afterSession();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isOpen() {
		return (miThreadCommand != ThreadConstant.THREAD_STOP);
	}

	public boolean validateCPInfo(String shortcode, String commandcode, int cpId) {
		SMSMORoutingObject smsMORoutingObject = SMSCMORoutingUtil
				.getServiceInfo(shortcode, commandcode, getThreadManager()
						.getSMSMORouting());
		if (getThreadManager().getValidateServiceMT()) {
			if (smsMORoutingObject == null) {
				return false;
			} else if (smsMORoutingObject.getCp_id() != cpId) {
				return false;
			}
		}
		return true;
	}

	public SubscriberObject getSubscriberInfo(String isdn) {
		Connection con = null;
		try {
			con = getThreadManager().getConnection();
			if (getThreadManager().getCheckSubscriber()) {
				SubscriberObject subscriberObject = databaseBean
						.getSubscriberInfo(con, isdn);
				return subscriberObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(con);
		}
		return null;
	}

	public int checkIdentity(String strIp, String strCpUser, String strCpPass)
			throws Exception {
		int commandStatus = Data.ESME_ROK;
		int cpId = 0;
		Vector vtUser = getUserInfo(strCpUser);
		if (vtUser != null) {
			String password = vtUser.get(2).toString();
			try {
				strCpPass = StringUtil.encrypt(strCpPass, mstrAlgorithm);
				if (!strCpPass.equals(password)) {
					commandStatus = Data.ESME_RINVPASWD;
					debugMonitor("system id " + strCpUser
							+ " not authenticated. Invalid password.", 4);
					debugMonitor("not authenticated " + strCpUser
							+ " -- invalid password", 4);
				} else {
					cpId = Integer.valueOf(vtUser.get(0).toString());
					debugMonitor("authenticated " + strCpUser, 4);
				}
			} catch (Exception e) {
				e.printStackTrace();
				commandStatus = Data.ESME_RSYSERR;
			}
		} else {
			commandStatus = Data.ESME_RINVSYSID;
			debugMonitor("system id " + strCpUser
					+ " not authenticated -- not found", 4);
			debugMonitor("not authenticated " + strCpUser
					+ " -- user not found", 4);
		}
		if (commandStatus != Data.ESME_ROK) {
			throw new LoginException(commandStatus, "Login Fail, code :"
					+ commandStatus);
		}
		return cpId;
	}
	public static void main(String [] agm)
	{
		
	}
}