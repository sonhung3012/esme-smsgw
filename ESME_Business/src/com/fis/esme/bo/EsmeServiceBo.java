package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeServices;
import com.fis.framework.bo.GenericBo;

public interface EsmeServiceBo extends GenericBo<EsmeServices, Long> {
	List<EsmeServices> findAll(EsmeServices EsmeServices) throws Exception;

	List<EsmeServices> findAll(EsmeServices EsmeServices, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeServices> findAll(EsmeServices EsmeServices, boolean exactMatch)
			throws Exception;

	List<EsmeServices> findAll(EsmeServices EsmeServices, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeServices> findAll(EsmeServices EsmeServices, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeServices EsmeServices, boolean exactMatch) throws Exception;

	int checkExited(EsmeServices EsmeServices) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
