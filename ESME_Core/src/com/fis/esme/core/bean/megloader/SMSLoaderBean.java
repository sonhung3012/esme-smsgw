package com.fis.esme.core.bean.megloader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import com.fss.sql.Database;

public abstract class SMSLoaderBean {
	public abstract Vector getMOMessage(Connection con, int iRowNumber,
			int intPartitionRank, int intRetryRowNumber, int intRetrySpace,
			int intRetryNumber) throws Exception;

	public abstract int updateMOStatus(Connection con, String mt_id,
			String status);

	public abstract Vector getMTMessage(Connection con, int iRowNumber,
			int intPartitionRank, int intRetryRowNumber, int intRetrySpace,
			int intRetryNumber, int intReloadSpace, int intReloadNumber)throws Exception;

	public abstract int updateMTStatus(Connection con, String mt_id,
			String status, String type) throws SQLException;
}
