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

import com.fis.esme.dao.EsmeCPDao;
import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeCPDaoImpl extends GenericDaoSpringHibernateTemplate<EsmeCp, Long> implements EsmeCPDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeCp> findAll(EsmeCp esmeCp) throws Exception {

		return findAll(esmeCp, false);
	}

	public List<EsmeCp> findAll(EsmeCp esmeCp, int firstItemIndex, int maxItems) throws Exception {

		return findAll(esmeCp, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeCp esmeCp, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeCp.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeCp != null) {
			Long id = esmeCp.getCpId();
			String code = esmeCp.getCode();
			String description = esmeCp.getDesciption();
			String status = esmeCp.getStatus();
			String shortCode = esmeCp.getDefaultShortCode();
			String username = esmeCp.getUsername();
			String receiveUsername = esmeCp.getReceiveUsername();
			String receiveUrlMsg = esmeCp.getReceiveUrlMsg();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("cpId", id));
				} else {
					or.add(Restrictions.like("cpId", "%" + id + "%"));
				}
			}

			if (!FieldChecker.isEmptyString(username)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(username);
				if (checkStartsWith != null) {
					or.add(Expression.like("username", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("username", username).ignoreCase());
					} else {
						or.add(Restrictions.like("username", "%" + username + "%").ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(shortCode)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(shortCode);
				if (checkStartsWith != null) {
					or.add(Expression.like("defaultShortCode", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (!exactMatch) {
						or.add(Restrictions.eq("defaultShortCode", shortCode).ignoreCase());
					} else {
						or.add(Restrictions.like("defaultShortCode", "%" + shortCode + "%").ignoreCase());
					}
				}
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

			if (!FieldChecker.isEmptyString(receiveUsername)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(receiveUsername);
				if (checkStartsWith != null) {
					or.add(Expression.like("receiveUsername", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("receiveUsername", receiveUsername).ignoreCase());
					} else {
						or.add(Restrictions.like("receiveUsername", "%" + receiveUsername + "%").ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(receiveUrlMsg)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(receiveUrlMsg);
				if (checkStartsWith != null) {
					or.add(Expression.like("receiveUrlMsg", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("receiveUrlMsg", receiveUrlMsg).ignoreCase());
					} else {
						or.add(Restrictions.like("receiveUrlMsg", "%" + receiveUrlMsg + "%").ignoreCase());
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

		if (orderedColumn != null && FieldChecker.classContainsField(EsmeCp.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<EsmeCp> findAll(EsmeCp esmeCp, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeCp, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeCp> findAll(EsmeCp esmeCp, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(esmeCp, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeCp esmeCp, boolean exactMatch) throws Exception {

		Criteria counter = createCriteria(esmeCp, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public List<EsmeCp> findAll(EsmeCp esmeCp, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(esmeCp, sortedColumn, ascSorted, exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int checkExited(EsmeCp esmeCp) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeCp.class);
		criteria.add(Expression.eq("code", esmeCp.getCode()));
		criteria.setProjection(Projections.count("cpId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeCp obj = new EsmeCp();
		obj.setCpId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeCp", obj));
			criteria.setProjection(Projections.count("esmeCp"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {

		Criteria counter = getSession().createCriteria(EsmeCp.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

}