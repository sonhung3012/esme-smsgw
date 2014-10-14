package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeIsdnPermission;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.bo.GenericBo;

public interface EsmeIsdnPermissionBo extends GenericBo<EsmeIsdnPermission, Long> {
	List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission) throws Exception;

	List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, int firstItemIndex, int maxItems)
			throws Exception;

	List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, boolean exactMatch) throws Exception;

	List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	List<EsmeIsdnPermission> findAll(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(SearchEntity searchEntity, EsmeIsdnPermission esmeIsdnPermission,
			boolean exactMatch) throws Exception;

	int checkExited(SearchEntity searchEntity,
			EsmeIsdnPermission esmeIsdnPermission) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
