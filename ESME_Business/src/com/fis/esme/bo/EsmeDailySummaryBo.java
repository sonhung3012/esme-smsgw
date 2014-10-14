package com.fis.esme.bo;

import java.util.Date;
import java.util.List;

import com.fis.esme.persistence.EsmeDailySummary;
import com.fis.framework.bo.GenericBo;

public interface EsmeDailySummaryBo extends GenericBo<EsmeDailySummary, Date> {

	public List<EsmeDailySummary> findAllByDate(Date fromDate, Date toDate)
			throws Exception;

	public List<EsmeDailySummary> findAllByDateByListField(List<String> fields,
			Date fromDate, Date toDate) throws Exception;

	public List<EsmeDailySummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception;

}
