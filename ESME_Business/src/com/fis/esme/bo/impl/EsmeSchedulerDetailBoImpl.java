
package com.fis.esme.bo.impl;

import java.util.List;

import com.fis.esme.bo.EsmeSchedulerDetailBo;
import com.fis.esme.dao.EsmeSchedulerDetailDao;
import com.fis.esme.persistence.EsmeSchedulerDetail;
import com.fis.framework.bo.GenericBoImp;

public class EsmeSchedulerDetailBoImpl extends
GenericBoImp<EsmeSchedulerDetail, Long, EsmeSchedulerDetailDao> implements EsmeSchedulerDetailBo{

	@Override
	public List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSchedulerDetail);
	}

	@Override
	public List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSchedulerDetail, firstItemIndex, maxItems);
	}

	@Override
	public List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, boolean exactMatch)
			throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSchedulerDetail, exactMatch);
	}

	@Override
	public List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSchedulerDetail, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeSchedulerDetail, sortedColumn, ascSorted,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeSchedulerDetail esmeSchedulerDetail, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().count(esmeSchedulerDetail, exactMatch);
	}

	@Override
	public int checkExited(EsmeSchedulerDetail esmeSchedulerDetail) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().checkExited(esmeSchedulerDetail);
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
