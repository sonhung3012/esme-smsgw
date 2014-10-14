package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeMessage;
import com.fis.framework.bo.GenericBo;

public interface EsmeMessageBo extends GenericBo<EsmeMessage, Long> {
	List<EsmeMessage> findAll(EsmeMessage esmeMessage) throws Exception;

	List<EsmeMessage> findAll(EsmeMessage esmeMessage, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeMessage> findAll(EsmeMessage esmeMessage, boolean exactMatch)
			throws Exception;

	List<EsmeMessage> findAll(EsmeMessage esmeMessage, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeMessage> findAll(EsmeMessage esmeMessage, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeMessage esmeMessage, boolean exactMatch) throws Exception;

	int checkExited(EsmeMessage esmeMessage) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
