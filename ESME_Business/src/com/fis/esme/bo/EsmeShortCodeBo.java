package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeShortCode;
import com.fis.framework.bo.GenericBo;

public interface EsmeShortCodeBo extends GenericBo<EsmeShortCode, Long> {
	List<EsmeShortCode> findAll(EsmeShortCode esmeShortCode) throws Exception;

	List<EsmeShortCode> findAll(EsmeShortCode esmeShortCode, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeShortCode> findAll(EsmeShortCode esmeShortCode, boolean exactMatch)
			throws Exception;

	List<EsmeShortCode> findAll(EsmeShortCode esmeShortCode, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeShortCode> findAll(EsmeShortCode esmeShortCode, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeShortCode esmeShortCode, boolean exactMatch) throws Exception;

	int checkExited(EsmeShortCode esmeShortCode) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
