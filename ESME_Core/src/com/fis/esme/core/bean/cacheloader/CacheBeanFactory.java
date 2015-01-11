package com.fis.esme.core.bean.cacheloader;

import com.fis.esme.core.app.ThreadManagerEx;

public class CacheBeanFactory {
	public static CacheBean getSmsBeanFactory(String factoryMode) {
		if (factoryMode.equalsIgnoreCase("SQLSERVER")) {
			return new CacheBeanSQLServer();
		} else {
			return new CacheBeanOracle();
		}
	}

	public static CacheBean getSmsBeanFactory() {
		String factoryMode = ThreadManagerEx.getDatabaseMode();
		if (factoryMode.equalsIgnoreCase("SQLSERVER")) {
			return new CacheBeanSQLServer();
		} else {
			return new CacheBeanOracle();
		}
	}

}
