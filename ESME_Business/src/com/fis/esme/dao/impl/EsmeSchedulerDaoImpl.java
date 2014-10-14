package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeSchedulerDao;
import com.fis.esme.persistence.EsmeScheduler;

import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeSchedulerDaoImpl extends
GenericDaoSpringHibernateTemplate<EsmeScheduler, Long> implements EsmeSchedulerDao{

	@Override
	public List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler) throws Exception {
		// TODO Auto-generated method stub
		return findAll(esmeScheduler, false);
	}

	@Override
	public List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return findAll(esmeScheduler, firstItemIndex, maxItems, false);
	}
	private Criteria createCriteria(EsmeScheduler esmeScheduler,
			String orderedColumn, boolean asc, boolean exactMatch)
			throws Exception {

		Criteria finder = getSession().createCriteria(EsmeScheduler.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeScheduler != null) {			
		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeScheduler.class,
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
	public List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, boolean exactMatch)
			throws Exception {
		Criteria finder = createCriteria(esmeScheduler, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(esmeScheduler, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria finder = createCriteria(esmeScheduler, sortedColumn, ascSorted,
				exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int count(EsmeScheduler esmeScheduler, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = createCriteria(esmeScheduler, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public int checkExited(EsmeScheduler esmeScheduler) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countAll() throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = getSession().createCriteria(EsmeScheduler.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
