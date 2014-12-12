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

import com.fis.esme.dao.EsmeSmsCommandDao;
import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.EsmeSmsRouting;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeSmsCommandDaoImpl extends GenericDaoSpringHibernateTemplate<EsmeSmsCommand, Long> implements EsmeSmsCommandDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EsmeSmsCommand> findAll(EsmeSmsCommand smsCommand) throws Exception {

		return findAll(smsCommand, false);
	}

	public List<EsmeSmsCommand> findAll(EsmeSmsCommand smsCommand, int firstItemIndex, int maxItems) throws Exception {

		return findAll(smsCommand, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(EsmeSmsCommand smsCommand, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(EsmeSmsCommand.class);
		Disjunction or = Restrictions.disjunction();
		if (smsCommand != null) {
			Long id = smsCommand.getCommandId();
			String name = smsCommand.getName();
			String code = smsCommand.getCode();
			String status = smsCommand.getStatus();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("commandId", id));
				} else {
					or.add(Restrictions.like("commandId", "%" + id + "%"));
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

			if (!FieldChecker.isEmptyString(name)) {
				String checkStartsWith = BusinessUtil.checkStartsWith(name);
				if (checkStartsWith != null) {
					or.add(Expression.like("name", checkStartsWith, MatchMode.START).ignoreCase());
				} else {
					if (exactMatch) {
						or.add(Restrictions.eq("name", name).ignoreCase());
					} else {
						or.add(Restrictions.like("name", "%" + name + "%").ignoreCase());
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

		if (orderedColumn != null && FieldChecker.classContainsField(EsmeSmsCommand.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<EsmeSmsCommand> findAll(EsmeSmsCommand smsCommand, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(smsCommand, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeSmsCommand> findAll(EsmeSmsCommand smsCommand, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(smsCommand, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeSmsCommand smsCommand, boolean exactMatch) throws Exception {

		Criteria counter = createCriteria(smsCommand, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public List<EsmeSmsCommand> findAll(EsmeSmsCommand smsCommand, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(smsCommand, sortedColumn, ascSorted, exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int checkExited(EsmeSmsCommand smsCommand) throws Exception {

		Criteria criteria = getSession().createCriteria(EsmeSmsCommand.class);
		if (smsCommand.getCode() != null) {

			criteria.add(Expression.eq("code", smsCommand.getCode()));
		} else if (smsCommand.getName() != null) {

			criteria.add(Expression.eq("name", smsCommand.getName()));
		}
		criteria.setProjection(Projections.count("commandId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		EsmeSmsCommand obj = new EsmeSmsCommand();
		obj.setCommandId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] { EsmeEmsMo.class, EsmeSmsLog.class, EsmeSmsRouting.class };

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("esmeSmsCommand", obj));
			criteria.setProjection(Projections.count("esmeSmsCommand"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {

		Criteria counter = getSession().createCriteria(EsmeSmsCommand.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

}