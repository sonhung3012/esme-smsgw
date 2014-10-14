package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeIsdnPrefix;
import com.fis.framework.bo.GenericBo;

public interface EsmeIsdnPrefixBo extends GenericBo<EsmeIsdnPrefix, Long> {
	List<EsmeIsdnPrefix> findAll(EsmeIsdnPrefix EsmeIsdnPrefix) throws Exception;

	List<EsmeIsdnPrefix> findAll(EsmeIsdnPrefix EsmeIsdnPrefix, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeIsdnPrefix> findAll(EsmeIsdnPrefix EsmeIsdnPrefix, boolean exactMatch)
			throws Exception;

	List<EsmeIsdnPrefix> findAll(EsmeIsdnPrefix EsmeIsdnPrefix, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeIsdnPrefix> findAll(EsmeIsdnPrefix EsmeIsdnPrefix, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeIsdnPrefix EsmeIsdnPrefix, boolean exactMatch) throws Exception;

	int checkExited(EsmeIsdnPrefix EsmeIsdnPrefix) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
