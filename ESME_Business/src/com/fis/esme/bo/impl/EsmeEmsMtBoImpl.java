package com.fis.esme.bo.impl;

import java.util.List;

import com.fis.esme.bo.EsmeEmsMtBo;
import com.fis.esme.dao.EsmeEmsMtDao;
import com.fis.esme.persistence.EsmeEmsMt;
import com.fis.framework.bo.GenericBoImp;

public class EsmeEmsMtBoImpl extends
GenericBoImp<EsmeEmsMt, Long, EsmeEmsMtDao> implements EsmeEmsMtBo{

	@Override
	public List<EsmeEmsMt> findAll(EsmeEmsMt esmeEmsMt) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeEmsMt);
	}

	@Override
	public List<EsmeEmsMt> findAll(EsmeEmsMt esmeEmsMt, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeEmsMt, firstItemIndex, maxItems);
	}

	@Override
	public List<EsmeEmsMt> findAll(EsmeEmsMt esmeEmsMt, boolean exactMatch)
			throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeEmsMt, exactMatch);
	}

	@Override
	public List<EsmeEmsMt> findAll(EsmeEmsMt esmeEmsMt, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeEmsMt, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeEmsMt> findAll(EsmeEmsMt esmeEmsMt, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeEmsMt, sortedColumn, ascSorted,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeEmsMt esmeEmsMt, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().count(esmeEmsMt, exactMatch);
	}

	@Override
	public int checkExited(EsmeEmsMt esmeEmsMt) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().checkExited(esmeEmsMt);
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

	@Override
	public EsmeEmsMt findByMtID(long id) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findByMtID(id);
	}

}
