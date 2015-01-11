package com.fis.esme.core.bean.cacheloader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.fis.esme.core.bean.AbstractBean;
import com.fis.esme.core.entity.CPObject;
import com.fis.esme.core.entity.SMSCMTRountingObject;
import com.fis.esme.core.entity.SMSMORoutingInfo;
import com.fis.esme.core.entity.SMSMORoutingObject;
import com.fss.sql.Database;
import com.fss.util.StringUtil;

public abstract class CacheBean extends AbstractBean {

	public abstract boolean checkBlackList(String isdn, String serviceid,
			Vector vtBlackList, Vector vtBlackServices) ;

	public abstract String getMessageContent(String MessageCode,
			Vector vtMessageCache, String defaulMeg) ;

	public abstract String getSystemParameterContent(String MessageType,
			String MessageCode, Vector vtMessageCache, String defaulMeg) ;

	/**
	 * 
	 * @param con
	 * @return
	 */
	public abstract Vector loadBlackListService(Connection con) ;

	/**
	 * 
	 * @param con
	 * @return
	 */
	public abstract Vector loadBlackListIsdn(Connection con) ;

	/**
	 * 
	 * @param con
	 * @return
	 */
	public abstract Hashtable<String, CPObject> loadCPCache(Connection con) ;

	/**
	 * 
	 * @param con
	 * @return
	 */
	public abstract Vector loadSystemParameterCache(Connection con) ;

	/**
	 * 
	 * @param con
	 * @return
	 */
	public abstract Vector loadMessageCache(Connection con) ;

	/**
	 * 
	 * @param con
	 * @return
	 */
	public abstract List<SMSCMTRountingObject> loadSMSMTRouting(Connection con);
	/**
	 * 
	 * @param con
	 * @return
	 */

	public abstract List<SMSMORoutingInfo> SMSRouting(Connection con) ;

}
