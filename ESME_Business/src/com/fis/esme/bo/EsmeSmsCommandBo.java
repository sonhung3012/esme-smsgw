package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.framework.bo.GenericBo;

public interface EsmeSmsCommandBo extends GenericBo<EsmeSmsCommand, Long> {
	List<EsmeSmsCommand> findAll(EsmeSmsCommand smsCommand) throws Exception;

	List<EsmeSmsCommand> findAll(EsmeSmsCommand smsCommand, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeSmsCommand> findAll(EsmeSmsCommand smsCommand, boolean exactMatch)
			throws Exception;

	List<EsmeSmsCommand> findAll(EsmeSmsCommand smsCommand, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeSmsCommand> findAll(EsmeSmsCommand smsCommand, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeSmsCommand smsCommand, boolean exactMatch) throws Exception;

	int checkExited(EsmeSmsCommand smsCommand) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
}
