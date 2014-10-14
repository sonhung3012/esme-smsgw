package com.fis.esme.emsmo;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeEmsMoBo;
import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://emsmo.esme.fis.com/", portName = "EmsMoTransfererPort", serviceName = "EmsMoTransfererService")
public class EmsMoTransferer {
	private EsmeEmsMoBo bo;

	public EmsMoTransferer() {
		bo = ServiceLocator.createService(EsmeEmsMoBo.class);
	};

	public Long add(EsmeEmsMo esmeEmsMo) throws Exception {
		return bo.persist(esmeEmsMo);
	}
	
	public void update(EsmeEmsMo esmeEmsMo) throws Exception {
		bo.update(esmeEmsMo);
	}

	public void delete(EsmeEmsMo esmeEmsMo) throws Exception {
		bo.delete(esmeEmsMo);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeEmsMo esmeEmsMo) {
		try {
			return bo.checkExited(esmeEmsMo);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeEmsMo> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeEmsMo> findAllWithOrderPaging(EsmeEmsMo esmeEmsMo,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeEmsMo, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeEmsMo esmeEmsMo, boolean exactMatch) {
		try {
			return bo.count(esmeEmsMo, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
