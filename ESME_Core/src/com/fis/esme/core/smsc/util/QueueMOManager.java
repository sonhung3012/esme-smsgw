package com.fis.esme.core.smsc.util;

import java.util.Vector;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.exception.QueueLevelException;
import com.fis.esme.core.util.LinkQueue;
import com.fss.mwallet.util.ReceiveMessage;
import com.fss.queue.Attributable;
import com.fss.util.AppException;

public class QueueMOManager extends SMSCQueue {

	private static final long serialVersionUID = 1L;
	private static long iIndex = 1;
	private Vector mvtDispatcher = new Vector();
	private LinkQueue<ReceiveMessage> lqMOQueue =null;
	
	public QueueMOManager(String[] strIndex) {
		super(strIndex);
		lqMOQueue=new LinkQueue<ReceiveMessage>();
	}

	// //////////////////////////////////////////////////////
	public void addDispatcher(ManageableThreadEx d) {
		mvtDispatcher.addElement(d);
	}

	// //////////////////////////////////////////////////////
	public void removeDispatcher(ManageableThreadEx d) {
		if (mvtDispatcher.size() > 0) {
			for (int iIndex = 0; iIndex < mvtDispatcher.size(); iIndex++) {
				ManageableThreadEx dispatcher = (ManageableThreadEx) mvtDispatcher
						.elementAt(iIndex);
				if (dispatcher.getThreadID() == d.getThreadID()) {
					synchronized (mvtDispatcher) {
						mvtDispatcher.remove(iIndex);
					}
				}
			}
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 */
	public boolean checkDispatcherAvailable(Attributable msg)
			throws AppException {
		for (int iIndex = 0; iIndex < mvtDispatcher.size(); iIndex++) {
			ManageableThreadEx dispatcher = (ManageableThreadEx) mvtDispatcher
					.elementAt(iIndex);
			if (matchAttributes(msg.getAttributes(), dispatcher.getProperties())) {
				if (dispatcher.isOpen()) {
					return true;
				}
			}
		}
		return false;
	}

	// //////////////////////////////////////////////////////
	/**
	 * 
	 * @param msg
	 *            Attributable
	 * @throws Exception
	 */
	// //////////////////////////////////////////////////////
	public synchronized void attachSubmitMessage(Attributable msg,
			String ThreadID) throws Exception {
		// Check dispatcher
		if (ThreadID != null && ThreadID != "") {
			msg.setAttribute("DataType", ThreadID);
		} else {
			throw new QueueLevelException(
					"Dispacher ID is null, can not routing info :" + ThreadID);
		}
		if (!checkDispatcherAvailable(msg)) {
			throw new QueueLevelException(
					"Can't find Dispacher sender to attach for ID:" + ThreadID);
		}
		super.attach(msg);
	}

	public synchronized long getSequence() {
		return iIndex++;
	}
	
	public LinkQueue<ReceiveMessage> getRequestMOQueue() {
		// TODO Auto-generated method stub
		return lqMOQueue;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public synchronized void attach(Attributable msg) throws Exception {
		if (!checkDispatcherAvailable(msg)) {
			throw new QueueLevelException(
					"Can't find dispatcher to attach ");
		}
		super.attach(msg);
	}

}
