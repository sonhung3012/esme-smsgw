package com.fis.esme.core.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import net.sf.ehcache.Ehcache;

import com.fis.esme.core.bean.SmsBean;
import com.fis.esme.core.bean.SmsBeanFactory;
import com.fis.esme.core.bean.cacheloader.CacheBeanFactory;
import com.fis.esme.core.entity.CPObject;
import com.fis.esme.core.entity.SMSCMTRountingObject;
import com.fis.esme.core.entity.SMSMOObject;
import com.fis.esme.core.entity.SMSMORoutingInfo;
import com.fis.esme.core.entity.SMSMORoutingObject;
import com.fis.esme.core.entity.SMSMTObject;
import com.fis.esme.core.entity.ServicesInternalObject;
import com.fis.esme.core.entity.SubscriberObject;
import com.fis.esme.core.log.LogRecord;
import com.fis.esme.core.smpp.util.EhCacheManager;
import com.fis.esme.core.smsc.util.QueueMOManager;
import com.fis.esme.core.smsc.util.SMSCQueue;
import com.fis.esme.core.smsc.util.QueueMTManager;
import com.fis.esme.core.smsc.util.SortableVector;
import com.fis.esme.core.util.LinkQueue;
import com.fis.esme.core.util.LinkQueueString;
import com.fis.licence.util.lib.LicenceException;
import com.fss.ddtp.DDTP;
import com.fss.sql.Database;
import com.fss.thread.ManageableThread;
import com.fss.thread.ProcessorListener;
import com.fss.thread.ThreadManager;
import com.fss.util.StringUtil;

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
public class ThreadManagerEx extends ThreadManager {
	public static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(ThreadManagerEx.class);
	private long mlSynchronizeInterval = 300000;
	private long mlNextConfigurationSynchronizeTime = 0;
	private String mstrAppName;
	private SMSCQueue mqlSMSCQueueLevel = null;
	private Map mapRequestAckCache = new HashMap();
	private Map mhmDeliveryACK = new HashMap();
	public SortableVector mPrefix = new SortableVector();
	public static final String SMSC_DATA_TYPE = "DataType";
	public static final String CP_DATA_TYPE = "DataType";
	public static final int mintMaxQueueSize = 50000;
	private static Vector vtMessageResponse = new Vector();
	private static Vector vtSystemParameterMessage = new Vector();
	private static String defaulMeg = "System error, pl. try again later !";
	private static String mstrDatabaseMode = "ORACLE";
	private static boolean blCheckSubscriber = false;
	private static boolean blValidateServiceMT = false;

	private Properties mprtResponse = new Properties();
	private Vector mvtLoggingStorageList = new Vector();
	private LinkQueue<LogRecord> mlqPrimaryLoggingStorage = new LinkQueue<LogRecord>();
	private QueueMTManager qQueueMTManager;
	private QueueMOManager qQueueMOManager = null;
	private LinkQueue<ServicesInternalObject> lqMessageQueueInter = new LinkQueue<ServicesInternalObject>();
	private LinkQueue<SMSMTObject> lqMessageQueue = new LinkQueue<SMSMTObject>();
	private LinkQueue<SMSMOObject> lqMessageMOQueue = new LinkQueue<SMSMOObject>();
	private List<SMSCMTRountingObject> lSMSCMTRountingObjects = new ArrayList<SMSCMTRountingObject>();
	private List<SMSMORoutingInfo> lSMSMORoutingInfo = new ArrayList<SMSMORoutingInfo>();
	// cache store
	private Vector vtBlacklistCache = new Vector();
	private Vector vtBlacklistServiceCache = new Vector();
	private Hashtable<String, CPObject> htbCPCache = new Hashtable<String, CPObject>();
	// defaul value
	private boolean mbLicenceLocked = false;
	private EhCacheManager ehCacheManager = new EhCacheManager();
	private Ehcache submitCache = null;
	private Ehcache deliveryCache = null;

	private LinkQueueString mSMSQueue = new LinkQueueString();
    public void attachQueue(String message) {
        mSMSQueue.enqueue(message);
    }

    public String dettach() {
        return (String) mSMSQueue.dequeue();
    }

    public int getQueuezise() {
        return mSMSQueue.size();
    }
    
