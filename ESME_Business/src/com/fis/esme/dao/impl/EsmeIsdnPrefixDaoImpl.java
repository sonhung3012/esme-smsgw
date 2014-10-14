package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeIsdnPrefixDao;
import com.fis.esme.persistence.EsmeIsdnPrefix;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeIsdnPrefixDaoImpl extends
		GenericDaoSpringHibernateTemplate<EsmeIsdnPrefix, Long> implements
		EsmeIsdnPrefixDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeIsdnPrefix> findAll(EsmeIsdnPrefix esmeServices)
			throws Exception {
		return findAll(esmeServices, false);
	}

	public List<EsmeIsdnPrefix> findAll(EsmeIsdnPrefix esmeServices,
			int firstItemIndex, int maxItems) throws Exception {
		return findAll(esmeServices, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeIsdnPrefix esmeServices,
			String orderedColumn, boolean asc, boolean exactMatch)
			throws Exception {

		Criteria finder = getSession().createCriteria(EsmeIsdnPrefix.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeServices != null) {
			Long id = esmeServices.getPrefixId();
			String name = esmeServices.getPrefixValue();
			String status = esmeServices.getStatus();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("prefixId", id).ignoreCase());
				} else {
					or.add(Restrictions.like("prefixId", "%" + id + "%"));
				}
			}

			if (!FieldChecker.isEmptyString(name)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(name);
				if (checkStartsWith != null) {
					or.add(Expression.like("prefixValue", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("prefixValue", name)
								.ignoreCase());
					} else {
						or.add(Restrictions.like("prefixValue",
								"%" + name + "%").ignoreCase());
					}
				}
			}

//			if (!FieldChecker.isEmptyString(status)) {
//				String checkStartsWith = BusinessUtil.checkStartsWith(status);
//				if (checkStartsWith != null) {
//					or.add(Expression.like("status", checkStartsWith,
//							MatchMode.START).ignoreCase());
//				} else {
//					or.add(Restrictions.eq("status", status));
//				}
//			}
		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeIsdnPrefix.class,
						orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<EsmeIsdnPrefix> findAll(EsmeIsdnPrefix esmeServices,
			boolean exactMatch) throws Exception {
		Criteria finder = createCriteria(esmeServices, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeIsdnPrefix> findAll(EsmeIsdnPrefix esmeServices,
			int firstItemIndex, int maxItems, boolean exactMatch)
			throws Exception {
		return findAll(esmeServices, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public int count(EsmeIsdnPrefix esmeServices, boolean exactMatch)
			throws Exception {
		Criteria counter = createCriteria(esmeServices, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public List<EsmeIsdnPrefix> findAll(EsmeIsdnPrefix esmeServices,
			String sortedColumn, boolean ascSorted, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		Criteria finder = createCriteria(esmeServices, sortedColumn, ascSorted,
				exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int checkExited(EsmeIsdnPrefix esmeServices) throws Exception {

		// Criteria criteria =
		// getSession().createCriteria(EsmeIsdnPrefix.class);
		// criteria.add(Expression.eq("prefixValue",
		// esmeServices.getPrefixValue()));
		// criteria.setProjection(Projections.count("prefix_id"));
		// return (Integer) criteria.uniqueResult();

		String strSQL = "select count(*) total from ESME_ISDN_PREFIX where "
				+ "PREFIX_VALUE=:prefixValue ";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.setString("prefixValue", esmeServices.getPrefixValue());
//		query.setString("status", esmeServices.getStatus());
		query.addScalar("total", Hibernate.INTEGER);
		Integer size = (Integer) query.uniqueResult();
		return size;
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeIsdnPrefix obj = new EsmeIsdnPrefix();
		obj.setPrefixId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeIsdnPrefix", obj));
			criteria.setProjection(Projections.count("esmeIsdnPrefix"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {
		Criteria counter = getSession().createCriteria(EsmeIsdnPrefix.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public Long persist(EsmeIsdnPrefix bk) throws Exception {
		// insert into t_emp values (emp_no_seq.nexval, 'Joe Black');
		String strSQL = "insert into ESME_ISDN_PREFIX(PREFIX_ID,PREFIX_VALUE,STATUS) "
				+ "values (ESME_ISDN_PREFIX_SEQ.nextval,:prefixValue,:status)";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.setString("prefixValue", bk.getPrefixValue());
		query.setString("status", bk.getStatus());
		return (long) query.executeUpdate();
	}

}