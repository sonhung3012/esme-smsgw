package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.EsmeSmscParam;
import com.fis.framework.bo.GenericBo;

public interface EsmeSmscBo extends GenericBo<EsmeSmsc, Long> {
	List<EsmeSmsc> findAll(EsmeSmsc EsmeSmsc) throws Exception;

	List<EsmeSmsc> findAll(EsmeSmsc EsmeSmsc, int firstItemIndex, int maxItems)
			throws Exception;

	List<EsmeSmsc> findAll(EsmeSmsc EsmeSmsc, boolean exactMatch)
			throws Exception;

	List<EsmeSmsc> findAll(EsmeSmsc EsmeSmsc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	List<EsmeSmsc> findAll(EsmeSmsc EsmeSmsc, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeSmsc EsmeSmsc, boolean exactMatch) throws Exception;

	int checkExited(EsmeSmsc EsmeSmsc) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;

	List<EsmeSmscParam> addCopyDataParam(EsmeSmsc copyData,
			EsmeSmsc esmeServices) throws Exception;

}
