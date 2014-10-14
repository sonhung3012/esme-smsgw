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

import com.fis.esme.dao.EsmeMessageDao;
import com.fis.esme.persistence.EsmeMessage;
import com.fis.esme.persistence.EsmeMessageContent;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeMessageDaoImpl extends
		GenericDaoSpringHibernateTemplate<EsmeMessage, Long> implements
		EsmeMessageDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeMessage> findAll(EsmeMessage esmeMessage) throws Exception {
		return findAll(esmeMessage, false);
	}

	public List<EsmeMessage> findAll(EsmeMessage esmeMessage, int firstItemIndex,
			int maxItems) throws Exception {
		return findAll(esmeMessage, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeMessage esmeMessage,
			String orderedColumn, boolean asc, boolean exactMatch)
			throws Exception {

		Criteria finder = getSession().createCriteria(EsmeMessage.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeMessage != null) {
			Long id = esmeMessage.getMessageId();
			String name = esmeMessage.getName();
			String code = esmeMessage.getCode();
			String description =esmeMessage.getDesciption();
			String status = esmeMessage.getStatus();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("messageId", id));
				} else {
					or.add(Restrictions.like("messageId", "%" + id + "%"));
				}
			}
			
			if (!FieldChecker.isEmptyString(code)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(code);
				if (checkStartsWith != null) {
					or.add(Expression.like("code", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("code", code).ignoreCase());
					} else {
						or.add(Restrictions.like("code", "%" + code + "%")
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
			
			if (!FieldChecker.isEmptyString(description)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(description);
				if (checkStartsWith != null) {
					or.add(Expression.like("description", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("description", description).ignoreCase());
					} else {
						or.add(Restrictions.like("description", "%" + description + "%")
								.ignoreCase());
					}
				}
			}

			if (!FieldChecker.isEmptyString(status)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(status);
				if (checkStartsWith != null) {
					or.add(Expression.like("status", checkStartsWith,
							MatchMode.START).ignoreCase());
				} else {
					or.add(Restrictions.eq("status", status));
				}
			}
		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeMessage.class,
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
	public List<EsmeMessage> findAll(EsmeMessage esmeMessage, boolean exactMatch)
			throws Exception {
		Criteria finder = createCriteria(esmeMessage, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeMessage> findAll(EsmeMessage esmeMessage, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(esmeMessage, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public int count(EsmeMessage esmeMessage, boolean exactMatch)
			throws Exception {
		Criteria counter = createCriteria(esmeMessage, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public List<EsmeMessage> findAll(EsmeMessage esmeMessage, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		Criteria finder = createCriteria(esmeMessage, sortedColumn, ascSorted,
				exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int checkExited(EsmeMessage esmeMessage) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeMessage.class);
		criteria.add(Expression.eq("code", esmeMessage.getCode()));
		criteria.setProjection(Projections.count("messageId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeMessage obj = new EsmeMessage();
		obj.setMessageId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] { EsmeMessageContent.class };

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeMessage", obj));
			criteria.setProjection(Projections.count("esmeMessage"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {
		Criteria counter = getSession().createCriteria(EsmeMessage.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

}