	// //////////////////////////////////////////////////////
	// Constructor
	// //////////////////////////////////////////////////////
	public ThreadManagerEx(int iPort, ProcessorListener lsn) throws Exception {
		// --starting check licence
		
		super(iPort, lsn);
		mSMSQueue=new LinkQueueString();
		try {
			// set private key
//			LicenceManager.setProjectKey();
//
//			// load and check licence file
//			LicenceManager.loadLicence();
//
//			// check to licence 's server
//			LicenceManager.recheckToServer();

			// set licence locked = false
			mbLicenceLocked = false;
			
			init();
			
	
//			
//		} catch (LicenceException ex) {
//			System.out.println(ex.getContext());
//			// //print mac address
//			throw new Exception(ex.getMessage());
		} catch (Exception ex) {
			// print mac address
			throw new Exception(ex.getMessage());
		}

	}

	public void init() {
		qQueueMTManager = new QueueMTManager(new String[] { SMSC_DATA_TYPE });
		qQueueMTManager.setMaxQueueSize(mintMaxQueueSize);
		qQueueMOManager = new QueueMOManager(new String[] { CP_DATA_TYPE });
		mvtLoggingStorageList.addElement(mlqPrimaryLoggingStorage);
		submitCache = getCacheManager().getCache(
				EhCacheManager.mstrSubmitCacheName);
		deliveryCache = getCacheManager().getCache(
				EhCacheManager.mstrDeliveryCacheName);
	}

	public EhCacheManager getCacheManager() {
		return ehCacheManager;
	}

	public Ehcache getSubmitCache() {
		return submitCache;
	}

	public Ehcache getDeliveryCache() {
		return deliveryCache;
	}

	public LinkQueue<SMSMOObject> getLqMessageMOQueue() {
		return lqMessageMOQueue;
	}

	public void setLqMessageMOQueue(LinkQueue<SMSMOObject> lqMessageMOQueue) {
		this.lqMessageMOQueue = lqMessageMOQueue;
	}

	public LinkQueue<ServicesInternalObject> getMessageQueueInter() {
		return lqMessageQueueInter;
	}

	public void setMessageQueueInter(
			LinkQueue<ServicesInternalObject> lqMessageMOQueue) {
		this.lqMessageQueueInter = lqMessageMOQueue;
	}

	public static boolean getValidateServiceMT() {
		return blValidateServiceMT;
	}

	public void setValidateServiceMT(boolean bl) {
		blValidateServiceMT = bl;
	}

	public static boolean getCheckSubscriber() {
		return blCheckSubscriber;
	}

	public void setCheckSubscriber(boolean bl) {
		blCheckSubscriber = bl;
	}

	/**
	 * 
	 * @param msgCode
	 * @return
	 */
	public static String getMessageResponse(String msgCode) {
		return CacheBeanFactory.getSmsBeanFactory().getMessageContent(msgCode,
				vtMessageResponse, defaulMeg);
	}

	public static String getSystemParameterMessage(String msgType,
			String msgCode) {
		return CacheBeanFactory.getSmsBeanFactory().getSystemParameterContent(
				msgType, msgCode, vtSystemParameterMessage, defaulMeg);
	}

	public CPObject getCP(String strCPID) {
		return htbCPCache.get(strCPID);
	}

