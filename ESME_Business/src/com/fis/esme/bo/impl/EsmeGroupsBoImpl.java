
package com.fis.esme.bo.impl;

import java.util.List;

import com.fis.esme.bo.EsmeGroupsBo;
import com.fis.esme.dao.EsmeGroupsDao;
import com.fis.esme.persistence.EsmeGroups;
import com.fis.framework.bo.GenericBoImp;

public class EsmeGroupsBoImpl extends
GenericBoImp<EsmeGroups, Long, EsmeGroupsDao> implements EsmeGroupsBo{

	@Override
	public List<EsmeGroups> findAll(EsmeGroups esmeGroups) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeGroups);
	}

	@Override
	public List<EsmeGroups> findAll(EsmeGroups esmeGroups, int firstItemIndex,
			int maxItems) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeGroups, firstItemIndex, maxItems);
	}

	@Override
	public List<EsmeGroups> findAll(EsmeGroups esmeGroups, boolean exactMatch)
			throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeGroups, exactMatch);
	}

	@Override
	public List<EsmeGroups> findAll(EsmeGroups esmeGroups, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeGroups, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeGroups> findAll(EsmeGroups esmeGroups, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().findAll(esmeGroups, sortedColumn, ascSorted,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(EsmeGroups esmeGroups, boolean exactMatch) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().count(esmeGroups, exactMatch);
	}

	@Override
	public int checkExited(EsmeGroups esmeGroups) throws Exception {
		// TODO Auto-generated method stub
		return getDaoInternal().checkExited(esmeGroups);
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
