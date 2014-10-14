package com.fis.esme.dao.impl;

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

import com.fis.esme.dao.EsmeSmscDao;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.EsmeSmscParam;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeSmscDaoImpl extends
		GenericDaoSpringHibernateTemplate<EsmeSmsc, Long> implements
		EsmeSmscDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeSmsc> findAll(EsmeSmsc esmeServices) throws Exception {
		return findAll(esmeServices, false);
	}

	public List<EsmeSmsc> findAll(EsmeSmsc esmeServices, int firstItemIndex,
			int maxItems) throws Exception {
		return findAll(esmeServices, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeSmsc esmeServices,
			String orderedColumn, boolean asc, boolean exactMatch)
			throws Exception {

		Criteria finder = getSession().createCriteria(EsmeSmsc.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeServices != null) {
			Long id = esmeServices.getSmscId();
			String name = esmeServices.getName();
			String code = esmeServices.getCode();
			String des = esmeServices.getDesciption();
			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("smscId", id));
				} else {
					or.add(Restrictions.like("smscId", "%" + id + "%"));
				}
			}

			if (!FieldChecker.isEmptyString(des)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(des);
				if (checkStartsWith != null) {
					or.add(Expression.like("desciption", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("desciption", des).ignoreCase());
					} else {
						or.add(Restrictions.like("desciption", "%" + des + "%")
								.ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(name)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(name);
				if (checkStartsWith != null) {
					or.add(Expression.like("name", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("name", name).ignoreCase());
					} else {
						or.add(Restrictions.like("name", "%" + name + "%")
								.ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(code)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(name);
				if (checkStartsWith != null) {
					or.add(Expression.like("code", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("code", name).ignoreCase());
					} else {
						or.add(Restrictions.like("code", "%" + name + "%")
								.ignoreCase());
					}
				}
			}

			// if (!FieldChecker.isEmptyString(status)) {
			// String checkStartsWith = BusinessUtil.checkStartsWith(status);
			// if (checkStartsWith != null) {
			// or.add(Expression.like("status", checkStartsWith,
			// MatchMode.START).ignoreCase());
			// } else {
			// or.add(Restrictions.eq("status", status));
			// }
			// }
		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeSmsc.class,
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
	public List<EsmeSmsc> findAll(EsmeSmsc esmeServices, boolean exactMatch)
			throws Exception {
		Criteria finder = createCriteria(esmeServices, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeSmsc> findAll(EsmeSmsc esmeServices, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(esmeServices, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public int count(EsmeSmsc esmeServices, boolean exactMatch)
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
	public List<EsmeSmsc> findAll(EsmeSmsc esmeServices, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		Criteria finder = createCriteria(esmeServices, sortedColumn, ascSorted,
				exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		// List<Object> l = finder.list();
		//
		// List<EsmeSmsc> list = new ArrayList<EsmeSmsc>();
		// for (Object m : l) {
		// Object[] arrObj = (Object[]) m;
		// EsmeSmsc mPB = new EsmeSmsc();
		// mPB.setName(arrObj[0].toString());
		// mPB.setCode(arrObj[1].toString());
		// mPB.setDefaulShortCode(arrObj[2].toString());
		// mPB.setStatus(arrObj[3].toString());
		// if (arrObj[4] != null && !arrObj[4].equals("")) {
		// mPB.setDesciption(arrObj[4].toString());
		// }
		// mPB.setClassName(arrObj[5].toString());
		// mPB.setStartupType(arrObj[6].toString());
		// list.add(mPB);
		// }

		return finder.list();
	}

	@Override
	public int checkExited(EsmeSmsc esmeServices) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeSmsc.class);
		if (!BusinessUtil.stringIsNullOrEmty(esmeServices.getCode()))
			criteria.add(Expression.eq("code", esmeServices.getCode()));
		if (!BusinessUtil.stringIsNullOrEmty(esmeServices.getName()))
			criteria.add(Expression.eq("name", esmeServices.getName()));
		criteria.setProjection(Projections.count("smscId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeSmsc obj = new EsmeSmsc();
		obj.setSmscId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeSmsc", obj));
			criteria.setProjection(Projections.count("esmeSmsc"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {
		Criteria counter = getSession().createCriteria(EsmeSmsc.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public List<EsmeSmscParam> addCopyDataParam(EsmeSmsc esmeServices)
			throws Exception {
		// Criteria criteria = getSession().createCriteria(EsmeSmscParam.class);
		// criteria.add(Restrictions.eq("smscId", esmeServices));
		// List<EsmeSmscParam> lst = (List<EsmeSmscParam>) criteria.list();
		String str = "Select * from esme_smsc_param where smsc_id="
				+ esmeServices.getSmscId();
		SQLQuery query = getSession().createSQLQuery(str);

		List lst = query.list();
		List<EsmeSmscParam> lstRs = new ArrayList<EsmeSmscParam>();
		if (lst.size() > 0 && lst != null) {
			for (int i = 0; i < lst.size(); i++) {
				Object[] val = (Object[]) lst.get(i);
				EsmeSmscParam rs = new EsmeSmscParam();
				EsmeSmsc smsc = new EsmeSmsc();
				smsc.setSmscId(Long.parseLong(val[0].toString()));
				rs.setSmscId(smsc);
				rs.setName(val[1].toString());
				rs.setValue(val[2].toString());
				lstRs.add(rs);
			}
			return lstRs;
		}

		return null;
	}

}