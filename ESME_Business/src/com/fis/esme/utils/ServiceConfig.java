/**
 * 
 */
package com.fis.esme.utils;

import java.util.Properties;

import com.fis.framework.util.PropertyLoader;

/**
 * @author Administrator
 * 
 */
public class ServiceConfig {

	/**
	 * 
	 */
	public ServiceConfig() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	static private Properties props = null;
	static private String filename = "properties/service.properties";

	static public String getString(String name) {
		return getProps().getProperty(name);
	}

	static private Properties getProps() {
		if (props == null) {
			props = PropertyLoader.loadProperties(filename);
		}
		return props;
	}
}
