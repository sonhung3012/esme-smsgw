package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.EsmeGroups;
import com.fis.framework.dao.GenericDao;

public interface EsmeGroupsDao extends GenericDao<EsmeGroups, Long> {
	List<EsmeGroups> findAll(EsmeGroups esmeGroups) throws Exception;

	List<EsmeGroups> findAll(EsmeGroups esmeGroups, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeGroups> findAll(EsmeGroups esmeGroups, boolean exactMatch)
			throws Exception;

	List<EsmeGroups> findAll(EsmeGroups esmeGroups, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeGroups> findAll(EsmeGroups esmeGroups, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeGroups esmeGroups, boolean exactMatch) throws Exception;

	int checkExited(EsmeGroups esmeGroups) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
