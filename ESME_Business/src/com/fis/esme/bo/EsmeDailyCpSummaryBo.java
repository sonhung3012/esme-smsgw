package com.fis.esme.bo;

import java.util.Date;
import java.util.List;

import com.fis.esme.persistence.EsmeDailyCpSummary;
import com.fis.framework.bo.GenericBo;

public interface EsmeDailyCpSummaryBo extends
		GenericBo<EsmeDailyCpSummary, Date> {

	public List<EsmeDailyCpSummary> findAllByDate(Date fromDate, Date toDate)
			throws Exception;

	public List<EsmeDailyCpSummary> findAllByDateByListField(
			List<String> fields, Date fromDate, Date toDate) throws Exception;

	public List<EsmeDailyCpSummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception;

	public List<EsmeDailyCpSummary> findAllByDateAndId(Long serviceId,
			Date fromDate, Date toDate) throws Exception;
}
