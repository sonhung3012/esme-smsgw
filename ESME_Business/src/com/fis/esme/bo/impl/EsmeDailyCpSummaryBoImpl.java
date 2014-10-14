package com.fis.esme.bo.impl;

import java.util.Date;
import java.util.List;

import com.fis.esme.bo.EsmeDailyCpSummaryBo;
import com.fis.esme.dao.EsmeDailyCpSummaryDao;
import com.fis.esme.persistence.EsmeDailyCpSummary;
import com.fis.framework.bo.GenericBoImp;

public class EsmeDailyCpSummaryBoImpl extends
		GenericBoImp<EsmeDailyCpSummary, Date, EsmeDailyCpSummaryDao> implements
		EsmeDailyCpSummaryBo {

	@Override
	public List<EsmeDailyCpSummary> findAllByDate(Date fromDate, Date toDate)
			throws Exception {

		return getDaoInternal().findAllByDate(fromDate, toDate);
	}

	@Override
	public List<EsmeDailyCpSummary> findAllByDateByListField(List<String> fields,
			Date fromDate, Date toDate) throws Exception {

		return getDaoInternal().findAllByDateByListField(fields, fromDate,
				toDate);
	}

	@Override
	public List<EsmeDailyCpSummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception {

		return getDaoInternal().findAllInDateByField(field, fromDate, toDate);
	}

	@Override
	public List<EsmeDailyCpSummary> findAllByDateAndId(Long serviceId,
			Date fromDate, Date toDate) throws Exception {
		return getDaoInternal().findAllByDateAndId(serviceId, fromDate, toDate);
	}

}
