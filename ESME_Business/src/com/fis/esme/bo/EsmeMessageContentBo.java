package com.fis.esme.bo;

import java.util.List;

import com.fis.esme.persistence.EsmeMessageContent;
import com.fis.framework.bo.GenericBo;

public interface EsmeMessageContentBo extends GenericBo<EsmeMessageContent, Long> {
	List<EsmeMessageContent> findAll(EsmeMessageContent esmeMessage) throws Exception;

	List<EsmeMessageContent> findAll(EsmeMessageContent esmeMessage, int firstItemIndex,
			int maxItems) throws Exception;

	List<EsmeMessageContent> findAll(EsmeMessageContent esmeMessage, boolean exactMatch)
			throws Exception;

	List<EsmeMessageContent> findAll(EsmeMessageContent esmeMessage, int firstItemIndex,
			int maxItems, boolean exactMatch) throws Exception;

	List<EsmeMessageContent> findAll(EsmeMessageContent esmeMessage, String sortedColumn,
			boolean ascSorted, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception;

	int count(EsmeMessageContent esmeMessage, boolean exactMatch) throws Exception;

	int checkExited(EsmeMessageContent esmeMessage) throws Exception;

	int countAll() throws Exception;

	boolean checkConstraints(Long id) throws Exception;
	
	public int deleteByMessageId(long messageId) throws Exception ;
	public List<EsmeMessageContent> findByMessageId(long messageId)
			throws Exception ;
	public EsmeMessageContent findByMessageIdAndLanguageId(long messageId,
			long languageId) throws Exception ;
}
