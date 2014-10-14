package com.fis.esme.bo.impl;

import java.util.Date;
import java.util.List;

import com.fis.esme.bo.EsmeDailyServiceSummaryBo;
import com.fis.esme.dao.EsmeDailyServiceSummaryDao;
import com.fis.esme.persistence.EsmeDailyServiceSummary;
import com.fis.framework.bo.GenericBoImp;

public class EsmeDailyServiceSummaryBoImpl extends
		GenericBoImp<EsmeDailyServiceSummary, Date, EsmeDailyServiceSummaryDao>
		implements EsmeDailyServiceSummaryBo {

	@Override
	public List<EsmeDailyServiceSummary> findAllByDate(Date fromDate,
			Date toDate) throws Exception {

		return getDaoInternal().findAllByDate(fromDate, toDate);
	}

	@Override
	public List<EsmeDailyServiceSummary> findAllByDateByListField(
			List<String> fields, Date fromDate, Date toDate) throws Exception {

		return getDaoInternal().findAllByDateByListField(fields, fromDate,
				toDate);
	}

	@Override
	public List<EsmeDailyServiceSummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception {

		return getDaoInternal().findAllInDateByField(field, fromDate, toDate);
	}

	public List<EsmeDailyServiceSummary> findAllByDateAndId(Long serviceId,
			Date fromDate, Date toDate) throws Exception {
		return getDaoInternal().findAllByDateAndId(serviceId, fromDate, toDate);
	}
}
