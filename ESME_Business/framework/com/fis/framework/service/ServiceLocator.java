package com.fis.framework.service;

import java.util.Hashtable;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceLocator {
	private static Hashtable<String, Object> hashtableBean;
	private static ApplicationContext appContext;

	public static void init() {
		hashtableBean = new Hashtable();
		appContext = new ClassPathXmlApplicationContext(
				"spring/config/ApplicationContext.xml");
	}

	public static Object getBean(String strBean) {
		Object obj = appContext.getBean(strBean);
		return obj;
	}

	public static <T> T createService(Class<T> cls) {
		String strName = cls.getName();
		Object tService = null;

		tService = appContext.getBean(strName);
		System.out.println(strName);

		return (T) tService;
	}
}