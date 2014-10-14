package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeGroupsDao;
import com.fis.esme.persistence.EsmeGroups;

import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeGroupsDaoImpl extends
GenericDaoSpringHibernateTemplate<EsmeGroups, Long> implements EsmeGroupsDao{

	@Override
	public List<EsmeGroups> findAll(EsmeGroups esmeGroups) throws Exception {
		// TODO Auto-generated method stub
		return findAll(esmeGroups, false);
	}

	@Override
	public List<EsmeGroups> findAll(EsmeGroups esmeGroups, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return findAll(esmeGroups, firstItemIndex, maxItems, false);
	}
	private Criteria createCriteria(EsmeGroups esmeGroups,
			String orderedColumn, boolean asc, boolean exactMatch)
			throws Exception {

		Criteria finder = getSession().createCriteria(EsmeGroups.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeGroups != null) {			
		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeGroups.class,
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
	public List<EsmeGroups> findAll(EsmeGroups esmeGroups, boolean exactMatch)
			throws Exception {
		Criteria finder = createCriteria(esmeGroups, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeGroups> findAll(EsmeGroups esmeGroups, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(esmeGroups, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeGroups> findAll(EsmeGroups esmeGroups, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria finder = createCriteria(esmeGroups, sortedColumn, ascSorted,
				exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int count(EsmeGroups esmeGroups, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = createCriteria(esmeGroups, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public int checkExited(EsmeGroups esmeGroups) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countAll() throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = getSession().createCriteria(EsmeGroups.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
