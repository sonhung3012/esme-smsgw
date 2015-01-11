package com.fis.esme.core.bean.cacheloader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.fis.esme.core.entity.CPObject;
import com.fis.esme.core.entity.SMSCMTRountingObject;
import com.fis.esme.core.entity.SMSMORoutingInfo;
import com.fis.esme.core.entity.SMSMORoutingObject;
import com.fss.sql.Database;
import com.fss.util.StringUtil;

public class CacheBeanOracle extends CacheBeanOracleDAO {

	/**
	 * 
	 * @param isdn
	 * @param serviceid
	 * @param vtBlackList
	 * @param vtBlackServices
	 * @return
	 */
	public  boolean checkBlackList(String isdn, String serviceid,
			Vector vtBlackList, Vector vtBlackServices) {
		if (checkBlackList(isdn, vtBlackList)) {
			int icheck = checkBlackListService(isdn, serviceid, vtBlackServices);
			if (icheck == 2) {
				return false;
			}
			return true;
		}
		int icheck = checkBlackListService(isdn, serviceid, vtBlackServices);
		if (icheck == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param isdn
	 * @param serviceid
	 * @param vtBlackServices
	 * @return
	 */
	private  int checkBlackListService(String isdn, String serviceid,
			Vector vtBlackServices) {
		if (vtBlackServices != null && vtBlackServices.size() > 0) {
			for (int i = 0; i < vtBlackServices.size(); i++) {
				Vector vtOnerow = (Vector) vtBlackServices.elementAt(i);
				String msisdn = String.valueOf(vtOnerow.elementAt(0));
				String type = String.valueOf(vtOnerow.elementAt(1));
				String service = String.valueOf(vtOnerow.elementAt(2));
				if (msisdn.equalsIgnoreCase(isdn)
						&& service.equalsIgnoreCase(serviceid)) {
					if (type.equalsIgnoreCase("2")) {
						return 2;
					} else if (type.equalsIgnoreCase("1")) {
						return 1;
					}

				}
			}
		}
		return 0;
	}

	/**
	 * 
	 * @param isdn
	 * @param serviceid
	 * @param vtBlackListIsdn
	 * @param vtBlackServices
	 * @return
	 */
	private  boolean checkBlackList(String isdn, Vector vtBlackListIsdn) {
		if (vtBlackListIsdn != null && vtBlackListIsdn.size() > 0) {
			for (int i = 0; i < vtBlackListIsdn.size(); i++) {
				Vector vtOnerow = (Vector) vtBlackListIsdn.elementAt(i);
				String msisdn = String.valueOf(vtOnerow.elementAt(0));
				String type = String.valueOf(vtOnerow.elementAt(1));
				if (msisdn.equalsIgnoreCase(isdn)) {
					if (type.equalsIgnoreCase("2")) {
						return false;
					} else if (type.equalsIgnoreCase("1")) {
						return true;
					}

				}
			}
		}
		return false;
	}

	public  String getMessageContent(String MessageCode,
			Vector vtMessageCache, String defaulMeg) {
		if (vtMessageCache == null || vtMessageCache.size() <= 0) {
			return defaulMeg;
		}
		for (int i = 0; i < vtMessageCache.size(); i++) {
			Vector vtTemp = (Vector) vtMessageCache.elementAt(i);
			String strMsgCode = String.valueOf(vtTemp.elementAt(0));
			String strMsgContent = String.valueOf(vtTemp.elementAt(1));
			if (strMsgCode.toUpperCase().equalsIgnoreCase(
					MessageCode.toUpperCase())) {
				return strMsgContent;
			}
		}
		return defaulMeg;
	}

	public  String getSystemParameterContent(String MessageType,
			String MessageCode, Vector vtMessageCache, String defaulMeg) {
		if (vtMessageCache == null || vtMessageCache.size() <= 0) {
			return defaulMeg;
		}
		for (int i = 0; i < vtMessageCache.size(); i++) {
			Vector vtTemp = (Vector) vtMessageCache.elementAt(i);
			String strMsgType = String.valueOf(vtTemp.elementAt(0));
			String strMsgCode = String.valueOf(vtTemp.elementAt(1));
			String strMsgContent = String.valueOf(vtTemp.elementAt(2));
			if (strMsgType.toUpperCase().equalsIgnoreCase(MessageType)
					&& strMsgCode.toUpperCase().equalsIgnoreCase(
							MessageCode.toUpperCase())) {
				return strMsgContent;
			}
		}
		return defaulMeg;
	}

}
