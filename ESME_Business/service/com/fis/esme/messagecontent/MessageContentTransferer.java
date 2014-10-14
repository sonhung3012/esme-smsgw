package com.fis.esme.messagecontent;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeMessageContentBo;
import com.fis.esme.persistence.EsmeMessageContent;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://messagecontent.esme.fis.com/", portName = "MessageContentTransfererPort", serviceName = "MessageContentTransfererService")
public class MessageContentTransferer {
	private EsmeMessageContentBo bo;

	public MessageContentTransferer() {
		bo = ServiceLocator.createService(EsmeMessageContentBo.class);
	};

	public Long add(EsmeMessageContent esmeMessage) throws Exception {
		return bo.persist(esmeMessage);
	}
	
	public void update(EsmeMessageContent esmeMessage) throws Exception {
		bo.update(esmeMessage);
	}

	public void delete(EsmeMessageContent esmeMessage) throws Exception {
		bo.delete(esmeMessage);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeMessageContent esmeMessage) {
		try {
			return bo.checkExited(esmeMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeMessageContent> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeMessageContent> findAllWithOrderPaging(EsmeMessageContent esmeMessage,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeMessage, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeMessageContent esmeMessage, boolean exactMatch) {
		try {
			return bo.count(esmeMessage, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public EsmeMessageContent findByMessageIdAndLanguageId(long messageId, long languageId) throws Exception 
	{
		return bo.findByMessageIdAndLanguageId(messageId, languageId);
	}
	
	public List<EsmeMessageContent> findByMessageId(long messageId) throws Exception 
	{
		return bo.findByMessageId(messageId);
	}
	
	public int deleteByMessageId(long messageId)
	{
		try
		{
			return bo.deleteByMessageId(messageId);
		}
		catch (Exception e)
		{
			return -1;
		}
	}

}
