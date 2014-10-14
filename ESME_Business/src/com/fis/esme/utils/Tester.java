package com.fis.esme.utils;

import com.fis.framework.dao.DaoFactory;
import com.fis.framework.service.ServiceLocator;

public class Tester {
	public static void main(String[] args) throws Exception {

		ServiceLocator.init();

		DaoFactory.init();

	}
}
