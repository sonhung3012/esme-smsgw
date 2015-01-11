package com.fis.esme.core.smpp.util;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

public class EhCacheManager {
	/**
	 * 
	 */
	public final static String mstrUsersCacheName = "UsersCache";
	public final static String mstrSubmitCacheName = "SubmitCache";
	public final static String mstrDeliveryCacheName = "DeliveryCache";
	
	private CacheManager manager = null;
	private static final String mstrConfigURL = "config/ehcache.xml";

	public EhCacheManager() throws InterruptedException {
		super();
		manager = getCacheManager();
	}

	/**
	 * 
	 * @param strName
	 * @return
	 */
	public Ehcache getCache(String strName) {
		return manager.getCache(strName);
	}

	/**
	 * 
	 * @return
	 */
	private CacheManager getCacheManager() {
		return CacheManager.create(mstrConfigURL);
	}
	
	public void shutdown() {
		manager.shutdown();
	}
}