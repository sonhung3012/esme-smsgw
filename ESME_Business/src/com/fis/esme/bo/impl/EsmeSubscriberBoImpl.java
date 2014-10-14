package com.fis.esme.bo.impl;

import java.util.List;

import com.fis.esme.bo.EsmeSubscriberBo;
import com.fis.esme.dao.EsmeSubscriberDao;
import com.fis.esme.persistence.EsmeSubscriber;
import com.fis.framework.bo.GenericBoImp;

public class EsmeSubscriberBoImpl extends
GenericBoImp<EsmeSubscriber, Long, EsmeSubscriberDao> implements EsmeSubscriberBo{

	@Override
	public List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSubscriber);
	}

	@Override
	public List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSubscriber, firstItemIndex, maxItems);
	}

	@Override
	public List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, boolean exactMatch)
			throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSubscriber, exactMatch);
	}

	@Override
	public List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSubscriber, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSubscriber, sortedColumn, ascSorted,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeSubscriber esmeSubscriber, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().count(esmeSubscriber, exactMatch);
	}

	@Override
	public int checkExited(EsmeSubscriber esmeSubscriber) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().checkExited(esmeSubscriber);
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
