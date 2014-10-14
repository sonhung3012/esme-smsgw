package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeSchedulerActionDao;
import com.fis.esme.persistence.EsmeMessage;
import com.fis.esme.persistence.EsmeSchedulerAction;
import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeSchedulerActionDaoImpl extends
GenericDaoSpringHibernateTemplate<EsmeSchedulerAction, Long> implements EsmeSchedulerActionDao{

	@Override
	public List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction) throws Exception {
		// TODO Auto-generated method stub
		return findAll(esmeSchedulerAction, false);
	}

	@Override
	public List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return findAll(esmeSchedulerAction, firstItemIndex, maxItems, false);
	}
	private Criteria createCriteria(EsmeSchedulerAction esmeSchedulerAction,
			String orderedColumn, boolean asc, boolean exactMatch)
			throws Exception {

		Criteria finder = getSession().createCriteria(EsmeSchedulerAction.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeSchedulerAction != null) {	
			EsmeMessage message = esmeSchedulerAction.getEsmeMessage();
			if (message != null) {
				or.add(Restrictions.eq("esmeMessage", message));
			}
		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeSchedulerAction.class,
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
	public List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, boolean exactMatch)
			throws Exception {
		Criteria finder = createCriteria(esmeSchedulerAction, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(esmeSchedulerAction, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria finder = createCriteria(esmeSchedulerAction, sortedColumn, ascSorted,
				exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int count(EsmeSchedulerAction esmeSchedulerAction, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = createCriteria(esmeSchedulerAction, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public int checkExited(EsmeSchedulerAction esmeSchedulerAction) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countAll() throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = getSession().createCriteria(EsmeSchedulerAction.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
