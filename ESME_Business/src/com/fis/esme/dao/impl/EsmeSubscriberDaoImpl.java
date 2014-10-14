package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeSubscriberDao;
import com.fis.esme.persistence.EsmeSubscriber;

import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeSubscriberDaoImpl extends
GenericDaoSpringHibernateTemplate<EsmeSubscriber, Long> implements EsmeSubscriberDao{

	@Override
	public List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber) throws Exception {
		// TODO Auto-generated method stub
		return findAll(esmeSubscriber, false);
	}

	@Override
	public List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return findAll(esmeSubscriber, firstItemIndex, maxItems, false);
	}
	private Criteria createCriteria(EsmeSubscriber esmeSubscriber,
			String orderedColumn, boolean asc, boolean exactMatch)
			throws Exception {

		Criteria finder = getSession().createCriteria(EsmeSubscriber.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeSubscriber != null) {			
		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeSubscriber.class,
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
	public List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, boolean exactMatch)
			throws Exception {
		Criteria finder = createCriteria(esmeSubscriber, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(esmeSubscriber, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria finder = createCriteria(esmeSubscriber, sortedColumn, ascSorted,
				exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int count(EsmeSubscriber esmeSubscriber, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = createCriteria(esmeSubscriber, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public int checkExited(EsmeSubscriber esmeSubscriber) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countAll() throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = getSession().createCriteria(EsmeSubscriber.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
