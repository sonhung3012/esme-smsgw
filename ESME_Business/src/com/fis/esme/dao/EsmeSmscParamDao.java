package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.EsmeSmscParam;
import com.fis.framework.dao.GenericDao;

public interface EsmeSmscParamDao extends GenericDao<EsmeSmscParam, Long> {
	List<EsmeSmscParam> findAll(EsmeSmscParam EsmeSmscParam) throws Exception;

	List<EsmeSmscParam> findAll(EsmeSmscParam EsmeSmscParam, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeSmscParam> findAll(EsmeSmscParam EsmeSmscParam, boolean exactMatch)
			throws Exception;

	List<EsmeSmscParam> findAll(EsmeSmscParam EsmeSmscParam, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeSmscParam> findAll(EsmeSmscParam EsmeSmscParam, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeSmscParam EsmeSmscParam, boolean exactMatch) throws Exception;

	int checkExited(EsmeSmscParam EsmeSmscParam) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;

	public void update(EsmeSmscParam oldObj, EsmeSmscParam newObj)
			throws Exception;
}
