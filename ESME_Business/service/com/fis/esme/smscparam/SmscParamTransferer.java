package com.fis.esme.smscparam;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeSmscParamBo;
import com.fis.esme.persistence.EsmeSmscParam;
import com.fis.esme.utils.SearchEntity;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://smscparam.esme.fis.com/", portName = "SmscParamTransfererPort", serviceName = "SmscParamTransfererService")
public class SmscParamTransferer {
	private EsmeSmscParamBo bo;

	public SmscParamTransferer() {
		bo = ServiceLocator.createService(EsmeSmscParamBo.class);
	};

	public Long add(EsmeSmscParam esmeServices) throws Exception {
		return bo.persist(esmeServices);
	}

	public void update(EsmeSmscParam esmeServices) throws Exception {
		bo.update(esmeServices);
	}

	public void delete(EsmeSmscParam esmeServices) throws Exception {
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

	public int checkExisted(EsmeSmscParam esmeServices) {
		try {
			return bo.checkExited(esmeServices);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeSmscParam> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeSmscParam> findAllWithOrderPaging(
			SearchEntity searchEntity, EsmeSmscParam esmeServices,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeServices, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(SearchEntity searchEntity, EsmeSmscParam esmeServices,
			boolean exactMatch) {
		try {
			return bo.count(esmeServices, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public void editDetail(EsmeSmscParam oldObj, EsmeSmscParam newObj)
			throws Exception {
		try {
			bo.update(oldObj, newObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
