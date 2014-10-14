package com.fis.esme.apparam;

import java.util.List;

import javax.jws.WebService;

import com.fis.esme.bo.ApParamBo;
import com.fis.esme.persistence.ApParam;
import com.fis.framework.service.ServiceLocator;

@WebService(targetNamespace = "http://apparam.esme.fis.com/", portName = "ApParamTransfererPort", serviceName = "ApParamTransfererService")
public class ApParamTransferer {
	private ApParamBo bo;

	public ApParamTransferer() {
		bo = ServiceLocator.createService(ApParamBo.class);
	};

	public String add(ApParam apParam) throws Exception {
		return bo.persist(apParam);
	}
	
	public void update(ApParam apParam) throws Exception {
		bo.update(apParam);
	}

	public void delete(ApParam apParam) throws Exception {
		bo.delete(apParam);
	}

	public boolean checkConstraints(String id) {
		try {
			return bo.checkConstraints(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkExisted(ApParam apParam) {
		try {
			return bo.checkExited(apParam);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<ApParam> findAllWithoutParameter() throws Exception {
		return bo.findAll();
	}

	public List<ApParam> findAllWithOrderPaging(ApParam apParam,
			String sortedColumn, boolean asc, int firstItemIndex, int maxItems,
			boolean exactMatch) throws Exception {
		return bo.findAll(apParam, sortedColumn, asc, firstItemIndex,
				maxItems, exactMatch);
	}

	public int count(ApParam apParam, boolean exactMatch) {
		try {
			return bo.count(apParam, exactMatch);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
