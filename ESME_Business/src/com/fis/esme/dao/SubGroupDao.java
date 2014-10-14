package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.SubGroup;
import com.fis.framework.dao.GenericDao;

public interface SubGroupDao extends GenericDao<SubGroup, Long> {
	List<SubGroup> findAll(SubGroup SubGroup) throws Exception;

	List<SubGroup> findAll(SubGroup SubGroup, int firstItemIndex,
			int maxItems) throws Exception;

	List<SubGroup> findAll(SubGroup SubGroup, boolean exactMatch)
			throws Exception;

	List<SubGroup> findAll(SubGroup SubGroup, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<SubGroup> findAll(SubGroup SubGroup, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(SubGroup SubGroup, boolean exactMatch) throws Exception;

	int checkExited(SubGroup SubGroup) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
