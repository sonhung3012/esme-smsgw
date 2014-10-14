package com.fis.esme.bo.impl;

import java.util.List;

import com.fis.esme.bo.EsmeSchedulerBo;
import com.fis.esme.dao.EsmeSchedulerDao;
import com.fis.esme.persistence.EsmeScheduler;
import com.fis.framework.bo.GenericBoImp;

public class EsmeSchedulerBoImpl extends
GenericBoImp<EsmeScheduler, Long, EsmeSchedulerDao> implements EsmeSchedulerBo{

	@Override
	public List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeScheduler);
	}

	@Override
	public List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeScheduler, firstItemIndex, maxItems);
	}

	@Override
	public List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, boolean exactMatch)
			throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeScheduler, exactMatch);
	}

	@Override
	public List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeScheduler, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeScheduler, sortedColumn, ascSorted,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeScheduler esmeScheduler, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().count(esmeScheduler, exactMatch);
	}

	@Override
	public int checkExited(EsmeScheduler esmeScheduler) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().checkExited(esmeScheduler);
	}

	@Override
	public int countAll() throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().countAll();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().checkConstraints(id);
	}

}
