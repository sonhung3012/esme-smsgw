package com.fis.esme.bo;

import java.util.Date;
import java.util.List;

import com.fis.esme.persistence.EsmeDailyServiceSummary;
import com.fis.framework.bo.GenericBo;

public interface EsmeDailyServiceSummaryBo extends
		GenericBo<EsmeDailyServiceSummary, Date> {

	public List<EsmeDailyServiceSummary> findAllByDate(Date fromDate,
			Date toDate) throws Exception;

	public List<EsmeDailyServiceSummary> findAllByDateByListField(
			List<String> fields, Date fromDate, Date toDate) throws Exception;

	public List<EsmeDailyServiceSummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception;

	public List<EsmeDailyServiceSummary> findAllByDateAndId(Long serviceId,
			Date fromDate, Date toDate) throws Exception;
}
