package com.fis.esme.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fis.esme.dao.EsmeSchedulerDetailDao;
import com.fis.esme.persistence.EsmeSchedulerDetail;
import com.fis.esme.persistence.EsmeScheduler;

import com.fis.esme.utils.FieldChecker;
import com.fis.framework.dao.hibernate.GenericDaoSpringHibernateTemplate;

public class EsmeSchedulerDetailDaoImpl extends
GenericDaoSpringHibernateTemplate<EsmeSchedulerDetail, Long> implements EsmeSchedulerDetailDao{

	@Override
	public List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail) throws Exception {
		// TODO Auto-generated method stub
		return findAll(esmeSchedulerDetail, false);
	}

	@Override
	public List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return findAll(esmeSchedulerDetail, firstItemIndex, maxItems, false);
	}
	private Criteria createCriteria(EsmeSchedulerDetail esmeSchedulerDetail,
			String orderedColumn, boolean asc, boolean exactMatch)
			throws Exception {

		Criteria finder = getSession().createCriteria(EsmeSchedulerDetail.class);
		Disjunction or = Restrictions.disjunction();
		if (esmeSchedulerDetail != null) {	
			EsmeScheduler esmeScheduler = esmeSchedulerDetail.getEsmeScheduler();
			if (esmeScheduler != null) {
				or.add(Restrictions.eq("esmeScheduler", esmeScheduler));
			}
		}

		finder.add(or);

		if (orderedColumn != null
				&& FieldChecker.classContainsField(EsmeSchedulerDetail.class,
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
	public List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, boolean exactMatch)
			throws Exception {
		Criteria finder = createCriteria(esmeSchedulerDetail, null, false, exactMatch);
		return finder.list();
	}

	@Override
	public List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return findAll(esmeSchedulerDetail, null, false, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria finder = createCriteria(esmeSchedulerDetail, sortedColumn, ascSorted,
				exactMatch);
		if (firstItemIndex >= 0 && maxItems >= 0) {
			finder.setFirstResult(firstItemIndex);
			finder.setMaxResults(maxItems);
		}

		return finder.list();
	}

	@Override
	public int count(EsmeSchedulerDetail esmeSchedulerDetail, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = createCriteria(esmeSchedulerDetail, null, false, exactMatch);
		counter.setProjection(Projections.rowCount());
		List re = counter.list();

		if (re.size() < 1) {
			return 0;
		} else {
			return (Integer) re.get(0);
		}
	}

	@Override
	public int checkExited(EsmeSchedulerDetail esmeSchedulerDetail) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countAll() throws Exception {
		// TODO Auto-generated method stub
		Criteria counter = getSession().createCriteria(EsmeSchedulerDetail.class);
		counter.setProjection(Projections.rowCount());
		return (Integer) counter.list().get(0);
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
