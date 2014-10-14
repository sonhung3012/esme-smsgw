package com.fis.esme.bo.impl;

import java.util.Date;
import java.util.List;

import com.fis.esme.bo.EsmeDailyCommandSummaryBo;
import com.fis.esme.dao.EsmeDailyCommandSummaryDao;
import com.fis.esme.persistence.EsmeDailyCommandSummary;
import com.fis.framework.bo.GenericBoImp;

public class EsmeDailyCommandSummaryBoImpl extends
		GenericBoImp<EsmeDailyCommandSummary, Date, EsmeDailyCommandSummaryDao> implements
		EsmeDailyCommandSummaryBo {

	@Override
	public List<EsmeDailyCommandSummary> findAllByDate(Date fromDate, Date toDate)
			throws Exception {

		return getDaoInternal().findAllByDate(fromDate, toDate);
	}

	@Override
	public List<EsmeDailyCommandSummary> findAllByDateByListField(List<String> fields,
			Date fromDate, Date toDate) throws Exception {

		return getDaoInternal().findAllByDateByListField(fields, fromDate,
				toDate);
	}

	@Override
	public List<EsmeDailyCommandSummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception {

		return getDaoInternal().findAllInDateByField(field, fromDate, toDate);
	}

	public List<EsmeDailyCommandSummary> findAllByDateAndId(Long serviceId,
			Date fromDate, Date toDate) throws Exception {
		return getDaoInternal().findAllByDateAndId(serviceId, fromDate, toDate);
	}

}
