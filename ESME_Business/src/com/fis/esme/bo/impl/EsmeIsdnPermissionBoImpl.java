package com.fis.esme.bo.impl;

import java.util.List;

import com.fis.esme.bo.EsmeIsdnPermissionBo;
import com.fis.esme.dao.EsmeIsdnPermissionDao;
import com.fis.esme.persistence.EsmeIsdnPermission;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.bo.GenericBoImp;

// Generated Apr 26, 2011 6:00:02 PM by Hibernate Tools 3.2.4.GA

/**
 * AccessNumber generated by hbm2java
 */
public class EsmeIsdnPermissionBoImpl extends
		GenericBoImp<EsmeIsdnPermission, Long, EsmeIsdnPermissionDao> implements
		EsmeIsdnPermissionBo {

	@Override
	public List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission) throws Exception {
		return getDaoInternal().findAll(searchEntity, esmeIsdnPermission);
	}

	@Override
	public List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, int firstItemIndex,
			int maxItems) throws Exception {
		return getDaoInternal().findAll(searchEntity, esmeIsdnPermission,
				firstItemIndex, maxItems);
	}

	@Override
	public List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, boolean exactMatch)
			throws Exception {
		return getDaoInternal().findAll(searchEntity, esmeIsdnPermission,
				exactMatch);
	}

	@Override
	public List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return getDaoInternal().findAll(searchEntity, esmeIsdnPermission,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return getDaoInternal().findAll(searchEntity, esmeIsdnPermission,
				sortedColumn, ascSorted, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, boolean exactMatch)
			throws Exception {
		return getDaoInternal().count(searchEntity, esmeIsdnPermission,
				exactMatch);
	}

	@Override
	public int checkExited(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission) throws Exception {
		return getDaoInternal().checkExited(searchEntity, esmeIsdnPermission);
	}

	@Override
	public int countAll() throws Exception {
		return getDaoInternal().countAll();
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		return getDaoInternal().checkConstraints(id);
	}

}
