package com.fis.framework.dao.hibernate;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.fis.framework.service.ServiceMethodInterceptor;

public class CloseSession extends Thread {
	private static final Log log = LogFactory
			.getLog(ServiceMethodInterceptor.class);

	
	public static void init(Long p_lSleep, Long p_lTimeCloseSession) {
		lSleep = p_lSleep;
		lTimeCloseSession = p_lTimeCloseSession;
		hashtableSession = new Hashtable<Long, Session>();
	}

	public static Hashtable<Long, Session> hashtableSession = null;
	public static Long lSleep = 0L;
	public static Long lTimeCloseSession = 0L;

	public static void put(Session session) {
		if(hashtableSession==null)
			hashtableSession = new Hashtable<Long, Session>();
		long startTime = System.currentTimeMillis();
		hashtableSession.put(startTime, session);
	}

	public void run() {

		while (true) {

			long startTime = System.currentTimeMillis();
			if(hashtableSession==null)
				hashtableSession = new Hashtable<Long, Session>();
			Enumeration<Long> e = hashtableSession.keys();
			Vector<Long> longs = new Vector<Long>();
			while (e.hasMoreElements()) {
				Long key = e.nextElement();
				long t = startTime - key;
				Session session = hashtableSession.get(key);
				if( (t > lTimeCloseSession)||(!session.isOpen()))
				{
					log.debug("get  Session time orver");
					log.debug(startTime);
					log.debug(key);
					log.debug(t);
					longs.add(key);
				}
				
				
			}
			for (Iterator iterator = longs.iterator(); iterator.hasNext();) {
				Long long1 = (Long) iterator.next();

				Session session = hashtableSession.get(long1);
				if (session.isOpen()) {
					session.close();
					log.debug("Close Session time orver");

				}
				hashtableSession.remove(long1);

			}

			try {
			
				Thread.sleep(lSleep);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
}
