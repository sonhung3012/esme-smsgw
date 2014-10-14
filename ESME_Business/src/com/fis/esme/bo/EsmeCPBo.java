package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeCp;
import com.fis.framework.bo.GenericBo;

public interface EsmeCPBo extends GenericBo<EsmeCp, Long> {
	List<EsmeCp> findAll(EsmeCp esmeCp) throws Exception;

	List<EsmeCp> findAll(EsmeCp esmeCp, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeCp> findAll(EsmeCp esmeCp, boolean exactMatch)
			throws Exception;

	List<EsmeCp> findAll(EsmeCp esmeCp, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeCp> findAll(EsmeCp esmeCp, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeCp esmeCp, boolean exactMatch) throws Exception;

	int checkExited(EsmeCp esmeCp) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
