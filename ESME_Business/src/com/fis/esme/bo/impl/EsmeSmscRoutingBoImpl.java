package com.fis.esme.bo.impl;

import java.util.List;

import com.fis.esme.bo.EsmeSmscRoutingBo;
import com.fis.esme.dao.EsmeSmscRoutingDao;
import com.fis.esme.persistence.EsmeSmscRouting;
import com.fis.framework.bo.GenericBoImp;

// Generated Apr 26, 2011 6:00:02 PM by Hibernate Tools 3.2.4.GA

/**
 * AccessNumber generated by hbm2java
 */
public class EsmeSmscRoutingBoImpl extends
		GenericBoImp<EsmeSmscRouting, Long, EsmeSmscRoutingDao> implements
		EsmeSmscRoutingBo {

	@Override
	public int checkExited(EsmeSmscRouting prcService) throws Exception {
		return getDaoInternal().checkExited(prcService);
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		return getDaoInternal().checkConstraints(id);
	}

	@Override
	public List<EsmeSmscRouting> findAll(EsmeSmscRouting prcService) throws Exception {
		return getDaoInternal().findAll(prcService);
	}

	@Override
	public List<EsmeSmscRouting> findAll(EsmeSmscRouting prcService, int firstItemIndex,
			int maxItems) throws Exception {
		return getDaoInternal().findAll(prcService, firstItemIndex, maxItems);
	}

	@Override
	public List<EsmeSmscRouting> findAll(EsmeSmscRouting prcService, boolean exactMatch)
			throws Exception {
		return getDaoInternal().findAll(prcService, exactMatch);
	}

	@Override
	public List<EsmeSmscRouting> findAll(EsmeSmscRouting prcService, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return getDaoInternal().findAll(prcService, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeSmscRouting> findAll(EsmeSmscRouting prcService, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return getDaoInternal().findAll(prcService, sortedColumn, ascSorted,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int countAll() throws Exception {
		return getDaoInternal().countAll();
	}

	@Override
	public int count(EsmeSmscRouting prcService, boolean exactMatch)
			throws Exception {
		return getDaoInternal().count(prcService, exactMatch);
	}

}
