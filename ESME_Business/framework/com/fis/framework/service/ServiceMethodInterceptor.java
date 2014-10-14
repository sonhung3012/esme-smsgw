package com.fis.framework.service;

import java.lang.reflect.Method;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

import com.fis.esme.utils.ErrorConfig;
import com.fis.framework.dao.hibernate.CloseSession;

public class ServiceMethodInterceptor implements MethodInterceptor {
	private static final Log log = LogFactory
			.getLog(ServiceMethodInterceptor.class);

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		SessionFactory sessionFactory = (SessionFactory) ServiceLocator
				.getBean("sessionFactory");
		Long startTime = System.currentTimeMillis();
		Session session = sessionFactory.openSession();
//		LazyInitializationController.setLazyInitialization(true);
		ThreadLocalContext.set(session);
		CloseSession.put(session);
		Method method = methodInvocation.getMethod();
		String methodName = method.getDeclaringClass().getName() + "."
				+ method.getName();
		Object result = null;
		Date date = new Date();
		log.debug("Method '" + methodName + "' took " + date + "  to run");

		long duration;
		try {
			result = methodInvocation.proceed();
		} catch (Exception e) {
			e.printStackTrace();
			Throwable ec = e;
			while(ec.getCause() !=null)
			{
				ec=ec.getCause();
			}
			String strErrorMessage = ec.getMessage();
			
			System.out.println(strErrorMessage);
			if (strErrorMessage != null)
			{
				strErrorMessage=strErrorMessage.trim();
				strErrorMessage=strErrorMessage.replace("\n", "");
					String strMsgErrorNew = ErrorConfig.getString(strErrorMessage);
					if (strMsgErrorNew != null)
						throw new Exception(strMsgErrorNew);
				}

			throw ec;
		} finally {
			
			session = (Session) ThreadLocalContext.get();
			if (session != null) {
				ThreadLocalContext.unset();
				log.debug("Session close ");
				if (session.isOpen()) {
					session.close();
				}
				session=null;
				duration = System.currentTimeMillis() - startTime;
				log.debug("Method '" + methodName + "' took " + duration
						+ " milliseconds to run");
			}
//			LazyInitializationController.close();
		}

		return result;
	}
}