package com.fis.esme.bo.impl;

import java.util.List;

import com.fis.esme.bo.EsmeMessageBo;
import com.fis.esme.dao.EsmeMessageDao;
import com.fis.esme.persistence.EsmeMessage;
import com.fis.framework.bo.GenericBoImp;

// Generated Apr 26, 2011 6:00:02 PM by Hibernate Tools 3.2.4.GA

/**
 * AccessNumber generated by hbm2java
 */
public class EsmeMessageBoImpl extends
		GenericBoImp<EsmeMessage, Long, EsmeMessageDao> implements
		EsmeMessageBo {

	@Override
	public int checkExited(EsmeMessage esmeMessage) throws Exception {
		return getDaoInternal().checkExited(esmeMessage);
	}

	@Override
	public boolean checkConstraints(Long id) throws Exception {
		return getDaoInternal().checkConstraints(id);
	}

	@Override
	public List<EsmeMessage> findAll(EsmeMessage esmeMessage) throws Exception {
		return getDaoInternal().findAll(esmeMessage);
	}

	@Override
	public List<EsmeMessage> findAll(EsmeMessage esmeMessage, int firstItemIndex,
			int maxItems) throws Exception {
		return getDaoInternal().findAll(esmeMessage, firstItemIndex, maxItems);
	}

	@Override
	public List<EsmeMessage> findAll(EsmeMessage esmeMessage, boolean exactMatch)
			throws Exception {
		return getDaoInternal().findAll(esmeMessage, exactMatch);
	}

	@Override
	public List<EsmeMessage> findAll(EsmeMessage esmeMessage, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception {
		return getDaoInternal().findAll(esmeMessage, firstItemIndex, maxItems,
				exactMatch);
	}

	@Override
	public List<EsmeMessage> findAll(EsmeMessage esmeMessage, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return getDaoInternal().findAll(esmeMessage, sortedColumn, ascSorted,
				firstItemIndex, maxItems, exactMatch);
	}

	@Override
	public int countAll() throws Exception {
		return getDaoInternal().countAll();
	}

	@Override
	public int count(EsmeMessage esmeMessage, boolean exactMatch)
			throws Exception {
		return getDaoInternal().count(esmeMessage, exactMatch);
	}

}
