package com.fis.esme.core.bean.subscriber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.fss.sql.Database;

public abstract class SMSSubscriber {
	public abstract int insertSubscriber(Connection con, String subid,
			String msisdn) throws Exception;

	public abstract int insertSubGroup(Connection con, String subid,
			String groupid) throws Exception;

	public abstract void regSubscriber(Connection con, String groupid,
			String msisdn) throws Exception;

	public abstract String checkGroupExis(Connection con, String groupName)
			throws Exception;

	public abstract String checkSubscriberExis(Connection con, String msisdn,
			String groupid) throws Exception;

	public abstract String getSequence(Connection con, String sequenceName)
			throws Exception;

	public abstract int deleteSubscriber(Connection con, String msisdn, String groupid);
	
	public abstract Vector getSubscriberGroups(Connection con, String strGroupList) ;

	public abstract String getMessageByID(Connection con, String MessageId) ;
	
}
