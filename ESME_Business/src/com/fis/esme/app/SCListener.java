package com.fis.esme.app;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.fis.framework.dao.DaoFactory;
import com.fis.framework.service.ServiceLocator;

/**
 * Application Lifecycle Listener implementation class McaListener
 * 
 */
public class SCListener implements ServletContextListener {

	Logger log = Logger.getLogger(SCListener.class);

	/**
	 * Default constructor.
	 */
	public SCListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see SCListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {

		ServiceLocator.init();
		DaoFactory.init();
//		try {
//			CacheManager.init();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	/**
	 * @see SCListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

}
