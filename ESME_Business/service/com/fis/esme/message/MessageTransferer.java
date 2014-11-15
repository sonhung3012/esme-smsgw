package com.fis.esme.message;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeMessageBo;
import com.fis.esme.persistence.EsmeMessage;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://message.esme.fis.com/", portName = "MessageTransfererPort", serviceName = "MessageTransfererService")
public class MessageTransferer {

	private EsmeMessageBo bo;

	public MessageTransferer() {

		bo = ServiceLocator.createService(EsmeMessageBo.class);
	};

	public Long add(EsmeMessage esmeMessage) throws Exception {

		return bo.persist(esmeMessage);
	}

	public void update(EsmeMessage esmeMessage) throws Exception {

		bo.update(esmeMessage);
	}

	public void delete(EsmeMessage esmeMessage) throws Exception {

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

	public int checkExisted(EsmeMessage esmeMessage) {

		try {
			return bo.checkExited(esmeMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeMessage> findAllWithoutParameter() throws Exception {

		return bo.findAll();
	}

	public List<EsmeMessage> findAllWithOrderPaging(EsmeMessage esmeMessage, String sortedColumn, boolean asc, int firstItemIndex, int maxItems, boolean exactMatch) throws Exception {

		return bo.findAll(esmeMessage, sortedColumn, asc, firstItemIndex, maxItems, exactMatch);
	}

	public int count(EsmeMessage esmeMessage, boolean exactMatch) {

		try {
			return bo.count(esmeMessage, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
