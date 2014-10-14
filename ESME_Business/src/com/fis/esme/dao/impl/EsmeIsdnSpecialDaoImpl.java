package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeIsdnPermissionDao;
import com.fis.esme.dao.EsmeIsdnSpecialDao;
import com.fis.esme.persistence.EsmeIsdnPermission;
import com.fis.esme.persistence.EsmeIsdnSpecial;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.dao.DaoFactory;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeIsdnSpecialDaoImpl extends
		GenericDaoSpringHibernateTemplate<EsmeIsdnSpecial, String> implements
		EsmeIsdnSpecialDao {

	EsmeIsdnPermissionDao getEsmeIsdnPermissionDao() {
		EsmeIsdnPermissionDao esmeIsdnPermissionDao = DaoFactory
				.createDao(EsmeIsdnPermissionDao.class);
		return esmeIsdnPermissionDao;
	}

	@Override
	public void delete(EsmeIsdnSpecial persistentObject) throws Exception {
		EsmeIsdnPermission esmeIsdnPermission = new EsmeIsdnPermission();
		esmeIsdnPermission.setPermissionId(-1);
		esmeIsdnPermission.setEsmeIsdnSpecial(persistentObject);

		getEsmeIsdnPermissionDao().delete(esmeIsdnPermission);

		super.delete(persistentObject);
	}

	@Override
	public List<EsmeIsdnSpecial> findAll(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial) throws Exception {
		return findAll(searchEntity, esmeIsdnSpecial, null, false, -1, -1, true);
	}

	@Override
	public List<EsmeIsdnSpecial> findAll(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial, int firstItemIndex, int maxItems)
			throws Exception {
		return findAll(searchEntity, esmeIsdnSpecial, null, false,
				firstItemIndex, maxItems, true);
	}

	@Override
	public List<EsmeIsdnSpecial> findAll(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial, boolean exactMatch)
			throws Exception {
		return findAll(searchEntity, esmeIsdnSpecial, null, false, -1, -1,
				exactMatch);
	}

	@Override
	public List<EsmeIsdnSpecial> findAll(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return findAll(searchEntity, esmeIsdnSpecial, null, false,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public List<EsmeIsdnSpecial> findAll(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(searchEntity, esmeIsdnSpecial,
				sortedColumn, ascSorted, exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}
		return finder.list();

	}

	@Override
	public int count(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial, boolean exactMatch)
			throws Exception {
		Criteria counter = createCriteria(searchEntity, esmeIsdnSpecial, null,
				false, exactMatch);
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
			EsmeIsdnSpecial esmeIsdnSpecial) throws Exception {
		Criteria criteria = getSession().createCriteria(EsmeIsdnSpecial.class);
		if (!BusinessUtil.stringIsNullOrEmty(esmeIsdnSpecial.getMsisdn()))
			criteria.add(Expression.eq("msisdn", esmeIsdnSpecial.getMsisdn()));
		if (!BusinessUtil.stringIsNullOrEmty(esmeIsdnSpecial.getName()))
			criteria.add(Expression.eq("name", esmeIsdnSpecial.getName()));
		if (!BusinessUtil.stringIsNullOrEmty(esmeIsdnSpecial.getStatus()))
			criteria.add(Expression.eq("status", esmeIsdnSpecial.getStatus()));
		if (!BusinessUtil.stringIsNullOrEmty(esmeIsdnSpecial.getType()))
			criteria.add(Expression.eq("type", esmeIsdnSpecial.getType()));
		if (!BusinessUtil.stringIsNullOrEmty(esmeIsdnSpecial.getReason()))
			criteria.add(Expression.eq("reason", esmeIsdnSpecial.getReason()));
		criteria.setProjection(Projections.count("msisdn"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public int countAll() throws Exception {
		Criteria counter = getSession().createCriteria(EsmeIsdnSpecial.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public boolean checkConstraints(String id) throws Exception {
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
			EsmeIsdnSpecial esmeIsdnSpecial, String orderedColumn, boolean asc,
			boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeIsdnSpecial.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeIsdnSpecial != null) {

			if (!FieldChecker.isEmptyString(esmeIsdnSpecial.getMsisdn())) {
				String checkStartsWith = BusinessUtil
						.checkStartsWith(esmeIsdnSpecial.getMsisdn());
				if (checkStartsWith != null) {
					or.add(Expression.like("msisdn", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("msisdn",
								esmeIsdnSpecial.getMsisdn()).ignoreCase());
					} else {
						or.add(Restrictions.like("msisdn",
								"%" + esmeIsdnSpecial.getMsisdn() + "%")
								.ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(esmeIsdnSpecial.getName())) {
				String checkStartsWith = BusinessUtil
						.checkStartsWith(esmeIsdnSpecial.getName());
				if (checkStartsWith != null) {
					or.add(Expression.like("name", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("name",
								esmeIsdnSpecial.getName()).ignoreCase());
					} else {
						or.add(Restrictions.like("name",
								"%" + esmeIsdnSpecial.getName() + "%")
								.ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(esmeIsdnSpecial.getStatus())) {
				if (exactMatch) {
					or.add(Restrictions.eq("status",
							esmeIsdnSpecial.getStatus()).ignoreCase());
				} else {
					or.add(Restrictions.like("status",
							"%" + esmeIsdnSpecial.getStatus() + "%")
							.ignoreCase());
				}
			}

			if (!FieldChecker.isEmptyString(esmeIsdnSpecial.getType())) {
				if (exactMatch) {
					or.add(Restrictions.eq("type", esmeIsdnSpecial.getType())
							.ignoreCase());
				} else {
					or.add(Restrictions.like("type",
							"%" + esmeIsdnSpecial.getType() + "%").ignoreCase());
				}
			}

			if (!FieldChecker.isEmptyString(esmeIsdnSpecial.getReason())) {
				if (exactMatch) {
					or.add(Restrictions.eq("reason",
							esmeIsdnSpecial.getReason()).ignoreCase());
				} else {
					or.add(Restrictions.like("reason",
							"%" + esmeIsdnSpecial.getReason() + "%")
							.ignoreCase());
				}
			}
		}
		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeIsdnSpecial.class,
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
	public List<EsmeIsdnPermission> getPermissionByMisdn(String msisdn)
			throws Exception {

		String strSQL = "select * from ESME_ISDN_PERMISSION where MSISDN =:msisdn";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.setString("msisdn", msisdn);
		query.addEntity(EsmeIsdnPermission.class);

		return query.list();
	}

	@Override
	public void deletePermissionByMsisdn(String msisdn) throws Exception {

		String sql = " delete ESME_ISDN_PERMISSION where MSISDN = :msisdn ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setString("msisdn", msisdn);
		query.executeUpdate();
	}

	@Override
	public void updateSpecial(EsmeIsdnSpecial esmeIsdnSpecial, String oldMsisdn)
			throws Exception {

		String sql = " update ESME_ISDN_SPECIAL set MSISDN="
				+"'"+ esmeIsdnSpecial.getMsisdn() +"'"+ ", NAME="
				+"'"+ esmeIsdnSpecial.getName() +"'"+ ", STATUS="
				+"'"+ esmeIsdnSpecial.getStatus() +"'"+ ", TYPE="
				+"'"+ esmeIsdnSpecial.getType() +"'"+", REASON="
				+"'"+ esmeIsdnSpecial.getReason() +"'"+ " where MSISDN="+"'"+oldMsisdn+"'"+" ";
		SQLQuery query = getSession().createSQLQuery(sql);
//		query.setString("oldMsisdn", oldMsisdn);
		query.executeUpdate();
	}

}