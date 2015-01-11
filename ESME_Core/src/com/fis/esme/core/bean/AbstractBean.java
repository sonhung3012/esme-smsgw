package com.fis.esme.core.bean;

import java.sql.Connection;
import java.sql.SQLException;
import com.fss.sql.Database;

/**
 * <p>Title: PRC Alert Controller</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: FIS-SOT</p>
 *
 * @author ThangBQ2
 * @version 1.0
 */

public  class AbstractBean {
	protected Connection mcnMain = null;
	
    public AbstractBean() {
    }

    public void setConnection(Connection cn) {
        mcnMain = cn;
    }

    public Connection getConnection() {
        return mcnMain;
    }

    //init all db object
    public  void init() throws SQLException {
	}

    //Close all db object
    public  void close() {
	}

    //close connection
    public void closeConnection() {
        try {
            if (mcnMain != null && !mcnMain.isClosed()) {
                Database.closeObject(mcnMain);
                mcnMain = null;
            }
        } catch (SQLException ex) {
        }
    }
}