	/**
	 * 
	 * @param isdn
	 * @param serviceid
	 * @return
	 */
	public boolean checkBlackList(String isdn, String serviceid) {
		try {
			return CacheBeanFactory.getSmsBeanFactory().checkBlackList(isdn,
					serviceid, vtBlacklistCache, vtBlacklistServiceCache);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public List<SMSCMTRountingObject> getSMSMTRouting() {
		return lSMSCMTRountingObjects;
	}

	public List<SMSMORoutingInfo> getSMSMORouting() {
		return lSMSMORoutingInfo;
	}

	public LinkQueue<SMSMTObject> getSMSMTQueue() {
		return lqMessageQueue;
	}

	public QueueMOManager getMOQueue() {
		return qQueueMOManager;
	}

	/**
	 * 
	 * @return
	 */
	public QueueMTManager getQueueMTManager() {
		return qQueueMTManager;
	}

	public Map getDeliveryACKStorage() {
		return mhmDeliveryACK;
	}

	public LinkQueue<LogRecord> getPrimaryLoggingStorage() {
		return mlqPrimaryLoggingStorage;
	}

	public Map getRequestAckCache() {
		return mapRequestAckCache;
	}

	/**
	 * 
	 * @param record
	 *            LogRecord
	 */
	// //////////////////////////////////////////////////////
	public void attachLogRecord(LogRecord record) {
		mlqPrimaryLoggingStorage.enqueueNotify(record);
	}

	public void loadRoutingDataInfo(Connection con) {
		synchronized (lSMSCMTRountingObjects) {
			lSMSCMTRountingObjects = CacheBeanFactory.getSmsBeanFactory()
					.loadSMSMTRouting(con);
		}

		synchronized (lSMSMORoutingInfo) {
			lSMSMORoutingInfo = CacheBeanFactory.getSmsBeanFactory()
					.SMSRouting(con);
		}

		synchronized (vtBlacklistCache) {
			vtBlacklistCache = CacheBeanFactory.getSmsBeanFactory()
					.loadBlackListIsdn(con);
		}

		synchronized (vtBlacklistServiceCache) {
			vtBlacklistServiceCache = CacheBeanFactory.getSmsBeanFactory()
					.loadBlackListService(con);
		}

		synchronized (vtMessageResponse) {
			vtMessageResponse = CacheBeanFactory.getSmsBeanFactory()
					.loadMessageCache(con);
		}

		synchronized (vtSystemParameterMessage) {
			vtSystemParameterMessage = CacheBeanFactory.getSmsBeanFactory()
					.loadSystemParameterCache(con);
		}

		synchronized (htbCPCache) {
			htbCPCache = CacheBeanFactory.getSmsBeanFactory().loadCPCache(con);
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param record
	 *            LogRecord
	 * @param iLevel
	 *            int
	 */
	// //////////////////////////////////////////////////////
	public void attachLogRecord(LogRecord record, int iLevel) {
		while (iLevel >= mvtLoggingStorageList.size()) {
			mvtLoggingStorageList.addElement(new LinkQueue<LogRecord>(10000));
		}
		LinkQueue<LogRecord> lqStorage = (LinkQueue<LogRecord>) mvtLoggingStorageList
				.elementAt(iLevel);
		lqStorage.enqueueNotify(record);
	}

	// //////////////////////////////////////////////////////
	public LogRecord detachLogRecord(int iLevel) {
		LogRecord record = null;
		try {
			while (iLevel >= mvtLoggingStorageList.size()) {
				mvtLoggingStorageList
						.addElement(new LinkQueue<LogRecord>(10000));
			}
			LinkQueue<LogRecord> lqStorage = (LinkQueue<LogRecord>) mvtLoggingStorageList
					.elementAt(iLevel);
			record = lqStorage.dequeueWait(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return record;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return LogRecord
	 */
	// //////////////////////////////////////////////////////
	public LogRecord detachLogRecord() {
		try {
			return mlqPrimaryLoggingStorage.dequeueWait(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getLogingQueueSize() {
		String strLog = "Primary Logging Queue Size: "
				+ mlqPrimaryLoggingStorage.getSize() + "; ";
		for (int level = 1; level < mvtLoggingStorageList.size(); level++) {
			LinkQueue<LogRecord> lqStorage = (LinkQueue<LogRecord>) mvtLoggingStorageList
					.elementAt(level);
			strLog += "Logging Level " + level + " Queue Size: "
					+ lqStorage.getSize() + "; ";
		}
		return strLog;
	}

	// //////////////////////////////////////////////////////
	/**
	 * Manage custom thread
	 * 
	 * @throws Exception
	 */
	// //////////////////////////////////////////////////////
	public void manageCustomThread() throws Exception {
		Connection cn = null;
		Statement stmtThread = null;
		Statement stmt = null;
//		if (mbLicenceLocked) {
//			return;
//		}
//		if (!LicenceManager.checkDayToDown()) {
//			System.out.println("Your licence has expired. Service was closed!");
//			// close server
//			try {
//				serverSocket.close();
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}

		try {
			// Open connection
			logger.debug("GatewayManager.manageCustomThread getAppConnection");
			cn = getConnection();
			// Synchronize configuration && authentication data
			syncData(cn, false);
			// Unload all disabled gateway & dispatcher
			stmt = cn.createStatement();
			String strSQL = " SELECT SMSC_ID,SMSC.NAME,SMSC.CLASS_NAME,SMSC.DEFAUL_SHORT_CODE FROM ESME_SMSC SMSC "
					+ " WHERE STARTUP_TYPE='0' ";
			ResultSet rs = stmt.executeQuery(strSQL);
			while (rs.next()) {
				// Unload thread
				String strThreadID = rs.getString(1);
				unloadThread(strThreadID, ManageableThread.DB_MANAGEMENT);
			}
			// Release
			rs.close();
			// Load dispatcher & gateway
			strSQL = " SELECT SMSC_ID,SMSC.NAME,SMSC.CLASS_NAME,STARTUP_TYPE,SMSC.DEFAUL_SHORT_CODE FROM ESME_SMSC SMSC "
					+ " WHERE SMSC.STATUS in (1,2) AND STARTUP_TYPE > 0 ";
			stmtThread = cn.createStatement();
			ResultSet rsThread = stmtThread.executeQuery(strSQL);
			while (rsThread.next()) {
				// get thread ID
				String strThreadID = StringUtil.nvl(rsThread.getString(1), "");
				String strThreadName = StringUtil
						.nvl(rsThread.getString(2), "");
				String strClassName = StringUtil.nvl(rsThread.getString(3), "");
				String strStartupType = StringUtil.nvl(rsThread.getString(4),
						"");
				// Find the monitor if it is already loaded
				ManageableThread thread = getThread(strThreadID);
				if (thread != null
						&& thread.miManageMethod == ManageableThread.DB_MANAGEMENT) { // loaded
					if (!strStartupType.equals(thread.getStartupType())) {
						thread.setStartupType(strStartupType);
					}
					if (!strClassName.equals(thread.getClassName())) {
						stopThread(thread);
						DDTP request = new DDTP();
						request.setString("ThreadID", strThreadID);
						if (isConnected()) {
							sendRequestToAll(request, "unloadThread",
									"MonitorProcessor");
						}
						mvtThread.removeElement(thread);
						thread = null;
					} else if (!strThreadName.equals(thread.getThreadName())) {
						DDTP request = new DDTP();
						thread.setThreadName(strThreadName);
						request.setString("ThreadID", strThreadID);
						request.setString("ThreadName", strThreadName);
						if (isConnected()) {
							sendRequestToAll(request, "renameThread",
									"MonitorProcessor");
						}
					}
				}
				// Haven't loaded
				if (thread == null) {
					thread = loadThread(strThreadID, strThreadName,
							strClassName, strStartupType,
							ManageableThread.DB_MANAGEMENT);
				}
			}
			// Release
			rsThread.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeObject(stmt);
			Database.closeObject(stmtThread);
			Database.closeObject(cn);
		}
	}

	// //////////////////////////////////////////////////////
	public synchronized void syncData(boolean bForceSyncData) {
		Connection cn = null;
		try {
			logger.debug("ESMEManager.syncData getConnection");
			cn = getConnection();
			// Synchronize configuration && authentication data
			syncData(cn, bForceSyncData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeObject(cn);
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param cn
	 *            Connection
	 * @param bForceSyncData
	 *            boolean
	 * @throws Exception
	 */
	// //////////////////////////////////////////////////////
	public synchronized void syncData(Connection cn, boolean bForceSyncData)
			throws Exception {
		// Synchronize configuration data
		boolean bExpired = false;
		if (bForceSyncData) {
			bExpired = true;
		} else {
			long lCurrentTime = System.currentTimeMillis();
			if (mlNextConfigurationSynchronizeTime < lCurrentTime) {
				bExpired = true;
			}
		}
		if (bExpired) {
			// call syn function
			loadRoutingDataInfo(cn);
			mlNextConfigurationSynchronizeTime = System.currentTimeMillis()
					+ mlSynchronizeInterval;
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public String getAppName() {
		return mstrAppName;
	}

	public void setDatabaseMode(String databaseMode) {
		mstrDatabaseMode = databaseMode;
	}

	public static String getDatabaseMode() {
		return mstrDatabaseMode;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param strAppName
	 *            String
	 */
	// //////////////////////////////////////////////////////
	public void setAppName(String strAppName) {
		mstrAppName = strAppName;
	}

}
