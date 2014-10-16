package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.Groups;
import com.fis.esme.persistence.Subscriber;
import com.fis.framework.bo.GenericBo;

public interface SubscriberBo extends GenericBo<Subscriber, Long> {

	List<Subscriber> findAll(Subscriber Subscriber) throws Exception;

	List<Subscriber> findAll(Subscriber Subscriber, int firstItemIndex, int maxItems) throws Exception;

	List<Subscriber> findAll(Subscriber Subscriber, boolean exactMatch) throws Exception;

	List<Subscriber> findAll(Subscriber Subscriber, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception;

	List<Subscriber> findAll(Subscriber Subscriber, String sortedColumn, boolean ascSorted, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception;

	List<Groups> findGroupsBySub(long subId) throws Exception;

	int count(Subscriber Subscriber, boolean exactMatch) throws Exception;

	int checkExited(Subscriber Subscriber) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;

}
