package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.ApParam;
import com.fis.framework.dao.GenericDao;

public interface ApParamDao extends GenericDao<ApParam, String> {
	
	List<ApParam> findAll(ApParam apParam) throws Exception;

	List<ApParam> findAll(ApParam apParam, int firstItemIndex,
			int maxItems) throws Exception;

	List<ApParam> findAll(ApParam apParam, boolean exactMatch)
			throws Exception;

	List<ApParam> findAll(ApParam apParam, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<ApParam> findAll(ApParam apParam, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(ApParam apParam, boolean exactMatch) throws Exception;

	int checkExited(ApParam apParam) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(String id) throws Exception;
}
