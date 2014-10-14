package com.fis.esme.service;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeServiceBo;
import com.fis.esme.persistence.EsmeServices;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://service.esme.fis.com/", portName = "ServiceTransfererPort", serviceName = "ServiceTransfererService")
public class ServiceTransferer {
	private EsmeServiceBo bo;

	public ServiceTransferer() {
		bo = ServiceLocator.createService(EsmeServiceBo.class);
	};

	public Long add(EsmeServices esmeServices) throws Exception {
		return bo.persist(esmeServices);
	}
	
	public void update(EsmeServices esmeServices) throws Exception {
		bo.update(esmeServices);
	}

	public void delete(EsmeServices esmeServices) throws Exception {
		bo.delete(esmeServices);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeServices esmeServices) {
		try {
			return bo.checkExited(esmeServices);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeServices> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeServices> findAllWithOrderPaging(EsmeServices esmeServices,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeServices, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeServices esmeServices, boolean exactMatch) {
		try {
			return bo.count(esmeServices, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
