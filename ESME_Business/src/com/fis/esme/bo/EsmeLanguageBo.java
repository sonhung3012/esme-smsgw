package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeLanguage;
import com.fis.framework.bo.GenericBo;

public interface EsmeLanguageBo extends GenericBo<EsmeLanguage, Long> {
	List<EsmeLanguage> findAll(EsmeLanguage esmeLanguage) throws Exception;

	List<EsmeLanguage> findAll(EsmeLanguage esmeLanguage, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeLanguage> findAll(EsmeLanguage esmeLanguage, boolean exactMatch)
			throws Exception;

	List<EsmeLanguage> findAll(EsmeLanguage esmeLanguage, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeLanguage> findAll(EsmeLanguage esmeLanguage, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeLanguage esmeLanguage, boolean exactMatch) throws Exception;

	int checkExited(EsmeLanguage esmeLanguage) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
	
	public void updateAllLanguage(EsmeLanguage language,String field,String value) throws Exception;
	public EsmeLanguage getDefaultLanguage() throws Exception;
	public List<EsmeLanguage> getLanguageStatus();
}
