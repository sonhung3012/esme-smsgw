package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.EsmeSubscriber;
import com.fis.framework.dao.GenericDao;

public interface EsmeSubscriberDao extends GenericDao<EsmeSubscriber, Long> {
	List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber) throws Exception;

	List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, boolean exactMatch)
			throws Exception;

	List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeSubscriber> findAll(EsmeSubscriber esmeSubscriber, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeSubscriber esmeSubscriber, boolean exactMatch) throws Exception;

	int checkExited(EsmeSubscriber esmeSubscriber) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}

