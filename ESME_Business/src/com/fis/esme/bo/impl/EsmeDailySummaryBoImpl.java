package com.fis.esme.bo.impl;

import java.util.Date;
import java.util.List;

import com.fis.esme.bo.EsmeDailySummaryBo;
import com.fis.esme.dao.EsmeDailySummaryDao;
import com.fis.esme.persistence.EsmeDailySummary;
import com.fis.framework.bo.GenericBoImp;

public class EsmeDailySummaryBoImpl extends
		GenericBoImp<EsmeDailySummary, Date, EsmeDailySummaryDao> implements
		EsmeDailySummaryBo {

	@Override
	public List<EsmeDailySummary> findAllByDate(Date fromDate, Date toDate)
			throws Exception {

		return getDaoInternal().findAllByDate(fromDate, toDate);
	}

	@Override
	public List<EsmeDailySummary> findAllByDateByListField(List<String> fields,
			Date fromDate, Date toDate) throws Exception {

		return getDaoInternal().findAllByDateByListField(fields, fromDate,
				toDate);
	}

	@Override
	public List<EsmeDailySummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception {

		return getDaoInternal().findAllInDateByField(field, fromDate, toDate);
	}

}
