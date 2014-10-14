package com.fis.esme.smscrouting;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeSmscRoutingBo;
import com.fis.esme.persistence.EsmeSmscRouting;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://smscrouting.esme.fis.com/", portName = "SmscRoutingTransfererPort", serviceName = "SmscRoutingTransfererService")
public class SmscRoutingTransferer {
	private EsmeSmscRoutingBo bo;

	public SmscRoutingTransferer() {
		bo = ServiceLocator.createService(EsmeSmscRoutingBo.class);
	};

	public Long add(EsmeSmscRouting esmeServices) throws Exception {
		return bo.persist(esmeServices);
	}

	public void update(EsmeSmscRouting esmeServices) throws Exception {
		bo.update(esmeServices);
	}

	public void delete(EsmeSmscRouting esmeServices) throws Exception {
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

	public int checkExisted(EsmeSmscRouting esmeServices) {
		try {
			return bo.checkExited(esmeServices);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeSmscRouting> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeSmscRouting> findAllWithOrderPaging(
			SearchEntity searchEntity, EsmeSmscRouting esmeServices,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeServices, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(SearchEntity searchEntity, EsmeSmscRouting esmeServices,
			boolean exactMatch) {
		try {
			return bo.count(esmeServices, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
