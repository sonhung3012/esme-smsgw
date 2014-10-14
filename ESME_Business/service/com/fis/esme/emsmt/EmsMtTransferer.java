package com.fis.esme.emsmt;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeEmsMtBo;
import com.fis.esme.persistence.EsmeEmsMt;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://emsmt.esme.fis.com/", portName = "EmsMtTransfererPort", serviceName = "EmsMtTransfererService")
public class EmsMtTransferer {
	private EsmeEmsMtBo bo;

	public EmsMtTransferer() {
		bo = ServiceLocator.createService(EsmeEmsMtBo.class);
	};

	public Long add(EsmeEmsMt esmeEmsMt) throws Exception {
		return bo.persist(esmeEmsMt);
	}
	
	public void update(EsmeEmsMt esmeEmsMt) throws Exception {
		bo.update(esmeEmsMt);
	}

	public void delete(EsmeEmsMt esmeEmsMt) throws Exception {
		bo.delete(esmeEmsMt);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeEmsMt esmeEmsMt) {
		try {
			return bo.checkExited(esmeEmsMt);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeEmsMt> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeEmsMt> findAllWithOrderPaging(EsmeEmsMt esmeEmsMt,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeEmsMt, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeEmsMt esmeEmsMt, boolean exactMatch) {
		try {
			return bo.count(esmeEmsMt, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	public EsmeEmsMt findByMtID(long id) throws Exception {
		return bo.findByMtID(id);
	}
}
