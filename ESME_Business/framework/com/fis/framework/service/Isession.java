package com.fis.framework.service;

import org.hibernate.Session;

public interface Isession {
	public Session getSession();
	public void setSession(Session session) ;
}
