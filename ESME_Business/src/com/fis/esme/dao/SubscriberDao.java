package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.Subscriber;
import com.fis.framework.dao.GenericDao;

public interface SubscriberDao extends GenericDao<Subscriber, Long> {
	List<Subscriber> findAll(Subscriber Subscriber) throws Exception;

	List<Subscriber> findAll(Subscriber Subscriber, int firstItemIndex,
			int maxItems) throws Exception;

	List<Subscriber> findAll(Subscriber Subscriber, boolean exactMatch)
			throws Exception;

	List<Subscriber> findAll(Subscriber Subscriber, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<Subscriber> findAll(Subscriber Subscriber, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(Subscriber Subscriber, boolean exactMatch) throws Exception;

	int checkExited(Subscriber Subscriber) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;

}
