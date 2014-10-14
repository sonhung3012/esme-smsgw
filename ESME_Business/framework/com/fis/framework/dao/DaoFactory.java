package com.fis.framework.dao;

import java.util.Hashtable;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DaoFactory {
	private static Hashtable<String, Object> hashtableBean1;
	private static ApplicationContext appContext1;

	public static void init() {
		hashtableBean1 = new Hashtable();
		appContext1 = new ClassPathXmlApplicationContext(
				"spring/config/ApplicationContext.xml");
	}

	public static <T> T createDao(Class<T> cls) {
		String strName = cls.getName();
		Object tService = hashtableBean1.get(strName);
		if (tService == null) {
			tService = appContext1.getBean(strName);
			System.out.println(strName);
			hashtableBean1.put(strName, tService);
		}
		return (T) tService;
	}
	public static <T> T getDao(String strName) {
		
		Object tService = hashtableBean1.get(strName);
		if (tService == null) {
			tService = appContext1.getBean(strName);
			System.out.println(strName);
			hashtableBean1.put(strName, tService);
		}
		return (T) tService;
	}
}