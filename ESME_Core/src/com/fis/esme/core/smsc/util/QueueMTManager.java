package com.fis.esme.core.smsc.util;

import java.util.HashMap;

import com.fis.esme.core.log.LogRecord;
import com.fis.esme.core.util.LinkQueue;
import com.fss.mwallet.util.ReceiveMessage;

public class QueueMTManager extends QueueManager{
	private HashMap htRequestACKCache=null;
	private LinkQueue<LogRecord> lqLoggerQueue=null;
	private LinkQueue<ReceiveMessage> lqMOQueue =null;
	
	
	public QueueMTManager(String[] strIndex) {
		super(strIndex);
		htRequestACKCache=new HashMap();
		lqMOQueue=new LinkQueue<ReceiveMessage>();
		lqLoggerQueue=new LinkQueue<LogRecord>();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public HashMap getRequestAckCache() {
		// TODO Auto-generated method stub
		return htRequestACKCache;
	}

	@Override
	public LinkQueue<LogRecord> getLoggerQueue() {
		// TODO Auto-generated method stub
		return lqLoggerQueue;
	}


	@Override
	public LinkQueue<ReceiveMessage> getRequestMOQueue() {
		// TODO Auto-generated method stub
		return lqMOQueue;
	}

}
