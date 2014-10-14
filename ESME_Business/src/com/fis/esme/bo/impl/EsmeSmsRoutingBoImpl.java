package com.fis.esme.bo.impl;

import java.util.List;

import com.fis.esme.bo.EsmeSmsRoutingBo;
import com.fis.esme.dao.EsmeSmsRoutingDao;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeSmsRouting;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.bo.GenericBoImp;

// Generated Apr 26, 2011 6:00:02 PM by Hibernate Tools 3.2.4.GA

/**
 * AccessNumber generated by hbm2java
 */
public class EsmeSmsRoutingBoImpl extends
		GenericBoImp<EsmeSmsRouting, Long, EsmeSmsRoutingDao> implements
		EsmeSmsRoutingBo {

	@Override
	public List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting) throws Exception {
		return getDaoInternal().findAll(searchEntity, esmeSmsRouting);
	}

	@Override
	public List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, int firstItemIndex, int maxItems)
			throws Exception {
		return getDaoInternal().findAll(searchEntity, esmeSmsRouting,
				firstItemIndex, maxItems);
	}

	@Override
	public List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, boolean exactMatch) throws Exception {
		return getDaoInternal().findAll(searchEntity, esmeSmsRouting,
				exactMatch);
	}

	@Override
	public List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return getDaoInternal().findAll(searchEntity, esmeSmsRouting,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return getDaoInternal().findAll(searchEntity, esmeSmsRouting,
				sortedColumn, ascSorted, firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int count(SearchEntity searchEntity, EsmeSmsRouting esmeSmsRouting,
			boolean exactMatch) throws Exception {
		return getDaoInternal().count(searchEntity, esmeSmsRouting, exactMatch);
	}

	@Override
	public int checkExited(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting) throws Exception {
		return getDaoInternal().checkExited(searchEntity, esmeSmsRouting);
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
