package com.fis.esme.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.ApParamDao;
import com.fis.esme.persistence.ApParam;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class ApParamDaoImpl extends
		GenericDaoSpringHibernateTemplate<ApParam, String> implements
		ApParamDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ApParam> findAll(ApParam apParam) throws Exception {
		return findAll(apParam, false);
	}

	public List<ApParam> findAll(ApParam apParam, int firstItemIndex,
			int maxItems) throws Exception {
		return findAll(apParam, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(ApParam apParam, String orderedColumn,
			boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(ApParam.class);
		Disjunction or = Restrictions.disjunction();
		if (apParam != null) {
			String type = apParam.getParType();
			String name = apParam.getParName();
			String value = apParam.getParValue();

			if (!FieldChecker.isEmptyString(type)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(type);
				if (checkStartsWith != null) {
					or.add(Expression.like("parType", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("parType", type).ignoreCase());
					} else {
						or.add(Restrictions.like("parType", "%" + type + "%")
								.ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(name)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(name);
				if (checkStartsWith != null) {
					or.add(Expression.like("parName", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("parName", name).ignoreCase());
					} else {
						or.add(Restrictions.like("parName", "%" + name + "%")
								.ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(value)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(value);
				if (checkStartsWith != null) {
					or.add(Expression.like("parValue", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("parValue", value).ignoreCase());
					} else {
						or.add(Restrictions.like("parValue", "%" + value + "%")
								.ignoreCase());
					}
				}
			}

		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker
						.classContainsField(ApParam.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<ApParam> findAll(ApParam apParam, boolean exactMatch)
			throws Exception {
		Criteria finder = createCriteria(apParam, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<ApParam> findAll(ApParam apParam, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(apParam, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public int count(ApParam apParam, boolean exactMatch) throws Exception {
		// Criteria counter = createCriteria(apParam, null, false, exactMatch);
		// counter.setProjection(Projections.rowCount());
		// List re = counter.list();
		//
		// if (re.size() < 1) {
		// return 0;
		// } else {
		// return (Integer) re.get(0);
		// }
		String strSQL = "select count(*) from AP_PARAM";
		if (apParam != null) {
			if (apParam.getParType() != null) {
				
				String checkStartsWith = BusinessUtil.checkStartsWith(apParam
						.getParType());
				if (checkStartsWith != null) {
					strSQL += " where UPPER(PAR_TYPE) like '"
							+ checkStartsWith + "%' ";
				} else {
				strSQL += " where UPPER(PAR_TYPE) like '%"
						+ apParam.getParType() + "%' ";
				}
			}
			if (apParam.getParName() != null && apParam.getParType() == null) {
				strSQL += " where UPPER(PAR_NAME) like '%"
						+ apParam.getParName() + "%' ";
			}
			if (apParam.getParName() != null && apParam.getParType() != null) {
				strSQL += " where UPPER(PAR_NAME) like '%"
						+ apParam.getParName()
						+ "%' and UPPER(PAR_TYPE) like '%"
						+ apParam.getParType() + "%'  ";
			}
		}
		SQLQuery query = getSession().createSQLQuery(strSQL);
		// query.addEntity(ApParam.class);
		List re = query.list();

		if (re.size() < 1) {
			return 0;
		} else {
//			BigDecimal de = (BigDecimal) re.get(0);
//			String s = String.valueOf(de);
			Integer i = (Integer) re.get(0);
			return i;
		}
	}

	@Override
	public List<ApParam> findAll(ApParam apParam, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// Criteria finder = createCriteria(apParam, sortedColumn, ascSorted,
		// exactMatch);
		// finder.setFirstResult(firstItemIndex);
		// finder.setMaxResults(maxItems);

		String strSQL = "SELECT * from AP_PARAM ";
		// if ((sortedColumn != null)) {
		// strSQL += " ORDER BY "+sortedColumn;
		// if (!ascSorted) {
		// strSQL += " DESC";
		// }
		// }
		if (apParam != null) {
			if (apParam.getParType() != null) {
				String checkStartsWith = BusinessUtil.checkStartsWith(apParam
						.getParType());
				if (checkStartsWith != null) {
					strSQL += " where UPPER(PAR_TYPE) like '"
							+ checkStartsWith + "%' ";
				} else {
					strSQL += " where UPPER(PAR_TYPE) like '%"
							+ apParam.getParType() + "%' ";
				}
			}
			if (apParam.getParName() != null && apParam.getParType() == null) {
				strSQL += " where UPPER(PAR_NAME) like '%"
						+ apParam.getParName() + "%' ";
			}
			if (apParam.getParName() != null && apParam.getParType() != null) {
				strSQL += " where UPPER(PAR_NAME) like '%"
						+ apParam.getParName()
						+ "%' and UPPER(PAR_TYPE) like '%"
						+ apParam.getParType() + "%'  ";
			}
		}

		SQLQuery query = getSession().createSQLQuery(strSQL);
		// query.addEntity(ApParam.class);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			query = (SQLQuery) query.setFirstResult(firstItemIndex);
			query.setMaxResults(maxItems);
		}

		List<Object> l = query.list();

		List<ApParam> list = new ArrayList<ApParam>();
		for (Object m : l) {
			Object[] arrObj = (Object[]) m;
			ApParam mPB = new ApParam();
			mPB.setParType(arrObj[0].toString());
			mPB.setParName(arrObj[1].toString());
			mPB.setParValue(arrObj[2].toString());
			if (arrObj[3] != null && !arrObj[3].equals("")) {
				mPB.setDescription(arrObj[3].toString());
			}

			list.add(mPB);
		}

		return list;
	}

	@Override
	public int checkExited(ApParam apParam) throws Exception {

		Criteria criteria = getSession().createCriteria(ApParam.class);
		criteria.add(Expression.eq("parType", apParam.getParType()));
		criteria.add(Expression.eq("parName", apParam.getParName()));
		criteria.setProjection(Projections.count("parType"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(String id) throws Exception {

		ApParam obj = new ApParam();
		obj.setParType(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("apParam", obj));
			criteria.setProjection(Projections.count("apParam"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {
		Criteria counter = getSession().createCriteria(ApParam.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public void delete(ApParam persistentObject) throws Exception {

		Session session = getSession();
		String sqlString = " delete from AP_PARAM where PAR_TYPE= :parType and PAR_NAME= :parName";
		SQLQuery query = session.createSQLQuery(sqlString);
		query.setString("parType", persistentObject.getParType());
		query.setString("parName", persistentObject.getParName());
		query.executeUpdate();
	}

}