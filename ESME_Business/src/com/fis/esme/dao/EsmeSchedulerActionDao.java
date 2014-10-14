package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.EsmeSchedulerAction;
import com.fis.framework.dao.GenericDao;

public interface EsmeSchedulerActionDao extends GenericDao<EsmeSchedulerAction, Long> {
	List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction) throws Exception;

	List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, boolean exactMatch)
			throws Exception;

	List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeSchedulerAction> findAll(EsmeSchedulerAction esmeSchedulerAction, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeSchedulerAction esmeSchedulerAction, boolean exactMatch) throws Exception;

	int checkExited(EsmeSchedulerAction esmeSchedulerAction) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
