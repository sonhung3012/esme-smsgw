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

public abstract class CacheBeanOracleDAO extends CacheBean{

	/**
	 * 
	 * @param con
	 * @return
	 */
	public  Vector loadBlackListService(Connection con) {
		PreparedStatement pstmBlackServiceLoading = null;
		try {
			String strSql = "select msisdn,type,service_id from esme_isdn_permission  order by type desc";
			pstmBlackServiceLoading = con.prepareStatement(strSql);
			return Database.convertToVector(pstmBlackServiceLoading
					.executeQuery());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			Database.closeObject(pstmBlackServiceLoading);
		}
	}

	/**
	 * 
	 * @param con
	 * @return
	 */
	public  Vector loadBlackListIsdn(Connection con) {
		PreparedStatement pstmBlackListLoading = null;
		try {
			String strSql = "select msisdn,type,reason from esme_isdn_special where status =1 order by type desc";
			pstmBlackListLoading = con.prepareStatement(strSql);
			return Database
					.convertToVector(pstmBlackListLoading.executeQuery());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			Database.closeObject(pstmBlackListLoading);
		}

	}

	/**
	 * 
	 * @param con
	 * @return
	 */
	public  Hashtable<String, CPObject> loadCPCache(Connection con) {
		PreparedStatement pstmCPLoading = null;
		Hashtable<String, CPObject> htbReturn = new Hashtable<String, CPObject>();
		try {
			String strSql = "SELECT CP_ID, RECEIVE_URL_MSG, RECEIVE_USERNAME, RECEIVE_PASSWORD "
					+ "FROM ESME_CP WHERE STATUS = 1";
			pstmCPLoading = con.prepareStatement(strSql);
			Vector vtData = Database.convertToVector(pstmCPLoading
					.executeQuery());
			if (vtData != null && vtData.size() > 0) {
				for (int i = 0; i < vtData.size(); i++) {
					Vector vtOneRow = (Vector) vtData.get(i);
					CPObject cpObj = new CPObject(vtOneRow);
					htbReturn.put(cpObj.getCPID(), cpObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Database.closeObject(pstmCPLoading);
		}
		return htbReturn;
	}

	/**
	 * 
	 * @param con
	 * @return
	 */
	public  Vector loadSystemParameterCache(Connection con) {
		PreparedStatement pstmMessageLoading = null;
		try {
			String strSql = "select par_type,par_name,par_value from ap_param ";
			pstmMessageLoading = con.prepareStatement(strSql);
			return Database.convertToVector(pstmMessageLoading.executeQuery());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			Database.closeObject(pstmMessageLoading);
		}
	}

	/**
	 * 
	 * @param con
	 * @return
	 */
	public  Vector loadMessageCache(Connection con) {
		PreparedStatement pstmMessageLoading = null;
		try {
			String strSql = " select meg.code msg_code,msg_detal.message,lang.code lang_code  "
					+ " from esme_language lang, esme_message meg ,esme_message_content msg_detal "
					+ " where lang.is_default ='1' and meg.message_id = msg_detal.message_id "
					+ " and msg_detal.language_id = lang.language_id and meg.status =1 ";
			pstmMessageLoading = con.prepareStatement(strSql);
			return Database.convertToVector(pstmMessageLoading.executeQuery());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			Database.closeObject(pstmMessageLoading);
		}
	}

	/**
	 * 
	 * @param con
	 * @return
	 */
	public  List<SMSCMTRountingObject> loadSMSMTRouting(Connection con) {
		PreparedStatement pstmSMSCRoutingLoading = null;
		List<SMSCMTRountingObject> vtSMSCRoutingCache = new ArrayList<SMSCMTRountingObject>();
		try {
			String strSql = " select smsc.smsc_id, smsc.code,smsc.defaul_short_code,smsc.defaul_short_code,isdn.prefix_value,nvl(REPLACE(REPLACE(prefix_value,'*'),'?'),'0') "
					+ " order_value from esme_smsc smsc,esme_isdn_prefix isdn,esme_smsc_routing routing "
					+ " where smsc.status in(1,2) and smsc.smsc_id= routing.smsc_id and routing.prefix_id= isdn.prefix_id "
					+ " order by length(order_value) desc ";
			pstmSMSCRoutingLoading = con.prepareStatement(strSql);
			Vector vtSMSCRouting = Database
					.convertToVector(pstmSMSCRoutingLoading.executeQuery());
			if (vtSMSCRouting != null && vtSMSCRouting.size() > 0) {
				for (int i = 0; i < vtSMSCRouting.size(); i++) {
					try {
						Vector vtOnerow = (Vector) vtSMSCRouting.elementAt(i);
						String smsc_id = String.valueOf(vtOnerow.elementAt(0));
						String smsc_code = String
								.valueOf(vtOnerow.elementAt(1));
						String defaul_short_code = String.valueOf(vtOnerow
								.elementAt(2));
						String shortcode = String
								.valueOf(vtOnerow.elementAt(3));
						String prefix_value = String.valueOf(vtOnerow
								.elementAt(4));
						SMSCMTRountingObject smsCRountingObject = new SMSCMTRountingObject();
						smsCRountingObject.setCode(smsc_code);
						smsCRountingObject
								.setDefaul_short_code(defaul_short_code);
						smsCRountingObject.setPrefix_value(prefix_value);
						smsCRountingObject.setShortcode(shortcode);
						smsCRountingObject.setSmsc_id(Integer.valueOf(smsc_id));
						vtSMSCRoutingCache.add(smsCRountingObject);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			return vtSMSCRoutingCache;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			Database.closeObject(pstmSMSCRoutingLoading);
		}
	}

	/**
	 * 
	 * @param con
	 * @return
	 */

	public  List<SMSMORoutingInfo> SMSRouting(Connection con) {
		PreparedStatement pstmSMSRoutingLoading;
		List<SMSMORoutingInfo> lSMSMORoutingInfo = null;
		try {

			String strSql = " select sv.service_id,sv.root_id,sc.short_code_id,sc.code,cmd.command_id,cmd.code CMD_CODE,cp.cp_id ,cp.code CP_CODE,cp.protocal,sv.parent_id,cp.receive_url_msg,cp.receive_username,cp.receive_password,nvl(REPLACE(REPLACE(cmd.code,'*'),'?'),'0') cmd_order "
					+ " from esme_services sv,esme_short_code sc,esme_sms_command cmd,esme_sms_routing rt,esme_cp cp "
					+ " where sv.status=1 and sc.status=1 and cmd.status=1 and sv.service_id= rt.service_id  "
					+ " and rt.short_code_id=sc.short_code_id and rt.command_id= cmd.command_id and rt.cp_id=cp.cp_id "
					+ " order by cmd_order desc";
			pstmSMSRoutingLoading = con.prepareStatement(strSql);
			lSMSMORoutingInfo = new ArrayList<SMSMORoutingInfo>();
			Vector vtSMSMORouting = Database
					.convertToVector(pstmSMSRoutingLoading.executeQuery());
			if (vtSMSMORouting != null && vtSMSMORouting.size() > 0) {
				for (int i = 0; i < vtSMSMORouting.size(); i++) {
					try {
						Vector vtOneRow = (Vector) vtSMSMORouting.get(i);
						String service_id = String.valueOf(vtOneRow
								.elementAt(0));
						String root_id = StringUtil.nvl(
								String.valueOf(vtOneRow.elementAt(1)), "0") == "" ? "0"
								: StringUtil.nvl(
										String.valueOf(vtOneRow.elementAt(1)),
										"0");
						String short_code_id = String.valueOf(vtOneRow
								.elementAt(2));
						String shortcode_code = String.valueOf(vtOneRow
								.elementAt(3));
						String command_id = String.valueOf(vtOneRow
								.elementAt(4));
						String cmd_code = String.valueOf(vtOneRow.elementAt(5));
						String cp_id = String.valueOf(vtOneRow.elementAt(6));
						String cp_code = String.valueOf(vtOneRow.elementAt(7));
						String protocal = String.valueOf(vtOneRow.elementAt(8));
						String sv_parent_id = String.valueOf(vtOneRow
								.elementAt(9));

						String url = String.valueOf(vtOneRow.elementAt(10));
						String username = String
								.valueOf(vtOneRow.elementAt(11));
						String password = String
								.valueOf(vtOneRow.elementAt(12));

						SMSMORoutingObject smsMORoutingObject = new SMSMORoutingObject();
						smsMORoutingObject.setService_id(formatInt(service_id));
						smsMORoutingObject.setRoot_id(formatInt(root_id));
						smsMORoutingObject
								.setShort_code_id(formatInt(short_code_id));
						smsMORoutingObject.setShort_code_code(shortcode_code);
						smsMORoutingObject.setCommand_id(formatInt(command_id));
						smsMORoutingObject.setCmd_code(cmd_code);
						smsMORoutingObject.setCp_id(formatInt(cp_id));
						smsMORoutingObject.setCmd_code(cmd_code);
						smsMORoutingObject.setProtocal(protocal);
						smsMORoutingObject
								.setParent_service_id(formatInt(sv_parent_id));
						smsMORoutingObject.setUrl(url);
						smsMORoutingObject.setUsername(username);
						smsMORoutingObject.setPassword(password);
						// add smsmo
						lSMSMORoutingInfo = addSMSMORouting(lSMSMORoutingInfo,
								smsMORoutingObject);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return lSMSMORoutingInfo;
	}

	/**
	 * 
	 * @param lSMSMOInfo
	 * @param smsMO
	 */
	public  List<SMSMORoutingInfo> addSMSMORouting(
			List<SMSMORoutingInfo> lSMSMOInfo, SMSMORoutingObject smsMO) {
		if (lSMSMOInfo == null || lSMSMOInfo.size() <= 0) {
			lSMSMOInfo = new ArrayList<SMSMORoutingInfo>();
			SMSMORoutingInfo smsMORoutingInfo = new SMSMORoutingInfo();
			if (smsMORoutingInfo.getVtSMSMORouting() == null) {
				smsMORoutingInfo
						.setVtSMSMORouting(new Vector<SMSMORoutingObject>());
			}
			smsMORoutingInfo.setShortcode(smsMO.getShort_code_code());
			smsMORoutingInfo.getVtSMSMORouting().add(smsMO);
			lSMSMOInfo.add(smsMORoutingInfo);
			return lSMSMOInfo;
		} else {
			for (int i = 0; i < lSMSMOInfo.size(); i++) {
				SMSMORoutingInfo smsMORoutingInfo = lSMSMOInfo.get(i);
				if (smsMORoutingInfo.getShortcode().equalsIgnoreCase(
						smsMO.getShort_code_code())) {
					if (smsMORoutingInfo.getVtSMSMORouting() == null) {
						smsMORoutingInfo
								.setVtSMSMORouting(new Vector<SMSMORoutingObject>());
					}
					smsMORoutingInfo.getVtSMSMORouting().add(smsMO);
				} else {
					smsMORoutingInfo = new SMSMORoutingInfo();
					if (smsMORoutingInfo.getVtSMSMORouting() == null) {
						smsMORoutingInfo
								.setVtSMSMORouting(new Vector<SMSMORoutingObject>());
					}
					smsMORoutingInfo.getVtSMSMORouting().add(smsMO);
					smsMORoutingInfo.setShortcode(smsMO.getShort_code_code());
					lSMSMOInfo.add(smsMORoutingInfo);
				}
			}
			return lSMSMOInfo;
		}
	}

	public  int formatInt(String input) {
		if (input == null || input == "") {
			return 0;
		} else {
			try {
				return Integer.valueOf(input);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return 0;
	}
}
