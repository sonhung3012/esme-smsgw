package com.fis.esme.core.bean.subscriber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.fss.sql.Database;

public class SMSSubscriberSQLServer extends SMSSubscriber {
	public String strSubscriberSequence = "cp_sub_seq";

	public int insertSubscriber(Connection con, String subid, String msisdn)
			throws Exception {
		PreparedStatement pstmtInsertSubscriber = null;
		try {
			String strSQLCommand = "INSERT INTO SUBSCRIBER(SUB_ID,MSISDN,STATUS,CREATE_DATE,EMAIL) VALUES(?,?,'1',GETDATE(),?)";
			pstmtInsertSubscriber = con.prepareStatement(strSQLCommand);
			pstmtInsertSubscriber.setString(1, subid);
			pstmtInsertSubscriber.setString(2, msisdn);
			pstmtInsertSubscriber.setString(3, "email.demo@gmail.com");
			return pstmtInsertSubscriber.executeUpdate();
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmtInsertSubscriber);
		}

	}

	public int insertSubGroup(Connection con, String subid, String groupid)
			throws Exception {
		PreparedStatement pstmtInsertSubGroup = null;
		try {
			String strSQLCommand = "INSERT INTO SUB_GROUP(SUB_ID,GROUP_ID) VALUES(?,?)";
			pstmtInsertSubGroup = con.prepareStatement(strSQLCommand);
			pstmtInsertSubGroup.setString(1, subid);
			pstmtInsertSubGroup.setString(2, groupid);
			return pstmtInsertSubGroup.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			Database.closeObject(pstmtInsertSubGroup);
		}
	}

	public void regSubscriber(Connection con, String groupid, String msisdn)
			throws Exception {
		try {
			String strSubSequence = getSequence(con, strSubscriberSequence);
			insertSubscriber(con, strSubSequence, msisdn);
			insertSubGroup(con, strSubSequence, groupid);
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}

	}

	public int deleteSubscriber(Connection con, String msisdn, String groupid) {
		PreparedStatement pstmtdeleteSubGroups = null;
		PreparedStatement pstmtdeleteSubcriber = null;
		try {
			String strSQLCommand = "DELETE SUB_GROUP WHERE SUB_ID IN (SELECT SUB_ID FROM SUBSCRIBER WHERE MSISDN =? )";
			if (groupid != null && groupid != "") {
				strSQLCommand += " AND GROUP_ID= '" + groupid + "'";
			}
			pstmtdeleteSubGroups = con.prepareStatement(strSQLCommand);
			pstmtdeleteSubGroups.setString(1, msisdn);
			pstmtdeleteSubGroups.executeUpdate();
			// // delete sub
			strSQLCommand = " DELETE SUBSCRIBER  WHERE MSISDN =? ";
			pstmtdeleteSubcriber = con.prepareStatement(strSQLCommand);
			pstmtdeleteSubcriber.setString(1, msisdn);
			return pstmtdeleteSubcriber.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		} finally {
			Database.closeObject(pstmtdeleteSubGroups);
			Database.closeObject(pstmtdeleteSubcriber);
		}
	}

	public String checkGroupExis(Connection con, String groupName)
			throws Exception {
		PreparedStatement pstmtcheckGroupExis = null;
		try {
			String strSQLCommand = "SELECT GROUP_ID FROM GROUPS WHERE STATUS='1' AND UPPER(NAME) = ?";
			pstmtcheckGroupExis = con.prepareStatement(strSQLCommand);
			pstmtcheckGroupExis.setString(1, groupName);
			ResultSet rsGroupId = pstmtcheckGroupExis.executeQuery();
			if (rsGroupId != null && rsGroupId.next())
				return rsGroupId.getString(1);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			Database.closeObject(pstmtcheckGroupExis);
		}
		return null;
	}

	public String checkSubscriberExis(Connection con, String msisdn,
			String groupid) throws Exception {
		PreparedStatement pstmtcheckSubscriberExis = null;
		try {
			String strSQLCommand = "SELECT * FROM SUBSCRIBER S INNER JOIN SUB_GROUP G ON S.STATUS='1' AND S.SUB_ID=G.SUB_ID AND G.GROUP_ID =? AND S.MSISDN=? ";
			pstmtcheckSubscriberExis = con.prepareStatement(strSQLCommand);
			pstmtcheckSubscriberExis.setString(1, groupid);
			pstmtcheckSubscriberExis.setString(2, msisdn);
			ResultSet rsSubId = pstmtcheckSubscriberExis.executeQuery();
			if (rsSubId != null && rsSubId.next())
				return rsSubId.getString(1);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			Database.closeObject(pstmtcheckSubscriberExis);
		}
		return null;
	}

	public Vector getSubscriberGroups(Connection con, String strGroupList) {
		PreparedStatement pstmtGetSubscriberGroups = null;
		try {
			String strSQLCommand = "SELECT S.SUB_ID,MSISDN FROM SUBSCRIBER S INNER JOIN SUB_GROUP G ON S.STATUS='1' AND S.SUB_ID=G.SUB_ID AND G.GROUP_ID IN (?)";
			pstmtGetSubscriberGroups = con.prepareStatement(strSQLCommand);
			pstmtGetSubscriberGroups.setString(1, strGroupList);
			return Database.convertToVector(pstmtGetSubscriberGroups
					.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmtGetSubscriberGroups);
		}
		return null;
	}

	public String getMessageByID(Connection con, String MessageId) {
		PreparedStatement pstmtGetMessageContent = null;
		try {
			String strSQLCommand = "SELECT C.MESSAGE FROM ESME_MESSAGE M INNER JOIN ESME_MESSAGE_CONTENT C ON M.STATUS='1' AND M.MESSAGE_ID = C.MESSAGE_ID AND C.MESSAGE_ID = ? ";
			pstmtGetMessageContent = con.prepareStatement(strSQLCommand);
			pstmtGetMessageContent.setString(1, MessageId);
			ResultSet rsMessageContent = pstmtGetMessageContent.executeQuery();
			if (rsMessageContent.next()) {
				return rsMessageContent.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmtGetMessageContent);
		}
		return null;

	}

	public String getSequence(Connection con, String sequenceName)
			throws Exception {
		PreparedStatement pstmSequence = null;
		try {
			pstmSequence = con.prepareStatement("SELECT NEXT VALUE FOR "
					+ sequenceName);
			ResultSet rsSequence = pstmSequence.executeQuery();
			Vector vtTemp = Database.convertToVector(rsSequence);
			return String.valueOf(((Vector) vtTemp.elementAt(0)).get(0));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			Database.closeObject(pstmSequence);
		}
		return null;
	}
}
