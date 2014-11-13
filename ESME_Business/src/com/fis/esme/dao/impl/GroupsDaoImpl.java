package com.fis.esme.dao.impl;

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

import com.fis.esme.dao.GroupsDao;
import com.fis.esme.persistence.Groups;
import com.fis.esme.utils.BusinessUtil;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class GroupsDaoImpl extends GenericDaoSpringHibernateTemplate<Groups, Long> implements GroupsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Groups> findAll(Groups Groups) throws Exception {

		return findAll(Groups, false);
	}

	public List<Groups> findAll(Groups Groups, int firstItemIndex, int maxItems) throws Exception {

		return findAll(Groups, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(Groups Groups, String orderedColumn, boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(Groups.class);
		Disjunction or = Restrictions.disjunction();
		if (Groups != null) {
			Long id = Groups.getGroupId();
			String name = Groups.getName();
			String description = Groups.getDesciption();
			String status = Groups.getStatus();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("groupId", id));
				} else {
					or.add(Restrictions.like("groupId", "%" + id + "%"));
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

		if (orderedColumn != null && FieldChecker.classContainsField(Groups.class, orderedColumn)) {
			if (asc) {
				finder.addOrder(Order.asc(orderedColumn));
			} else {
				finder.addOrder(Order.desc(orderedColumn));
			}
		}

		return finder;

	}

	@Override
	public List<Groups> findAll(Groups Groups, boolean exactMatch) throws Exception {

		Criteria finder = createCriteria(Groups, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<Groups> findAll(Groups Groups, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return findAll(Groups, null, false, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(Groups groups, boolean exactMatch) throws Exception {

		// Criteria counter = createCriteria(Groups, null, false, exactMatch);
		// counter.setProjection(Projections.rowCount());
		// List re = counter.list();
		//
		// if (re.size() < 1) {
		// return 0;
		// } else {
		// return (Integer) re.get(0);
		// }

		String strSQL = "select count(*) from GROUPS";

		if (groups != null) {

			if (groups.getName() != null) {

				String checkStartsWith = BusinessUtil.checkStartsWith(groups.getName());

				if (checkStartsWith != null) {

					strSQL += " where upper(NAME) like '" + checkStartsWith + "%' ";

				} else {

					strSQL += " where lower(NAME) like '%" + groups.getName() + "%' ";
				}
			} else {

				if (groups.getDesciption() != null && !groups.getDesciption().equals("") && groups.getDesciption().endsWith("_Search")) {

					String strDesc = groups.getDesciption().substring(0, groups.getDesciption().lastIndexOf("_Search"));
					strSQL += " where lower(DESCRIPTION) like '%" + strDesc.toLowerCase() + "%'";
				}

				if (groups.getDesciption() != null && !groups.getDesciption().equals("") && !groups.getDesciption().endsWith("_Search")) {

					strSQL += " where GROUP_ID in (" + groups.getDesciption() + ")";
				}
				if (groups.getStatus() != null && groups.getDesciption() == null) {
					strSQL += " where STATUS = " + groups.getStatus() + " ";
				}

				if (groups.getStatus() != null && groups.getDesciption() != null && !groups.getDesciption().endsWith("_Search")) {
					strSQL += " where GROUP_ID in (" + groups.getDesciption() + ") and STATUS = " + groups.getStatus() + " ";
				}
			}
		}

		SQLQuery query = getSession().createSQLQuery(strSQL);
		// query.addEntity(ApParam.class);
		List re = query.list();

		if (re.size() < 1) {

			return 0;

		} else {

			// BigDecimal de = (BigDecimal) re.get(0);
			// String s = String.valueOf(de);
			Integer i = (Integer) re.get(0);
			return i;
		}

	}

	@Override
	public List<Groups> findAll(Groups groups, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		// Criteria finder = createCriteria(Groups, sortedColumn, ascSorted, exactMatch);
		// if (firstItemIndex >= 0 && maxItems >= 0) {
		// finder.setFirstResult(firstItemIndex);
		// finder.setMaxResults(maxItems);
		// }
		//
		// return finder.list();
		String strSQL = "SELECT * from GROUPS";
		if (groups != null) {
			if (groups.getName() != null) {
				String checkStartsWith = BusinessUtil.checkStartsWith(groups.getName());
				if (checkStartsWith != null) {
					strSQL += " where upper(NAME) like '" + checkStartsWith + "%' ";
				} else {
					strSQL += " where lower(NAME) like '%" + groups.getName() + "%' ";
				}
			} else {
				if (groups.getDesciption() != null && !groups.getDesciption().equals("") && groups.getDesciption().endsWith("_Search")) {

					String strDesc = groups.getDesciption().substring(0, groups.getDesciption().lastIndexOf("_Search"));
					strSQL += " where lower(DESCRIPTION) like '%" + strDesc.toLowerCase() + "%'";
				}

				if (groups.getDesciption() != null && groups.getStatus() == null && !groups.getDesciption().endsWith("_Search")) {
					strSQL += " where GROUP_ID in (" + groups.getDesciption() + ")";
				}
				if (groups.getStatus() != null && groups.getDesciption() == null) {
					strSQL += " where STATUS = " + groups.getStatus() + " ";
				}
				if (groups.getStatus() != null && groups.getDesciption() != null && !groups.getDesciption().endsWith("_Search")) {
					strSQL += " where GROUP_ID in (" + groups.getDesciption() + ") and STATUS = " + groups.getStatus() + " ";
				}
			}
		}

		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.addEntity(Groups.class);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			query = (SQLQuery) query.setFirstResult(firstItemIndex);
			query.setMaxResults(maxItems);
		}

		return query.list();
	}

	@Override
	public int checkExited(Groups Groups) throws Exception {

		Criteria criteria = getSession().createCriteria(Groups.class);
		criteria.add(Expression.eq("name", Groups.getName()));
		criteria.setProjection(Projections.count("groupId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		Groups obj = new Groups();
		obj.setGroupId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("Groups", obj));
			criteria.setProjection(Projections.count("Groups"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {

		Criteria counter = getSession().createCriteria(Groups.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public Groups findBySubGgroup(long subId) throws Exception {

		String strSQL = "select * from GROUPS where GROUP_ID in (select GROUP_ID from" + " SUB_GROUP where sub_id=" + subId + ") and status='1'";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.addEntity(Groups.class);
		List<Groups> lst = (List<Groups>) query.list();
		if (lst != null && lst.size() > 0)
			return lst.get(0);
		return null;
	}

}