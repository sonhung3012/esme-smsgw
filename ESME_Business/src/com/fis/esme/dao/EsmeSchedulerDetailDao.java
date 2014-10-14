package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.EsmeSchedulerDetail;
import com.fis.framework.dao.GenericDao;

public interface EsmeSchedulerDetailDao extends GenericDao<EsmeSchedulerDetail, Long> {
	List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail) throws Exception;

	List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, boolean exactMatch)
			throws Exception;

	List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeSchedulerDetail> findAll(EsmeSchedulerDetail esmeSchedulerDetail, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeSchedulerDetail esmeSchedulerDetail, boolean exactMatch) throws Exception;

	int checkExited(EsmeSchedulerDetail esmeSchedulerDetail) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
