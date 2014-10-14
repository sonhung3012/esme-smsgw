package com.fis.esme.bo;

import java.util.Date;
import java.util.List;

import com.fis.esme.persistence.EsmeDailyCommandSummary;
import com.fis.framework.bo.GenericBo;

public interface EsmeDailyCommandSummaryBo extends
		GenericBo<EsmeDailyCommandSummary, Date> {

	public List<EsmeDailyCommandSummary> findAllByDate(Date fromDate,
			Date toDate) throws Exception;

	public List<EsmeDailyCommandSummary> findAllByDateByListField(
			List<String> fields, Date fromDate, Date toDate) throws Exception;

	public List<EsmeDailyCommandSummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception;

	public List<EsmeDailyCommandSummary> findAllByDateAndId(Long serviceId,
			Date fromDate, Date toDate) throws Exception;
}
