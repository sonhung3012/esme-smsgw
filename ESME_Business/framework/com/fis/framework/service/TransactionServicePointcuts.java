package com.fis.framework.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class TransactionServicePointcuts {
	private static final Log log = LogFactory
			.getLog(TransactionServicePointcuts.class);

	public void commit() {
		log.debug("*** you've commit now!  ***");

		Session session = (Session) ThreadLocalContext.get();
		
		session.getTransaction().commit();
	}

	public void beginTransaction() {
		log.debug("*** you've beginTransaction now! ***");

		Session session = (Session) ThreadLocalContext.get();
		session.beginTransaction();
	}

	public void rollback() {
		Session session = (Session) ThreadLocalContext.get();
		if (session != null) {
			if (session.isOpen()) {
				Transaction tx = session.getTransaction();
				if (tx != null)
					if (tx.isActive())
						tx.rollback();
			}
		}
		log.debug("*** you've rollback now!  ***");
	}
}