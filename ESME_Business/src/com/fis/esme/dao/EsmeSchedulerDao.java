package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.EsmeScheduler;
import com.fis.framework.dao.GenericDao;

public interface EsmeSchedulerDao extends GenericDao<EsmeScheduler, Long> {
	List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler) throws Exception;

	List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, boolean exactMatch)
			throws Exception;

	List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeScheduler> findAll(EsmeScheduler esmeScheduler, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeScheduler esmeScheduler, boolean exactMatch) throws Exception;

	int checkExited(EsmeScheduler esmeScheduler) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
