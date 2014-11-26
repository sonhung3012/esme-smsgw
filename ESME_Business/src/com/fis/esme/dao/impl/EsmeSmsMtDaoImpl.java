package com.fis.esme.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeSmsMtDao;
import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsMt;
import com.fis.esme.persistence.EsmeSmsRouting;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;
import com.fis.framework.service.ServiceLocator;

public class EsmeSmsMtDaoImpl extends GenericDaoSpringHibernateTemplate<EsmeSmsMt, Long> implements EsmeSmsMtDao {

	boolean stopUpload = false;

	public void stopUploadFile() {

		stopUpload = true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeSmsMt> findAll(EsmeSmsMt esmeServices) throws Exception {

		return findAll(esmeServices, false);
	}

	public List<EsmeSmsMt> findAll(EsmeSmsMt esmeServices, int firstItemIndex, int maxItems) throws Exception {

		return findAll(esmeServices, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeSmsMt esmeServices, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeSmsMt.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeServices != null) {

		}

		finder.add(or);

		if (orderedColumn != null && FieldChecker.classContainsField(EsmeSmsMt.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<EsmeSmsMt> findAll(EsmeSmsMt esmeServices, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeServices, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeSmsMt> findAll(EsmeSmsMt esmeServices, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(esmeServices, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeSmsMt esmeServices, boolean exactMatch) throws Exception {

		// Criteria counter = createCriteria(esmeServices, null, false,
		// exactMatch);
		// counter.setProjection(Projections.rowCount());
		// List re = counter.list();
		String strSQL = "select count(*) from ESME_SERVICES";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		// query.addEntity(ApParam.class);
		List re = query.list();

		if (re.size() < 1) {
			return 0;
		} else {
			BigDecimal de = (BigDecimal) re.get(0);
			String s = String.valueOf(de);
			Integer i = Integer.parseInt(s);
			return i;
		}
	}

	@Override
	public List<EsmeSmsMt> findAll(EsmeSmsMt esmeServices, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		// Criteria finder = createCriteria(esmeServices, sortedColumn,
		// ascSorted,
		// exactMatch);
		// if (firstItemIndex >= 0 && maxItems >= 0) {
		// finder.setFirstResult(firstItemIndex);
		// finder.setMaxResults(maxItems);
		// }
		String strSQL = "SELECT * from ESME_SERVICES";
		if (esmeServices != null) {}

		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.addEntity(EsmeSmsMt.class);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			query = (SQLQuery) query.setFirstResult(firstItemIndex);
			query.setMaxResults(maxItems);
		}

		return query.list();
	}

	@Override
	public int checkExited(EsmeSmsMt esmeServices) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeSmsMt.class);
		criteria.setProjection(Projections.count("serviceId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeSmsMt obj = new EsmeSmsMt();
		obj.setMtId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeServices", obj));
			criteria.setProjection(Projections.count("esmeServices"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {

		Criteria counter = getSession().createCriteria(EsmeSmsMt.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public List<EsmeSmsRouting> FindBySmsRouting(EsmeSmsRouting esmeSmsRouting) throws Exception {

		String strSQL = "SELECT * from ESME_SMS_ROUTING where SERVICE_ID = " + esmeSmsRouting.getEsmeServices().getServiceId() + " ";
		if (esmeSmsRouting != null) {
			if (esmeSmsRouting.getEsmeCp() != null) {
				strSQL += "and CP_ID = " + esmeSmsRouting.getEsmeCp().getCpId() + " ";
			}
			if (esmeSmsRouting.getEsmeShortCode() != null) {
				strSQL += "and SHORT_CODE_ID = " + esmeSmsRouting.getEsmeShortCode().getShortCodeId() + " ";
			}
			if (esmeSmsRouting.getEsmeSmsCommand() != null) {
				strSQL += "and COMMAND_ID = " + esmeSmsRouting.getEsmeSmsCommand().getCommandId() + " ";
			}

		}

		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.addEntity(EsmeSmsRouting.class);

		return query.list();
	}

	@Override
	public List<EsmeCp> findByCP(String id) throws Exception {

		String strSQL = "SELECT * from ESME_CP where CP_ID in (" + id + ") ";

		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.addEntity(EsmeCp.class);

		return query.list();
	}

	@Override
	public List<EsmeSmsCommand> findByCommand(String id) throws Exception {

		String strSQL = "SELECT * from ESME_SMS_COMMAND where COMMAND_ID in (" + id + ") ";

		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.addEntity(EsmeSmsCommand.class);

		return query.list();
	}

	@Override
	public List<EsmeShortCode> findByShortCode(String id) throws Exception {

		String strSQL = "SELECT * from ESME_SHORT_CODE where SHORT_CODE_ID in (" + id + ") ";

		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.addEntity(EsmeShortCode.class);

		return query.list();
	}

	@Override
	public void deleteByFileUploadId(long fileUploadId) throws Exception {

		Session session = getSession();
		String sqlString = " delete from ESME_SMS_MT where FILE_UPLOAD_ID = :fileUploadId";
		SQLQuery query = session.createSQLQuery(sqlString);
		query.setLong("fileUploadId", fileUploadId);
		query.executeUpdate();
	}

	@Override
	public long addMultiProcess(List<EsmeSmsMt> esmeSmsMt) throws Exception {

		Session session;
		SessionFactory sessionFactory = (SessionFactory) ServiceLocator.getBean("sessionFactory");
		session = sessionFactory.openSession();
		// Transaction tx = session.beginTransaction();
		stopUpload = false;
		long total = 0;
		for (int i = 0; i < esmeSmsMt.size(); i++) {

			if (stopUpload) {
				session.beginTransaction();
				session.getTransaction().commit();
				session.close();
				break;
			}
			try {
				EsmeSmsMt smsMt = esmeSmsMt.get(i);
				String sqlSelectID = "SELECT NEXT VALUE FOR SMS_MT_SEQ";
				SQLQuery query = session.createSQLQuery(sqlSelectID);
				// query.addScalar("ID", Hibernate.LONG);
				Long id = Long.parseLong(String.valueOf(query.uniqueResult()));
				smsMt.setMtId(id);
				smsMt.setStatus("0");
				session.save(smsMt);
				total++;
				if (i % 500 == 0) { // 20, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
					session.beginTransaction();
					session.getTransaction().commit();
				}
			} catch (Exception e) {
				e.printStackTrace();
				total--;
			}

		}
		// tx.commit();

		if (session.isConnected()) {
			session.beginTransaction();
			session.getTransaction().commit();
			session.close();
		}
		return total;
	}

	@Override
	public void stopUpload() throws Exception {

		stopUploadFile();
	}
}