package com.fis.esme.smsc;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeSmscBo;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.EsmeSmscParam;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://smsc.esme.fis.com/", portName = "SmscTransfererPort", serviceName = "SmscTransfererService")
public class SmscTransferer {
	private EsmeSmscBo bo;

	public SmscTransferer() {
		bo = ServiceLocator.createService(EsmeSmscBo.class);
	};

	public Long add(EsmeSmsc esmeServices) throws Exception {
		return bo.persist(esmeServices);
	}

	public List<EsmeSmscParam> addCopyDataParam(EsmeSmsc copyData,
			EsmeSmsc esmeServices) throws Exception {
		return bo.addCopyDataParam(copyData, esmeServices);
	}

	public void update(EsmeSmsc esmeServices) throws Exception {
		bo.update(esmeServices);
	}

	public void delete(EsmeSmsc esmeServices) throws Exception {
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

	public int checkExisted(EsmeSmsc esmeServices) {
		try {
			return bo.checkExited(esmeServices);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeSmsc> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeSmsc> findAllWithOrderPaging(EsmeSmsc esmeServices,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeServices, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeSmsc esmeServices, boolean exactMatch) {
		try {
			return bo.count(esmeServices, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
