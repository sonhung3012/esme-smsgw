package com.fis.esme.app;
//package com.fis.prc.app;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Map;
//import java.util.Vector;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.connection.ConnectionProvider;
//import org.hibernate.engine.SessionFactoryImplementor;
//
//import com.fis.framework.service.ServiceLocator;
//import com.fis.prc.mt.persistence.BuildMTInput;
//import com.fis.prc.mt.persistence.GlobalParameter;
//import com.fis.prc.mt.persistence.MessageObject;
//import com.fis.prc.mt.persistence.SubMessageObject;
//import com.fis.prc.persistence.PrcVariableMapping;
//import com.fss.sql.Database;
//import com.fss.util.StringUtil;
//
//public class CacheBuildMT {
//	public static Hashtable mprtResponse = new Hashtable();
//	public Hashtable mprtInteractionCache = new Hashtable();
//	public static Hashtable<String, PrcVariableMapping> mhtbVariableMappingCache = new Hashtable<String, PrcVariableMapping>();
//	public static Hashtable<String, String> mhtbMessageID = new Hashtable<String, String>();
//
//	private PreparedStatement pstmSelectVariableMapping = null;
//	private PreparedStatement pstmSelectMessageID = null;
//	private PreparedStatement pstmSelectRoleID = null;
//	private String strParentRoleCode = ""; // ghi chinh xac Code tuong ung voi
//											// cac role o day
//	private String strSubParentRoleCode = "";
//	private String strChildRoleCode = "";
//
//	SessionFactory sessionFactory;
//
//	Session session;
//
//	Connection conn;
//
//	public void init() throws Exception {
//		System.out.println("Init Build MT Cache...");
//		opentConnection();
//		reloadResponse(conn);
//		beforeSession(conn);
//		processSession();
//		closeConnection();
//		System.out.println("Init Build MT Cache finish...");
//	}
//
//	private void opentConnection() throws Exception {
//		sessionFactory = (SessionFactory) ServiceLocator
//				.getBean("sessionFactory");
//		session = sessionFactory.openSession();
//		SessionFactoryImplementor sfi = (SessionFactoryImplementor) session
//				.getSessionFactory();
//		ConnectionProvider cp = sfi.getConnectionProvider();
//		conn = cp.getConnection();
//	}
//
//	private void closeConnection() throws Exception {
//		Database.closeObject(conn);
//		session.close();
//	}
//
//	protected void beforeSession(Connection conn) throws Exception {
//		String strCommand = "SELECT VARIABLE_ID, MESSAGE_ID, VARIABLE_NAME, MAP_WITH, GETTER_FUNCTION_NAME, "
//				+ "VALUE_DEFAULT, CHILDREN_VARIABLE_ID, OPTIONS, TEMPLATE, TEMPLATE_REFERENCE "
//				+ "FROM PRC_VARIABLE_MAPPING";
//
//		pstmSelectVariableMapping = conn.prepareStatement(strCommand);
//
//		strCommand = "SELECT ACTION_ID, CODE, MESSAGE_ID FROM PRC_INTERACTION WHERE STATUS = 1 ";
//
//		pstmSelectMessageID = conn.prepareStatement(strCommand);
//		// pstmSelectMessageID.setString(1, actionId);
//
//		strCommand = "SELECT ROLE_ID, CODE FROM PRC_FAMILY_ROLE WHERE STATUS = 1 ";
//
//		pstmSelectRoleID = conn.prepareStatement(strCommand);
//	}
//
//	protected void processSession() throws Exception {
//		if (mhtbVariableMappingCache.size() > 0) {
//			mhtbVariableMappingCache.clear();
//		}
//		Vector vtData = Database.convertToVector(pstmSelectVariableMapping
//				.executeQuery());
//		if (vtData != null && vtData.size() > 0) {
//			for (int i = 0; i < vtData.size(); i++) {
//				Vector vtOneRow = (Vector) vtData.get(i);
//
//				PrcVariableMapping variableObject = new PrcVariableMapping(
//						vtOneRow);
//				mhtbVariableMappingCache.put(
//						StringUtil.nvl(variableObject.getVariableId(), null),
//						variableObject);
//			}
//		}
//
//		if (mhtbMessageID.size() > 0) {
//			mhtbMessageID.clear();
//		}
//		Vector vtDataMessage = Database.convertToVector(pstmSelectMessageID
//				.executeQuery());
//		if (vtDataMessage != null && vtDataMessage.size() > 0) {
//			for (int i = 0; i < vtDataMessage.size(); i++) {
//				Vector vtOneRow = (Vector) vtDataMessage.get(i);
//				mhtbMessageID.put(vtOneRow.get(0).toString() + "_"
//						+ vtOneRow.get(1).toString(), vtOneRow.get(2)
//						.toString());
//			}
//		}
//
//		Vector vtDataRole = Database.convertToVector(pstmSelectRoleID
//				.executeQuery());
//		if (vtDataRole != null && vtDataRole.size() > 0) {
//			for (int i = 0; i < vtDataRole.size(); i++) {
//				Vector vtOneRow = (Vector) vtDataRole.get(i);
//				String strRoleCode = StringUtil.nvl(vtOneRow.get(1), "");
//				String strRoleID = vtOneRow.get(0).toString();
//				if (!strRoleCode.equals("")) {
//					if (strRoleCode.equals(strParentRoleCode)) {
//						GlobalParameter.MAIN_PARENT_ROLE_ID = strRoleID;
//					} else if (strRoleCode.equals(strSubParentRoleCode)) {
//						GlobalParameter.SUB_PARENT_ROLE_ID = strRoleID;
//					} else if (strRoleCode.equals(strChildRoleCode)) {
//						GlobalParameter.CHILDREN_ROLE_ID = strRoleID;
//					}
//				}
//			}
//		}
//	}
//
//	private void reloadResponse(Connection cn) throws Exception {
//		Map mapInteraction = getInteraction(cn);
//		this.mprtInteractionCache.clear();
//		this.mprtInteractionCache.putAll(mapInteraction);
//		Map map = getResponseMessage(cn, mprtInteractionCache);
//		this.mprtResponse.clear();
//		this.mprtResponse.putAll(map);
//	}
//
//	// theo action va language
//	public Map getInteraction(Connection cn) throws Exception {
//		String strSQL = " SELECT IT.action_id,IT.code,CT.message,CT.language_id, IT.interaction_id "
//				+ " FROM PRC_INTERACTION IT,PRC_MESSAGE MS,PRC_MESSAGE_CONTENT CT "
//				+ " WHERE IT.message_id=MS.message_id "
//				+ " AND MS.message_id=CT.message_id ";
//		Map map = new HashMap();
//		Vector vtInteraction = Database.executeQuery(cn, strSQL);
//		for (int i = 0; i < vtInteraction.size(); i++) {
//			Vector vtTemp = (Vector) vtInteraction.elementAt(i);
//			String strActionID = String.valueOf(vtTemp.elementAt(0));
//			String strInterCode = String.valueOf(vtTemp.elementAt(1));
//			String strLanguage = String.valueOf(vtTemp.elementAt(3));
//			String strInteractionID = String.valueOf(vtTemp.elementAt(4));
//
//			String strValue = strActionID + "_" + strInterCode + "_"
//					+ strLanguage;
//			if (strValue != "") {
//				map.put(strInteractionID, strValue);
//			}
//		}
//		return map;
//	}
//
//	// mess id, action id,
//	public Map getResponseMessage(Connection cn, Hashtable mprtInteractionCache)
//			throws Exception {
//		String strSQL = " SELECT IT.action_id,IT.code,CT.message,CT.language_id, "
//				+ "IT.PARENT_ID, IT.ROLE_ID, IT.MESSAGE_ID FROM PRC_INTERACTION IT,PRC_MESSAGE MS,PRC_MESSAGE_CONTENT CT "
//				+ " WHERE IT.message_id=MS.message_id "
//				+ " AND MS.message_id=CT.message_id ";
//		Map map = new HashMap();
//		ArrayList<Vector> alSubMessage = new ArrayList<Vector>();
//		Vector vtResponse = Database.executeQuery(cn, strSQL);
//		for (int i = 0; i < vtResponse.size(); i++) {
//			Vector vtTemp = (Vector) vtResponse.elementAt(i);
//			String strActionID = String.valueOf(vtTemp.elementAt(0));
//			String strInterCode = String.valueOf(vtTemp.elementAt(1));
//			String strMessage = String.valueOf(vtTemp.elementAt(2));
//			String strLanguage = String.valueOf(vtTemp.elementAt(3));
//			String strParentID = StringUtil.nvl(vtTemp.elementAt(4), "");
//			String strKey = strActionID + "_" + strInterCode + "_"
//					+ strLanguage;
//			if (strKey != "" && strMessage != "") {
//				if (strParentID.equals("")) {
//					MessageObject messageObject = new MessageObject();
//					messageObject.setMainContent(strMessage);
//					map.put(strKey.toUpperCase(), messageObject);
//				} else {
//					alSubMessage.add(vtTemp);
//				}
//			}
//		}
//		if (alSubMessage.size() > 0) {
//			for (int i = 0; i < alSubMessage.size(); i++) {
//				Vector vtTemp = (Vector) alSubMessage.get(i);
//				String strMessage = String.valueOf(vtTemp.elementAt(2));
//				String strParentID = StringUtil.nvl(vtTemp.elementAt(4), "");
//				String strRoleID = StringUtil.nvl(vtTemp.elementAt(5), "");
//				String strMessageID = String.valueOf(vtTemp.elementAt(6));
//				if (mprtInteractionCache.get(strParentID) == null)
//					continue;
//				String strParentKey = mprtInteractionCache.get(strParentID)
//						.toString();
//				MessageObject messageObject = (MessageObject) map
//						.get(strParentKey);
//				SubMessageObject subMessageObj = new SubMessageObject();
//				subMessageObj.setContent(strMessage);
//				subMessageObj.setMessageID(strMessageID);
//				subMessageObj.setRoleID(strRoleID);
//				messageObject.getListSubMessage().add(subMessageObj);
//			}
//		}
//		return map;
//	}
//}
