package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.framework.bo.GenericBo;

public interface EsmeEmsMoBo extends GenericBo<EsmeEmsMo, Long> {
	List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo) throws Exception;

	List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, boolean exactMatch)
			throws Exception;

	List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeEmsMo> findAll(EsmeEmsMo esmeEmsMo, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeEmsMo esmeEmsMo, boolean exactMatch) throws Exception;

	int checkExited(EsmeEmsMo esmeEmsMo) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}

