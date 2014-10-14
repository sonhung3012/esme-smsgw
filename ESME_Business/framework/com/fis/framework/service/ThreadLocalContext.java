package com.fis.framework.service;

public class ThreadLocalContext {
	public static final ThreadLocal<Object> userThreadLocal = new ThreadLocal<Object>();

	public static void set(Object user) {
		userThreadLocal.set(user);
	}

	public static void unset() {
		userThreadLocal.remove();
	}

	public static Object get() {
		return userThreadLocal.get();
	}
}