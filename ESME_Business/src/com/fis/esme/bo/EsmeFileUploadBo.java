package com.fis.esme.bo;

import java.util.Date;
import java.util.List;

import com.fis.esme.persistence.EsmeFileUpload;
import com.fis.framework.bo.GenericBo;

public interface EsmeFileUploadBo extends GenericBo<EsmeFileUpload, Long> {
	List<EsmeFileUpload> findAll(EsmeFileUpload esmeFileUpload)
			throws Exception;

	List<EsmeFileUpload> findAll(EsmeFileUpload esmeFileUpload,
			int firstItemIndex, int maxItems) throws Exception;

	List<EsmeFileUpload> findAll(EsmeFileUpload esmeFileUpload,
			boolean exactMatch) throws Exception;

	List<EsmeFileUpload> findAll(EsmeFileUpload esmeFileUpload,
			int firstItemIndex, int maxItems, boolean exactMatch)
			throws Exception;

	List<EsmeFileUpload> findAll(EsmeFileUpload esmeFileUpload,
			String sortedColumn, boolean ascSorted, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	int count(EsmeFileUpload esmeFileUpload, boolean exactMatch)
			throws Exception;

	int checkExited(EsmeFileUpload esmeFileUpload) throws Exception;

	int countAll() throws Exception;
	
	public List<EsmeFileUpload> findAllByDate(Date fromDate, Date toDate)
			throws Exception;
	
	public List<EsmeFileUpload> findAllInDateByField(String field, Date fromDate,
			Date toDate) throws Exception;

}
