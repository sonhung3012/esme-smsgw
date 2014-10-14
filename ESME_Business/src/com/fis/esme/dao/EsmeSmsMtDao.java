package com.fis.esme.dao;

import java.util.List;

import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsMt;
import com.fis.esme.persistence.EsmeSmsRouting;
import com.fis.framework.dao.GenericDao;

public interface EsmeSmsMtDao extends GenericDao<EsmeSmsMt, Long> {
	List<EsmeSmsMt> findAll(EsmeSmsMt EsmeSmsMt) throws Exception;

	List<EsmeSmsMt> findAll(EsmeSmsMt EsmeSmsMt, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeSmsMt> findAll(EsmeSmsMt EsmeSmsMt, boolean exactMatch)
			throws Exception;

	List<EsmeSmsMt> findAll(EsmeSmsMt EsmeSmsMt, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeSmsMt> findAll(EsmeSmsMt EsmeSmsMt, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeSmsMt EsmeSmsMt, boolean exactMatch) throws Exception;

	int checkExited(EsmeSmsMt EsmeSmsMt) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
	
	List<EsmeSmsRouting> FindBySmsRouting(EsmeSmsRouting esmeSmsRouting) throws Exception;
	List<EsmeCp> findByCP(String id) throws Exception;
	List<EsmeSmsCommand> findByCommand(String id) throws Exception;
	List<EsmeShortCode> findByShortCode(String id) throws Exception;
	
	public void deleteByFileUploadId(long fileUploadId) throws Exception;
	public long addMultiProcess(List<EsmeSmsMt> esmeSmsMt) throws Exception;
	
	public void stopUpload() throws Exception;
}
