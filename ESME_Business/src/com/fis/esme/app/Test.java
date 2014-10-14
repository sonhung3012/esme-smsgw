/**
 * 
 */
package com.fis.esme.app;

import java.text.Collator;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.fis.esme.bo.EsmeServiceBo;
import com.fis.esme.bo.EsmeSmsLogBo;
import com.fis.esme.bo.EsmeEmsMtBo;
import com.fis.esme.language.languageTransferer;
import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.EsmeEmsMt;
import com.fis.esme.smslog.EsmeSmsLogTransferer;
import com.fis.esme.smsmt.SmsMtTransferer;
import com.fis.framework.dao.DaoFactory;
import com.fis.framework.service.ServiceLocator;

/**
 * @author Administrator
 * 
 */
public class Test {

	/**
	 * 
	 */
	public Test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// System.out.println(BusinessUtil.cutMSISDNStartsWidth(null));

		ServiceLocator.init();

		DaoFactory.init();

		try {
			
			EsmeEmsMtBo bo = ServiceLocator.createService(EsmeEmsMtBo.class);
			EsmeEmsMt t = new EsmeEmsMt();
			
//			EsmeSmsMtBo bo = ServiceLocator.createService(EsmeSmsMtBo.class);
//			EsmeServiceBo t = new EsmeSmsLogTransferer();
//			languageTransferer t = new languageTransferer();
//			EsmeSmsLog mt = new EsmeSmsLog();
//			mt.setRequestTime(Calendar.getInstance().getTime());
			
			System.out.println("ss>>>>>>>>>"+bo.findAll().size());

//			EsmeMessageBo bo = ServiceLocator
//					.createService(EsmeMessageBo.class);
//			
//			System.out.println("ket qua trước:  " + bo.findAll().size());
//			EsmeMessage se = new EsmeMessage();
//			se.setCode("TEST");
//			se.setDesciption("insert");
//			se.setName("test");
//			se.setStatus("1");
//			se.setLastModify(new Date());
//			
//			long a = bo.persist(se);
//			
//			System.out.println("ket qua trar ve:  " + a);
//
//			System.out.println("ket qua sau insert:  " + bo.findAll().size());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void testStuff() {
		List words = Arrays.asList("Đáng", "đáng", "đang", "đăng", "đắng",
				"đen");

		//
		// this won't sort the words properly, in German language
		//
		Collections.sort(words);

		System.out.println("wrong sorting: " + words);

		//
		// Define a collator for German language
		//
		Collator collator = Collator.getInstance(Locale.FRENCH);

		//
		// Sort the list using Collator
		//
		Collections.sort(words, collator);

		System.out.println("proper sorting with Collator: " + words);
	}

}
