package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeEmsMt;
import com.fis.framework.bo.GenericBo;

public interface EsmeEmsMtBo extends GenericBo<EsmeEmsMt, Long> {
	List<EsmeEmsMt> findAll(EsmeEmsMt esmeEmsMt) throws Exception;

	List<EsmeEmsMt> findAll(EsmeEmsMt esmeEmsMt, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeEmsMt> findAll(EsmeEmsMt esmeEmsMt, boolean exactMatch)
			throws Exception;

	List<EsmeEmsMt> findAll(EsmeEmsMt esmeEmsMt, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeEmsMt> findAll(EsmeEmsMt esmeEmsMt, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeEmsMt esmeEmsMt, boolean exactMatch) throws Exception;

	int checkExited(EsmeEmsMt esmeEmsMt) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
	
	EsmeEmsMt findByMtID(long id) throws Exception;
}

