package com.fis.esme.shortcode;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeShortCodeBo;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://shortcode.esme.fis.com/", portName = "ShortCodeTransfererPort", serviceName = "ShortCodeTransfererService")
public class ShortCodeTransferer {
	private EsmeShortCodeBo bo;

	public ShortCodeTransferer() {
		bo = ServiceLocator.createService(EsmeShortCodeBo.class);
	};

	public Long add(EsmeShortCode esmeShortCode) throws Exception {
		return bo.persist(esmeShortCode);
	}

	public void update(EsmeShortCode esmeShortCode) throws Exception {
		bo.update(esmeShortCode);
	}

	public void delete(EsmeShortCode esmeShortCode) throws Exception {
		bo.delete(esmeShortCode);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeShortCode esmeShortCode) {
		try {
			return bo.checkExited(esmeShortCode);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeShortCode> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeShortCode> findAllWithOrderPaging(
			EsmeShortCode esmeShortCode, String sortedColumn, boolean asc,
			int firstItemIndex, int maxItems, boolean exactMatch)
			throws Exception {
		return bo.findAll(esmeShortCode, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeShortCode esmeShortCode, boolean exactMatch) {
		try {
			return bo.count(esmeShortCode, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
