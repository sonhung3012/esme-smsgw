package com.fis.esme.subscriber;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeSubscriberBo;
import com.fis.esme.persistence.EsmeSubscriber;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://subscriber.esme.fis.com/", portName = "SubscriberTransfererPort", serviceName = "SubscriberTransfererService")
public class SubscriberTransferer {
	private EsmeSubscriberBo bo;

	public SubscriberTransferer() {
		bo = ServiceLocator.createService(EsmeSubscriberBo.class);
	};

	public Long add(EsmeSubscriber esmeSubscriber) throws Exception {
		return bo.persist(esmeSubscriber);
	}
	
	public void update(EsmeSubscriber esmeSubscriber) throws Exception {
		bo.update(esmeSubscriber);
	}

	public void delete(EsmeSubscriber esmeSubscriber) throws Exception {
		bo.delete(esmeSubscriber);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeSubscriber esmeSubscriber) {
		try {
			return bo.checkExited(esmeSubscriber);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeSubscriber> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeSubscriber> findAllWithOrderPaging(EsmeSubscriber esmeSubscriber,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeSubscriber, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeSubscriber esmeSubscriber, boolean exactMatch) {
		try {
			return bo.count(esmeSubscriber, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}

