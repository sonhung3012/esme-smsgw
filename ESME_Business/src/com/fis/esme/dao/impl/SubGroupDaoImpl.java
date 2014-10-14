package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.SubGroupDao;
import com.fis.esme.persistence.EsmeSmscParam;
import com.fis.esme.persistence.SubGroup;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class SubGroupDaoImpl extends
		GenericDaoSpringHibernateTemplate<SubGroup, Long> implements
		SubGroupDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SubGroup> findAll(SubGroup SubGroup) throws Exception {
		return findAll(SubGroup, false);
	}

	public List<SubGroup> findAll(SubGroup SubGroup, int firstItemIndex,
			int maxItems) throws Exception {
		return findAll(SubGroup, firstItemIndex, maxItems, false);
	}

	private Criteria createCriteria(SubGroup SubGroup, String orderedColumn,
			boolean asc, boolean exactMatch) throws Exception {

		Criteria finder = getSession().createCriteria(SubGroup.class);
		Disjunction or = Restrictions.disjunction();
		if (SubGroup != null) {
			Long id = SubGroup.getGroupId();

			if (id > 0) {
				if (exactMatch) {
					or.add(Restrictions.eq("groupId", id));
				} else {
					or.add(Restrictions.like("groupId", "%" + id + "%"));
				}
			}
		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(SubGroup.class,
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
	public List<SubGroup> findAll(SubGroup SubGroup, boolean exactMatch)
			throws Exception {
		Criteria finder = createCriteria(SubGroup, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<SubGroup> findAll(SubGroup SubGroup, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(SubGroup, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public int count(SubGroup SubGroup, boolean exactMatch) throws Exception {
		Criteria counter = createCriteria(SubGroup, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public List<SubGroup> findAll(SubGroup SubGroup, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		Criteria finder = createCriteria(SubGroup, sortedColumn, ascSorted,
				exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int checkExited(SubGroup SubGroup) throws Exception {

		Criteria criteria = getSession().createCriteria(SubGroup.class);
		criteria.add(Expression.eq("groupId", SubGroup.getGroupId()));
		criteria.add(Expression.eq("subId", SubGroup.getSubId()));
		criteria.setProjection(Projections.count("groupId"));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {

		SubGroup obj = new SubGroup();
		obj.setGroupId(id);

		Criteria criteria = null;
		Session session = getSession();
		;
		int i = 0;

		Class[] cls = new Class[] {};

		for (Class c : cls) {
			criteria = session.createCriteria(c);
			criteria.add(Expression.eq("SubGroup", obj));
			criteria.setProjection(Projections.count("SubGroup"));
			i += (Integer) criteria.uniqueResult();
			if (i > 0)
				return true;
		}
		return false;
	}

	@Override
	public int countAll() throws Exception {
		Criteria counter = getSession().createCriteria(SubGroup.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}
	
	@Override
	public Long persist(SubGroup bk) throws Exception {
		String strSQL = "insert into SUB_GROUP(SUB_ID,GROUP_ID) "
				+ "values (:subId,:groupId)";
		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.setLong("subId", bk.getSubId());
		query.setLong("groupId", bk.getGroupId());
		query.executeUpdate();
		return bk.getSubId();
	}

	@Override
	public void delete(SubGroup sg) throws Exception {
		String strSQL = "delete from SUB_GROUP "
				+ "where sub_id="+sg.getSubId()+" and group_id="+sg.getGroupId();
		SQLQuery query = getSession().createSQLQuery(strSQL);
		query.executeUpdate();
	}
	
}