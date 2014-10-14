package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.EsmeSmsRouting;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.dao.GenericDao;

public interface EsmeSmsRoutingDao extends GenericDao<EsmeSmsRouting, Long> {

	List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting) throws Exception;

	List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, int firstItemIndex, int maxItems)
			throws Exception;

	List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, boolean exactMatch) throws Exception;

	List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	List<EsmeSmsRouting> findAll(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(SearchEntity searchEntity, EsmeSmsRouting esmeSmsRouting,
			boolean exactMatch) throws Exception;

	int checkExited(SearchEntity searchEntity,EsmeSmsRouting esmeSmsRouting) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
