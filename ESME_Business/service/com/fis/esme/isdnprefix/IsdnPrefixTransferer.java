package com.fis.esme.isdnprefix;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeIsdnPrefixBo;
import com.fis.esme.persistence.EsmeIsdnPrefix;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://isdnprefix.esme.fis.com/", portName = "IsdnPrefixTransfererPort", serviceName = "IsdnPrefixTransfererService")
public class IsdnPrefixTransferer {
	private EsmeIsdnPrefixBo bo;

	public IsdnPrefixTransferer() {
		bo = ServiceLocator.createService(EsmeIsdnPrefixBo.class);
	};

	public Long add(EsmeIsdnPrefix esmeServices) throws Exception {
		return bo.persist(esmeServices);
	}

	public void update(EsmeIsdnPrefix esmeServices) throws Exception {
		bo.update(esmeServices);
	}

	public void delete(EsmeIsdnPrefix esmeServices) throws Exception {
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

	public int checkExisted(EsmeIsdnPrefix esmeServices) {
		try {
			return bo.checkExited(esmeServices);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeIsdnPrefix> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeIsdnPrefix> findAllWithOrderPaging(
			EsmeIsdnPrefix esmeServices, String sortedColumn, boolean asc,
			int firstItemIndex, int maxItems, boolean exactMatch)
			throws Exception {
		return bo.findAll(esmeServices, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeIsdnPrefix esmeServices, boolean exactMatch) {
		try {
			return bo.count(esmeServices, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
