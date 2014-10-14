package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeIsdnPermissionDao;
import com.fis.esme.persistence.EsmeIsdnPermission;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeIsdnPermissionDaoImpl extends
		GenericDaoSpringHibernateTemplate<EsmeIsdnPermission, Long> implements
		EsmeIsdnPermissionDao {

	@Override
	public void delete(EsmeIsdnPermission persistentObject) throws Exception {
		if (persistentObject != null && persistentObject.getPermissionId() <= 0
				&& persistentObject.getEsmeIsdnSpecial() != null
				&& persistentObject.getType() != null) {

			String sql = " DELETE ESME_ISDN_PERMISSION WHERE ESME_ISDN_PERMISSION.MSISDN = :MSISDN AND ESME_ISDN_PERMISSION.TYPE = :TYPE ";
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setString("MSISDN", persistentObject.getEsmeIsdnSpecial()
					.getMsisdn());
			query.setString("TYPE", persistentObject.getType());
			query.executeUpdate();

		} else if (persistentObject != null
				&& persistentObject.getPermissionId() <= 0
				&& persistentObject.getEsmeIsdnSpecial() != null) {

			String sql = " DELETE ESME_ISDN_PERMISSION WHERE ESME_ISDN_PERMISSION.MSISDN = :MSISDN ";
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setString("MSISDN", persistentObject.getEsmeIsdnSpecial()
					.getMsisdn());
			query.executeUpdate();

		} else
			super.delete(persistentObject);
	}

	@Override
	public List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission) throws Exception {
		return findAll(searchEntity, esmeIsdnPermission, null, false, -1, -1,
				true);
	}

	@Override
	public List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, int firstItemIndex,
			int maxItems) throws Exception {
		return findAll(searchEntity, esmeIsdnPermission, null, false,
				firstItemIndex, maxItems, true);
	}

	@Override
	public List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, boolean exactMatch)
			throws Exception {
		return findAll(searchEntity, esmeIsdnPermission, null, false, -1, -1,
				exactMatch);
	}

	@Override
	public List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(searchEntity, esmeIsdnPermission, null, false,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(searchEntity, esmeIsdnPermission,
				sortedColumn, ascSorted, exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}
		return finder.list();

	}

	@Override
	public int count(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, boolean exactMatch)
			throws Exception {
		Criteria counter = createCriteria(searchEntity, esmeIsdnPermission,
				null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public int checkExited(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission) throws Exception {
		Criteria criteria = getSession().createCriteria(
				EsmeIsdnPermission.class);

		if (esmeIsdnPermission.getEsmeIsdnSpecial() != null)
			criteria.add(Expression.eq("esmeIsdnSpecial",
					esmeIsdnPermission.getEsmeIsdnSpecial()));
		if (esmeIsdnPermission.getEsmeServices() != null)
			criteria.add(Expression.eq("esmeServices",
					esmeIsdnPermission.getEsmeServices()));
		if (!BusinessUtil.stringIsNullOrEmty(esmeIsdnPermission.getType()))
			criteria.add(Expression.eq("type", esmeIsdnPermission.getType()));
		criteria.setProjection(Projections.count("permissionId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public int countAll() throws Exception {
		Criteria counter = getSession()
				.createCriteria(EsmeIsdnPermission.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		// EsmeIsdnSpecial obj = new EsmeIsdnSpecial();
		// obj.setMsisdn(id);
		//
		// Criteria criteria = null;
		// Session session = getSession();
		// ;
		// int i = 0;
		//
		// Class[] cls = new Class[] {};
		//
		// for (Class c : cls) {
		// criteria = session.createCriteria(c);
		// criteria.add(Expression.eq("esmeSmsRoutings", obj));
		// criteria.setProjection(Projections.count("esmeSmsRoutings"));
		// i += (Integer) criteria.uniqueResult();
		// if (i > 0)
		// return true;
		// }
		return false;
	}

	private Criteria createCriteria(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, String orderedColumn,
			boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeIsdnPermission.class);
		Disjunction or = Restrictions.disjunction();

		if (searchEntity != null
				&& searchEntity.getSwitchCase().equals("CASE-END")) {
			if (!FieldChecker.isEmptyString(esmeIsdnPermission.getType())) {
				finder.add(Restrictions
						.eq("type", esmeIsdnPermission.getType()).ignoreCase());
			}

			if (esmeIsdnPermission.getEsmeIsdnSpecial() != null) {
				finder.add(Restrictions.eq("esmeIsdnSpecial",
						esmeIsdnPermission.getEsmeIsdnSpecial()));
			}

			if (esmeIsdnPermission.getEsmeServices() != null) {
				finder.add(Restrictions.eq("esmeServices",
						esmeIsdnPermission.getEsmeServices()));
			}
		}

		else if (esmeIsdnPermission != null) {

			// if (!FieldChecker.isEmptyString(esmeIsdnSpecial.getMsisdn())) {
			// String checkStartsWith = BusinessUtil
			// .checkStartsWith(esmeIsdnSpecial.getMsisdn());
			// if (checkStartsWith != null) {
			// or.add(Expression.like("msisdn", checkStartsWith,
			// MatchMode.START).ignoreCase());
			// } else {
			// if (exactMatch) {
			// or.add(Restrictions.eq("msisdn",
			// esmeIsdnSpecial.getMsisdn()).ignoreCase());
			// } else {
			// or.add(Restrictions.like("msisdn",
			// "%" + esmeIsdnSpecial.getMsisdn() + "%")
			// .ignoreCase());
			// }
			// }
			// }

			if (!FieldChecker.isEmptyString(esmeIsdnPermission.getType())) {
				or.add(Restrictions.eq("type", esmeIsdnPermission.getType())
						.ignoreCase());
			}

			if (esmeIsdnPermission.getEsmeIsdnSpecial() != null) {
				or.add(Restrictions.eq("esmeIsdnSpecial",
						esmeIsdnPermission.getEsmeIsdnSpecial()));
			}

			if (esmeIsdnPermission.getEsmeServices() != null) {
				or.add(Restrictions.eq("esmeServices",
						esmeIsdnPermission.getEsmeServices()));
			}
			finder.add(or);
		}

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeIsdnPermission.class,
						orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;
	}

}