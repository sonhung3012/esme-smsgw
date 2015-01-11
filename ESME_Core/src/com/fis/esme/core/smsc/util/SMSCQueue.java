package com.fis.esme.core.smsc.util;

import java.util.Map;

import com.fss.queue.Attributable;

public class SMSCQueue extends com.fis.queue.Queue {

	/**
	 * 
	 * @param strIndex
	 */
	public SMSCQueue(String[] strIndex) {
		super(strIndex);
	}

	/**
	 * 
	 * @param MaxQueue
	 */
	public void setQueueSize(int MaxQueue) {
		super.setMaxQueueSize(MaxQueue);
	}

	// //////////////////////////////////////////////////////
	protected Attributable onMessageTimedOut(Attributable msg) {
		if (msg == null) {
			return null;
		}
		return msg;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	protected synchronized Object dequeue(Map Attribute) throws Exception {
		Object object = detach(Attribute);
		return object;
	}

}
