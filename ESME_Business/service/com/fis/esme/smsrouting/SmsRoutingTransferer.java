package com.fis.esme.smsrouting;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeSmsRoutingBo;
import com.fis.esme.persistence.EsmeSmsRouting;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://smsrouting.esme.fis.com/", portName = "SmsRoutingTransfererPort", serviceName = "SmsRoutingTransfererService")
public class SmsRoutingTransferer {
	private EsmeSmsRoutingBo bo;

	public SmsRoutingTransferer() {
		bo = ServiceLocator.createService(EsmeSmsRoutingBo.class);
	};

	public Long add(EsmeSmsRouting esmeSmsRouting) throws Exception {
		return bo.persist(esmeSmsRouting);
	}

	public void update(EsmeSmsRouting esmeSmsRouting) throws Exception {
		bo.update(esmeSmsRouting);
	}

	public void delete(EsmeSmsRouting esmeSmsRouting) throws Exception {
		bo.delete(esmeSmsRouting);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting) {
		try {
			return bo.checkExited(searchEntity, esmeSmsRouting);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeSmsRouting> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeSmsRouting> findAllWithParameter(SearchEntity searchEntity,
			EsmeSmsRouting esmeSmsRouting) throws Exception {
		return bo.findAll(searchEntity, esmeSmsRouting);
	}

	public List<EsmeSmsRouting> findAllWithOrderPaging(
			SearchEntity searchEntity, EsmeSmsRouting esmeSmsRouting,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(searchEntity, esmeSmsRouting, sortedColumn, asc,
				firstItemIndex, maxItems, exactMatch);
	}

	public int count(SearchEntity searchEntity, EsmeSmsRouting esmeSmsRouting,
			boolean exactMatch) {
		try {
			return bo.count(searchEntity, esmeSmsRouting, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int countAll() {
		try {
			return bo.countAll();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
