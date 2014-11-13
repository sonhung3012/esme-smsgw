package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeShortCodeDao;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeShortCodeDaoImpl extends GenericDaoSpringHibernateTemplate<EsmeShortCode, Long> implements EsmeShortCodeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeShortCode> findAll(EsmeShortCode esmeShortCode) throws Exception {

		return findAll(esmeShortCode, false);
	}

	public List<EsmeShortCode> findAll(EsmeShortCode esmeShortCode, int firstItemIndex, int maxItems) throws Exception {

		return findAll(esmeShortCode, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeShortCode esmeShortCode, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeShortCode.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeShortCode != null) {
			Long id = esmeShortCode.getShortCodeId();
			String code = esmeShortCode.getCode();
			String status = esmeShortCode.getStatus();
			String description = esmeShortCode.getDesciption();
			Long price = esmeShortCode.getPrice();
			Byte mtFreeNumber = esmeShortCode.getMtFreeNumber();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("shortCodeId", id));
				} else {
					or.add(Restrictions.like("shortCodeId", "%" + id + "%"));
				}
			}

			if (!FieldChecker.isEmptyString(description)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(description);
				if (checkStartsWith != null) {
					or.add(Expression.like("desciption", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("desciption", description).ignoreCase());
					} else {
						or.add(Restrictions.like("desciption", "%" + description + "%").ignoreCase());
					}
				}
			}

			if (price != null && !FieldChecker.isEmptyString(String.valueOf(price))) {
				or.add(Restrictions.eq("price", price));
			}

			if (mtFreeNumber != null && !FieldChecker.isEmptyString(String.valueOf(mtFreeNumber))) {
				or.add(Restrictions.eq("mtFreeNumber", mtFreeNumber));
			}

			if (!FieldChecker.isEmptyString(code)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(code);
				if (checkStartsWith != null) {
					or.add(Expression.like("code", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("code", code).ignoreCase());
					} else {
						or.add(Restrictions.like("code", "%" + code + "%").ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(status)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(status);
				if (checkStartsWith != null) {
					or.add(Expression.like("status", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					or.add(Restrictions.eq("status", status));
				}
			}
		}

		finder.add(or);

		if (orderedColumn != null && FieldChecker.classContainsField(EsmeShortCode.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<EsmeShortCode> findAll(EsmeShortCode esmeShortCode, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeShortCode, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeShortCode> findAll(EsmeShortCode esmeShortCode, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(esmeShortCode, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeShortCode esmeShortCode, boolean exactMatch) throws Exception {

		Criteria counter = createCriteria(esmeShortCode, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public List<EsmeShortCode> findAll(EsmeShortCode esmeShortCode, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeShortCode, sortedColumn, ascSorted, exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int checkExited(EsmeShortCode esmeShortCode) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeShortCode.class);
		criteria.add(Expression.eq("code", esmeShortCode.getCode()));
		criteria.setProjection(Projections.count("shortCodeId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeShortCode obj = new EsmeShortCode();
		obj.setShortCodeId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeShortcode", obj));
			criteria.setProjection(Projections.count("esmeShortcode"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {

		Criteria counter = getSession().createCriteria(EsmeShortCode.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

}