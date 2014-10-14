package com.fis.esme.dao;

import java.util.Date;
import java.util.List;

import com.fis.esme.persistence.EsmeDailySummary;
import com.fis.framework.dao.GenericDao;

public interface EsmeDailySummaryDao extends GenericDao<EsmeDailySummary, Date> {

	public List<EsmeDailySummary> findAllByDate(Date fromDate, Date toDate)
			throws Exception;

	public List<EsmeDailySummary> findAllByDateByListField(List<String> fields,
			Date fromDate, Date toDate) throws Exception;

	public List<EsmeDailySummary> findAllInDateByField(String field,
			Date fromDate, Date toDate) throws Exception;
}
