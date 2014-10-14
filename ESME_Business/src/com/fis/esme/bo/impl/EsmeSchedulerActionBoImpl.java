package com.fis.esme.bo.impl;

import java.util.List;

import com.fis.esme.bo.EsmeSchedulerActionBo;
import com.fis.esme.dao.EsmeSchedulerActionDao;
import com.fis.esme.persistence.EsmeSchedulerAction;
import com.fis.framework.bo.GenericBoImp;

public class EsmeSchedulerActionBoImpl extends
GenericBoImp<EsmeSchedulerAction, Long, EsmeSchedulerActionDao> implements EsmeSchedulerActionBo{

	@Override
	public List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSchedulerAction);
	}

	@Override
	public List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSchedulerAction, firstItemIndex, maxItems);
	}

	@Override
	public List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, boolean exactMatch)
			throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSchedulerAction, exactMatch);
	}

	@Override
	public List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSchedulerAction, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSchedulerAction, sortedColumn, ascSorted,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeSchedulerAction esmeSchedulerAction, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().count(esmeSchedulerAction, exactMatch);
	}

	@Override
	public int checkExited(EsmeSchedulerAction esmeSchedulerAction) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().checkExited(esmeSchedulerAction);
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
