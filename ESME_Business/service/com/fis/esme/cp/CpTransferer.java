package com.fis.esme.cp;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.EsmeCPBo;
import com.fis.esme.persistence.EsmeCp;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://cp.esme.fis.com/", portName = "CpTransfererPort", serviceName = "CpTransfererService")
public class CpTransferer {
	private EsmeCPBo bo;

	public CpTransferer() {
		bo = ServiceLocator.createService(EsmeCPBo.class);
	};

	public Long add(EsmeCp esmeCp) throws Exception {
		return bo.persist(esmeCp);
	}
	
	public void update(EsmeCp esmeCp) throws Exception {
		bo.update(esmeCp);
	}

	public void delete(EsmeCp esmeCp) throws Exception {
		bo.delete(esmeCp);
	}

	public boolean checkConstraints(Long id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(EsmeCp esmeCp) {
		try {
			return bo.checkExited(esmeCp);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<EsmeCp> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<EsmeCp> findAllWithOrderPaging(EsmeCp esmeCp,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(esmeCp, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(EsmeCp esmeCp, boolean exactMatch) {
		try {
			return bo.count(esmeCp, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
