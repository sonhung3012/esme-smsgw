package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.Groups;
import com.fis.framework.bo.GenericBo;

public interface GroupsBo extends GenericBo<Groups, Long> {
	List<Groups> findAll(Groups Groups) throws Exception;

	List<Groups> findAll(Groups Groups, int firstItemIndex,
			int maxItems) throws Exception;

	List<Groups> findAll(Groups Groups, boolean exactMatch)
			throws Exception;

	List<Groups> findAll(Groups Groups, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<Groups> findAll(Groups Groups, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(Groups Groups, boolean exactMatch) throws Exception;

	int checkExited(Groups Groups) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;

	Groups findBySubGgroup(long subId) throws Exception;
}
