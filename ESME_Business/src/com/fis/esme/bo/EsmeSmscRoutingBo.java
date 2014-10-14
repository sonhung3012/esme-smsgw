package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeSmscRouting;
import com.fis.framework.bo.GenericBo;

public interface EsmeSmscRoutingBo extends GenericBo<EsmeSmscRouting, Long> {
	List<EsmeSmscRouting> findAll(EsmeSmscRouting EsmeSmscRouting)
			throws Exception;

	List<EsmeSmscRouting> findAll(EsmeSmscRouting EsmeSmscRouting,
			int firstItemIndex, int maxItems) throws Exception;

	List<EsmeSmscRouting> findAll(EsmeSmscRouting EsmeSmscRouting,
			boolean exactMatch) throws Exception;

	List<EsmeSmscRouting> findAll(EsmeSmscRouting EsmeSmscRouting,
			int firstItemIndex, int maxItems, boolean exactMatch)
			throws Exception;

	List<EsmeSmscRouting> findAll(EsmeSmscRouting EsmeSmscRouting,
			String sortedColumn, boolean ascSorted, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	int count(EsmeSmscRouting EsmeSmscRouting, boolean exactMatch)
			throws Exception;

	int checkExited(EsmeSmscRouting EsmeSmscRouting) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
