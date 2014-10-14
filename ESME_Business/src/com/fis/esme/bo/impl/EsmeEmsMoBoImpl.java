package com.fis.esme.bo.impl;

import java.util.List;

import com.fis.esme.bo.EsmeEmsMoBo;
import com.fis.esme.dao.EsmeEmsMoDao;
import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.framework.bo.GenericBoImp;

public class EsmeEmsMoBoImpl extends
GenericBoImp<EsmeEmsMo, Long, EsmeEmsMoDao> implements EsmeEmsMoBo{

	@Override
	public List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeEmsMo);
	}

	@Override
	public List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeEmsMo, firstItemIndex, maxItems);
	}

	@Override
	public List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, boolean exactMatch)
			throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeEmsMo, exactMatch);
	}

	@Override
	public List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeEmsMo, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeEmsMo, sortedColumn, ascSorted,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeEmsMo esmeEmsMo, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().count(esmeEmsMo, exactMatch);
	}

	@Override
	public int checkExited(EsmeEmsMo esmeEmsMo) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().checkExited(esmeEmsMo);
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
