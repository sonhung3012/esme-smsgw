package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.EsmeIsdnPermission;
import com.fis.esme.persistence.EsmeIsdnSpecial;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.dao.GenericDao;

public interface EsmeIsdnSpecialDao extends GenericDao<EsmeIsdnSpecial, String> {

	List<EsmeIsdnSpecial> findAll(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial) throws Exception;

	List<EsmeIsdnSpecial> findAll(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial, int firstItemIndex, int maxItems)
			throws Exception;

	List<EsmeIsdnSpecial> findAll(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial, boolean exactMatch)
			throws Exception;

	List<EsmeIsdnSpecial> findAll(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	List<EsmeIsdnSpecial> findAll(SearchEntity searchEntity,
			EsmeIsdnSpecial esmeIsdnSpecial, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(SearchEntity searchEntity, EsmeIsdnSpecial esmeIsdnSpecial,
			boolean exactMatch) throws Exception;

	int checkExited(SearchEntity searchEntity, EsmeIsdnSpecial esmeIsdnSpecial)
			throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(String id) throws Exception;

	List<EsmeIsdnPermission> getPermissionByMisdn(String msisdn)
			throws Exception;

	void deletePermissionByMsisdn(String msisdn) throws Exception;

	void updateSpecial(EsmeIsdnSpecial esmeIsdnSpecial, String oldMsisdn)
			throws Exception;
}
